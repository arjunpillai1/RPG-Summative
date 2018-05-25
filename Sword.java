/* class Sword
 * Johann Muth
 * May 23 2018
 * An abstract class of the shortranged weapon sword
 */

abstract class Sword extends ShortRangeWeapon{
  
  Sword(int cost, int attackBoost, double speed){
    super(cost, attackBoost, speed, 1); //range is 1
  }
  
  public void attack(int strength){ //attack method
  }
}