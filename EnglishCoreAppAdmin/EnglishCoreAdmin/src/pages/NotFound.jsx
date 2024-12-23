import React from 'react'
import '../assets/styles/NotFound.css';
import Header from '../components/Shared/Header';
import Footer from '../components/Shared/Footer';
import { useNavigate } from 'react-router-dom';
function NotFound ()  {
    const navigate = useNavigate();
  return (
    <div className='notfound-page'>
       <h2>Oops, la página que buscas no existe  {':('} </h2>
       <button onClick={navigate('/home')}className='gohome-btn'>Regresar al inicio</button>
    </div>
  )
}

export default NotFound