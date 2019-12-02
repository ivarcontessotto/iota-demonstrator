/*
  Reading CO2, humidity and temperature from the SCD30
  By: Nathan Seidle
  SparkFun Electronics
  Date: May 22nd, 2018
  License: MIT. See license file for more information but you can
  basically do whatever you want with this code.
  Feel like supporting open source hardware?
  Buy a board from SparkFun! https://www.sparkfun.com/products/14751
  This example prints the current CO2 level, relative humidity, and temperature in C.
  Hardware Connections:
  If needed, attach a Qwiic Shield to your Arduino/Photon/ESP32 or other
  Plug the device into an available Qwiic port
  Open the serial monitor at 9600 baud to see the output
*/

#include <Wire.h>
#include "SparkFun_SCD30_Arduino_Library.h" 

SCD30 airSensor;

void setup()
{
  Wire.begin();
  Serial.begin(9600);
  airSensor.begin(); //This will cause readings to occur every two seconds
  airSensor.setAltitudeCompensation(426); //Set altitude of the sensor in m
}

void loop()
{
  delay(2000);
  
  if (airSensor.dataAvailable())
  {
    Serial.print("temperature:");
    Serial.print(airSensor.getTemperature(), 3); // Second parameter for decimal precision 
    Serial.print("\n");
       
    Serial.print("humidity:");
    Serial.print(airSensor.getHumidity(), 3);
    Serial.print("\n");
  }
}
