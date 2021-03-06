class HuntQuestE extends Quest {
  int killsA=0, killsB=0,killsC=0, enemyCountA, enemyCountB, enemyCountC, numTasks;
  HuntQuestE(int experience, String name, String[] objectives, Item itemReward) {
    super(experience, name, objectives, itemReward);
    numTasks = objectives.length;
  }
  
  @Override
  void spawn(World[][] world) {
    world[97][36] = new Peasant(10, "Master Hunter Jack", true, this);
  }
  @Override 
  void initialize(World[][] world) {
    System.out.println(getTask(0));
    enemyCountA = 1;
    enemyCountB = 1;
    enemyCountC = 1;
    System.out.println("Quest started: " + getName());
    for (int i = 0; i < numTasks; i++) {
      System.out.println(getTask(i));
    }
    setActive(true);
  }
  
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

}
