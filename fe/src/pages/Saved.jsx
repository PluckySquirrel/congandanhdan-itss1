import React, { useEffect, useState } from "react";
import { BsSearch, BsTrashFill } from "react-icons/bs";
import { useCookies } from "react-cookie";
import { useNavigate } from "react-router-dom";
import SavedItem from "../components/SavedItem";
import { ConfirmDialog } from "../components/ConfirmDialog";

const Saved = () => {
  const navigate = useNavigate();
  const [input, setInput] = useState("");
  const [savedList, setSavedList] = useState([]);
  const [cookies, setCookie, removeCookie] = useCookies(["token"]);
  const [openConfirm, setOpenConfirm] = useState(false);

  const fetchSaved = async () => {
    const response = await fetch(
      `http://localhost:8080/api/v1/saved?page=0&size=1000&keyword=${input}`,
      {
        method: "GET",
        headers: {
          Authorization: `Bearer ${cookies.token}`,
        },
      }
    );
    const data = await response.json();
    setSavedList(data.content);
  };

  const deleteAll = async () => {
    const response = await fetch(`http://localhost:8080/api/v1/saved`, {
      method: "DELETE",
      headers: {
        Authorization: `Bearer ${cookies.token}`,
      },
    });
    if (response.ok) {
      fetchSaved();
    }
  };

  useEffect(() => {
    if (cookies.token) {
      fetchSaved();
    } else {
      navigate("/login");
    }
  }, []);

  const handleChange = (event) => {
    setInput(event.target.value);
  };

  const savedItems = savedList.map((item) => (
    <SavedItem
      key={item.uuid}
      uuid={item.uuid}
      action={item.action}
      input={item.input}
      output={item.output}
      sourceLanguage={item.sourceLanguage}
      targetLanguage={item.targetLanguage}
      timestamp={item.timestamp}
      fetchSaved={fetchSaved}
    />
  ));

  return (
    <div className="h-full py-4 flex items-start justify-center">
      <div className="w-1/2 flex flex-col items-center justify-center gap-8">
        <h3 className="text-3xl leading-7">保存歴史</h3>
        <div className="w-2/3 flex items-center justify-center gap-2">
          <div className="relative w-full flex items-center pl-2 border border-lightGray rounded-md shadow-md">
            <div className="text-gray">
              <BsSearch />
            </div>
            <input
              type="search"
              name="search"
              id="search"
              placeholder="キーワードで検索する"
              className="w-full p-2 bg-transparent outline-none"
              value={input}
              onChange={(e) => handleChange(e)}
              onKeyDown={(e) => e.key === "Enter" && fetchSaved()}
            />
          </div>

          <button
            className="px-4 h-10 w-20 flex items-center justify-center px-4 bg-blue text-white shadow-md rounded-md hover:bg-darkBlue disabled:bg-disabled"
            onClick={fetchSaved}
          >
            検索
          </button>
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
          {savedItems.length > 0 ? (
            savedItems
          ) : (
            <p>保存されたアイテムはありません。</p>
          )}
        </div>
      </div>
      {openConfirm && (
        <ConfirmDialog
          open={openConfirm}
          setOpen={setOpenConfirm}
          title={"削除を確認"}
          content={"保存履歴のすべてのアイテムを削除してもよろしいですか？"}
          handleConfirm={deleteAll}
        />
      )}
    </div>
  );
};

export default Saved;
