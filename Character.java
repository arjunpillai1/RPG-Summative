/*
 * 
 * 
 */
abstract class Character extends World{
  
  private String name;
  private int health;
  private Object quest;
  /*
   * 
   * 
   */
  Character(int health, String name){
    this.name = name;
    this.health = health;
  }  
  /*
   * 
   * 
   */
  Character(int health, String name, Object quest){
    this.name = name;
    this.health = health;
    this.quest = quest;
  }
  /*
   * 
   * 
   */
  public int getHealth(){ //health methods are made in character in case of NPC killing functionality
    return health;
  }
  
  /*
   * 
   * 
   */
  public void setHealth(int newHealth){
    this.health = newHealth;
  }
  /*
   * 
   * 
   */
  public String getName() {
    return name;
  }
  /*
   * 
   * 
   */
  public void setName(String newName) {
    this.name = newName;
  }
  /*
   * 
   * 
   */
  public Object getQuest() {
    return this.quest;
  }
  /*
   * 
   * 
   */
  public void setQuest(Object quest) {
    this.quest = quest;
  }
  
}




