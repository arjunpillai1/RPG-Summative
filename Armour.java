abstract class Armour extends Item{
  
  int defenseBoost;
  
  double speed;
  
  boolean equipped;
  
  Armour(String name, int cost, int defenseBoost, double speed){
    super(name, cost, 2);
    
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