/*
 * [HuntQuest.java]
 * First hunt quest/side quest
 * Albert Quon
 * 06/14/2018
 */
class HuntQuest extends Quest {
  int kills = 0, enemyCount;
  HuntQuest(int experience, String name, String[] objectives, Item itemReward) {
    super(experience, name, objectives, itemReward);
  }
  
  @Override
  void spawn(World[][] world) {
    world[10][57] = new NPC(10, "Hunter Alex", true, this);
  }
  @Override
  void initialize(World[][] world) {
    enemyCount = 5;
    setActive(true);
  }
  
  Boolean updateObjective(int task) {
    if (task == 1) {
      kills++;
    }
    if (kills == enemyCount) {
      setActive(false);
      return true;
    }
    return false;
  }
  public int trackTasks(int task) {
    if (task == 1) {
      return kills;
    }
    return kills;
  }
}
