
/* class GameFrame
 * Mangat, Aiden, Albert, Guy, Arjun, Johann
 * June 14th, 2018
 * class that shows the map and runs constant updates on the game
*/

//Graphics &GUI imports
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.Color;
import java.io.PrintWriter;
import java.io.File;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.*;
import java.awt.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

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
  Quest[] sideQuests = new Quest[5];
  ArrayList<String> speechQueue = new ArrayList<String>();
  static Quest mainQuests;
  ArrayList<Quest> activeQuests = new ArrayList<Quest>();
  int spaceX; //elon musk?
  int spaceY;
  int advanceDialogue = 0;
  Clock clock = new Clock();
  Clock enemyDelay = new Clock();
  JPanel mainPanel = new JPanel();
  Boolean dialogue = false;
  //class variable (non-static)
  static double x, y;
  static GameAreaPanel gamePanel;
  static Player player;
  
  //Music variables
  static File menuMusicFile ;
  static AudioInputStream menuMusicStream ;
  static DataLine.Info info;
  static Clip clip ;
  
  //Constructor - this runs first
  GameFrame(World[][] world, Quest[] sideQuests, Quest mainQuests, Player player) { 
    super("My Game");  
    this.world = world;
    this.player = player;
    
    maxX = Toolkit.getDefaultToolkit().getScreenSize().width;
    maxY = Toolkit.getDefaultToolkit().getScreenSize().height;
    GridToScreenRatio = maxY / (10);  //ratio to fit in screen as square map
    
    //Music initalisation
    try {
      menuMusicFile = new File("maskoff.wav");
      menuMusicStream = AudioSystem.getAudioInputStream(menuMusicFile);
      info = new DataLine.Info(Clip.class, menuMusicStream.getFormat());
      clip = (Clip) AudioSystem.getLine(info);
      clip.open(menuMusicStream);
    }catch (Exception e) {};
    
    clip.start();
    
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
    boolean firstTime = true;
    boolean stamina = true;
    
    while(player.getHealth() > 0){
      
      if (((int)clock.getElapsedTime()) == 15) {
        respawnEnemies(((int)clock.getElapsedTime()));
        System.out.println("respawn");
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
            
            if (((int)enemyDelay.getElapsedTime()) % 2 == 0 && ((int)enemyDelay.getElapsedTime()) > 0 ) {
              for (int m = playX - 4; m < playX+5; m++) {
                for (int n = playY - 4; n < playY+5; n++) {
                  if (world[m][n] instanceof Enemy) {
                    //System.out.println("move");
                    ((Enemy)world[m][n]).move(world, m, n);
                    enemyDelay.update();
                  }                 
                }
              }
            }
          }
        }
      }
      this.repaint();
    }
  }
  
  
  public static void saveGame(World[][] world, Quest[] sideQuests, Quest mainQuestA) throws Exception{
    File saveGame = new File("saveGame.txt");
    File saveMap = new File("mapSave.txt");
    Player playerSave;
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
        } else if (world[i][j] instanceof Player) {
          output.print("P");
        }
        
      }
      output.println("");
    }
    output.close();
    playerSave = player;
    PrintWriter outputPlayer = new PrintWriter(saveGame) ;
    //save player
    outputPlayer.println(player.getName());
    outputPlayer.println(player.getLvl());
    outputPlayer.println(player.getInt());
    outputPlayer.println(player.getStr());
    outputPlayer.println(player.getDef());
    outputPlayer.println(player.getHealth());
    outputPlayer.println(player.getX());
    outputPlayer.println(player.getY());
    outputPlayer.println(player.getAccuracy());
    //save quest
    for (int k = 0; k < sideQuests.length; k++) {
      if (sideQuests[k].getActive()) {
        //outputPlayer.println(sideQuests[i].getCurrentTask());
      }
    }
    //outputPlayer.println(mainQuestA.getCurrentTask());
    outputPlayer.close();
    
    
    
    
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
      Font questTitle = new Font("Arial", Font.BOLD, 16);
      Font questTask = new Font("Berlin Sans FB", Font.PLAIN, 15);
      Font questLogTitle = new Font("Verdana", Font.BOLD, 14);
      
      Image[] floorTextures={Toolkit.getDefaultToolkit().getImage("Grass Texture.png"),
        Toolkit.getDefaultToolkit().getImage("Poison Grass Texture.png"),
        Toolkit.getDefaultToolkit().getImage("Frost Grass Texture.png"),
        Toolkit.getDefaultToolkit().getImage("Fire Grass Texture.png"),
        Toolkit.getDefaultToolkit().getImage("dirt.png"),
        Toolkit.getDefaultToolkit().getImage("floor.jpg")
      };
      Image[] knights={Toolkit.getDefaultToolkit().getImage("Knight2_IDLE_000.png")};
      Image[] trolls={Toolkit.getDefaultToolkit().getImage("Trolls1_IDLE_000.png")};
      Image[] playerSprite={Toolkit.getDefaultToolkit().getImage("No Armour Character Sprite with Dagger_idle_000.png")};
      Image[] spider={Toolkit.getDefaultToolkit().getImage("Spider_sprite_``                                                                                                                                                                                                                                                               walk_4.png")}; 
      Image[] sorcerer={Toolkit.getDefaultToolkit().getImage("sorcerer attack_Animation 1_1.png")}; 
      Image waterImage=Toolkit.getDefaultToolkit().getImage("Water.png");
      Image chestImage=Toolkit.getDefaultToolkit().getImage("chest texture.png");
      Image castleWallImage=Toolkit.getDefaultToolkit().getImage("castle wall.png");
      Image wallImage=Toolkit.getDefaultToolkit().getImage("wall.png");
      
      setDoubleBuffered(true); 
      Color myGreen = new Color(11, 215, 72);
      Color myBeige = new Color(245,245,220);
      Color myBeigeFemale = new Color(227, 220, 192);
      Color myBrown = new Color(176, 102, 96);
      Color myBlue = new Color(0, 168, 252);
      Color mySaddleBrown = new Color(139,69,19);
      Color questLog = new Color(205, 133, 63);
      Color wood = new Color(102, 51, 0);
      Color floor = new Color(20, 80, 40); 
      Color tree = new Color(20, 51, 6);
      Color bandit = new Color(139, 60, 100);
      Color archer = new Color(11, 110, 80);
      Color yellow = new Color(255,255,0);
      Color royalYellow = new Color(250, 218, 94);
      Color farmer = new Color(176, 102, 84);
      Color speech = new Color(0,49,82);
      Color sideQuest = new Color(0, 128, 255);
      
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
          }
          //NPCs  
          if (world[i][j] instanceof NPC) {
            g.setColor(farmer); //sets colour for printing organism
            g.fillRect((j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio);
            g.setColor(Color.WHITE);
            g.drawString(((Character)world[i][j]).getName(), (j - (j - (countY %9))) * GridToScreenRatio + 5, (i - (i - countX)) * GridToScreenRatio + 8);
            
          } else if (world[i][j] instanceof Enemy) {
            
          } 
          //Enemies
          if (world[i][j] instanceof Enemy || world[i][j] instanceof Player){
            World characterImageBackground=null;
            if (world[i][j] instanceof Enemy){
              characterImageBackground=((Enemy)world[i][j]).getFutureStep();
            }else if(world[i][j] instanceof Player){
              characterImageBackground=((Player)world[i][j]).getFutureStep();
            }
            if (characterImageBackground instanceof Grass) {  
              g.drawImage(floorTextures[0],(j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
              
            } else if (characterImageBackground instanceof FrostGrass) {
              g.drawImage(floorTextures[2],(j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);            
              
            } else if (characterImageBackground instanceof FireGrass) {
              g.drawImage(floorTextures[3],(j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
              
            } else if (characterImageBackground instanceof Dirt) {
              g.drawImage(floorTextures[4],(j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
              
            } else if (characterImageBackground instanceof PoisonGrass) {
              g.drawImage(floorTextures[1],(j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
              
            }else if (characterImageBackground instanceof HouseFloor) {
              g.drawImage(floorTextures[5],(j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
              
            }
          }
          if (world[i][j] instanceof Bandit) {
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
            g.drawImage(playerSprite[0],(j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
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
      
      updateActiveQuests();
      
      g.setColor(questLog);
      if (activeQuests.size() > 3) {
        g.fillRect(0,0, (maxX/8) + 20, maxY/3);
      } else {
        g.fillRect(0,0, (maxX/8) + 20, maxY/4);
      }
      g.setColor(royalYellow);
      g.drawLine(0,28, maxX/8, 28);
      g.setFont(questLogTitle);
      g.drawString("Quest Log",10, 25);
      for (int i = 0; i < activeQuests.size(); i++) {
        if (activeQuests.get(i) instanceof MainQuestA) {
          g.setColor(Color.BLACK);
          g.setFont(questTitle);
          g.drawString(mainQuests.getName(), 15, 55 + i*30);
          g.setFont(questTask);
          //System.out.println(mainQuests.getCurrentTask());
          // need to change all tasks by reducing their numbers by 1 soon
          if (mainQuests.getCurrentTask() == 1 || mainQuests.getCurrentTask() == 5 || mainQuests.getCurrentTask() == 12 || 
              mainQuests.getCurrentTask() == 19) {
            g.drawString("- " + mainQuests.getTask(mainQuests.getCurrentTask()), 15, 55 + i*30 + 20);
            g.drawString("- " + mainQuests.getTask(mainQuests.getCurrentTask()+1), 15, 55 + i*30 + 40);
          } else {
            g.drawString("- " + mainQuests.getTask(mainQuests.getCurrentTask()), 15, 55 + i*30 + 20);
          }
        } else {
          g.setColor(sideQuest);
          g.setFont(questTitle);
          g.drawString((activeQuests.get(i)).getName(),10, 65 + 60*i);
          g.setFont(questTask);
          g.drawString("- " +(activeQuests.get(i)).getTask((activeQuests.get(i)).getCurrentTask()), 15, 65 + 60*i + 20);
        }
      }
      
      
      if (speechQueue.size() > 0) {
        dialogue = true; 
        
        if (advanceDialogue == speechQueue.size()) {
          dialogue = false;
          speechQueue.clear();
          speechQueue.trimToSize();
          advanceDialogue=0;
        } else {
          g.setColor(Color.BLACK);
          g.fillRect((maxX/4) - 220, maxY - 250, maxX/3 - 150, 142);
          g.setColor(speech);
          g.fillRect((maxX/4) - 210, maxY - 240, maxX/3 - 170, 122);
          g.setFont(questTask);
          g.setColor(Color.WHITE);
          g.drawString(speechQueue.get(advanceDialogue), (maxX/4)-100, ((maxY - 250) + 142 / 2));
        }
        
        
      }
    }
  }
  
  
  private void updateActiveQuests() {
    activeQuests.clear();
    if (mainQuests.getActive()) {
      activeQuests.add(mainQuests);
    }
    for (int i = 0; i < sideQuests.length; i++) {
      if (sideQuests[i] != null) {
        if (sideQuests[i].getActive()) {
          activeQuests.add(sideQuests[i]);
        }
      }
    }
  }
  
  
  
  void respawnEnemies(int time) {
    int noobCount = 0, fireCount = 0, frostCount = 0, poisonCount = 0, totalCount = 0;
    Random rand = new Random();
    int enemyType;
    int randX;
    int randY;
    System.out.println(time);
    
    
    
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
      if (dialogue == true) {
        advanceDialogue++;
      }
      
      
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
      

      interact(((Player)world[playerX][playerY]), world[xToTile][yToTile], world);
      
    }
    
    public void interact(Player player, World interactable, World[][] world) {
      
      if (interactable instanceof Enemy) {
        ((Player)player).attack(((Enemy)interactable));
        
        //Quests interaction
        if (((Enemy)interactable).getHealth() <= 0) {
          if (interactable instanceof Bandit) {
            if (((Quest)sideQuests[0]).getActive() && !((Quest)sideQuests[0]).getComplete()) {
              ((Quest)sideQuests[0]).setComplete(((Quest)sideQuests[0]).updateObjective(1));
              if (((Quest)sideQuests[0]).getComplete()) {
                speechQueue.add("Quest completed: " + ((Quest)sideQuests[0]).getName());
                ((Player)player).setExp(((Player)player).getExp() +((Quest)sideQuests[0]).getXPReward());
                speechQueue.add("You have gained : " + ((Quest)sideQuests[0]).getXPReward() + " XP");
                speechQueue.add("You have gained : something");
              }
            }
            // MAIN QUEST
            if (((Quest)mainQuests).getCurrentTask() < 3 && !((Quest)mainQuests).getComplete()) {
              ((Quest)mainQuests).updateObjective(1);
              
              if (((Quest)mainQuests).getCurrentTask() == 3) {
                speechQueue.add("Tasks completed!");
                ((Player)player).setExp(((Player)player).getExp() + ((Quest)mainQuests).getXPReward());
                speechQueue.add("You have gained : " + ((Quest)mainQuests).getXPReward() + " XP");
                speechQueue.add("You have gained : something");
              }
            }
          }
          else if (interactable instanceof Archer) {
            //MAIN QUEST
            if (((Quest)mainQuests).getCurrentTask() < 3 && !((Quest)mainQuests).getComplete()) {
              ((Quest)mainQuests).updateObjective(2);
              System.out.println(mainQuests.getCurrentTask());
              if (((Quest)mainQuests).getCurrentTask() == 3) {
                speechQueue.add("Tasks completed!");
                ((Player)player).setExp(((Player)player).getExp() + ((Quest)mainQuests).getXPReward());
                speechQueue.add("You have gained : " + ((Quest)mainQuests).getXPReward() + " XP");
                speechQueue.add("You have gained : something");
              }
            }
          }
          //Poison Quest (huntquestB/(1 in index)
          else if (interactable instanceof PoisonSnake) {
            if (((Quest)sideQuests[1]).getActive() && !((Quest)sideQuests[1]).getComplete()) {
              
              ((Quest)sideQuests[1]).setComplete(((Quest)sideQuests[1]).updateObjective(1));
              if (((Quest)sideQuests[1]).getCurrentTask() == 7) {
                System.out.println("Quest completed: " + ((Quest)sideQuests[1]).getName());
                ((Player)player).setExp(((Player)player).getExp() +((Quest)sideQuests[1]).getXPReward());
                speechQueue.add("You have gained : " + ((Quest)sideQuests[1]).getXPReward() + " XP");
                speechQueue.add("You have gained : something");
              }
            }
            // MAIN QUEST
            if (((Quest)mainQuests).getCurrentTask() > 4 && ((Quest)mainQuests).getCurrentTask() < 7 && !((Quest)mainQuests).getComplete()) {
              ((Quest)mainQuests).updateObjective(1);
              System.out.println(mainQuests.getCurrentTask());
              if (((Quest)mainQuests).getCurrentTask() == 7) {
                speechQueue.add("Tasks completed!");
                ((Player)player).setExp(((Player)player).getExp() + ((Quest)mainQuests).getXPReward());
                speechQueue.add("You have gained : " + ((Quest)mainQuests).getXPReward() + " XP");
                speechQueue.add("You have gained : something");
              }
            }
          }
          else if (interactable instanceof PoisonSpider) {
            if (((Quest)sideQuests[1]).getActive() && !((Quest)sideQuests[1]).getComplete()) {
              ((Quest)sideQuests[1]).setComplete(((Quest)sideQuests[0]).updateObjective(2));
              if (((Quest)sideQuests[1]).getComplete()) {
                speechQueue.add("Quest completed: " + ((Quest)sideQuests[1]).getName());
                ((Player)player).setExp(((Player)player).getExp() +((Quest)sideQuests[1]).getXPReward());
                speechQueue.add("You have gained : " + ((Quest)sideQuests[1]).getXPReward() + " XP");
                speechQueue.add("You have gained : something");
              }
            }
            // MAIN QUEST
            if (((Quest)mainQuests).getCurrentTask() > 4 && ((Quest)mainQuests).getCurrentTask() < 7 && !((Quest)mainQuests).getComplete()) {
              ((Quest)mainQuests).updateObjective(2);
              System.out.println(mainQuests.getCurrentTask());
              if (((Quest)mainQuests).getCurrentTask() == 7) {
                speechQueue.add("Tasks completed!");
                ((Player)player).setExp(((Player)player).getExp() + ((Quest)mainQuests).getXPReward());
                speechQueue.add("You have gained : " + ((Quest)mainQuests).getXPReward() + " XP");
                speechQueue.add("You have gained : something");
              }
            }
          }
          //Frost Quest (huntquestC/(2 in index)
          else if (interactable instanceof FrostSnake) {
            if (((Quest)sideQuests[2]).getActive() && !((Quest)sideQuests[2]).getComplete()) {
              ((Quest)sideQuests[2]).setComplete(((Quest)sideQuests[2]).updateObjective(1));
              if (((Quest)sideQuests[2]).getComplete()) {
                speechQueue.add("Quest completed: " + ((Quest)sideQuests[2]).getName());
                ((Player)player).setExp(((Player)player).getExp() +((Quest)sideQuests[2]).getXPReward());
                speechQueue.add("You have gained : " + ((Quest)sideQuests[2]).getXPReward() + " XP");
                speechQueue.add("You have gained : something");
              }
            }
            // MAIN QUEST
            if (((Quest)mainQuests).getCurrentTask() > 11 && ((Quest)mainQuests).getCurrentTask() < 14 && !((Quest)mainQuests).getComplete()) {
              ((Quest)mainQuests).updateObjective(1);
              if (((Quest)mainQuests).getCurrentTask() == 14) {
                speechQueue.add("Tasks completed!");
                ((Player)player).setExp(((Player)player).getExp() + ((Quest)mainQuests).getXPReward());
                speechQueue.add("You have gained : " + ((Quest)mainQuests).getXPReward() + " XP");
                speechQueue.add("You have gained : something");
              }
            }
          }
          else if (interactable instanceof FrostSpider) {
            if (((Quest)sideQuests[2]).getActive() && !((Quest)sideQuests[2]).getComplete()) {
              ((Quest)sideQuests[2]).setComplete(((Quest)sideQuests[2]).updateObjective(2));
              if (((Quest)sideQuests[2]).getComplete()) {
                speechQueue.add("Quest completed: " + ((Quest)sideQuests[2]).getName());
                ((Player)player).setExp(((Player)player).getExp() +((Quest)sideQuests[2]).getXPReward());
                speechQueue.add("You have gained : " + ((Quest)sideQuests[2]).getXPReward() + " XP");
                speechQueue.add("You have gained : something");
              }
            }
            // MAIN QUEST
            if (((Quest)mainQuests).getCurrentTask() > 11 && ((Quest)mainQuests).getCurrentTask() < 14 && !((Quest)mainQuests).getComplete()) {
              ((Quest)mainQuests).updateObjective(1);
              if (((Quest)mainQuests).getCurrentTask() == 14) {
                speechQueue.add("Tasks completed!");
                ((Player)player).setExp(((Player)player).getExp() + ((Quest)mainQuests).getXPReward());
                speechQueue.add("You have gained : " + ((Quest)mainQuests).getXPReward() + " XP");
                speechQueue.add("You have gained : something");
              }
            }
          }
          //Fire Quest (huntquestD/(3 in index)
          else if (interactable instanceof FireSnake) {
            if (((Quest)sideQuests[3]).getActive() && !((Quest)sideQuests[3]).getComplete()) {
              ((Quest)sideQuests[3]).setComplete(((Quest)sideQuests[3]).updateObjective(1));
              if (((Quest)sideQuests[3]).getComplete()) {
                speechQueue.add("Quest completed: " + ((Quest)sideQuests[3]).getName());
                ((Player)player).setExp(((Player)player).getExp() +((Quest)sideQuests[3]).getXPReward());
                speechQueue.add("You have gained : " + ((Quest)sideQuests[3]).getXPReward() + " XP");
                speechQueue.add("You have gained : something");
              }
            }
            // MAIN QUEST
            if (((Quest)mainQuests).getCurrentTask() > 18 && ((Quest)mainQuests).getCurrentTask() < 21 && !((Quest)mainQuests).getComplete()) {
              ((Quest)mainQuests).updateObjective(1);
              if (((Quest)mainQuests).getCurrentTask() == 21) {
                speechQueue.add("Tasks completed!");
                ((Player)player).setExp(((Player)player).getExp() + ((Quest)mainQuests).getXPReward());
                speechQueue.add("You have gained : " + ((Quest)mainQuests).getXPReward() + " XP");
                speechQueue.add("You have gained : something");
              }
            }
          }
          else if (interactable instanceof FireSpider) {
            if (((Quest)sideQuests[3]).getActive() && !((Quest)sideQuests[3]).getComplete()) {
              ((Quest)sideQuests[3]).setComplete(((Quest)sideQuests[3]).updateObjective(2));
              if (((Quest)sideQuests[3]).getComplete()) {
                speechQueue.add("Quest completed: " + ((Quest)sideQuests[3]).getName());
                ((Player)player).setExp(((Quest)sideQuests[3]).getXPReward());
                speechQueue.add("You have gained : " + ((Quest)sideQuests[3]).getXPReward() + " XP");
                speechQueue.add("You have gained : something");
              }
            }
            // MAIN QUEST
            if (((Quest)mainQuests).getCurrentTask() > 18 && ((Quest)mainQuests).getCurrentTask() < 21 && !((Quest)mainQuests).getComplete()) {
              ((Quest)mainQuests).updateObjective(2);
              if (((Quest)mainQuests).getCurrentTask() == 21) {
                speechQueue.add("Tasks completed!");
                ((Player)player).setExp(((Player)player).getExp() + ((Quest)mainQuests).getXPReward());
                speechQueue.add("You have gained : " + ((Quest)mainQuests).getXPReward() + " XP");
                speechQueue.add("You have gained : something");
              }
            }
          }
          
          //Boss Quest (HuntQuestE/(4 in index)
          else if (interactable instanceof PoisonBoss) {
            if (((Quest)sideQuests[4]).getActive() && !((Quest)sideQuests[4]).getComplete()) {
              ((Quest)sideQuests[4]).setComplete(((Quest)sideQuests[4]).updateObjective(1));
              if (((Quest)sideQuests[4]).getComplete()) {
                speechQueue.add("Quest completed: " + ((Quest)sideQuests[4]).getName());
                ((Player)player).setExp(((Player)player).getExp() +((Quest)sideQuests[4]).getXPReward());
                speechQueue.add("You have gained : " + ((Quest)sideQuests[0]).getXPReward() + " XP");
                speechQueue.add("You have gained : something");
              }
            }
            // MAIN QUEST
            if (((Quest)mainQuests).getCurrentTask() == 9 && !((Quest)mainQuests).getComplete()) {
              ((Quest)mainQuests).updateObjective(1);
              if (((Quest)mainQuests).getCurrentTask() == 10) {
                speechQueue.add("Task complete!");
                speechQueue.add(((Quest)mainQuests).getTask(9));
                ((Player)player).setExp(((Player)player).getExp() + ((Quest)mainQuests).getXPReward());
                speechQueue.add("You have gained : " + ((Quest)mainQuests).getXPReward() + " XP");
                speechQueue.add("You have gained : something");
              }
            }
          }
          else if (interactable instanceof FrostBoss) {
            if (((Quest)sideQuests[4]).getActive() && !((Quest)sideQuests[4]).getComplete()) {
              ((Quest)sideQuests[4]).setComplete(((Quest)sideQuests[4]).updateObjective(2));
              if (((Quest)sideQuests[4]).getComplete()) {
                speechQueue.add("Quest completed: " + ((Quest)sideQuests[4]).getName());
                ((Player)player).setExp(((Player)player).getExp() +((Quest)sideQuests[4]).getXPReward());
                speechQueue.add("You have gained : " + ((Quest)sideQuests[4]).getXPReward() + " XP");
                speechQueue.add("You have gained : something");
              }
            }
            // MAIN QUEST
            if (((Quest)mainQuests).getCurrentTask() == 16 && !((Quest)mainQuests).getComplete()) {
              ((Quest)mainQuests).updateObjective(1);
              if (((Quest)mainQuests).getCurrentTask() == 17) {
                speechQueue.add("Task complete!");
                ((Player)player).setExp(((Player)player).getExp() + ((Quest)mainQuests).getXPReward());
                speechQueue.add("You have gained : " + ((Quest)mainQuests).getXPReward() + " XP");
                speechQueue.add("You have gained : something");
              }
            }
          }
          else if (interactable instanceof FireBoss) {
            if (((Quest)sideQuests[4]).getActive() && !((Quest)sideQuests[4]).getComplete()) {
              ((Quest)sideQuests[4]).setComplete(((Quest)sideQuests[4]).updateObjective(3));
              if (((Quest)sideQuests[4]).getComplete()) {
                speechQueue.add("Quest completed: " + ((Quest)sideQuests[4]).getName());
                ((Player)player).setExp(((Player)player).getExp() + ((Quest)sideQuests[4]).getXPReward());
                speechQueue.add("You have gained : " + ((Quest)sideQuests[4]).getXPReward() + " XP");
                speechQueue.add("You have gained : something");
              }
            }
            // MAIN QUEST
            if (((Quest)mainQuests).getCurrentTask() == 24 && !((Quest)mainQuests).getComplete()) {
              ((Quest)mainQuests).updateObjective(1);
              if (((Quest)mainQuests).getCurrentTask() == 25) {
                speechQueue.add("Task complete!");
                ((Player)player).setExp(((Player)player).getExp() + ((Quest)mainQuests).getXPReward());
                speechQueue.add("You have gained : " + ((Quest)mainQuests).getXPReward() + " XP");
                speechQueue.add("You have gained : something");
              }
            }
          }
          else if (interactable instanceof MangatBoss) {
            if (((Quest)mainQuests).getCurrentTask() == 25 && !((Quest)mainQuests).getComplete()) {
              ((Quest)mainQuests).updateObjective(1);
              if (((Quest)mainQuests).getCurrentTask() == 26) {
                speechQueue.add("You have slain Mangat!");
                ((Player)player).setExp(((Player)player).getExp() + ((Quest)mainQuests).getXPReward());
                speechQueue.add("You have gained : " + ((Quest)mainQuests).getXPReward() + " XP");
                speechQueue.add("You have gained : something");
              }
            }
          }
        }
        
      }
      else if (interactable instanceof NPC) {
        
        if (((NPC)interactable).getQuestGiver()) { 
          
          Quest newQuest = ((NPC)interactable).getQuest();
          
          if (newQuest instanceof HuntQuest && !((Quest)newQuest).getComplete()) {
            ((Quest)sideQuests[0]).setActive(true);
            ((Quest)newQuest).initialize(world);
            speechQueue.add("Please help me defeat these bandits!");
          }
          
          if (newQuest instanceof HuntQuestB && !((Quest)newQuest).getComplete()) {
            ((Quest)sideQuests[1]).setActive(true);
            ((Quest)newQuest).initialize(world);
            speechQueue.add("These poison monsters need a beating!");
          }
          
          if (newQuest instanceof HuntQuestC && !((Quest)newQuest).getComplete()) {
            ((Quest)sideQuests[2]).setActive(true);
            ((Quest)newQuest).initialize(world);
            speechQueue.add("Help me with a little something eh?");
          }
          
          if (newQuest instanceof HuntQuestD && !((Quest)newQuest).getComplete()) {
            ((Quest)sideQuests[3]).setActive(true);
            ((Quest)newQuest).initialize(world);
            speechQueue.add("Prove your worth to a master!");
          }
          
          if (newQuest instanceof HuntQuestE && !((Quest)newQuest).getComplete()) {
            ((Quest)sideQuests[4]).setActive(true);
            ((Quest)newQuest).initialize(world);
            speechQueue.add("Show me what you got.");
            speechQueue.add("If you defeat the strongest monsters in this realm,");
            speechQueue.add("I'd consider you as a master hunter!");
          }
          
          if (newQuest instanceof FetchQuest && !((Quest)newQuest).getComplete()) {
            ((Quest)sideQuests[5]).setActive(true);
            ((Quest)newQuest).initialize(world);
          }
          
          if (newQuest instanceof FetchQuestB && !((Quest)newQuest).getComplete()) {
            ((Quest)sideQuests[6]).setActive(true);
            ((Quest)newQuest).initialize(world);
          }
          
          if (newQuest instanceof FetchQuestC && !((Quest)newQuest).getComplete()) {
            ((Quest)sideQuests[7]).setActive(true);
            ((Quest)newQuest).initialize(world);
          }
          
          // Main Quest handling
          if (((NPC)interactable).getQuest() instanceof MainQuestA) {
            Quest mainQuest = ((NPC)interactable).getQuest();
            if (((NPC)interactable).getName() == "Bob" && mainQuest.getCurrentTask() == 0) {
              mainQuest.initialize(world);
              speechQueue.add("Hey buddy!");
              speechQueue.add("You look kind of strong.");
              speechQueue.add("There's bandits and archers everywhere!");
              speechQueue.add("Help me defeat these bandits and archers!");
            }
            else if (((NPC)interactable).getName() == "Bob" && mainQuest.getCurrentTask() == 3) {
              mainQuest.setCurrentTask(4);
              speechQueue.add("Hey, please find my farmer friend,");
              speechQueue.add("He needs help in the poison lands.");
              speechQueue.add(mainQuest.getTask(4));
            }
            else if (((NPC)interactable).getName() == "Farmer" && mainQuest.getCurrentTask() == 4) {
              mainQuest.setCurrentTask(5);
              speechQueue.add("These snakes are killing my crops, help me get rid of dem");
              speechQueue.add(mainQuest.getTask(5));
            }
            else if (((NPC)interactable).getName() == "Farmer" && mainQuest.getCurrentTask() == 7) {
              mainQuest.setCurrentTask(8);
              speechQueue.add("Damn, you're strong!");
              speechQueue.add("I'm sure the king has the work for your kind");
              speechQueue.add(mainQuest.getTask(8));
            }
            else if (((NPC)interactable).getName() == "King Tagnam") {
              if (mainQuest.getCurrentTask() == 8) {
                mainQuest.setCurrentTask(9);
                speechQueue.add("Who're you?");
                speechQueue.add(player.getName() + ":" + "I'm " + player.getName());
                speechQueue.add("I've heard about your strength");
                speechQueue.add("Let me see you test it");
                speechQueue.add(mainQuest.getTask(9));
              }
              else if (mainQuest.getCurrentTask() == 10) {
                mainQuest.setCurrentTask(11);
                speechQueue.add("Not bad, but I still doubt your worth");
                speechQueue.add("Prove it to me, speak with the ice fisher");    
                speechQueue.add(mainQuest.getTask(11));
              }
              else if (mainQuest.getCurrentTask() == 15) {
                mainQuest.setCurrentTask(16);
                speechQueue.add("Well done I must say!");
                speechQueue.add("But I'm sure you can do better!");
                speechQueue.add(mainQuest.getTask(16));
              }
              else if (mainQuest.getCurrentTask() == 17) {
                mainQuest.setCurrentTask(18);
                speechQueue.add("I'm impressed");
                speechQueue.add("There's one section where I'm reluctant to send my men");
                speechQueue.add("I want you to make it safe for them to patrol");
                speechQueue.add("For glory and honour of course!");
                speechQueue.add(mainQuest.getTask(18));
              }
              else if (mainQuest.getCurrentTask() == 24) {
                mainQuest.setCurrentTask(25);
                speechQueue.add("You've managed to survive for this long, I don't know how,");
                speechQueue.add("Anyways, I can't let you be the strongest, only I can.");
                speechQueue.add(mainQuest.getTask(25));
                World initialGround = world[60][86];
                world[60][86] = new MangatBoss(100,100,100,100,100,100,"u screwed",60,86,initialGround);
              }
            }
            else if (((NPC)interactable).getName() == "Ice Fisher") {
              if (mainQuest.getCurrentTask() == 11) {
                mainQuest.setCurrentTask(12);
                speechQueue.add("Did the King send you? I need space for fishing man!");
                speechQueue.add(mainQuest.getTask(12));
              }
              else if (mainQuest.getCurrentTask() == 14) {
                mainQuest.setCurrentTask(15);
                speechQueue.add("Thanks, the King will probably want to know about this.");
                speechQueue.add(mainQuest.getTask(15));
              }
            }
            else if (((NPC)interactable).getName() == "Volat" && mainQuest.getCurrentTask() == 18) {
              mainQuest.setCurrentTask(19);
              speechQueue.add("Burn burn BUURRRRN burrnnnnnn");
              speechQueue.add("*You somehow understood what he said*");
              speechQueue.add(mainQuest.getTask(19));
            } 
            else if (((NPC)interactable).getName() == "Vivian" && mainQuest.getCurrentTask() == 21) {
              mainQuest.setCurrentTask(22);
              speechQueue.add("Thanks for the help!");
              speechQueue.add("And thanks for dealing up with Volat.");
              speechQueue.add("I've already sent a message to the King about what you've done!");
              speechQueue.add(mainQuest.getTask(22));
            } 
            else if (((NPC)interactable).getName() == "Chancellor") {
              if (mainQuest.getCurrentTask() == 24) {
                speechQueue.add("The King has told me to meet him in a house outside the capital");
                speechQueue.add("Don't keep him waiting!");
                mainQuest.setCurrentTask(25);
                speechQueue.add(mainQuest.getTask(25));
              }
              else if(mainQuest.getCurrentTask() == 27) {
                mainQuest.setCurrentTask(28);
                speechQueue.add("Oh my, the King was evil all along?");
                speechQueue.add("Thank you for saving our kingdom!");
                speechQueue.add("Perhaps you shall be our new King!");
                mainQuest.setComplete(mainQuest.updateObjective(0));
                speechQueue.add("A New King!");
              }
            } 
          } 
        }else{
        speechQueue.add(((NPC)interactable).speak());
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


