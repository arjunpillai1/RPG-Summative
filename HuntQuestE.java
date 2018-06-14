/*
 * [HuntQuest.java]
 * First hunt quest/side quest
 * Albert Quon
 * 06/14/2018
 */
class HuntQuestE extends Quest {
  int killsA=0, killsB=0,killsC=0, enemyCountA, enemyCountB, enemyCountC, numTasks;
  /**
   * Makes quest available in the world
   * @param the world array
   */
  HuntQuestE(int experience, String name, String[] objectives, Item itemReward) {
    super(experience, name, objectives, itemReward);
    numTasks = objectives.length;
  }
  /**
   * Makes quest available in the world
   * @param the world array
   */
  @Override
  void spawn(World[][] world) {
    world[97][36] = new NPC(10, "Master Hunter Jack", true, this);
  }
  @Override 
  void initialize(World[][] world) {
    System.out.println(getTask(0));
    enemyCountA = 1;
    enemyCountB = 1;
    enemyCountC = 1;
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
    } else {
      killsC++;
    }
    if (killsA == enemyCountA && killsB == enemyCountB && killsC == enemyCountC) {
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
  @Override
  public int trackTask(int task) {
    if (task == 1) {
      return killsA;
    } else if (task == 2) {
      return killsB;
    }
    return killsA;
  }
}
