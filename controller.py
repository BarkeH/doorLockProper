import time
import zmq
import cv2
import numpy as np
from io import BytesIO
import random
from photos import photoCapture
from train import retrain
from recogniseWithName import recognise

import serial

import pickle

data = pickle.loads(open("encodings.pickle", "rb").read())

arduino = serial.Serial(port='/dev/ttyACM0')
arduino.flush()

context = zmq.Context()
socket = context.socket(zmq.REP)
socket.bind("tcp://*:4444")
socket.RCVTIMEO = 1000
while True:
    try:
        message = socket.recv()
        print(str(message).split(','))
        print("Received request: {}".format(message))
        if message.decode('utf-8').split(',')[0] == "takePhoto":
            photoCapture(message.decode('utf-8').split(',')[1],data)
            socket.send(b"done")
        elif message == b"train":
            retrain()
            socket.send(b"done")
        elif message == b"lock":
            send_string = "l\n"
            arduino.write(send_string.encode('utf-8'))
            socket.send(b"done")
        elif message == b"unlock":
            send_string = "u\n"
            arduino.write(send_string.encode('utf-8'))
            socket.send(b"done")
        elif message == b"camera":
            frame = recognise(data)
            _, jpeg = cv2.imencode('.jpeg', frame)
            socket.send(jpeg)
        else:
            socket.send(b"failed")
    except zmq.Again:
        print("timeout")

    recognise(data)
