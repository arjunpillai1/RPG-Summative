abstract class Type extends Enemy{
  
  private String type;
  
    Type(int health, int strength, int intelligence, int defence, int level, int accuracy){
    super(health, strength, intelligence, defence, level, accuracy);
  }



  public String getType(){
    return type;
  }
  
  
    public void setType(String newType){
    this.type = newType;
  }
    
}