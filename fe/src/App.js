import { BrowserRouter, Routes, Route } from 'react-router-dom';
import './App.css';
import Home from './pages/Home';
import Signup from './pages/Signup';
import Login from './pages/Login';
import Navbar from './components/Navbar';
import ForgotPassword from './pages/ForgotPassword';
import { AuthProvider } from './AuthContext';

function App() {
  return (
    <AuthProvider>
      <div className="App">
        <BrowserRouter>
          <Navbar/>
          <Routes>  
            <Route index element={<Home />}/>
            <Route path="/signup" element={<Signup />} />
            <Route path = "/login" element= {<Login/>}/>
            <Route path = "/forgot-password" element= {<ForgotPassword/>}/>
          </Routes>
        </BrowserRouter>
      </div>
    </AuthProvider>
  );
}

export default App;
