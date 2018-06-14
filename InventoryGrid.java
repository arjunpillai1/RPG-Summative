/* [InventoryGrid.java]
 * A Small program for Display a 2D String Array graphically
 * @author Mangat
 */

// Graphics Imports
import javax.swing.*;
import java.awt.*;


class InventoryGrid { 
  
  private JFrame frame;
  private int maxX,maxY, GridToScreenRatio;
  private Inventory bag;
  
  InventoryGrid(Inventory bag) { 
    this.bag = bag;
    
    maxX = Toolkit.getDefaultToolkit().getScreenSize().width;
    maxY = Toolkit.getDefaultToolkit().getScreenSize().height;
    GridToScreenRatio = maxY / (bag.amount()+1);  //ratio to fit in screen as square map
    
    // System.out.println("Map size: "+bag.amount()+" by "+world[0].length + "\nScreen size: "+ maxX +"x"+maxY+ " Ratio: " + GridToScreenRatio);
    
    this.frame = new JFrame("Map of World");
    
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
      
      setDoubleBuffered(true); 
      g.setColor(Color.BLACK);
      
      for(int i = 0; i<bag.amount();i=i+1)
      { 
        if (bag.getState(i) == true){
          g.setColor(Color.BLACK);
        }else if (bag.call(i) instanceof RustySword)    //This block can be changed to match character-color pairs
          g.setColor(Color.RED);
        else if (bag.call(i) instanceof FalconSword) 
          g.setColor(Color.BLUE);
        else if (bag.call(i) instanceof TinArmour) 
          g.setColor(Color.GREEN);
        if (i%2==0){
          g.fillRect((i%2)*GridToScreenRatio, (i/2)*GridToScreenRatio, GridToScreenRatio, GridToScreenRatio);
          g.setColor(Color.BLACK);
          g.drawRect((i%2)*GridToScreenRatio, (i/2)*GridToScreenRatio, GridToScreenRatio, GridToScreenRatio);
        } else
          g.fillRect((i%2)*GridToScreenRatio, ((i-1)/2)*GridToScreenRatio, GridToScreenRatio, GridToScreenRatio);
        g.setColor(Color.BLACK);
        g.drawRect((i%2)*GridToScreenRatio, ((i-1)/2)*GridToScreenRatio, GridToScreenRatio, GridToScreenRatio);
      }
    }
  }
}//end of GridAreaPanel