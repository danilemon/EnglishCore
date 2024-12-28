import React from 'react'
import '../assets/styles/ManageStudents.css';
import Header from '../components/Shared/Header'
import Footer from '../components/Shared/Footer'
import GroupItem from '../components/Students/StudentItem';

function ManageStudents () {
  return (
    <div className='stud-page'>
      <header className="header-eng">
        <Header />
      </header>
      <div className="top-bar">
        <div>
          <p>Ordenar por:</p>
          <div className="custom-combobox">
            <select>
              <option value="1">Nivel-1</option>
              <option value="2">Nivel-2</option>
              <option value="3">Nivel-3</option>
              <option value="4">Nivel-4</option>
            </select>
          </div>
          
      </div>
      <button>Agregar Alumno</button>
      </div>

      <div className="content">
        <GroupItem />
      </div>

      <footer className="footer-eng">
        <Footer />
      </footer>
    </div>
  )
}

export default ManageStudents