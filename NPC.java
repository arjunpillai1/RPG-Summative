/*
 * 
 * 
 * 
 */
import java.util.Random;

class NPC extends NonCombatCharacter{
  private Boolean questGiver;
  private Quest quest;
  Random rand = new Random();
  int dialogue;
  private int imageChoice;
  
  NPC(int health, String name, Boolean isQuestGiver, Quest quest){
    super(health,name);
    this.questGiver= isQuestGiver;
    this.quest = quest;
    imageChoice=((int)(Math.random()*3));
  }
  
  NPC(int health, String name, Boolean isQuestGiver){
    super(health,name);
    this.questGiver= isQuestGiver;
    imageChoice=((int)(Math.random()*5));
  } 
  public Quest getQuest(){
    // construct the appropriate quest object and then somehow attribute it to the character
    return this.quest;
  }
  
  public String speak(){
    int dialogue = rand.nextInt(12);
    if (dialogue == 6) {
      return "You're annoying me.";
    } 
    else if (dialogue == 3) {
      return "Lovely day eh?";
    }
    else if (dialogue == 9) {
      return "Have you heard the rumours about the Mangat creature?";
    }
    else if (dialogue == 1) {
      return "King Tagnam is the best king.";
    } else {
      return "Hello";
    }
  }
  
  public Boolean getQuestGiver() {
    return this.questGiver;
  }
  
  public int getImageChoice(){
   return this.imageChoice; 
  }
}