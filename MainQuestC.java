class MainQuestC extends Quest {
  //items to fetch
  MainQuestC(int experience, String name, String[] objectives, Item itemReward) {
    super(experience, name, objectives, itemReward);
  }

  void initialize(Object[][] world) {
    world[6][6] = new Peasant(10, "bob");
    //if (objective complete)
  }
}
