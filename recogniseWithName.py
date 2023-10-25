import face_recognition
from imutils.video import VideoStream, FPS
import imutils
import pickle
import time
import cv2
vs = VideoStream(src = 0, framerate = 10).start()

time.sleep(2.0)

fps = FPS().start()
#data = pickle.loads(open("encodings.pickle", "rb").read())
currentname = ""

def recognise(data, justImage=False):
    frame = vs.read()
    frame = imutils.resize(frame, width=500)
    if justImage:
        return frame
    boxes = face_recognition.face_locations(frame)

    encodings = face_recognition.face_encodings(frame, boxes)
    names = []

    for encoding in encodings:
        matches = face_recognition.compare_faces(data["encodings"], encoding)
        name = "Unknown"
        
        if True in matches:
            matchedIdxs = [i for (i,b) in enumerate(matches) if b]

            counts = {}
            
            for i in matchedIdxs:
                name = data["name"][i]
                counts[name] = counts.get(name, 0) + 1

            name = max(counts, key=counts.get)

            #if currentname != name:
            #    currentname = name
            #    print(name)
            print(name)

    for (top, right, bottom, left) in boxes:
        cv2.rectangle(frame, (left, top), (right, bottom), (0, 255, 225), 2)
        y = top - 15 if top - 15 else top + 15
        cv2.putText(frame, name, (left, y), cv2.FONT_HERSHEY_SIMPLEX, .8, (0, 255, 25), 2)
    cv2.imshow("test", frame)
    fps.update()
    key = cv2.waitKey(1) & 0xFF

    return frame
fps.stop()
