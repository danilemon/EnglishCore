from pydantic import BaseModel, constr, field_validator,Field


class LoginRequest(BaseModel):
    username:str
    password:str

class RegisterRequest(BaseModel):
    username:str
    password: str=constr(min_length=8)
    adrees:str
    birthday:str
    phone:str=Field(pattern=r'^\d{10}$')


    

    
