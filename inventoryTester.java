/* inventoryTester
 * Program Desc
 * Johann Muth
 * 4-6-2018
 */


import java.util.Scanner;
class inventoryTester {
  public static void main (String[] args) {
    Inventory inv = new Inventory();
    inv.find(new RustySword());
    inv.find(new FalconSword());
    inv.find(new TinArmour());
    inv.find(new BasicAttackPotion());
    Scanner input = new Scanner (System.in);
    PlayerTest bob = new PlayerTest (0, 0, 0, 0, 0, 0, "Bob",0,0);
    int option;
    while(true){
      System.out.println("0=equip, 1=use");
      option = input.nextInt();
      if (inv.call(option) instanceof Weapon){
        bob.equipWeapon(inv, option);
        
      }
      else if(inv.call(option) instanceof Armour){
        bob.equipArmour(inv, option);
      }
      else if(inv.call(option) instanceof AttackPotion){
        bob.useAttackPotion(inv, option);
      }
      System.out.println("Equipped " + inv.getItemName(option));
      for (int i=0; i<4; i++){
        if ( inv.call(i) instanceof Weapon) {
          Weapon weapon = (Weapon)(inv.call(i));
          if (weapon.getEquipped() == true){
            System.out.println("Bob has " + weapon.getName() + " equipped");
          }
        } else if (inv.call(i) instanceof Armour) {
          Armour armour = (Armour)(inv.call(i));
          if (armour.getEquipped() == true){
            System.out.println("Bob has " + armour.getName() + " equipped");
          }
        }
        
        
        
      }
    }
  }
}
