import React, { useEffect, useState } from "react";
import { BsSearch, BsTrashFill } from "react-icons/bs";
import HistoryItem from "../components/HistoryItem";
import { useCookies } from "react-cookie";
import { useNavigate } from "react-router-dom";
import { ConfirmDialog } from "../components/ConfirmDialog";

const History = () => {
  const navigate = useNavigate();
  const [input, setInput] = useState("");
  const [history, setHistory] = useState([]);
  const [cookies, setCookie, removeCookie] = useCookies(["token"]);
  const [actionType, setActionType] = useState("");
  const [timeRange, setTimeRange] = useState(0);
  const [openConfirm, setOpenConfirm] = useState(false);

  const formatDate = (date) => {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, "0"); // Months are 0-based
    const day = String(date.getDate()).padStart(2, "0");

    return `${year}-${month}-${day}`;
  };

  const constructParams = () => {
    let params = "";
    if (input) {
      params += `&keyword=${input}`;
    }
    if (actionType) {
      params += `&action=${actionType}`;
    }
    if (timeRange > 0) {
      let date = new Date();
      date.setDate(date.getDate() - (timeRange - 1));
      params += `&from=${formatDate(date)}`;
    }
    return params;
  };

  const fetchHistory = async () => {
    const response = await fetch(
      `http://localhost:8080/api/v1/history?page=0&size=1000${constructParams()}`,
      {
        method: "GET",
        headers: {
          Authorization: `Bearer ${cookies.token}`,
        },
      }
    );
    const data = await response.json();
    setHistory(data.content);
  };

  const deleteAll = async () => {
    const response = await fetch(`http://localhost:8080/api/v1/history`, {
      method: "DELETE",
      headers: {
        Authorization: `Bearer ${cookies.token}`,
      },
    });
    if (response.ok) {
      fetchHistory();
    }
  };

  useEffect(() => {
    if (cookies.token) {
      fetchHistory();
    } else {
      navigate("/login");
    }
  }, []);

  const handleChange = (event) => {
    setInput(event.target.value);
  };

  const historyItems = history.map((item) => (
    <HistoryItem
      key={item.uuid}
      uuid={item.uuid}
      action={item.action}
      input={item.input}
      output={item.output}
      sourceLanguage={item.sourceLanguage}
      targetLanguage={item.targetLanguage}
      liked={item.liked}
      timestamp={item.timestamp}
      fetchHistory={fetchHistory}
    />
  ));

  return (
    <div className="h-full py-8 flex items-start justify-center">
      <div className="w-1/2 flex flex-col items-center justify-center gap-4">
        <h3 className="text-3xl leading-7">歴史</h3>
        <div className="w-2/3 flex items-center justify-center gap-2">
          <div className="relative w-full flex items-center pl-2 border border-lightGray rounded-md shadow-md">
            <div className="text-gray">
              <BsSearch />
            </div>
            <input
              type="search"
              name="search"
              id="search"
              placeholder="キーワードで履歴を検索する"
              className="w-full p-2 bg-transparent outline-none"
              value={input}
              onChange={(e) => handleChange(e)}
              onKeyDown={(e) => {
                if (e.key === "Enter") {
                  fetchHistory();
                }
              }}
            />
          </div>

          <button
            className="px-4 h-10 w-20 flex items-center justify-center px-4 bg-blue text-white shadow-md rounded-md hover:bg-darkBlue disabled:bg-disabled"
            onClick={() => fetchHistory()}
          >
            検索
          </button>
        </div>

        <div className="flex items-center justify-center gap-2">
          <h4>タイプ:</h4>
          <select
            className="px-4 h-10 flex items-center gap-2 px-4 border border-lightGray shadow-md rounded-md"
            onChange={(e) => {
              setActionType(e.target.value);
            }}
          >
            <option value="">全て</option>
            <option value="INTENT_EXPRESSION">意図の表現</option>
            <option value="EASY_JAPANESE_MODE">簡単な日本語モード</option>
            <option value="TRANSLATION">翻訳</option>
          </select>
          <h4>時間範囲:</h4>
          <select
            className="px-4 w-18 h-10 flex items-center gap-2 px-4 border border-lightGray shadow-md rounded-md"
            onChange={(e) => {
              setTimeRange(e.target.value);
            }}
          >
            <option value={0}>全て</option>
            <option value={1}>最後の1日</option>
            <option value={3}>最後の3日間</option>
            <option value={7}>最後の7日間</option>
          </select>
        </div>

        <div className="w-full flex items-center justify-end gap-2">
          <button
            className="px-4 h-10 flex items-center px-4 bg-red text-white shadow-md rounded-md hover:bg-darkRed disabled:bg-disabled"
            onClick={() => setOpenConfirm(true)}
          >
            <BsTrashFill /> &nbsp;すべてを削除する
          </button>
        </div>

        <div className="w-full flex flex-col items-center gap-4">
          {historyItems.length > 0 ? (
            historyItems
          ) : (
            <h1>アイテムが見つかりませんでした。</h1>
          )}
        </div>
      </div>
      {openConfirm && (
        <ConfirmDialog
          open={openConfirm}
          setOpen={setOpenConfirm}
          title={"削除を確認"}
          content={"履歴のすべてのアイテムを削除してもよろしいですか？"}
          handleConfirm={deleteAll}
        />
      )}
    </div>
  );
};

export default History;
