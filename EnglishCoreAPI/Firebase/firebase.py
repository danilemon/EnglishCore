import firebase_admin 
from firebase_admin import credentials, firestore

# Inicializa Firebase Admin SDK
cred = credentials.Certificate("./englishcore-6cd88-firebase-adminsdk-e0ov2-96fcd5d006.json")
firebase_admin.initialize_app(cred)

# Crea la instancia de Firestore
db = firestore.client()