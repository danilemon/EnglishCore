from pydantic import BaseModel
from Dataclases.Teachers import StudetnsPreview
from typing import Any



#Request
class ActivityRequest(BaseModel):
    ID:str


class GetActivityAnwersPck(BaseModel):
    GroupID:str
    ActID:str

#Previews
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
    UnitID:str=""
    ActivityID:str

class AsignExamPck(BaseModel):
    GroupID:str
    ExamID:str
    Minutes:int
    Tries:int
    Date:str

class AsignPracticePck(BaseModel):
    TeacherID:str
    StudentID:str
    PracticeID:str


#Views
class UnitViews(BaseModel):
    Unit:int
    Name:str
    ID:str
    Acts:list

class AsignedView(BaseModel):
    Act:ActivityPreview
    HasAnswers:bool


#Wrapers para actividades

class Activity(BaseModel):
    ID:str
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


#Actividades ya respondidas
class ActivityAnswer(BaseModel):
    Type:int
    value:Any
    Correct:bool

class StudentAnswers(BaseModel):
    ID:str
    student:StudetnsPreview
    Answers:list[ActivityAnswer]

class AnsweredActivity(BaseModel):
    Act:Activity
    StudentsAnswers:list[StudentAnswers]