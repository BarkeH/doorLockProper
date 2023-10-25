import time
import zmq
import cv2
import numpy as np
from io import BytesIO

context = zmq.Context()
socket = context.socket(zmq.REQ)
socket.connect("tcp://localhost:4444")
message = None
for request in range(1):
    print("sending request {} ...".format(request))
    socket.send(b'camera')
    message = socket.recv()
    print("received reply {} [ {} ]".format(request,message))
print(type(message))

message = np.frombuffer(message, np.uint8)
message = cv2.imdecode(message, cv2.IMREAD_UNCHANGED)
cv2.imshow("monkey", message)

while True:
    key = cv2.waitKey(1) & 0xFF

    continue

socket.close()
context.term()

