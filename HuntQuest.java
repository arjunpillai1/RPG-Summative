/*
 * [HuntQuest.java]
 * First hunt quest/side quest
 * Albert Quon
 * 06/14/2018
 */
class HuntQuest extends Quest {
  int kills = 0, enemyCount;
  /**
   * Hunt Quest constructor
   * @param exp reward, name, objectives to be completed, item reward
   */
  HuntQuest(int experience, String name, String[] objectives, Item itemReward) {
    super(experience, name, objectives, itemReward);
  }
  
  @Override
  /**
   * Have the quest available in the world
   * @param the world array
   */
  void spawn(World[][] world) {
    world[10][57] = new NPC(10, "Hunter Alex", true, this);
  }
  @Override
  /**
   * Initialiize the quest
   * @param the world array
   */
  void initialize(World[][] world) {
    enemyCount = 5;
    setActive(true);
  }
  /**
   * Update the objectives
   * @param the task to be updated
   * @return boolean to indicate completion
   */
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
  /** 
   * Keeps track of progress in objectives
   * @param the requested task
   * @return the task progress
   */
  public int trackTasks(int task) {
    if (task == 1) {
      return kills;
    }
    return kills;
  }
}
