import React, { useState, useEffect } from 'react';

import Header from '../components/Shared/Header';
import Footer from '../components/Shared/Footer';
import ActivityItem from '../components/ActivityListed/ActivityListed';
import db from '../services/firebaseConfig';

import {  collection,getDocs,doc,query,orderBy} from 'firebase/firestore';
function ViewActivities(){

    const [Activities,SetActivities]=useState([]);
    const [Option,SetOption]=useState(0);

    
    return(
    <div  className="home-page">
    <header className="header-eng">
        <Header />
    </header>
    <div className='Section-bar'>
    <ReturnTopBar Option={Option} UpdateActivities={SetActivities}/>
      <div style={{ borderLeft: '4px solid black', height: '120px' }}></div>
      <div className='SelectOption'>
          
          <p>Seleciona una Opcion:</p>
          <div className="custom-combobox">
            <select onChange={(event) => {
            const selectedValue = event.target.value;
            SetOption(selectedValue)
            }} value={Option}>
              <option value={0} disabled>Seleciona una opcion</option>
              <option value={1}>Actividades</option>
              <option value={2}>Examenes</option>
              <option value={3}>Practicas</option>
            </select>
          </div>
      </div>
    </div>

    <div className="content">
      {
       Activities.length === 0 && <p>Seleciona un nivel y unidad</p> 
      }
      {
        Activities.map((Act)=>(
          <ActivityItem Actvitiy={Act} />
        ))
      }
    </div>

    <footer className="footer-eng">
        <Footer />
    </footer>
    </div>)
}

function ActivitiesTopBar({UpdateActivities}){
  const [Level,setLevel]=useState(null);
  const [Unit,setUnit]=useState(null);

  const [Units,setUnits]=useState([]);

  const [UnitsCollection,SetUnitsColection]=useState(null);

  const getUnits=async(SelectedLevel)=>{
    const Collection = collection(db,"Levels");
    const docRef = doc(Collection,SelectedLevel);
    const SubUnits=collection(docRef,"units");
    SetUnitsColection(SubUnits);
    const Units=await getDocs(SubUnits);

    const UnitsList=[]

    Units.forEach((doc)=>{
      const data=doc.data()
      UnitsList.push(new LevelUnit(doc.id,data.Name) )
    })
    setUnits(UnitsList)
  }
  const getActs=async(SelectedUnit)=>{
    const UnitRef = doc(UnitsCollection,SelectedUnit);
    const ActivitiesColl = collection(UnitRef,"activities");
    const Activities= await getDocs(ActivitiesColl);

    const ActivitiesList=[]
    for(const doc of Activities.docs){
      const Collection = collection(doc.ref,"Preguntas");
      const Docs=await getDocs(Collection);
      const data=doc.data()
      ActivitiesList.push(new Activity(doc.id,data.Nombre,data.Tema,Docs.size,doc))
    }
    UpdateActivities(ActivitiesList)
  }
  return(<div className="top-bar2">
      <div>
          <p>Nivel:</p>
          <div className="custom-combobox">
            <select onChange={(event) => {
            const selectedValue = event.target.value;
            setLevel(selectedValue)
            getUnits(selectedValue)
            }} value={Level||"Seleciona un nivel"}>
              <option value="Seleciona un nivel" disabled>Seleciona un nivel</option>
              <option value={"level1"}>Nivel-1</option>
              <option value={"level2"}>Nivel-2</option>
              <option value={"level3"}>Nivel-3</option>
              <option value={"level4"}>Nivel-4</option>
              <option value={"level5"}>Nivel-5</option>
            </select>
          </div>
      </div>
      <div>
        <p>Unidad:</p>
        <div className="custom-combobox" >
            <select value={Unit||"Seleciona una unidad"} onChange={(event)=>{
              const selectedValue = event.target.value;
              setUnit(selectedValue);
              getActs(selectedValue);
            }}>
            <option value="Seleciona una unidad" disabled>Seleciona una unidad</option>
            {
              Units.map((Unit)=>(<option value={Unit.ID}>
                {Unit.Name}
              </option>))
            }
            </select>
          </div>
    </div>
  </div>)
}

