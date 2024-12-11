import React from 'react'
import { useNavigate } from 'react-router-dom';
import { useCookies } from 'react-cookie';

const Navbar = () => {
  const navigate = useNavigate();
  const [cookies, setCookie, removeCookie] = useCookies(['token']);

  const handleLogout = () => {
    removeCookie('token');
    navigate('/login');
  }

  return (
    <nav className='w-full h-16 flex justify-between items-center px-8'>
      <div>
        <img src="/logo.svg" className='h-8' />
      </div>
      <div className='h-full gap-8 flex'>
        <a className='w-32 h-full flex items-center justify-center text-lg hover:cursor-pointer hover:bg-gray' href='/'>Home</a>
        {
          cookies.token ?
          <>
            <a className='w-32 h-full flex items-center justify-center text-lg hover:cursor-pointer hover:bg-gray' href='/history'>History</a>
            <button className='w-32 h-full flex items-center justify-center text-lg hover:cursor-pointer hover:bg-gray' onClick={handleLogout}>Logout</button>
          </>
          :
          <>
            <a className='w-32 h-full flex items-center justify-center text-lg hover:cursor-pointer hover:bg-gray' href='/login'>Login</a>
            <a className='w-32 h-full flex items-center justify-center text-lg hover:cursor-pointer hover:bg-gray' href='/signup'>Sign up</a>
          </>
        }
        
      </div>
    </nav>
  )
}

export default Navbar