class MainQuestA extends Quest {
  int archerKills=0, banditKills=0, enemyCountA, enemyCountB, currentTask;
  //items to fetch
  MainQuestA(int experience, String name, String[] objectives, Item itemReward) {
    super(experience, name, objectives, itemReward);
  }

  @Override
  void spawn(World[][] world) {
    world[6][6] = new Peasant(10, "Bob", true, this);
  }
  
  
  @Override
  void initialize(World[][] world) {
    enemyCountA = 5;
    enemyCountB = 5;
    System.out.println(getTask(getCurrentTask()));
    setCurrentTask(getCurrentTask() + 1);
    setActive(true);
  }
  
  Boolean updateObjective(int task) {
    if (task == 1) {
      archerKills++;
    }
      if (task == 2) {
      banditKills++;
    }
    if (archerKills >= enemyCountA && banditKills >= enemyCountB && getCurrentTask() == 1) {
      System.out.println(getTask(getCurrentTask()));
      setCurrentTask(getCurrentTask() + 1);
    }
    return false;
  }
}