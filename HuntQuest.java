class HuntQuest extends Quest {
  int kills;
  HuntQuest(int experience, String name, String[] objectives, Item itemReward) {
    super(experience, name, objectives, itemReward);
  }
  
  @Override
  void spawn(Object[][] world) {
    world[7][6] = new Peasant(10, "bobo", true, this);
  }
  @Override
  void initialize(Object[][] world) {
    System.out.println(giveTask(0));
  }
  
  void updateObjective(int kills) {
    this.kills += kills;
  }
}
