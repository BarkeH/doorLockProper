import zmq
from flask import send_file
from io import BytesIO

def retrain():
    context = zmq.Context()
    socket = context.socket(zmq.REQ)
    socket.connect("tcp://localhost:4444")
    request = b'train'
    print("sending request {} ...".format(request))
    socket.send(request)
    message = socket.recv()
    print("received reply {} [ {} ]".format(request,message))

    socket.close()
    context.term()

def newName(name):
    context = zmq.Context()
    socket = context.socket(zmq.REQ)
    socket.connect("tcp://localhost:4444")
    request = b'takePhoto,' + name.encode('utf-8')
    print("sending request {} ...".format(request))
    socket.send(request)
    message = socket.recv()
    print("received reply {} [ {} ]".format(request,message))

    socket.close()
    context.term()

def lock():
    context = zmq.Context()
    socket = context.socket(zmq.REQ)
    socket.connect("tcp://localhost:4444")
    request = b'lock'
    print("sending request {} ...".format(request))
    socket.send(request)
    message = socket.recv()
    print("received reply {} [ {} ]".format(request,message))

    socket.close()
    context.term()

def unlock():
    context = zmq.Context()
    socket = context.socket(zmq.REQ)
    socket.connect("tcp://localhost:4444")
    request = b'unlock'
    print("sending request {} ...".format(request))
    socket.send(request)
    message = socket.recv()
    print("received reply {} [ {} ]".format(request,message))

    socket.close()
    context.term()

def getImage():
    context = zmq.Context()
    socket = context.socket(zmq.REQ)
    socket.connect("tcp://localhost:4444")
    request = b'camera'
    print("sending request{} ...".format(request))
    socket.send(request)
    message = socket.recv()
    print("received reply {} [ {} ]".format(request,len(message)))

    socket.close()
    context.term()
    return send_file(BytesIO(message), mimetype='image/jpeg')

