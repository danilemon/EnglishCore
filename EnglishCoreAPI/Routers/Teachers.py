from fastapi import APIRouter,HTTPException
from Dataclases.Teachers import GetGroupsRequest,Groups,StudetnsPreview
from Firebase.firebase import db

TeachersR=APIRouter()

@TeachersR.post('/GetGroups',response_model=list[Groups])
def GetGroups(Data:GetGroupsRequest):
    Groups_ref=db.collection('Groups')
    Query_ref=Groups_ref.where('TeacherID','==',Data.ID).get()
    groups_list: list[Groups] = []
    for document in Query_ref:
        data = document.to_dict()  # Convertir cada documento a un diccionario
        # Crear una instancia de Groups con los datos obtenidos
        ID=data.get('ID')
        Days=data.get('Days')
        Hours=data.get('Hours')
        Level=data.get('Level')
        StartDate=data.get('StartDate')
        TeacherID=data.get('TeacherID')
        group = Groups(ID=ID,Days=Days,Hours=Hours,Level=Level,StartDate=StartDate,TeacherID=TeacherID)
        groups_list.append(group) 
    print(groups_list)
    return(groups_list)

@TeachersR.post('/GetStudents', response_model=list[StudetnsPreview])
def GetStudents(Data:GetGroupsRequest):
    pass