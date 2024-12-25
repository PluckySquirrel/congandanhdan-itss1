import React, { useEffect, useState } from 'react'
import { BsSearch } from 'react-icons/bs';
import HistoryItem from '../components/HistoryItem';
import { useCookies } from 'react-cookie';
import { useNavigate } from 'react-router-dom';

const Settings = () => {
  const navigate = useNavigate();
  const [input, setInput] = useState('');
  const [user, setUser] = useState({});
  const [loading, setLoading] = useState(false);
  const [cookies, setCookie, removeCookie] = useCookies(['token']);

  const fetchUser = async () => {
    const response = await fetch(`http://localhost:8080/api/v1/users/${cookies.token}`, {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${cookies.token}`
      }
    });
    const data = await response.json();
    console.log(data);
  }

  useEffect(() => {
    if (cookies.token) {
      fetchUser();
    }
    else {
      navigate('/login');
    }
  }, [])

  const handleChange = (event) => {
    setInput(event.target.value);
  }

  const submit = async (e) => {
    e.preventDefault();
    setLoading(true);
    const response = {}
    setLoading(false);

    if (!response.ok) {
      return;
    }
  }

  return (
    <div className='h-full py-4 flex items-start justify-center'>
      <form className='w-1/2 flex flex-col items-center justify-center gap-8' onSubmit={submit}>
        <h3 className='text-3xl leading-7'>Settings</h3>
        <div className='w-2/3 flex items-center gap-2'>
          <div className='w-36 text-start text-lg'>
            Username
          </div>
          <input 
            type="text" 
            name="username" 
            id="username" 
            placeholder="Username"
            className="w-96 p-3 text-lg bg-transparent border border-lightGray rounded-md shadow-md"
            value={user.username || ''}
            onChange={(e) => handleChange(e)}
            required
          />
        </div>
        <div className='w-2/3 flex items-center gap-2'>
          <div className='w-36 text-start text-lg'>
            Name
          </div>
          <input 
            type="text" 
            name="name" 
            id="name" 
            placeholder="Name"
            className="w-96 p-3 text-lg bg-transparent border border-lightGray rounded-md shadow-md"
            value={user.name || ''}
            onChange={(e) => handleChange(e)}
            required
          />
        </div>
        <div className='w-2/3 flex items-center gap-2'>
          <div className='w-36 text-start text-lg'>
            E-mail
          </div>
          <input 
            type="email" 
            name="email" 
            id="email" 
            placeholder="E-mail"
            className="w-96 p-3 text-lg bg-transparent border border-lightGray rounded-md shadow-md"
            value={user.email || ''}
            onChange={(e) => handleChange(e)}
            required
          />
        </div>
        <button 
          className='px-6 h-12 flex items-center bg-blue text-white text-lg shadow-md rounded-md hover:bg-darkBlue disabled:bg-disabled'
          type='submit'
          disabled={loading}
        >
          Save changes
        </button>
      </form>
    </div>
  )
}

export default Settings