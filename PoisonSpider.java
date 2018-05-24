class PoisonSpider extends Type{
  
 private String newType;
  
      PoisonSpider(int health, int strength, int intelligence, int defence, int level, int accuracy){
    super(health, strength, intelligence, defence, level, accuracy);
    newType = "poison";
    setType(newType);
  }
      
      
      public void attack(int strength, int intelligence){
      //if enemy hits the player, deal 10% damage to player(Tentative) and 
      //deal a poison effect causing 1 damage over time for 5 hits
      //once it attacks it will wait 1 second to attack again
  }
      
      
      public void move(){
        //enemy moves in a random way until it sees the player
        //the enemy chases after the player and attacks them
      }
}