function ReturnTopBar({Option,UpdateActivities}){
  switch(Option){
    case "1":
      return(<ActivitiesTopBar UpdateActivities={UpdateActivities}/>)
    case "2":
      return(<ExamsTopBar UpdateActivities={UpdateActivities}/>)
    case "3":
      return(<Practices UpdateActivities={UpdateActivities}/>)
    case 0:
      return(<div className="top-bar2"><p>Seleciona una opcion</p></div>) 
  }
}
function ExamsTopBar({UpdateActivities}){
  const  [Level,setLevel]=useState(null);
  const getActs=async(SelectedLevel)=>{
    const Collection = collection(db,"Levels");
    const docRef = doc(Collection,SelectedLevel);
    const ExamsCol=collection(docRef,"exams");
    const Exams=await getDocs(ExamsCol);

    const ExamList=[]
    for(const doc of Exams.docs){
      const Collection = collection(doc.ref,"Preguntas");
      const Docs=await getDocs(Collection);
      const data=doc.data()
      ExamList.push(new Activity(doc.id,data.Nombre,data.Tema,Docs.size,doc))
    }
    UpdateActivities(ExamList)
  }
  return(<div className="top-bar2">
    <div>
        <p>Nivel:</p>
        <div className="custom-combobox">
          <select onChange={(event) => {
          const selectedValue = event.target.value;
          getActs(selectedValue)
          setLevel()
          }} value={Level||"Seleciona un nivel"}>
            <option value="Seleciona un nivel" disabled>Seleciona un nivel</option>
            <option value={"level1"}>Nivel-1</option>
            <option value={"level2"}>Nivel-2</option>
            <option value={"level3"}>Nivel-3</option>
            <option value={"level4"}>Nivel-4</option>
            <option value={"level5"}>Nivel-5</option>
          </select>
        </div>
    </div>
</div>)
}
function Practices({UpdateActivities}){

}
class LevelUnit{
  constructor(ID,Name){
    this.ID=ID,
    this.Name=Name
  }
}
class Activity{
  
  constructor(ID,Name,Subject,Number,Doc){
    this.ID=ID,
    this.Name=Name,
    this.Subject=Subject,
    this.Number=Number,
    this.Doc=Doc
    }

  async  getQuestions() {
    const Collection =collection(this.Doc.ref,"Preguntas");
    const Query=query(Collection,orderBy("Index","asc"))
    const Docs=await getDocs(Query);
    const Questions=[];
    Docs.forEach((doc)=>{
      const data=doc.data()
      switch(data.Tipo){
        case 0:
          Questions.push(new TextView(data.Text))
          break;
        case 1:
          Questions.push(new OpenQuestion(data.Pregunta||"",data.TextoSecundario||"",data.Imagen||"",data.Respuesta||""))
          break;
        case 2:
          Questions.push(new ClosedQuestion(data.Pregunta||"",data.TextoSecundario||"",data.Imagen||"",data.Incisos||[],data.Respuesta||0,data.TrueFalse||false))
          break;
        case 3:
          Questions.push(new CompleteQuestion(data.Pregunta||"",data.TextoSecundario||"",data.Imagen||"",data.Answers||[],data.TextoAcompletar||"",data.Options||[],data.MultipleSets||[],data.NoRep||false))
          break;
        
        
      }
    })
    return Questions
  }
}
export class OpenQuestion{
  constructor(Question,HelpText,Img,Answer){
    this.Type=1,
    this.Question=Question,
    this.HelpText=HelpText,
    this.Img=Img,
    this.Answer=Answer
  }
}

export class ClosedQuestion{
  constructor(Question,HelpText,Img,Options,Answer,TrueFalse){
    this.Type=2,
    this.Question=Question,
    this.HelpText=HelpText,
    this.Img=Img,
    this.Options=Options,
    this.Answer=Answer,
    this.TrueFalse=TrueFalse
  }
}

export class CompleteQuestion{
  constructor(Question,HelpText,Img,Answer,Text,Options,MultipleSets,NoRep){
    this.Type=3,
    this.Question=Question,
    this.HelpText=HelpText,
    this.Img=Img,
    this.Answer=Answer,
    this.Text=Text,
    this.Options=Options,
    this.MultipleSets=MultipleSets,
    this.NoRep=NoRep
  }
}

export class TextView{
  constructor(HelpText){
    this.HelpText=HelpText
  }
}
export default ViewActivities