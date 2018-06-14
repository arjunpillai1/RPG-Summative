class Archer extends Normal{
  
  private int newLvl;
  private int imageChoice;
  
  Archer(int health, int strength, int intelligence, int defence, int level, int accuracy,
         String name, int posX, int posY , World initialGround){
    super(health, strength, intelligence, defence, level, accuracy, name, posX, posY, initialGround);
    newLvl = ((int)(Math.random()*2)+1);
    setLvl(newLvl);
    imageChoice=((int)(Math.random()*3))*4;//0,4,8
  }
  
  
  public int getImageChoice(){
   return this.imageChoice; 
  }
  
  public void move(){
    //enemy moves in a random way until it sees the player
    //enemy stops(Tentative) to shoot at the player
  }
}