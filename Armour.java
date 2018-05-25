abstract class Armour extends Item{
  
  int defenseBoost;
  
  double speed;
  
  Armour(int cost, int defenseBoost, double speed){
    super(cost);
    
    this.defenseBoost=defenseBoost;
    
    this.speed=speed;
    
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
}