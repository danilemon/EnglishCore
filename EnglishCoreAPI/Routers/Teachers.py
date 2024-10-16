from fastapi import APIRouter,HTTPException
from Dataclases.Teachers import GetGroupsRequest,Groups
from Firebase.firebase import db

Teachers=APIRouter()

Teachers.post('/GetGroups')
def GetGroups(Teacher:GetGroupsRequest):
    Groups_ref=db.collection('Groups')
    Query_ref=Groups_ref.where('TeacherID','==',Teacher.ID).get()
    groups_list: list[Groups] = []
    for document in Query_ref:
        data = document.to_dict()  # Convertir cada documento a un diccionario
        # Crear una instancia de Groups con los datos obtenidos
        group = Groups(
            ID=data.get('ID'),
            Days=data.get('Days'),
            Hours=data.get('Hours'),
            Level=data.get('Level'),
            StartDate=data.get('StartDate'),
            TeacherID=data.get('TeacherID')
        )
        groups_list.append(group) 
    return(groups_list)