/** 
 * this template can be used for a start menu
 * for your final project
 **/


//Imports
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingUtilities;
import java.io.File;
import java.util.Scanner;
import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

class StartingFrame extends JFrame { 
  
  JFrame thisFrame;
  static World[][] world = new World[106][106];
  static Quest[] sideQuests = new Quest[8];
  static Quest mainStory;
  
  //Music variables
  static File menuMusicFile ;
  static AudioInputStream menuMusicStream ;
  static DataLine.Info info;
  static Clip clip ;
    
  //Constructor - this runs first
   StartingFrame() { 
    super("Start Screen");
    this.thisFrame = this; //lol  
    
    //configure the window
    this.setSize(500,650);
    
    this.setLocationRelativeTo(null); //start the frame in the center of the screen
    //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    this.setResizable (false);
    this.setUndecorated(true);
    
    //Create a Panel for stuff
    JPanel mainPanel = new JPanel();
    ImageIcon newGame = new ImageIcon( "New Game.png" );
    ImageIcon loadGame = new ImageIcon ( "LoadGame.png" );
    ImageIcon exitGame = new ImageIcon ( "ExitGame.png" );
    ImageIcon welcome = new ImageIcon ( "Medieval Fantasy.png" );
    ImageIcon background = new ImageIcon ( "backgorund.png" );
 //JPanel startPanel = new JPanel();
    BoxLayout mainLayout = new BoxLayout(mainPanel,BoxLayout.Y_AXIS);
    mainPanel.setLayout(mainLayout);
    mainPanel.setBackground(Color.WHITE);
    
    //Create a JButton for the centerPanel
    JButton startButton = new JButton(newGame);
    startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    startButton.setBackground(new Color(0, 0, 0, 0));
    startButton.setBorder(BorderFactory.createEmptyBorder());
    startButton.setFocusPainted(false);
    JButton loadButton = new JButton(loadGame);
    loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);    
    loadButton.setBackground(new Color(0, 0, 0, 0));
    loadButton.setBorder(BorderFactory.createEmptyBorder());
    loadButton.setFocusPainted(false);
    JButton exitButton = new JButton(exitGame);
    exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    exitButton.setBackground(new Color(0, 0, 0, 0));
    exitButton.setBorder(BorderFactory.createEmptyBorder());
    exitButton.setFocusPainted(false);
    startButton.addActionListener(new StartButtonListener());
    exitButton.addActionListener(new ExitButtonListener());
    
    //Create a JButton for the centerPanel
    JLabel startLabel = new JLabel(welcome);
    startLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    //Add all panels to the mainPanel according to border layout
    mainPanel.add(startLabel);
    mainPanel.add(startButton);
    mainPanel.add(loadButton);
    mainPanel.add(exitButton);
    
    
    //add the main panel to the frame
    this.add(mainPanel);
    
