class Archer extends Normal{
  
  private int newLvl;
  
      Archer(int health, int strength, int intelligence, int defence, int level, int accuracy, String name){
    super(health, strength, intelligence, defence, level, accuracy, name);
    newLvl = ((int)(Math.random()*2)+1);
    setLvl(newLvl);
  }
      
      
      public void attack(int strength, int intelligence){
      //if enemy hits the player at range, deal 10% damage to player(Tentative)
      //once it attacks it will wait 0.5 second to attack again
  }
}