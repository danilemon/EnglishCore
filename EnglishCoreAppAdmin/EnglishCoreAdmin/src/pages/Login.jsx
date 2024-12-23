import React, { useState } from 'react';
import '../assets/styles/Login.css';
import logo from '../assets/logos/logo.png'; 
import Swal from 'sweetalert2';
import { ENDPOINTS } from '../api/apiConfig';
function Login() {
const [userN, setUserN] = useState('');
const [passN, setPassN] = useState('');
const [error, setError] = useState('');
const [success, setSuccess] = useState(false);

const handleLogin = async (e) => {
  e.preventDefault();

  try {
    const response = await fetch(ENDPOINTS.LOGIN, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        username: userN,
        password: passN,
      }),
    });

    if (!response.ok) {
      throw new Error('Usuario o contraseña incorrectos');
    }

    const data = await response.json();
    setSuccess(true);
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
  } catch (err) {
    setError(err.message);
    setSuccess(false);
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
        {success && <p style={{ color: 'green' }}>¡Inicio de sesión exitoso!</p>}
      </form>
    </div>
    </div>
  );
}

export default Login;
