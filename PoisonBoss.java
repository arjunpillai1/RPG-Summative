class PoisonBoss extends Type{
   
    PoisonBoss(int health, int strength, int intelligence, int defence, int level, int accuracy, String name,  int posX, int posY){
        super(health, strength, intelligence, defence, level, accuracy,"poison"/*type*/, "Venom"/*name*/, posX, posY);
    }

    /*attack
     * the melee attack for the boss
     * @param int strength the physical attacks
     * @param int intelligence the value of ranged attacks
     * @return void
     */
    public void attack(int strength, int intelligence){
        // Stronger than fire bosss
        // not every attack is a poison attack but poison effect is similar to burning
        // 2 seconds between attacks
    }

    public void move(){
        //if he is close to the player, he will melee attack
        //if he is farther from the player, he will used a ranged attack
        //only ranged attack applies poison effect
    }
}