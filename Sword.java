/* class Sword
 * Johann Muth
 * May 23 2018
 * An abstract class of the shortranged weapon sword
 */

import java.util.Random;

abstract class Sword extends ShortRangeWeapon{
  
  Sword(int cost, int attackBoost, double speed){
    super(cost, attackBoost, speed, 1); //range is 1
  }
  
  public void attack(Player user, CombatCharacter target){
    target.setHealth((target.getHealth())- ((int) (Math.floor(( ( ( (2 * user.getLvl() + 2) * 60 * ( user.getStr()/target.getDef() ) ) / 50 ) + 2) * ( ( (int)(Math.random()*16) + 85) / 100)))));
    }
  public void swing(Player user, Object[][] map, int xSpot, int ySpot){
    if (map[xSpot][ySpot] instanceof CombatCharacter){
      attack(user, (CombatCharacter)(map[xSpot][ySpot]));
    }
  }
  
}