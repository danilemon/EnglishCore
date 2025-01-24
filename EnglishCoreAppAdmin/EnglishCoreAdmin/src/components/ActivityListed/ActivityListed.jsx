import React, { useState, useEffect } from 'react';
import {OpenQuestion,ClosedQuestion,CompleteQuestion} from "../../pages/ViewActivities"
import db from "../../services/firebaseConfig"
import './ActivityListed.css'

function ActivityListed({Actvitiy}){
    const [isModalVisible, setIsModalVisible] = useState(false);
    const [ActivityValue,setAct]=useState(Actvitiy);

    return(
        <div className="ActivityItem">
            <p>Nombre: {Actvitiy.Name}</p>
            <p>Tema: {Actvitiy.Subject}</p>
            <p>Preguntas: {Actvitiy.Number}</p>
            <button onClick={()=>setIsModalVisible(true)}>Ver Actividad</button>

        {isModalVisible && (
            <Modal onClose={() =>{ 
                setIsModalVisible(false)
            }}>
                <ActivityViwer ACT={ActivityValue}/>
            </Modal>
        )}
        </div>
    )
}

function ActivityViwer({ACT}) {
    const [index,setIndex]=useState(0);
    const [Question,setQuestion]=useState(new OpenQuestion("","","",""))
    const [QuestionList,setQuestions]=useState([])

    const nextQuestion= ()=>{
        if(index<QuestionList.length-1){
            setIndex(index+1)
            setQuestion(QuestionList[index+1])
        }
    }
    const previusQuestion= ()=>{
        if(index>0){
            setIndex(index-1)
            setQuestion(QuestionList[index-1])
        }
    }
    useEffect(() => {
        const fetchQuestions = async () => {
          try {
            const questions = await ACT.getQuestions(); // Asegúrate de que esta función devuelve la lista
            setQuestions(questions); // Actualiza la lista de preguntas
            if (questions.length > 0) {
              setQuestion(questions[0]); // Guarda el primer elemento
            }
          } catch (error) {
            console.error("Error fetching questions:", error);
          }
        };
      
        fetchQuestions(); // Llama a la función de manera segura
      }, [setQuestion,setQuestions]);  // Llama a la función de manera segura
    return (
        <div className="activity-viewer">
            <div className="phone-screen">
            <div className="text-container">
                    <p className="small-text">{ACT.Name+":"+ACT.Subject}</p>
                    <p className="large-bold-text">{(index+1)+". "+Question.Question}</p>
                    <hr className="divider" />
                    <p className="centered-gray-text">{Question.HelpText}</p>
            </div>
            <div className="Question-Content">
                {
                    Content(Question)
                }
            </div>
            </div>
            <div className="navigation-buttons">
                <button className="arrow-button" onClick={previusQuestion}>⬅</button>
                <button className="arrow-button"onClick={nextQuestion}>➡</button>
            </div>
        </div>
    );
}

function ClosedQuestionView({CQ}){
    if(CQ.TrueFalse){
        return(<div className='ClosedQuestion'>
            <input 
                type="radio" 
                id="option-true" 
                name="options" 
                value="True" 
            />
            <label for="option-true">True</label>

            <input 
                type="radio" 
                id="option-false" 
                name="options" 
                value="False" 
            />
            <label for="option-false">False</label>
            </div>)  
    }else{
        return(<div className='ClosedQuestion'>
            {
                CQ.Options.map((unit)=>(
                <div>
                <input 
                    type="radio" 
                    id={unit} 
                    name="options" 
                    value="False" 
                /> 
                <label for={unit}>{unit}</label>
                </div>
                ))
            }
            </div>)
    }
    

}

function OpenQuestionView({OP}){
    return(<div className='OpenQuestionView'>
        <p>{OP.Answer}</p>
    </div>)
}

function CompleteTextView({CT}){

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
function Content(Question){
    switch(true){
        case Question instanceof OpenQuestion:
            return(<OpenQuestionView OP={Question}/>)
            break;
        case Question instanceof ClosedQuestion:
            return(<ClosedQuestionView CQ={Question}/>)
            break;
        case Question instanceof CompleteQuestion:
            break;
    }
}
export default ActivityListed;

