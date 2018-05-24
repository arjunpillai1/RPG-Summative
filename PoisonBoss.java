class PoisonBoss extends Type{
    private final static String name= "Venom";
    private String newType;

    PoisonBoss(int health, int strength, int intelligence, int defence, int level, int accuracy){
        super(health, strength, intelligence, defence, level, accuracy);
        newType = "poison";
        setType(newType);
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
    }
}