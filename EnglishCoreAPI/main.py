import socket
from fastapi import FastAPI
import uvicorn
from Routers import Students
from Routers import Login
from Routers import Teachers
from Routers import Activities

app = FastAPI()

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