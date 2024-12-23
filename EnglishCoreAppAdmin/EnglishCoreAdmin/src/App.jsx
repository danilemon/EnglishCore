import './App.css'
import { BrowserRouter as Router, Route,Routes} from 'react-router-dom';
import Login from './pages/Login';
import Home from './pages/Home';
import NotFound from './pages/NotFound';
function App() {


  return (
    <Router>
      <Routes>
      <Route path="/" element={<Login/>} />
      <Route path='/home' element={<Home/>}/>
      <Route path='*' element={<NotFound/>}/> 
      </Routes>
    </Router>
  )
}

export default App
