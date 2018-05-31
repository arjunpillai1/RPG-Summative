/* class Sword
 * Johann Muth
 * May 23 2018
 * An abstract class of the shortranged weapon sword
 */



abstract class Sword extends ShortRangeWeapon{
  
  Sword(int cost, int attackBoost, double speed){
    super(cost, attackBoost, speed, 1); //range is 1
  }
  
  public void swing(Player user, Object[][] map, int xSpot, int ySpot){
    if (map[xSpot][ySpot] instanceof CombatCharacter){
      attack(user, (CombatCharacter)(map[xSpot][ySpot]));
    }
  }
  
}