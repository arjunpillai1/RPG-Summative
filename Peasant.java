/* 
 * [Peasant.java]
 * Peasant class for NPCs
 * Albert Quon
 * 05/31/2018
 */

class Peasant extends NPC {
  Peasant(int health, String name, Boolean isQuestGiver, Quest quest) {
    super(health, name, isQuestGiver, quest);
  }
  Peasant(int health, String name, Boolean isQuestGiver) {
    super(health, name, isQuestGiver);
  }
  
}