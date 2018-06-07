/* class Inventory
 * Johann Muth
 * May 24 2018
 * Storage system of all items
 */  

import java.util.ArrayList;

class Inventory{
  ArrayList<Item> inventory = new ArrayList<Item>(30);
  
  public void equip(int placement){
    if ( (inventory.get(placement)).getInventoryNum() == 1){ //it's a weapon
      ((Weapon)(inventory.get(placement))).equipWeapon();
    } else if ( (inventory.get(placement)).getInventoryNum() == 2){// it's armour
      ((Armour)(inventory.get(placement))).equipArmour();
    }
  }
  
  public void useConsumable(int placement){
    if ( (inventory.get(placement)).getInventoryNum() == 3){ //it's consumable
      ((Consumable)(inventory.get(placement))).use();
    }
  }
}