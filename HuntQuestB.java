class HuntQuestB extends Quest {
  int kills, enemyCount;
  HuntQuestB(int experience, String name, String[] objectives, Item itemReward) {
    super(experience, name, objectives, itemReward);
  }
  
  @Override
  void spawn(Object[][] world) {
    world[60][60] = new Peasant(10, "bobo", true, this);
  }
  @Override
  void initialize(Object[][] world) {
    System.out.println(getTask(0));
    enemyCount = 0;
    for (int i = 0; i < world.length; i++) {
      for (int j = 0; j < world.length; j++) {
        if (world[i][j] instanceof Bandit) {
          enemyCount+=1;
        }
      }
    }
  }
  
  void updateObjective(Object[][] world) {
    int remaining = 0;
    for (int i = 0; i < world.length; i++) {
      for (int j = 0; j < world.length; j++) {
        if (world[i][j] instanceof Bandit) {
          remaining+=1;
        }
      }
    }
    enemyCount = enemyCount - (enemyCount - remaining);
    if (enemyCount == 0) {
      setComplete();
    }
  }
}
