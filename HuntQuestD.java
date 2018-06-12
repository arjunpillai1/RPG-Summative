class HuntQuestD extends Quest {
  int killsA=0, killsB=0, enemyCountA, enemyCountB, numTasks;
  HuntQuestD(int experience, String name, String[] objectives, Item itemReward) {
    super(experience, name, objectives, itemReward);
    numTasks = objectives.length;
  }
  
  @Override
  void spawn(World[][] world) {
    world[67][62] = new Peasant(10, "Hunter Alex", true, this);
  }
  @Override 
  void initialize(World[][] world, Inventory bag) {
    System.out.println(getTask(0));
    enemyCountA = 5;
    enemyCountB = 10;
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
    }
    if (killsA == enemyCountA && killsB == enemyCountB) {
      setActive(false);
      return true;
    }
    return false;
  }

}
