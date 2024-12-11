import React, { useContext } from 'react'
import { AuthContext } from '../AuthContext';
import { useNavigate } from 'react-router-dom';

const Navbar = () => {
  const navigate = useNavigate();
  const { auth, logout } = useContext(AuthContext);

  const handleLogout = () => {
    document.cookie = '';
    logout();
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
          auth ?
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