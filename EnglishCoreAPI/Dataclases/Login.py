from pydantic import BaseModel

class LoginRequest(BaseModel):
    username:str
    password:str

class RegisterAttemp(BaseModel):
    USR:int
    PSW:str
    Addres:str
    BDate:str
    Phone:int
    
