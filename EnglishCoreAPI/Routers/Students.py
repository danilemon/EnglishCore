from typing import Dict, List
from fastapi import APIRouter, HTTPException
from Dataclases.Students import GetStudentDataRequest, StudentData, GetStudentReminds
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


@StudentsR.put('/UpdateStudentData/{studentDocID}')
def UpdateStudent(studentDocID: str, updatedFields: Dict[str, str]):
    # Obtiene la referencia del documento
    User_Ref = db.collection('users').document(studentDocID)
    student_doc = User_Ref.get()
    
    if not student_doc.exists:
        raise HTTPException(status_code=404, detail="Student not found")
    
    # Actualiza solo los campos proporcionados
    User_Ref.update(updatedFields)
    
    return {"message": "Student data updated successfully"}


@StudentsR.post('/GetStudentReminders', response_model=List[GetStudentReminds])
def fetch_student_reminders(Data: GetStudentDataRequest):
    # Accede al documento del usuario
    print(f"StudentDocId recibido: {Data.StudentDocId}")

    User_Ref = db.collection('users').document(Data.StudentDocId)
    student_doc = User_Ref.get()

    # Verifica si el documento existe
    if not student_doc.exists:
        print("Documento del estudiante no encontrado.")
        raise HTTPException(status_code=404, detail="Student not found")
    
    # Accede a la subcolección 'reminders'
    Reminders_Ref = User_Ref.collection('reminders')
    reminders = Reminders_Ref.stream()

    reminders_list = []
    for reminder in reminders:
        reminder_data = reminder.to_dict()
        # Obtén el ID del profesor desde el recordatorio
        title= reminder_data.get('Title', '')
        professor_id = reminder_data.get('ProfessorID', '')
        content= reminder_data.get('Content', '')
        date=reminder_data.get('Date', '')
        print(f"ID DEL PROFE recibido: {professor_id}")
        print(f"Contenido: {content}")
        print(f"Fecha: {date}")


        # Si hay un ID de profesor, busca su documento
        professor_name = "Unknown"  # Valor por defecto si no se encuentra el profesor
        if professor_id:
            professor_ref = db.collection('users').document(professor_id)
            professor_doc = professor_ref.get()

            if professor_doc.exists:
                professor_name = professor_doc.to_dict().get('Name','')
        
        # Agrega el recordatorio al listado
        reminders_list.append(
            GetStudentReminds(
                Title=title,
                ProfessorName=professor_name,
                Content=content,
                Date=date
            )
        )
    

    # Si no se encontraron recordatorios
    if not reminders_list:
        raise HTTPException(status_code=404, detail="No reminders found for the given student")
    
    return reminders_list
