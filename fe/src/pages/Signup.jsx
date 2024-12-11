import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom';

const Signup = () => {
  const navigate = useNavigate();
  const [inputs, setInputs] = useState({});

  const handleChange = (event) => {
    const name = event.target.name;
    const value = event.target.value;
    setInputs(values => ({...values, [name]: value}))
  }

  const submit = async (e) => {
    e.preventDefault();
    const response = await fetch('http://localhost:8080/api/v1/users/register', {
      method: 'POST',
      headers: { 
        'Content-Type': 'application/json' 
      },
      body: JSON.stringify(inputs)
    });
    const data = await response.json();

    if (!response.ok) {
      const message = `An error has occured: ${response.status} - ${data.message}`;
      console.error(message);
      return;
    }

    navigate('/login');
  }

  return (
    <form className='h-full flex flex-col items-center justify-center gap-4' onSubmit={submit}>
      <div className='flex gap-2'>
        <h3 className='text-3xl leading-7'>Create your</h3><img src="/logo.svg" className='h-8'/> <h3 className='text-3xl leading-7'>account</h3>
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
        type="text" 
        name="name" 
        id="name" 
        placeholder="Name"
        className="w-1/5 p-2 bg-transparent border border-lightGray rounded-md shadow-md"
        value={inputs.name || ''}
        onChange={(e) => handleChange(e)}
      />
      <input 
        type="email" 
        name="email" 
        id="email" 
        placeholder="E-mail"
        className="w-1/5 p-2 bg-transparent border border-lightGray rounded-md shadow-md"
        value={inputs.email || ''}
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
      <input 
        type="password" 
        name="confirmPassword" 
        id="confirmPassword" 
        placeholder="Confirm password"
        className="w-1/5 p-2 bg-transparent border border-lightGray rounded-md shadow-md"
        value={inputs.confirmPassword || ''}
        onChange={(e) => handleChange(e)}
      />
      <button 
        className='px-4 h-10 flex items-center px-4 bg-blue text-white shadow-md rounded-md hover:bg-darkBlue disabled:bg-disabled'
        type='submit'
      >
        Create account
      </button>
      <div>
        Already have an account? <span className='text-red hover:underline'><a href="/login">Sign in</a></span>
      </div>
    </form>
  )
}

export default Signup