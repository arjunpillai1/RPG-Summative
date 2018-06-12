class HuntQuest extends Quest {
  int kills = 0, enemyCount;
  HuntQuest(int experience, String name, String[] objectives, Item itemReward) {
    super(experience, name, objectives, itemReward);
  }
  
  @Override
  void spawn(World[][] world) {
    world[10][57] = new Peasant(10, "Hunter Alex", true, this);
  }
  @Override
  void initialize(World[][] world) {
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
