from fastapi import APIRouter
from Dataclases.Activities import *
from Firebase.firebase import db
from Services.Activities import ActivitiesService
from google.cloud import firestore

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
    Units_Colection=level_ref.collection("units")
    Unit_ref=Units_Colection.document(Data.UnitID)
    Activities_Collection=Unit_ref.collection("activities")
    Activity_ref=Activities_Collection.document(Data.ActivityID)
    Asigned_Colection=Group_Ref.collection("AsignedActivities")
    NAsigment={"Activity":Activity_ref}
    doc_ref = Asigned_Colection.add(NAsigment)
    return "Asignada correctamente"

@Activities.post("/AsiggnExam")
def AssignExam(Data:AsignExamPck):
    Group_Ref=db.collection("Groups").document(Data.GroupID)
    Group_Doc=Group_Ref.get()
    level_ref=Group_Doc.get("Level")
    Exam_Colection=level_ref.collection("exams")
    Exam_ref=Exam_Colection.document(Data.ExamID)
    Asigned_Colection=Group_Ref.collection("AsignedExams")
    NAsigment={"Activity":Exam_ref,"Tries":Data.Tries,"Date":Data.Date,"Min":Data.Minutes}
    Asigned_Colection.add(NAsigment)
    return "Asignada correctamente"

@Activities.post("/AsiggnPractice")
def AssignPractice(Data:AsignPracticePck):
    Teacher_ref=db.collection("users").document(Data.TeacherID)
    Student_ref=db.collection("users").document(Data.StudentID)
    Practice_ref=Teacher_ref.collection("Practices").document(Data.PracticeID)
    NAsigment={"Practice":Practice_ref}
    Asignment_Ref=Student_ref.collection("Practices").add(NAsigment)
    Teacher_ref.update({
        "AsignedPractices":firestore.ArrayUnion([Asignment_Ref[1]])
    })  
    return "Asignada correctamente"

@Activities.post("/GetAssignedActivities")
def GetAssignedActivities(Data:ActivityRequest):
    Asigned=ActivitiesService.GetAssignedActivities(Data.ID)
    return Asigned

@Activities.post("/GetAssignedExams")
def GetAssignedExams(Data:ActivityRequest):
    Asigned=ActivitiesService.GetAssignedExams(Data.ID)
    return Asigned

@Activities.post("/GetAssignedPractices")
def GetAssignedPractices(Data:ActivityRequest):
    Asigned=ActivitiesService.GetAssignedPractices(Data.ID)
    return Asigned