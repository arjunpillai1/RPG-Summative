class FireBoss extends Type{
    
  FireBoss(int health, int strength, int intelligence, int defence, int level, int accuracy, String name,  int posX, int posY){
    super(health, strength, intelligence, defence, level, accuracy,"fire"/*type*/, "The Volcano"/*name*/, posX, posY);
  }
  
  
  public void attack(int strength, int intelligence){
    //if enemy hits the player, deal 25% damage to player(Tentative) and 
    //deal a burn effect causing 2 damage over time for 5 hits
    //once it attacks once it will wait 2 second to attack again
  }
  
  
  public void move(){
    //The Volcano moves towards the enemy slowly
    //if he is close to the player, he will melee attack
    //if he is farther from the player, he will used a ranged attack
    //only ranged attack applies burn effect
  }
}

