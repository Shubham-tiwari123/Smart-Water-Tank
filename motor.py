import RPi.GPIO as GPIO
import time
GPIO.setmode(GPIO.BCM)
mypin = 23
GPIO.setwarnings(False)
GPIO.setup(mypin,GPIO.OUT)

def startMotor():
    print ("LED on")
    GPIO.output(mypin,GPIO.HIGH)
    print ("LED start")
#time.sleep(5)
def stopMotor():
    print ("LED off")
    GPIO.output(mypin,GPIO.LOW)
