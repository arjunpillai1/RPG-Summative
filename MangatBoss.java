class MangatBoss extends Type {
  MangatBoss(int health, int strength, int intelligence, int defence, int level, int accuracy, 
           String name,  int posX, int posY, World initialGround){
    super(health, strength, intelligence, defence, level, accuracy,"fire", "Mangat", posX, posY
         , initialGround);
  }
  
}