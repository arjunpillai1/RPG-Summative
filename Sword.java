/* class Sword
 * Johann Muth
 * May 23 2018
 * An abstract class of the shortranged weapon sword
 */



abstract class Sword extends ShortRangeWeapon{
  /**
   * Default sword constructor 
   * @param name, value, attackBoost, speed
   */
  Sword(String name, int cost, int attackBoost, double speed){
    super(name, cost, attackBoost, speed, 1); //range is 1
  }
  /** 
   * Attacks a spot on the map
   * @param the user, the map, x and y coordinate
   */
  public void swing(Player user, World[][] map, int xSpot, int ySpot){
    if (map[xSpot][ySpot] instanceof CombatCharacter){
      attack(user, (CombatCharacter)(map[xSpot][ySpot]));
    }
  }
  

  
}