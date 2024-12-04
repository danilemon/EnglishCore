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
    Title: str
    ProfessorName: str
    Date: str

class GetStudentTickets(BaseModel):
    TicketID: int
    Date: str
    Description: str
    ImageURL: str
