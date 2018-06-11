
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
import java.io.PrintWriter;
import java.io.File;
import java.util.ArrayList;
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
  World[][] world;
  Quest[] sideQuests = new Quest[6];
  static Quest mainQuests;
  ArrayList<Quest> activeQuests = new ArrayList<Quest>();
  
  //class variable (non-static)
  static double x, y;
  static GameAreaPanel gamePanel;
  
  
  
  
  //Constructor - this runs first
  GameFrame(World[][] world, Quest[] sideQuests, Quest mainQuests) { 
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
  
  
  public static void saveGame(World[][] world, Quest[] sideQuests, Quest mainQuestA) throws Exception{
    File saveGame = new File("saveGame.txt");
    File saveMap = new File("mapSave.txt");
    Player player;
    PrintWriter output = new PrintWriter(saveMap);
    for (int i = 0; i < world.length; i++) {
      for (int j = 0; j < world.length; j++) {
        if (world[i][j] instanceof Bridge) {
          output.print("B");
        } else if (world[i][j] instanceof CaveWall) {
          output.print("c");
        } else if (world[i][j] instanceof Tree) {
          output.print("T");
        } else if (world[i][j] instanceof Wall) {
          output.print("h");
        } else if (world[i][j] instanceof CastleWall) {
          output.print("C");
        } else if (world[i][j] instanceof HouseFloor) {
          output.print("F");
        } else if (world[i][j] instanceof Dirt) {
          output.print("-");
        } else if (world[i][j] instanceof Grass) {
          output.print("E");
        } else if (world[i][j] instanceof Chest) {
          output.print("L");
        } else if (world[i][j] instanceof FrostGrass) {
          output.print("I");
        } else if (world[i][j] instanceof PoisonGrass) {
          output.print("D");
        } else if (world[i][j] instanceof FireGrass) {
          output.print("M");
        } else if (world[i][j] instanceof Water) {
          output.print("S");
        } else if (world[i][j] instanceof Enemy) {
          output.print("A");
        } else if (world[i][j] instanceof Player) {
          output.print("P");
          player = ((Player)world[i][j]);
          PrintWriter outputPlayer = new PrintWriter(saveGame) ;
          //save player
          outputPlayer.println(player.getName());
          outputPlayer.println(player.getExp());
          outputPlayer.println(player.getInt());
          outputPlayer.println(player.getStr());
          //save quest
          for (int k = 0; k < sideQuests.length; k++) {
            if (sideQuests[i].getActive()) {
              //outputPlayer.println(sideQuests[i].getCurrentTask());
            }
          }
          outputPlayer.println(((Quest)mainQuests).getCurrentTask());
          //outputPlayer.println(mainQuestA.getCurrentTask());
          outputPlayer.close();
        }
        
      }
      output.println("");
    }
    output.close();
    
    
    
    
  }
  /** --------- INNER CLASSES ------------- **/
  
  // Inner class for the the game area - This is where all the drawing of the screen occurs
  private class GameAreaPanel extends JPanel {
    public void paintComponent(Graphics g) {   
      
      
      super.paintComponent(g);
      maxX = Toolkit.getDefaultToolkit().getScreenSize().width;
      maxY = Toolkit.getDefaultToolkit().getScreenSize().height;
      
      Image[] floorTextures={Toolkit.getDefaultToolkit().getImage("Grass Texture.png"),
        Toolkit.getDefaultToolkit().getImage("Poison Grass Texture.png"),
        Toolkit.getDefaultToolkit().getImage("Frost Grass Texture.png"),
        Toolkit.getDefaultToolkit().getImage("Fire Grass Texture.png"),
        Toolkit.getDefaultToolkit().getImage("dirt.png"),
        Toolkit.getDefaultToolkit().getImage("floor.jpg")
      };
      Image[] knights={Toolkit.getDefaultToolkit().getImage("Knight2_IDLE_000.png")};
      Image[] trolls={Toolkit.getDefaultToolkit().getImage("Trolls1_IDLE_000.png")};
      Image[] player={Toolkit.getDefaultToolkit().getImage("No Armour Character Sprite with Dagger_idle_000.png")};
      Image[] spider={Toolkit.getDefaultToolkit().getImage("Spider_sprite_walk_4.png")}; 
      Image[] sorcerer={Toolkit.getDefaultToolkit().getImage("sorcerer attack_Animation 1_1.png")}; 
      Image waterImage=Toolkit.getDefaultToolkit().getImage("Water.png");
      Image chestImage=Toolkit.getDefaultToolkit().getImage("chest texture.png");
      Image castleWallImage=Toolkit.getDefaultToolkit().getImage("castle wall.png");
      Image wallImage=Toolkit.getDefaultToolkit().getImage("wall.png");
      
      int playerX = 0;
      int playerY = 0;
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
      Color yellow = new Color(255,255,0);
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
            g.drawImage(floorTextures[0],(j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
            
          } else if (world[i][j] instanceof FrostGrass) {
            g.drawImage(floorTextures[2],(j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);            
            
          } else if (world[i][j] instanceof FireGrass) {
            g.drawImage(floorTextures[3],(j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
            
          } else if (world[i][j] instanceof Dirt) {
            g.drawImage(floorTextures[4],(j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
            
          } else if (world[i][j] instanceof PoisonGrass) {
            g.drawImage(floorTextures[1],(j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
            
          } else if (world[i][j] instanceof Water) {
            g.drawImage(waterImage,(j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
            
            
          } else if (world[i][j] instanceof Chest) {
            g.drawImage(chestImage,(j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
            
            
          } else if (world[i][j] instanceof CastleWall) {
            g.drawImage(castleWallImage,(j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
            
          } else if (world[i][j] instanceof Wall) {
            g.drawImage(wallImage,(j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
            
            
          } else if (world[i][j] instanceof HouseFloor) {
            g.drawImage(floorTextures[5],(j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
            
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
            g.drawImage(knights[0],(j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
            g.setColor(Color.WHITE);
            g.drawString(((Character)world[i][j]).getName(), (j - (j - (countY %9))) * GridToScreenRatio + 5, (i - (i - countX)) * GridToScreenRatio + 8);
          }
          else if (world[i][j] instanceof Archer) {
            g.drawImage(trolls[0],(j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
            g.setColor(Color.WHITE);
            g.drawString(((Character)world[i][j]).getName(), (j - (j - (countY %9))) * GridToScreenRatio + 5, (i - (i - countX)) * GridToScreenRatio + 8);
          }
          else if (world[i][j] instanceof PoisonSpider) {
            g.drawImage(spider[0],(j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
            g.setColor(Color.WHITE);
            g.drawString(((Character)world[i][j]).getName(), (j - (j - (countY %9))) * GridToScreenRatio + 5, (i - (i - countX)) * GridToScreenRatio + 8);
          }
          else if (world[i][j] instanceof PoisonSnake) {
            g.drawImage(sorcerer[0],(j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
            g.setColor(Color.WHITE);
            g.drawString(((Character)world[i][j]).getName(), (j - (j - (countY %9))) * GridToScreenRatio + 5, (i - (i - countX)) * GridToScreenRatio + 8);
          }
          //Player
          else if (world[i][j] instanceof Player) {
            g.drawImage(player[0],(j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
            g.setColor(Color.WHITE);
            g.drawString(((Character)world[i][j]).getName(), (j - (j - (countY %9))) * GridToScreenRatio + 5, (i - (i - countX)) * GridToScreenRatio + 8);
            g.drawString("Lv:" + ((Player)world[i][j]).getLvl(), (j - (j - (countY %9))) * GridToScreenRatio + 30, (i - (i - countX)) * GridToScreenRatio + 30);
          }
          
          countY++;
        }
        countX++;
      }
      //updateActiveQuests();
      
//      for (int i = 0; i < activeQuests.size(); i++) {
//        if (activeQuests.get(i) instanceof MainQuestA) {
//          g.setColor(Color.RED);
//          g.drawString(mainQuests.getName(), maxX / 2 - 100, maxY / 8);
//        }
//      }
    }
  }
//  private void updateActiveQuests() {
//    if (mainQuests.getActive()) {
//      activeQuests.add(mainQuests);
//    }
//    for (int i = 0; i < sideQuests.length; i++) {
//      if (sideQuests[i].getActive()) {
//        activeQuests.add(sideQuests[i]);
//      }
//    }
//  }
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
      System.out.println(mouseX);
      System.out.println(mouseY);
      yToTile = ((mouseX / GridToScreenRatio) - 4) + playerY;
      xToTile = ((mouseY / GridToScreenRatio) - 4) + playerX;
      
      interact(world[playerX][playerY], world[xToTile][yToTile], world);
      
      
      //System.out.println(xToTile + " " + yToTile);
    }
    
    public void interact(Object player, Object interactable, World[][] world) {
      
      if (interactable instanceof Enemy) {
        ((Player)player).attack(((Enemy)interactable));
        
        //Quests interaction
        if (((Enemy)interactable).getHealth() <= 0) {
          if (interactable instanceof Bandit) {
            if (((Quest)sideQuests[0]).getActive() && !((Quest)sideQuests[0]).getComplete()) {
              ((Quest)sideQuests[0]).setComplete(((Quest)sideQuests[0]).updateObjective(1));
              if (((Quest)sideQuests[0]).getComplete()) {
                System.out.println("complete");
                ((Player)player).setExp(((Player)player).getExp() +((Quest)sideQuests[0]).getXPReward());
                System.out.println("You have gained : " + ((Quest)sideQuests[0]).getXPReward() + " XP");
                System.out.println("You have gained : something");
              }
            }
            // MAIN QUEST
            if (((Quest)mainQuests).getCurrentTask() < 3 && !((Quest)mainQuests).getComplete()) {
              ((Quest)mainQuests).updateObjective(1);
              
              if (((Quest)mainQuests).getCurrentTask() == 3) {
                System.out.println("complete");
                ((Player)player).setExp(((Player)player).getExp() + ((Quest)mainQuests).getXPReward());
                System.out.println("You have gained : " + ((Quest)mainQuests).getXPReward() + " XP");
                System.out.println("You have gained : something");
              }
            }
          }
          else if (interactable instanceof Archer) {
            //MAIN QUEST
            if (((Quest)mainQuests).getCurrentTask() < 3 && !((Quest)mainQuests).getComplete()) {
              ((Quest)mainQuests).updateObjective(2);
              System.out.println(mainQuests.getCurrentTask());
              if (((Quest)mainQuests).getCurrentTask() == 3) {
                System.out.println("complete");
                ((Player)player).setExp(((Player)player).getExp() + ((Quest)mainQuests).getXPReward());
                System.out.println("You have gained : " + ((Quest)mainQuests).getXPReward() + " XP");
                System.out.println("You have gained : something");
              }
            }
          }
          //Poison Quest (huntquestB/(1 in index)
          else if (interactable instanceof PoisonSnake) {
            if (((Quest)sideQuests[1]).getActive() && !((Quest)sideQuests[1]).getComplete()) {
              
              ((Quest)sideQuests[1]).setComplete(((Quest)sideQuests[1]).updateObjective(1));
              if (((Quest)sideQuests[1]).getCurrentTask() == 7) {
                System.out.println("complete");
                ((Player)player).setExp(((Player)player).getExp() +((Quest)sideQuests[1]).getXPReward());
                System.out.println("You have gained : " + ((Quest)sideQuests[1]).getXPReward() + " XP");
                System.out.println("You have gained : something");
              }
            }
            // MAIN QUEST
            if (((Quest)mainQuests).getCurrentTask() > 4 && ((Quest)mainQuests).getCurrentTask() < 7 && !((Quest)mainQuests).getComplete()) {
              ((Quest)mainQuests).updateObjective(1);
              System.out.println(mainQuests.getCurrentTask());
              if (((Quest)mainQuests).getCurrentTask() == 7) {
                System.out.println("complete");
                ((Player)player).setExp(((Player)player).getExp() + ((Quest)mainQuests).getXPReward());
                System.out.println("You have gained : " + ((Quest)mainQuests).getXPReward() + " XP");
                System.out.println("You have gained : something");
              }
            }
          }
          else if (interactable instanceof PoisonSpider) {
            if (((Quest)sideQuests[1]).getActive() && !((Quest)sideQuests[1]).getComplete()) {
              ((Quest)sideQuests[1]).setComplete(((Quest)sideQuests[0]).updateObjective(2));
              if (((Quest)sideQuests[1]).getComplete()) {
                System.out.println("complete");
                ((Player)player).setExp(((Player)player).getExp() +((Quest)sideQuests[1]).getXPReward());
                System.out.println("You have gained : " + ((Quest)sideQuests[1]).getXPReward() + " XP");
                System.out.println("You have gained : something");
              }
            }
            // MAIN QUEST
            if (((Quest)mainQuests).getCurrentTask() > 4 && ((Quest)mainQuests).getCurrentTask() < 7 && !((Quest)mainQuests).getComplete()) {
              ((Quest)mainQuests).updateObjective(2);
              System.out.println(mainQuests.getCurrentTask());
              if (((Quest)mainQuests).getCurrentTask() == 7) {
                System.out.println("complete");
                ((Player)player).setExp(((Player)player).getExp() + ((Quest)mainQuests).getXPReward());
                System.out.println("You have gained : " + ((Quest)mainQuests).getXPReward() + " XP");
                System.out.println("You have gained : something");
              }
            }
          }
          //Frost Quest (huntquestC/(2 in index)
          else if (interactable instanceof FrostSnake) {
            if (((Quest)sideQuests[2]).getActive() && !((Quest)sideQuests[2]).getComplete()) {
              ((Quest)sideQuests[2]).setComplete(((Quest)sideQuests[2]).updateObjective(1));
              if (((Quest)sideQuests[2]).getComplete()) {
                System.out.println("complete");
                ((Player)player).setExp(((Player)player).getExp() +((Quest)sideQuests[2]).getXPReward());
                System.out.println("You have gained : " + ((Quest)sideQuests[2]).getXPReward() + " XP");
                System.out.println("You have gained : something");
              }
            }
            // MAIN QUEST
            if (((Quest)mainQuests).getCurrentTask() > 11 && ((Quest)mainQuests).getCurrentTask() < 14 && !((Quest)mainQuests).getComplete()) {
              ((Quest)mainQuests).updateObjective(1);
              if (((Quest)mainQuests).getCurrentTask() == 14) {
                System.out.println("complete");
                ((Player)player).setExp(((Player)player).getExp() + ((Quest)mainQuests).getXPReward());
                System.out.println("You have gained : " + ((Quest)mainQuests).getXPReward() + " XP");
                System.out.println("You have gained : something");
              }
            }
          }
          else if (interactable instanceof FrostSpider) {
            if (((Quest)sideQuests[2]).getActive() && !((Quest)sideQuests[2]).getComplete()) {
              ((Quest)sideQuests[2]).setComplete(((Quest)sideQuests[2]).updateObjective(2));
              if (((Quest)sideQuests[2]).getComplete()) {
                System.out.println("complete");
                ((Player)player).setExp(((Player)player).getExp() +((Quest)sideQuests[2]).getXPReward());
                System.out.println("You have gained : " + ((Quest)sideQuests[2]).getXPReward() + " XP");
                System.out.println("You have gained : something");
              }
            }
            // MAIN QUEST
            if (((Quest)mainQuests).getCurrentTask() > 11 && ((Quest)mainQuests).getCurrentTask() < 14 && !((Quest)mainQuests).getComplete()) {
              ((Quest)mainQuests).updateObjective(1);
              if (((Quest)mainQuests).getCurrentTask() == 14) {
                System.out.println("complete");
                ((Player)player).setExp(((Player)player).getExp() + ((Quest)mainQuests).getXPReward());
                System.out.println("You have gained : " + ((Quest)mainQuests).getXPReward() + " XP");
                System.out.println("You have gained : something");
              }
            }
          }
          //Fire Quest (huntquestD/(3 in index)
          else if (interactable instanceof FireSnake) {
            if (((Quest)sideQuests[3]).getActive() && !((Quest)sideQuests[3]).getComplete()) {
              ((Quest)sideQuests[3]).setComplete(((Quest)sideQuests[3]).updateObjective(1));
              if (((Quest)sideQuests[3]).getComplete()) {
                System.out.println("complete");
                ((Player)player).setExp(((Player)player).getExp() +((Quest)sideQuests[3]).getXPReward());
                System.out.println("You have gained : " + ((Quest)sideQuests[3]).getXPReward() + " XP");
                System.out.println("You have gained : something");
              }
            }
            // MAIN QUEST
            if (((Quest)mainQuests).getCurrentTask() > 18 && ((Quest)mainQuests).getCurrentTask() < 21 && !((Quest)mainQuests).getComplete()) {
              ((Quest)mainQuests).updateObjective(1);
              if (((Quest)mainQuests).getCurrentTask() == 7) {
                System.out.println("complete");
                ((Player)player).setExp(((Player)player).getExp() + ((Quest)mainQuests).getXPReward());
                System.out.println("You have gained : " + ((Quest)mainQuests).getXPReward() + " XP");
                System.out.println("You have gained : something");
              }
            }
          }
          else if (interactable instanceof FireSpider) {
            if (((Quest)sideQuests[3]).getActive() && !((Quest)sideQuests[3]).getComplete()) {
              ((Quest)sideQuests[3]).setComplete(((Quest)sideQuests[3]).updateObjective(2));
              if (((Quest)sideQuests[3]).getComplete()) {
                System.out.println("complete");
                ((Player)player).setExp(((Quest)sideQuests[3]).getXPReward());
                System.out.println("You have gained : " + ((Quest)sideQuests[3]).getXPReward() + " XP");
                System.out.println("You have gained : something");
              }
            }
            // MAIN QUEST
            if (((Quest)mainQuests).getCurrentTask() > 18 && ((Quest)mainQuests).getCurrentTask() < 21 && !((Quest)mainQuests).getComplete()) {
              ((Quest)mainQuests).updateObjective(2);
              if (((Quest)mainQuests).getCurrentTask() == 7) {
                System.out.println("complete");
                ((Player)player).setExp(((Player)player).getExp() + ((Quest)mainQuests).getXPReward());
                System.out.println("You have gained : " + ((Quest)mainQuests).getXPReward() + " XP");
                System.out.println("You have gained : something");
              }
            }
          }
          
          //Boss Quest (HuntQuestE/(4 in index)
          else if (interactable instanceof PoisonBoss) {
            if (((Quest)sideQuests[4]).getActive() && !((Quest)sideQuests[4]).getComplete()) {
              ((Quest)sideQuests[4]).setComplete(((Quest)sideQuests[4]).updateObjective(1));
              if (((Quest)sideQuests[4]).getComplete()) {
                System.out.println("complete");
                ((Player)player).setExp(((Player)player).getExp() +((Quest)sideQuests[4]).getXPReward());
                System.out.println("You have gained : " + ((Quest)sideQuests[0]).getXPReward() + " XP");
                System.out.println("You have gained : something");
              }
            }
            // MAIN QUEST
            if (((Quest)mainQuests).getCurrentTask() == 9 && !((Quest)mainQuests).getComplete()) {
              ((Quest)mainQuests).updateObjective(1);
              if (((Quest)mainQuests).getCurrentTask() == 10) {
                System.out.println(((Quest)mainQuests).getTask(9));
                ((Player)player).setExp(((Player)player).getExp() + ((Quest)mainQuests).getXPReward());
                System.out.println("You have gained : " + ((Quest)mainQuests).getXPReward() + " XP");
                System.out.println("You have gained : something");
              }
            }
          }
          else if (interactable instanceof FrostBoss) {
            if (((Quest)sideQuests[4]).getActive() && !((Quest)sideQuests[4]).getComplete()) {
              ((Quest)sideQuests[4]).setComplete(((Quest)sideQuests[4]).updateObjective(2));
              if (((Quest)sideQuests[4]).getComplete()) {
                System.out.println("complete");
                ((Player)player).setExp(((Player)player).getExp() +((Quest)sideQuests[4]).getXPReward());
                System.out.println("You have gained : " + ((Quest)sideQuests[4]).getXPReward() + " XP");
                System.out.println("You have gained : something");
              }
            }
            // MAIN QUEST
            if (((Quest)mainQuests).getCurrentTask() == 16 && !((Quest)mainQuests).getComplete()) {
              ((Quest)mainQuests).updateObjective(1);
              if (((Quest)mainQuests).getCurrentTask() == 7) {
                System.out.println("complete");
                ((Player)player).setExp(((Player)player).getExp() + ((Quest)mainQuests).getXPReward());
                System.out.println("You have gained : " + ((Quest)mainQuests).getXPReward() + " XP");
                System.out.println("You have gained : something");
              }
            }
          }
          else if (interactable instanceof FireBoss) {
            if (((Quest)sideQuests[4]).getActive() && !((Quest)sideQuests[4]).getComplete()) {
              ((Quest)sideQuests[4]).setComplete(((Quest)sideQuests[4]).updateObjective(3));
              if (((Quest)sideQuests[4]).getComplete()) {
                System.out.println("complete");
                ((Player)player).setExp(((Player)player).getExp() + ((Quest)sideQuests[4]).getXPReward());
                System.out.println("You have gained : " + ((Quest)sideQuests[4]).getXPReward() + " XP");
                System.out.println("You have gained : something");
              }
            }
            // MAIN QUEST
            if (((Quest)mainQuests).getCurrentTask() == 24 && !((Quest)mainQuests).getComplete()) {
              ((Quest)mainQuests).updateObjective(1);
              if (((Quest)mainQuests).getCurrentTask() == 25) {
                System.out.println("complete");
                ((Player)player).setExp(((Player)player).getExp() + ((Quest)mainQuests).getXPReward());
                System.out.println("You have gained : " + ((Quest)mainQuests).getXPReward() + " XP");
                System.out.println("You have gained : something");
              }
            }
          }
          // else if (interactable instanceof MangatBoss) {
//          if (((Quest)mainQuests).getCurrentTask() == 24 && !((Quest)mainQuests).getComplete()) {
//              ((Quest)mainQuests).updateObjective(1);
//              if (((Quest)mainQuests).getCurrentTask() == 25) {
//                System.out.println("complete");
//                ((Player)player).setExp(((Player)player).getExp() + ((Quest)mainQuests).getXPReward());
//                System.out.println("You have gained : " + ((Quest)mainQuests).getXPReward() + " XP");
//                System.out.println("You have gained : something");
//              }
//            }
          //}
        }
        
      }
      else if (interactable instanceof NPC) {
        //((NPC)interactable).speak();
        if (((NPC)interactable).getQuestGiver()) { // possibly start the quest by pressing a key not the mouse
          
          Quest newQuest = ((NPC)interactable).getQuest();
          
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
        }
        // Main Quest handling
        if (((NPC)interactable).getQuest() instanceof MainQuestA) {
          Quest mainQuest = ((NPC)interactable).getQuest();
          if (((NPC)interactable).getName() == "Bob" && mainQuest.getCurrentTask() == 0) {
            mainQuest.initialize(world);
            Speech bobSpeech = new Speech("Hey can you help me?", 1,1);
          }
          else if (((NPC)interactable).getName() == "Bob" && mainQuest.getCurrentTask() == 3) {
            mainQuest.setCurrentTask(4);
            System.out.println("Hello, please find the farmer");
            System.out.println(mainQuest.getTask(3));
          }
          else if (((NPC)interactable).getName() == "Farmer" && mainQuest.getCurrentTask() == 4) {
            mainQuest.setCurrentTask(5);
            System.out.println("kill the snakes plox ty very much");
            System.out.println(mainQuest.getTask(4));
          }
          else if (((NPC)interactable).getName() == "Farmer" && mainQuest.getCurrentTask() == 7) {
            mainQuest.setCurrentTask(8);
            System.out.println(mainQuest.getTask(7));
          }
          else if (((NPC)interactable).getName() == "King Tagnam") {
            if (mainQuest.getCurrentTask() == 8) {
              mainQuest.setCurrentTask(9);
              System.out.println(mainQuest.getTask(8));
            }
            else if (mainQuest.getCurrentTask() == 10) {
              mainQuest.setCurrentTask(11);
              System.out.println(mainQuest.getTask(10));
            }
            else if (mainQuest.getCurrentTask() == 15) {
              mainQuest.setCurrentTask(16);
              System.out.println(mainQuest.getTask(15));
            }
            else if (mainQuest.getCurrentTask() == 17) {
              mainQuest.setCurrentTask(18);
              System.out.println(mainQuest.getTask(17));
            }
            else if (mainQuest.getCurrentTask() == 24) {
              mainQuest.setCurrentTask(25);
              System.out.println(mainQuest.getTask(24));
              World initialGround = world[60][86];
              world[60][86] = new MangatBoss(100,100,100,100,100,100,"u screwed",60,86,initialGround);
            }
          }
          else if (((NPC)interactable).getName() == "Ice Fisher") {
            if (mainQuest.getCurrentTask() == 11) {
              mainQuest.setCurrentTask(12);
              System.out.println(mainQuest.getTask(11));
            }
            else if (mainQuest.getCurrentTask() == 14) {
              mainQuest.setCurrentTask(15);
              System.out.println(mainQuest.getTask(14));
            }
          }
          else if (((NPC)interactable).getName() == "Volat" && mainQuest.getCurrentTask() == 18) {
            mainQuest.setCurrentTask(19);
            System.out.println(mainQuest.getTask(18));
          } 
          else if (((NPC)interactable).getName() == "Vivian" && mainQuest.getCurrentTask() == 21) {
            mainQuest.setCurrentTask(22);
            System.out.println(mainQuest.getTask(21));
          } 
          else if (((NPC)interactable).getName() == "Chancellor") {
            if (mainQuest.getCurrentTask() == 24) {
              mainQuest.setCurrentTask(25);
              System.out.println(mainQuest.getTask(24));
            }
            else if(mainQuest.getCurrentTask() == 27) {
              mainQuest.setCurrentTask(28);
              mainQuest.setComplete(mainQuest.updateObjective(0));
              System.out.println("A New King!");
            }
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


