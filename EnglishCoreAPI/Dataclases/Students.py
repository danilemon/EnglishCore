from pydantic import BaseModel

class GetStudentDataRequest(BaseModel):
    StudentID:int

class StudentData(BaseModel):
    StudentID:int
    Name:str
    LastName:str
    Phone:str
    Address:str
    Birthday:str
    Username:str
    Group: int