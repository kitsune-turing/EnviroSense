import datetime
import threading
from app.src.data_generator import SyntheticPollutantDataGenerator


"""
    Function to generate synthetic pollutant data and send it to Firebase.
"""
def generate_and_send_data():
    print(f"[{datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')}] - Starting data generation...")
    generator = SyntheticPollutantDataGenerator()
    generator.generate_all_data()
    print(f"[{datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')}] - Data generated and sent to Firebase successfully!")


"""
    Program to schedule the generation of synthetic pollutant data every 30 minutes.
"""
def schedule_data_generation(interval_minutes=30):
    generate_and_send_data()
    threading.Timer(interval_minutes * 60, schedule_data_generation).start()



if __name__ == '__main__':
    # Start the program to generate synthetic pollutant data every 30 minutes
    schedule_data_generation(interval_minutes=30)
