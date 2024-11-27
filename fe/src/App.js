import { BrowserRouter, Routes, Route } from 'react-router-dom';
import './App.css';
import Home from './pages/Home';
import Signup from './pages/Signup';
import Login from './pages/Login';
import Navbar from './components/Navbar';

function App() {
  return (
    <div className="App">
      <Navbar/>
      <BrowserRouter>
        <Routes>  
          <Route index element={<Home />}/>
          <Route path="/signup" element={<Signup />} />
          <Route path = "/login" element= {<Login/>}/>
          {/* <Route path = "/forgotpassword" element= {<Forgot/>}/> */}
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
