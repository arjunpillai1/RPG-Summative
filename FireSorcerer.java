class FireSorcerer extends Type{
  
  
  FireSorcerer(int health, int strength, int intelligence, int defence, int level, int accuracy, 
           String name,  int posX, int posY, World initialGround){
    super(health, strength, intelligence, defence, level, accuracy,"fire", name, posX, posY, initialGround);
    
    setLvl( ((int)(Math.random()*5)+10));//between 10 and 14
  }
  
      
      public void attack(int strength, int intelligence){
      //if enemy hits the player, deal 10% damage to player(Tentative) and
      //deal a burn effect causing  1 damage over time for 5 hits
      //once it attacks once it will wait 1 second to attack again
  }
      
      

}