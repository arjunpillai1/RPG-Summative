/* class FrostSnake
 * Aiden Gimpel
 * June 14th, 2018
 * object for the frost snake enemy
*/
class FrostSnake extends Type{
  
  FrostSnake(int health, int strength, int intelligence, int defence, int level, int accuracy, 
           String name,  int posX, int posY, World initialGround){
    super(health, strength, intelligence, defence, level,
          accuracy,"frost", name, posX, posY, initialGround);
    
    setLvl(((int)(Math.random()*4)+6));//between 6 and 9
    
  }
  
      /**
* attack
* This method accepts a world object and allows the enemy to attack the player
* the method returns a void
* @param A  world array that holds data representing the game map
* @return void
*/
  public void attack(int strength, int intelligence){
    //if enemy hits the player, deal 10% damage to player(Tentative) and 
    //deal a burn effect causing  1 damage over time for 5 hits
    //once it attacks once it will wait 1 second to attack again
  }
  
  
  
}