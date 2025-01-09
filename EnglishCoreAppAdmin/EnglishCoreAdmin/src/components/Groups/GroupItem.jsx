import React, { useState, useEffect } from 'react';
import {UserPreview} from "../../pages/GroupView"
import './GroupItem.css'
import db from "../../services/firebaseConfig"
import { where ,collection,getDocs,getDoc,doc,query,addDoc} from 'firebase/firestore';
function GroupItem({Group}) {
    const [isModalVisible, setIsModalVisible] = useState(false);
    const [students, setStudents] = useState([]);

    const [FullStudents,SetFullStudents]=useState([]);
    const [Teachers,setTeachers]=useState([]);

    const [level, setLevel] = useState(null);
    const [hours, setHours] = useState(null);
    const [days, setDays] = useState(null);
    const [teacher, setTeacher] = useState(null);
    const [date,setDate]=useState(null);

    const GetAllStudents= async()=>{
        const Collection = collection(db,"users")
        const q = query(Collection,
            where("GroupAID","!=",Group.ID))
        const querySnapshot = await getDocs(q);
        const Students = []
        querySnapshot.forEach((doc)=>{
          const data=doc.data()
          Students.push(new UserPreview(doc.id,data.Name+" "+data.LastName));
        });
        SetFullStudents(Students)
    }

    const GetTeachers = async()=>{
        const Collection = collection(db,"users")
        const q = query(Collection,where("IsStudent","==",false))
        const querySnapshot = await getDocs(q);
        const teachers = []
        querySnapshot.forEach((doc)=>{
          const data=doc.data()
          teachers.push(new UserPreview(doc.id,data.Name+" "+data.LastName));
        });
        setTeachers(teachers)
      }

    const AddStudent = async(event)=>{
        const value = event.target.value;
        const user = FullStudents.find((student) => student.FName === value);
        if(user==undefined){
            return
        }
        Group.StudentList.push(user);
        const updatedFullStudents = FullStudents.filter((student) => student.ID !== user.ID);
        SetFullStudents(updatedFullStudents);
        const studentsList = [...students, user]; 
        setStudents(studentsList);
        
    }
    useEffect(() => {
        const Setup = async () => {
          if (isModalVisible) {
            const fetchedStudents = await Group.GetStudents();
            setStudents(fetchedStudents); // Asegúrate de que se establecen los datos reales
            GetAllStudents();
            GetTeachers();
          }
        };
    
        Setup(); // Llamada a la función asíncrona
      }, [isModalVisible,setStudents]); // Dependencias
    // Depuración: useEffect que reacciona a isModalVisible
    console.log('Componente montado');
    return (
        <div className="GroupItem">
            <p>Nivel: {Group.Level}</p>
            <p>Horario: {Group.Hours}</p>
            <p>Dias: {Group.Days}</p>
            <p>Profesor: {Group.Profesor}</p>
            <p>Numero de alumnos: {Group.Number}</p>
            <button onClick={() => setIsModalVisible(true)}>Desplegar</button>
            {isModalVisible && (
                <Modal onClose={() =>{ 
                    setIsModalVisible(false)
                    setLevel(null)
                    setHours(null)
                    setDays(null)
                    setTeacher(null)
                    setDate(null)
                }}>
                    <h2 className="modal-title">Grupo</h2>
                    <div className="modal-body">
                    <div className="form-grid">
        <div className="form-group">
            <label>Nivel:</label>
            <select onChange={
              (event) => {
                const selectedValue = event.target.value;
                setLevel(selectedValue); 
            }} value={level||""}>
            <option value="" disabled></option>
                <option value={"level1"}>Nivel-1</option>
                <option value={"level2"}>Nivel-2</option>
                <option value={"level3"}>Nivel-3</option>
                <option value={"level4"}>Nivel-4</option>
                <option value={"level5"}>Nivel-5</option>
            </select>
        </div>

        <div className="form-group">
            <label>Horario:</label>
            <select onChange={
              (event) => {
                const selectedValue = event.target.value;
                setHours(selectedValue); 
            }} value={hours||""}>
                <option value="" disabled></option>
                <option value={"5:00 - 8:00"}>5:00 - 8:00</option>
                <option value={"8:00 - 11:00"}>8:00 - 11:00</option>
            </select>
        </div>
        <div className="form-group">
            <label>Días:</label>
            <select onChange={(event) => {
            const selectedValue = event.target.value;
            setDays(selectedValue); }} 
              value={days||""}>
                <option value="" disabled></option>
                <option value={"L-M-V"}>L-M-V</option>
                <option value={"M-J"}>M-J</option>
                <option value={"S"}>S</option>
            </select>
        </div>
        <div className="form-group">
            <label>Profesor:</label>
            <select onChange={(event) => {
            const selectedValue = event.target.value;
            setTeacher(selectedValue);}}
              value={teacher || ""}>
                <option value="" disabled></option>
              {
                Teachers.map((teacher)=>(<option value={teacher.ID}>
                  {teacher.FName}
                </option>))
              }
            </select>
        </div>
        <div className="form-group">
            <label>Fecha de inicio:</label>
            <input type="date" value={date || ""} onChange={(event) => {
            const selectedValue = event.target.value;
            setDate(selectedValue);}}/>
        </div>
        <div className="form-group">
            <label>Agregar Alumno:</label>
            <input type="text" list="options" onInput={AddStudent}/>
            <datalist id="options">
                {
                    FullStudents.map((student)=>(
                        <option value={student.FName}></option>
                    ))
                }
            </datalist>
        </div>
    </div>
    <div className="students-list">
        {
            students.map((student) => (
                <p>{student.FName}</p>
              ))
        }
    </div>
    <button className="submit-btn"  onClick={
        () => Group.UpdateGroup(level, hours, days, teacher)
        }>guardar</button>
                    </div>
                </Modal>
            )}
        </div>
    );
}
function Modal({ onClose, children }) {
    return (
        <div className="modal-overlay" onClick={onClose}>
            <div className="modal-content" onClick={(e) => e.stopPropagation()}>
                <button className="close-button" onClick={onClose}>
                    &times;
                </button>
                {children}
            </div>
        </div>
    );
}

export default GroupItem;


