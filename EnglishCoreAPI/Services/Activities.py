from Dataclases.Activities import *
from Dataclases.Teachers import StudetnsPreview
from Dataclases.Teachers import StudetnsPreview
from Firebase.firebase import db
from google.cloud.firestore import DocumentReference
class ActivitiesService:

    def GetActivity(Activity_Ref:DocumentReference):
        Questions=Activity_Ref.collection("Preguntas").order_by("Index").get()
        QuestionsList=[]
        for Question in Questions:
            Question_Data=Question.to_dict()
            match Question_Data["Tipo"]:
                case 1:
                    QuestionsList.append(ActivitiesService.OpenQuestionCase(Question_Data))
                case 2:
                    QuestionsList.append(ActivitiesService.ClosedQuestionCase(Question_Data))
                case 3:
                    QuestionsList.append(ActivitiesService.CompleteTextCase(Question_Data))
        Data=Activity_Ref.get().to_dict()
        Final_Activity=Activity(ID="",Name=Data["Nombre"],Level=Data["Nivel"],Topic=Data["Tema"],Questions=QuestionsList)
        print(Data)
        return Final_Activity
    
    def GetAnswers(Answers):
        StudentsActs=[]
        for AnswerDoc in Answers:
            Student_Doc=AnswerDoc.get("User")
            AnsList=AnswerDoc.get("Answers")
            Student_Data=Student_Doc.get()
            UserP=StudetnsPreview(ID=Student_Doc.id,Name=Student_Data.get('Name')+" "+Student_Data.get('LastName'))
            AnswerList=[]
            for Ans in AnsList:
                data=next(iter(Ans.values()))
                Response=ActivityAnswer(Type=data["Type"],value=data["Value"],Correct=data["Correct"])
                AnswerList.append(Response)
            Student_act=StudentAnswers(ID=AnswerDoc.id,student=UserP,Answers=AnswerList)
            StudentsActs.append(Student_act)
        return StudentsActs

    def OpenQuestionCase(Data):
        return OpenQuestion(Type=1,Question=Data.get("Pregunta",None),HelpText=Data.get("TextoSecundario",""),Img=Data.get("Imagen",""),Answer=Data.get("Respuesta",None))

    def ClosedQuestionCase(Data):
        return ClosedQuestion(Type=2,Question=Data.get("Pregunta",""),HelpText=Data.get("TextoSecundario",""),Img=Data.get("Imagen",""),Answer=Data.get("Respuesta",None),Options=Data.get("Incisos",[]),TrueFalse=Data.get("TrueFalse",False))

    def CompleteTextCase(Data):
        sets=Data.get("MultipleSets",[])
        return CompleteText(Type=3,Question=Data.get("Pregunta",""),HelpText=Data.get("TextoSecundario",""),Img=Data.get("Imagen",""),Text=Data.get("TextoAcompletar",""),Options=Data.get("Options",[]),Answers=Data.get("Answers",[]),MultipleSets=sets,NoRep=Data.get("NoRep",False))


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
    
    def GetAssignedActivities(ID:str):
        Group_ref=db.collection("Groups").document(ID)
        Asigned_Acts_ref=Group_ref.collection("AsignedActivities")
        AsiggnedActs={}
        for act in Asigned_Acts_ref.stream():
            data=act.to_dict()
            Act=data["Activity"].get().to_dict()
            Act_ref=data["Activity"]
            Unit_Ref=Act_ref.parent.parent
            Collection_ref=act.reference.collection("Answers")
            if Unit_Ref.id in AsiggnedActs:
                Unit=AsiggnedActs[Unit_Ref.id]
            else:
                Unit_Doc= Unit_Ref.get()
                Unit=UnitViews(Name=Unit_Doc.get("Name"),Unit=Unit_Doc.get("Unit"),ID=Unit_Doc.id,Acts=[]) 
                AsiggnedActs[Unit.ID]=Unit
            if any( Collection_ref.limit(1).stream()):
                ActPre=AsignedView(HasAnswers=True,Act=ActivityPreview(Name=Act.get("Nombre"),ID=act.id))
            else:
                ActPre=AsignedView(HasAnswers=False,Act=ActivityPreview(Name=Act.get("Nombre"),ID=act.id))
            Unit.Acts.append(ActPre)
        return list(AsiggnedActs.values())
    
    def GetAssignedExams(ID:str):
        Group_ref=db.collection("Groups").document(ID)
        Exams_ref=Group_ref.collection("AsignedExams")
        AsiggnedExams=[]
        for Exam in Exams_ref.stream():
            data=Exam.to_dict()
            Exam_doc=data["Activity"].get()
            Collection_ref=Exam.reference.collection("Answers")
            if any( Collection_ref.limit(1).stream()):
                ExamPre=AsignedView(HasAnswers=True,Act=ActivityPreview(Name=Exam_doc.get("Nombre"),ID=Exam.id))
            else:
                ExamPre=AsignedView(HasAnswers=False,Act=ActivityPreview(Name=Exam_doc.get("Nombre"),ID=Exam.id))
            AsiggnedExams.append(ExamPre)
        return AsiggnedExams
    
    def GetAssignedPractices(ID:str):
        User_Doc=db.collection("users").document(ID).get()
        Assigned=User_Doc.get("AsignedPractices")
        AssignedPractices=[]
        if(Assigned is None):
            return AssignedPractices
        for Practice in Assigned:
            Practice_Doc=Practice.get()
            Act=Practice_Doc.get("Practice").get().to_dict()
            Collection_ref=Practice.collection("Answers")
            if any( Collection_ref.limit(1).stream()):
                PracPre=AsignedView(HasAnswers=True,Act=ActivityPreview(Name=Act.get("Nombre"),ID=Practice_Doc.id))
            else:
                PracPre=AsignedView(HasAnswers=False,Act=ActivityPreview(Name=Act.get("Nombre"),ID=Practice_Doc.id))
            AssignedPractices.append(PracPre)
        return AssignedPractices
            
