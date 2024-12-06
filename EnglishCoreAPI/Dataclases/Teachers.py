from pydantic import BaseModel

class GetGroupsRequest(BaseModel):
    ID:str

class Groups(BaseModel):
    ID:str
    Days:str
    Hours:str
    Level:int
    StartDate:str
    TeacherID:int

class StudetnsPreview(BaseModel):
    ID:str
    Name:str
class StudentInfo(BaseModel):
    ID:str
    Name:str
    Cellphone:str
    Adrress:str