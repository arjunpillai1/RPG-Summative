/* class Inventory
 * Johann Muth
 * May 24 2018
 * Storage system of all items
 */  

import java.util.ArrayList;

class Inventory{
  ArrayList<Item> inventory = new ArrayList<Item>(30);
  
  public void equip(int placement, Player user){
    if ( (inventory.get(placement)).getInventoryNum() == 1){ //it's a weapon
      Weapon weapon = (Weapon)(inventory.get(placement));
      user.unequipWeapon(weapon);
      user.equipWeapon(weapon);
    } else if ( (inventory.get(placement)).getInventoryNum() == 2){// it's armour
      Armour armour = (Armour)(inventory.get(placement));
      user.unequipArmour(armour);
      user.equipArmour(armour);
    }
  }
  
  public void useConsumable(int placement){
    if ( (inventory.get(placement)).getInventoryNum() == 3){ //it's consumable
      ((Consumable)(inventory.get(placement))).use();
      inventory.remove(placement);
    }
  }
  
  public void pickUpItem(Item found, Object[][] map, int xSpot, int ySpot){
    int invNum = found.getInventoryNum();
    for (int i=0; i <= inventory.size(); i++){
      if (invNum == (inventory.get(i)).getInventoryNum()){
        inventory.add(i, found);
        return;
      }
    }
    map[xSpot][ySpot] = map[xSpot+1][ySpot];
  }
  
  
}