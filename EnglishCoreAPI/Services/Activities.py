from Dataclases.Activities import ActivityPreview,Units
from Firebase.firebase import db
class ActivitiesService:
    def GetGroupActivities(ID:str):
        Group_ref= db.collection("Groups").document(ID)
        Group_Doc=Group_ref.get()
        Level_Ref=Group_Doc.get("Level")
        Units_ref=Level_Ref.collection("units")
        Units_Docs=Units_ref.stream()
        UnitsList=[]
        for unit in Units_Docs:
            unit_data=unit.to_dict()
           
            Unit_S=Units(Name=unit_data['Name'],Activities=[])
            unit_id = unit.id  # ID del documento actual
            unit_ref = Units_ref.document(unit_id)
            activities_ref = unit_ref.collection('activities')
            activities_docs = activities_ref.stream()
            for activity in activities_docs:
                Act_Data=activity.to_dict()
                Act=ActivityPreview(Name=Act_Data['Nombre'],ID=activity.id)
                Unit_S.Activities.append(Act)
            UnitsList.append(Unit_S)
        return(UnitsList)
    
    def GetGroupExams(ID:str):
        Group_ref= db.collection("Groups").document(ID)
        Group_Doc=Group_ref.get()
        Level_Ref=Group_Doc.get("Level")
        Exam_ref=Level_Ref.collection("Exams")
        Exams_Docs=Exam_ref.stream()
        ExamsList=[]
        for exam in Exams_Docs:
            Exam_Data=exam.to_dict()
            Exm=ActivityPreview(Exam_Data["Nombre"],ID=exam.id)
            ExamsList.append(Exm)
        return(ExamsList)


        



    