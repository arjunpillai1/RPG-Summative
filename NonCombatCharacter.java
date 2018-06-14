abstract class NonCombatCharacter extends Character{
  /*
   * 
   * 
   * 
   */
  NonCombatCharacter(int health, String name){
    super(health,name);
  }
  /*
   * 
   * 
   * 
   */
  NonCombatCharacter(int health, String name, World associatedQuest){
    super(health,name,associatedQuest);
  }
}