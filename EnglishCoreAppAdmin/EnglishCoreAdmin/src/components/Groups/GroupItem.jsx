import React, { useState, useEffect } from 'react';
import {UserPreview} from "../../pages/GroupView"
import './GroupItem.css'
import db from "../../services/firebaseConfig"
import { where ,collection,getDocs,query} from 'firebase/firestore';
function GroupItem({Group}) {
    const [isModalVisible, setIsModalVisible] = useState(false);
    const [students, setStudents] = useState([]);
    const [Schedules, setSchedules]=useState([]);

    const [newStudents,setNewStudents]=useState([]);
    const [RemovedStudents,setRemovedStudents]=useState([]);

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
            where("GroupAID","==",""))
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
        const newUser = FullStudents.find((student) => student.FName === value);
        if(newUser==undefined){
            return
        }
        Group.StudentList.push(newUser);
        const updatedFullStudents = FullStudents.filter((student) => student.ID !== newUser.ID);
        SetFullStudents(updatedFullStudents);
        const studentsList = [...students, newUser]; 
        setStudents(studentsList);
        const Repeated = RemovedStudents.findIndex(std=>std === newUser.ID)
        if(Repeated !== -1){
            const UpdatedStudents=[...RemovedStudents]
            UpdatedStudents.splice(Repeated,1)
            setRemovedStudents(UpdatedStudents)
        }else{
            setNewStudents((Prev)=>[...Prev,newUser])
        }
        
    }
    const RemoveStudent = async(Student,Name,Index)=>{
        const Students = [...students]; 
        Students.splice(Index, 1);  
        setStudents(Students)
        const Repeated = newStudents.findIndex(studentI => studentI.ID === Student);
        SetFullStudents((Prev)=>[...Prev,new UserPreview(Student,Name)])
        if(Repeated !== -1){
            const UpdatedStudents=[...newStudents]
            UpdatedStudents.splice(Repeated,1)
            setNewStudents(UpdatedStudents)
        }else{
            setRemovedStudents((Prev)=>[...Prev,Student])
        }
        
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
                    setRemovedStudents([])
                    setIsModalVisible(false)
                    setLevel(null)
                    setHours(null)
                    setDays(null)
                    setTeacher(null)
                    setDate(null)
                    setStudents([])
                    setNewStudents([])
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
            }} value={level||Group.Level}>
            <option value="" disabled></option>
                <option value={"level1"}>Nivel-1</option>
                <option value={"level2"}>Nivel-2</option>
                <option value={"level3"}>Nivel-3</option>
                <option value={"level4"}>Nivel-4</option>
                <option value={"level5"}>Nivel-5</option>
            </select>
        </div>
        <div className="form-group">
            <label>Días:</label>
            <select onChange={(event) => {
            const selectedValue = event.target.value;
            setDays(selectedValue); 
            switch(selectedValue){
                case "L-M-V":
                  setSchedules(["9:00 - 10:20 am","4:00 - 5:20 Pm","6:00 - 7:20 Pm","7:30 - 8:50 Pm"])
                  setHours(null)
                  break;
                case "M-J":
                  setSchedules(["8:00 - 10 am"," 4:00 - 6:00 Pm","6:00 - 8:00 Pm"])
                  setHours(null)
                  break;
                case "S":
                  setSchedules(["9:00 - 1:00 PM"])
                  setHours(null)
                  break;
              }
            }} 
              value={days||Group.Days}>
                <option value="" disabled></option>
                <option value={"L-M-V"}>L-M-V</option>
                <option value={"M-J"}>M-J</option>
                <option value={"S"}>S</option>
            </select>
        </div>
        <div className="form-group">
            <label>Horario:</label>
            <select onChange={
              (event) => {
                const selectedValue = event.target.value;
                setHours(selectedValue); 
            }} value={hours||Group.Hours}>
                {
                  hours == null &&(<option value="" >{Group.Hours}</option>)  
                }
                {
                  Schedules.map((Hours)=>(<option value={Hours}>
                    {Hours}
                  </option>
                  ))
                }
            </select>
        </div>
        
        <div className="form-group">
            <label>Profesor:</label>
            <select onChange={(event) => {
            const selectedValue = event.target.value;
            setTeacher(selectedValue);}}
              value={teacher || Group.Profesor}>
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
            <input type="date" value={date || Group.StartDate} onChange={(event) => {
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
            students.map((student,index) => (
                <div key={student.ID} className="student-row">
                  <p className="student-name">{student.FName}</p>
                  <button className="student-button" onClick={() => RemoveStudent(student.ID,student.FName,index)}>X</button>
                </div>
              ))
        }
    </div>
    <button className="submit-btn"  onClick={() => 
        {Group.UpdateGroup(level, hours, days, teacher,date,newStudents,RemovedStudents)
        setNewStudents([])
        setRemovedStudents([])   
        }}>guardar</button>
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


