/*
 * 
 * 
 */
import java.util.Random;

class Chest extends World{
  Random rand = new Random();
  private Item chestItems;
  Chest(){ 
    //this.chestItems = chestItems;
    int chestOption = rand.nextInt(2);
    
    if (chestOption == 0) {
      this.chestItems = new AttackPermanentPotion(1);
    } else if (chestOption == 1) {
      this.chestItems = new DefensePermanentPotion(1);
    }
    System.out.println(chestOption);
  }
  
  Chest(Item chestItem) {
    this.chestItems = chestItem;
  }
  
  public Item getChestItems() {
    return chestItems;
  }
  
  public void removeItem() {
    this.chestItems = null;
  }
}