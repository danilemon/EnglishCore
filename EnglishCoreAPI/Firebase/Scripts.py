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

# Ejecuta la función
update_group_references_for_students()
