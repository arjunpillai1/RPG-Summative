/*
 * [GameFrame.java]
 * The place where the game is active
 * @author Albert, Guy, Arjun, Aiden, Johann
 * 05/30/2018
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


//import music
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

//import java.awt.*;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;

//Keyboard imports
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//Mouse imports
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

class GameFrame extends JFrame {
  private static JFrame frame;
  private static int maxX,maxY, GridToScreenRatio;
  // World and Quests
  static World[][] world;
  Quest[] sideQuests = new Quest[8];
  static Quest mainQuests;
  ArrayList<Quest> activeQuests = new ArrayList<Quest>();
  //Keep track of speeches
  ArrayList<String> speechQueue = new ArrayList<String>();
  //To prevent player for attacking far
  int spaceX; //elon musk?
  int spaceY;
  //Current dialogue
  int advanceDialogue = 0;
  //Clock variables
  Clock clock = new Clock();
  Clock enemyDelay = new Clock();
  Clock total = new Clock();
  Clock bossTimer = new Clock();
  JPanel mainPanel = new JPanel();
  //Boolean values to track active methods
  Boolean dialogue = false;
  boolean playerMove = true;
  Boolean allowRespawn = false;
  //Inventory
  Inventory bag = new Inventory();
  //For representing what image to draw
  int playerState=0;

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
  /**
   * Constructor for Game Frame
   * @param world array, array of side quests, main quest, player 
   */
  GameFrame(World[][] world, Quest[] sideQuests, Quest mainQuests, Player player) {
    super("Epic Medieval Fantasy");
    this.world = world;
    this.player = player;

    maxX = Toolkit.getDefaultToolkit().getScreenSize().width;
    maxY = Toolkit.getDefaultToolkit().getScreenSize().height;
    GridToScreenRatio = maxY / (10);  //ratio to fit in screen as square map

    //Music initalisation
    try {
      menuMusicFile = new File("Skyrim Music - Dragonborn (Main Theme).wav");
      menuMusicStream = AudioSystem.getAudioInputStream(menuMusicFile);
      info = new DataLine.Info(Clip.class, menuMusicStream.getFormat());
      clip = (Clip) AudioSystem.getLine(info);
      clip.open(menuMusicStream);
    }catch (Exception e) {};

    clip.start();

    //System.out.println("Map size: "+world.length+" by "+world[0].length + "\nScreen size: "+ maxX +"x"+maxY+ " Ratio: " + GridToScreenRatio);

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
    
    //keyboard
    MyKeyListener keyListener = new MyKeyListener();
    this.addKeyListener(keyListener);
    
    //mouse listener
    MyMouseListener mouseListener = new MyMouseListener();
    this.addMouseListener(mouseListener);

    this.requestFocusInWindow(); //make sure the frame has focus

    this.setVisible(true);

    //Start the game loop in a separate thread
    Thread t = new Thread(new Runnable() { public void run() { animate(); }}); //start the gameLoop
    t.start();

  } //End of Constructor

  //the main gameloop - this is where the game state is updated
  /**
   * Method that updates all data in the game
   */
  public void animate() {
    boolean firstTime = true;

    while(player.getHealth() > 0){
      if (((int)clock.getElapsedTime()) == 6) {
        respawnEnemies();
        clock.update();
      }


      if (((int)bossTimer.getElapsedTime()) == 60) {
        respawnBoss();
        bossTimer.update();

      }

      for (int i = 0; i < world.length; i++) {
        for (int j = 0; j < world.length; j++) {
          if (world[i][j] instanceof Enemy) {
            if (((Enemy)world[i][j]).getHealth() <= 0)
              ((Enemy)world[i][j]).death(world, i, j);
          }
        }
      }

      if (Math.round(total.getElapsedTime() * 10.0) / 10.0 == 0.1) {
        playerMove = true;
        total.update();
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

  /**
   * Method that saves the current game state
   * @throws e
   */
  public void saveGame() throws Exception{
    File saveGame = new File("saveGame.txt");
    File saveMap = new File("mapSave.txt");
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
    outputPlayer.println(mainQuests.getCurrentTask());
    for (int k = 0; k < sideQuests.length; k++) {
      if (sideQuests[k].getComplete()) {
        outputPlayer.println("true");
      } else {
        outputPlayer.println("false");
      }
      if (sideQuests[k].getActive()) {
        outputPlayer.println(sideQuests[k].getCurrentTask());
      } else {
        outputPlayer.println(0);
      }
    }

    outputPlayer.close();




  }
  /** --------- INNER CLASSES ------------- **/

  // Inner class for the the game area - This is where all the drawing of the screen occurs
  private class GameAreaPanel extends JPanel {
    public void paintComponent(Graphics g) {


      super.paintComponent(g);
      //variables for the player, images, and fonts
      maxX = Toolkit.getDefaultToolkit().getScreenSize().width;
      maxY = Toolkit.getDefaultToolkit().getScreenSize().height;
      int playerX = player.getX();
      int playerY = player.getY();
      int countX = 0;
      int countY = 0;
      Font questTitle = new Font("Arial", Font.BOLD, 16);
      Font questTask = new Font("Verdana", Font.PLAIN, 15);
      Font questLogTitle = new Font("Verdana", Font.BOLD, 14);
      int imageChoice;
      int enemyDirection=1;
      
      Image[] floorTextures={Toolkit.getDefaultToolkit().getImage("Grass Texture.png"),
        Toolkit.getDefaultToolkit().getImage("Poison Grass Texture.png"),
        Toolkit.getDefaultToolkit().getImage("Frost Grass Texture.png"),
        Toolkit.getDefaultToolkit().getImage("Fire Grass Texture.png"),
        Toolkit.getDefaultToolkit().getImage("dirt.png"),
        Toolkit.getDefaultToolkit().getImage("floor.jpg"),
        Toolkit.getDefaultToolkit().getImage("Bridge tile.png")
      };

      Image[] playerEmpty={Toolkit.getDefaultToolkit().getImage("No Armour Character Sprite left.png"),
        Toolkit.getDefaultToolkit().getImage("No Armour Character Sprite right.png"),
        Toolkit.getDefaultToolkit().getImage("No Armour Character Sprite.png"),
        Toolkit.getDefaultToolkit().getImage("No Armour Character Sprite with Dagger back idle.png")
      };

      Image[] playerNoArmour={Toolkit.getDefaultToolkit().getImage("No Armour Character Sprite with Dagger left idle.png"),
        Toolkit.getDefaultToolkit().getImage("No Armour Character Sprite with Dagger left attack.png"),
        Toolkit.getDefaultToolkit().getImage("No Armour Character Sprite with Dagger right idle.png"),
        Toolkit.getDefaultToolkit().getImage("No Armour Character Sprite with Dagger right attack.png"),
        Toolkit.getDefaultToolkit().getImage("No Armour Character Sprite with Dagger front idle.png"),
        Toolkit.getDefaultToolkit().getImage("No Armour Character Sprite with Dagger front attack.png"),
        Toolkit.getDefaultToolkit().getImage("No Armour Character Sprite with Dagger back idle.png"),
        Toolkit.getDefaultToolkit().getImage("No Armour Character Sprite with Dagger back attack.png")
      };

      Image[] playerArmour={Toolkit.getDefaultToolkit().getImage("Armoured Character Sprite with Dagger left idle.png"),
        Toolkit.getDefaultToolkit().getImage("Armoured Character Sprite with Dagger left attack.png"),
        Toolkit.getDefaultToolkit().getImage("Armoured Character Sprite with Dagger right idle.png"),
        Toolkit.getDefaultToolkit().getImage("Armoured Character Sprite with Dagger right attack.png"),
        Toolkit.getDefaultToolkit().getImage("Armoured Character Sprite with Dagger front idle.png"),
        Toolkit.getDefaultToolkit().getImage("Armoured Character Sprite with Dagger front attack.png"),
        Toolkit.getDefaultToolkit().getImage("Armoured Character Sprite with Dagger back idle.png"),
        Toolkit.getDefaultToolkit().getImage("Armoured Character Sprite with Dagger back attack.png")
      };

      Image[] npcSprites={Toolkit.getDefaultToolkit().getImage("NPC 1.png"),
        Toolkit.getDefaultToolkit().getImage("NPC 2.png"),
        Toolkit.getDefaultToolkit().getImage("NPC 3.png"),
        Toolkit.getDefaultToolkit().getImage("NPC 4.png"),
        Toolkit.getDefaultToolkit().getImage("NPC 5.png")
      };

      Image[] knights={Toolkit.getDefaultToolkit().getImage("Knight1 IDLE left.png"),
        Toolkit.getDefaultToolkit().getImage("Knight1 IDLE right.png"),
        Toolkit.getDefaultToolkit().getImage("Knight1 Attack left.png"),
        Toolkit.getDefaultToolkit().getImage("Knight1 Attack right.png"),
        Toolkit.getDefaultToolkit().getImage("knight2 IDLE left.png"),
        Toolkit.getDefaultToolkit().getImage("knight2 IDLE right.png"),
        Toolkit.getDefaultToolkit().getImage("knight2 Attack left.png"),
        Toolkit.getDefaultToolkit().getImage("knight2 Attack right.png"),
        Toolkit.getDefaultToolkit().getImage("knight3 IDLE left.png"),
        Toolkit.getDefaultToolkit().getImage("knight3 IDLE right.png"),
        Toolkit.getDefaultToolkit().getImage("knight3 Attack left.png"),
        Toolkit.getDefaultToolkit().getImage("knight3 Attack right.png"),
      };

      Image[] trolls={Toolkit.getDefaultToolkit().getImage("Troll1 IDLE left.png"),
        Toolkit.getDefaultToolkit().getImage("Troll1 IDLE right.png"),
        Toolkit.getDefaultToolkit().getImage("Troll1 Attack left.png"),
        Toolkit.getDefaultToolkit().getImage("Troll1 Attack right.png"),
        Toolkit.getDefaultToolkit().getImage("Troll2 IDLE left.png"),
        Toolkit.getDefaultToolkit().getImage("Troll2 IDLE right.png"),
        Toolkit.getDefaultToolkit().getImage("Troll2 Attack left.png"),
        Toolkit.getDefaultToolkit().getImage("Troll2 Attack right.png"),
        Toolkit.getDefaultToolkit().getImage("Troll3 IDLE left.png"),
        Toolkit.getDefaultToolkit().getImage("Troll3 IDLE right.png"),
        Toolkit.getDefaultToolkit().getImage("Troll3 Attack left.png"),
        Toolkit.getDefaultToolkit().getImage("Troll3 Attack right.png"),
      };

      Image[] spider={Toolkit.getDefaultToolkit().getImage("Poison Spider IDLE left.png"),
        Toolkit.getDefaultToolkit().getImage("Poison Spider IDLE right.png"),
        Toolkit.getDefaultToolkit().getImage("Frost Spider IDLE left.png"),
        Toolkit.getDefaultToolkit().getImage("Frost Spider IDLE right.png"),
        Toolkit.getDefaultToolkit().getImage("Fire Spider IDLE left.png"),
        Toolkit.getDefaultToolkit().getImage("Fire Spider IDLE right.png")
      };

      Image[] sorcerer={Toolkit.getDefaultToolkit().getImage("Poison sorcerer IDLE left.png"),
        Toolkit.getDefaultToolkit().getImage("Poison sorcerer IDLE right.png"),
        Toolkit.getDefaultToolkit().getImage("Frost sorcerer IDLE left.png"),
        Toolkit.getDefaultToolkit().getImage("Frost sorcerer IDLE right.png"),
        Toolkit.getDefaultToolkit().getImage("Fire sorcerer IDLE left.png"),
        Toolkit.getDefaultToolkit().getImage("Fire sorcerer IDLE right.png"),
      };

      Image waterImage=Toolkit.getDefaultToolkit().getImage("Water.png");
      Image chestImage=Toolkit.getDefaultToolkit().getImage("chest texture.png");
      Image treeImage=Toolkit.getDefaultToolkit().getImage("tree.png");
      Image castleWallImage=Toolkit.getDefaultToolkit().getImage("castle wall.png");
      Image wallImage=Toolkit.getDefaultToolkit().getImage("wall.png");
      Image caveWallImage=Toolkit.getDefaultToolkit().getImage("cave wall tile.png");

      setDoubleBuffered(true);
      //Colors
      Color questLog = new Color(205, 133, 63);
      Color royalYellow = new Color(250, 218, 94);
      Color speech = new Color(0,49,82);
      Color sideQuest = new Color(0, 128, 255);



      // draw map
      for(int i =  playerX - 4; i <= playerX + 4;i=i+1)
      {
        for(int j =playerY - 4; j <= playerY + 4;j=j+1)
        {

          // Environment

          if (world[i][j] instanceof NormalGrass || world[i][j] instanceof CityGrounds) {
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

          }else if (world[i][j] instanceof Bridge) {
            g.drawImage(floorTextures[6],(j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);

          } else if (world[i][j] instanceof Tree) {
            g.drawImage(treeImage,(j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);

          } else if (world[i][j] instanceof CaveWall) {
            g.drawImage(caveWallImage,(j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
          }
          //NPCs
          if (world[i][j] instanceof NPC) {
            imageChoice=((NPC)(world[i][j])).getImageChoice();
            g.drawImage(npcSprites[imageChoice],(j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
            g.setColor(Color.WHITE);
            g.drawString(((Character)world[i][j]).getName(), (j - (j - (countY %9))) * GridToScreenRatio + 5, (i - (i - countX)) * GridToScreenRatio + 8);

          }
          //Enemies
          if (world[i][j] instanceof Enemy || world[i][j] instanceof Player){

            World characterImageBackground=null;
            if (world[i][j] instanceof Enemy){
              characterImageBackground=((Enemy)world[i][j]).getPreviousStep();
              enemyDirection=((Enemy)world[i][j]).getDirection();
            }else if(world[i][j] instanceof Player){
              characterImageBackground=((Player)world[i][j]).getPreviousStep();
            }

            if (characterImageBackground instanceof Grass) {
              g.drawImage(floorTextures[0],(j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);

            } else if (characterImageBackground instanceof PoisonGrass) {
              g.drawImage(floorTextures[1],(j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);

            } else if (characterImageBackground instanceof FrostGrass) {
              g.drawImage(floorTextures[2],(j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);

            } else if (characterImageBackground instanceof FireGrass) {
              g.drawImage(floorTextures[3],(j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);

            } else if (characterImageBackground instanceof Dirt) {
              g.drawImage(floorTextures[4],(j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);

            } else if (characterImageBackground instanceof HouseFloor) {
              g.drawImage(floorTextures[5],(j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);

            }else if (characterImageBackground instanceof Bridge) {
              g.drawImage(floorTextures[6],(j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
            }
          }
          if (world[i][j] instanceof Bandit) {
            imageChoice=((Bandit)(world[i][j])).getImageChoice();
            g.drawImage(knights[imageChoice+enemyDirection],(j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
            g.setColor(Color.WHITE);
            g.drawString(((Character)world[i][j]).getName(), (j - (j - (countY %9))) * GridToScreenRatio + 5, (i - (i - countX)) * GridToScreenRatio + 8);
          }
          else if (world[i][j] instanceof Archer) {
            imageChoice=((Archer)(world[i][j])).getImageChoice();
            g.drawImage(trolls[imageChoice+enemyDirection],(j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
            g.setColor(Color.WHITE);
            g.drawString(((Character)world[i][j]).getName(), (j - (j - (countY %9))) * GridToScreenRatio + 5, (i - (i - countX)) * GridToScreenRatio + 8);
          }
          else if (world[i][j] instanceof PoisonSpider) {
            g.drawImage(spider[0+enemyDirection],(j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
            g.setColor(Color.WHITE);
            g.drawString(((Character)world[i][j]).getName(), (j - (j - (countY %9))) * GridToScreenRatio + 5, (i - (i - countX)) * GridToScreenRatio + 8);
          }
          else if (world[i][j] instanceof FrostSpider) {
            g.drawImage(spider[2+enemyDirection],(j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
            g.setColor(Color.WHITE);
            g.drawString(((Character)world[i][j]).getName(), (j - (j - (countY %9))) * GridToScreenRatio + 5, (i - (i - countX)) * GridToScreenRatio + 8);
          }
          else if (world[i][j] instanceof FireSpider) {
            g.drawImage(spider[4+enemyDirection],(j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
            g.setColor(Color.WHITE);
            g.drawString(((Character)world[i][j]).getName(), (j - (j - (countY %9))) * GridToScreenRatio + 5, (i - (i - countX)) * GridToScreenRatio + 8);
          }
          else if (world[i][j] instanceof PoisonSnake) {
            g.drawImage(sorcerer[0+enemyDirection],(j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
            g.setColor(Color.WHITE);
            g.drawString(((Character)world[i][j]).getName(), (j - (j - (countY %9))) * GridToScreenRatio + 5, (i - (i - countX)) * GridToScreenRatio + 8);
          }
          else if (world[i][j] instanceof FrostSnake) {
            g.drawImage(sorcerer[2+enemyDirection],(j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
            g.setColor(Color.WHITE);
            g.drawString(((Character)world[i][j]).getName(), (j - (j - (countY %9))) * GridToScreenRatio + 5, (i - (i - countX)) * GridToScreenRatio + 8);
          }
          else if (world[i][j] instanceof FireSnake) {
            g.drawImage(sorcerer[4+enemyDirection],(j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
            g.setColor(Color.WHITE);
            g.drawString(((Character)world[i][j]).getName(), (j - (j - (countY %9))) * GridToScreenRatio + 5, (i - (i - countX)) * GridToScreenRatio + 8);
          }
          //Player
          else if (world[i][j] instanceof Player) {
            if (((Player)(world[i][j])).getEquippedArmour()){
              g.drawImage(playerArmour[playerState],(j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
            }
            else if (((Player)(world[i][j])).getEquippedWeapon()){
              g.drawImage(playerNoArmour[playerState],(j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
            }
            else{
              g.drawImage(playerEmpty[playerState/2],(j - (j - (countY %9))) * GridToScreenRatio, (i - (i - countX)) * GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
            }
            g.setColor(Color.WHITE);
            g.drawString(((Character)world[i][j]).getName(), (j - (j - (countY %9))) * GridToScreenRatio + 5, (i - (i - countX)) * GridToScreenRatio + 8);
            g.drawString("Lv:" + ((Player)world[i][j]).getLvl(), (j - (j - (countY %9))) * GridToScreenRatio + 30, (i - (i - countX)) * GridToScreenRatio + 30);
          }

          countY++;
        }
        countX++;
      }
      //player health bar
      g.setColor(Color.BLACK);
      g.fillRect((maxX/4) - 205 , maxY - 150, 410, 35);
      g.setColor(Color.RED);
      g.fillRect((maxX/4) - 200 , maxY - 145, (player.getHealth() * 400) / player.getMaxHealth(), 25);
      g.drawString(player.getHealth() + " / " + player.getMaxHealth(), maxX/5, maxY - 100);

      updateActiveQuests();
      //draw the quest log
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
      // add the quests
      for (int i = 0; i < activeQuests.size(); i++) {
        if (activeQuests.get(i) instanceof MainQuestA) {
          g.setColor(Color.BLACK);
          g.setFont(questTitle);
          g.drawString(mainQuests.getName(), 15, 55 + i*30);
          g.setFont(questTask);
          if (mainQuests.getCurrentTask() == 1 || mainQuests.getCurrentTask() == 5 || mainQuests.getCurrentTask() == 12 ||
              mainQuests.getCurrentTask() == 19) {
            g.drawString("- " + mainQuests.getTask(mainQuests.getCurrentTask()) + " (" + ((MainQuestA)mainQuests).trackTask(1) + ")", 15, 55 + i*30 + 20);
            g.drawString("- " + mainQuests.getTask(mainQuests.getCurrentTask()+1) + " (" + ((MainQuestA)mainQuests).trackTask(2) + ")", 15, 55 + i*30 + 40);
          } else {
            g.drawString("- " + mainQuests.getTask(mainQuests.getCurrentTask()) + " (" + ((MainQuestA)mainQuests).trackTask(1) + ")", 15, 55 + i*30 + 20);
          }
        } else {
          g.setColor(sideQuest);
          g.setFont(questTitle);
          g.drawString((activeQuests.get(i)).getName(),10, 65 + 60*i);
          g.setFont(questTask);
          if ((activeQuests.get(i)).getNumTasks() > 1) {
            for (int k = 0; k < (activeQuests.get(i)).getNumTasks(); k++) {
              g.drawString("- " +(activeQuests.get(i)).getTask((activeQuests.get(i)).getCurrentTask() + k), 15, 65 + 60*i + 20*(k+1));
            }
          } else {
            g.drawString("- " +(activeQuests.get(i)).getTask((activeQuests.get(i)).getCurrentTask()), 15, 65 + 60*i + 20);
          }
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

  /*
   *
   *
   *
   */
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
  /*
   *
   *
   *
   */
  void respawnBoss() {
    if (!(world[8][83] instanceof PoisonBoss)) {
      world[8][83] = new PoisonGrass();
      world[8][83] = new PoisonBoss(1,1,1,1,1,1, "Poison Boss", 8, 83,world[8][83] );
    }

    if (!(world[91][89] instanceof FrostBoss)) {
      world[91][89] = new Bridge();
      world[92][89] = new FrostBoss(1,1,1,1,1,1, "Frost Boss", 91, 89, world[91][89] );
    }

    if (!(world[97][10] instanceof FireBoss)) {
      world[97][10] = new FireGrass();
      world[97][10] = new FireBoss(1,1,1,1,1,1, "Fire Boss", 97, 10, world[97][10]);
    }
  }
  /*
   *
   *
   *
   */
  void respawnEnemies() {
    System.out.println("respawn");
    int noobCount = 0, fireCount = 0, frostCount = 0, poisonCount = 0, totalCount = 0;
    Random rand = new Random();
    int enemyType;
    int randX;
    int randY;

    for (int i = 0; i < world.length; i++) {
      for (int j = 0; j < world.length; j++) {
        randX = rand.nextInt(97) + 4;
        randY = rand.nextInt(97) + 4;
        if (noobCount < 2) {
          if (world[randX][randY] instanceof NormalGrass) {
            enemyType = rand.nextInt(2);
            if (enemyType == 1) {
              noobCount++;
              world[randX][randY] = new Bandit(18,3,1,2,3,100,"Bandit", randX, randY, world[randX][randY]);
            } else {
              noobCount++;
              world[randX][randY] = new Archer(10,3,1,1,3,94,"Archer", randX, randY, world[randX][randY]);
            }
          }
        }
        if (poisonCount < 1) {
          if (world[randX][randY] instanceof PoisonGrass) {
            enemyType = rand.nextInt(2);
            if (enemyType == 1) {
              poisonCount++;
              world[randX][randY] = new PoisonSnake(72,9,4,10,8,100,"PoisonSnake", randX, randY, world[randX][randY]);
            } else {
              poisonCount++;
              world[randX][randY] = new PoisonSpider(48,3,9,6,10,96,"PoisonSpider", randX, randY, world[randX][randY]);
            }
          }
        }
        if (frostCount < 2) {
          if (world[randX][randY] instanceof FrostGrass) {
            enemyType = rand.nextInt(2);
            if (enemyType == 1) {
              frostCount++;
              world[randX][randY] = new FrostSnake(108,24,22,16,14,100,"FrostSnake", randX, randY, world[randX][randY]);
            } else {
              frostCount++;
              world[randX][randY] = new FrostSpider(84,16,25,12,15,98,"FrostSpider", randX, randY, world[randX][randY]);
            }
          }
        }
        if (fireCount < 1) {
          if (world[randX][randY] instanceof FireGrass) {
            enemyType = rand.nextInt(2);
            if (enemyType == 1) {
              fireCount++;
              world[randX][randY] = new FireSnake(324,32,36,30,20,100,"FireSnake", randX, randY, world[randX][randY]);
            } else {
              fireCount++;
              world[randX][randY] = new FireSpider(224,26,36,30,20,100,"FireSpider", randX, randY, world[randX][randY]);
            }
          }
        }
      }
    }
    allowRespawn = false;
  }
  /*
   * [Clock.class]
   * Clock class
   * @author Guy
   * 06/14/2018
   */
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

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
      int playerX = player.getX();
      int playerY = player.getY();
      if (playerMove == true) {
        if (KeyEvent.getKeyText(e.getKeyCode()).equals("W")) {  //If 'W' is pressed
          ((Player)world[playerX][playerY]).move(world, 1);
          playerState=6;
        } else if (KeyEvent.getKeyText(e.getKeyCode()).equals("S")) {  //If 'S' is pressed
          ((Player)world[playerX][playerY]).move(world, 2);
          playerState=4;
        } else if (KeyEvent.getKeyText(e.getKeyCode()).equals("A")) {
          ((Player)world[playerX][playerY]).move(world, 3);
          playerState=0;
        } else if (KeyEvent.getKeyText(e.getKeyCode()).equals("D")) {
          ((Player)world[playerX][playerY]).move(world, 4);
          playerState=2;
        }
        playerMove = false;

      }
    }

    public void keyReleased(KeyEvent e) {
    }
  } //end of keyboard listener

  // -----------  Inner class for the keyboard listener - This detects mouse movement & clicks and runs the corresponding methods
  private class MyMouseListener implements MouseListener {

    public void mouseClicked(MouseEvent e) {
      int mouseX = e.getX();
      int mouseY = e.getY();
      int yToTile = ((mouseX / GridToScreenRatio) - 4) + player.getX();
      int xToTile = ((mouseY / GridToScreenRatio) - 4) + player.getY();

      // advance the active dialogue if someone is talking
      if (dialogue == true) {
        advanceDialogue++;
      }


      yToTile = ((mouseX / GridToScreenRatio) - 4) + player.getY();
      xToTile = ((mouseY / GridToScreenRatio) - 4) + player.getX();
      spaceX = Math.abs(xToTile - player.getX());
      spaceY = Math.abs(yToTile - player.getY());

      interact(world[xToTile][yToTile]);

    }

    public void interact(World interactable) {

      if (interactable instanceof Enemy) {
        if (spaceX <= 1 && spaceY <= 1) {
          player.attack(((Enemy)interactable));
        }

        //Quests interaction
        if (((Enemy)interactable).getHealth() <= 0) {
          int enemyLevel = ((CombatCharacter)interactable).getLvl();
          int giveXP = 0;
          allowRespawn = true;
          Random randXP = new Random();
          if (enemyLevel <= 5) {
            giveXP = randXP.nextInt(3);
          } else if (enemyLevel < 10) {
            giveXP = randXP.nextInt(6) + 3;
          } else if (enemyLevel <= 15) {
            giveXP = randXP.nextInt(8) + 4;
          } else if (enemyLevel <= 20) {
            giveXP = randXP.nextInt(11) + 10;
          }

          if (interactable instanceof Bandit) {
            if (((Quest)sideQuests[0]).getActive() && !((Quest)sideQuests[0]).getComplete() && ((Quest)sideQuests[0]).getActive()) {
              ((Quest)sideQuests[0]).setComplete(((Quest)sideQuests[0]).updateObjective(1));
              if (((Quest)sideQuests[0]).getComplete()) {
                speechQueue.add("Quest completed: " + ((Quest)sideQuests[0]).getName());
                ((Player)player).setExp(((Player)player).getExp() +((Quest)sideQuests[0]).getXPReward());
                speechQueue.add("You have gained : " + ((Quest)sideQuests[0]).getXPReward() + " XP");
                bag.find(sideQuests[0].getItemReward());
                speechQueue.add((sideQuests[0].getItemReward()).getName());
              }
            }
            // MAIN QUEST
            if (((Quest)mainQuests).getCurrentTask() < 3 && !((Quest)mainQuests).getComplete() && ((Quest)mainQuests).getActive()) {
              ((Quest)mainQuests).updateObjective(1);

              if (((Quest)mainQuests).getCurrentTask() == 3) {
                speechQueue.add("Tasks completed!");
                ((Player)player).setExp(((Player)player).getExp() + ((Quest)mainQuests).getXPReward());
                speechQueue.add("You have gained : " + ((Quest)mainQuests).getXPReward() + " XP");
              }
            }
          }
          else if (interactable instanceof Archer) {
            //MAIN QUEST
            if (((Quest)mainQuests).getCurrentTask() < 3 && !((Quest)mainQuests).getComplete() && ((Quest)mainQuests).getActive()) {
              ((Quest)mainQuests).updateObjective(2);
              if (((Quest)mainQuests).getCurrentTask() == 3) {
                speechQueue.add("Tasks completed!");
                ((Player)player).setExp(((Player)player).getExp() + ((Quest)mainQuests).getXPReward());
                speechQueue.add("You have gained : " + ((Quest)mainQuests).getXPReward() + " XP");
                bag.find(sideQuests[0].getItemReward());
                speechQueue.add((sideQuests[0].getItemReward()).getName());
              }
            }
          }
          //Poison Quest (huntquestB/(1 in index)
          else if (interactable instanceof PoisonSnake) {
            if (((Quest)sideQuests[1]).getActive() && !((Quest)sideQuests[1]).getComplete() && ((Quest)sideQuests[1]).getActive()) {

              ((Quest)sideQuests[1]).setComplete(((Quest)sideQuests[1]).updateObjective(1));
              if (((Quest)sideQuests[1]).getCurrentTask() == 7) {
                ((Player)player).setExp(((Player)player).getExp() +((Quest)sideQuests[1]).getXPReward());
                speechQueue.add("You have gained : " + ((Quest)sideQuests[1]).getXPReward() + " XP");
                bag.find(sideQuests[1].getItemReward());
                speechQueue.add((sideQuests[1].getItemReward()).getName());
              }
            }
            // MAIN QUEST
            if (((Quest)mainQuests).getCurrentTask() > 4 && ((Quest)mainQuests).getCurrentTask() < 7 && !((Quest)mainQuests).getComplete()
                  && ((Quest)mainQuests).getActive()) {
              ((Quest)mainQuests).updateObjective(1);
              if (((Quest)mainQuests).getCurrentTask() == 7) {
                speechQueue.add("Tasks completed!");
                ((Player)player).setExp(((Player)player).getExp() + ((Quest)mainQuests).getXPReward());
                speechQueue.add("You have gained : " + ((Quest)mainQuests).getXPReward() + " XP");
              }
            }
          }
          else if (interactable instanceof PoisonSpider) {
            if (((Quest)sideQuests[1]).getActive() && !((Quest)sideQuests[1]).getComplete() && ((Quest)mainQuests).getActive()) {
              ((Quest)sideQuests[1]).setComplete(((Quest)sideQuests[0]).updateObjective(2));
              if (((Quest)sideQuests[1]).getComplete()) {
                speechQueue.add("Quest completed: " + ((Quest)sideQuests[1]).getName());
                ((Player)player).setExp(((Player)player).getExp() +((Quest)sideQuests[1]).getXPReward());
                speechQueue.add("You have gained : " + ((Quest)sideQuests[1]).getXPReward() + " XP");
                bag.find(sideQuests[1].getItemReward());
                speechQueue.add((sideQuests[1].getItemReward()).getName());
              }
            }
            // MAIN QUEST
            if (((Quest)mainQuests).getCurrentTask() > 4 && ((Quest)mainQuests).getCurrentTask() < 7 && !((Quest)mainQuests).getComplete()
                  && ((Quest)mainQuests).getActive()) {
              ((Quest)mainQuests).updateObjective(2);
              if (((Quest)mainQuests).getCurrentTask() == 7) {
                speechQueue.add("Tasks completed!");
                ((Player)player).setExp(((Player)player).getExp() + ((Quest)mainQuests).getXPReward());
                speechQueue.add("You have gained : " + ((Quest)mainQuests).getXPReward() + " XP");
              }
            }
          }
          //Frost Quest (huntquestC/(2 in index)
          else if (interactable instanceof FrostSnake) {
            if (((Quest)sideQuests[2]).getActive() && !((Quest)sideQuests[2]).getComplete() && ((Quest)sideQuests[2]).getActive()) {
              ((Quest)sideQuests[2]).setComplete(((Quest)sideQuests[2]).updateObjective(1));
              if (((Quest)sideQuests[2]).getComplete()) {
                speechQueue.add("Quest completed: " + ((Quest)sideQuests[2]).getName());
                ((Player)player).setExp(((Player)player).getExp() +((Quest)sideQuests[2]).getXPReward());
                speechQueue.add("You have gained : " + ((Quest)sideQuests[2]).getXPReward() + " XP");
                bag.find(sideQuests[2].getItemReward());
                speechQueue.add((sideQuests[2].getItemReward()).getName());
              }
            }
            // MAIN QUEST
            if (((Quest)mainQuests).getCurrentTask() > 11 && ((Quest)mainQuests).getCurrentTask() < 14 && !((Quest)mainQuests).getComplete()
                  && ((Quest)mainQuests).getActive()) {
              ((Quest)mainQuests).updateObjective(1);
              if (((Quest)mainQuests).getCurrentTask() == 14) {
                speechQueue.add("Tasks completed!");
                ((Player)player).setExp(((Player)player).getExp() + ((Quest)mainQuests).getXPReward());
                speechQueue.add("You have gained : " + ((Quest)mainQuests).getXPReward() + " XP");
              }
            }
          }
          else if (interactable instanceof FrostSpider) {
            if (((Quest)sideQuests[2]).getActive() && !((Quest)sideQuests[2]).getComplete() && ((Quest)sideQuests[2]).getActive()) {
              ((Quest)sideQuests[2]).setComplete(((Quest)sideQuests[2]).updateObjective(2));
              if (((Quest)sideQuests[2]).getComplete()) {
                speechQueue.add("Quest completed: " + ((Quest)sideQuests[2]).getName());
                ((Player)player).setExp(((Player)player).getExp() +((Quest)sideQuests[2]).getXPReward());
                speechQueue.add("You have gained : " + ((Quest)sideQuests[2]).getXPReward() + " XP");
                bag.find(sideQuests[2].getItemReward());
                speechQueue.add((sideQuests[2].getItemReward()).getName());
              }
            }
            // MAIN QUEST
            if (((Quest)mainQuests).getCurrentTask() > 11 && ((Quest)mainQuests).getCurrentTask() < 14 && !((Quest)mainQuests).getComplete()
                  && ((Quest)mainQuests).getActive()) {
              ((Quest)mainQuests).updateObjective(1);
              if (((Quest)mainQuests).getCurrentTask() == 14) {
                speechQueue.add("Tasks completed!");
                ((Player)player).setExp(((Player)player).getExp() + ((Quest)mainQuests).getXPReward());
                speechQueue.add("You have gained : " + ((Quest)mainQuests).getXPReward() + " XP");
              }
            }
          }
          //Fire Quest (huntquestD/(3 in index)
          else if (interactable instanceof FireSnake) {
            if (((Quest)sideQuests[3]).getActive() && !((Quest)sideQuests[3]).getComplete() && ((Quest)sideQuests[3]).getActive()) {
              ((Quest)sideQuests[3]).setComplete(((Quest)sideQuests[3]).updateObjective(1));
              if (((Quest)sideQuests[3]).getComplete()) {
                speechQueue.add("Quest completed: " + ((Quest)sideQuests[3]).getName());
                ((Player)player).setExp(((Player)player).getExp() +((Quest)sideQuests[3]).getXPReward());
                speechQueue.add("You have gained : " + ((Quest)sideQuests[3]).getXPReward() + " XP");
                bag.find(sideQuests[3].getItemReward());
                speechQueue.add((sideQuests[3].getItemReward()).getName());
              }
            }
            // MAIN QUEST
            if (((Quest)mainQuests).getCurrentTask() > 18 && ((Quest)mainQuests).getCurrentTask() < 21 && !((Quest)mainQuests).getComplete() &&
                ((Quest)mainQuests).getActive()) {
              ((Quest)mainQuests).updateObjective(1);
              if (((Quest)mainQuests).getCurrentTask() == 21) {
                speechQueue.add("Tasks completed!");
                ((Player)player).setExp(((Player)player).getExp() + ((Quest)mainQuests).getXPReward());
                speechQueue.add("You have gained : " + ((Quest)mainQuests).getXPReward() + " XP");
              }
            }
          }
          else if (interactable instanceof FireSpider) {
            if (((Quest)sideQuests[3]).getActive() && !((Quest)sideQuests[3]).getComplete() && ((Quest)sideQuests[3]).getActive()) {
              ((Quest)sideQuests[3]).setComplete(((Quest)sideQuests[3]).updateObjective(2));
              if (((Quest)sideQuests[3]).getComplete()) {
                speechQueue.add("Quest completed: " + ((Quest)sideQuests[3]).getName());
                ((Player)player).setExp(((Quest)sideQuests[3]).getXPReward());
                speechQueue.add("You have gained : " + ((Quest)sideQuests[3]).getXPReward() + " XP");
                bag.find(sideQuests[3].getItemReward());
                speechQueue.add((sideQuests[3].getItemReward()).getName());
              }
            }
            // MAIN QUEST
            if (((Quest)mainQuests).getCurrentTask() > 18 && ((Quest)mainQuests).getCurrentTask() < 21 && !((Quest)mainQuests).getComplete()
                  && ((Quest)mainQuests).getActive()) {
              ((Quest)mainQuests).updateObjective(2);
              if (((Quest)mainQuests).getCurrentTask() == 21) {
                speechQueue.add("Tasks completed!");
                ((Player)player).setExp(((Player)player).getExp() + ((Quest)mainQuests).getXPReward());
                speechQueue.add("You have gained : " + ((Quest)mainQuests).getXPReward() + " XP");
              }
            }
          }

          //Boss Quest (HuntQuestE/(4 in index)
          else if (interactable instanceof PoisonBoss) {
            if (((Quest)sideQuests[4]).getActive() && !((Quest)sideQuests[4]).getComplete() && ((Quest)sideQuests[4]).getActive()) {
              ((Quest)sideQuests[4]).setComplete(((Quest)sideQuests[4]).updateObjective(1));
              if (((Quest)sideQuests[4]).getComplete()) {
                speechQueue.add("Quest completed: " + ((Quest)sideQuests[4]).getName());
                ((Player)player).setExp(((Player)player).getExp() +((Quest)sideQuests[4]).getXPReward());
                speechQueue.add("You have gained : " + ((Quest)sideQuests[0]).getXPReward() + " XP");
                bag.find(sideQuests[4].getItemReward());
                speechQueue.add((sideQuests[4].getItemReward()).getName());
              }
            }
            // MAIN QUEST
            if (((Quest)mainQuests).getCurrentTask() == 9 && !((Quest)mainQuests).getComplete() && ((Quest)mainQuests).getActive()) {
              ((Quest)mainQuests).updateObjective(1);
              if (((Quest)mainQuests).getCurrentTask() == 10) {
                speechQueue.add("Task complete!");
                speechQueue.add(((Quest)mainQuests).getTask(9));
                ((Player)player).setExp(((Player)player).getExp() + ((Quest)mainQuests).getXPReward());
                speechQueue.add("You have gained : " + ((Quest)mainQuests).getXPReward() + " XP");
              }
            }
          }
          else if (interactable instanceof FrostBoss) {
            if (((Quest)sideQuests[4]).getActive() && !((Quest)sideQuests[4]).getComplete() && ((Quest)sideQuests[4]).getActive()) {
              ((Quest)sideQuests[4]).setComplete(((Quest)sideQuests[4]).updateObjective(2));
              if (((Quest)sideQuests[4]).getComplete()) {
                speechQueue.add("Quest completed: " + ((Quest)sideQuests[4]).getName());
                ((Player)player).setExp(((Player)player).getExp() +((Quest)sideQuests[4]).getXPReward());
                speechQueue.add("You have gained : " + ((Quest)sideQuests[4]).getXPReward() + " XP");
                bag.find(sideQuests[4].getItemReward());
                speechQueue.add((sideQuests[4].getItemReward()).getName());
              }
            }
            // MAIN QUEST
            if (((Quest)mainQuests).getCurrentTask() == 16 && !((Quest)mainQuests).getComplete() && ((Quest)mainQuests).getActive()) {
              ((Quest)mainQuests).updateObjective(1);
              if (((Quest)mainQuests).getCurrentTask() == 17) {
                speechQueue.add("Task complete!");
                ((Player)player).setExp(((Player)player).getExp() + ((Quest)mainQuests).getXPReward());
                speechQueue.add("You have gained : " + ((Quest)mainQuests).getXPReward() + " XP");
              }
            }
          }
          else if (interactable instanceof FireBoss) {
            if (((Quest)sideQuests[4]).getActive() && !((Quest)sideQuests[4]).getComplete() && ((Quest)sideQuests[4]).getActive()) {
              ((Quest)sideQuests[4]).setComplete(((Quest)sideQuests[4]).updateObjective(3));
              if (((Quest)sideQuests[4]).getComplete()) {
                speechQueue.add("Quest completed: " + ((Quest)sideQuests[4]).getName());
                ((Player)player).setExp(((Player)player).getExp() + ((Quest)sideQuests[4]).getXPReward());
                speechQueue.add("You have gained : " + ((Quest)sideQuests[4]).getXPReward() + " XP");
                bag.find(mainQuests.getItemReward());
                speechQueue.add((sideQuests[4].getItemReward()).getName());
              }
            }
            // MAIN QUEST
            if (((Quest)mainQuests).getCurrentTask() == 24 && !((Quest)mainQuests).getComplete() && ((Quest)mainQuests).getActive()) {
              ((Quest)mainQuests).updateObjective(1);
              if (((Quest)mainQuests).getCurrentTask() == 25) {
                speechQueue.add("Task complete!");
                ((Player)player).setExp(((Player)player).getExp() + ((Quest)mainQuests).getXPReward());
                speechQueue.add("You have gained : " + ((Quest)mainQuests).getXPReward() + " XP");
              }
            }
          }
          else if (interactable instanceof MangatBoss) {
            if (((Quest)mainQuests).getCurrentTask() == 25 && !((Quest)mainQuests).getComplete() && ((Quest)mainQuests).getActive()) {
              ((Quest)mainQuests).updateObjective(1);
              if (((Quest)mainQuests).getCurrentTask() == 26) {
                speechQueue.add("You have slain Mangat!");
                ((Player)player).setExp(((Player)player).getExp() + ((Quest)mainQuests).getXPReward());
                speechQueue.add("You have gained : " + ((Quest)mainQuests).getXPReward() + " XP");
                bag.find(sideQuests[0].getItemReward());
                speechQueue.add("You have gained : something");
              }
            }
          }
        }
      } else if (interactable instanceof Chest){
        if (((Chest)interactable).getChestItems() != null) {
          bag.find(((Chest)interactable).getChestItems());
          System.out.println(bag.getItemName(0));
          System.out.println(bag.getItemName(1));
          System.out.println(bag.getItemName(2));
          System.out.println(bag.getItemName(3));
          ((Chest)interactable).removeItem();
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
              speechQueue.add("I have stuff in this chest, take it!");
              speechQueue.add("Help me defeat these bandits and archers!");
              speechQueue.add(mainQuest.getTask(1));
              speechQueue.add(mainQuest.getTask(2));
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
              speechQueue.add("I'm sure the King has the work for your kind");
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
