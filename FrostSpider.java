class FrostSpider extends Type{
  
  FrostSpider(int health, int strength, int intelligence, int defence, int level, int accuracy, 
           String name,  int posX, int posY, World initialGround){
    super(health, strength, intelligence, defence, level, accuracy,"frost", name, posX, posY, initialGround);
    
    setLvl(((int)(Math.random()*4)+6));
  }
  

  
}