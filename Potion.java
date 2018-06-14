/*
 * [Potion.java]
 * Potion class for inventory
 * @author Johann
 * 06/01/2018
 */

abstract class Potion extends Consumable{


  private int timer;
  
  /**
   * Potion constructor
   * @param name, value, duration
   */
  Potion(String name, int cost, int timer){
    super(name, cost);
    this.timer = timer;
  }
  /**
   * Potion constructor
   * @param name, value, duration
   */
  Potion(String name, int cost) {
    super(name,cost);
  }


}
