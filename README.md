# Cat-Weather-App

## Summary
This project is a cat-themed weather App. 

It uses an API to pull real-time weather data for any location. The data then gets displayed to the user through a GUI. The program displays information about the short forecast (a quick description of the weather), the probability of precipitation, the wind speed and direction, the long forecast (a detailed description of the weather), a 3-, 5-, or 7-day forecast, and the date and time. All of this information is not displayed on just one screen. 

The GUI features five different screens: the Main screen (where the user starts), the More Information screen, the 3-Day Forecast screen, the 5-Day Forecast screen, and the 6-Day Forecast screen. Each screen is able to go back to the previous screen (meaning there are no dead ends and the user cannot get stuck on any screen). The main screen has access to the other 4 screens. 

Each screen displays information dedicated to it and features custom artwork. Each screen shows a cat themed around the current weather conditions; if the weather changes, so do the cats. All the images are designed to tell the user what the conditions are outside at a quick glance. 

This project incorporates many elements of JavaFX to create the GUIs and give them the required functionality.

## Installiation
To install the project, you must clone the repo using:
   ```
   git clone https://github.com/AshleyD50/Cat-Weather-App.git
   ```
Once cloned, you might need to install Apache Maven if it is not already installed. Download Maven here: https://maven.apache.org/download.cgi

## Running the Code
Once installed, you can run the code. I developed and ran the code in IntelliJ, so I recommend running it there.
Each time you run, I recommend running the clean command each time.

<ins> Run in IntelliJ</ins>
   1. Running
      To run in IntelliJ, under Run, go to Edit Configurations and add a Maven configuration. In the run line of that configuration, add
      ```
      clean compile exec:java
      ```
   2. Cleaning
      To just clean in IntelliJ, either modify an old Maven configuration or create a new one that just has `clean` in the run line.
      
      Ensure you have the correct working directory and hit ok to save it. Then you can press the run button to run the project under that configuration.

<ins> Run in the Terminal</ins>
   1. Running
      To run in the terminal, you need to navigate to the root directory of the project, where the pom.xml file is located. Then run the command
      ```
      mvn clean compile exec:java
      ```
   2. Cleaning
      To just clean in the terminal, run the command
      ```
      mvn clean
      ```

The GUI should then load in, and the project is running!

## Acknowledgements
Credits for the project idea, the API design, and the API implementation go to Professor McCarty.
