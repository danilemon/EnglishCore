from pydantic import BaseModel
from fastapi import UploadFile  # or use a specialized type


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
    GroupID:str

class GetStudentReminds(BaseModel):
    Title: str
    ProfessorName: str
    Date: str

class GetStudentTickets(BaseModel):
    TicketID: int
    Date: str
    Description: str
    ImageURL: str

class UploadTickets(BaseModel):
    ImageFile: UploadFile
    ImageURL: str
    TicketID: int
    Date: str
    Description: str
