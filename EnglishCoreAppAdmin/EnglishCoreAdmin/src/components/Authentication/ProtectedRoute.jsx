import React, { useContext } from 'react';
import { Navigate } from 'react-router-dom';
import { AuthContext } from '../../context/AuthContext';

const ProtectedRoute = ({ children }) => {
  const { user } = useContext(AuthContext);

  if (!user) {
    return <Navigate to="/" />; // Return to login page if there's no session
  }

  return children; // Render the children components on Protected Route if there is a session
};

export default ProtectedRoute;
