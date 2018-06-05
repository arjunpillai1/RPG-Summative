class HuntQuest extends Quest {
  int kills = 0, enemyCount;
  HuntQuest(int experience, String name, String[] objectives, Item itemReward) {
    super(experience, name, objectives, itemReward);
  }
  
  @Override
  void spawn(Object[][] world) {
    world[7][6] = new Peasant(10, "Hunter", true, this);
  }
  @Override
  void initialize(Object[][] world) {
    System.out.println(getTask(0));
    enemyCount = 5;
    System.out.println("Quest started: " + getName());
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
}