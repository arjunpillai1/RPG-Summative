/*
 * [Type.java]
 * A superclass for enemies that have an element associated with them
 * @author Aiden
 * 05/30/2018
 */

abstract class Type extends Enemy{
  
  private String type;
  
  /**
   * Default type constructor
   * @param health, strength, intelligence, defence, level, accuracy, type, name, initial position x and y, initial
   * world spawn
   */
  Type(int health, int strength, int intelligence, int defence, int level, int accuracy, String type,
           String name,  int posX, int posY, World initialGround){
    super(health, strength, intelligence, defence, level, accuracy, name, posX, posY, initialGround);
    this.type=type;
  }
   
}