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

class StartingFrame extends JFrame {
   
  JFrame thisFrame;
  static Object[][] world = new Object[106][106];
  
  //Constructor - this runs first
  StartingFrame() { 
    super("Start Screen");
    this.thisFrame = this; //lol  
    
    //configure the window
    this.setSize(500,650);    
    this.setLocationRelativeTo(null); //start the frame in the center of the screen
    //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    this.setResizable (false);
    
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
      new GameFrame(world, ((Player)world[23][21])); //create a new FunkyFrame (another file that extends JFrame)
      
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
      Object initialGround;
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
              world[i][j] = new Bandit(100,1,1,1,1,1,"Bandit", i, j, initialGround);
            }else {
              world[i][j] = new Archer(100,1,1,1,1,1,"Archer", i, j, initialGround);
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
              world[i][j] = new FireSnake(100,1,1,1,1,1,"Fire Snake", i, j, initialGround);
              
            }else {
              world[i][j] = new FireSpider(100,1,1,1,1,1,"Fire Spider", i, j, initialGround);
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
              world[i][j] = new PoisonSnake(100,1,1,1,1,1,"Venom Snake", i, j, initialGround);
            }else {
              world[i][j] = new PoisonSpider(100,1,1,1,1,1,"Venom Spider", i, j, initialGround);
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
              world[i][j] = new FrostSpider(100,1,1,1,1,1,"Frost Spider", i, j, initialGround);
            }else {
              world[i][j] = new FrostSnake(100,1,1,1,1,1,"Frost Snake", i, j, initialGround);
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
          world[i][j] = new Chest();
        }  
      }
      frostEnemyCount += 1;
      noobEnemyCount += 1;
      fireEnemyCount += 1;
      poisonEnemyCount += 1;
    }
    
    
    //Set up Grid Panel
    world[23][21] = new Player(100,100,100,100,100,100, playerName, 23, 21);
    int playX = 23;
    int playY = 21;
    Object[] mainStory = new Object[5];
    mainStory = createStory(mainStory, 1);
    Object[] sideQuests = new Object[5];
    sideQuests = createSide(sideQuests);
    Object[] questLog = new Object[20]; // all quests in here
    
    //start all quests
    ((Quest)mainStory[0]).spawn(world);
    for(int i = 0; i < sideQuests.length; i++) {
      if (sideQuests[i] != null) {
        ((Quest)sideQuests[i]).spawn(world);
      }
    }
    fileIn.close();
  }
  
  public static Object[] createStory(Object[] questline, int quest) {
    if (quest == 1) {
      String[] objectives = {"Talk to the farmer", "Kill 5 Bandits", "Talk to the farmer"};
      Item item = new RustySword();
      questline[0] = new MainQuestA(1, "Awakening", objectives, item);
    }
    else if (quest == 2) {
      String[] objectives = {"Talk to the castle guard", "Kill 5 Poison snakes", "Talk to the King"};
      Item item = new RustySword();
      questline[1] = new MainQuestB(1, "Revelations", objectives, item);
    }
    else if (quest == 3) {
      String[] objectives = {"Speak to the citizen", "Kill the Poison Boss", "Talk"};
      Item item = new RustySword();
      questline[2] = new MainQuestC(1, "A Symbol of Hope", objectives, item);
    }
    else if (quest == 4) {
      String[] objectives = {"Kill 5 Frost Enemies", "Kill the Frost Boss", "Return to town"};
      Item item = new RustySword();
      questline[3] = new MainQuestD(1, "Shivering Foes", objectives, item);
    }
    else if (quest == 5) {
      String[] objectives = {"Kill 2 Fire Enemies", "Kill the Fire Boss", "Talk to the King"};
      Item item = new RustySword();
      questline[4] = new MainQuestE(1, "No Turning Back", objectives, item);
    }
    //questline[2] = new Quest();
    //questline[3] = new Quest();
    return questline;
  }
  public static Object[] createSide(Object[] quests) {
    String[] objectives = new String[1];
    Item item = new RustySword();
    quests[1] = new FetchQuest(1, "The Missing Box", objectives, item);
    objectives[0] = "kill";
    quests[2] = new HuntQuest(1, "Evening the Odds", objectives, item);
    return quests;
  }
  //Main method starts this application
  public static void main(String[] args) throws Exception { 
    Scanner keyInput = new Scanner(System.in);
    System.out.println("enter player name");
    String playerName = keyInput.nextLine();
    new StartingFrame();
    mapInitialize(playerName);
  }
  
}