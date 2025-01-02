import React, { createContext, useState, useEffect } from 'react';

export const AuthContext = createContext(); //Se crea el contexto de autenticación y se exporta

// Crear el provider del contexto
export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [name, setName] = useState('');
  useEffect(() => {
    const storedUser = JSON.parse(localStorage.getItem('user')); //Read data from the local storage when opening the app
    if (storedUser) {
      setUser(storedUser);
    }
  }, []);

  const login = (userData) => {
    setUser(userData); //userData is the doc of the user on the db
    setName(userData.Name);
    console.log("User Data:", userData);

    localStorage.setItem('user', JSON.stringify(userData)); // Save user on LocalStorage
    console.log("LocalStorage (after set):", localStorage.getItem('user'));

  };

  

  const logout = () => { //Close session
    setUser(null);
    localStorage.removeItem('user'); //Delete user from LocalStorage
  };

  return (
    <AuthContext.Provider value={{ user, login, logout, name}}>
      {children}
    </AuthContext.Provider>
  );
};
