/* class Type
 * Aiden Gimpel
 * June 14th, 2018
 * abstract object for all enemies with typings such as frost snake or poison boss
*/
abstract class Type extends Enemy{
  
  private String type;
  
  //constructor for and enemy with an elemental typing such as fire spider
  Type(int health, int strength, int intelligence, int defence, int level, int accuracy, String type,
           String name,  int posX, int posY, World initialGround){
    super(health, strength, intelligence, defence, level, accuracy, name, posX, posY, initialGround);
    this.type=type;
  }
   
}