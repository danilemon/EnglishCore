import React from 'react';
import '../assets/styles/GroupView.css'
import '../components/Groups/GroupItem'
import GroupItem from '../components/Groups/GroupItem';

function GroupView(){
    const groupData = [
        { id: 1, name: "Grupo A", level: "Nivel-1" },
        { id: 2, name: "Grupo B", level: "Nivel-2" },
        { id: 3, name: "Grupo C", level: "Nivel-3" },
      ];
    return (
        <div>
            <div className="top-bar">
            <div>
                <p>Nivel:</p>
                <div class="custom-combobox">
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
            <div class="custom-combobox">
                <select>
                    <option value="1">Nivel-1</option>
                    <option value="2">Nivel-2</option>
                    <option value="3">Nivel-3</option>
                    <option value="4">Nivel-4</option>
                    </select>
            </div>
            </div>
            <div>
            <p>Dias:</p>
            <div class="custom-combobox">
                <select>
                    <option value="1">Nivel-1</option>
                    <option value="2">Nivel-2</option>
                    <option value="3">Nivel-3</option>
                    <option value="4">Nivel-4</option>
                    </select>
            </div>
            </div>
            <button>Crear Grupo</button>
            </div>


            <div className="content">
                <GroupItem Level={1} Hours={"20:00 - 21:00"} Days={"L-M-J-V"} Teacher={'Daniel Lopez'} Students={1}/>
                <GroupItem Level={1} Hours={"20:00 - 21:00"} Days={"L-M-J-V"} Teacher={'Daniel Lopez'} Students={1}/>
            
            </div>

        </div>
    )
}

export default GroupView