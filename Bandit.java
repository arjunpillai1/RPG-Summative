/* class Bandit
 * Aiden Gimpel
 * June 14th, 2018
 * object for the bandit enemy
*/
class Bandit extends Normal{
  
  private int newLvl;
  
  Bandit(int health, int strength, int intelligence, int defence, int level, int accuracy, 
         String name,  int posX, int posY, World initialGround){
    super(health, strength, intelligence, defence, level, accuracy, name, posX, posY, initialGround);
    newLvl = ((int)(Math.random()*2)+1);
    setLvl(newLvl);
  }
  
      /**
* attack
* This method accepts a world object and allows the enemy to attack the player
* the method returns a void
* @param A  world array that holds data representing the game map
* @return void
*/
  public void attack(int strength, int intelligence){
    //if enemy hits the player, deal 10% damage to player(Tentative)
    //once it attacks it will wait 0.5 second to attack again
  }
  
    /**
* move
* This method accepts a world, x Coord and a y coord as a parameter and moves the enemy.
* method returns a void
* @param A  world array that holds data representing the game map
* @param B integer holding the x coordinate of the enemy
* @param C integer holding the y coordinate of the enemy
* @return void
*/
  public void move(){
    //enemy moves in a random way until it sees the player
    //the enemy chases after the player and attacks them
  }
}