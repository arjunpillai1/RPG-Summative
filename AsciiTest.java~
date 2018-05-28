/* [DisplayGrid.java]
 * A Small program for Display a 2D String Array graphically
 * @author Mangat
 */

// Graphics Imports
import javax.swing.*;
import java.awt.*;
import java.util.Scanner;
import java.io.File;

class AsciiTest {   
  
  private static JFrame frame;
  private static int maxX,maxY, GridToScreenRatio;
  String[][] world;
  
  
  
  
  AsciiTest(String[][] world) { 
    this.world = world;
    
    maxX = Toolkit.getDefaultToolkit().getScreenSize().width;
    maxY = Toolkit.getDefaultToolkit().getScreenSize().height;
    GridToScreenRatio = maxY / (world.length+1);  //ratio to fit in screen as square map
    
    System.out.println("Map size: "+world.length+" by "+world[0].length + "\nScreen size: "+ maxX +"x"+maxY+ " Ratio: " + GridToScreenRatio);
    
    frame = new JFrame("Map of World");
    
    GridAreaPanel worldPanel = new GridAreaPanel();
    
    frame.getContentPane().add(BorderLayout.CENTER, worldPanel);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
    frame.setVisible(true);
  }
  
  
  public void refresh() { 
    frame.repaint();
  }
  
  
  
  class GridAreaPanel extends JPanel {
    public void paintComponent(Graphics g) {        
      //super.repaint();
      maxX = Toolkit.getDefaultToolkit().getScreenSize().width;
      maxY = Toolkit.getDefaultToolkit().getScreenSize().height;
      
      setDoubleBuffered(true); 
      Color myGreen = new Color(11, 215, 72);
      Color myBeige = new Color(245,245,220);
      Color myBeigeFemale = new Color(227, 220, 192);
      Color myBrown = new Color(176, 102, 96);
      Color myBlue = new Color(0, 168, 252);
      Color mySaddleBrown = new Color(139,69,19);
      Color wood = new Color(102, 51, 0);
      Color floor = new Color(20, 80, 40); 
      Color tree = new Color(20, 51, 6);
      
      
      for(int i = 0; i < world.length;i=i+1)
      { 
        for(int j = 0; j<world.length;j=j+1) 
        { 
          
          
          
          if (world[i][j].equals("E")) {  
            g.setColor(myGreen); //sets colour for printing organism
            g.fillRect(j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio);
            
          } else if (world[i][j].equals("I")) {
            g.setColor(Color.RED); //sets colour for printing organism
            g.fillRect(j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio);
            
          } else if (world[i][j].equals("M")) {
            g.setColor(Color.GREEN); //sets colour for printing organism
            g.fillRect(j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio);
            
          } else if (world[i][j].equals("-")) {
            g.setColor(myBrown); //sets colour for printing organism
            g.fillRect(j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio);
            
          } else if (world[i][j].equals("D")) {
            g.setColor(mySaddleBrown); //sets colour for printing organism
            g.fillRect(j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio);
            
          } else if (world[i][j].equals("r")) {
            g.setColor(myBlue); //sets colour for printing organism
            g.fillRect(j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio);
            
          } else if (world[i][j].equals("S")) {
            g.setColor(myBlue); //sets colour for printing organism
            g.fillRect(j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio);
            
          } else if (world[i][j].equals("C")) {
            g.setColor(Color.BLACK); //sets colour for printing organism
            g.fillRect(j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio);   
          } else if (world[i][j].equals("H")) {
            g.setColor(floor); //sets colour for printing organism
            g.fillRect(j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio); 
          } else if (world[i][j].equals("F")) {
            g.setColor(wood); //sets colour for printing organism
            g.fillRect(j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio);  
          } else if (world[i][j].equals("T")) {
            g.setColor(tree); //sets colour for printing organism
            g.fillRect(j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio); 
          } 
          
        }
      }
      
    }
    
  }
}//end of GridAreaPanel
