import React, { useState } from 'react'
import Dialog from '../components/Dialog';

const ForgotPassword = () => {
  const [input, setInput] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [showDialog, setShowDialog] = useState(false);
  const [sent, setSent] = useState(false);

  const handleChange = (event) => {
    setInput(event.target.value)
  }

  const submit = async (e) => {
    e.preventDefault();
    setLoading(true);
    const response = await fetch('http://localhost:8080/api/v1/users/forgot-password', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ email: input })
    });
    const data = await response.json();
    setLoading(false);
    if (response.ok) {
      // setShowDialog(true);
      setSent(true);
    }

    if (!response.ok) {
      setError(data.message);
      return;
    }
  }

  return (
    <form className='h-full flex flex-col items-center justify-center gap-4' onSubmit={submit}>
      <div className='flex gap-2'>
        <h3 className='text-4xl'>パスワードを忘れた</h3>
      </div>
      <input 
        type="email" 
        name="email" 
        id="email" 
        placeholder="メールアドレス"
        className="w-96 p-3 text-lg bg-transparent border border-lightGray rounded-md shadow-md"
        value={input}
        onChange={(e) => handleChange(e)}
        required
      />
      {error !== '' && <div className='text-lg text-error'>{error}</div>}
      {!sent ? <button 
        className='px-6 h-12 flex items-center bg-blue text-white text-lg shadow-md rounded-md hover:bg-darkBlue disabled:bg-disabled'
        type='submit'
        disabled={loading}
      >
        パスワードリセットメールを送信する
      </button> : <div className='text-lg text-success'>パスワードリセットメールを送信しました</div>}
      {showDialog && 
        <Dialog
          message='Password reset e-mail sent'
          showDialog={showDialog}
          setShowDialog={setShowDialog}
        />
      }
    </form>
  )
}

export default ForgotPassword