/* class Potion
 * Johann Muth
 * May 24 2018
 * Abstract class of potions that boost your attack
*/  

class DefensePotion extends TempPotion{
  
  private int defenseBoost =(int)(Math.random()*5)+10;
  
  DefensePotion(int cost, int timer){
  super("Defense Potion", cost, timer);
  }
  
  public int getDefenseBoost(){
    return defenseBoost;
  }
}