import cv2
import random
import face_recognition
from imutils.video import VideoStream, FPS
import imutils
import pickle
import time
from recogniseWithName import recognise

def photoCapture(name,data):

    

    img_counter = 0
    
    frame = recognise(data,True)
    
    img_name = "dataset/"+ name +"/image_{}.jpg".format(random.randrange(10000))
    print(img_name)
    cv2.imwrite(img_name, frame)
    print("{} written!".format(img_name))
    

