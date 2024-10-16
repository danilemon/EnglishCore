from fastapi import APIRouter,HTTPException
from Dataclases.Login import LoginRequest,RegisterRequest
from Firebase.firebase import db

Login=APIRouter()

@Login.post('/Login')
def LoginAtemp(Data:LoginRequest):
    users_ref = db.collection('users')
    query_ref = users_ref.where('username', '==', Data.username).limit(1).get()

    if not query_ref:
        raise HTTPException(status_code=404, detail="Usuario no encontrado")

    # Verifica la contraseña
    user_data = query_ref[0].to_dict()
    
    if user_data['password'] != Data.password:
        raise HTTPException(status_code=400, detail="Contraseña incorrecta")
    
    if user_data['IsStudent']==True:
        return {"success":True,"message": "Inicio de sesión exitoso","isStudent":True}
    else:
        return {"success":True,"message": "Inicio de sesión exitoso","isStudent":False}

@Login.post('/Register')
def registerAtemp(Data:RegisterRequest):
    users_ref = db.collection('users')
    query_ref = users_ref.where('username', '==', Data.username).limit(1).get()
    if not query_ref:
        raise HTTPException(status_code=404, detail="Usuario no encontrado")
    
    user_doc = query_ref[0]

    doc_ref = user_doc.reference


    doc_ref.update({
    'Phone': Data.phone,
    'address': Data.adrees,
    'birthday': Data.birthday,
    'password': Data.password
    })
    return {"message": "Usuario actualizado correctamente"}