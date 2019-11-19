/*******************************************************************************************
 *******************************************************************************************
 **                                www.bastelgarage.ch                                    **    
 ** Der Onlineshop mit Videoanleitungen und kompletten Bausätzen für Anfänger und Profis! **  
 *******************************************************************************************
 *******************************************************************************************
 ** Mit diesem einfachen Testprogramm möchten wir dir zeigen wie einfach du mit einem     **
 ** Arduino UNO die Temperatur und Luftfeuchtigkeit eines DHT11 oder DHT22 Sensors        ** 
 ** auslessen kanns.                                                                      **
 **                                                                                       **
 ** Autor: Philippe Keller                                                                **
 ** Datum: April 2016                                                                     **
 ** Version: 1.1                                                                          **
 * *****************************************************************************************
*/

/************************( Importieren der genutzten Bibliotheken )************************/
#include "DHT.h"                
#define DHTPIN 3          // Hier die Pin Nummer eintragen wo der Sensor angeschlossen ist
#define DHTTYPE DHT22     // Hier wird definiert was für ein Sensor ausgelesen wird. In 
                          // unserem Beispiel möchten wir einen DHT11 auslesen, falls du 
                          // ein DHT22 hast einfach DHT22 eintragen
       
/********************************( Definieren der Objekte )********************************/                          
DHT dht(DHTPIN, DHTTYPE);

void setup() {
  Serial.begin(9600);
  dht.begin();
}

void loop() {                  
  float t = dht.readTemperature();                 
  float h = dht.readHumidity();
  
  if (!isnan(h)) {       
    Serial.print("temp:");
    Serial.print(t);
    Serial.print("\n");
  }

  if (!isnan(t)) {
    Serial.print("humidity:");
    Serial.print(h);
    Serial.print("\n");
  }

  delay(1000);  
}
