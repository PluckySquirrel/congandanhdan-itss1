import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom';
import { useCookies } from 'react-cookie';

const Login = () => {
  const navigate = useNavigate();
  const [inputs, setInputs] = useState({});
  const [cookies, setCookie, removeCookie] = useCookies(['token'])
  const [loading, setLoading] = useState(false);

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
      const message = `An error has occured: ${response.status} - ${data.message}`;
      console.error(message);
      window.alert(data.message);
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
        className="w-1/5 p-2 bg-transparent border border-lightGray rounded-md shadow-md"
        value={inputs.username || ''}
        onChange={(e) => handleChange(e)}
      />
      <input 
        type="password" 
        name="password" 
        id="password" 
        placeholder="Password"
        className="w-1/5 p-2 bg-transparent border border-lightGray rounded-md shadow-md"
        value={inputs.password || ''}
        onChange={(e) => handleChange(e)}
      />
      <button 
        className='px-4 h-10 flex items-center px-4 bg-blue text-white shadow-md rounded-md hover:bg-darkBlue disabled:bg-disabled'
        type='submit'
        disabled={loading}
      >
        Sign in
      </button>
      <div>
        Don't have an account? <span className='text-red hover:underline'><a href="/signup">Sign up</a></span>
        <div className='mt-2 text-red hover:underline'><a href="/forgot-password">Forgotten password?</a></div>
      </div>
    </form>
  )
}

export default Login