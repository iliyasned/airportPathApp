// --== CS400 File Header Information ==--
// Name: Iliyas Alabdulaal
// Email: alabdulaal@wisc.edu
// Team: CN red
// TA: Evan Wireman
// Lecturer: Florian Heimerl
// Notes to Grader: None

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

//interface
interface AirportPathAppInterface{
    public void start(final Stage stage);
    public String userInput(String fromAirport, String toAirport, BackEnd<String> engine);
}

//public class
public class AirportPathApp extends Application implements AirportPathAppInterface {

    public BackEnd<String> engine;
    
    @Override
    public void start(final Stage stage){
        //loads the backend graph
        engine = new BackEnd<>();
        AirportsLoader loader = new AirportsLoader();
        loader.loadVertices("Airports.csv", engine);
        loader.loadEdges("Connections.csv", engine);

        //initializtions of borderpane and scene
        BorderPane bp = new BorderPane();
        Scene scene = new Scene (bp, 640, 750);
        stage.setTitle("Airport Paths App");

        //welcome message
        Label welcomeLabel = new Label("Welcome to Airport Paths App!");
        bp.setTop(welcomeLabel);
        welcomeLabel.setFont(new Font(30));
        bp.setAlignment(welcomeLabel, Pos.CENTER);

        //main message label on the right
        Label msgLabel = new Label("Please choose departing airport and \n arrival airport, respectively.");
        msgLabel.setFont(new Font(30));
        bp.setRight(msgLabel);
        bp.setAlignment(msgLabel, Pos.CENTER_RIGHT);

        //exit button
        Button exit = new Button("Exit");
        exit.setPrefWidth(100);
        exit.setStyle("-fx-background-color: #ff0000; ");
        exit.setOnAction(e -> {
            Platform.exit();
        });
        bp.setBottom(exit);
        bp.setAlignment(exit, Pos.TOP_RIGHT);


        //arraylist two buttons to keep track of when two buttons are pressed
        ArrayList<Button> twoButtons = new ArrayList<>();
        //vbox to place all buttons
        VBox vBox = new VBox();
        bp.setCenter(vBox);
        bp.setAlignment(vBox, Pos.CENTER_RIGHT);

        //iterating through key set of hashtable
        Set<String> setOfAirports = engine.getAllVertices().keySet();
        for(String key : setOfAirports){

            //for each key create a new button with the key and add to vbox
            Button button = new Button(key);
            button.setPrefWidth(100);
            vBox.getChildren().add(button);
            vBox.setSpacing(1.5);
            button.setStyle("-fx-background-color: #9e7bb5; ");

            button.setOnAction(e ->{
                //when no buttons have been pressed yet ask user to press another airport
                if(twoButtons.size() == 0){ 
                    twoButtons.add(button); 
                    msgLabel.setText("Please select arrival airport");
                }
                //when one button already pressed get total time and cost with methods below and show user
                else if(twoButtons.size() == 1) {
                    twoButtons.add(button);
                    //gets the time taken for total
                    String time = userInput(twoButtons.get(0).getText(), twoButtons.get(1).getText(), engine);
                    //gets the total cost
                    int cost = getCost(twoButtons.get(0).getText(), twoButtons.get(1).getText(), engine);
                    //gets the total path
                    List<String> shortestPath = engine.shortestPath(twoButtons.get(0).getText(), twoButtons.get(1).getText());
                    String totalPath = shortestPath.toString().replace("[", "").replace("]", "").replaceAll(",", "->");

                    msgLabel.setText(" From: " + twoButtons.get(0).getText() + " To: " + twoButtons.get(1).getText() +
                     "\n\n Total Path: "+ totalPath + " \n Total time: " + time + "\n Total Cost: $" + cost +
                     "\n\n To find a new route, please \n select a new departing airport");
                     msgLabel.setFont(new Font(30));
                     //clear arraylist to allow for another route
                    twoButtons.clear();
                    //keep focus on exit
                    exit.requestFocus();
                }
            });
        }

        stage.setScene(scene);
        stage.show();
    }
    /** 
     * Takes departing and arriving airports as parameters and returns the time for them
     * 
     * @param fromAirport deprating airport
     * @param toAirport arriving ariport
     * @return String representation of time taken between them 
     */
    @Override
    public String userInput(String fromAirport, String toAirport, BackEnd<String> engine){
        return engine.calcHours(fromAirport, toAirport) + " hours";
    }
    /**
     * Returns the cost of the total flight itenerary
     * Cost = path cost (minutes) * 5
     * i.e., each minute in the air costs $5
     * 
     * @param fromAirport deprating airport
     * @param toAirport arriving ariport
     * @return Cost of going from fromAirport to toAirport
     */
    public int getCost(String fromAirport, String toAirport, BackEnd<String> engine){
        return engine.getPathCost(fromAirport, toAirport) * 5;
    }

    public static void main(String[] args) {
        Application.launch();
    }
}



