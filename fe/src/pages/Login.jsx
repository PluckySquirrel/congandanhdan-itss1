import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom';
import { useCookies } from 'react-cookie';

const Login = () => {
  const navigate = useNavigate();
  const [inputs, setInputs] = useState({});
  const [cookies, setCookie, removeCookie] = useCookies(['token']);
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
    const response = await fetch('http://localhost:8080/api/v1/users/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(inputs)
    });
    const data = await response.json();
    setLoading(false);

    if (!response.ok) {
      setError(data.message);
      return;
    }

    setCookie('token', data.accessToken, {
      secure: true,
      sameSite: 'lax',
    })
    navigate('/');
  }

  return (
    <form className='h-full flex flex-col items-center justify-center gap-4' onSubmit={submit}>
      <div className='flex gap-2'>
        <h3 className='text-3xl leading-7'>Welcome to </h3><img src="/logo.svg" className='h-8' />
      </div>
      <input 
        type="text" 
        name="username" 
        id="username" 
        placeholder="Username"
        className="w-96 p-3 text-lg bg-transparent border border-lightGray rounded-md shadow-md"
        value={inputs.username || ''}
        onChange={(e) => handleChange(e)}
        required
      />
      <input 
        type="password" 
        name="password" 
        id="password" 
        placeholder="Password"
        className="w-96 p-3 text-lg bg-transparent border border-lightGray rounded-md shadow-md"
        value={inputs.password || ''}
        onChange={(e) => handleChange(e)}
        required
      />
      {error !== '' && <div className='text-lg text-error'>{error}</div>}
      <button 
        className='px-6 h-12 flex items-center px-4 bg-blue text-white text-lg shadow-md rounded-md hover:bg-darkBlue disabled:bg-disabled'
        type='submit'
        disabled={loading}
      >
        Sign in
      </button>
      <div>
        <span className='text-lg'>Don't have an account?</span> <span className='text-red text-lg hover:underline'><a href="/signup">Sign up</a></span>
        <div className='mt-2 text-red text-lg hover:underline'><a href="/forgot-password">Forgotten password?</a></div>
      </div>
    </form>
  )
}

export default Login