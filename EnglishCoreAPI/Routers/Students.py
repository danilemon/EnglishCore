from fastapi import APIRouter, HTTPException
from Dataclases.Students import GetStudentDataRequest, StudentData
from Firebase.firebase import db

StudentsR = APIRouter()

@StudentsR.post('/GetStudentData', response_model=StudentData)
def GetStudent(Data: GetStudentDataRequest):
    # Cambiamos a '==' para la comparación en Firestore
    User_Ref = db.collection('users')
    Query_Ref = User_Ref.where('StudentID', '==', Data.StudentID).get()
    
    if not Query_Ref:
        raise HTTPException(status_code=404, detail="Student not found")
    
    # Suponiendo que solo hay un estudiante con ese ID, tomamos el primer documento
    student_data = Query_Ref[0].to_dict()

    # Convertimos los datos en un objeto de tipo StudentData
    student = StudentData(
        StudentID=student_data.get('StudentID'),
        Name=student_data.get('Name'),
        LastName=student_data.get('LastName'),
        Phone=student_data.get('Phone'),
        Address=student_data.get('address'),
        Birthday=student_data.get('birthday'),
        Username=student_data.get('username'),
        Group=student_data.get('Group_id')
    )

    return student
