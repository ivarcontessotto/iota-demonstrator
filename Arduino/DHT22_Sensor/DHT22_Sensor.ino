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
  Serial.println("DHT22 Testprogramm");
  dht.begin();
}

void loop() {
  // Wait a few seconds between measurements.
  delay(2000);                     // Hier definieren wir die Verweilzeit die gewartet wird  
                                   // bis der Sensor wieder ausgelesen wird. Da der DHT11  
                                   // auch ca. 2 Sekunden hat um seine Werte zuaktualisieren
                                   // macht es keinen sinn ihn schneller auszulesen!
                                    
  float h = dht.readHumidity();    // Lesen der Luftfeuchtigkeit und speichern in die Variable h
  float t = dht.readTemperature(); // Lesen der Temperatur in °C und speichern in die Variable t
  
/*********************( Überprüfen ob alles richtig Ausgelesen wurde )*********************/ 
  if (isnan(h) || isnan(t)) {       
    Serial.println("Fehler beim auslesen des Sensors!");
    return;
  }

  // Nun senden wir die gemessenen Werte an den PC dise werden wir im Seriellem Monitor sehen
  Serial.print("Luftfeuchtigkeit(%): ");
  Serial.print(h);                  // Ausgeben der Luftfeuchtigkeit
  Serial.print(" Temp(C): ");
  Serial.print(t);                  // Ausgeben der Temperatur
  Serial.println();
}
