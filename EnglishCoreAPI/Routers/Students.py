from fastapi import APIRouter, HTTPException
from Dataclases.Students import GetStudentDataRequest, StudentData
from Firebase.firebase import db

StudentsR = APIRouter()

@StudentsR.post('/GetStudentData', response_model=StudentData)
def GetStudent(Data: GetStudentDataRequest):
    # Accede directamente al documento usando el ID proporcionado
    User_Ref = db.collection('users').document(Data.StudentDocId)
    student_doc = User_Ref.get()
    
    if not student_doc.exists:
        raise HTTPException(status_code=404, detail="Student not found")
    
    # Obtén los datos del documento como un diccionario
    student_data = student_doc.to_dict()

    # Convertimos los datos en un objeto de tipo StudentData
    student = StudentData(
        StudentID=student_data.get('StudentID'),
        Name=student_data.get('Name'),
        LastName=student_data.get('LastName'),
        Phone=student_data.get('Phone'),
        Address=student_data.get('address'),
        Birthday=student_data.get('birthday'),
        Username=student_data.get('username'),
        # GroupID=student_data.get('GroupID')  # Descomenta si usas este campo
    )

    return student


@StudentsR.put('/UpdateStudentData')
def UpdateStudent(Data: StudentData):
    User_Ref = db.collection('users')
    Query_Ref = User_Ref.where('StudentID', '==', Data.StudentID).get()
    
    if not Query_Ref:
        raise HTTPException(status_code=404, detail="Student not found")
    
    # Obtiene la referencia del documento y actualiza
    doc_ref = Query_Ref[0].reference
    doc_ref.update(Data.dict())  # Usa Data.dict() para actualizar con todos los datos

    return {"message": "Student data updated successfully"}
