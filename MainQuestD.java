class MainQuestD extends Quest {
  //items to fetch
  MainQuestD(int experience, String name, String[] objectives, Item itemReward) {
    super(experience, name, objectives, itemReward);
  }

  void initialize(Object[][] world) {
    world[6][6] = new Peasant(10, "bob");
    //if (objective complete)
  }
}
