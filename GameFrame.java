
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
  Object[] sideQuests = new Object[6];
  Object mainQuests = new Object();
  Object[] activeQuests = new Object[11];
  
  //class variable (non-static)
  static double x, y;
  static GameAreaPanel gamePanel;
  
  
  
  
  //Constructor - this runs first
  GameFrame(Object[][] world, Object[] sideQuests, Object mainQuests, Object[] activeQuests) { 
    super("My Game");  
    this.world = world;
    
    maxX = Toolkit.getDefaultToolkit().getScreenSize().width;
    maxY = Toolkit.getDefaultToolkit().getScreenSize().height;
    GridToScreenRatio = maxY / (10);  //ratio to fit in screen as square map
    
    System.out.println("Map size: "+world.length+" by "+world[0].length + "\nScreen size: "+ maxX +"x"+maxY+ " Ratio: " + GridToScreenRatio);
    
    frame = new JFrame("Map of World");
    
    //GameAreaPanel worldPanel = new GameAreaPanel();
    gamePanel = new GameAreaPanel();
    frame.getContentPane().add(BorderLayout.CENTER, gamePanel);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
    frame.setVisible(false);
    
    
    // Set the frame to full screen 
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
    // this.setUndecorated(true);  //Set to true to remove title bar
    //frame.setResizable(false);
    
    this.sideQuests = sideQuests;
    this.mainQuests = mainQuests;
    this.activeQuests = activeQuests;
    
    //Set up the game panel (where we put our graphics)
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
      for (int i = 0; i < world.length; i++) {
        for (int j = 0; j < world.length; j++) {
          // this is for when enemies are in vision range
          
          if (world[i][j] instanceof Player) {
            int playX = ((Player)world[i][j]).getX();
            int playY = ((Player)world[i][j]).getY();
            for (int m = playX - 4; m < playX+5; m++) {
              for (int n = playY - 4; n < playY+5; n++) {
                if (world[m][n] instanceof Enemy) {
                  if (((Enemy)world[m][n]).getHealth() <= 0) {
                    ((Enemy)world[m][n]).death(world, m, n);
                  } else {
                    ((Enemy)world[m][n]).move(world, m, n);
                  }
                }
              }
            }
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
      
      
      super.paintComponent(g);
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
      Color bandit = new Color(139, 60, 100);
      Color archer = new Color(11, 110, 80);
      Color farmer = new Color(176, 102, 84);
      
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
          
          // Environment
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
            
            //NPCs  
          } else if (world[i][j] instanceof NPC) {
            g.setColor(farmer); //sets colour for printing organism
            g.fillRect((j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio);
            g.setColor(Color.WHITE);
            g.drawString(((Character)world[i][j]).getName(), (j - (j - (countY %9))) * GridToScreenRatio + 5, (i - (i - countX)) * GridToScreenRatio + 8);
            //Enemies
          } else if (world[i][j] instanceof Bandit) {
            g.setColor(bandit); //sets colour for printing organism
            g.fillRect((j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio);
            g.setColor(Color.WHITE);
            g.drawString(((Character)world[i][j]).getName(), (j - (j - (countY %9))) * GridToScreenRatio + 5, (i - (i - countX)) * GridToScreenRatio + 8);
          }
          else if (world[i][j] instanceof Archer) {
            g.setColor(archer); //sets colour for printing organism
            g.fillRect((j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio);
            g.setColor(Color.WHITE);
            g.drawString(((Character)world[i][j]).getName(), (j - (j - (countY %9))) * GridToScreenRatio + 5, (i - (i - countX)) * GridToScreenRatio + 8);
          }
          //Player
          else if (world[i][j] instanceof Player) {
            g.setColor(Color.BLACK); //sets colour for printing organism
            g.fillRect((j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio);
            g.setColor(Color.WHITE);
            g.drawString(((Character)world[i][j]).getName(), (j - (j - (countY %9))) * GridToScreenRatio + 5, (i - (i - countX)) * GridToScreenRatio + 8);
            g.drawString("Lv:" + ((Player)world[i][j]).getLvl(), (j - (j - (countY %9))) * GridToScreenRatio + 30, (i - (i - countX)) * GridToScreenRatio + 30);
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
      int playerX = 0;
      int playerY = 0;
      int mouseX = e.getX();
      int mouseY = e.getY();
      int yToTile = ((mouseX / GridToScreenRatio) - 4) + playerX;
      int xToTile = ((mouseY / GridToScreenRatio) - 4) + playerY;
      
      for (int i = 0; i < world.length; i++) {
        for (int j = 0; j < world.length; j++) {
          if (world[i][j] instanceof Player) {
            playerX = i;
            playerY = j;
          }
        }
      }
      
      yToTile = ((mouseX / GridToScreenRatio) - 4) + playerY;
      xToTile = ((mouseY / GridToScreenRatio) - 4) + playerX;
      
      interact(world[playerX][playerY], world[xToTile][yToTile], world);
      
      
      //System.out.println(xToTile + " " + yToTile);
    }
    
    public void interact(Object player, Object interactable, Object[][] world) {
      if (interactable instanceof Enemy) {
        ((Player)player).attack(((Enemy)interactable));
        
        //Quests interaction
        if (((Enemy)interactable).getHealth() <= 0) {
          if (interactable instanceof Bandit) {
            if (((Quest)sideQuests[0]).getActive() && !((Quest)sideQuests[0]).getComplete()) {
              ((Quest)sideQuests[0]).setComplete(((Quest)sideQuests[0]).updateObjective(1));
              if (((Quest)sideQuests[0]).getComplete()) {
                System.out.println("complete");
                ((Player)player).setExp(((Quest)sideQuests[0]).getXPReward());
                System.out.println("You have gained : " + ((Quest)sideQuests[0]).getXPReward() + " XP");
                System.out.println("You have gained : something");
              }
            }
          }
          
          if (interactable instanceof Archer) {
            if (((Quest)mainQuests).getActive() && !((Quest)mainQuests).getComplete()) {
              ((Quest)mainQuests).setComplete(((Quest)mainQuests).updateObjective(1));
              if (((Quest)mainQuests).getComplete()) {
                System.out.println("complete");
                ((Player)player).setExp(((Player)player).getExp() + ((Quest)mainQuests).getXPReward());
                System.out.println("You have gained : " + ((Quest)mainQuests).getXPReward() + " XP");
                System.out.println("You have gained : something");
              }
            }
          }
          
                    if (interactable instanceof Bandit) {
            if (((Quest)mainQuests).getActive() && !((Quest)mainQuests).getComplete()) {
              ((Quest)mainQuests).setComplete(((Quest)mainQuests).updateObjective(1));
              if (((Quest)mainQuests).getComplete()) {
                System.out.println("complete");
                ((Player)player).setExp(((Player)player).getExp() + ((Quest)mainQuests).getXPReward());
                System.out.println("You have gained : " + ((Quest)mainQuests).getXPReward() + " XP");
                System.out.println("You have gained : something");
              }
            }
          }
          //Poison Quest (huntquestB/(1 in index)
          else if (interactable instanceof PoisonSnake) {
//          if (((Quest)sideQuests[1]).getActive() && !((Quest)sideQuests[1]).getComplete()) {
//            ((Quest)sideQuests[1]).setComplete(((Quest)sideQuests[1]).updateObjective(1));
//            if (((Quest)sideQuests[1]).getComplete()) {
//              System.out.println("complete");
//              ((Player)player).setExp(((Quest)sideQuests[1]).getXPReward());
//              System.out.println("You have gained : " + ((Quest)sideQuests[1]).getXPReward() + " XP");
//              System.out.println("You have gained : something");
//            }
//          }
          }
          else if (interactable instanceof PoisonSpider) {
//          if (((Quest)sideQuests[1]).getActive() && !((Quest)sideQuests[1]).getComplete()) {
//            ((Quest)sideQuests[1]).setComplete(((Quest)sideQuests[0]).updateObjective(2)
//            if (((Quest)sideQuests[1]).getComplete()) {
//              System.out.println("complete");
//              ((Player)player).setExp(((Quest)sideQuests[1]).getXPReward());
//              System.out.println("You have gained : " + ((Quest)sideQuests[1]).getXPReward() + " XP");
//              System.out.println("You have gained : something");
//            }
//          }
          }
          //Frost Quest (huntquestC/(2 in index)
          else if (interactable instanceof FrostSnake) {
//          if (((Quest)sideQuests[2]).getActive() && !((Quest)sideQuests[2]).getComplete()) {
//            ((Quest)sideQuests[2]).setComplete(((Quest)sideQuests[2]).updateObjective(1));
//            if (((Quest)sideQuests[2]).getComplete()) {
//              System.out.println("complete");
//              ((Player)player).setExp(((Quest)sideQuests[2]).getXPReward());
//              System.out.println("You have gained : " + ((Quest)sideQuests[2]).getXPReward() + " XP");
//              System.out.println("You have gained : something");
//            }
//          }
          }
          else if (interactable instanceof FrostSpider) {
//          if (((Quest)sideQuests[2]).getActive() && !((Quest)sideQuests[2]).getComplete()) {
//            ((Quest)sideQuests[2]).setComplete(((Quest)sideQuests[2]).updateObjective(2));
//            if (((Quest)sideQuests[2]).getComplete()) {
//              System.out.println("complete");
//              ((Player)player).setExp(((Quest)sideQuests[2]).getXPReward());
//              System.out.println("You have gained : " + ((Quest)sideQuests[2]).getXPReward() + " XP");
//              System.out.println("You have gained : something");
//            }
//          }
          }
          //Fire Quest (huntquestD/(3 in index)
          else if (interactable instanceof FireSnake) {
//          if (((Quest)sideQuests[3]).getActive() && !((Quest)sideQuests[3]).getComplete()) {
//            ((Quest)sideQuests[3]).setComplete(((Quest)sideQuests[3]).updateObjective(1));
//            if (((Quest)sideQuests[3]).getComplete()) {
//              System.out.println("complete");
//              ((Player)player).setExp(((Quest)sideQuests[3]).getXPReward());
//              System.out.println("You have gained : " + ((Quest)sideQuests[3]).getXPReward() + " XP");
//              System.out.println("You have gained : something");
//            }
//          }
          }
          else if (interactable instanceof FireSpider) {
//          if (((Quest)sideQuests[3]).getActive() && !((Quest)sideQuests[3]).getComplete()) {
//            ((Quest)sideQuests[3]).setComplete(((Quest)sideQuests[3]).updateObjective(2));
//            if (((Quest)sideQuests[3]).getComplete()) {
//              System.out.println("complete");
//              ((Player)player).setExp(((Quest)sideQuests[3]).getXPReward());
//              System.out.println("You have gained : " + ((Quest)sideQuests[3]).getXPReward() + " XP");
//              System.out.println("You have gained : something");
//            }
//          }
          }
          
          //Boss Quest (HuntQuestE/(4 in index)
          else if (interactable instanceof PoisonBoss) {
//          if (((Quest)sideQuests[4]).getActive() && !((Quest)sideQuests[4]).getComplete()) {
//            ((Quest)sideQuests[4]).setComplete(((Quest)sideQuests[4]).updateObjective(1));
//            if (((Quest)sideQuests[4]).getComplete()) {
//              System.out.println("complete");
//              ((Player)player).setExp(((Quest)sideQuests[4]).getXPReward());
//              System.out.println("You have gained : " + ((Quest)sideQuests[0]).getXPReward() + " XP");
//              System.out.println("You have gained : something");
//            }
//          }
          }
          else if (interactable instanceof FrostBoss) {
//          if (((Quest)sideQuests[4]).getActive() && !((Quest)sideQuests[4]).getComplete()) {
//            ((Quest)sideQuests[4]).setComplete(((Quest)sideQuests[4]).updateObjective(2));
//            if (((Quest)sideQuests[4]).getComplete()) {
//              System.out.println("complete");
//              ((Player)player).setExp(((Quest)sideQuests[4]).getXPReward());
//              System.out.println("You have gained : " + ((Quest)sideQuests[4]).getXPReward() + " XP");
//              System.out.println("You have gained : something");
//            }
//          }
          }
          else if (interactable instanceof FireBoss) {
//          if (((Quest)sideQuests[4]).getActive() && !((Quest)sideQuests[4]).getComplete()) {
//            ((Quest)sideQuests[4]).setComplete(((Quest)sideQuests[4]).updateObjective(2));
//            if (((Quest)sideQuests[4]).getComplete()) {
//              System.out.println("complete");
//              ((Player)player).setExp(((Quest)sideQuests[4]).getXPReward());
//              System.out.println("You have gained : " + ((Quest)sideQuests[4]).getXPReward() + " XP");
//              System.out.println("You have gained : something");
//            }
//          }
          }
        }
        
      }
      else if (interactable instanceof NPC) {
        ((NPC)interactable).speak();
        if (((NPC)interactable).getQuestGiver()) { // possibly start the quest by pressing a key not the mouse
          
          Object newQuest = ((NPC)interactable).getQuest();
          
          if (newQuest instanceof HuntQuest && !((Quest)newQuest).getComplete()) {
            ((Quest)sideQuests[0]).setActive(true);
            ((Quest)newQuest).initialize(world);
          }
          
          if (newQuest instanceof HuntQuestB && !((Quest)newQuest).getComplete()) {
            ((Quest)sideQuests[1]).setActive(true);
            ((Quest)newQuest).initialize(world);
          }
          
          if (newQuest instanceof HuntQuestC && !((Quest)newQuest).getComplete()) {
            ((Quest)sideQuests[2]).setActive(true);
            ((Quest)newQuest).initialize(world);
          }
          
          if (newQuest instanceof HuntQuestD && !((Quest)newQuest).getComplete()) {
            ((Quest)sideQuests[3]).setActive(true);
            ((Quest)newQuest).initialize(world);
          }
          
          if (newQuest instanceof HuntQuestE && !((Quest)newQuest).getComplete()) {
            ((Quest)sideQuests[4]).setActive(true);
            ((Quest)newQuest).initialize(world);
          }
          
                    if (newQuest instanceof MainQuestA && !((Quest)newQuest).getComplete()) {
            ((Quest)mainQuests).setActive(true);
            ((Quest)newQuest).initialize(world);
          }
        }
      }
    }
    public void mousePressed(MouseEvent e) {
      
      
      
    }
    
    public void mouseReleased(MouseEvent e) {
    }
    
    public void mouseEntered(MouseEvent e) {
    }
    
    public void mouseExited(MouseEvent e) {
    }
  } //end of mouselistener
  
}
