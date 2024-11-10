import numpy as np
import pandas as pd
from datetime import datetime, timedelta
import firebase_admin
from firebase_admin import credentials, firestore
from .config import END_DATE, CO2_MIN, CO2_MAX, NO2_MIN, NO2_MAX, SO2_MIN, SO2_MAX, INTERVAL_SECONDS



# Inicializer Firebase
cred = credentials.Certificate("./../app/firebase_credentials.json")
firebase_admin.initialize_app(cred)
db = firestore.client()


"""
    Class SyntheticPollutantDataGenerator is used to generate synthetic data for CO2, NO2 and SO2 pollutants
    and send it to Firebase in the desired structure. The data is generated randomly within the specified
    ranges and the municipalities are also randomly selected from a list of municipalities in Antioquia.
"""
class SyntheticPollutantDataGenerator:
    def __init__(self):
        # Date and time variables
        self.start_datetime = datetime.now()
        self.end_datetime = datetime.strptime(END_DATE, "%Y-%m-%d %H:%M:%S")
        self.total_seconds = int((self.end_datetime - self.start_datetime).total_seconds())
        self.num_records = 500
        self.timestamps = [
            self.start_datetime + timedelta(seconds=i * INTERVAL_SECONDS)
            for i in range(self.num_records)
        ]
        
        
        # List of municipalities in Antioquia
        self.municipalities = [
            "ABRIAQUI", "ANGOSTURA", "BELLO", "LA UNION", "MEDELLIN", "RIONEGRO",
            "SAN ROQUE", "SANTA BARBARA", "SANTA FE DE ANTIOQUIA", "SANTA ROSA DE OSOS",
            "SONSON", "TITIRIBI", "VALDIVIA", "YOLOMBO"
        ]
        
        
    """
        send_to_firebase method is used to send the data to Firebase in the desired structure.
    """
    def send_to_firebase(self, pollutant, data):
        collection_ref = db.collection("sensors").document(pollutant).collection("data")
        
        for entry in data:
            collection_ref.add(entry)
        
        print(f"Data for {pollutant} sent to Firebase")
        
        
    """
        generate_co2_data method is used to generate synthetic CO2 data and send it to Firebase.
    """
    def generate_co2_data(self):
        co2_values = np.random.uniform(CO2_MIN, CO2_MAX, size=self.num_records)
        municipalities = np.random.choice(self.municipalities, size=self.num_records)
        
        
        data = [
            {
                "value": co2_values[i],
                "date": self.timestamps[i].isoformat(),
                "municipality": municipalities[i]
            }
            
            for i in range(self.num_records)
        ]
        self.send_to_firebase("CO2", data)


    """
        generate_no2_data method is used to generate synthetic NO2 data and send it to Firebase.
    """
    def generate_no2_data(self):
        no2_values = np.random.uniform(NO2_MIN, NO2_MAX, size=self.num_records)
        municipalities = np.random.choice(self.municipalities, size=self.num_records)

        data = [
            {
                "value": no2_values[i],
                "fecha": self.timestamps[i].isoformat(),
                "municipio": municipalities[i]
            }
            
            for i in range(self.num_records)
        ]        
        self.send_to_firebase("NO2", data)


    """
        generate_so2_data method is used to generate synthetic SO2 data and send it to Firebase.
    """
    def generate_so2_data(self):
        so2_values = np.random.uniform(SO2_MIN, SO2_MAX, size=self.num_records)
        municipalities = np.random.choice(self.municipalities, size=self.num_records)
        
        data = [
            {
                "value": so2_values[i],
                "fecha": self.timestamps[i].isoformat(),
                "municipio": municipalities[i]
            }
            for i in range(self.num_records)
        ]
        self.send_to_firebase("SO2", data)


    """
        generate_temperature_data method is used to generate synthetic temperature data and send it to Firebase.
    """
    def generate_temperature_data(self):
        temperature_values = np.random.uniform(15, 35, size=self.num_records)
        municipalities = np.random.choice(self.municipalities, size=self.num_records)
        
        data = [
            {
                "value": temperature_values[i],
                "date": self.timestamps[i].isoformat(),
                "municipality": municipalities[i]
            }
            for i in range(self.num_records)
        ]
        self.send_to_firebase("Temperature", data)
    
    
    """
        generate_humidity_data method is used to generate synthetic humidity data and send it to Firebase.
    """
    def generate_humidity_data(self):
        humidity_values = np.random.uniform(40, 90, size=self.num_records)
        municipalities = np.random.choice(self.municipalities, size=self.num_records)
        
        data = [
            {
                "value": humidity_values[i],
                "date": self.timestamps[i].isoformat(),
                "municipality": municipalities[i]
            }
            for i in range(self.num_records)
        ]
        self.send_to_firebase("Humidity", data)


    """
        generate_all_data method is used to generate synthetic data for all pollutants and environmental metrics and send it to Firebase.
    """
    def generate_all_data(self):
        self.generate_so2_data()
        self.generate_no2_data()
        self.generate_co2_data()
        self.generate_temperature_data()
        self.generate_humidity_data()
