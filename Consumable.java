/* class Consumable
 * Johann Muth
 * May 22 2018
 * Abstract class of all items to be found in inventory that you can only use once such as potions
*/  
abstract class Consumable extends Item{
  private boolean used;
  
  Consumable(String name, int cost){
  super(name, cost, 3);
  
  used = false;
  }
  

}

