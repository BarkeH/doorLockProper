from flask import render_template, request, jsonify
import connexion
from pathlib import Path
from jose import JWTError, jwt
from werkzeug.exceptions import Unauthorized
import time


USERS = {
        "username":"password"
        }

JWT_ISSUER = "com.zalando.connexion"
JWT_SECRET = "password"
JWT_LIFETIME_SECONDS = 600
JWT_ALGORITHM = "HS256"

def login():
    timestamp = current_timestamp()

    data = request.json
    username = data.get("username")
    password = data.get("password")
    if USERS.get(username) == password:
        token = jwt.encode({
            "user":username,
            "exp": int(timestamp + JWT_LIFETIME_SECONDS)
            }, JWT_SECRET, algorithm=JWT_ALGORITHM)
        print(jsonify({"token":token}))
        return jsonify({"token":token})
    else:
        return jsonify({"message":"invalid credentials"}), 401

def generate_token(user_id):
    print("monkey")
    timestamp = current_timestamp()
    payload = {
            "iss": JWT_ISSUER,
            "iat": int(timestamp),
            "exp": int(timestamp + JWT_LIFETIME_SECONDS),
            "sub": str(user_id),
        }

    return jwt.encode(payload, JWT_SECRET, algorithm=JWT_ALGORITHM)

def decode_token(token):
    try:
        return jwt.decode(token, JWT_SECRET, algorithms=[JWT_ALGORITHM])
    except JWTError as e:
        raise Unauthorized from e

def get_secret(user, token_info) -> str:
    return """
    You are user_id {user} and the secret is 'wbevuec'.
    Decoded token claims: {token_info}.
    """.format(
            user=user, token_info=token_info
    )

def current_timestamp() -> int:
    return int(time.time())

app = connexion.App(__name__, specification_dir="./")
app.add_api("swagger.yml")

@app.route("/")
def home():
    return render_template("home.html")

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=8000, debug=True)
