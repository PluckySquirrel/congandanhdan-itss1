import React, { useEffect, useState } from 'react'
import { BsSearch } from 'react-icons/bs';
import HistoryItem from '../components/HistoryItem';
import { useCookies } from 'react-cookie';
import { useNavigate } from 'react-router-dom';

const Saved = () => {
  const navigate = useNavigate();
  const [input, setInput] = useState('');
  const [history, setHistory] = useState([]);
  const [cookies, setCookie, removeCookie] = useCookies(['token']);

  const fetchHistory = async () => {
    const response = await fetch(`http://localhost:8080/api/v1/saved?page=0&size=1000`, {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${cookies.token}`
      }
    });
    const data = await response.json();
    setHistory(data.content);
  }

  useEffect(() => {
    if (cookies.token) {
      fetchHistory();
    }
    else {
      navigate('/login');
    }
  }, [])

  const handleChange = (event) => {
    setInput(event.target.value);
  }

  const historyItems = history.map((item) =>
    <HistoryItem
      key={item.uuid}
      action={item.action}
      input={item.input}
      output={item.output}
      sourceLanguage={item.sourceLanguage}
      targetLanguage={item.targetLanguage}
      timestamp={item.timestamp}
    />
  );

  return (
    <div className='h-full py-4 flex items-start justify-center'>
      <div className='w-1/2 flex flex-col items-center justify-center gap-8'>
        <h3 className='text-3xl leading-7'>Saved history</h3>
        <div className='w-2/3 flex items-center justify-center gap-2'>
          <div className='relative w-full flex items-center pl-2 border border-lightGray rounded-md shadow-md'>
            <div className='text-gray'>
              <BsSearch />
            </div>
            <input 
              type="search"
              name="search" 
              id="search" 
              placeholder="Search history by keywords"
              className="w-full p-2 bg-transparent outline-none"
              value={input}
              onChange={(e) => handleChange(e)}
            />
          </div>
          
          <button 
            className='px-4 h-10 flex items-center px-4 bg-blue text-white shadow-md rounded-md hover:bg-darkBlue disabled:bg-disabled'
          >
            Search
          </button>
        </div>
        <div className='w-full flex flex-col items-center gap-4'>
          {historyItems}
        </div>
      </div>
    </div>
  )
}

export default Saved