abstract class Type extends Enemy{
  
  private String type;
  
    Type(int health, int strength, int intelligence, int defence, int level, int accuracy,String type, String name,  int posX, int posY){
    super(health, strength, intelligence, defence, level, accuracy, name, posX, posY);
    this.type=type;
  }
   
}