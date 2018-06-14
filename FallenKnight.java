class FallenKnight extends Normal{
  
  private int newLvl;
  private int imageChoice;
  
  FallenKnight(int health, int strength, int intelligence, int defence, int level, int accuracy, 
         String name,  int posX, int posY, World initialGround){
    super(health, strength, intelligence, defence, level, accuracy, name, posX, posY, initialGround);
    newLvl = ((int)(Math.random()*2)+1);
    setLvl(newLvl);
    imageChoice=((int)(Math.random()*3))*4;//0,4,8
  }
  
  
  public void attack(int strength, int intelligence){
    //if enemy hits the player, deal 10% damage to player(Tentative)
    //once it attacks it will wait 0.5 second to attack again
  }
  
  public int getImageChoice(){
   return this.imageChoice; 
  }
  
  public void move(){
    //enemy moves in a random way until it sees the player
    //the enemy chases after the player and attacks them
  }
}