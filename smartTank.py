import RPi.GPIO as GPIO
import motor
import time
import http.client
import urllib.request
import json

GPIO.setmode(GPIO.BCM)
GPIO_TRIGGER = 18
GPIO_ECHO = 24
GPIO.setup(GPIO_TRIGGER, GPIO.OUT)
GPIO.setup(GPIO_ECHO, GPIO.IN)
 
def distance():
    GPIO.output(GPIO_TRIGGER, True)
    time.sleep(0.00001)
    GPIO.output(GPIO_TRIGGER, False) 
    StartTime = time.time()
    StopTime = time.time()

    while GPIO.input(GPIO_ECHO) == 0:
        StartTime = time.time()
    while GPIO.input(GPIO_ECHO) == 1:
        StopTime = time.time()
    TimeElapsed = StopTime - StartTime
    distance = (TimeElapsed * 34300) / 2
    return distance

def sendData(distance):
    con = http.client.HTTPConnection("192.168.1.198",22849)
    headers = {'Content-type': 'application/json'}
    foo = {'waterLevel': str(distance)}
    json_data = json.dumps(foo)
    con.request("POST","/RaspberryPi/ConnectionWithPi",json_data)
    r1 = con.getresponse()
    data= r1.read().decode()
    responseData = json.loads(data)
    return responseData["response"]
 
def fillWater():
    motorStatus=0;
    btnStatus=0;
    motor.startMotor()
    while True:
        if motorStatus==0:
            dist = int(distance())
            if dist<=16.0 and dist>3:
                btnStatus = sendData(dist)
                print ("Measured Distance if = %.1f cm" % dist)
                if btnStatus is 0:
                    motorStatus=1
                    #motor.stopMotor()
            elif dist<=3:
                sendData(dist)
                motorStatus=1
                #motor.stopMotor()
                print ("Measured Distance motor off= %.1f cm" % dist)
            time.sleep(2)
        else:
            dist = int(distance())
            if dist<14.0:
                btnStatus = sendData(-dist)
                print ("Measured Distance water dec= %.1f cm" % dist)
                if btnStatus is 1:
                    motorStatus=0
                    #motor.stopMotor()
            else:
                sendData(-dist)
                motorStatus=0
                print ("Measured Distance water dec= %.1f cm" % dist)
            time.sleep(2)
                      
def checkBtnStatus():
    con = http.client.HTTPConnection("192.168.1.198",22849)
    headers = {'Content-type': 'application/json'}
    foo = {'waterLevel': -165}
    json_data = json.dumps(foo)
    con.request("POST","/RaspberryPi/ConnectionWithPi",json_data)
    r1 = con.getresponse()
    data= r1.read().decode()
    responseData = json.loads(data)
    
    if responseData["response"] is 0:
        print(responseData["response"])
        time.sleep(1)
        checkBtnStatus()
    else:
        print(responseData["response"])
        fillWater()
        
if __name__ == '__main__':
        try:
            checkBtnStatus()
        except KeyboardInterrupt:
            print("Measurement stopped by User")
            GPIO.cleanup()


