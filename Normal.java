/* class Normal
 * Aiden Gimpel
 * June 14th, 2018
 * abstract object for the normal typed enemies such as archer and bandit
*/
abstract class Normal extends Enemy{
  
  //constructor to make a normal enemy (Archer/Bandit)
    Normal(int health, int strength, int intelligence, int defence, int level, int accuracy, 
           String name,  int posX, int posY, World initialGround){
    super(health, strength, intelligence, defence, level, accuracy, name, posX, posY, initialGround);
  }
}