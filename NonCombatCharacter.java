/*
 * [NonCombatCharacter.java]
 * Super class for all peaceful characters
 * @author Aiden, Arjun
 * 05/25/2018
 */

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
  NonCombatCharacter(int health, String name, Quest associatedQuest){
    super(health,name,associatedQuest);
  }
}