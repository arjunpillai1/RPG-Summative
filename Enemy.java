// imports
import java.util.Random;

abstract class Enemy extends CombatCharacter{
  
  private Object futureStep;
  private Object previousStep = new Grass();
  
  Enemy(int health, int strength, int intelligence, int defence, int level, int accuracy, String name, int posX, int posY){
    super(health, strength, intelligence, defence, level, accuracy, name, posX, posY);
  }
  void attack(Object player) {
    int damage = getStr() + getInt();
    ((Character)player).setHealth(((Character)player).getHealth()-damage);
  }
  void move(Object[][] world, int coordX, int coordY) {
    //System.out.println("works" + getX() + getY());
//    System.out.println(coordX + " d" + coordY);
    Random rand = new Random();
    int decision;
    
    for (int i=coordX-1; i < coordX+2; i++) {
      for (int j=coordY-1; j < coordY+2; j++) {
//        System.out.print(world[i][j]);
        if (world[i][j] instanceof Player) {
          attack(world[i][j]);
//          System.out.println("attacks");
          return;
        }
        
      }
//      System.out.println("");
    }
    
    // we can set a limit on their movement based on the original position that we save it as from initialization
    
    // this code is for random movement
    decision = rand.nextInt(5);
    
    /*
     * 
     * 
     * MAKE THE INITIALIZATION THING BOII
     * 
     * 
     */ 
    
    
    if (decision == 1 && coordX >= getX()) { // up
      
      if (world[coordX - 1][coordY] instanceof Floor) { //checks if spot is walkable
        futureStep = world[coordX - 1][coordY];
        world[coordX - 1][coordY] = world[coordX][coordY];
        world[coordX][coordY] = previousStep;
        previousStep = futureStep;
        //System.out.println("works");
      }
    }
    else if (decision == 2 && coordX <= getX()) { // down
      if (world[coordX + 1][coordY] instanceof Floor) { //checks if spot is walkable
        futureStep = world[coordX + 1][coordY];
        world[coordX + 1][coordY] = world[coordX][coordY];
        world[coordX][coordY] = previousStep;
        previousStep = futureStep;
        //System.out.println("works");
      }
    }
    else if (decision == 3 && coordY <= getY()) { // right
      if (world[coordX][coordY+1] instanceof Floor) { //checks if spot is walkable
        futureStep = world[coordX][coordY+1];
        world[coordX][coordY+1] = world[coordX][coordY];
        world[coordX][coordY] = previousStep;
        previousStep = futureStep;
        //System.out.println("works");
      }
    }
    else if (decision == 4 && coordY >= getY()) { // left
      if (world[coordX][coordY-1] instanceof Floor) { //checks if spot is walkable
        futureStep = world[coordX][coordY-1];
        world[coordX][coordY-1] = world[coordX][coordY];
        world[coordX][coordY] = previousStep;
        previousStep = futureStep;
        //System.out.println("works");
      }
    }
  }
  
  void death (Object[][] world, Object object) {
    if (object instanceof FrostSpider || object instanceof FrostSnake) {
      System.out.println(getX() + " " + getY());
      System.out.println(world[getX()][getY()]);
      world[((Enemy)object).getX()][((Enemy)object).getY()] = new FrostGrass();
      System.out.println("dead");
    } else if (object instanceof FireSpider || object instanceof FireSnake) {
      System.out.println(world[getX()][getY()]);
      world[((Enemy)object).getX()][((Enemy)object).getY()] = new FireGrass();
      System.out.println("dead");
    } else if (object instanceof Bandit || object instanceof Archer) {
      System.out.println(((Enemy)object).getX() + " " + ((Enemy)object).getY());
      System.out.println(world[((Enemy)object).getX()][((Enemy)object).getY()]);
      world[((Enemy)object).getX()][((Enemy)object).getY()] = new Grass();
      System.out.println("dead");
    }else if (object instanceof PoisonSpider || object instanceof PoisonSnake) {
      System.out.println(world[getX()][getY()]);
      world[((Enemy)object).getX()][((Enemy)object).getY()] = new PoisonGrass();
      System.out.println("dead");
    }
    return;
  }
  
}