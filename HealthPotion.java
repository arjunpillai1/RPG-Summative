/* class HealthPotion
 * Johann Muth
 * May 24 2018
 * Abstract class of potions that boost your attack
*/  

abstract class HealthPotion extends Potion{
  
  private int healthBoost;
  
  HealthPotion(int cost, int timer, int healthBoost){
  super("Health Potion", cost, timer);
  this.healthBoost=healthBoost;
  }
  
  public int gethealthBoost(){
    return healthBoost;
  }
  
  public void use(Player user){
    user.setHealth( (user.getHealth()) + healthBoost);
    setUsed(true);
  }
}