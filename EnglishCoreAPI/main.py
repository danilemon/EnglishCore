from fastapi import FastAPI
from Routers import Login

app = FastAPI()

app.include_router(Login.Login)
@app.get("/")
def read_root():
    return {"message": "Welcome to FastAPI!"}