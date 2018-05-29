abstract class Type extends Enemy{
  
  private String type;
  
    Type(int health, int strength, int intelligence, int defence, int level, int accuracy, String name,  int posX, int posY){
    super(health, strength, intelligence, defence, level, accuracy, name, posX, posY);
  }



  public String getType(){
    return type;
  }
  
  
    public void setType(String newType){
    this.type = newType;
  }
    
}