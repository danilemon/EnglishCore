import React from 'react'
import Header from '../components/Shared/Header'
import Footer from '../components/Shared/Footer'
import '../assets/styles/ManageStudents.css';

function ManageStudents () {
  return (
    <div className='stud-page'>
      <Header/>
      Administrar estudiantes
      <Footer/>
    </div>
  )
}

export default ManageStudents