from imutils import paths
import face_recognition
import cv2
import pickle
import os

def retrain():
    filePaths = "dataset/"

    imagePaths = list(paths.list_images(filePaths))
    print(imagePaths)

    names = []
    encodings = []
    for (i,imagePath) in enumerate(imagePaths):
        print("Training image " + str(i) + "/" + str(len(imagePaths))) 
        name = imagePath.split(os.path.sep)[-2]
        image = cv2.imread(imagePath)
        image = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)
    
        face_locations = face_recognition.face_locations(image, model="hog")
        face_encodings = face_recognition.face_encodings(image, face_locations)

        for encoding in face_encodings:
            names.append(name)
            encodings.append(encoding)

    print("Dumping to file")
    data = {"encodings": encodings, "name": names}
    f = open("encodings.pickle", "wb")
    f.write(pickle.dumps(data))
