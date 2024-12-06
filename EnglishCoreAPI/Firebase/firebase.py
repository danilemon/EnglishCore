import firebase_admin 
from firebase_admin import credentials, firestore, storage
import os
print("          A")
print(os.getcwd())

# Inicializa Firebase Admin SDK
cred = credentials.Certificate("Firebase/englishcore-6cd88-firebase-adminsdk-e0ov2-96fcd5d006.json")
firebase_admin.initialize_app(cred, {
    'storageBucket': 'englishcore-6cd88.appspot.com'  # Reemplaza con el nombre real de tu bucket
})
# Crea la instancia de Firestore
db = firestore.client()

bucket = storage.bucket()
