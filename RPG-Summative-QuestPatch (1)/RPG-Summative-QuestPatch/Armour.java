abstract class Armour extends Item{
  
  int defenseBoost;
  
  double speed;
  
  boolean equipped;
  
  Armour(int cost, int defenseBoost, double speed){
    super(cost, 2);
    
    this.defenseBoost=defenseBoost;
    
    this.speed=speed;
    
    this.equipped=false;
  }
  
  /* getDefenseBoost()
   * gets the value of defense boost
   * @param none
   * @return int value of defense boost
   */
  public int getDefenseBoost(){
    return this.defenseBoost;
  }
  
  public double getSpeed(){
    return this.speed;
  }
  
  public void setSpeed(int value){
    this.speed = value;
  }
  
  public boolean getEquipped(){
    return equipped;
  }
  
  public void equipArmour(){
    equipped=true;
  }
  
  public void unequipArmour(){
    equipped = false;
  }
}