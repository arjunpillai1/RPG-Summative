/**
 * Player Class
 * Guy and Arjun
 * May 24 2018
 */

class Player extends CombatCharacter {
  private int exp;

  Player(int health, int strength, int intelligence, int defence, int level, int accuracy, String name) {
    super(health, strength, intelligence, defence, level, accuracy, name);
  }

  public void attack(Object enemy) {

  }

  public void move() {

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



