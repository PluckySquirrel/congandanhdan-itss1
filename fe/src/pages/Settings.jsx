import React, { useEffect, useState } from "react";
import { useCookies } from "react-cookie";
import { useNavigate } from "react-router-dom";
import { jwtDecode } from "jwt-decode";
import Dialog from "../components/Dialog";
import { BsPersonCircle } from "react-icons/bs";

const Settings = (props) => {
  const navigate = useNavigate();
  const [inputs, setInputs] = useState({});
  const [changed, setChanged] = useState(false);
  const [loading, setLoading] = useState(false);
  const [showDialog, setShowDialog] = useState(false);
  const [error, setError] = useState("");
  const [cookies, setCookie, removeCookie] = useCookies(["token"]);
  const [avatar, setAvatar] = useState(null);
  const [file, setFile] = useState(null);
  const decoded = jwtDecode(cookies.token);

  const fetchUser = async () => {
    const response = await fetch(
      `http://localhost:8080/api/v1/users/${decoded.uuid}`,
      {
        method: "GET",
        headers: {
          Authorization: `Bearer ${cookies.token}`,
        },
      }
    );
    const data = await response.json();
    setInputs(data);
    if (data.avatarUrl) {
      setAvatar(data.avatarUrl); // Assuming the avatar URL is stored here
    }
  };

  useEffect(() => {
    if (cookies.token) {
      fetchUser();
    } else {
      navigate("/login");
    }
  }, []);

  const handleChange = (event) => {
    const name = event.target.name;
    const value = event.target.value;
    setChanged(true);
    setInputs((values) => ({ ...values, [name]: value }));
  };

  const handleAvatarChange = (e) => {
    const file = e.target.files[0];
    if (file) {
      setAvatar(URL.createObjectURL(file)); // Create a temporary URL for preview
      setChanged(true);
      setFile(file);
    }
  };

  const handleAvatarClick = () => {
    document.getElementById("avatar-upload").click(); // Trigger the file input click
  };

  const submit = async (e) => {
    e.preventDefault();
    setLoading(true);

    if (file) {
      const formData = new FormData();
      formData.append("file", file);
      try {
        const response = await fetch(
          `http://localhost:8080/api/v1/users/${decoded.uuid}/upload-avatar`, // Replace with your actual endpoint
          {
            method: 'POST',
            body: formData, // The form data with the file
          }
        );
  
        if (!response.ok) {
          setError('Failed to upload file');
          return;
        }
  
        const result = await response.json();
        console.log('Upload successful:', result);
      } catch (error) {
        console.error('Error uploading file:', error);
      }
    }

    const response = await fetch(
      `http://localhost:8080/api/v1/users/${decoded.uuid}`,
      {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(inputs),
      }
    );
    setChanged(false);
    setLoading(false);
    if (response.ok) {
      setShowDialog(true);
    }
    const data = await response.json();
    console.log(data);

    if (!response.ok) {
      setError(data.message);
      return;
    }

    props.setAvatar(data.avatarUrl);
  };

  return (
    <div className="h-full py-4 flex items-center justify-center">
      <form
        className="w-1/2 flex flex-col items-center justify-center gap-8 bg-white shadow-lg py-4"
        onSubmit={submit}
      >
        <h3 className="text-3xl leading-7">設定</h3>

        {/* Avatar Section */}
        <div className="w-2/3 flex flex-col items-center">
          <div
            className="w-36 h-36 rounded-full overflow-hidden mb-4 border border-lightGray shadow-md cursor-pointer"
            onClick={handleAvatarClick} // Trigger file input click on avatar click
          >
            {avatar ? (
              <img
                src={avatar || "default-avatar.png"} // Provide a default avatar image
                alt="Avatar"
                className="w-full h-full object-cover"
              />
            ) : (
              <BsPersonCircle className="w-full h-full object-cover text-darkGray" />
            )}
          </div>

          {/* Hidden file input */}
          <input
            type="file"
            accept="image/*"
            id="avatar-upload"
            onChange={handleAvatarChange}
            style={{ display: "none" }} // Hide the input element
          />
          <p className="text-sm text-gray-500">
            クリックして新しい画像をアップロード
          </p>
        </div>

        {/* User Info Fields */}
        <div className="w-2/3 flex items-center gap-2">
          <div className="w-36 text-start text-lg">ユーザー名</div>
          <input
            type="text"
            name="username"
            id="username"
            placeholder="ユーザー名"
            className="w-96 p-3 text-lg bg-transparent border border-lightGray rounded-md shadow-md"
            defaultValue={inputs.username || ""}
            onChange={(e) => handleChange(e)}
            required
          />
        </div>

        <div className="w-2/3 flex items-center gap-2">
          <div className="w-36 text-start text-lg">名前</div>
          <input
            type="text"
            name="name"
            id="name"
            placeholder="名前"
            className="w-96 p-3 text-lg bg-transparent border border-lightGray rounded-md shadow-md"
            defaultValue={inputs.name || ""}
            onChange={(e) => handleChange(e)}
            required
          />
        </div>

        <div className="w-2/3 flex items-center gap-2">
          <div className="w-36 text-start text-lg">メールアドレス</div>
          <input
            type="email"
            name="email"
            id="email"
            placeholder="メールアドレス"
            className="w-96 p-3 text-lg bg-transparent border border-lightGray rounded-md shadow-md"
            defaultValue={inputs.email || ""}
            onChange={(e) => handleChange(e)}
            required
          />
        </div>

        {error !== '' && <div className='text-lg text-error'>{error}</div>}

        <button
          className="px-6 h-12 flex items-center bg-blue text-white text-lg shadow-md rounded-md hover:bg-darkBlue disabled:bg-disabled"
          type="submit"
          disabled={loading || !changed}
        >
          変更を保存する
        </button>

        {showDialog && (
          <Dialog
            message="変更が保存されました"
            showDialog={showDialog}
            setShowDialog={setShowDialog}
          />
        )}
      </form>
    </div>
  );
};

export default Settings;
