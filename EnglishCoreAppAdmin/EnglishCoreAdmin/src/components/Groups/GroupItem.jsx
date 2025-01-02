import React, { useState } from 'react';
import './GroupItem.css'
function GroupItem({ Level, Hours, Days, Teacher, Students }) {
    const [isModalVisible, setIsModalVisible] = useState(false);

    return (
        <div className="GroupItem">
            <p>Nivel: {Level}</p>
            <p>Horario: {Hours}</p>
            <p>Dias: {Days}</p>
            <p>Profesor: {Teacher}</p>
            <p>Numero de alumnos: {Students}</p>
            <button onClick={() => setIsModalVisible(true)}>Desplegar</button>
            {isModalVisible && (
                <Modal onClose={() => setIsModalVisible(false)}>
                    <h2 className="modal-title">Grupo</h2>
                    <div className="modal-body">
                    <div className="form-grid">
        <div className="form-group">
            <label>Nivel:</label>
            <select>
                <option>Nivel-1</option>
                <option>Nivel-2</option>
            </select>
        </div>
        <div className="form-group">
            <label>Horario:</label>
            <select>
                <option>5:00 - 8:00</option>
                <option>8:00 - 11:00</option>
            </select>
        </div>
        <div className="form-group">
            <label>Días:</label>
            <select>
                <option>L-M-J</option>
                <option>M-W-F</option>
            </select>
        </div>
        <div className="form-group">
            <label>Profesor:</label>
            <input type="text" />
        </div>
        <div className="form-group">
            <label>Fecha de inicio:</label>
            <input type="date" />
        </div>
        <div className="form-group">
            <label>Agregar Alumno:</label>
            <input type="text" />
        </div>
    </div>
    <div className="students-list">
        <p>Daniel Lopez Aguilera - 1234567890 ✖</p>
        <p>Daniel Lopez Aguilera - 1234567890 ✖</p>
        <p>Daniel Lopez Aguilera - 1234567890 ✖</p>
    </div>
    <button className="submit-btn">Cerrar</button>
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


