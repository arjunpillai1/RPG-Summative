/* class Potion
 * Johann Muth
 * May 24 2018
 * Abstract class of all potions that boost your stats
*/  

abstract class Potion extends Consumable{
  private int timer;
  
  Potion(String name, int cost, int timer){
  super(name, cost);
  this.timer = timer;
  }
  public int getTimer(){
    return timer;
  }
  
  public void setTimer(int value){
    timer=value;
  }
  
}