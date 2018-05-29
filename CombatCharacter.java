abstract class CombatCharacter extends Character {

  private int strength;
  private int intelligence;
  private int defence;
  private int level;
  private int accuracy;



  CombatCharacter (int health, int strength, int intelligence, int defence, int level, int accuracy, String name) {
    super (health, name);
    this.strength = strength;
    this.intelligence = intelligence;
    this.defence = defence;
    this.level = level;
    this.accuracy = accuracy;
  }

  abstract void attack(Object enemy);
  


  public int getStr(){
    return strength;
  }


  public void setStr(int newStr){
    this.strength = newStr;
  }

  public int getInt(){
    return intelligence;
  }


  public void setInt(int newInt){
    this.intelligence = newInt;
  }


  public int getDef(){
    return defence;
  }


  public void setDef(int newDef){
    this.defence = newDef;
  }


  public int getLvl(){
    return level;
  }


  public void setLvl(int newLvl){
    this.level = newLvl;
  }


  public int getAccuracy(){
    return accuracy;
  }


  public void setAccuracy(int newAccuracy){
    this.accuracy = newAccuracy;
  }





}
