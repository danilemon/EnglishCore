from Dataclases.Activities import ActivityPreview,Units
from Dataclases.Teachers import StudetnsPreview
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
            Unit_S=Units(ID=unit.id,Name=unit_data['Name'],Activities=[])
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
        Exam_ref=Level_Ref.collection("exams")
        Exams_Docs=Exam_ref.stream()
        ExamsList=[]
        for exam in Exams_Docs:
            Exam_Data=exam.to_dict()
            Exm=ActivityPreview(Name=Exam_Data["Nombre"],ID=exam.id)
            ExamsList.append(Exm)
        return(ExamsList)
    
    def GetTeacherStudents(ID:str):
        User_ref=db.collection('users').document(ID)
        User_doc=User_ref.get()
        Groups_ref=User_doc.get("Groups")
        StudentDic={}
        for Group in Groups_ref:
            Group_Doc=Group.get()
            Students=Group_Doc.get('StudentsIDs')
            for Student_Doc in Students:
                Student_Data=Student_Doc.get().to_dict()
                Student=StudetnsPreview(ID=Student_Doc.get().id,Name=Student_Data.get('Name')+" "+Student_Data.get('LastName'))
                if Student_Doc.id not in StudentDic:
                    StudentDic[Student_Doc.id]=Student
        FinalList=[]
        for StudentOBJ in StudentDic.values():
            FinalList.append(StudentOBJ)
        return(FinalList)
    
    def GetTeacherPractices(ID:str):
        User_ref=db.collection('users').document(ID)
        Practices_Ref=User_ref.collection("Practices")
        Practice_List=[]
        for Practice in Practices_Ref.stream():
            Practice_Data=Practice.to_dict()
            Practice=ActivityPreview(ID=Practice.id,Name=Practice_Data["Nombre"])
            Practice_List.append(Practice)
        return(Practice_List)
        