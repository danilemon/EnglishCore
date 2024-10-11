from fastapi import APIRouter
from Dataclases.Login import LoginAtemp,RegisterAttemp

Login=APIRouter()

@Login.post('/Login')
def LoginAtemp(Data:LoginAtemp):
    users_ref = db.collection('users')
    query_ref = users_ref.where('username', '==', data.username).limit(1).get()

    if not query_ref:
        raise HTTPException(status_code=404, detail="Usuario no encontrado")

    # Verifica la contraseña
    user_data = query_ref[0].to_dict()
    
    if user_data['password'] != data.password:
        raise HTTPException(status_code=400, detail="Contraseña incorrecta")

    return {"message": "Inicio de sesión exitoso", "user": user_data}

@Login.post('/Register')
def registerAtemp(Data:RegisterAttemp):
    pass