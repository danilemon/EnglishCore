from firebase import db
def agregar_usuario(nombre, apellido, telefono, direccion, fecha_nacimiento, usuario, password, id_grupo):
    db.collection("users").add({
        "IsStudent": True,
        "LastName": apellido,
        "Name": nombre,
        "Phone": telefono,
        "address": direccion,
        "birthday": fecha_nacimiento,
        "password": password,
        "username": usuario,
        "Group id": id_grupo
    })

def update_students_references():
    # Obtiene todos los grupos de la colección 'Groups'
    groups_ref = db.collection('Groups')
    groups_docs = groups_ref.stream()

    for group_doc in groups_docs:
        group_data = group_doc.to_dict()
        
        # Verifica que exista el campo 'StudentsIDs' en el grupo
        if 'StudentsIDs' in group_data:
            new_student_references = []
            
            for student_id in group_data['StudentsIDs']:
                # Busca el usuario correspondiente en la colección 'users'
                users_query = db.collection('users').where('IsStudent', '==', True).where('StudentID', '==', student_id).limit(1)
                user_docs = users_query.stream()
                
                # Verifica si encontró un usuario
                user_ref = None
                for user_doc in user_docs:
                    user_ref = user_doc.reference  # Obtiene la referencia al documento
                    break
                
                if user_ref:
                    new_student_references.append(user_ref)  # Agrega la referencia al nuevo arreglo
            
            # Actualiza el documento del grupo con las referencias
            groups_ref.document(group_doc.id).update({
                'StudentsIDs': new_student_references
            })
            print(f"Grupo {group_doc.id} actualizado con referencias a los estudiantes.")

def update_group_references_for_students():
    # Obtiene todos los usuarios de la colección 'users' que son estudiantes (IsStudent == True)
    users_query = db.collection('users').where('IsStudent', '==', True)
    users_docs = users_query.stream()

    for user_doc in users_docs:
        user_data = user_doc.to_dict()
        
        # Verifica que el usuario tenga un campo 'GroupID'
        if 'GroupID' in user_data:
            group_id = user_data['GroupID']
            
            # Busca el grupo correspondiente en la colección 'Groups'
            groups_query = db.collection('Groups').where('ID', '==', group_id).limit(1)
            group_docs = groups_query.stream()
            
            # Verifica si encontró un grupo
            group_ref = None
            for group_doc in group_docs:
                group_ref = group_doc.reference  # Obtiene la referencia al documento
                break
            
            if group_ref:
                # Actualiza el campo 'GroupID' en el documento del usuario con la referencia al grupo
                db.collection('users').document(user_doc.id).update({
                    'GroupID': group_ref
                })
                print(f"Usuario {user_doc.id} actualizado con referencia al grupo {group_id}.")

def update_group_references_for_students(skip_first=True):
    # Obtiene todos los usuarios de la colección 'users' que son estudiantes (IsStudent == True)
    users_query = db.collection('users').where('IsStudent', '==', True)
    users_docs = users_query.stream()

    first_document_skipped = False  # Bandera para controlar el salto del primer documento

    for user_doc in users_docs:
        # Si estamos saltando el primer documento, lo hacemos aquí
        if skip_first and not first_document_skipped:
            first_document_skipped = True
            continue  # Salta al siguiente documento

        user_data = user_doc.to_dict()
        
        # Verifica que el usuario tenga un campo 'GroupID'
        if 'GroupID' in user_data:
            group_id = user_data['GroupID']
            
            # Busca el grupo correspondiente en la colección 'Groups'
            groups_query = db.collection('Groups').where('ID', '==', group_id).limit(1)
            group_docs = groups_query.stream()
            
            # Verifica si encontró un grupo
            group_ref = None
            for group_doc in group_docs:
                group_ref = group_doc.reference  # Obtiene la referencia al documento
                break
            
            if group_ref:
                # Actualiza el campo 'GroupID' en el documento del usuario con la referencia al grupo
                db.collection('users').document(user_doc.id).update({
                    'GroupID': group_ref
                })
                print(f"Usuario {user_doc.id} actualizado con referencia al grupo {group_id}.")


def agregar_activities_a_units():
    # Accede a la colección 'Levels'
    levels_ref = db.collection("Levels")
    levels_docs = levels_ref.stream()

    # Recorre cada documento en 'Levels'
    for level_doc in levels_docs:
        print(f"Procesando Level: {level_doc.id}")
        
        # Accede a la subcolección 'units' de cada documento
        units_ref = levels_ref.document(level_doc.id).collection("units")
        units_docs = units_ref.stream()

        # Recorre cada documento en 'units'
        for unit_doc in units_docs:
            print(f"  Procesando Unit: {unit_doc.id}")
            
            # Agrega la colección 'Activities' dentro de cada 'unit'
            activities_ref = units_ref.document(unit_doc.id).collection("Activities")
            activities_ref.add({
                "activity_name": "Example Activity"
            })

        
            print(f"    Colección 'Activities' agregada a Unit: {unit_doc.id}")

