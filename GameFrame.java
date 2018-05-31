/**
 * This template can be used as reference or a starting point
 * for your final summative project
 * @author Mangat
 **/

//Graphics &GUI imports
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.*;
import java.awt.*;

//Keyboard imports
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//Mouse imports
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

class GameFrame extends JFrame { 
  private static JFrame frame;
  private static int maxX,maxY, GridToScreenRatio;
  Object[][] world;
  
  //class variable (non-static)
  static double x, y;
  static GameAreaPanel gamePanel;
  
  
  
  //Constructor - this runs first
  GameFrame(Object[][] world) { 
    super("My Game");  
    this.world = world;
    
    maxX = Toolkit.getDefaultToolkit().getScreenSize().width;
    maxY = Toolkit.getDefaultToolkit().getScreenSize().height;
    GridToScreenRatio = maxY / (10);  //ratio to fit in screen as square map
    
    System.out.println("Map size: "+world.length+" by "+world[0].length + "\nScreen size: "+ maxX +"x"+maxY+ " Ratio: " + GridToScreenRatio);
    
    frame = new JFrame("Map of World");
    
    GameAreaPanel worldPanel = new GameAreaPanel();
    
    frame.getContentPane().add(BorderLayout.CENTER, worldPanel);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
    frame.setVisible(true);
    
    
    // Set the frame to full screen 
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
    // this.setUndecorated(true);  //Set to true to remove title bar
    //frame.setResizable(false);
    
    
    
    //Set up the game panel (where we put our graphics)
    gamePanel = new GameAreaPanel();
    this.add(new GameAreaPanel());
    
    MyKeyListener keyListener = new MyKeyListener();
    this.addKeyListener(keyListener);
    
    MyMouseListener mouseListener = new MyMouseListener();
    this.addMouseListener(mouseListener);
    
    this.requestFocusInWindow(); //make sure the frame has focus   
    
    this.setVisible(true);
    
    //Start the game loop in a separate thread
    Thread t = new Thread(new Runnable() { public void run() { animate(); }}); //start the gameLoop 
    t.start();
    
  } //End of Constructor
  
  //the main gameloop - this is where the game state is updated
  public void animate() { 
    
    while(true){
      int randomInt;
      for (int m = 0; m < world.length; m++) {
        for (int n = 0; n < world.length; n++) {
          // this is for when enemies are in vision range
          
          if (world[m][n] instanceof Enemy) {
            int radX = ((Enemy)world[m][n]).getX();
            int radY = ((Enemy)world[m][n]).getY();
            Object[][] moveRadius = new Object[5][5];
            for (int x = radX - 2; x <= radX + 2; x++) {
              for (int y = radY - 2; y <= radY + 2; y++) {
                moveRadius[x%5][y%5] = world[x][y];
              }
            }
            ((Enemy)world[m][n]).move(moveRadius);
          }
        }
      }
      try{ Thread.sleep(500);} catch (Exception exc){}  //delay
      this.repaint();
    }    
  }
  
  /** --------- INNER CLASSES ------------- **/
  
