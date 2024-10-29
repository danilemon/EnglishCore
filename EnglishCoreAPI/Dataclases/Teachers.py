from pydantic import BaseModel

class GetGroupsRequest(BaseModel):
    ID:int

class Groups(BaseModel):
    ID:int
    Days:str
    Hours:str
    Level:int
    StartDate:str
    TeacherID:int

class StudetnsPreview(BaseModel):
    ID:int
    Name:str