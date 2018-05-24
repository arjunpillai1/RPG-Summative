class FireBoss extends Type{

    private String newType;
    Private final String name = "The Volcano";

    FireSpider(int health, int strength, int intelligence, int defence, int level, int accuracy){
        super(health, strength, intelligence, defence, level, accuracy);
        newType = "fire";
        setType(newType);
    }


    public void attack(int strength, int intelligence){
        //if enemy hits the player, deal 25% damage to player(Tentative) and
        //deal a burn effect causing 2 damage over time for 5 hits
        //once it attacks once it will wait 2 second to attack again
    }


    public void move(){
        //The Volvano moves towards the enemy slowly
        //if he is close to the player, he will melee attack
        //if he is farther from the player, he will used a ranged attack
        //only ranged attack applies burn effect
    }
}
  
  