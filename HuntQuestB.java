/*
 * [HuntQuestB.java]
 * Hunt quest/side quest
 * Albert Quon
 * 06/14/2018
 */
class HuntQuestB extends Quest {
  int killsA=0, killsB=0, enemyCountA, enemyCountB;
  HuntQuestB(int experience, String name, String[] objectives, Item itemReward) {
    super(experience, name, objectives, itemReward);
  }
  
  @Override
  void spawn(World[][] world) {
    world[10][61] = new NPC(10, "Hunter Dylan", true, this);
  }
  @Override 
  void initialize(World[][] world) {
    enemyCountA = 5;
    enemyCountB = 10;
    setActive(true);
  }
  
  Boolean updateObjective(int task) {
    if (task == 1) {
      killsA++;
    }
    else if (task == 2) {
      killsB++;
    }
    if (killsA == enemyCountA && killsB == enemyCountB) {
      setActive(false);
      return true;
    }
    return false;
  }
  
  public int trackTasks(int task) {
    if (task == 1) {
      return killsA;
    } else if (task == 2) {
      return killsB;
    }
    return killsA;
  }
}
