/* class FireBoss
 * Aiden Gimpel
 * June 14th, 2018
 * object for the Fire typed boss
*/
class FireBoss extends Type{
    
  FireBoss(int health, int strength, int intelligence, int defence, int level, int accuracy, 
           String name,  int posX, int posY, World initialGround){
    super(health, strength, intelligence, defence, level, accuracy,"fire", "The Volcano", posX, posY
         , initialGround);
  }
  
  
  public void attack(int strength, int intelligence){
    //if enemy hits the player, deal 25% damage to player(Tentative) and 
    //deal a burn effect causing 2 damage over time for 5 hits
    //once it attacks once it will wait 2 second to attack again
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
    //The Volcano moves towards the enemy slowly
    //if he is close to the player, he will melee attack
    //if he is farther from the player, he will used a ranged attack
    //only ranged attack applies burn effect
  }
}

