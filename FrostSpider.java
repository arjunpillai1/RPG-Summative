class FrostSpider extends Type{
  
  private String newType;
  private int newLvl;
  
      FrostSpider(int health, int strength, int intelligence, int defence, int level, int accuracy, String name,  int posX, int posY){
    super(health, strength, intelligence, defence, level, accuracy, name, posX, posY);
        newType = "frost";
    setType(newType);
    newLvl = ((int)(Math.random()*4)+6);
    setLvl(newLvl);
  }
      
      
      public void attack(int strength, int intelligence){
      //if enemy hits the player, deal 10% damage to player(Tentative) and 
      //deal a burn effect causing  1 damage over time for 5 hits
      //once it attacks once it will wait 1 second to attack again
  }

}