import './App.css'
import { BrowserRouter as Router, Route,Routes} from 'react-router-dom';
import Login from './pages/Login';
import NotFound from './pages/NotFound';
import ManageStudents from './pages/ManageStudents';
import GroupView from './pages/GroupView';
import ManageExercises from './pages/ManageExercises';
function App() {


  return (
    <Router>
      <Routes>
      <Route path="/" element={<Login/>} />
      <Route path='/home' element={<GroupView/>}/>
      <Route path='/students' element={<ManageStudents/>}/>
      <Route path='/exercises' element={<ManageExercises/>}/>
      <Route path='*' element={<NotFound/>}/> 
      </Routes>
    </Router>
  )
}

export default App
