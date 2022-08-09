import java.io.FileNotFoundException;
import java.util.List;

// Interface
interface AirportInterface {
    public String getName();
    public String getCode();
    public String getLocation();
}

public class Airport implements AirportInterface {

  // Name for the airport
  private String name;
  // Code for the airport
  private String code;
  // Name of the location of the airport
  private String location;

  public Airport(String name, String code, String location) {
    this.name = name;
    this.code = code;
    this.location = location;
  }

  public String getNamewithCode(String code) throws FileNotFoundException {
    AirportsLoader loader = new AirportsLoader();
    List<AirportInterface> airportlist = loader.loadAirports("Airports.csv");
    for (int i = 0; i < airportlist.size(); i++) {
      if (airportlist.get(i).getCode().equals(code)) {
        return airportlist.get(i).getName();
      }
    }
    return null;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public String getCode() {
    return this.code;
  }

  @Override
  public String getLocation() {
    return this.location;
  }
}