  // Inner class for the the game area - This is where all the drawing of the screen occurs
  private class GameAreaPanel extends JPanel {
    public void paintComponent(Graphics g) {   
      
      
      //super.repaint();
      maxX = Toolkit.getDefaultToolkit().getScreenSize().width;
      maxY = Toolkit.getDefaultToolkit().getScreenSize().height;
      int playerX = 0;
      int playerY = 0;
      int countX = 0;
      int countY = 0;
      Object playerSight = new Object[9][9];
      
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
      
      for (int a = 0; a < world.length; a++) {
        for (int b = 0; b < world.length; b++) {
          if (world[a][b] instanceof Player) {
            playerX = a;
            ((Player)world[a][b]).setX(playerX);
            playerY = b;
            ((Player)world[a][b]).setY(playerY);
          }
        }
      }
      
      
      
      
      for(int i =  playerX - 4; i <= playerX + 4;i=i+1)
      { 
        for(int j =playerY - 4; j <= playerY + 4;j=j+1) 
        { 
          
          
          if (world[i][j] instanceof Grass) {  
            g.setColor(myGreen); //sets colour for printing organism
              g.fillRect((j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio);
            
          } else if (world[i][j] instanceof FrostGrass) {
            g.setColor(Color.RED); //sets colour for printing organism
              g.fillRect((j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio);
            
          } else if (world[i][j] instanceof FireGrass) {
            g.setColor(Color.GREEN); //sets colour for printing organism
              g.fillRect((j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio);
            
          } else if (world[i][j] instanceof Dirt) {
            g.setColor(myBrown); //sets colour for printing organism
              g.fillRect((j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio);
            
          } else if (world[i][j] instanceof PoisonGrass) {
            g.setColor(mySaddleBrown); //sets colour for printing organism
              g.fillRect((j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio);
            
          } else if (world[i][j] instanceof Water) {
            g.setColor(myBlue); //sets colour for printing organism
              g.fillRect((j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio);
            
          } else if (world[i][j] instanceof Chest) {
            g.setColor(Color.YELLOW); //sets colour for printing organism
              g.fillRect((j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio);
            
          } else if (world[i][j] instanceof CastleWall) {
            g.setColor(Color.BLACK); //sets colour for printing organism
              g.fillRect((j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio);   
            
          } else if (world[i][j] instanceof Wall) {
            g.setColor(floor); //sets colour for printing organism
              g.fillRect((j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio); 
            
          } else if (world[i][j] instanceof HouseFloor) {
            g.setColor(wood); //sets colour for printing organism
              g.fillRect((j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio);  
            
          } else if (world[i][j] instanceof Tree) {
            g.setColor(tree); //sets colour for printing organism
              g.fillRect((j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio); 
          } else if (world[i][j] instanceof CaveWall) {
            g.setColor(Color.BLACK); //sets colour for printing organism
              g.fillRect((j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio); 
          } else if (world[i][j] instanceof Player) {
            g.setColor(Color.BLACK); //sets colour for printing organism
              g.fillRect((j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio);
            
          }
          countY++;
        }
        countX++;
      }
      
    }
  }
  
  // -----------  Inner class for the keyboard listener - this detects key presses and runs the corresponding code
  private class MyKeyListener implements KeyListener {
    int playerX = 0;
    int playerY = 0;
    public void keyTyped(KeyEvent e) {  
    }
    
    public void keyPressed(KeyEvent e) {
      //System.out.println("keyPressed="+KeyEvent.getKeyText(e.getKeyCode()));
      for (int i = 0; i < world.length; i++) {
        for (int j = 0; j < world.length; j++) {
          if (world[i][j] instanceof Player) {
            playerX = i;
            playerY = j;
          }
        }
      }
      
      if (KeyEvent.getKeyText(e.getKeyCode()).equals("W")) {  //If 'W' is pressed
        ((Player)world[playerX][playerY]).move(world,1);
      } else if (KeyEvent.getKeyText(e.getKeyCode()).equals("S")) {  //If 'S' is pressed
        ((Player)world[playerX][playerY]).move(world, 2);
      } else if (KeyEvent.getKeyText(e.getKeyCode()).equals("A")) {
        ((Player)world[playerX][playerY]).move(world, 3);      
      } else if (KeyEvent.getKeyText(e.getKeyCode()).equals("D")) {
        ((Player)world[playerX][playerY]).move(world, 4);
      }
    }   
    
    public void keyReleased(KeyEvent e) {
    }
  } //end of keyboard listener
  
  // -----------  Inner class for the keyboard listener - This detects mouse movement & clicks and runs the corresponding methods 
  private class MyMouseListener implements MouseListener {
    
    public void mouseClicked(MouseEvent e) {
      System.out.println("Mouse Clicked");
      System.out.println("X:"+e.getX() + " y:"+e.getY());
    }
    
    public void mousePressed(MouseEvent e) {
      int mouseX = e.getX();
      int mouseY = e.getY();
      
    }
    
    public void mouseReleased(MouseEvent e) {
    }
    
    public void mouseEntered(MouseEvent e) {
    }
    
    public void mouseExited(MouseEvent e) {
    }
  } //end of mouselistener
  
}