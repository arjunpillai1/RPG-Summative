/* class ShortRangeWeapon
 * Johann Muth
 * May 22 2018
 * Abstract class of short ranged weapons to be used in the game (eg. swords etc)
 */ 

abstract class ShortRangeWeapon extends Weapon{
    
  
  ShortRangeWeapon(int cost, int attackBoost, double speed, int range){
    
    super(cost, attackBoost, speed, range);
    

  }
  
  
}