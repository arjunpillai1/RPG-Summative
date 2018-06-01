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
      new GameFrame(world); //create a new FunkyFrame (another file that extends JFrame)
      
    }
    
  }
  
  public static void mapInitialize() throws Exception {
    File map = new File("map.txt");
    Scanner fileIn = new Scanner(map);
    Random rand = new Random();
    String value = fileIn.nextLine();
    int noobEnemyCount = 10, poisonEnemyCount = 20, frostEnemyCount = 30, fireEnemyCount = 15;
    
    for (int a = 0; a < world.length - 1; a++) { // draws first row of the map to avoid errors
      if (value.substring(a, a + 1).equals("S")) {
        world[0][a] = new Water();
      }
    }
    
    for (int i = 1; i < world.length - 1; i++) { // draws the rest of the map in the array
      value = fileIn.nextLine();
      for (int j = 0; j < world.length - 1; j++) {
        if (value.substring(j, j + 1).equals("S") || (value.substring(j, j + 1).equals("r"))) { 
          world[i][j] = new Water();
        } else if (value.substring(j, j + 1).equals("E")) {
          world[i][j] = new Grass();
          int enemyChance = rand.nextInt(5);
          if ((enemyChance == 1) && (noobEnemyCount > 0)) {
            enemyChance = rand.nextInt(2);
            if (enemyChance == 1) {
              world[i][j] = new Bandit(1,1,1,1,1,1,"Bandit", i, j);
            }else {
              world[i][j] = new Archer(1,1,1,1,1,1,"Archer", i, j);
            }
            noobEnemyCount--;
          }
        } else if (value.substring(j, j + 1).equals("M")) {
          world[i][j] = new FireGrass();
          int enemyChance = rand.nextInt(5);
          if ((enemyChance == 1) && (fireEnemyCount > 0)) {
            enemyChance = rand.nextInt(2);
            if (enemyChance == 1) {
              world[i][j] = new FireSnake(1,1,1,1,1,1,"Fire Snake", i, j);
            }else {
              world[i][j] = new FireSpider(1,1,1,1,1,1,"Fire Spider", i, j);
            }
            fireEnemyCount--;
          }
        } else if (value.substring(j, j + 1).equals("D")) {
          world[i][j] = new PoisonGrass();
          int enemyChance = rand.nextInt(5);
          if ((enemyChance == 1) && (poisonEnemyCount > 0)) {
            enemyChance = rand.nextInt(2);
            if (enemyChance == 1) {
              world[i][j] = new PoisonSnake(1,1,1,1,1,1,"Venom Snake", i, j);
            }else {
              world[i][j] = new PoisonSpider(1,1,1,1,1,1,"Venom Spider", i, j);
            }
            poisonEnemyCount--;
          }
        } else if (value.substring(j, j + 1).equals("I")) {
          world[i][j] = new FrostGrass();
          int enemyChance = rand.nextInt(5);
          if ((enemyChance == 1) && (frostEnemyCount > 0)) {
            enemyChance = rand.nextInt(2);
            if (enemyChance == 1) {
              world[i][j] = new FrostSpider(1,1,1,1,1,1,"Frost Spider", i, j);
            }else {
              world[i][j] = new FrostSnake(1,1,1,1,1,1,"Frost Snake", i, j);
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
    }
    
    
    //Set up Grid Panel
    world[4][4] = new Player(100,100,100,100,100,100, "guy", 4, 4);
    int playX = 4;
    int playY = 4;
    fileIn.close();
  }
  
  
  //Main method starts this application
  public static void main(String[] args) throws Exception { 
    new StartingFrame();
    mapInitialize();
  }
  
}