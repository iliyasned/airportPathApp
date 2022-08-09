import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.assertTrue;


public class AirportPathAppTests {
    public BackEnd<String> graph;
    public AirportPathApp ui;
    
    /**
     * Instantiate graph from last week's shortest path activity.
     */
    @BeforeEach
     public void createGraph() {
        graph = new BackEnd<>();
        // insert vertices 
        graph.insertVertex("ORD");
        graph.insertVertex("ATL");
        graph.insertVertex("DEN");
        graph.insertVertex("DAL");
        graph.insertVertex("LAX");
        graph.insertVertex("PHX");
        graph.insertEdge("ORD","ATL",500);
        graph.insertEdge("ORD","DEN",300);
        graph.insertEdge("ORD","DAL",200);
        graph.insertEdge("ATL","LAX",300);
        graph.insertEdge("ATL","DEN",100);
        graph.insertEdge("DEN","ATL",400);
        graph.insertEdge("DEN","PHX",200);
        graph.insertEdge("DAL","LAX",300);
        graph.insertEdge("LAX","ORD",500);
        graph.insertEdge("PHX","ORD",200);
        graph.insertEdge("PHX","DAL",100);
        ui = new AirportPathApp();
    }

    //backend tests
    /**
     * Checks the path from different locations to different destinations
     */
    @Test
    public void testBackEndPath() {
      assertTrue(graph.shortestPath("ORD", "LAX").toString().equals(
          "[ORD, DAL, LAX]"
      ));
      assertTrue(graph.shortestPath("ATL","PHX").toString().equals("[ATL, DEN, PHX]"));
      assertTrue(graph.shortestPath("PHX","ORD").toString().equals("[PHX, ORD]"));
    }

    /*
     * confirms that the distance computed for this node in last week's activity is correct
     */
    @Test
    public void testBackEndDistance() {
      assertTrue(graph.getPathCost("ORD", "LAX") == 500);
      assertTrue(graph.getPathCost("PHX", "DEN") == 500);
      assertTrue(graph.getPathCost("DEN", "ATL") == 400);
    }
    /*
     * test method to confirm the predecessor for F is C when going from A to F
     */
    @Test
    public void testBackEndCalcHours() {
      assertTrue(graph.calcHours("ORD", "LAX").equals("8:20"));
      assertTrue(graph.calcHours("PHX","DAL").equals("1:40"));
      assertTrue(graph.calcHours("LAX","DEN").equals("13:20"));
    }

	
	//frontend tests
  // only two tests for two user inputs, two others will be done by integration manager
  /**
   * tests the userInput method for the frontend
   */
	@Test
  public void testFrontEndUserInput(){
    assertTrue(ui.userInput("DAL", "ORD", graph).equals("13:20 hours"));
    assertTrue(ui.userInput("DEN", "DAL", graph).equals("5:00 hours"));
    assertTrue(ui.userInput("ORD", "LAX", graph).equals("8:20 hours"));
  }
  /**
   * tests the getCost method for the frontend
   */
  @Test 
  public void testFrontEndGetCost(){
    assertTrue(ui.getCost("DAL", "ORD", graph) == 4000);
    assertTrue(ui.getCost("DEN", "DAL", graph) == 1500);
    assertTrue(ui.getCost("ORD", "LAX", graph) == 2500);
  }
	//data wrangler tests
	
	//integration manager tests
    /**
     * Test method to confirm backend implementation works
     */
    @Test
    public void integrationTestBackEnd() {
        //testing shortest path
        assertTrue(graph.shortestPath("PHX", "ATL").toString().equals("[PHX, ORD, ATL]"));
        //testing a path's distance
        assertTrue(graph.getPathCost("PHX","ATL") == 700);
        //testing a path's duration
        assertTrue(graph.calcHours("PHX", "ATL").equals("11:40"));

    }

    /**
     * Test method to confirm Data Wrangler implementation works
     */
    @Test
    public void integrationTestDataWrangler() {
        Airport test = new Airport("Testing Regional Airport", "TST", "Test");
        assertTrue(test.getName().equals("Testing Regional Airport"));
        assertTrue(test.getCode().equals("TST"));
        assertTrue(test.getLocation().equals("Test"));
    }
    /**
     * 1st test method to confirm frontend implementation works
     */
    @Test
    public void firstIntegrationTestFrontEnd() {

    }
    /**
     * 2nd test method to confirm frontend implementation works
     */
    @Test
    public void secondIntegrationTestFrontEnd() {
        
    }
	}
