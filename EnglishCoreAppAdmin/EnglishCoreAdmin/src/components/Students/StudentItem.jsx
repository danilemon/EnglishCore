    import React from 'react'
    import './StudentItem.css'

    function StudentItem({name, lastname, level, group, onClick}){

        
        return(
            <div className="StudentItem" onClick={onClick}>
                <div>
                <strong>Nombre: </strong>
                <p>{name} {lastname} </p>
                </div>
                <div>
                <strong>Nivel:  </strong>
                <p>{level}</p>
                </div>
            <div>
            <strong>Grupo:</strong>
            <p>{group}</p>
            </div>
                
            </div>
        )
    }

    export default StudentItem;