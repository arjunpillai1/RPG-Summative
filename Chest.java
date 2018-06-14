/*
 * [Chest.java]
 * Chests related to environment and inventory
 * @author Guy
 * 06/12/2018
 */
import java.util.Random;

class Chest extends World{
  Random rand = new Random();
  private Item chestItems;
  /**
   * Default constructor for chest
   * @author Guy
   */
  Chest(){ 
    //this.chestItems = chestItems;
    int chestOption = rand.nextInt(2);
    // random item
    if (chestOption == 0) {
      this.chestItems = new AttackPermanentPotion(1);
    } else if (chestOption == 1) {
      this.chestItems = new DefensePermanentPotion(1);
    }
  }
  /**
   * Constructor for the initial chest item
   * @author Guy
   */
  Chest(Item chestItem) {
    this.chestItems = chestItem;
  }
  /**
   * Returns the item stored in chest
   * @return item variable
   */
  public Item getChestItems() {
    return chestItems;
  }
  /**
   * Removes items associated in the class
   */
  public void removeItem() {
    this.chestItems = null;
  }
}