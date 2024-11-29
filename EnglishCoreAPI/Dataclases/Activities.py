from pydantic import BaseModel
from Dataclases.Teachers import StudetnsPreview

class ActivityRequest(BaseModel):
    ID:str

class ActivityPreview(BaseModel):
    Name:str
    ID:str

class Units(BaseModel):
    ID:str
    Name:str
    Activities:list

class PracticesPck(BaseModel):
    Students:list[StudetnsPreview]
    Practices:list[ActivityPreview]

class AsignActivityPck(BaseModel):
    GroupID:str
    UnitID:str
    ActivityID:str


class Activity(BaseModel):
    Name:str
    Level:int
    Topic:str
    Questions:list

class ClosedQuestion(BaseModel):
    Type:int
    Question:str| None
    HelpText:str| None
    Img:str| None
    Options:list| None
    Answer:int| None

class OpenQuestion(BaseModel):
    Type:int
    Question:str| None
    HelpText:str| None
    Img:str| None
    Answer:str| None

class CompleteText(BaseModel):
    Type:int
    Question:str
    HelpText:str| None
    Img:str| None
    Text:str| None
    Options:list| None
    Answers:list| None

