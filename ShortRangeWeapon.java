/**
 * ShortRangeWeapon
 * @author Johann Muth
 * @author Arjun Pillai
 * May 22 2018
 * Abstract class of short ranged weapons to be used in the game (eg. swords etc)
 */ 

abstract class ShortRangeWeapon extends Weapon{
  
  /**
   * Default short range weapon constructor
   * @param name, value, attack boosted, speed, range
   */
  ShortRangeWeapon(String name, int cost, int attackBoost, double speed, int range){
    
    super(name, cost, attackBoost, speed, range);
    
    
  }
  
  /**
  *attack method 
  * @param target The target which is being hit by the weapon
  * @param user The Player who is using the weapon
  */ 
  public void attack(Player user, CombatCharacter target){
    target.setHealth((target.getHealth())- ((int) (Math.floor(( ( ( (2 * user.getLvl() + 2) * 60 * ( (user.getStr() + user.getWeaponBoost())/target.getDef() ) ) / 50 ) + 2) * ( ( (int)(Math.random()*16) + 85) / 100)))));
  }

  
}
