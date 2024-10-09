from fastapi import APIRouter
from Dataclases.Login import LoginAtemp,RegisterAttemp

Login=APIRouter()

@Login.post('/Login')
def LoginAtemp(Data:LoginAtemp):
    pass

@Login.post('/Register')
def registerAtemp(Data:RegisterAttemp):
    pass