import React from 'react'
import '../../assets/styles/Header.css';
import logo from '../../assets/logos/logo.png'; 
import { useNavigate } from 'react-router-dom';
function Header(props) {
    const navigate = useNavigate();
  return (
    <div className='hdr-cont'>
    <img onClick={navigate('/home')} src={logo} alt="Logo" className="eng-logo" />
    <p className = 'hdr-links' >Administrar grupos</p>
    <p className = 'hdr-links'>Administrar estudiantes</p>
    <p className = 'hdr-links' >Subir ejercicios</p>
    <p className = 'hdr-links-logout' >Cerrar sesión</p>

    </div>
  )
}

export default Header