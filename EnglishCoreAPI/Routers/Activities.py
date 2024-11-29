from fastapi import APIRouter
from Dataclases.Activities import *
from Firebase.firebase import db
from Services.Activities import ActivitiesService

Activities=APIRouter()

@Activities.post('/GetActivity')
def GetActivity(Data:ActivityRequest):
    Activity_Ref=db.collection("Activdades").document("oGvaIR55gzsaltnfBSHX")
    Questions=Activity_Ref.collection("Preguntas").get()
    QuestionsList=[]
    for Question in Questions:
        Question_Data=Question.to_dict()
        match Question_Data["Tipo"]:
            case 1:
                QuestionsList.append(ClosedQuestionCase(Question_Data))
            case 2:
                QuestionsList.append(OpenQuestionCase(Question_Data))
            case 3:
                QuestionsList.append(CompleteTextCase(Question_Data))
    Data=Activity_Ref.get().to_dict()
    Final_Activity=Activity(Name=Data["Nombre"],Level=Data["Nivel"],Topic=Data["Tema"],Questions=QuestionsList)
    print(Data)
    return Final_Activity

def ClosedQuestionCase(Data):
    return ClosedQuestion(Type=1,Question=Data.get("Pregunta",None),HelpText=Data.get("TextoSecundario",None),Img=Data.get("Imagen",None),Answer=Data.get("Respuesta",None),Options=Data.get("Incisos",None))

def OpenQuestionCase(Data):
    return OpenQuestion(Type=2,Question=Data.get("Pregunta",None),HelpText=Data.get("TextoSecundario",None),Img=Data.get("Imagen",None),Answer=Data.get("Respuesta",None))

def CompleteTextCase(Data):
    return CompleteText(Type=3,Question=Data.get("Pregunta",None),HelpText=Data.get("TextoSecundario",None),Img=Data.get("Imagen",None),Text=Data.get("TextoAcompletar",None),Options=Data.get("Options",None),Answers=Data.get("Respuesta",None))

@Activities.post("/GetGroupActivities")
def GetGroupActs(Data:list[ActivityRequest]):
    ReturnList=[]
    for Group in Data:
        Units=ActivitiesService.GetGroupActivities(Group.ID)
        ReturnList.append(Units)
    return ReturnList

@Activities.post("/GetGroupExams")
def GetGroupExms(Data:list[ActivityRequest]):
    ReturnList=[]
    for Group in Data:
        Units=ActivitiesService.GetGroupExams(Group.ID)
        ReturnList.append(Units)
    return ReturnList

@Activities.post("/GetPractices")
def GetPractices(Data:ActivityRequest):
    Practices=ActivitiesService.GetTeacherPractices(Data.ID)
    Students=ActivitiesService.GetTeacherStudents(Data.ID)
    return(PracticesPck(Students=Students,Practices=Practices))

@Activities.post("/AsiggnActivity")
def AssignActivity(Data:AsignActivityPck):
    Group_Ref=db.collection("Groups").document(Data.GroupID)
    Group_Doc=Group_Ref.get()
    level_ref=Group_Doc.get("Level")
    
    pass