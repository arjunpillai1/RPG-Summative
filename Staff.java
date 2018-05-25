/* class Staff
 * Johann Muth
 * May 22 2018
 * Abstract class of Staffs which have attack and spells
 */ 

abstract class Staff extends LongRangeWeapon{
  Staff(int cost, int attackBoost, double speed, int range){
    super(cost, attackBoost, speed, range);
  }
}