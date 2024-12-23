import './App.css'
import { BrowserRouter as Router, Route,Routes} from 'react-router-dom';
//import Login from './pages/Login';
import GroupView from './pages/GroupView';
function App() {


  return (
    <Router>
      <Routes>
      <Route path="/" element={<GroupView/>} />
      
      {/* <Route path='*' element=}{<NotFound/>}/> */}
      </Routes>
    </Router>
  )
}

export default App
