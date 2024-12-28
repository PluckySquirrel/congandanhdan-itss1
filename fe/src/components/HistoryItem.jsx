import React from 'react'
import { BsFillStarFill, BsHandThumbsUp, BsHandThumbsUpFill, BsVolumeUpFill } from 'react-icons/bs';
import getAction from '../utils/getAction';
import getLanguage from '../utils/getLanguage';
import getDate from '../utils/getDate';
import getOutputLanguageTag from '../utils/getOutputLanguageTag';

const HistoryItem = (props) => {
  const {action, input, output, sourceLanguage, targetLanguage, timestamp} = props;

  const suaGauGau = (phrase, language='ja') => {
    const utterance = new SpeechSynthesisUtterance(phrase);
    utterance.lang = language;
    speechSynthesis.speak(utterance);
  }

  return (
    <div className='relative w-full p-6 flex flex-col gap-2 items-start rounded-md shadow-md'>
      <div className='w-full flex items-center justify-between'>
        <div className='flex items-center gap-2 text-red'>
          <BsFillStarFill />
          <h3 className='text-lg'>{getAction(action)}</h3>
          <p className='text-gray'>
            {action === 'TRANSLATION' && `${getLanguage(sourceLanguage)} → ${getLanguage(targetLanguage)}`}
          </p>
        </div>
        <p className='text-gray'>{getDate(timestamp)}</p>
      </div>
      <div className='flex items-start gap-2'>
        <button onClick={() => suaGauGau(input)}>
          <BsVolumeUpFill size='1.5rem' />
        </button>
        <h2 className='text-xl font-bold text-left'>{input}</h2>
      </div>
      <div className='flex items-start gap-2'>
        <button onClick={() => suaGauGau(output, getOutputLanguageTag(targetLanguage))}>
          <BsVolumeUpFill size='1.5rem' />
        </button>
        <h3 className='text-lg text-blue text-left'>{output}</h3>
      </div>
      <button 
        className='absolute bottom-6 right-6 text-blue hover:text-darkBlue'
        onClick={() => {}}
      >
        <BsHandThumbsUpFill size='1.6rem' />
      </button>
      
      {/* chua like thi dung cai nay 
      <button 
        className='absolute bottom-6 right-6 text-blue hover:text-darkBlue'
        onClick={() => {}}
      >
        <BsHandThumbsUp size='1.6rem' />
      </button> */}
    </div>
  )
}

export default HistoryItem