import React from 'react'

const Navbar = () => {
  return (
    <nav className='w-full h-16 flex justify-between items-center px-8'>
      <div>
        <img src="/logo.svg" className='h-8' />
      </div>
      <div className='h-full gap-8 flex'>
        <a className='w-32 h-full flex items-center justify-center text-lg hover:cursor-pointer hover:bg-gray' href='#'>Home</a>
        <a className='w-32 h-full flex items-center justify-center text-lg hover:cursor-pointer hover:bg-gray' href='#'>Explore</a>
        <a className='w-32 h-full flex items-center justify-center text-lg hover:cursor-pointer hover:bg-gray' href='#'>About</a>
      </div>
    </nav>
  )
}

export default Navbar