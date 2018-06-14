/* class FrostBoss
 * Aiden Gimpel
 * June 14th, 2018
 * object for the frost typed boss
*/
class FrostBoss extends Type{


    FrostBoss(int health, int strength, int intelligence, int defence, int level, int accuracy, 
           String name,  int posX, int posY, World initialGround){
        super(health, strength, intelligence, defence, level, accuracy,"frost"/*type*/ , 
              "The King of Winter"/*name*/, posX, posY, initialGround);
    }

    /*attack
     * the melee attack for the boss
     * @param int strength the physical attacks
     * @param int intelligence the value of ranged attacks
     * @return void
     */
    public void attack(int strength, int intelligence){
        // Stronger than poison bosss
        // not every attack is a freezing attack but freezing causes a slowdown in the player(either move speed or attack speed)
        // 2 seconds between attacks
    }

        /**
* move
* This method accepts a world, x Coord and a y coord as a parameter and moves the enemy.
* method returns a void
* @param A  world array that holds data representing the game map
* @param B integer holding the x coordinate of the enemy
* @param C integer holding the y coordinate of the enemy
* @return void
*/
    public void move(){
        //if he is close to the player, he will melee attack
        //if he is farther from the player, he will used a ranged attack
        //only ranged attack applies freezing effect
    }
}