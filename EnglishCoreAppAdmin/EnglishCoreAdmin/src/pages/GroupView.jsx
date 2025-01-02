import React, { useContext } from 'react';
import '../assets/styles/GroupView.css';

import GroupItem from '../components/Groups/GroupItem';
import Header from '../components/Shared/Header';
import Footer from '../components/Shared/Footer';
import { AuthContext } from '../context/AuthContext';
function GroupView() {

  const {name} = useContext(AuthContext);
  const getGreeting = () => {
    const now = new Date(); // Obtiene la fecha y hora actuales
    const hour = now.getHours(); // Obtiene solo la hora (0 - 23)
  
    if (hour >= 6 && hour < 12) {
      return "Buenos días";
    } else if (hour >= 12 && hour < 18) {
      return "Buenas tardes";
    } else {
      return "Buenas noches";
    }
  };

  const groupData = [
    { id: 1, level: 1, hours: "20:00 - 21:00", days: "L-M-J-V", teacher: "Daniel Lopez", students: 1 },
    { id: 2, level: 2, hours: "18:00 - 19:00", days: "M-J", teacher: "Maria Perez", students: 5 },
  ];

  return (
    <div className="home-page">
      <header className="header-eng">
        <Header />
      </header>

      <div className="greeting-div">{getGreeting()}, {name}</div>

      <div className="top-bar">
        <div>
          <p>Nivel:</p>
          <div className="custom-combobox">
            <select>
              <option value="1">Nivel-1</option>
              <option value="2">Nivel-2</option>
              <option value="3">Nivel-3</option>
              <option value="4">Nivel-4</option>
            </select>
          </div>
        </div>
        <div>
          <p>Horario:</p>
          <div className="custom-combobox">
            <select>
              <option value="1">20:00 - 21:00</option>
              <option value="2">18:00 - 19:00</option>
              <option value="3">17:00 - 18:00</option>
            </select>
          </div>
        </div>
        <div>
          <p>Días:</p>
          <div className="custom-combobox">
            <select>
              <option value="1">Lunes</option>
              <option value="2">Martes</option>
              <option value="3">Miércoles</option>
              <option value="4">Jueves</option>
            </select>
          </div>
        </div>
        <button>Crear Grupo</button>
      </div>

      <div className="content">
        {groupData.map((group) => (
          <GroupItem
            key={group.id} // Clave única
            Level={group.level}
            Hours={group.hours}
            Days={group.days}
            Teacher={group.teacher}
            Students={group.students}
          />
        ))}
      </div>

      <footer className="footer-eng">
        <Footer />
      </footer>
    </div>
  );
}

export default GroupView;
