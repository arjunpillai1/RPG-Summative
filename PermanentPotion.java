/* class Potion
 * Johann Muth
 * May 24 2018
 * Abstract class of all potions that boost your stats
 */  

abstract class  PermanentPotion extends Potion{
  
  PermanentPotion(String name, int cost){
    super(name, cost);
  }
}