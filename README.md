# Cat-Weather-App

## Summary
This project is a cat-themed weather App. 

It uses an API to pull real-time weather data for any location. However, this app only displays weather data for Chicago. The data then gets displayed to the user through a GUI. The program displays information about the short forecast (a quick description of the weather), the probability of precipitation, the wind speed and direction, the long forecast (a detailed description of the weather), a 3-, 5-, or 7-day forecast, and the date and time. All of this information is not displayed on just one screen. 

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
   1. Running<br/>
      To run in IntelliJ, under Run, go to Edit Configurations and add a Maven configuration. In the run line of that configuration, add
      ```
      clean compile exec:java
      ```
   2. Cleaning<br/>
      To just clean in IntelliJ, either modify an old Maven configuration or create a new one that just has `clean` in the run line.
      
      Ensure you have the correct working directory and hit ok to save it. Then you can press the run button to run the project under that configuration.

<ins> Run in the Terminal</ins>
   1. Running<br/>
      To run in the terminal, you need to navigate to the root directory of the project, where the pom.xml file is located. Then run the command
      ```
      mvn clean compile exec:java
      ```
   2. Cleaning<br/>
      To just clean in the terminal, run the command
      ```
      mvn clean
      ```

The GUI should then load in, and the project is running!

## Project Explained
After you get the project running and open the GUI, there are many features to explore. 

1. The Main Screen (the screen you start on) displays the current weather and a cat themed to those conditions. Then from that screen, you have 4 options: the More Info Screen, the 3-Day Forecast Screen, the 5-Day Forecast Screen, or the 7-Day Forecast Screen. By clicking any of the respective buttons, you will move to the next screen. All screens will have a back button that takes you back to the Main Screen, so you will never get stuck on one screen.

2. The More Info Screen displays a more detailed forecast and conditions for the day. It also features the themed cat "dancing" by having it rotate at an angle nonstop. This screen also has custom "The Forecast" art. Press the back button to return to the Main Screen, which will allow you to access the other screens.

3. The 3-Day Forecast Screen displays the forecast for the next 3 days. This screen still displays the themed cat along with the weather data. It shows the short forecast for each period of the next 3 days. Each period represents either the morning or the evening of the day, so 2 periods make up one full day. Press the back button to return to the Main Screen, which will allow you to access the other screens.

4. The 5-Day Forecast Screen displays the forecast for the next 5 days. This screen still displays the themed cat along with the weather data. It shows the short forecast for each period of the next 5 days. Press the back button to return to the Main Screen, which will allow you to access the other screens.

5. The 7-Day Forecast Screen displays the forecast for the next 7 days. This screen still displays the themed cat along with the weather data. It shows the short forecast for each period of the next 7 days. Press the back button to return to the Main Screen, which will allow you to access the other screens.

After you have explored all the screens, you should have a good idea of Chicago's weather! To exit the app, you can just close the GUI screen or manually stop the program from running.

## Video Demo
[![Cat Weather App Demo](https://img.youtube.com/vi/fIyaK1NkiWQ/0.jpg)]
(https://www.youtube.com/watch?v=fIyaK1NkiWQ)

## Acknowledgements
Credits for the project idea, the API design, and the API implementation go to Professor McCarty.
