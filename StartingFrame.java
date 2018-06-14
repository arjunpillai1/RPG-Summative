
/* [StartingFrame.java]
 * Starting menu 
 * @author Albert, Arjun, Guy, Aiden, Johann
 * 06/13/2018
 */



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

import javax.swing.JTextField;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.ImageIcon;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;


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
    
  //****************** An internal class to repond to the button event ******
  /*
   * Each listener requires the code below in order to respond to an event. The name of this
   * class much match the listener that was added to the JComponent. In our case, the button.
   * Note - this is not in the main method, but is still within our class
   *  
   */
  
  static class clickButtonListener implements ActionListener {  //this is the required class definition
    private JFrame nameFrame;
          clickButtonListener(JFrame nameFrame){
      this.nameFrame = nameFrame;
      }
    public void actionPerformed(ActionEvent event)  {     //this is the only method in this class and it will run automatically when the button is activated
    // **** This is where the code to respond to the button event goes
      try{
        String name;
        name = nameField.getText();
        new StartingFrame();
        mapInitialize(name);
        nameFrame.dispose();
    
    // ****
      }catch (Exception E){};
    }
  } // ******* end of action listener

  /*
 * 
 * 
 * 
 */

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

          world[i][j] = new NormalGrass();

          int enemyChance = rand.nextInt(5);
          if ((enemyChance == 1) && (noobEnemyCount > 0) && i > 8 && j > 8) {
            initialGround = world[i][j];
            enemyChance = rand.nextInt(2);
            if (enemyChance == 1) {

              world[i][j] = new FallenKnight(18,3,1,2,3,100,"Fallen Knight", i, j, initialGround);
            }else {
              world[i][j] = new Troll(10,1,3,1,5,95,"Troll", i, j, initialGround);
            }
            noobEnemyCount--;
          }

        } else if (value.substring(j, j + 1).equals("M")) {
          world[i][j] = new PoisonGrass();
          int enemyChance = rand.nextInt(5);
          if ((enemyChance == 1) && (poisonEnemyCount > 0)) {
            initialGround = world[i][j];
            enemyChance = rand.nextInt(2);
            if (enemyChance == 1) {

              world[i][j] = new PoisonSorcerer(12,6,3,6,8,100,"Venom Sorcerer", i, j, initialGround);
            }else {
              world[i][j] = new PoisonSpider(8,6,8,8,7,96,"Venom Spider", i, j, initialGround);
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

              world[i][j] = new FrostSpider(24,14,7,10,13,100,"Frost Spider", i, j, initialGround);
            }else {
              world[i][j] = new FrostSorcerer(16,8,12,1,15,98,"Frost Sorcerer", i, j, initialGround);
            }
            frostEnemyCount--;
          }
        } else if (value.substring(j, j + 1).equals("D")) {
          world[i][j] = new FireGrass();
          int enemyChance = rand.nextInt(5);
          if ((enemyChance == 1) && (fireEnemyCount > 0)) {
            initialGround = world[i][j];
            enemyChance = rand.nextInt(2);
            if (enemyChance == 1) {
              world[i][j] = new FireSorcerer(48,30,20,14,20,100,"Fire Sorcerer", i, j, initialGround);
            }else {
              world[i][j] = new FireSpider(32,20,20,8,20,100,"Fire Spider", i, j, initialGround);
            }
            fireEnemyCount--;
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
          world[i][j] = new Chest();
        } else if (value.substring(j, j + 1).equals("Y")) {
          world[i][j] = new CityGrounds();
        }
      }
      if (i % 2 == 0) {
        frostEnemyCount += 1;
        noobEnemyCount += 1;
        fireEnemyCount += 1;
        poisonEnemyCount += 1;
      }
    }
    //spawn the villagers in
    world[26][68] = new NPC(100, "Marcus", false);
    world[29][46] = new NPC(100, "Amelia", false);
    world[29][47] = new NPC(100, "Romeo", false);
    world[33][36] = new NPC(100, "Tom", false);
    world[33][54] = new NPC(100, "Andy", false);
    world[38][46] = new NPC(100, "Lily", false);
    world[46][35] = new NPC(100, "Eliza", false);
    world[53][68] = new NPC(100, "Jerry", false);
    world[53][69] = new NPC(100, "Thomas", false);
    world[55][70] = new NPC(100, "Bianca", false);
    world[55][72] = new NPC(100, "Bruce", false);
    world[56][67] = new NPC(100, "Aiden", false);
    world[56][76] = new NPC(100, "Arjun", false);
    world[62][79] = new NPC(100, "Guy", false);
    world[68][37] = new NPC(100, "Johann", false);
    world[69][36] = new NPC(100, "Alphonso", false);
    world[71][61] = new NPC(100, "Albert", false);
    world[71][62] = new NPC(100, "Alberto", false);
    world[71][65] = new NPC(100, "Mason", false);
    world[71][70] = new NPC(100, "Jill", false);
    
    
    // initial positions for certain objects
    world[23][21] = new Player(25,1,1,0,1,100, playerName, 23, 21);
    RustySword first = new RustySword();
    world[22][19] = new Chest(first);
    world[8][83] = new PoisonBoss(100,30,10,10,1,10, "Poison Boss", 8, 83, world[8][83]);
    world[91][89] = new FrostBoss(300,30,30,12,1,15, "Frost Boss", 92, 89, world[92][89]);
    world[97][10] = new FireBoss(500,30,60,15,1,20, "Fire Boss", 97, 10, world[97][10]);
    
    //add the main story content
    mainStory = createStory(mainStory);
    //add the side quest content
    sideQuests = createSide(sideQuests);
    
    //start all side quests and first main quest 

    mainStory.spawn(world);
    for(int i = 0; i < sideQuests.length; i++) {
      if (sideQuests[i] != null) {
        ((Quest)sideQuests[i]).spawn(world);
      }
    }
    fileIn.close();
  }

  /*
 * 
 * 
 * 
 */
  public static Quest createStory(Quest questline) { 
    String[] objectives = {"Text to fix indexing","Kill 5 Trolls", "Kill 5 Fallen Knights", "Talk to Bob", "Find The Farmer in the Poison lands", 
      "Kill 5 Spiders", "Kill 5 Sorcerers", "Talk to The Farmer", 
      "Go to the capital and meet The King", "Kill the poisonous creature",
      "Talk to King Tagnam", "Find the Ice Fisherman in the Frost Lands", "Kill 7 Sorcerers", "Kill 7 Spiders",
      "Talk to Fisherman James", "Go back to the capital and speak with King Tagnam", "Kill the Frost Boss",
      "Talk to Tagnam", "Find the Pyromaniac in the Firelands", "Defeat 10 Spiders", "Defeat 10 Sorcerers", "Talk to Vivian",
      "Go talk to Tagnam at the capital", "Kill the Flaming Entity", "Talk to the king", "Meet the king near the abandoned hut",
      "Defeat Mangat", "Speak with the counsellor"};
    KingsCrown kingsCrown = new KingsCrown(69);
    questline = new MainQuestA(100, "A Far Journey", objectives, kingsCrown);

    return questline;
  }
  /*
 * 
 * 
 * 
 */

  public static Quest[] createSide(Quest[] quests) {
    String[] objectivesA = {"Kill 5 Fallen Knights"};
    String[] objectivesB = {"Kill 5 Poison Sorcerers", "Kill 10 Poison Spiders"};
    String[] objectivesC = {"Kill 5 Frost Sorcerers", "Kill 10 Frost Spiders"};
    String[] objectivesD = {"Kill 3 Fire Sorcerers", "Kill 4 Fire Spiders"};
    String[] objectivesE = {"Kill the Poison Boss", "Kill the Frost Boss", "Kill the Fire Boss"};
//    String[] objectivesF = {"Find a Wood Staff", "Give the Wood Staff to John"};
//    String[] objectivesG = {"Find a Defence Potion", "Give the Defence Potion to John"};
//    String[] objectivesH = {"Find a Wood Staff", "Give the Wood Staff to John"};
    Item itemA = new AttackPermanentPotion(10);
    Item itemB = new DefensePermanentPotion(10);
    quests[0] = new HuntQuest(1, "Evening the Odds", objectivesA, itemA);
    quests[1] = new HuntQuestB(1, "Poison Conquerer", objectivesB, itemB);
    quests[2] = new HuntQuestC(1, "Frost Conquerer", objectivesC, itemA);
    quests[3] = new HuntQuestD(1, "Fire Conquerer", objectivesD, itemB);
    quests[4] = new HuntQuestE(1, "World Conquerer", objectivesE, itemA);

//    quests[5] = new FetchQuest(1, "Birthday Gift", objectivesF, item);
//    quests[6] = new FetchQuestB(1, "More Protection", objectivesG, item);
//    quests[7] = new FetchQuestC(1, "The Ultimate Prize", objectivesH, item);

    //objectives[0] = "Find the box";
    //quests[1] = new FetchQuest(1, "The Missing Box", objectives, item);
    
    return quests;
  }
  
  

  /*
 * 
 * 
 * 
 */
  public static void loadGame() throws Exception{
    File map = new File("saveMap.txt");
    File player = new File("savePlayer.txt");
    Scanner fileIn = new Scanner(map);
    Random rand = new Random();
    String value = fileIn.nextLine();
    int noobEnemyCount = 1, poisonEnemyCount = 2, frostEnemyCount = 2, fireEnemyCount = 1;
    int playerX, playerY, playerLevel, health, strength, intel, defence, accuracy;

    int task, mainA;
    Boolean active;
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
          world[i][j] = new NormalGrass();
          int enemyChance = rand.nextInt(5);
          if ((enemyChance == 1) && (noobEnemyCount > 0) && i > 8 && j > 8) {
            initialGround = world[i][j];
            enemyChance = rand.nextInt(2);
            if (enemyChance == 1) {
              world[i][j] = new FallenKnight(18,3,1,2,3,100,"Fallen Knight", i, j, initialGround);
            }else {
              world[i][j] = new Troll(10,1,3,1,4,95,"Troll", i, j, initialGround);
            }
            noobEnemyCount--;
          }
        }
        else if (value.substring(j, j + 1).equals("D")) {
          world[i][j] = new PoisonGrass();
          int enemyChance = rand.nextInt(5);
          if ((enemyChance == 1) && (poisonEnemyCount > 0)) {
            initialGround = world[i][j];
            enemyChance = rand.nextInt(2);
            if (enemyChance == 1) {
              world[i][j] = new PoisonSorcerer(72,9,4,10,8,100,"Venom Sorcerer", i, j, initialGround);
            }else {
              world[i][j] = new PoisonSpider(24,3,9,6,9,96,"Venom Spider", i, j, initialGround);
            }
            poisonEnemyCount--;
          }
        } 
        else if (value.substring(j, j + 1).equals("I")) {
          world[i][j] = new FrostGrass();
          int enemyChance = rand.nextInt(5);
          if ((enemyChance == 1) && (frostEnemyCount > 0)) {
            initialGround = world[i][j];
            enemyChance = rand.nextInt(2);
            if (enemyChance == 1) {
              world[i][j] = new FrostSpider(108,24,22,16,14,100,"Frost Spider", i, j, initialGround);
            }else {
              world[i][j] = new FrostSorcerer(84,16,25,12,14,98,"Frost Sorcerer", i, j, initialGround);
            }
            frostEnemyCount--;
          }
        } 
        else if (value.substring(j, j + 1).equals("M")) {
          world[i][j] = new FireGrass();
          int enemyChance = rand.nextInt(5);
          if ((enemyChance == 1) && (fireEnemyCount > 0)) {
            initialGround = world[i][j];
            enemyChance = rand.nextInt(2);
            if (enemyChance == 1) {
              world[i][j] = new FireSorcerer(324,26,36,30,19,100,"Fire Sorcerer", i, j, initialGround);
            }else {
              world[i][j] = new FireSpider(224,26,36,30,19,100,"Fire Spider", i, j, initialGround);
            }
            fireEnemyCount--;
          }
        }  else if (value.substring(j, j + 1).equals("B")) {
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
          world[i][j] = new Chest();
        }   
      }
      if (i % 2 == 0) {
        frostEnemyCount += 1;
        noobEnemyCount += 1;
        fireEnemyCount += 1;
        poisonEnemyCount += 1;
      }
      
    }
    
    fileIn.close();
    //spawn the villagers in
    world[26][68] = new NPC(100, "Marcus", false);
    world[29][46] = new NPC(100, "Amelia", false);
    world[29][47] = new NPC(100, "Romeo", false);
    world[33][36] = new NPC(100, "Tom", false);
    world[33][54] = new NPC(100, "Andy", false);
    world[38][46] = new NPC(100, "Lily", false);
    world[46][35] = new NPC(100, "Eliza", false);
    world[53][68] = new NPC(100, "Jerry", false);
    world[53][69] = new NPC(100, "Thomas", false);
    world[55][70] = new NPC(100, "Bianca", false);
    world[55][72] = new NPC(100, "Bruce", false);
    world[56][67] = new NPC(100, "Aiden", false);
    world[56][76] = new NPC(100, "Arjun", false);
    world[62][79] = new NPC(100, "Guy", false);
    world[68][37] = new NPC(100, "Johann", false);
    world[69][36] = new NPC(100, "Alphonso", false);
    world[71][61] = new NPC(100, "Steve", false);
    world[71][62] = new NPC(100, "Sharon", false);
    world[71][65] = new NPC(100, "Albert", false);
    world[71][70] = new NPC(100, "Jill", false);
    
    //initial objects
    RustySword first = new RustySword();
    world[22][19] = new Chest(first);
    world[8][83] = new PoisonBoss(100,30,10,10,1,10, "Poison Boss", 8, 83, world[8][83]);
    world[91][89] = new FrostBoss(300,30,30,12,1,15, "Frost Boss", 92, 89, world[92][89] );
    world[97][10] = new FireBoss(500,30,60,15,1,20, "Fire Boss", 97, 10, world[97][10]);
    
    //add the main story content
    mainStory = createStory(mainStory);
    //add the side quest content
    sideQuests = createSide(sideQuests);
    Scanner fileInput = new Scanner(player);

    
    name = fileInput.nextLine();
    playerLevel = Integer.parseInt(fileInput.nextLine());
    intel = Integer.parseInt(fileInput.nextLine());
    strength = Integer.parseInt(fileInput.nextLine());
    defence = Integer.parseInt(fileInput.nextLine());
    health = Integer.parseInt(fileInput.nextLine());
    playerX = Integer.parseInt(fileInput.nextLine());
    playerY = Integer.parseInt(fileInput.nextLine());
    accuracy = Integer.parseInt(fileInput.nextLine());

    active = Boolean.valueOf(fileInput.nextLine());
    task = Integer.parseInt(fileInput.nextLine());
    sideQuests[0].setComplete(active);
    sideQuests[0].setCurrentTask(task);
    active = Boolean.valueOf(fileInput.nextLine());
    task = Integer.parseInt(fileInput.nextLine());
    sideQuests[1].setComplete(active);
    sideQuests[1].setCurrentTask(task);
    active = Boolean.valueOf(fileInput.nextLine());
    task = Integer.parseInt(fileInput.nextLine());
    sideQuests[2].setComplete(active);
    sideQuests[2].setCurrentTask(task);
    active = Boolean.valueOf(fileInput.nextLine());
    task = Integer.parseInt(fileInput.nextLine());
    sideQuests[3].setComplete(active);
    sideQuests[3].setCurrentTask(task);
    active = Boolean.valueOf(fileInput.nextLine());
    task = Integer.parseInt(fileInput.nextLine());
    sideQuests[4].setComplete(active);
    sideQuests[4].setCurrentTask(task);
    task = Integer.parseInt(fileInput.nextLine());
    mainStory.setCurrentTask(task);

    fileInput.close();
    world[playerY][playerX] = new Player(health, strength, intel, defence, playerLevel, accuracy, name, playerX, playerY);
    
  }
    
  
  //Main method starts this application

  
  /* All Jcomponents (buttons, fields, etc) that you want to access in the actionPerformed method
   * must be partially declared as class variable, otherwise they are only visible in the main method
   * the compnents are still initialized and set up in the main method 
   * note - they must be declared as static variables in this part of the program
  */
  static JTextField nameField;
  static JLabel messageLabel;
  static JButton doneButton;
  // ****
  
  
  public static void main(String[] args) {
  
    //Music

    try {
      menuMusicFile = new File("maskoff.wav");
      menuMusicStream = AudioSystem.getAudioInputStream(menuMusicFile);
      info = new DataLine.Info(Clip.class, menuMusicStream.getFormat());
      clip = (Clip) AudioSystem.getLine(info);
      clip.open(menuMusicStream);
    }catch (Exception e){};
    
    clip.start();
      
    
    // **** Create a new Window and set it up
    JFrame myWindow = new JFrame("Character Creation"); //create a new window with a title
    ImageIcon namePic = new ImageIcon ( "name.png" );
    ImageIcon done = new ImageIcon ( "startName.png" );
    
    myWindow.setSize(700,500);  // set the size of my window to 700 by 500 pixels
    myWindow.setResizable(true);  // set my window to allow the user to resize it
    myWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // set the window up to end the program when closed
    

    myWindow.setLayout(new FlowLayout());  // <-- my frame is set up as a grid layout. 0 means unlimited rows    
    // ****
    
    
    // **** Create a some Panels and set their layouts
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new GridLayout(0,1));  // set the layouts to grid 1 coloumn
    // ****
    
    
    // **** Create a button
    doneButton = new JButton(done); 
    doneButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    doneButton.setBackground(new Color(0, 0, 0, 0));
    doneButton.setBorder(BorderFactory.createEmptyBorder());
    doneButton.setFocusPainted(false);
    
    doneButton.addActionListener(new clickButtonListener(myWindow));  // add a listener to the button (makes the button active)
    // ****
    
    // **** Create a label
    messageLabel = new JLabel(namePic);
    messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    // ****
    
    // **** Creat a text field
    nameField = new JTextField(10);  //size 10 in length, not initial text
    // ****
    
    // **** Now adding all the components tot the panels 
    mainPanel.add(messageLabel);
    mainPanel.add(nameField);
    mainPanel.add(doneButton);
    // ****
    
    // **** Add the main panel to the frame, the order is important
    myWindow.add(mainPanel);    
    // ****
    
    // **** finally, display the window on the screen
     myWindow.setVisible(true); // make the window visible on the screen
        
  
  } // *** end of main method
  
  
}