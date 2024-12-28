import {
  BsCopy,
  BsHandThumbsUp,
  BsHandThumbsUpFill,
  BsMicFill,
  BsPencilFill,
  BsVolumeUpFill,
} from "react-icons/bs";
import React, { useEffect, useState } from "react";
import getOutputLanguageTag from "../utils/getOutputLanguageTag";
import { useCookies } from "react-cookie";
import { useNavigate } from "react-router-dom";
import EditButton from "../components/EditButton";
import CopyButton from "../components/CopyButton";
import getLanguageFromTag from "../utils/getLanguageFromTag";

const Home = () => {
  const navigate = useNavigate();

  const [cookies, setCookie, removeCookie] = useCookies(["token"]);

  const [input, setInput] = useState("");
  const [output, setOutput] = useState("");
  const [translateLanguage, setTranslateLanguage] = useState("VIETNAMESE");
  const [outputLanguage, setOutputLanguage] = useState("vi");
  const [listening, setListening] = useState(false);
  const [uuid, setUuid] = useState("");
  const [liked, setLiked] = useState(false);

  useEffect(() => {
    if (!cookies.token) {
      navigate("/login");
    }
  }, []);

  const handleInputChange = (e) => {
    setInput(e.target.value);
  };

  const handleLanguageChange = (e) => {
    const language = e.target.value;
    setTranslateLanguage(language);
    // setOutputLanguage(getOutputLanguageTag(language));
  };

  const getVoiceInput = () => {
    const SpeechRecognition =
      window.SpeechRecognition || window.webkitSpeechRecognition;

    const recognition = new SpeechRecognition();
    recognition.lang = "ja";

    recognition.start();

    recognition.onstart = () => {
      setListening(true);
    };

    recognition.onresult = (event) => {
      const transcript = event.results[0][0].transcript;
      setInput(transcript);
    };

    recognition.onend = () => {
      setListening(false);
    };
  };

  const checkResponse = (response, data) => {
    if (!response.ok) {
      const message = `An error has occured: ${response.status} - ${data.message}`;
      console.error(message);
      window.alert("エラーが発生しました");
      return false;
    }
    return true;
  };

  const getEasyJapanese = async () => {
    setOutputLanguage("ja");
    setOutput("ローディング...");
    const response = await fetch("http://localhost:8080/api/v1/easy-japanese", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: cookies.token ? `Bearer ${cookies.token}` : null,
      },
      body: JSON.stringify({ input }),
    });
    const data = await response.json();

    if (checkResponse(response, data)) {
      setOutput(data.output);
      setUuid(data.uuid);
      setLiked(false);
    }
  };

  const getIntent = async () => {
    setOutputLanguage("ja");
    setOutput("ローディング...");
    const response = await fetch(
      "http://localhost:8080/api/v1/express-intent",
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: cookies.token ? `Bearer ${cookies.token}` : null,
        },
        body: JSON.stringify({ input }),
      }
    );
    const data = await response.json();

    if (checkResponse(response, data)) {
      setOutput(data.output);
      setUuid(data.uuid);
      setLiked(false);
    }
  };

  const getTranslation = async () => {
    const text = output;
    setOutput("ローディング...");
    console.log(outputLanguage);
    const response = await fetch("http://localhost:8080/api/v1/translate", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: cookies.token ? `Bearer ${cookies.token}` : null,
      },
      body: JSON.stringify({ sourceLanguage: getLanguageFromTag(outputLanguage), targetLanguage: translateLanguage, input: text }),
    });
    const data = await response.json();

    if (checkResponse(response, data)) {
      setOutputLanguage(getOutputLanguageTag(translateLanguage));
      setOutput(data.output);
      setUuid(data.uuid);
      setLiked(false);
    }
  };

  const suaGauGau = (phrase, language = "ja") => {
    const utterance = new SpeechSynthesisUtterance(phrase);
    utterance.lang = language;
    speechSynthesis.speak(utterance);
  };

  const toggleLike = async (uuid) => {
    let action = liked ? "unlike" : "like";
    const response = await fetch(
      `http://localhost:8080/api/v1/history/${uuid}/${action}`,
      {
        method: "POST",
        headers: {
          Authorization: cookies.token ? `Bearer ${cookies.token}` : null,
        },
      }
    );
    if (response.ok) {
      setLiked(!liked);
    }
  };

  return (
    <div className="flex flex-col items-center py-4">
      <h1 className="text-3xl">入力</h1>
      <div className="relative w-1/2 h-44 my-4 border border-lightGray shadow-md rounded-md ">
        <textarea
          name="input"
          id="input"
          className="w-full h-32 p-4 outline-none resize-none"
          placeholder="何かを入力してください"
          value={input}
          onChange={(e) => handleInputChange(e)}
        />
        <button
          className="absolute bottom-2 left-4 text-gray hover:text-darkGray"
          onClick={() => suaGauGau(input)}
        >
          <BsVolumeUpFill size="2rem" />
        </button>
      </div>

      <button
        className="px-4 py-2 flex gap-2 border border-lightGray shadow-md rounded-md hover:bg-lightGray"
        onClick={() => getVoiceInput()}
      >
        <div className="w-6 h-6 flex items-center justify-center rounded-full bg-red text-white">
          <BsMicFill />
        </div>
        {listening ? "聞いている中..." : "音声入力"}
      </button>
      <div className="w-1/2 my-4 flex items-center justify-center gap-4">
        <button
          className="px-4 h-10 flex items-center gap-2 px-4 bg-blue text-white shadow-md rounded-md hover:bg-darkBlue disabled:bg-disabled"
          onClick={() => getEasyJapanese()}
          disabled={input === ""}
        >
          簡単な日本語モード
        </button>
        <button
          className="px-4 h-10 flex items-center gap-2 px-4 bg-blue text-white shadow-md rounded-md hover:bg-darkBlue disabled:bg-disabled"
          onClick={() => getIntent()}
          disabled={input === ""}
        >
          意図を表現する
        </button>
        または
        <select
          className="px-4 h-10 flex items-center gap-2 px-4 border border-lightGray shadow-md rounded-md"
          onChange={(e) => handleLanguageChange(e)}
        >
          <option value="VIETNAMESE">ベトナム語</option>
          <option value="ENGLISH">英語</option>
        </select>
        <button
          className="px-4 h-10 flex items-center gap-2 px-4 bg-blue text-white shadow-md rounded-md hover:bg-darkBlue disabled:bg-disabled"
          onClick={() => getTranslation()}
          disabled={output === "" || output == "ローディング..."}
        >
          翻訳する
        </button>
      </div>
      <h1 className="text-3xl">出力</h1>
      <div className="relative w-1/2 h-44 p-4 my-4 border border-lightGray shadow-md rounded-md">
        <p
          className={`w-full h-32 text-wrap text-left ${
            output === "" && "text-gray"
          } caret-transparent`}
        >
          {output !== ""
            ? output
            : "出力された意図の表現または翻訳がここに表示されます"}
        </p>
        <button
          className="absolute bottom-2 left-4 text-gray hover:text-darkGray"
          onClick={() => suaGauGau(output, outputLanguage)}
        >
          <BsVolumeUpFill size="2rem" />
        </button>
        <div className="absolute bottom-4 right-4 flex gap-4 text-gray">
          <CopyButton
            output={output}
            disabled={!cookies.token || output === "" || output == "ローディング..."}
          />
          <button
            className="hover:text-darkGray"
            disabled={!cookies.token || output === "" || output == "ローディング..."}
            onClick={() => toggleLike(uuid)}
          >
            {liked ? (
              <BsHandThumbsUpFill size="1.6rem" />
            ) : (
              <BsHandThumbsUp size="1.6rem" />
            )}
          </button>
          <EditButton
            output={output}
            setOutput={setOutput}
            uuid={uuid}
            disabled={!cookies.token || output === "" || output == "ローディング..."}
          />
        </div>
      </div>
    </div>
  );
};

export default Home;
