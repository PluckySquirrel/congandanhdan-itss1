import React, { useState } from 'react'
import { BsCopy } from 'react-icons/bs'
import Dialog from './Dialog'

const CopyButton = (props) => {
  const [showDialog, setShowDialog] = useState(false);

  return (
    <>
      <button 
        className='hover:text-darkGray'
        onClick={() => {
          navigator.clipboard.writeText(props.output)
          setShowDialog(true);
        }}
        disabled={props.disabled}
      >
        <BsCopy size='1.6rem' />
      </button>
      {showDialog && 
        <Dialog
          message='Copied to clipboard'
          showDialog={showDialog}
          setShowDialog={setShowDialog}
        />
      }
    </>
  )
}

export default CopyButton