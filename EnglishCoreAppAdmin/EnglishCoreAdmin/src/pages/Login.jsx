import React, { useState } from 'react';
import '../assets/styles/Login.css';
import logo from '../assets/logos/logo.png'; 
import Swal from 'sweetalert2';
import { useNavigate } from 'react-router-dom';
import db from '../services/firebaseConfig';
import { collection, getDocs, query, where } from 'firebase/firestore';
function Login() {
const [userN, setUserN] = useState('');
const [passN, setPassN] = useState('');
const [error, setError] = useState('');
const navigate = useNavigate();

const handleLogin = async (e) => {
  e.preventDefault();

  try {
    const userRef = collection(db, "users");

    // Crear la consulta para buscar el usuario con el username y password proporcionados
    const q = query(
      userRef,
      where("username", "==", userN),
      where("password", "==", passN),
      where("isAdmin", "==", true)
    );

    // Ejecutar la consulta
    const querySnapshot = await getDocs(q);

    if (!querySnapshot.empty) {
      // Si se encuentra el usuario
      const Toast = Swal.mixin({
        toast: true,
        position: "top-end",
        showConfirmButton: false,
        timer: 3000,
        timerProgressBar: true,
        didOpen: (toast) => {
          toast.onmouseenter = Swal.stopTimer;
          toast.onmouseleave = Swal.resumeTimer;
        }
      });

      Toast.fire({
        icon: "success",
        title: "Signed in successfully"
      });

      navigate('/home');
    } else {
      // Si no se encuentra un usuario con esas credenciales
      Swal.fire({
        icon: "error",
        title: "Error de autenticación",
        text: "Usuario o contraseña incorrectos.",
      });
      setError("Usuario o contraseña incorrectos.");
    }
  } catch (err) {
    console.error("Error al iniciar sesión:", err);
    setError("Hubo un problema al intentar iniciar sesión.");
  }
};


  return ( 
    <div className='log-page'>
    <img src={logo} alt="Logo" className="login-logo" />
    <div className="login-container">
      <h2>Sistema English Core</h2>
      <form onSubmit={handleLogin}>
        <div className="form-group">
          <label htmlFor="username">Usuario:</label>
          <input value={userN} onChange={(e) => setUserN(e.target.value)} type="text" id="username" name="username" placeholder="Ingresa tu usuario" />
        </div>
        <div className="form-group">
          <label htmlFor="password">Contraseña:</label>
          <input value={passN} onChange={(e) => setPassN(e.target.value)}type="password" id="password" name="password" placeholder="Ingresa tu contraseña" />
        </div>
        <button className="log-btn" type="submit">Ingresar</button>
        {error && <p style={{ color: 'red' }}>{error}</p>}
      </form>
    </div>
    </div>
  );
}

export default Login;
