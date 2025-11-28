//
// Cat Weather App
// Designed by Ashley Daly
// Class Project at UIC for Software Design taught by Professor McCarty
//
// This project is a Cat Themed Weather App. It uses an API to pull real time weather data for any location. The data
// then gets displayed to the user through a GUI. The program displays information about the short forecast (a quick
// description about the weather), the probability of precipitation, the wind speed and direction, the long forecast
// (a detailed description of the weather), a 3- ,5-, or 7- day forecast, and the date and time. All of this information
// is not displayed on just one screen. The GUI has 5 different screens: the main screen (this is the screen the user
// starts on), the more information screen, the 3-day forecast screen, the 5-day forecast screen, and the 6-day forecast
// screen. Each screen is able to go back to the previous screen (meaning there are no dead ends and the user cannot get
// stuck on any screen). The main screen has access to the other 4 screens. Each screen shows the information dedicated
// to itself and includes custom art. Each screen shows a cat themed around the current weather conditions, if the weather
// changes, so do the cats. All the images are designed to tell the user what the conditions are outside at a quick glance.
// This project incorporates many elements of JavaFX to create the GUIs and give then the required functionality. The API
// was designed and implemnted by Professor McCarty and the GUI design and program were designed and implmented by Ashley Daly.
//
// Credits for the idea and starter code go to Professor McCarty
//


import javafx.animation.Animation;
import javafx.application.Application;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.RotateTransition;
import javafx.scene.layout.*;
import javafx.util.Duration;

