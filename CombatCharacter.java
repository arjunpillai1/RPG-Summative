/* [CombatCharacter.java]
 * Combat character class
 * @author Aiden
 * 05/30/2018
 */

abstract class CombatCharacter extends Character {
  
  private int strength;
  private int intelligence;
  private int defence;
  private int level;
  private int accuracy;
  private int posX, posY;
  
  /**
   * Combat character constructor
   * @param health, strength, intelligence, defence, level, accuracy, name, initial position x and y
   */
  CombatCharacter (int health, int strength, int intelligence, int defence, int level, int accuracy, String name, int posX, int posY) {

    super (health, name);
    this.strength = strength;
    this.intelligence = intelligence;
    this.defence = defence;
    this.level = level;
    this.accuracy = accuracy;
    this.posX = posX;
    this.posY = posY;
  }

  /**
   * Attack method for enemy
   * @param the combat character to attack
   */
  public void attack(CombatCharacter enemy) {
    
  }
  
  /**
   * Gets the strength of the character
   * @return the int value of strength
   */
  public int getStr(){
    return strength;
  }
  
  /**
   * Sets the strength
   * @param the int value for strength to be set
   */
  public void setStr(int newStr){
    this.strength = newStr;
  }
  
  /**
   * Gets the intelligence of the character
   * @return int value of intelligence
   */
  public int getInt(){
    return intelligence;
  }
  
  /**
   * Sets the value of intelligence of the character
   * @param the int value of intelligence
   */
  public void setInt(int newInt){
    this.intelligence = newInt;
  }
  
  /**
   * Gets the defense value of character
   * @return the defense value
   */
  public int getDef(){
    return defence;
  }
  
  /**
   * Sets the defense value of character
   * @return the defense of the character
   */
  public void setDef(int newDef){
    this.defence = newDef;
  }
  
  /**
   * Gets the level of the character
   * @return the int level of the character
   */
  public int getLvl(){
    return level;
  }
  
  /**
   * Sets the level of the character
   * @param the level to be set
   */
  public void setLvl(int newLvl){
    this.level = newLvl;
  }
  
  /**
   * Gets the accuracy of the player
   * @return the int value of accuracy
   */
  public int getAccuracy(){
    return accuracy;
  }
  
  /**
   * Sets the accuracy of the player
   * @param the int value of accuracy
   */
  public void setAccuracy(int newAccuracy){
    this.accuracy = newAccuracy;
  }
  /**
   * Gets the x position of the character, initial spawn position if enemy, current position if player
   * @return the x value of the character
   */
  public int getX(){
    return posX;
  }
  
  /**
   * Sets the x position of the character, initial spawn position if enemy, current position if player
   * @param the x value of the character
   */
  public void setX(int posX){
    this.posX = posX;
  }
  /**
   * Gets the y position of the character, initial spawn position if enemy, current position if player
   * @return the y value of the character
   */
  public int getY(){
    return posY;
  }
  
  /**
   * Sets the y position of the character, initial spawn position if enemy, current position if player
   * @return the y value of the character
   */
  public void setY(int posY){
    this.posY = posY;
  }
  
  
  
}
