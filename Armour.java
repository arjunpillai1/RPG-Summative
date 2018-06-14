/* [Armour.java]
 * Enemy archer
 * @author Johann
 * 06/14/2018
 */

abstract class Armour extends Item{
  
  int defenseBoost;
  
  double speed;
  
  boolean equipped;
  /**
   * Armour constructor
   * @param name, value of item, defense addition, speed restriction
   * of enemy spawns
   */
  Armour(String name, int cost, int defenseBoost, double speed){
    super(name, cost, 2);
    
    this.defenseBoost=defenseBoost;
    
    this.speed=speed;
    
    this.equipped=false;
  }
  
  /* getDefenseBoost()
   * gets the value of defense boost
   * @return int value of defense boost
   */
  public int getDefenseBoost(){
    return this.defenseBoost;
  }
  /**
   * Getter for private variable speed
   * @return int value of speed
   */
  public double getSpeed(){
    return this.speed;
  }
  /**
   * Setter for the speed variable
   * @return int value of speed
   */ 
  public void setSpeed(int value){
    this.speed = value;
  }
  
  /** 
   * Getter for equipped value
   * @return the boolean value of equipped
   */
  public boolean getEquipped(){
    return equipped;
  }
  
  /** 
   * Setter for the equipped value
   * @param the boolean value to be used
   */
  public void setEquipped(boolean value){
    equipped = value;
  }
  /*
  public void equipArmour(Player user){
    if (user.getEquippedArmour() == false){
    equipped=true;
    user.setDef(user.getDef()+defenseBoost);
    user.setEquippedArmour(false);
    }
  }
  
  public void unequipArmour(Player user){
     if (user.getEquippedArmour() == true){
    equipped=false;
    user.setDef(user.getDef()-defenseBoost);
    user.setEquippedWeapon(false);
    }
  }
  */
}