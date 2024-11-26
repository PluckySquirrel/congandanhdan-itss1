import { BsMicFill } from 'react-icons/bs'
import React, { useState } from 'react'

const Home = () => {
  const [input, setInput] = useState('');
  const [output, setOutput] = useState('');
  const [language, setLanguage] = useState('');

  const handleInputChange = (e) => {
    setInput(e.target.value);
  }

  const handleOutputChange = (e) => {
    setOutput(e.target.value);
  }

  const handleLanguageChange = (e) => {
    setLanguage(e.target.value);
  }

  return (
    <div className='flex flex-col items-center py-4'>
      <h1 className='text-3xl'>Input</h1>
      <textarea 
        name="input"
        id="input"
        className='w-1/2 h-44 p-4 my-4 border border-gray shadow-md rounded-md'
        placeholder='Enter something...'
        value={input}
        onChange={(e) => handleInputChange(e)}
      />
      <button className='px-4 py-2 flex gap-2 border border-gray shadow-md rounded-md hover:bg-gray'>
        <div className='w-6 h-6 flex items-center justify-center rounded-full bg-red text-white'>
          <BsMicFill />
        </div>
        Voice input
      </button>
      <div className='w-1/2 my-4 flex items-center justify-center gap-4'>
        <button className='px-4 py-2 flex gap-2 bg-blue text-white shadow-md rounded-md hover:bg-darkBlue'>
          Easy Japanese mode
        </button>
        <button className='px-4 py-2 flex gap-2 bg-blue text-white shadow-md rounded-md hover:bg-darkBlue'>
          Express intent
        </button>
        or
        <select 
          className='px-4 py-2 flex gap-2 border border-gray shadow-md rounded-md'
          onChange={(e) => handleLanguageChange(e)}
        >
          <option value='jp'>
            日本語
          </option>
          <option value='vi'>
            Tiếng Việt
          </option>
          <option value='en'>
            English
          </option>
        </select>
        <button className='px-4 py-2 flex gap-2 bg-blue text-white shadow-md rounded-md hover:bg-darkBlue'>
          Translate
        </button>
      </div>
      <h1 className='text-3xl'>Output</h1>
      <textarea 
        name="output"
        id="output"
        className='w-1/2 h-44 p-4 my-4 border border-gray shadow-md rounded-md'
        placeholder='The output intent expression or translation is displayed here...'
        value={output}
        onChange={(e) => handleOutputChange(e)}
      />
    </div>
  )
}

export default Home