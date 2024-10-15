from pydantic import BaseModel, constr, field_validator
from typing import Optional

class LoginRequest(BaseModel):
    username:str
    password:str

class RegisterAttemp(BaseModel):
    username:int
    password: str=constr(min_length=8)
    address:str
    birthday:str
    Phone:int=constr(regex=r'^\d{10}$')

    @field_validator('Phone')
    def phone_must_be_ten_digits(cls, v):
        if len(v) != 10:
            raise ValueError('El número de teléfono debe tener exactamente 10 dígitos')
        return v
    

    
