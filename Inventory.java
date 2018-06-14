/* class Inventory
 * Johann Muth
 * May 24 2018
 * Storage system of all items
 */  

import java.util.ArrayList;

class Inventory{
  ArrayList<Item> inventory = new ArrayList<Item>(30);
  
  /*public void equip(int placement, Player user){
    if ( (inventory.get(placement)).getInventoryNum() == 1){ //it's a weapon
      Weapon weapon = (Weapon)(inventory.get(placement));
      user.unequipWeapon();
      user.equipWeapon(weapon);
    } else if ( (inventory.get(placement)).getInventoryNum() == 2){// it's armour
      Armour armour = (Armour)(inventory.get(placement));
      user.unequipArmour(armour);
      user.equipArmour(armour);

    } else if ( (inventory.get(placement)).getInventoryNum() == 3){// it's armour
      AttackPotion potion = (AttackPotion)(inventory.get(placement));
      user.useAttackPotion(potion);
      inventory.remove(placement);

    }
  }
  */
  /**
   * Discards an item based on location in inventory
   * @param index
   */
  public void tossItem(int placement){
    inventory.remove(placement);
  }
  

//  public void useConsumable(int placement){
//    if ( (inventory.get(placement)).getInventoryNum() == 3){ //it's consumable
//      ((Consumable)(inventory.get(placement))).use();
//      inventory.remove(placement);
//    }
//    map[xSpot][ySpot] = map[xSpot+1][ySpot];
//  }
  
  /**
   * Returns the item name at a specific index
   * @return the item name
   */
  public String getItemName(int placement){
    return (inventory.get(placement)).getName();
  }
  /**
   * Adds an item to inventory
   * @param the item to add
   */
  public void find(Item object){
    inventory.add(object);
  }
  /**
   * Finds a specific item at an index
   * @returns the item at the index
   */
  public Item call(int placement){
     return inventory.get(placement);
  }
  /**
   * The size of the inventory
   * @return the inventory size
   */
  public int amount(){
    return inventory.size();
  }
  /**
   * Finds what type of item in inventory
   * @param the placement in inventory
   * @return boolean value of being equippable
   */
  public boolean getState(int placement){
    if ((inventory.get(placement)) instanceof Weapon){
      Weapon weapon = (Weapon)(inventory.get(placement));
      return(weapon.getEquipped());
    } else if ((inventory.get(placement)) instanceof Armour){
      Armour armour = (Armour)(inventory.get(placement));
      return(armour.getEquipped());
    } else {
      return(false);
    }
  }
  /**
   * Picks up an item based on the position of the world
   * @param the item found, world array, x and y position of the item
   */
  public void pickUpItem(Item found, World[][] map, int xSpot, int ySpot){
    int invNum = found.getInventoryNum();
    for (int i=0; i <= inventory.size(); i++){
      if (invNum == (inventory.get(i)).getInventoryNum()){
        inventory.add(i, found);
        return;
      } else {
        inventory.add(found);
      }
    }
    map[xSpot][ySpot] = map[xSpot+1][ySpot];
  }
  
  
}