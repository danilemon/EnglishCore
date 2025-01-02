    import React from 'react'
    import './ScoresItem.css'

    function ScoresItem({lapse, level, generalScore, onClick}){

        
        return(
            <div className="ScoresItem" onClick={onClick}>
                <div>
                <strong>Periodo: </strong>
                <p>{lapse} </p>
                </div>
                <div>
                <strong>Nivel:  </strong>
                <p>{level}</p>
                </div>
            <div>
            <strong>Calificación:</strong>
            <p>{generalScore}</p>
            </div>
                
            </div>
        )
    }

    export default ScoresItem;