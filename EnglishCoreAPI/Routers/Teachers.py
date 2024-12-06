from fastapi import APIRouter,HTTPException
from Dataclases.Teachers import GetGroupsRequest,Groups,StudetnsPreview,StudentInfo
from Firebase.firebase import db

TeachersR=APIRouter()

@TeachersR.post('/GetGroups',response_model=list[Groups])
def GetGroups(Data:GetGroupsRequest):
    User_ref=db.collection('users').document(Data.ID)
    User_doc=User_ref.get()
    Groups_ref=User_doc.get("Groups")
    groups_list: list[Groups] = []
    for Group in Groups_ref:
        Group_Doc=Group.get() 
        data = Group_Doc.to_dict()  # Convertir cada documento a un diccionario
        # Crear una instancia de Groups con los datos obtenidos
        ID=Group_Doc.id
        Days=data.get('Days')
        Hours=data.get('Hours')
        Level=data.get('Level').get().get("Level")
        StartDate=data.get('StartDate')
        TeacherID=data.get('TeacherID')
        group = Groups(ID=ID,Days=Days,Hours=Hours,Level=Level,StartDate=StartDate,TeacherID=TeacherID)
        groups_list.append(group) 
    print(groups_list)
    return(groups_list)

    
        

@TeachersR.post('/GetStudents', response_model=list[StudetnsPreview])
def GetStudents(Data:GetGroupsRequest):
    Group=db.collection('Groups').document(Data.ID)
    if Group:
        Group_Doc=Group.get()
        Students=Group_Doc.get('StudentsIDs')
        ReturnList=[]
        for Student in Students:
            Student_Doc=Student.get().to_dict()
            Student=StudetnsPreview(ID=Student.get().id,Name=Student_Doc.get('Name')+" "+Student_Doc.get('LastName'))
            ReturnList.append(Student)
        return(ReturnList)
    
@TeachersR.post('/GetStudentInfo')
def GetStudentInfo(Data:GetGroupsRequest):
    Student=db.collection('users').document(Data.ID)
    if Student:
        Student_doc=Student.get()
        FinalStudent=StudentInfo(ID=Data.ID,Name=Student_doc.get('Name')+" "+Student_doc.get('LastName'),Cellphone=Student_doc.get('Phone'),Adrress=Student_doc.get('address'))
        return FinalStudent
