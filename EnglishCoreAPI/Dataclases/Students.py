from pydantic import BaseModel

class GetStudentDataRequest(BaseModel):
    StudentDocId: str

class StudentData(BaseModel):
    StudentID:int
    Name:str
    LastName:str
    Phone:str
    Address:str
    Birthday:str
    Username:str

class GetStudentReminds(BaseModel):
    ProfessorName: str
    RemindContent: str
    RemindDate: str
