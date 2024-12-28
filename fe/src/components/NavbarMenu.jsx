import React, { useEffect, useRef, useState } from 'react'
import { useCookies } from 'react-cookie';
import { useNavigate } from 'react-router-dom';

const NavbarMenu = (props) => {
  const navigate = useNavigate();
  const [cookies, setCookie, removeCookie] = useCookies(['token']);
  const ref = useRef(null);

  useEffect(() => {
    document.addEventListener('click', handleClickOutside, true);
    return () => {
        document.removeEventListener('click', handleClickOutside, true);
    };
  }, []);

  const handleClickOutside = (event) => {
    if (ref.current && !ref.current.contains(event.target)) {
      props.show();
    }
  };

  const handleLogout = () => {
    removeCookie('token');
    navigate('/login');
  };

  return (
    <div ref={ref} className='z-50 absolute top-20 right-8 w-48 flex flex-col bg-white rounded-sm overflow-hidden shadow-md'>
      <a 
        className='w-full px-4 text-start py-2 h-full text-lg hover:cursor-pointer hover:bg-lightGray' 
        href='/settings'
      >
        Settings
      </a>
      <a 
        className='w-full px-4 text-start py-2 h-full text-lg hover:cursor-pointer hover:bg-lightGray' 
        href='/history'
      >
        History
      </a>
      <a 
        className='w-full px-4 text-start py-2 h-full text-lg hover:cursor-pointer hover:bg-lightGray' 
        href='/saved'
      >
        Saved history
      </a>
      <button 
        className='w-full px-4 text-start py-2 h-full text-lg hover:cursor-pointer hover:bg-lightGray' 
        onClick={handleLogout}
      >
        Logout
      </button>
    </div>
  )
}

export default NavbarMenu