def populate_levels_with_activity_data():

    # Referencia al documento único en la colección "activities"
    activity_doc_ref = db.collection('Activdades').document('oGvaIR55gzsaltnfBSHX')
    activity_data = activity_doc_ref.get()

    # Verificar que el documento existe
    if not activity_data.exists:
        print("El documento 'unique_activity' en 'activities' no existe.")
        return

    # Convertir a diccionario para manipular
    activity_data = activity_data.to_dict()

    # Iterar sobre los documentos en la colección "Levels"
    levels_ref = db.collection('Levels')
    levels_docs = levels_ref.stream()

    for level_doc in levels_docs:
        level_id = level_doc.id  # ID del documento en la colección "Levels"

        # Colección "exams" dentro de cada documento de "Levels"
        exams_ref = levels_ref.document(level_id).collection('exams')

        # Crear tres documentos en "exams" con datos de "activities"
        for i in range(1, 4):
            exam_name = f"Exam {i}"
            exams_ref.add({
                **activity_data,  # Copia los campos de activities
                'Nombre': exam_name  # Sobrescribe el campo 'Nombre'
            })

        # Colección "units" dentro de cada documento de "Levels"
        units_ref = levels_ref.document(level_id).collection('units')
        units_docs = units_ref.stream()

        for unit_doc in units_docs:
            unit_id = unit_doc.id  # ID del documento en "units"

            # Colección "activities" dentro de cada documento de "units"
            unit_activities_ref = units_ref.document(unit_id).collection('activities')

            # Crear tres documentos en "activities" con datos de "activities"
            for i in range(1, 4):
                activity_name = f"Actividad {i}"
                unit_activities_ref.add({
                    **activity_data,  # Copia los campos de activities
                    'Nombre': activity_name  # Sobrescribe el campo 'Nombre'
                })

    print("Se completó la creación de documentos en 'exams' y 'activities'.")

def AgregarRespuestas():
    Group_ref=db.collection("Groups").document("HMmvyGl9pDHaZCHLDUMQ")
    Answer=Group_ref.collection("AsignedActivities").document("185aPlUm25MHRr20BQvd").collection("Answers").document("Un8DXcmSVIGVMnVJnYgo")
    Answer.update({
        "Answers":[{"1":{"Type":1,"Correct":True,"Value":1}},
                    {"2":{"Type":2,"Correct":False,"Value":["Runing","Playing","Saw"]}},
                    {"3":{"Type":3,"Correct":True,"Value":"Run"}}
        ]
    })
    print(Answer)



