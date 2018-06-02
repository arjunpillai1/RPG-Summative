/**
 * Player Class
 * Guy and Arjun
 * May 24 2018
 */

import java.util.Random;

class Player extends CombatCharacter {
  private int exp;
  private Object futureStep;
  private Object previousStep = new Grass();
  Random rand = new Random();
  
  
  Player(int health, int strength, int intelligence, int defence, int level, int accuracy, String name, int posX, int posY) {
    super(health, strength, intelligence, defence, level, accuracy, name, posX, posY);
  }

  


  public void attack(Object enemy) {
    int critPoint = rand.nextInt(9);                             
    int damage = getStr() + getInt() + critPoint;
    System.out.println(damage);
    ((Enemy)enemy).setHealth(((Enemy)enemy).getHealth()-damage);


  }
  
  public void move(Object[][] world, int value) {
    if (value == 1) {
      if (world[getX() - 1][getY()] instanceof Grass || world[getX() - 1][getY()] instanceof Floor) { //checks if spot is walkable
        futureStep = world[getX() - 1][getY()];
        world[getX() - 1][getY()] = world[getX()][getY()];
        world[getX()][getY()] = previousStep;
        previousStep = futureStep;
      }
    } 
    else if (value == 4) {
      if (world[getX()][getY() + 1] instanceof Grass || world[getX()][getY() + 1] instanceof Floor) {
        futureStep = world[getX()][getY() + 1];
        world[getX()][getY() + 1] = world[getX()][getY()];
        world[getX()][getY()] = previousStep;
        previousStep = futureStep;
      }
    }  
    else if (value == 2) {
      if (world[getX() + 1][getY()] instanceof Grass || world[getX() + 1][getY()] instanceof Floor) {
        futureStep = world[getX() + 1][getY()];
        world[getX() + 1][getY()] = world[getX()][getY()];
        world[getX()][getY()] = previousStep;
        previousStep = futureStep;
      }
    }
    else if (value == 3) {
      if (world[getX()][getY() - 1] instanceof Grass || world[getX()][getY() - 1] instanceof Floor) {
        futureStep = world[getX()][getY() - 1];
        world[getX()][getY() - 1] = world[getX()][getY()];
        world[getX()][getY()] = previousStep;
        previousStep = futureStep;
      }
    }
  }
  
  public int getExp(){
    return exp;
  }
  
  public void setExp(int newExp){
    this.exp = newExp;
  }
  
  public void interact (Object interObj) {
//    if (interObj instanceof Chest) {
//    
//    } else if (interObj instanceof NPC) {
//      
//    }
  }
  
}


