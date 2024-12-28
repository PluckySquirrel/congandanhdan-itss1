import React, { useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom';

const ResetPassword = () => {
  const navigate = useNavigate();
  const { token } = useParams();
  const [inputs, setInputs] = useState({});
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const handleChange = (event) => {
    const name = event.target.name;
    const value = event.target.value;
    setInputs(values => ({...values, [name]: value}))
  }

  const submit = async (e) => {
    e.preventDefault();
    setLoading(true);
    const response = await fetch('http://localhost:8080/api/v1/users/reset-password', {
      method: 'PUT',
      headers: { 
        'Content-Type': 'application/json' 
      },
      body: JSON.stringify({
        token: token,
        newPassword: inputs.password,
      })
    });
    const data = await response.json();
    setLoading(false);

    if (!response.ok) {
      setError(data.message);
      return;
    }

    navigate('/login');
  }

  return (
    <form className='h-full flex flex-col items-center justify-center gap-4' onSubmit={submit}>
      <div className='flex gap-2'>
        <h3 className='text-4xl'>パスワードをリセット</h3>
      </div>
      <input 
        type="password" 
        name="password" 
        id="password" 
        placeholder="新しいパスワード"
        className="w-96 p-3 text-lg bg-transparent border border-lightGray rounded-md shadow-md"
        value={inputs.password || ''}
        onChange={(e) => handleChange(e)}
        required
      />
      <input 
        type="password" 
        name="confirmPassword" 
        id="confirmPassword" 
        placeholder="新しいパスワードを再入力"
        className="w-96 p-3 text-lg bg-transparent border border-lightGray rounded-md shadow-md"
        value={inputs.confirmPassword || ''}
        onChange={(e) => handleChange(e)}
        required
      />
      {error !== '' && <div className='text-lg text-error'>{error}</div>}
      <button 
        className='px-6 h-12 flex items-center bg-blue text-white text-lg shadow-md rounded-md hover:bg-darkBlue disabled:bg-disabled'
        type='submit'
        disabled={loading}
      >
        パスワードをリセットする
      </button>
    </form>
  )
}

export default ResetPassword