from typing import Dict, List
from fastapi import APIRouter, HTTPException, UploadFile, Form
from Dataclases.Students import GetStudentDataRequest, StudentData, GetStudentReminds, GetStudentTickets, UploadTickets
from Firebase.firebase import db, storage
from datetime import datetime
import uuid
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
        date=reminder_data.get('Date', '')
        print(f"ID DEL PROFE recibido: {professor_id}")
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
                Date=date
            )
        )
    

    # Si no se encontraron recordatorios
    if not reminders_list:
        raise HTTPException(status_code=404, detail="No reminders found for the given student")
    
    return reminders_list

@StudentsR.post('/GetStudentTickets', response_model=List[GetStudentTickets])
def fetch_student_tickets(Data: GetStudentDataRequest):
    # Accede al documento del usuario
    print(f"StudentDocId recibido: {Data.StudentDocId}")

    User_Ref = db.collection('users').document(Data.StudentDocId)
    student_doc = User_Ref.get()

    # Verifica si el documento existe
    if not student_doc.exists:
        print("Documento del estudiante no encontrado.")
        raise HTTPException(status_code=404, detail="Student not found")
    
    # Accede a la subcolección 'reminders'
    Tickets_Ref = User_Ref.collection('tickets')
    tickets = Tickets_Ref.stream()

    tickets_list = []
    for ticket in tickets:
        ticket_data = ticket.to_dict()
        # Obtén el ID del profesor desde el recordatorio
        ticketid= ticket_data.get('TicketID', '')
        description = ticket_data.get('Description', '')
        date=ticket_data.get('Date', '')
        imageurl=ticket_data.get('ImageURL','')
        print(f"Fecha: {date}")

        
        # Agrega el recordatorio al listado
        tickets_list.append(
            GetStudentTickets(
                TicketID=ticketid,
                Date=date,
                Description=description,
                ImageURL=imageurl
            )
        )
    

    # Si no se encontraron recordatorios
    if not tickets_list:
        raise HTTPException(status_code=404, detail="No tickets found for the given student")
    
    return tickets_list

@StudentsR.post('/UploadStudentTickets')
async def upload_student_ticket(
    StudentDocId: str = Form(...),  # Parámetro para el ID del estudiante
    Description: str = Form(...),  # Descripción del ticket
    ImageFile: UploadFile = UploadFile(...),  # Archivo del ticket
):
    try:
        # Validar que el estudiante existe
        user_ref = db.collection('users').document(StudentDocId)
        student_doc = user_ref.get()

        if not student_doc.exists:
            raise HTTPException(status_code=404, detail="Student not found")

        # Subir imagen a Firebase Storage
        public_url = await upload_image_to_firebase(StudentDocId, ImageFile)

        # Generar datos del ticket
        ticket_data = {
            'TicketID': str(uuid.uuid4()),  # Generar un UUID único
            'Date': datetime.now().isoformat(),
            'Description': Description,
            'ImageURL': public_url,
        }

        # Añadir el ticket a la subcolección del usuario
        user_ref.collection('tickets').add(ticket_data)


        return {
            "message": "Ticket uploaded successfully",
            **ticket_data
        }

    except Exception as e:
        raise HTTPException(status_code=500, detail=f"Error uploading ticket: {str(e)}")


# Función para subir la imagen a Firebase Storage
async def upload_image_to_firebase(student_id: str, image_file: UploadFile) -> str:
    try:
        # Crear un nombre único para el archivo
        file_extension = image_file.filename.split('.')[-1]
        unique_filename = f"tickets/{student_id}/{uuid.uuid4()}.{file_extension}"

        # Leer el contenido del archivo
        contents = await image_file.read()

        # Subir el archivo a Firebase Storage
        bucket = storage.bucket()
        blob = bucket.blob(unique_filename)
        blob.upload_from_string(contents, content_type=image_file.content_type)

        # Hacer público el archivo
        blob.make_public()

        return blob.public_url

    except Exception as e:
        raise HTTPException(status_code=500, detail=f"Error uploading image: {str(e)}")