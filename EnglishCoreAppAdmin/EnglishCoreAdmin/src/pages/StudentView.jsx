import React, { useEffect, useState } from 'react'
import '../assets/styles/ManageStudents.css';
import '../assets/styles/StudentView.css';
import Swal from 'sweetalert2';
import Header from '../components/Shared/Header'
import Footer from '../components/Shared/Footer'
import { collection, getDocs, query, where, getDoc, doc } from 'firebase/firestore';
import db from '../services/firebaseConfig';
import { useLocation, useNavigate } from 'react-router-dom';
import ScoresItem from '../components/Students/ScoresItem';

function StudentView () {
    const location = useLocation();
    const student = location.state?.student; // Recuperar los datos pasados
    const navigate = useNavigate();
    const [scores, setScores] = useState([]); // Estado para las calificaciones
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(true);
    const [emptyS, setIsEmptyS] = useState(false);
    const [studentArray, setStudentArray] = useState([]);

    useEffect(() => {
      const loadScores = async () => {
          if (!student) return;

          setLoading(true);
          try {
              // Referencia a la subcolección
              const scoreCollectionRef = collection(db, "users", student.id, "scores");
              const scoresSnapshot = await getDocs(scoreCollectionRef);

              if (scoresSnapshot.empty) {
                  // Subcolección vacía o no existe
                  setScores([]); // Configura como un arreglo vacío
              } else {
                  // Mapea los datos si hay documentos
                  const scoresData = scoresSnapshot.docs.map((doc) => ({
                      id: doc.id,
                      ...doc.data(),
                  }));
                  setScores(scoresData);
              }
          } catch (err) {
              console.error("Error al cargar las calificaciones:", err);
              setError("No se pudieron cargar las calificaciones del estudiante.");
          } finally {
              setLoading(false);
          }
      };

      loadScores();
  }, [student]);

    const goScores = (score) => {
      const scoreData = {
        

      };

      navigate(`/students/${student.id}/${score.id}`, { state: { score: scoreData } }); // Pasar solo los datos

    }

 

  const removeStudent = () => {
    Swal.fire({
      title: "Estás seguro de que quieres dar de baja a "+ student.Name + " " + student.LastName + "?",
      text: "No podrás revertir el cambio",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
      cancelButtonText: "No, cancelar",
      confirmButtonText: "Sí, dar de baja"
    }).then((result) => {
      if (result.isConfirmed) {
        Swal.fire({
          title: "Hecho!",
          text: "El alumno ha sido dado de baja.",
          icon: "success"
        });
      }
    });
  }

  const restrictAcces = () => {
    Swal.fire({
      title: "Estás seguro de que quieres restringir el acceso a "+ student.Name + " " + student.LastName + "?",
      text: "Puedes volver a otorgarle acceso cuando lo necesites",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
      cancelButtonText: "No, cancelar",
      confirmButtonText: "Sí, restringir el acceso"
    }).then((result) => {
      if (result.isConfirmed) {
        Swal.fire({
          title: "Hecho!",
          text: "El alumno ha sido restringido del sistema.",
          icon: "success"
        });
      }
    });



  }


  if (!student) {
    return (
        <div className='stud-page'>
            <header className="header-eng">
                <Header />
            </header>
            <div className="content">
                <h1>Error</h1>
                <p>No se encontró información del estudiante.</p>
                <button onClick={() => navigate('/students')}>Volver a la lista de estudiantes</button>
            </div>
            <footer className="footer-eng">
                <Footer />
            </footer>
        </div>
    );
}

  return (
    <div className='stud-page'>
      <header className="header-eng">
        <Header />
      </header>
      
    <div className='student-header'>
        <div>
        <h2>{student.Name} {student.LastName}</h2>
        </div>

        <div className='top-student-buttons'>
            <button onClick={removeStudent}className='manage-st-btn'>Dar de baja</button>
            <button onClick={restrictAcces}className='manage-st-btn'>Restringir acceso</button>
        </div>
    </div>
      <div className="content">
      <h2>Detalles del Estudiante</h2>
      <hr></hr>
      <div className='std-content'>
      <div className='st-details'>
            <p>Nombre: {student.Name} </p>
            <p>Apellidos: {student.LastName}</p>
            <p>Nivel: {student.level}</p>
            <p>Registro: {student.id}</p>
            <p>Grupo: {student.groupName}</p>
            <p>Teléfono: {student.phone}</p>
            <p>Fecha de nacimiento: {student.birthday}</p>
            <p>Dirección:  {student.address}</p>
      </div>

      <div className='scores-div'>
                        <div className='scores-title'>
                            Calificaciones
                        </div>
                        {loading ? (
                            <p>Cargando calificaciones...</p>
                        ) : error ? (
                            <p className="error-message">{error}</p>
                        ) : scores.length === 0 ? (
                            <p>No hay calificaciones registradas.</p>
                        ) : (
                            scores.map(score => (
                                <ScoresItem   
                                key={score.id}
                                level={score.level}
                                lapse={score.lapse}
                                generalScore={score.generalscore}
                                onClick={() => goScores(score)} 
                                 />
                            ))
                        )}
                    </div>
      </div>
     
           
      </div>


      <footer className="footer-eng">
        <Footer />
      </footer>
    </div>
  )
}

export default StudentView