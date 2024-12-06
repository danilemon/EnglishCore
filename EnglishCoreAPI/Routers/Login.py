from fastapi import APIRouter,HTTPException
from Dataclases.Login import LoginRequest,RegisterRequest
from Firebase.firebase import db

Login=APIRouter()

@Login.post('/Login')
def LoginAtemp(Data: LoginRequest):
    users_ref = db.collection('users')
    query_ref = users_ref.where('username', '==', Data.username).limit(1).get()

    if not query_ref:
        raise HTTPException(status_code=404, detail="Usuario no encontrado")

    # Obtén los datos del usuario y su ID de documento
    user_doc = query_ref[0]  # Primer resultado de la consulta
    user_data = user_doc.to_dict()  # Datos del usuario
    user_doc_id = user_doc.id  # ID del documento
    print(f"ID del documento: {user_doc_id}")

    # Verifica la contraseña
    if user_data['password'] != Data.password:
        raise HTTPException(status_code=400, detail="Contraseña incorrecta")

    # Retorna la respuesta con el ID del documento
    if user_data['IsStudent'] == True:

        return {
            "success": True,
            "message": "Inicio de sesión exitoso",
            "isStudent": True,
            "userDocId": user_doc_id
        }
    else:
        return {
            "success": True,
            "message": "Inicio de sesión exitoso",
            "isStudent": False,
            "userDocId": user_doc_id
        }

       

@Login.post('/Register')
def registerAtemp(Data:RegisterRequest):
    users_ref = db.collection('users')
    query_ref = users_ref.where('username', '==', Data.username).limit(1).get()
    if not query_ref:
        raise HTTPException(status_code=404, detail="Usuario no encontrado")
    
    user_doc = query_ref[0]
    data=user_doc.to_dict()
    if data['password'] != "":
        raise HTTPException(status_code=400, detail="Usuario ya registrado")

    doc_ref = user_doc.reference

    doc_ref.update({
    'Phone': Data.phone,
    'address': Data.adrees,
    'birthday': Data.birthday,
    'password': Data.password
    })
    return {"message": "Usuario actualizado correctamente"}
