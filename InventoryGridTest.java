/* [GridTest.java]
 * A program to demonstrate usage of DisplayGrid.java.
 * @author Mangat
 */

class GridTest { 
  

  public static void main(String[] args) { 
    RustySword rusty = new RustySword();
    FalconSword falco = new FalconSword();
    TinArmour tin = new TinArmour();
  Inventory bag= new Inventory();
  bag.find(rusty);
  bag.find(falco);
  bag.find(tin);
  bag.find(tin);
  bag.find(falco);
  bag.find(falco);
  bag.find(falco);
  bag.find(rusty);
  bag.find(rusty);
  bag.find(tin);
  Player bob = new Player(0,0,0,0,0,0, "Bob",0,0);
  // Initialize Map
  //  moveItemsOnGrid(bag);
    
    // display the fake grid on Console
    //DisplayGridOnConsole(map);
    { 
    
     
    // Initialize Map
      equipTest(bob, bag);
    
    // display the fake grid on Console
    //DisplayGridOnConsole(map);
    
    //Set up Grid Panel
    DisplayGrid grid = new DisplayGrid(bag);
    
    //Display the grid on a Panel
    grid.refresh();
    
    
    //Small delay
    try{ Thread.sleep(1000); }catch(Exception e) {};
    
    
    // Initialize Map (Making changes to map)
   equipTest(bob, bag);
    
    //Display the grid on a Panel
    grid.refresh();
  }
    
  }
  
    public static void equipTest(Player player, Inventory bag){
    int something = (int)(Math.random()*9);
    if (bag.call(something) instanceof Weapon){
      player.equip(bag, something);
  }
  }
}