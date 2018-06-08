abstract class NPC extends NonCombatCharacter{
  private Boolean questGiver;
  private Quest quest;
  
  NPC(int health, String name, Boolean isQuestGiver, Quest quest){
    super(health,name);
    this.questGiver= isQuestGiver;
    this.quest = quest;
  }
  
  NPC(int health, String name, Boolean isQuestGiver){
    super(health,name);
    this.questGiver= isQuestGiver;
  } 
  public Quest getQuest(){
    // construct the appropriate quest object and then somehow attribute it to the character
    return this.quest;
  }
  
  public void speak(){
    System.out.println("HI!");
  }
  
  public Boolean getQuestGiver() {
    return this.questGiver;
  }
}