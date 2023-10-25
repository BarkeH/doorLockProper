import face_recognition
from imutils.video import VideoStream, FPS
import imutils
import pickle
import time
import cv2

vs = VideoStream(src = 0, framerate = 10).start()

time.sleep(2.0)

fps = FPS().start()

while True:
    frame = vs.read()
    frame = imutils.resize(frame, width=500)

    boxes = face_recognition.face_locations(frame)

    encodings = face_recognition.face_encodings(frame, boxes)

    for (top, right, bottom, left) in boxes:
        cv2.rectangle(frame, (left, top), (right, bottom), (0, 255, 225), 2)
    cv2.imshow("test", frame)
    fps.update()

    key = cv2.waitKey(1) & 0xFF
    if key == ord("q"):
        break
fps.stop()
