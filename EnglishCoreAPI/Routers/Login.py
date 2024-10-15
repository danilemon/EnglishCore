from fastapi import APIRouter,HTTPException
from Dataclases.Login import LoginRequest,RegisterAttemp
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

    return {"message": "Inicio de sesión exitoso", "user": user_data}

@Login.post('/Register')
def registerAtemp(Data:RegisterAttemp):
    users_ref = db.collection('users').document
    query_ref = users_ref.where('username', '==', Data.username).limit(1).get()
    if not query_ref:
        raise HTTPException(status_code=404, detail="Usuario no encontrado")
    
    user_doc = query_ref[0]

    user_doc.update({
        'Phone':Data.Phone,
        'address':Data.address,
        'birthday':Data.birthday,
        'password':Data.password
    })
    return {"message": "Usuario actualizado correctamente"}