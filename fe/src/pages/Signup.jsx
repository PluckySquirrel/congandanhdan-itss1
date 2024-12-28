import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom';

const Signup = () => {
  const navigate = useNavigate();
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
    const response = await fetch('http://localhost:8080/api/v1/users/register', {
      method: 'POST',
      headers: { 
        'Content-Type': 'application/json' 
      },
      body: JSON.stringify(inputs)
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
        <h3 className='text-4xl leading-9'>Create your</h3><img src="/logo.svg" className='h-10'/> <h3 className='text-4xl leading-9'>account</h3>
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
        type="text" 
        name="name" 
        id="name" 
        placeholder="Name"
        className="w-96 p-3 text-lg bg-transparent border border-lightGray rounded-md shadow-md"
        value={inputs.name || ''}
        onChange={(e) => handleChange(e)}
        required
      />
      <input 
        type="email" 
        name="email" 
        id="email" 
        placeholder="E-mail"
        className="w-96 p-3 text-lg bg-transparent border border-lightGray rounded-md shadow-md"
        value={inputs.email || ''}
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
      <input 
        type="password" 
        name="confirmPassword" 
        id="confirmPassword" 
        placeholder="Confirm password"
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
        Create account
      </button>
      <div>
        <span className='text-lg'>Already have an account?</span> <span className='text-red text-lg hover:underline'><a href="/login">Sign in</a></span>
      </div>
    </form>
  )
}

export default Signup