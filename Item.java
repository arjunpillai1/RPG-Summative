/* class Item
 * Johann Muth
 * May 22 2018
 * Abstract class of all items to be found in inventory such as armor, weapons and consumables
 */ 

abstract class Item{
  

  int cost;
  
  
  Item(int cost){ //Constructor
    this.cost = cost; 
  }
  
  public int getCost(){
    return this.cost;
  }
}