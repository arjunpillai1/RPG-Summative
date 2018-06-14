class PoisonSorcerer extends Type{
  
  PoisonSorcerer(int health, int strength, int intelligence, int defence, int level, int accuracy, 
           String name,  int posX, int posY, World initialGround){
    super(health, strength, intelligence, defence, level, accuracy,"poison", name, posX, posY, initialGround);
    setLvl(((int)(Math.random()*3)+3));
  }
  
  
  public void attack(int strength, int intelligence){
    //if enemy hits the player, deal 10% damage to player(Tentative) and 
    //deal a poison effect causing 1 damage over time for 5 hits
    //once it attacks it will wait 1 second to attack again
  }
}