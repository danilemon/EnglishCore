from fastapi import APIRouter
from Dataclases.Activities import *
from Firebase.firebase import db
from Services.Activities import ActivitiesService
from google.cloud import firestore

Activities=APIRouter()

@Activities.post('/GetActivity')
def GetActivity(Data:ActivityRequest):
    pass

@Activities.post("/GetAnsweredActivity")
def GetAnsweredActivity(Data:GetActivityAnwersPck):
    Group_Ref=db.collection("Groups").document(Data.GroupID)
    Collection_ref=Group_Ref.collection("AsignedActivities")
    AsignedAct_Doc=Collection_ref.document(Data.ActID).get()
    Act_Ref=AsignedAct_Doc.get("Activity")
    ModeldAct=ActivitiesService.GetActivity(Act_Ref)
    Answers_Colecion=Collection_ref.document(Data.ActID).collection("Answers")
    StudentsAnswers=ActivitiesService.GetAnswers(list(Answers_Colecion.stream()))
    Response=AnsweredActivity(Act=ModeldAct,StudentsAnswers=StudentsAnswers)
    return Response


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