import React, { useState } from 'react'
import { useCookies } from 'react-cookie';
import { BsPersonCircle } from 'react-icons/bs'
import NavbarMenu from './NavbarMenu';

const Navbar = () => {
  const [cookies, setCookie, removeCookie] = useCookies(['token']);
  const [showMenu, setShowMenu] = useState(false)

  const show = () => {
    setShowMenu(!showMenu);
  }

  return (
    <nav className='relative w-full min-h-20 flex justify-between items-center px-8 border-b border-lightGray'>
      <a href='/'>
        <img src="/logo.svg" className='h-10' />
      </a>
      <div className='h-full gap-8 flex items-center'>
        <a className='w-32 h-full flex items-center justify-center text-lg hover:cursor-pointer hover:bg-lightGray' href='/'>Home</a>
        {
          cookies.token ?
          <>
            <div className='text-darkGray hover:cursor-pointer' onClick={show}>
              <BsPersonCircle size='3rem' />
            </div>

            {showMenu && <NavbarMenu show={show} />}
          </>
          :
          <>
            <a className='w-32 h-full flex items-center justify-center text-lg hover:cursor-pointer hover:bg-lightGray' href='/login'>Login</a>
            <a className='w-32 h-full flex items-center justify-center text-lg hover:cursor-pointer hover:bg-lightGray' href='/signup'>Sign up</a>
          </>
        }
        
      </div>
    </nav>
  )
}

export default Navbar