class PoisonSnake extends Type{
  
  PoisonSnake(int health, int strength, int intelligence, int defence, int level, int accuracy, 
           String name,  int posX, int posY, World initialGround){
    super(health, strength, intelligence, defence, level, accuracy,"poison", name, posX, posY, initialGround);
    setLvl(((int)(Math.random()*3)+3));
  }
  
  

}