    //Start the app
    this.setVisible(true);
  }
  
  //This is an inner class that is used to detect a button press
  class StartButtonListener implements ActionListener {  //this is the required class definition
    public void actionPerformed(ActionEvent event) {  
      System.out.println("Starting new Game");
      thisFrame.dispose();
      clip.close();
      new GameFrame(world, sideQuests, mainStory, ((Player)world[23][21]));
      
    }
    
  }
  class LoadButtonListener implements ActionListener {  //this is the required class definition
    public void actionPerformed(ActionEvent event) {  
      System.out.println("Loading previous save");
      thisFrame.dispose();
      clip.close();
      new GameFrame(world, sideQuests, mainStory, ((Player)world[23][21]));
    }
  }
  class ExitButtonListener implements ActionListener {  //this is the required class definition
    public void actionPerformed(ActionEvent event) {  
      
      System.exit(0);
    }
  }
    
  public static void mapInitialize(String playerName) throws Exception {
    File map = new File("map.txt");
    Scanner fileIn = new Scanner(map);
    Random rand = new Random();
    String value = fileIn.nextLine();
    int noobEnemyCount = 1, poisonEnemyCount = 2, frostEnemyCount = 2, fireEnemyCount = 1;
    
    for (int a = 0; a < world.length - 1; a++) { // draws first row of the map to avoid errors
      if (value.substring(a, a + 1).equals("S")) {
        world[0][a] = new Water();
      }
    }
    
    for (int i = 1; i < world.length - 1; i++) { // draws the rest of the map in the array
      value = fileIn.nextLine();
      World initialGround;
      for (int j = 0; j < world.length - 1; j++) {
        if (value.substring(j, j + 1).equals("S") || (value.substring(j, j + 1).equals("r"))) {
          world[i][j] = new Water();
        } else if (value.substring(j, j + 1).equals("E")) {
          world[i][j] = new Grass();
          int enemyChance = rand.nextInt(5);
          if ((enemyChance == 1) && (noobEnemyCount > 0) && i > 8 && j > 8) {
            initialGround = world[i][j];
            enemyChance = rand.nextInt(2);
            if (enemyChance == 1) {
              world[i][j] = new Bandit(1,1,1,1,1,1,"Bandit", i, j, initialGround);
            }else {
              world[i][j] = new Archer(1,1,1,1,1,1,"Archer", i, j, initialGround);
            }
            noobEnemyCount--;
          }
        } else if (value.substring(j, j + 1).equals("D")) {
          world[i][j] = new FireGrass();
          int enemyChance = rand.nextInt(5);
          if ((enemyChance == 1) && (fireEnemyCount > 0)) {
            initialGround = world[i][j];
            enemyChance = rand.nextInt(2);
            if (enemyChance == 1) {
              world[i][j] = new FireSnake(1,1,1,1,1,1,"Fire Snake", i, j, initialGround);
            }else {
              world[i][j] = new FireSpider(1,1,1,1,1,1,"Fire Spider", i, j, initialGround);
            }
            fireEnemyCount--;
          }
        } else if (value.substring(j, j + 1).equals("M")) {
          world[i][j] = new PoisonGrass();
          int enemyChance = rand.nextInt(5);
          if ((enemyChance == 1) && (poisonEnemyCount > 0)) {
            initialGround = world[i][j];
            enemyChance = rand.nextInt(2);
            if (enemyChance == 1) {
              world[i][j] = new PoisonSnake(1,1,1,1,1,1,"Venom Snake", i, j, initialGround);
            }else {
              world[i][j] = new PoisonSpider(1,1,1,1,1,1,"Venom Spider", i, j, initialGround);
            }
            poisonEnemyCount--;
          }
        } else if (value.substring(j, j + 1).equals("I")) {
          world[i][j] = new FrostGrass();
          int enemyChance = rand.nextInt(5);
          if ((enemyChance == 1) && (frostEnemyCount > 0)) {
            initialGround = world[i][j];
            enemyChance = rand.nextInt(2);
            if (enemyChance == 1) {
              world[i][j] = new FrostSpider(1,1,1,1,1,1,"Frost Spider", i, j, initialGround);
            }else {
              world[i][j] = new FrostSnake(1,1,1,1,1,1,"Frost Snake", i, j, initialGround);
            }
            frostEnemyCount--;
          }
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
          Item chestItems[] = new Item[1];
          world[i][j] = new Chest(chestItems);
        }  
      }
      frostEnemyCount += 1;
      noobEnemyCount += 1;
      fireEnemyCount += 1;
      poisonEnemyCount += 1;
    }
    
    
    //Set up Grid Panel
    world[23][21] = new Player(100,100,100,100,100,100, playerName, 23, 21);
    mainStory = createStory(mainStory);
    
    sideQuests = createSide(sideQuests);
     // all quests in here
    
    //start all side quests and first main quest \
    mainStory.spawn(world);
    for(int i = 0; i < sideQuests.length; i++) {
      if (sideQuests[i] != null) {
        ((Quest)sideQuests[i]).spawn(world);
      }
    }
    fileIn.close();
  }
  
  public static Quest createStory(Quest questline) { 
    String[] objectives = {"Filler text","Kill 5 Archers", "Kill 5 Bandits", "Talk to Bob", "Find The Farmer in the Poison lands", 
      "Kill 5 Spiders", "Kill 5 Snakes", "Talk to The Farmer", 
      "Go to the capital and meet The King", "Kill the poisonous creature",
      "Talk to King Tagnam", "Find the Ice Fisherman in the Frost Lands", "Kill 7 Snakes", "Kill 7 Spiders",
      "Talk to Fisherman James", "Go back to the capital and speak with King Tagnam", "Kill the Frost Boss",
      "Talk to Tagnam", "Find the Pyromaniac in the Firelands", "Defeat 10 Spiders", "Defeat 10 Snakes", "Talk to Vivian",
      "Go talk to Tagnam at the capital", "Kill the Flaming Entity", "Talk to the king", "Meet the king near the abandoned hut",
      "Defeat Mangat", "Speak with the counsellor"};
    KingsCrown kingsCrown = new KingsCrown(69);
    questline = new MainQuestA(100, "Awakening", objectives, kingsCrown);

    return questline;
  }
  public static Quest[] createSide(Quest[] quests) {
    String[] objectivesA = {"Kill 5 Bandits"};
    String[] objectivesB = {"Kill 5 Poison Snakes", "Kill 10 Poison Spiders"};
    String[] objectivesC = {"Kill 5 Frost Snakes", "Kill 10 Frost Spiders"};
    String[] objectivesD = {"Kill 3 Fire Snakes", "Kill 4 Fire Spiders"};
    String[] objectivesE = {"Kill the Poison Boss", "Kill the Frost Boss", "Kill the Fire Boss"};
    String[] objectivesF = {"Find a Wood Staff", "Give the Wood Staff to John"};
    String[] objectivesG = {"Find a Defence Potion", "Give the Defence Potion to John"};
    String[] objectivesH = {"Find a Wood Staff", "Give the Wood Staff to John"};
    Item item = new RustySword();
    quests[0] = new HuntQuest(1, "Evening the Odds", objectivesA, item);
    quests[1] = new HuntQuestB(1, "Poison Conquerer", objectivesB, item);
    quests[2] = new HuntQuestC(1, "Frost Conquerer", objectivesC, item);
    quests[3] = new HuntQuestD(1, "Fire Conquerer", objectivesD, item);
    quests[4] = new HuntQuestE(1, "World Conquerer", objectivesE, item);
    quests[5] = new FetchQuest(1, "Birthday Gift", objectivesF, item);
    quests[6] = new FetchQuestB(1, "More Protection", objectivesG, item);
    quests[7] = new FetchQuestC(1, "The Ultimate Prize", objectivesH, item);
    //objectives[0] = "Find the box";
    //quests[1] = new FetchQuest(1, "The Missing Box", objectives, item);
    
    return quests;
  }
  
  
  
  public static void loadGame() throws Exception{
    File map = new File("saveMap.txt");
    File player = new File("savePlayer.txt");
    Scanner fileIn = new Scanner(map);
    Random rand = new Random();
    String value = fileIn.nextLine();
    int noobEnemyCount = 1, poisonEnemyCount = 2, frostEnemyCount = 2, fireEnemyCount = 1;
    int playerX, playerY, playerLevel, health, strength, intel, defence, accuracy;
    String name;
    
    for (int a = 0; a < world.length - 1; a++) { // draws first row of the map to avoid errors
      if (value.substring(a, a + 1).equals("S")) {
        world[0][a] = new Water();
      }
    }
    
    for (int i = 1; i < world.length - 1; i++) { // draws the rest of the map in the array
      value = fileIn.nextLine();
      World initialGround;
      for (int j = 0; j < world.length - 1; j++) {
        if (value.substring(j, j + 1).equals("S") || (value.substring(j, j + 1).equals("r"))) {
          world[i][j] = new Water();
        } else if (value.substring(j, j + 1).equals("E")) {
          world[i][j] = new Grass();
          int enemyChance = rand.nextInt(5);
          if ((enemyChance == 1) && (noobEnemyCount > 0) && i > 8 && j > 8) {
            initialGround = world[i][j];
            enemyChance = rand.nextInt(2);
            if (enemyChance == 1) {
              world[i][j] = new Bandit(1,1,1,1,1,1,"Bandit", i, j, initialGround);
            }else {
              world[i][j] = new Archer(1,1,1,1,1,1,"Archer", i, j, initialGround);
            }
            noobEnemyCount--;
          }
        } else if (value.substring(j, j + 1).equals("M")) {
          world[i][j] = new FireGrass();
          int enemyChance = rand.nextInt(5);
          if ((enemyChance == 1) && (fireEnemyCount > 0)) {
            initialGround = world[i][j];
            enemyChance = rand.nextInt(2);
            if (enemyChance == 1) {
              world[i][j] = new FireSnake(1,1,1,1,1,1,"Fire Snake", i, j, initialGround);
            }else {
              world[i][j] = new FireSpider(1,1,1,1,1,1,"Fire Spider", i, j, initialGround);
            }
            fireEnemyCount--;
          }
        } else if (value.substring(j, j + 1).equals("D")) {
          world[i][j] = new PoisonGrass();
          int enemyChance = rand.nextInt(5);
          if ((enemyChance == 1) && (poisonEnemyCount > 0)) {
            initialGround = world[i][j];
            enemyChance = rand.nextInt(2);
            if (enemyChance == 1) {
              world[i][j] = new PoisonSnake(1,1,1,1,1,1,"Venom Snake", i, j, initialGround);
            }else {
              world[i][j] = new PoisonSpider(1,1,1,1,1,1,"Venom Spider", i, j, initialGround);
            }
            poisonEnemyCount--;
          }
        } else if (value.substring(j, j + 1).equals("I")) {
          world[i][j] = new FrostGrass();
          int enemyChance = rand.nextInt(5);
          if ((enemyChance == 1) && (frostEnemyCount > 0)) {
            initialGround = world[i][j];
            enemyChance = rand.nextInt(2);
            if (enemyChance == 1) {
              world[i][j] = new FrostSpider(1,1,1,1,1,1,"Frost Spider", i, j, initialGround);
            }else {
              world[i][j] = new FrostSnake(1,1,1,1,1,1,"Frost Snake", i, j, initialGround);
            }
            frostEnemyCount--;
          }
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
          Item chestItems[] = new Item[1];
          world[i][j] = new Chest(chestItems);
        }  
      }
//      if (i % 2 == 0) {
//        frostEnemyCount += 1;
//        noobEnemyCount += 1;
//        fireEnemyCount += 1;
//        poisonEnemyCount += 1;
//      }
    }
    
    fileIn.close();
    
    
    Scanner fileInput = new Scanner(player);
   // playerX, playerY, playerLevel, health, strength, intel, name, defence
   //  Player(int health, int strength, int intelligence, int defence, int level, int accuracy, String name, int posX, int posY)
    
    name = fileInput.nextLine();
    playerLevel = Integer.parseInt(fileInput.nextLine());
    intel = Integer.parseInt(fileInput.nextLine());
    strength = Integer.parseInt(fileInput.nextLine());
    defence = Integer.parseInt(fileInput.nextLine());
    health = Integer.parseInt(fileInput.nextLine());
    playerX = Integer.parseInt(fileInput.nextLine());
    playerY = Integer.parseInt(fileInput.nextLine());
    accuracy = Integer.parseInt(fileInput.nextLine());
    fileInput.close();
    world[playerY][playerX] = new Player(health, strength, intel, defence, playerLevel, accuracy, name, playerX, playerY);
    
  }
  //Main method starts this application
  public static void main(String[] args) throws Exception { 
    //Music initalisation
    try {
      menuMusicFile = new File("maskoff.wav");
      menuMusicStream = AudioSystem.getAudioInputStream(menuMusicFile);
      info = new DataLine.Info(Clip.class, menuMusicStream.getFormat());
      clip = (Clip) AudioSystem.getLine(info);
    }catch (Exception e) {};
    clip.open(menuMusicStream);
    clip.start();
    
    Scanner keyInput = new Scanner(System.in);
    System.out.println("enter player name");
    String playerName = keyInput.nextLine();
    keyInput.close();
    new StartingFrame();
    mapInitialize(playerName);
    //saveGame(world);
  }
  
}