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
import java.util.ArrayList;
import java.util.Random;

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
  int spaceX; //elon musk?
  int spaceY;
  Clock clock = new Clock();
  Clock enemyDelay = new Clock();
  Player player;
  JPanel mainPanel = new JPanel();
  
  
  
  
  //class variable (non-static)
  static double x, y;
  static GameAreaPanel gamePanel;
  static DeathPanel deathPanel;
  
  
  
  
  //Constructor - this runs first
  GameFrame(Object[][] world, Player player) { 
    super("My Game");  
    this.world = world;
    this.player = player;
    System.out.println(player.getX());
    System.out.println(player.getY());
    clock.update();
    System.out.println(clock.getElapsedTime());
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
    boolean firstTime = true;
    boolean stamina = true;
    
    while(player.getHealth() > 0){
      if (((int)clock.getElapsedTime()) == 15) {
        respawnEnemies(((int)clock.getElapsedTime()));
        clock.update();
      }
      
      for (int i = 0; i < world.length; i++) {
        for (int j = 0; j < world.length; j++) {
          if (world[i][j] instanceof Enemy) {
            if (((Enemy)world[i][j]).getHealth() <= 0)
              ((Enemy)world[i][j]).death(world, i, j);
          }
        }
      }
      
      for (int i = 0; i < world.length; i++) {
        for (int j = 0; j < world.length; j++) {
          // this is for when enemies are in vision range
          
          if (world[i][j] instanceof Player) {
            int playX = ((Player)world[i][j]).getX();
            int playY = ((Player)world[i][j]).getY();
            if (firstTime == true) {
              enemyDelay.update();
              firstTime = false;
            }
            System.out.println(enemyDelay.getElapsedTime());
            if (((int)enemyDelay.getElapsedTime()) % 2 == 0) {
              for (int m = playX - 4; m < playX+5; m++) {
                for (int n = playY - 4; n < playY+5; n++) {
                  if (world[m][n] instanceof Enemy) {
                    System.out.println("move");
                    ((Enemy)world[m][n]).move(world, m, n);
                    enemyDelay.update();
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
    playerDeath();
  }
  
  void respawnEnemies(int time) {
    int noobCount = 0, fireCount = 0, frostCount = 0, poisonCount = 0, totalCount = 0;
    Random rand = new Random();
    int enemyType;
    int randX;
    int randY;
    System.out.println(time);
    
    
    System.out.println("works");
    
    while (noobCount < 2 || fireCount < 1 || frostCount < 2 || poisonCount < 1) {
      for (int i = 0; i < world.length; i++) {
        for (int j = 0; j < world.length; j++) {
          randX = rand.nextInt(97) + 4;
          randY = rand.nextInt(97) + 4;
          if (noobCount < 2) {
            if (world[randX][randY] instanceof Grass) {
              enemyType = rand.nextInt(2);
              if (enemyType == 1) {
                noobCount++;
                world[randX][randY] = new Bandit(1,1,1,1,1,1,"Bandit", randX, randY, world[randX][randY]);
              } else {
                noobCount++;
                world[randX][randY] = new Archer(1,1,1,1,1,1,"Archer", randX, randY, world[randX][randY]);
              }
            } 
          } if (frostCount < 2) { 
            if (world[randX][randY] instanceof FrostGrass) {
              enemyType = rand.nextInt(2);
              if (enemyType == 1) {
                frostCount++;
                world[randX][randY] = new FrostSnake(1,1,1,1,1,1,"FrostSnake", i, j, world[i][j]);
              } else {
                frostCount++;
                world[randX][randY] = new FrostSpider(1,1,1,1,1,1,"FrostSpider", i, j, world[i][j]);
              }
            }
          } if (fireCount < 1) {
            if (world[randX][randY] instanceof FireGrass) {
              enemyType = rand.nextInt(2);
              if (enemyType == 1) {
                fireCount++;
                world[randX][randY] = new FireSnake(1,1,1,1,1,1,"FireSnake", randX, randY, world[randX][randY]);
              } else {
                fireCount++;
                world[randX][randY] = new FireSpider(1,1,1,1,1,1,"FireSpider", randX, randY, world[i][j]);
              }
            }
          } if (poisonCount < 1) { 
            if (world[randX][randY] instanceof PoisonGrass) {
              enemyType = rand.nextInt(2);
              if (enemyType == 1) {
                poisonCount++;
                world[randX][randY] = new PoisonSnake(1,1,1,1,1,1,"PoisonSnake", randX, randY, world[randX][randY]);
              } else {
                poisonCount++;
                world[randX][randY] = new PoisonSpider(1,1,1,1,1,1,"PoisonSpider", randX, randY, world[randX][randY]);
              }
            }
          }
        }
      }
    }
  }
  
  void playerDeath() {
    deathPanel = new DeathPanel();
    frame.getContentPane().add(BorderLayout.CENTER, deathPanel);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
    frame.setVisible(true);
    
  }
  /** --------- INNER CLASSES ------------- **/
  
  private class DeathPanel extends JPanel {
    public void paintComponent(Graphics f) {   
      maxX = Toolkit.getDefaultToolkit().getScreenSize().width;
      maxY = Toolkit.getDefaultToolkit().getScreenSize().height;
      
      f.setColor(Color.BLACK);
      f.fillRect(0,0,maxX,maxY);
      
      f.setColor(Color.RED);
      f.drawString(player.getName() + " Died",maxX/2,100); 

      
    }
  }
  
// Inner class for the the game area - This is where all the drawing of the screen occurs
  private class GameAreaPanel extends JPanel {
    public void paintComponent(Graphics g) {   
      
      
      //super.repaint();
      maxX = Toolkit.getDefaultToolkit().getScreenSize().width;
      maxY = Toolkit.getDefaultToolkit().getScreenSize().height;
      int countX = 0;
      int countY = 0;
      
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
      
      
      
      
      
      
      
      for(int i =  player.getX() - 4; i <= player.getX() + 4;i=i+1) { 
        for(int j =player.getY() - 4; j <= player.getY() + 4;j=j+1) { 
          
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
          }
          //Enemies
          if (world[i][j] instanceof Enemy) {
            if (world[i][j] instanceof Bandit) {
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
            g.setColor(Color.BLACK);
            g.fillRect((j - (j - (countY%9))) * GridToScreenRatio, ((i - (i - countX)) * GridToScreenRatio) - 30, GridToScreenRatio - 25, 10);
            g.setColor(Color.RED);
            g.fillRect(((j - (j - (countY%9))) * GridToScreenRatio) + 2, ((i - (i - countX)) * GridToScreenRatio) - 29, (((Enemy)world[i][j]).getHealth() * (GridToScreenRatio - 28)) / 100, 8);
            
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
      
      g.setColor(Color.BLACK);
      g.fillRect((maxX/4) - 205 , maxY - 150, 410, 35); 
      g.setColor(Color.RED);
      g.fillRect((maxX/4) - 200 , maxY - 145, player.getHealth() * 4, 25);
    }
  }
  
  class Clock {
    long elapsedTime;
    long lastTimeCheck;
    long currentTime;
    
    public Clock() { 
      lastTimeCheck=System.nanoTime();
      elapsedTime=0;
    }
    
    public void update() {
      //if the computer is fast you need more precision
      currentTime = System.nanoTime();
      elapsedTime=currentTime - lastTimeCheck;
      lastTimeCheck=currentTime;
    }
    
    //return elapsed time in milliseconds
    public double getElapsedTime() {
      currentTime = System.nanoTime();
      return (currentTime - lastTimeCheck)/1.0E9;
    }
  }
  
// -----------  Inner class for the keyboard listener - this detects key presses and runs the corresponding code
  private class MyKeyListener implements KeyListener {
    int playerX = 0;
    int playerY = 0;
    public void keyTyped(KeyEvent e) {  
    }
    
    public void keyPressed(KeyEvent e) {
      clock.update();
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
      spaceX = Math.abs(xToTile - playerX);
      spaceY = Math.abs(yToTile - playerY);
      
      interact(world[playerX][playerY], world[xToTile][yToTile], world);
      
      
      
      //System.out.println(xToTile + " " + yToTile);
    }
    
    public void interact(Object player, Object interactable, Object[][] world) {
      if (interactable instanceof Enemy) {
        
        if (spaceX == 0 || spaceX == 1) {
          if (spaceY == 0 || spaceY == 1) {
            ((Player)player).attack(((Enemy)interactable));
            if (((Enemy)interactable).getQuest() instanceof HuntQuest /* && ((Quest)((Enemy)interactable)).isComplete() */) {
              ((Quest)((Enemy)interactable).getQuest()).updateObjective(world);
            }
            else if (((Enemy)interactable).getQuest() instanceof HuntQuestB /* && ((Quest)((Enemy)interactable)).isComplete() */) {
              ((Quest)((Enemy)interactable).getQuest()).updateObjective(world);
            }
            else if (((Enemy)interactable).getQuest() instanceof HuntQuestC /* && ((Quest)((Enemy)interactable)).isComplete() */) {
              ((Quest)((Enemy)interactable).getQuest()).updateObjective(world);
            }
          }
        }
      }
      else if (interactable instanceof NPC) {
        ((NPC)interactable).speak();
        if (((NPC)interactable).getQuestGiver()) {
          Object newQuest = ((NPC)interactable).getQuest();
          ((Quest)newQuest).initialize(world);
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

