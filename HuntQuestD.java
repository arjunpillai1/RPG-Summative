/*
 * [HuntQuest.java]
 * First hunt quest/side quest
 * Albert Quon
 * 06/14/2018
 */
class HuntQuestD extends Quest {
  int killsA=0, killsB=0, enemyCountA, enemyCountB, numTasks;
  /**
   * Makes quest available in the world
   * @param the world array
   */
  HuntQuestD(int experience, String name, String[] objectives, Item itemReward) {
    super(experience, name, objectives, itemReward);
    numTasks = objectives.length;
  }
  /**
   * Makes quest available in the world
   * @param the world array
   */
  @Override
  void spawn(World[][] world) {
    world[67][62] = new NPC(10, "Master Hunter Alek", true, this);
  }
  /**
   * Starts the quest in the world
   * @param the world array
   */
  @Override 
  void initialize(World[][] world) {
    enemyCountA = 5;
    enemyCountB = 10;
    setActive(true);
  }
  /**
   * Updates objectives
   * @param the task to update
   * @return if quest is complete
   */
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
  /**
   * Tracks the progress of tasks
   * @param the task to return progress of
   * @return the progress of task
   */
  public int trackTasks(int task) {
    if (task == 1) {
      return killsA;
    } else if (task == 2) {
      return killsB;
    }
    return killsA;
  }
}