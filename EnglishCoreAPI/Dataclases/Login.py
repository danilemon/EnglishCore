from pydantic import BaseModel

class LoginAtemp(BaseModel):
    USR:int
    PSW:str

class RegisterAttemp(BaseModel):
    USR:int
    PSW:str
    Addres:str
    BDate:str
    Phone:int
    
