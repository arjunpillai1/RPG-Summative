abstract class Normal extends Enemy{
  
  
    Normal(int health, int strength, int intelligence, int defence, int level, int accuracy, 
           String name,  int posX, int posY, World initialGround){
    super(health, strength, intelligence, defence, level, accuracy, name, posX, posY, initialGround);
  }
}