import React, { useEffect, useRef, useState } from 'react'
import { BsPencilFill } from 'react-icons/bs';
import Dialog from './Dialog';

const EditButton = (props) => {
  const [show, setShow] = useState(false);
  const [showDialog, setShowDialog] = useState(false);
  const [input, setInput] = useState('');
  const ref = useRef(null);

  useEffect(() => {
    document.addEventListener('click', handleClickOutside, true);
    return () => {
        document.removeEventListener('click', handleClickOutside, true);
    };
  }, []);

  const handleClickOutside = (e) => {
    if (ref.current && !ref.current.contains(e.target)) {
      setShow(false);
    }
  };

  const handleInputChange = (e) => {
    setInput(e.target.value);
  }

  const handleSubmit = (e) => {
    e.preventDefault();
    props.setOutput(input);
    setShow(false);
    setShowDialog(true);
  }

  return (
    <>   
      <button 
        className='hover:text-darkGray'
        onClick={() => setShow(true)}
      >
        <BsPencilFill size='1.6rem' />
      </button>
      {
        show &&
        <div className='fixed top-0 left-0 w-screen h-screen flex justify-center items-center bg-black/60 z-50'>
          <div ref={ref} className='fixed w-1/2 p-4 bg-white rounded-lg shadow-md'>
            <h1 className='font-bold text-black text-xl mb-4'>Edit output</h1>
            <form onSubmit={handleSubmit} className='w-full h-full'>
              <div className='relative w-full h-44 my-4 border border-lightGray shadow-md rounded-md '>
                <textarea 
                  name="input"
                  id="input"
                  className='w-full h-32 p-4 text-black outline-none resize-none'
                  placeholder='Edit output'
                  defaultValue={props.output}
                  onChange={(e) => handleInputChange(e)}
                />
              </div>
              <div className='w-full h-12 flex justify-end gap-2'>
                <button type="submit" className='w-32 h-full p-2 bg-blue hover:bg-darkBlue duration-150 ease-in-out text-white rounded-md shadow-md'>
                  Save
                </button>
                <button type="button" onClick={() => setShow(!show)} className='w-32 h-full p-2 border border-blue text-blue rounded-md shadow-md hover:bg-lightGray duration-150 ease-in-out'>
                  Cancel
                </button>
              </div>
            </form>
          </div>
        </div>
      }
      {showDialog && 
        <Dialog
          message='Saved output'
          showDialog={showDialog}
          setShowDialog={setShowDialog}
        />}
    </>
  )
}

export default EditButton