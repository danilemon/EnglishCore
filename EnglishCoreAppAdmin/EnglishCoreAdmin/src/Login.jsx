import React from 'react';
import './Login.css';
import logo from './assets/logo.png'; 

function Login() {
  return (
    <>
    <img src={logo} alt="Logo" className="login-logo" />
    <div className="login-container">
      <h2>Sistema English Core</h2>
      <form>
        <div className="form-group">
          <label htmlFor="username">Usuario:</label>
          <input type="text" id="username" name="username" placeholder="Ingresa tu usuario" />
        </div>
        <div className="form-group">
          <label htmlFor="password">Contraseña:</label>
          <input type="password" id="password" name="password" placeholder="Ingresa tu contraseña" />
        </div>
        <button type="submit">Ingresar</button>
      </form>
    </div>
    </>
  );
}

export default Login;
