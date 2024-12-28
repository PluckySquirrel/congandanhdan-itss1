import React, { useEffect } from 'react'

const Dialog = (props) => {
  useEffect(() => {
    const timeId = setTimeout(() => {
      props.setShowDialog(false);
    }, 3000);

    return () => {
      clearTimeout(timeId);
    }
  }, []);

  if (!props.showDialog) {
    return null;
  }

  return (
    <div className='fixed px-4 py-2 left-1/2 bottom-8 bg-white text-black text-lg border border-lightGray rounded-md shadow-md z-50 -translate-x-1/2'>
      {props.message}
    </div>
  )
}

export default Dialog