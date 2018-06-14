/* [Character.java]
 * Superclass for all characters in program
 * @author Guy, Arjun, Aiden
 * 05/28/2018
 */
abstract class Character extends World{
  
  private String name;
  private int health;
  private Quest quest;
  /**
   * Constructor for the character
   * @param health, name
   */
  Character(int health, String name){
    this.name = name;
    this.health = health;
  }  
  /**
   * Constructor for the character
   * @para health, name, associated quest
   */
  Character(int health, String name, Quest quest){
    this.name = name;
    this.health = health;
    this.quest = quest;
  }
  /**
   * Getter for the health variable
   * @int return health;
   */
  public int getHealth(){ //health methods are made in character in case of NPC killing functionality
    return health;
  }
  
  /**
   * Setter for the health variable
   * @param the health to be set to
   */
  public void setHealth(int newHealth){
    this.health = newHealth;
  }
  /**
   * Getter for the name of the character
   * @return String value of name
   */
  public String getName() {
    return name;
  }
  /**
   * Setter for the name of the character
   * @param the name to be set
   */
  public void setName(String newName) {
    this.name = newName;
  }
  /**
   * Getter for the associated quest with the character
   * @return Quest object of the quest
   */
  public Quest getQuest() {
    return this.quest;
  }
  /**
   * Set an associated quest to character
   * @param Quest object of quest
   */
  public void setQuest(Quest quest) {
    this.quest = quest;
  }
  
}




