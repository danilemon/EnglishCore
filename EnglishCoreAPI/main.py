import socket
from fastapi import FastAPI
import uvicorn
from Routers import Students
from Routers import Login
from Routers import Teachers
from Routers import Activities
from fastapi.middleware.cors import CORSMiddleware

app = FastAPI()
# Configuración de CORS para aceptar solicitudes de cualquier origen
app.add_middleware(
    CORSMiddleware,
    allow_origins=["http://localhost:3000"],  # Permite todas las IPs y dominios
    allow_credentials=True,
    allow_methods=["*"],  # Permite todos los métodos (GET, POST, etc.)
    allow_headers=["*"],  # Permite todos los encabezados
)
app.include_router(Students.StudentsR)
app.include_router(Teachers.TeachersR)
app.include_router(Login.Login)
app.include_router(Activities.Activities)
hostname = socket.gethostname()
local_ip = socket.gethostbyname(hostname)
print(f"Application running on: http://{local_ip}:5000")

@app.get("/")
def read_root():
    return {"message": "Welcome to FastAPI!"}

if __name__ == "__main__":
    # Obtener la IP local

    # Ejecutar el servidor Uvicorn
    uvicorn.run(app, host="0.0.0.0", port=5000) 