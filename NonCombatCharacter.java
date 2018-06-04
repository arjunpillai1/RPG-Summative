abstract class NonCombatCharacter extends Character{

  NonCombatCharacter(int health, String name){
    super(health,name);
  }
  
  NonCombatCharacter(int health, String name, Object associatedQuest){
    super(health,name,associatedQuest);
  }
}