import React, { useEffect, useState } from 'react'
import { useCookies } from 'react-cookie';
import { BsPersonCircle } from 'react-icons/bs'
import NavbarMenu from './NavbarMenu';
import { use } from 'react';
import { jwtDecode } from 'jwt-decode';

const Navbar = (props) => {
  const [cookies, setCookie, removeCookie] = useCookies(['token']);
  const [showMenu, setShowMenu] = useState(false)

  const show = () => {
    setShowMenu(!showMenu);
  }

  const fetchUser = async () => {
    const decoded = jwtDecode(cookies.token);
    const response = await fetch(
      `http://localhost:8080/api/v1/users/${decoded.uuid}`,
      {
        method: "GET",
        headers: {
          Authorization: `Bearer ${cookies.token}`,
        },
      }
    );
    const data = await response.json();
    props.setAvatar(data.avatarUrl);
  };

  useEffect(() => {
    fetchUser();
  }, [])

  return (
    <nav className='relative w-full min-h-20 flex justify-between items-center px-8 border-b border-lightGray bg-white shadow-md'>
      <a href='/'>
        <img src="/logo.svg" className='h-10' />
      </a>
      <div className='h-full gap-8 flex items-center'>
        <a className='w-32 h-full flex items-center justify-center text-lg hover:cursor-pointer hover:bg-lightGray' href='/'>ホーム</a>
        {
          cookies.token ?
          <>
            <div className='text-darkGray hover:cursor-pointer' onClick={show}>
              {props.avatar ? <img
                src={props.avatar}
                alt="Avatar"
                className="w-[3rem] h-[3rem] object-cover rounded-full overflow-hidden"
              /> : <BsPersonCircle size='3rem' />}
            </div>

            {showMenu && <NavbarMenu show={show} />}
          </>
          :
          <>
            <a className='w-32 h-full flex items-center justify-center text-lg hover:cursor-pointer hover:bg-lightGray' href='/login'>ログイン</a>
            <a className='w-32 h-full flex items-center justify-center text-lg hover:cursor-pointer hover:bg-lightGray' href='/signup'>登録</a>
          </>
        }
        
      </div>
    </nav>
  )
}

export default Navbar