  import React, { useEffect, useState } from 'react'
  import '../assets/styles/ManageStudents.css';
  import Header from '../components/Shared/Header'
  import Footer from '../components/Shared/Footer'
  import { collection, getDocs, query, where, getDoc, doc } from 'firebase/firestore';
  import StudentItem from '../components/Students/StudentItem';
  import db from '../services/firebaseConfig';
  import { useNavigate } from 'react-router-dom';
  import GenModal from '../components/Shared/GenModal';
  function ManageStudents () {

    const [loadingS, setIsLoadingS] = useState(true);
    const [emptyS, setIsEmptyS] = useState(false);
    const [error, setError] = useState('');
    const [studentArray, setStudentArray] = useState([]);
    const [modalStudentVisible, setIsModalStudentVisible] = useState(false);
    const navigate = useNavigate();


    const addStudent = () => 
      {

      }
    const goStudent = (student) => {
      const studentData = {
        id: student.id,
        Name: student.Name,
        LastName: student.LastName,
        level: student.level,
        groupName: student.groupName,
        birthday: student.birthday,
        address: student.address,
        phone: student.Phone
      };
    
      navigate(`/students/${student.id}`, { state: { student: studentData } }); // Pasar solo los datos
    };

    useEffect(() => {
      const searchForStudents = async () => {
        setIsLoadingS(true);
        try {
          const userRef = collection(db, 'users');
          const qs = query(userRef, where('IsStudent', '==', true));
          const studentSnapshot = await getDocs(qs);
  
          if (studentSnapshot.empty) {
            setIsEmptyS(true);
            setStudentArray([]); // Asegúrate de limpiar cualquier dato previo
            setIsLoadingS(false);
            return;
          }
  
          const studentsWithGroupName = await Promise.all(
            studentSnapshot.docs.map(async studentDoc => {
              const studentData = {
                id: studentDoc.id,
                ...studentDoc.data(),
              };

          
              if (studentData.GroupAID) {
                try {
                  // Usar GroupID directamente como referencia
                  const groupRef = doc(db, "Groups", studentData.GroupAID )
                  const groupDoc = await getDoc(groupRef);
                  studentData.groupName = groupDoc.exists()
                    ? groupDoc.data().GroupName
                    : 'Grupo desconocido';
                } catch (groupError) {
                  console.error(`Error al obtener el grupo para ${studentData.GroupID.id}:`, groupError);
                  studentData.groupName = 'Error al cargar grupo';
                }
              } else {
                studentData.groupName = 'Sin grupo asignado';
              }
          
              return studentData;
            })
          );
  
          setStudentArray(studentsWithGroupName);
          setIsEmptyS(false);
          setIsLoadingS(false);
        } catch (error) {
          setError('Ha ocurrido un error al cargar los alumnos.');
          console.error('Error:', error);
          setIsLoadingS(false);
        }
      };
  
      searchForStudents();
    }, []);

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
                <option value="level">Nivel</option>
                <option value="name">Nombre</option>
                <option value="group">Grupo</option>


              </select>
            </div>
            
        </div>
        <button onClick={() => setIsModalStudentVisible(true)}>Agregar Alumno</button>
        {modalStudentVisible && (
                <GenModal onClose={() => setIsModalStudentVisible(false)}>
                    <h2 className="modal-title">Alumno</h2>
                    <div className="modal-body">
                    <div className="form-grid">
        <div className="form-group">
            <label>Nivel:</label>
            <select>
                <option>Nivel-1</option>
                <option>Nivel-2</option>
                <option>Nivel-3</option>
                <option>Nivel-4</option>

            </select>
        </div>
        
        <div className="form-group">
            <label>Nombre:</label>
            <input type="text" />
        </div>
        <div className="form-group">
            <label>Apellidos:</label>
            <input type="text" />
        </div>
        <div className="form-group">
            <label>Fecha de nacimiento:</label>
            <input type="date" />
        </div>
       
    </div>
    <button  onClick={() => setIsModalStudentVisible(false)} className="submit-btn">Cerrar</button>
    <button onClick={addStudent}> Añadir alumno</button>
                    </div>
                </GenModal>
            )}
        </div>

        <div className="content">
        {loadingS ? (
          <p>Cargando estudiantes...</p>
        ) : error ? (
          <p className="error-message">{error}</p>
        ) : emptyS ? (
          <p>No hay alumnos registrados.</p>
        ) : (
            studentArray.map(student => (
              <StudentItem
              key={student.id}
              name={student.Name}
              lastname={student.LastName}
              level={student.level}
              group={student.groupName}
              onClick={() => goStudent(student)} // Pasar la función aquí
            />
            ))
          )}
        </div>


        <footer className="footer-eng">
          <Footer />
        </footer>
      </div>
    )
  }

  export default ManageStudents