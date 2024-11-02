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


