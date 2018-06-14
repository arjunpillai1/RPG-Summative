abstract class CombatCharacter extends Character {
  
  private int strength;
  private int intelligence;
  private int defence;
  private int level;
  private int accuracy;

  private int posX, posY;
  
  
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
* attack
* This method accepts a world object and allows the enemy to attack the player
* and the player attack the enemy, the method returns a void
* @param A  world array that holds data representing the game map
* @return void
*/
  public void attack(CombatCharacter enemy) {
    
  }

  public int getStr(){
    return strength;
  }
  
  
  public void setStr(int newStr){
    this.strength = newStr;
  }
  
  public int getInt(){
    return intelligence;
  }
  
  
  public void setInt(int newInt){
    this.intelligence = newInt;
  }
  
  
  public int getDef(){
    return defence;
  }
  
  
  public void setDef(int newDef){
    this.defence = newDef;
  }
  
  
  public int getLvl(){
    return level;
  }
  
  
  public void setLvl(int newLvl){
    this.level = newLvl;
  }
  
  
  public int getAccuracy(){
    return accuracy;
  }
  
  
  public void setAccuracy(int newAccuracy){
    this.accuracy = newAccuracy;
  }
  
  public int getX(){
    return posX;
  }
  
  
  public void setX(int posX){
    this.posX = posX;
  }
  
  public int getY(){
    return posY;
  }
  
  
  public void setY(int posY){
    this.posY = posY;
  }
  
  
  
}
