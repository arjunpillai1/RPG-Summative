/* class Item
 * Johann Muth
 * May 22 2018
 * Abstract class of all items to be found in inventory such as armor, weapons and consumables
 */ 

abstract class Item{
  

  int cost;
  
  int inventoryNum;
  
  
  Item(int cost, int inventoryNum){ //Constructor
    this.cost = cost; 
    this.inventoryNum = inventoryNum;
  }
  
  public int getCost(){
    return this.cost;
  }
  
  public int getInventoryNum(){
    return this.inventoryNum;
  }
}