import { BrowserRouter, Routes, Route } from 'react-router-dom';
import './App.css';
import Home from './pages/Home';
import Signup from './pages/Signup';
import Login from './pages/Login';
import Navbar from './components/Navbar';
import ForgotPassword from './pages/ForgotPassword';
import History from './pages/History';
import Settings from './pages/Settings';

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Navbar/>
        <Routes>  
          <Route index element={<Home />}/>
          <Route path="/signup" element={<Signup />} />
          <Route path = "/login" element= {<Login />}/>
          <Route path = "/forgot-password" element= {<ForgotPassword />}/>
          <Route path = "/history" element= {<History />}/>
          <Route path = "/settings" element= {<Settings />}/>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
