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
    String[][] world = new String[100][100];
    
    for (int a = 0; a < world.length; a++) { // draws first row of the map to avoid errors
      world[0][a]= value.substring(a,a+1);
      
    }
    
    for (int i = 1; i < world.length; i++) { // draws the rest of the map in the array
      value = fileIn.nextLine();
      for (int j = 0; j < world.length; j++) {
        world[i][j] = value.substring(j,j+1);
        
      }
      
    }
    
    
    
    // Initialize Map
    //moveItemsOnGrid(map);
    
    // display the fake grid on Console
    //DisplayGridOnConsole(map);
    
    //Set up Grid Panel
    AsciiTest grid = new AsciiTest(world);
    
    //Display the grid on a Panel
//    while (true) {
//      grid.refresh();
//      
//      
//      //Small delay
//      try{ Thread.sleep(1000); }catch(Exception e) {};
//      
//      
//      // Initialize Map (Making changes to map)
//      moveItemsOnGrid(map);
//      
//      //Display the grid on a Panel
//      grid.refresh();
//    }
  }
  
  // Method to simulate grid movement
  public static void moveItemsOnGrid(String[][] map) { 
    
    for(int i = 0; i<map[0].length;i++)        
      for(int j = 0; j<map.length;j++) 
    { 
      
      map[i][j]=((int)(Math.random()* 3))+"";
    }
  }
  
  //method to display grid a text for debugging
  public static void DisplayGridOnConsole(String[][] map) { 
    for(int i = 0; i<map.length;i++){        
      for(int j = 0; j<map[0].length;j++) 
        System.out.print(map[i][j]+" ");
      System.out.println("");
    }
  }
}