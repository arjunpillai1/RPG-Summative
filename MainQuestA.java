class MainQuestA extends Quest {
  int archerKills=0, banditKills=0, enemyCountA, enemyCountB;
  //items to fetch
  MainQuestA(int experience, String name, String[] objectives, Item itemReward) {
    super(experience, name, objectives, itemReward);
  }

  @Override
  void spawn(Object[][] world) {
    world[6][6] = new Peasant(10, "Bob", true, this);
    System.out.println(getTask(0));
  }
  
  @Override
  void initialize(Object[][] world) {
    System.out.println(getTask(0));
    enemyCountA = 5;
    enemyCountB = 5;
    System.out.println("Quest started: " + getName());
System.out.println(getTask(0));
    setActive(true);
  }
  
  Boolean updateObjective(int task) {
    if (task == 1) {
      archerKills++;
    }
      if (task == 2) {
      banditKills++;
    }
    if (archerKills == enemyCountA && banditKills == enemyCountB) {

      setActive(false);
      return true;
    }
    return false;
  }
}