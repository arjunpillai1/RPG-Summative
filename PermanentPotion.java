/* class Potion
 * Johann Muth
 * May 24 2018
 * Abstract class of all potions that boost your stats
 */  

abstract class  PermanentPotion extends Potion{
  /**
   * Constructor for permanent potion
   * @param name, cost
   */
  PermanentPotion(String name, int cost){
    super(name, cost);
  }
}