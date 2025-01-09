import {React,useState, useEffect } from 'react';

import '../assets/styles/GroupView.css';
import db from '../services/firebaseConfig';
import GroupItem from '../components/Groups/GroupItem';
import Swal from 'sweetalert2';
import Header from '../components/Shared/Header';
import Footer from '../components/Shared/Footer';
import { collection, getDocs,getDoc,doc,query,where,addDoc,updateDoc} from 'firebase/firestore';

function GroupView() {
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [groupData, setGroupData] = useState([]);

  const [Teachers,setTeachers]=useState([]);

  const [level, setLevel] = useState(null);
  const [hours, setHours] = useState(null);
  const [days, setDays] = useState(null);
  const [teacher, setTeacher] = useState(null);

  const GetGroups = async() =>{
    const GroupsCollection = collection(db, "Groups");
  
  // Esperamos la respuesta de getDocs
    const groupDocs = await getDocs(GroupsCollection);


    const GroupsList = [];
    for (const doc of groupDocs.docs) {
      const data = doc.data();
      const teacher= data.Teacher
      const TEACHER=await getDoc(teacher)
      const TeacherData=TEACHER.data();
      let GroupData = new Group(
        doc.id,
        data.LevelNumber,
        data.Hours,
        data.Days,
        TeacherData.Name+" "+TeacherData.LastName, // Concatenando el nombre y apellido
        TEACHER.id,
        data.StudentsIDs.length
      );
      GroupsList.push(GroupData);
    }
    setGroupData(GroupsList);
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

  const CreateGroup = async ()=>{
    if(!level||!hours||!days||!teacher){
      alert(`Rellene todos los campos para crear un grupo`);
    }else{
      const TeacherRef = doc(db,"users",teacher)
      const LevelRef = doc(db,"Levels",level)
      await addDoc(collection(db,"Groups"),{
        Days:days,
        LevelNumber:level,
        Hours:hours,
        Teacher:TeacherRef,
        Level:LevelRef,
        StudentsIDs:[]
      });
      setIsModalVisible(false)
      const Toast = Swal.mixin({
              toast: true,
              position: "top-end",
              showConfirmButton: false,
              timer: 3000,
              timerProgressBar: true,
              didOpen: (toast) => {
                toast.onmouseenter = Swal.stopTimer;
                toast.onmouseleave = Swal.resumeTimer;
              }
            });
      Toast.fire({
        icon: "success",
        title: "Grupo creado correctamente"
      });
    }
  }
  useEffect(() => {
    GetGroups();
    if(isModalVisible){
      GetTeachers()
    }else{

    }
  }, [isModalVisible]);
  return (
    <div className="home-page">
      <header className="header-eng">
        <Header />
      </header>
      

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
        <button onClick={() => setIsModalVisible(true)}>Crear Grupo</button>
      </div>

      <div className="content">
      {groupData.map((groupItem) => (
          <GroupItem Group={groupItem} />
        ))}
      </div>

      <footer className="footer-eng">
        <Footer />
      </footer>
      {isModalVisible && (
        <Modal onClose={() => {
          setIsModalVisible(false)
          setDays(null)
          setTeacher(null)
          setHours(null)
          setLevel(null)}}>
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

    </div>

    <button className="submit-btn" onClick={
      CreateGroup
      }>Crear</button>
  </div>
</Modal>)}
</div>
  );
}

function Modal({ onClose, children }) {
  return (
      <div className="modal-overlay" onClick={onClose} >
          <div className="modal-content" onClick={(e) => 
            e.stopPropagation()} 
            style={{width:"400px"}}>
              <button className="close-button" onClick={onClose}>
                  &times;
              </button>
              {children}
          </div>
      </div>
  );
}


class Group{
  constructor(ID,Level,Hours,Days,Profesor,ProfesorID,Number){
    this.ID=ID;
    this.Level=Level;
    this.Hours=Hours;
    this.Days=Days;
    this.Profesor=Profesor;
    this.ProfesorID=ProfesorID;
    this.Number=Number
    this.StudentList=[];
  }

  async GetStudents(){
    const StudentsList=[]
    const DocRef=doc(db,"Groups",this.ID)
    const GroupDoc=await getDoc(DocRef);
    const Students = GroupDoc.data().StudentsIDs
    for (const i of Students){
      const StudentDoc=await getDoc(i);
      const data=StudentDoc.data()
      StudentsList.push(new UserPreview(StudentDoc.id,data.Name+" "+data.LastName))
    }
    return StudentsList;
  }

  async UpdateGroup(level,hours,days,profesor){

    this.Level=level||this.Level
    this.Hours=hours||this.Hours
    this.Days=days||this.Days
   

    const DocRef=doc(db,"Groups",this.ID)
    const GroupDoc=await getDoc(DocRef);


    const newProfesor=profesor||this.ProfesorID

    const TeacherRef=doc(db, "users",newProfesor);
    const TeacherDoc= await getDoc(TeacherRef)
    const TeacherData=TeacherDoc.data()

    const OriginalTeacher= doc (db,"users",this.ProfesorID)
    const OriginalDoc=await getDoc(OriginalTeacher)

    this.Profesor=TeacherData.Name+" "+TeacherData.LastName
    this.ProfesorID=TeacherDoc.id

    

    const LevelRef = doc(db,"Levels",this.Level)
    const NewGroup={
      Days:this.Days,
      Hours:this.Hours,
      Teacher:TeacherRef,
      Level:LevelRef,
      LevelNumber:this.Level

    }
    await updateDoc(DocRef,NewGroup);

    if(profesor){
      const OriginalData= OriginalDoc.data()
      const OriginalGroups=OriginalData.Groups
      const copy=OriginalGroups
      copy.forEach((group,index)=>{
        if (group.id === GroupDoc.id) {
          OriginalGroups.splice(index, 1);
        }
      })
      const NewGroups=TeacherData.Groups
      NewGroups.push(DocRef)

      await updateDoc(TeacherRef,{Groups:NewGroups})
      await updateDoc(OriginalTeacher,{Groups:OriginalGroups})
    }



  }
}

export class UserPreview{
  constructor(ID,FName){
    this.ID=ID;
    this.FName=FName;
  }
}
export default GroupView;
