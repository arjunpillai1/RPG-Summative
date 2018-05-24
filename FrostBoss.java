class FrostBoss extends Type{
    private final static String name= "The King of Winter";
    private String newType;

    FrostBoss(int health, int strength, int intelligence, int defence, int level, int accuracy){
        super(health, strength, intelligence, defence, level, accuracy);
        newType = "fire";
        setType(newType);
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

    public void move(){
        //if he is close to the player, he will melee attack
        //if he is farther from the player, he will used a ranged attack
        //only ranged attack applies freezing effect
    }
}