def AgregarExamen():
    Level_ref=db.collection("Levels").document("level1")
    Exams=Level_ref.collection("exams")
    Exam_ref=Exams.document("YecnDb9argBmphq22GbT")
    collection_ref=Exam_ref.collection("Preguntas")
    Doc={
        "Index":1,
        "Tipo":2,
        "Pregunta":"Read and choose the correct words.",
        "TextoSecundario":"Charlie isn't tall. He's",
        "Incisos":["pretty","short","heavy"],
        "Respuesta":1,
    }
    doc_ref = collection_ref.add(Doc)
    Doc={
        "Index":2,
        "Tipo":2,
        "Pregunta":"Read and choose the correct words.",
        "TextoSecundario":"Javier is good-looking. He is",
        "Incisos":["pretty","talkative","shy"],
        "Respuesta":0,
    } 
    doc_ref = collection_ref.add(Doc)
    
    Doc={
        "Index":3,
        "Tipo":2,
        "Pregunta":"Read and choose the correct words.",
        "TextoSecundario":"Amanda isn't very serious. she is",
        "Incisos":["short","pretty","funny"],
        "Respuesta":2,
    }
    doc_ref = collection_ref.add(Doc)
    Doc={
        "Index":4,
        "Tipo":2,
        "Pregunta":"Read and choose the correct words.",
        "TextoSecundario":"My brother isn't shy. He is",
        "Incisos":["fiendly","thin","friendly"],
        "Respuesta":0,
    }
    doc_ref = collection_ref.add(Doc)
    Doc={
        "Index":5,
        "Tipo":2,
        "Pregunta":"Read and choose the correct words.",
        "TextoSecundario":"My mother talks a lot. She's",
        "Incisos":["talkative","pretty","heavy"],
        "Respuesta":0,
    }

    
    
    Doc={
        "Index":6,
        "Tipo":2,
        "Pregunta":"Complete with thw right response",
        "TextoSecundario":"See you in a bit.",
        "Incisos": ["Bye-Bye", "Very good, how about you, Mr Javier?", "Good afternoon"],
        "Respuesta":1,
    }
    doc_ref = collection_ref.add(Doc)
    Doc={
        "Index":7,
        "Tipo":2,
        "Pregunta":"Complete with thw right response",
        "TextoSecundario":"Have a good weekend.",
        "Incisos": [" Thaks, you too", "Nice to meet you, too", "I'm OK. Thanks"],
        "Respuesta":0,
    }
    doc_ref = collection_ref.add(Doc)
    Doc={
        "Index":8,
        "Tipo":2,
        "Pregunta":"Complete with thw right response",
        "TextoSecundario":" Hello, how are you?",
        "Incisos": ["Good morning, I'm ok.", "Nothing much, and you?", "See you next weekend"],
        "Respuesta":1,
    }
    doc_ref = collection_ref.add(Doc)
    Doc={
        "Index":9,
        "Tipo":2,
        "Pregunta":"Complete with thw right response",
        "TextoSecundario":"See you next week!",
        "Incisos": [" I'm fine, how about you?", " Nice to meet you, too.", "Good bye."],
        "Respuesta":2,
    }
    doc_ref = collection_ref.add(Doc)
    Doc={
        "Index":10,
        "Tipo":2,
        "Pregunta":"Complete with thw right response",
        "TextoSecundario":"Hello",
        "Incisos": ["Bye-Bye", "I'm OK, thanks", "Good evening."],
        "Respuesta":2,
    }
    doc_ref = collection_ref.add(Doc)

    Doc={
        "Index":15,
        "Tipo":0,
        "Text":"",
    }
    Doc={
        "Index":11,
        "Tipo":3,
        "Pregunta":"Complete the conversations. Select the correct words",
        "TextoAcompletar":"""A: What is {}?
B: {} my new laptop.
""",    "MultipleSets":[3,3],
        "Options":["this","you","these","it is","these are","they're",],
        "Answers":["this","it is"]
    }
    doc_ref = collection_ref.add(Doc)
    Doc={
        "Index":12,
        "Tipo":3,
        "Pregunta":"Complete the conversations. Select the correct words",
        "TextoAcompletar":"""A: are {}your pens?
B: Yes, they {} Thank you.""",
        "MultipleSets":[3,3],
        "Options":["these","this","it","is","are","its"],
        "Answers":["this","are"]

    }
    doc_ref = collection_ref.add(Doc)
    Doc={
        "Index":13,
        "Tipo":3,
        "Pregunta":"Complete the conversations. Select the correct words",
        "TextoAcompletar":"""A: Is {} a map?
B: No, {} is not. It is a newspaper.""",
        "MultipleSets":[3,3],
        "Options":["this","its","these","is","are","it"],
        "Answers":["this","it"]
    }
    doc_ref = collection_ref.add(Doc)



    
    Doc={
        "Index":14,
        "Tipo":3,
        "Pregunta":"Look at the image. Complete the sentenses using the words in the box",
        "TextoAcompletar":"""1. The briefcase is {} the newspaper.
2. The newspaper is {} the books.
3. The cellphone is {} the briefcase.
4. The keys are {} the newspaper.
""","Options":["on","next to","under","in front of"],
        "Answers":["next to","under","on","in front of"],
        
    }
    doc_ref = collection_ref.add(Doc)


    Doc={
        "Index":15,
        "Tipo":0,
        "Text":"Hello! My name is Nikolaus Björn, but my friends call me Niko. I am 29 years old. I live in theU.S, but my family is from Germany. I am serious, inteligent, and short, but I'm not shy",
    }
    doc_ref = collection_ref.add(Doc)


    Doc={
        "Index":16,
        "Tipo":1,
        "Pregunta":": Read the text. Select True or False.",
        "TextoSecundario":"He isn't shy",
        "TrueFalse":True,
        "Respuesta":True
    }
    doc_ref = collection_ref.add(Doc)
    Doc={
        "Index":17,
        "Tipo":1,
        "Pregunta":": Read the text. Select True or False.",
        "TextoSecundario":"His family is German",
        "TrueFalse":True,
        "Respuesta":True
    }
    doc_ref = collection_ref.add(Doc)
    Doc={
        "Index":18,
        "Tipo":1,
        "Pregunta":": Read the text. Select True or False.",
        "TextoSecundario":" He is 19 years old",
        "TrueFalse":True,
        "Respuesta":False
    }
    doc_ref = collection_ref.add(Doc)
    Doc={
        "Index":19,
        "Tipo":1,
        "Pregunta":": Read the text. Select True or False.",
        "TextoSecundario":"He is funny.",
        "TrueFalse":True,
        "Respuesta":False
    }
    doc_ref = collection_ref.add(Doc)
    Doc={
        "Index":20,
        "Tipo":1,
        "Pregunta":": Read the text. Select True or False.",
        "TextoSecundario":"His nickname is Niko",
        "TrueFalse":True,
        "Respuesta":True
    }
    doc_ref = collection_ref.add(Doc)
    
    
    


    
    


AgregarExamen()
