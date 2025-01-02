import './App.css'
import { BrowserRouter as Router, Route,Routes} from 'react-router-dom';
import Login from './pages/Login';
import NotFound from './pages/NotFound';
import ManageStudents from './pages/ManageStudents';
import GroupView from './pages/GroupView';
import ManageExercises from './pages/ManageExercises';
import ProtectedRoute from './components/Authentication/ProtectedRoute';
import { AuthProvider } from './context/AuthContext';
import StudentView from './pages/StudentView';
function App() {


  return (
    <AuthProvider> {/* Aquí envuelves todo */}

    <Router>
      <Routes>
      <Route path="/" element={<Login/>} /> {/* The login page, which is not protected with a safe session as the other pages*/}
        <Route path="/home" element={<ProtectedRoute><GroupView /></ProtectedRoute>} />
        <Route path="/students" element={<ProtectedRoute><ManageStudents /></ProtectedRoute>} />
        <Route path="/students/:id" element={<ProtectedRoute><StudentView/></ProtectedRoute>}/>
        <Route path="/exercises" element={<ProtectedRoute><ManageExercises /></ProtectedRoute>} />
        <Route path="*" element={<NotFound />} />
     
      </Routes>
    </Router>
    </AuthProvider>

  )
}

export default App
