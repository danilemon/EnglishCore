    import React from 'react'
    import '../../assets/styles/Header.css';
    import logo from '../../assets/logos/logo.png'; 
    import { useNavigate } from 'react-router-dom';
    function Header(props) {
        const navigate = useNavigate();

        const goToDashboard = () => {
            navigate('/home');
        };

        const goToStudents = () => {
            navigate('/students');
        };

        const goToExercises = () => {
            navigate('/exercises');
        }
    return (
        <div className='hdr-cont'>
        <img onClick={goToDashboard} src={logo} alt="Logo" className="eng-logo" />
        <p onClick={goToDashboard} className = 'hdr-links' >Administrar grupos</p>
        <p onClick={goToStudents} className = 'hdr-links'>Administrar estudiantes</p>
        <p onClick={goToExercises} className = 'hdr-links' >Subir ejercicios</p>
        <p className = 'hdr-links-logout' >Cerrar sesión</p>

        </div>
    )
    }

    export default Header