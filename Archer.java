/* class Archer
 * Aiden Gimpel
 * June 14th, 2018
 * object for the archer enemy
*/  
class Archer extends Normal{
  
  private int newLvl;
  
  Archer(int health, int strength, int intelligence, int defence, int level, int accuracy,
         String name, int posX, int posY , World initialGround){
    super(health, strength, intelligence, defence, level, accuracy, name, posX, posY, initialGround);
    newLvl = ((int)(Math.random()*2)+1);
    setLvl(newLvl);
  }
  
  
  
  /**
* move
* This method accepts a world, x Coord and a y coord as a parameter and moves the archer.
* method returns a void
* @param A  world array that holds data representing the game map
* @param B integer holding the x coordinate of the enemy
* @param C integer holding the y coordinate of the enemy
* @return void
*/
  public void move(){
    //enemy moves in a random way until it sees the player
    //enemy attacks the player when in range
  }
}