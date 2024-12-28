import { BrowserRouter, Routes, Route } from 'react-router-dom';
import './App.css';
import Home from './pages/Home';
import Signup from './pages/Signup';
import Login from './pages/Login';
import Navbar from './components/Navbar';
import ForgotPassword from './pages/ForgotPassword';
import History from './pages/History';
import Settings from './pages/Settings';
import ResetPassword from './pages/ResetPassword';
import Saved from './pages/Saved';
import { useState } from 'react';

function App() {
  const [avatar, setAvatar] = useState(null);
  return (
    <div className="App">
      <BrowserRouter>
        <Navbar avatar={avatar} setAvatar={setAvatar}/>
        <Routes>  
          <Route index element={<Home />}/>
          <Route path="/signup" element={<Signup />} />
          <Route path = "/login" element= {<Login setAvatar={setAvatar} />}/>
          <Route path = "/forgot-password" element= {<ForgotPassword />}/>
          <Route path = "/reset-password/:token" element= {<ResetPassword />}/>
          <Route path = "/history" element= {<History />}/>
          <Route path = "/saved" element= {<Saved />}/>
          <Route path = "/settings" element= {<Settings setAvatar={setAvatar} />}/>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
