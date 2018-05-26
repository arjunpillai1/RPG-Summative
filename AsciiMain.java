/* [GridTest.java]
 * A program to demonstrate usage of DisplayGrid.java.
 * @author Mangat
 */

import java.util.Scanner;
import java.io.File;


class AsciiMain { 
  
  
  public static void main(String[] args) throws Exception { 
    
    
    File map = new File("map.txt");
    Scanner fileIn = new Scanner(map);
    
    String value = fileIn.nextLine();
    Object[][] world = new Object[106][106];
    
    for (int a = 0; a < world.length - 1; a++) { // draws first row of the map to avoid errors
      if (value.substring(a, a + 1).equals("S")) {
        world[0][a] = new Water();
      }
    }
    
    for (int i = 1; i < world.length - 1; i++) { // draws the rest of the map in the array
      value = fileIn.nextLine();
      for (int j = 0; j < world.length - 1; j++) {
        if (value.substring(j, j + 1).equals("S") || (value.substring(j, j + 1).equals("r"))) { 
          world[i][j] = new Water();
        } else if (value.substring(j, j + 1).equals("E")) {
          world[i][j] = new Grass();
        } else if (value.substring(j, j + 1).equals("M")) {
          world[i][j] = new FireGrass();
        } else if (value.substring(j, j + 1).equals("D")) {
          world[i][j] = new PoisonGrass();
        } else if (value.substring(j, j + 1).equals("I")) {
          world[i][j] = new FrostGrass();
        } else if (value.substring(j, j + 1).equals("B")) {
          world[i][j] = new Bridge();
        } else if (value.substring(j, j + 1).equals("c")) {
          world[i][j] = new CaveWall();
        } else if (value.substring(j, j + 1).equals("T")) {
          world[i][j] = new Tree();
        } else if (value.substring(j, j + 1).equals("H")) {
          world[i][j] = new Wall();
        } else if (value.substring(j, j + 1).equals("C")) {
          world[i][j] = new CastleWall();
        } else if (value.substring(j, j + 1).equals("F")) {
          world[i][j] = new HouseFloor();
        } else if (value.substring(j, j + 1).equals("-")) {
          world[i][j] = new Dirt();
        } else if (value.substring(j, j + 1).equals("E")) {
          world[i][j] = new Grass();
        } else if (value.substring(j, j + 1).equals("L")) {
          world[i][j] = new Chest();
        }  
      }
    }
    
    
    
    // Initialize Map
    //moveItemsOnGrid(map);
    
    // display the fake grid on Console
    //DisplayGridOnConsole(map);
    
    //Set up Grid Panel
    AsciiTest grid = new AsciiTest(world);
    
  }
  
  
}