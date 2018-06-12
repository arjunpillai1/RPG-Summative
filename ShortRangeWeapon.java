/* class ShortRangeWeapon
 * Johann Muth
 * May 22 2018
 * Abstract class of short ranged weapons to be used in the game (eg. swords etc)
 */ 

abstract class ShortRangeWeapon extends Weapon{
  
  
  ShortRangeWeapon(String name, int cost, int attackBoost, double speed, int range){
    
    super(name, cost, attackBoost, speed, range);
    
    
  }
  public void attack(Player user, CombatCharacter target){
    target.setHealth((target.getHealth())- ((int) (Math.floor(( ( ( (2 * user.getLvl() + 2) * 60 * ( (user.getStr() + user.getWeaponBoost())/target.getDef() ) ) / 50 ) + 2) * ( ( (int)(Math.random()*16) + 85) / 100)))));
  }
  public void attack(Player user, CombatCharacter target){
    target.setHealth((target.getHealth())- ((int) (Math.floor(( ( ( (2 * user.getLvl() + 2) * 60 * ( user.getStr()/target.getDef() ) ) / 50 ) + 2) * ( ( (int)(Math.random()*16) + 85) / 100)))));
    }
  
  
}