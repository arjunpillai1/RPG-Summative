import java.util.Scanner;
import java.io.File;
import java.util.Random;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class AsciiMain {


  public static void main(String[] args) throws Exception {


    File map = new File("map.txt");
    Scanner fileIn = new Scanner(map);
    Random rand = new Random();
    String value = fileIn.nextLine();
    Object[][] world = new Object[106][106];
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
    AsciiTest grid = new AsciiTest(world);
    world[4][4] = new Player (100,100,100,100,100,100, "guy");
    int playX = 4;
    int playY = 4;
    Object[] mainStory = new Object[5];\
    mainStory = createStory(mainStory);
    Object[] sideQuests = new Object[5];
    sideQuests = createSide(sideQuests);
    Object[] questLog = new Object[10]; // all quests in here
    int rand;
    fileIn.close();
    do {
      grid.refresh();

      for (int m = 0; m < world.length; m++) {
        for (int n = 0; n < world.length; n++) {
          if (world[m][n] instanceof Player) {
            rand = ((int)(Math.random() * 4)) + 1;
            ((Player)world[m][n]).move(world, m, n, rand);
          }
          // this is for when enemies are in vision range

          if (world[m][n] instanceof Enemy) {
            int radX = ((Enemy)world[m][n]).getX();
            int radY = ((Enemy)world[m][n]).getY();
            Object[][] moveRadius = new Object[5][5];
            for (int x = radX - 2; l <= radX + 2; x++) {
              for (int y = radY - 2; y <= radY + 2; y++) {
                moveRadius[x%5][y%5] = world[x][y];
              }
            }
            //((Enemy)world[m][n]).move(moveRadius);
          }
        }
      }

      grid.refresh();


    } while (true);

    public Quest[] createStory(Object[] questline) {
      questline[1] = new Quest();
      questline[2] = new Quest();
      questline[3] = new Quest();
    }
    public Quest[] createSide(Object[] quests) {
      quests[1] = new FetchQuest;
      quests[2] = new HuntQuest;
    }
  }
}
