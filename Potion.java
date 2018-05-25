/* class Potion
 * Johann Muth
 * May 24 2018
 * Abstract class of all potions that boost your stats
*/  

abstract class Potion extends Consumable{
  int timer;
  
  Potion(int cost, int timer){
  super(cost);
  this.timer = timer;
  }
  
  abstract public void use();
  
  public int getTimer(){
    return timer;
  }
  
  public void setTimer(int value){
    timer=value;
  }
}