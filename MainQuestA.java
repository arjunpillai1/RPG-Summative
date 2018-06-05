class MainQuestA extends Quest {
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
    world[7][7] = new Peasant(10, "Notbob", false);
    System.out.println(getTask(1));
  }
  
  Boolean updateObjective(int task) {
    return false;
  }
}
