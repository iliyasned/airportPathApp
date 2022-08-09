import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

//Interface
interface AirportsLoaderInterface {
    // Load airports (i.e. vertices)
    public List<AirportInterface> loadAirports(String airportsCSV) throws FileNotFoundException;
    // Load airport nodes and inserts them into the backend
    public void loadVertices(String airportsCSV, BackEnd<String> backend);
    // Load airport edges and inserts them into the backend
    public void loadEdges(String connectionsCSV, BackEnd<String> backend);
}

public class AirportsLoader implements AirportsLoaderInterface {

  public List<AirportInterface> loadAirports(String airports) throws FileNotFoundException {

    // LinkedList of Airports
    List<AirportInterface> airportList = new LinkedList<>();
    // File object to scan through
    File file = new File(airports);
    Scanner scnr = new Scanner(file);

    // Indexes of the columns
    int nameIndex = 0;
    int codeIndex = 0;
    int locationIndex = 0;

    // Creating the first line
    String currentLine = scnr.nextLine();
    String[] firstLine = currentLine.split(",");

    // for loop to iterate through columns
    for (int i = 0; i < firstLine.length; i++) {
      if (firstLine[i].contains("Airport")) {
        nameIndex = i;
      } else if (firstLine[i].contains("Code")) {
        codeIndex = i;
      } else if (firstLine[i].contains("Location")) {
        locationIndex = i;
      }
    }

    // while scanner has next
    while (scnr.hasNextLine()) {
      // Create an oversize array
      String[] airportArray = new String[150];
      // Sets current Line to the line after it
      currentLine = scnr.nextLine();
      // Split the line by using commas to separate the values
      airportArray = currentLine.split(",");
      // Sets the Airport Names, Codes, and Locations
      String airportName = airportArray[nameIndex];
      String airportCode = airportArray[codeIndex];
      String airportLocation = airportArray[locationIndex];
      // Creates new airport object and adds it to the airport list
      Airport newAirport = new Airport(airportName, airportCode, airportLocation);
      airportList.add(newAirport);
    }
    return airportList;
  }



  public void loadVertices(String airportsCSV, BackEnd<String> backend) {
    try {
      List<AirportInterface> listAirport = loadAirports(airportsCSV);
      for (int i = 0; i < listAirport.size(); i++) {
        backend.insertVertex(listAirport.get(i).getCode().trim());
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public void loadEdges(String connections, BackEnd<String> backend) {
    // LinkedList of Airports
    List<AirportInterface> airportList = new LinkedList<>();
    // File object to scan through
    File file = new File(connections);
    Scanner scnr;
    try {
      scnr = new Scanner(file);

    // Indexes of the columns
    int departingIndex = 0;
    int arrivingIndex = 0;
    int timeIndex = 0;

    // Creating the first line
    String currentLine = scnr.nextLine();
    String[] firstLine = currentLine.split(",");


    //for loop to iterate through columns
    for (int i = 0; i < firstLine.length; i++) {
      if (firstLine[i].contains("Departing Airport")) {
        departingIndex = i;
      } else if (firstLine[i].contains("Arriving Airport")) {
        arrivingIndex = i;
      } else if (firstLine[i].contains("Time")) {
        timeIndex = i;
      }
    }
    
    while (scnr.hasNextLine()) {
      // Create an oversize array
      String[] connectionArray = new String[150];
      // Sets current Line to the line after it
      currentLine = scnr.nextLine();
      // Split the line by using commas to separate the values
      connectionArray = currentLine.split(",");
      // Sets the Airport Names, Codes, and Locations
      String departing = connectionArray[departingIndex].trim();
      String arriving = connectionArray[arrivingIndex].trim();
      int time = Integer.valueOf(connectionArray[timeIndex].trim());
      // Creates new airport object and adds it to the airport list
      backend.insertEdge(departing, arriving, time);
    }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

  }


}




