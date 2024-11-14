from fastapi import APIRouter,HTTPException
from Dataclases.Teachers import GetGroupsRequest,Groups,StudetnsPreview,StudentInfo
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
    Group=db.collection('Groups').where('ID','==',Data.ID).get()
    if Group:
        Group_Doc=Group[0]
        Students=Group_Doc.get('StudentsIDs')
        ReturnList=[]
        for Student in Students:
            Student_Doc=Student.get().to_dict()
            Student=StudetnsPreview(ID=Student_Doc.get("StudentID"),Name=Student_Doc.get('Name')+" "+Student_Doc.get('LastName'))
            ReturnList.append(Student)
        return(ReturnList)
    
@TeachersR.post('/GetStudentInfo')
def GetStudentInfo(Data:GetGroupsRequest):
    Student=db.collection('users').where('StudentID','==',Data.ID).get()
    if Student:
        Student_doc=Student[0]
        FinalStudent=StudentInfo(ID=Data.ID,Name=Student_doc.get('Name')+" "+Student_doc.get('LastName'),Cellphone=Student_doc.get('Phone'),Adrress=Student_doc.get('address'))
        return FinalStudent
