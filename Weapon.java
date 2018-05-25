/* class Weapon
 * Johann Muth
 * May 22 2018
 * Abstract class of all weapons to be used in the game (eg. staff, swords etc)
 */ 

abstract class Weapon extends Item{
  
  int attackBoost;
  
  double speed;
  
  int range;
  
  Weapon(int cost, int attackBoost, double speed, int range){
    super(cost);
    
    this.attackBoost=attackBoost;
    
    this.speed=speed;
    
    this.range=range;
  }
  
  /* getAttackBoost()
   * gets the value of attack boost
   * @param none
   * @return int value of attack boost
   */
  public int getAttackBoost(){
    return this.attackBoost;
  }
  
  public double getSpeed(){
    return this.speed;
  }
  
  public void setSpeed(int value){
    this.speed = value;
  }
  
  public int getRange(){
    return this.range;
  }
  
  public void setRange(int value){
    this.range=value;
  }
}