import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import weather.Period;
import weather.WeatherAPI;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class JavaFX extends Application {
	// sets up all the global GUI elements that will be needed
	Text temperature, weather, precipitation, date, time, dayNum, nightNum, precipitation1,
			precipitation2, temp1, temp2, windSpeed, windDirection, detailedWeather;
	Button threeDay, fiveDay, sevenDay, moreInfo, backButton3, backButton5, backButton7,
	backButtonMoreInfo;
	HBox temperatureHBox, weatherHBox, daysHBox;
	VBox v1, descriptionVBox;
	Region space1, space2, space3, space4, space5, space6;
	ScrollPane scroll;


	public static void main(String[] args) {

		launch(args);
	}

	// this function sets the date and time into their respective text fields.
	// it has no parameters and no returns
	private void dateAndTimeText() {
		Date dateNow = new Date();
		// date text
		date = new Text();
		date.setStyle("-fx-background-color: transparent; -fx-font-size: 22px;");

		SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
		date.setText(dateFormatter.format(dateNow));

		// time text
		time = new Text();
		time.setStyle("-fx-background-color: transparent; -fx-font-size: 22px;");

		SimpleDateFormat timeFormatter = new SimpleDateFormat("h:mm a");
		time.setText(timeFormatter.format(dateNow));
	}

	// this function sets the action of the back button. all the back buttons share the same functionality,
	// return to the previous screen
	// the parameters are a Button for the specific back button, a Stage to the primary stage, and a Scene
	// to the scene the back button should be going back to
	// it has no returns
	private void backButtonAction(Button backButton, Stage primaryStage, Scene scene) {
		backButton.setOnAction(e-> {primaryStage.setScene(scene);});
	}

	// this function takes in a string and adds newlines to the string so it can be properly displayed in the GUI
	// the parameters are an ArrayList of the periods to hold the forecasts, an int for the number of words
	// before spliting, a Text area for the new string to be displayed in, and a String to the original string
	// it has no returns
	private void addNewlines(ArrayList<Period> forecast, int splitNum, Text newString, String orgString) {
		String format = "";

		String[] words = orgString.split(" "); // splits the string by each space, so it creates an array of words
		int wordCount = 0;

		// loops through each word in the words array
		for (String word : words) {
			format += word + " "; // adds the word and a space to the temp string
			wordCount++;
			if (wordCount % splitNum == 0) { // if the word count mod the number to split on is 0, adds a newline
				format += "\n";
			}
		}
		newString.setText(format);
	}

	// this function sets up the information for day forecast. it gathers the information from the day and
	// night periods and saves them into text areas to be displayed in the GUI
	// the parameters are a Period to the morning forecast's period, a Period to the night forecast's period,
	// and an integer for the font size
	// it has no returns
	private void daysSetup(Period period1, Period period2, int fontSize) {
		dayNum = new Text(period1.name); // sets the name of the morning period to a text area
		nightNum = new Text(period2.name); // sets the name of the night period to a text area

		// this sets the temp and precipitation for each period into Text areas for future use
		temp1 = new Text(String.valueOf(period1.temperature + "° "
				+ period1.temperatureUnit));
		precipitation1 = new Text("Probability of \nPrecipitation:\n" +
				String.valueOf(period1.probabilityOfPrecipitation.value) + "%");
		temp2 = new Text(String.valueOf(period2.temperature + "° "
				+ period2.temperatureUnit));
		precipitation2 = new Text("Probability of \nPrecipitation:\n" +
				String.valueOf(period2.probabilityOfPrecipitation.value) + "%");

		// this sets the style for the text areas to transparent and the font size to the parameter
		temp1.setStyle("-fx-background-color: transparent; -fx-font-size: "
				+ (fontSize + 2) + "px;");
		temp2.setStyle("-fx-background-color: transparent; -fx-font-size: "
				+ (fontSize + 2) + "px;");
		precipitation1.setStyle("-fx-font-size:" + fontSize + "px;");
		precipitation2.setStyle("-fx-font-size:" + fontSize + "px;");
		dayNum.setStyle("-fx-font-size:" + fontSize + "px;");
		nightNum.setStyle("-fx-font-size:" + fontSize + "px;");
	}

	// this function generates the forecast for the days. it gets the information from the current periods
	// and sets them into VBoxes and HBoxes that get displayed in the days VBox
	// the parameters are a VBox to hold the days vertically, an ArrayList of the periods for the forecast,
	// an integer for the font size, and an integer to get the current period
	// it has no returns
	private void generateDaysForecast(VBox days, ArrayList<Period> forecast, int fontSize, int i) {
		Period period1 = forecast.get(i*2); // gets the next day morning period (since the current day is already displayed)
		Period period2 = forecast.get(i*2+1); // gets the next day night period (since the current day is already displayed)

		daysSetup(period1, period2, fontSize);

		// creates separators for the GUI to display the information neatly
		Separator daySeparator = new Separator();
		Separator nightSeparator = new Separator();
		Separator timeSeparator = new Separator();
		timeSeparator.setOrientation(Orientation.VERTICAL);
		timeSeparator.setPrefHeight(80);
		daySeparator.setPrefWidth(80);
		nightSeparator.setPrefWidth(80);

		// creates the format to display the forecast for each number of days and adds it to the days VBox
		VBox vBox1 = new VBox(5, dayNum, temp1, precipitation1, daySeparator);
		VBox vBox2 = new VBox(5, nightNum, temp2, precipitation2, nightSeparator);
		HBox hBox = new HBox(20, vBox1, timeSeparator, vBox2);
		days.getChildren().add(hBox);
	}

	// this function
	// the parameters are an ArrayList of the periods for the forecast, and integer for the number of days,
	// an integer for the font size, and a VBox for the current day's details
	// it returns a VBox containing the forecast for each day
	private VBox numDaysForecast(ArrayList<Period> forecast, int numDays, int fontSize, VBox currentDayDetails) {
		VBox days = new VBox(10);

		// checks if the number of days is equal to 7 or not, because the forecast for 7 days does not fit
		// onto one side of the screen well and must be split onto both sided (while 3 and 5 days fit on one
		// side well and do not need to be split)
		if (numDays == 7) {
			// calls generateDaysForecast for 2 out of 7 days
			for (int i = 0; i < 2; i++) {
				generateDaysForecast(currentDayDetails, forecast, fontSize, i);
			}
			// calls generateDaysForecast for remaining 5 days
			for (int i = 2; i < 7; i++) {
				generateDaysForecast(days, forecast, fontSize, i);
			}
		}
		else {
			// calls generateDaysForecast for number of days passed into the function
			for (int i = 0; i < numDays; i++) {
				generateDaysForecast(days, forecast, fontSize, i);
			}
		}
		return days;
	}

	// this function sets the main GUI screen's text. it sets the temperature, current weather, and
	// chance of precipitation text for day's forecast
	// the parameters are an ArrayList of the periods for the forecast
	// it has no returns
	public void setMainText(ArrayList<Period> forecast) {
		// temperature text
		temperature = new Text();
		temperature.setStyle("-fx-background-color: transparent; -fx-font-size: 34px; ");
		temperature.setText(String.valueOf(forecast.get(0).temperature + "° "
		+ forecast.get(0).temperatureUnit));

		// weather text
		weather = new Text();
		weather.setStyle("-fx-background-color: transparent; -fx-font-size: 28px;");
		String shortForecast = String.valueOf(forecast.get(0).shortForecast);
		addNewlines(forecast, 3, weather, shortForecast);

		// precipitation text
		precipitation = new Text();
		precipitation.setStyle("-fx-font-size: 22px;");
		precipitation.setText("Probability of Precipitation: \n" +
				String.valueOf(forecast.get(0).probabilityOfPrecipitation.value) + "%");
	}

	// this function sets up the buttons for the main GUI screen. it sets up 4 buttons that will
	// be displayed on the main screen
 	// it has no parameters or returns
	public void setMainButtons() {
		// button for the 3 day forecast
		threeDay = new Button("3 Day Forecast");
		threeDay.setPadding(new Insets(10, 10, 10, 10));
		threeDay.setStyle("-fx-font-size: 18px; -fx-background-color: silver");

		// button for the 5 day forecast
		fiveDay = new Button("5 Day Forecast");
		fiveDay.setPadding(new Insets(10, 10, 10, 10));
		fiveDay.setStyle("-fx-font-size: 18px; -fx-background-color: silver");

		// button for the 7 day forecast
		sevenDay = new Button("7 Day Forecast");
		sevenDay.setPadding(new Insets(10, 10, 10, 10));
		sevenDay.setStyle("-fx-font-size: 18px; -fx-background-color: silver");

		// button for the more info button
		moreInfo = new Button("More Information");
		moreInfo.setPadding(new Insets(10, 10, 10, 10));
		moreInfo.setStyle("-fx-font-size: 18px; -fx-background-color: silver");
	}

	// this function creates Regions for spacing and sets their width/height to the needed values for the main screen
	// it has no parameters or returns
	public void setMainSpacing() {
		// creates all the regions
		space1 = new Region();
		space2 = new Region();
		space3 = new Region();
		space4 = new Region();
		space5 = new Region();
		space6 = new Region();

		// sets the regions widths or heights
		space1.setPrefWidth(30);
		space2.setPrefWidth(40);
		space3.setPrefWidth(40);
		space4.setPrefHeight(200);
		space5.setPrefWidth(120);
		space6.setPrefWidth(60);
	}

	// this function sets the actions for all the buttons on the main page. all the buttons switch to another
	// scene so the button rely on the parameters to correctly switch to the scene
 	// the parameters are a Stage to the primary stage, an ArrayList of the periods for the forecast, and a
	// Scene for the scene to switch to
 	// it has no returns
	public void setMainButtonActions(Stage primaryStage, ArrayList<Period> forecast, Scene scene) {
		// 3 day
		Scene scene2 = getDaysScene(primaryStage, forecast, scene, 3,22, backButton3);
		threeDay.setOnAction(e-> {primaryStage.setScene(scene2);});

		// 5 day
		Scene scene3 = getDaysScene(primaryStage, forecast, scene, 5, 16, backButton5);
		fiveDay.setOnAction(e-> {primaryStage.setScene(scene3);});

		// 7 day
		Scene scene4 = getDaysScene(primaryStage, forecast, scene, 7, 15, backButton7);
		sevenDay.setOnAction(e-> {primaryStage.setScene(scene4);});

		// More info
		Scene scene5 = moreInfoScene(primaryStage, forecast, scene);
		moreInfo.setOnAction(e-> {primaryStage.setScene(scene5);});
	}

	// this function checks the weather forecast to see which keywords are present and returns the required image
	// based on the keywords. a boolean is also passed in to determine if a cat image is needed or just the icon png
 	// the parameters are an ArrayList of the periods for the forecast, and a boolean to determine whether or not
	// the required file is a cat png or not
 	// it returns an ImageView of the required image with all the requirements correctly set
	public ImageView getImage(ArrayList<Period> forecast, Boolean isCat) {
		String catFile, iconFile, file;
		int width;
		String weather = forecast.get(0).shortForecast.toLowerCase(); // gets the short forecast to check which words are present

		// checks which keywords are present and correctly assigns the cat and icon files
		if (weather.contains("rain") || weather.contains("showers")) {
			catFile = "rain.png";
			iconFile = "rainIcon.png";
		}
		else if (weather.contains("snow")) {
			catFile = "snow.png";
			iconFile = "snowIcon.png";
		}
		else if (weather.contains("sunny")) {
			catFile = "sunny.png";
			iconFile = "sunnyIcon.png";
		}
		else if (weather.contains("cloudy")) {
			catFile = "cloudy.png";
			iconFile = "cloudyIcon.png";
		}
		else if (weather.contains("wind") || weather.contains("windy") ||
				weather.contains("breezy") || weather.contains("strong")) {
			catFile = "windy.png";
			iconFile = "windyIcon.png";
		}
		else if (!forecast.get(0).isDaytime) {
			catFile = "night.png";
			iconFile = "nightIcon.png";
		}
		else {
			catFile = "clear.png";
			iconFile = "clearIcon.png";
		}

		// this checks if the cat file is required or not, if it is the file used is the cat image, otherwise
		// the icon image is used instead
		if (isCat) {
			file = catFile;
			width = 350;
		}
		else {
			file = iconFile;
			width = 80;
		}

		// creates the ImageView with the file and sets the correct elements
		Image image = new Image(file);
		ImageView pic = new ImageView();
		pic.setImage(image);
		pic.setFitWidth(width);
		pic.setPreserveRatio(true);
		pic.setSmooth(true);

		return pic;
	}

	// this function checks the weather forecast to see which keywords are present and returns the required
	// color based on the keywords.
 	// the parameters are an ArrayList of the periods for the forecast
 	// it returns a String of the color based on the weather
	public String getColor(ArrayList<Period> forecast) {
		String weather = forecast.get(0).shortForecast.toLowerCase(); // gets the short forecast to check which words are present

		// checks the keywords and returns the required color
		if (weather.contains("rain") || weather.contains("showers")) {
			return "Lightskyblue";
		}
		else if (weather.contains("snow")) {
			return "Ivory";
		}
		else if (weather.contains("sunny")) {
			return "Lemonchiffon";
		}
		else if (weather.contains("cloudy")) {
			return "Gainsboro";
		}
		else if (weather.contains("wind") || weather.contains("windy") ||
				weather.contains("breezy") || weather.contains("strong")) {
			return "Lightgrey";
		}
		else if (!forecast.get(0).isDaytime) {
			return "Grey";
		}
		else {
			return "Beige";
		}
	}

	// this function sets up the pane. it sets the padding, back button, the hbox, and the color based on the
	// parameters and the current weather forecast
 	// the parameters are an ArrayList of the periods for the forecast, a BorderPane for the pane, a Button for the
	// back button, and an HBox for everything inside the pane
 	// it has no returns
	private void paneSetup(ArrayList<Period> forecast, BorderPane pane, Button backButton, HBox hbox) {
		pane.setPadding(new Insets(20)); // adds the padding the pane
		pane.setCenter(hbox); // sets the hbox as the center of the pane
		pane.setBottom(backButton); // ensure the back button will be on the bottom
		backButton.setStyle("-fx-font-size: 18px; -fx-background-color: silver");

		String color = getColor(forecast);
		pane.setStyle("-fx-background-color: " + color + ";");
	}

	// this function sets up the text for the more info GUI screen. it uses the current forecast to get the
	// information and places it into Text areas for the screen
 	// the parameters are an ArrayList of the periods for the forecast
 	// it has no returns
	private void moreInfoTextSetup(ArrayList<Period> forecast) {
		// gets and sets the temperature into a Text area
		temperature = new Text();
		temperature.setStyle("-fx-background-color: transparent; -fx-font-size: 34px; ");
		temperature.setText(String.valueOf(forecast.get(0).temperature + "° "
				+ forecast.get(0).temperatureUnit));

		// gets and sets the wind speed and direction into Text ares
		windSpeed = new Text();
		windDirection = new Text();
		windSpeed.setStyle("-fx-background-color: transparent; -fx-font-size: 24px; ");
		windDirection.setStyle("-fx-background-color: transparent; -fx-font-size: 24px; ");
		windSpeed.setText("Today's wind speed is\n" +
				String.valueOf(forecast.get(0).windSpeed) + "\n\n");
		windDirection.setText("The direction of the wind is\n" +
				String.valueOf(forecast.get(0).windDirection + "\n\n"));

		// gets and sets the detailed forecast into a Text area
		detailedWeather = new Text();
		detailedWeather.setStyle("-fx-background-color: transparent; -fx-font-size: 24px; ");
		detailedWeather.setText(String.valueOf(forecast.get(0).detailedForecast));
		String detailedForecast = String.valueOf(forecast.get(0).detailedForecast);
		addNewlines(forecast, 4, detailedWeather, detailedForecast);

		String color = getColor(forecast);

		// creates a scroll pane for the detailed forecast
		scroll = new ScrollPane(detailedWeather);
		scroll.setFitToWidth(true);
		scroll.setPannable(true);
		scroll.setPrefViewportHeight(300);
		scroll.setStyle("-fx-background-color: " + color + ";");
	}

	// this function sets up the main screen of the GUI. it calls all the helper function to set up all the elements
	// being used and then creates the screen using VBoxes and HBoxes
	// the parameters are an ArrayList of the periods for the forecast
	// it has no returns
	public void mainPageScene(ArrayList<Period> forecast) {
		// calls up the main page setup function to create and fill and the elements
		setMainText(forecast);
		dateAndTimeText();
		setMainButtons();
		setMainSpacing();
		ImageView cat = getImage(forecast, true);
		ImageView icon = getImage(forecast, false);

		// creates Vboxes and HBoxes to hold all the gathered information neatly
		descriptionVBox = new VBox(20, weather, precipitation, moreInfo);

		temperatureHBox = new HBox(20, icon, temperature, space5, time, space6, date);
		weatherHBox = new HBox(descriptionVBox, cat);
		daysHBox = new HBox(20, space1, threeDay, space2, fiveDay, space3, sevenDay);

		// creates a final VBox to hold everything on the main screen
		v1 = new VBox(20, temperatureHBox, weatherHBox, space4, daysHBox);
		v1.setPadding(new Insets(20, 0, 0, 0));
	}

	// this function creates the scene for the x-day forecast. it uses the parameters to determine how many days to create
	// the scene for and uses the helper functions to create the scene with all the required information
 	// the parameters are a Stage for the primary stage, an ArrayList of the periods for the forecast, a Scene for the scene
	// for the back button to return to, an integer for the number of days to display in the x-day forecast, and integer
	// for the font size, and a Button for the back button
 	// it returns a Scene to the newly created scene for the x-day forecast screen
	private Scene getDaysScene(Stage primaryStage, ArrayList<Period> forecast, Scene scene, int numDays, int fontSize, Button backButton) {
		// gets and sets the temperature into a Text area
		temperature = new Text();
		temperature.setStyle("-fx-background-color: transparent; -fx-font-size: 34px; ");
		temperature.setText(String.valueOf(forecast.get(0).temperature + "° "
				+ forecast.get(0).temperatureUnit));

		// sets the date and time and gathers the images to use
		dateAndTimeText();
		ImageView cat = getImage(forecast, true);
		ImageView icon = getImage(forecast, false);

		// sets all the data into VBoxes and HBoxes
		HBox timeDate = new HBox(20, time, date);
		HBox temp = new HBox(10, icon, temperature);
		VBox currentDayDetails = new VBox(20, timeDate, temp, cat);
		VBox numDayForecast = numDaysForecast(forecast, numDays, fontSize, currentDayDetails);
		HBox numDayScene = new HBox(currentDayDetails, numDayForecast);

		// creates the pane for the screen and adds all the previously created Vboxes and HBoxes
		BorderPane pane = new BorderPane();
		paneSetup(forecast, pane, backButton, numDayScene);
		backButtonAction(backButton, primaryStage, scene);

		return new Scene(pane, 700,700);
	}

	// this function creates the more info GUI screen. it gathers the data for that period and organizes into a neat
	// way to display it to the user.
 	// the parameters are a Stage for the primary stage, an ArrayList of the periods for the forecast, a Scene for the scene
	// for the back button to return to
 	// it returns a Scene to the newly created scene for the more info screen
	private Scene moreInfoScene(Stage primaryStage, ArrayList<Period> forecast, Scene scene) {
		moreInfoTextSetup(forecast); // sets up the text for the screen

		// sets the date and time and gathers the images to use
		dateAndTimeText();
		ImageView cat = getImage(forecast, true);
		cat.setRotate(-15);
		ImageView icon = getImage(forecast, false);

		// rotates the cat image so it looks like its "dancing"
		// does this by initially setting the image -15 degrees to the left and then rotating it back and forth
		// the entire time it is on the screen
		RotateTransition rotate = new RotateTransition();
		rotate.setDuration(Duration.millis(1000));
		rotate.setNode(cat);
		rotate.setByAngle(45);
		rotate.setCycleCount(Animation.INDEFINITE);
		rotate.setAutoReverse(true);
		rotate.play();

		// sets up the forecast logo picture
		Image image = new Image("forecast.png");
		ImageView forecastPic = new ImageView();
		forecastPic.setImage(image);
		forecastPic.setFitWidth(280);
		forecastPic.setPreserveRatio(true);
		forecastPic.setSmooth(true);

		// sets all the gathered data into VBoxes and HBoxes
		HBox timeDate = new HBox(20, time, date);
		HBox temp = new HBox(10, icon, temperature);
		VBox currentDayDetails = new VBox(20, timeDate, temp, cat);
		VBox info = new VBox(windSpeed, windDirection, scroll, forecastPic);
		HBox infoScene = new HBox(currentDayDetails, info);

		// creates the pane for the screen and adds all the previously created Vboxes and HBoxes
		BorderPane pane = new BorderPane();
		paneSetup(forecast, pane, backButtonMoreInfo, infoScene);
		backButtonAction(backButtonMoreInfo, primaryStage, scene);

		return new Scene(pane, 700,700);
	}

	// this function starts the GUI. it creates the main screen by calling the helper function, and creates the pane for
	// all the GUIs. it sets the pane to the primary stage and shows it so the user can see the GUI
 	// the parameters are a Stage to the primary stage
 	// it has no returns
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("The Weather");
		// creates the Array List of the periods by calling the API for Chicago and setting each period into list
		ArrayList<Period> forecast = WeatherAPI.getForecast("LOT",77,70);
		// checks if the forecast correctly loaded or not
		if (forecast == null){
			throw new RuntimeException("Forecast did not load");
		}

		// creates all the back buttons for the screens
		backButton3 = new Button("<- Back");
		backButton3.setPadding(new Insets(10, 10, 10, 10));
		backButton5 = new Button("<- Back");
		backButton5.setPadding(new Insets(10, 10, 10, 10));
		backButton7 = new Button("<- Back");
		backButton7.setPadding(new Insets(10, 10, 10, 10));
		backButtonMoreInfo = new Button("<- Back");
		backButtonMoreInfo.setPadding(new Insets(10, 10, 10, 10));

		mainPageScene(forecast); // creates the main screen

		// creates a pane for the screen
		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(20));
		pane.setCenter(v1);

		String color = getColor(forecast);
		pane.setStyle("-fx-background-color: " + color + ";");

		Scene scene = new Scene(pane, 700,700);

		setMainButtonActions(primaryStage, forecast, scene); // sets the actions for the main screen

		// locks the size of the screen, sets the scene, and shows the stage so the user can see the GUI
		primaryStage.resizableProperty().setValue(Boolean.FALSE);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
