class MainQuestA extends Quest {
  //items to fetch
  MainQuestA(int experience, String name, String[] objectives, Item itemReward) {
    super(experience, name, objectives, itemReward);
  }

  void initialize(Object[][] world) {
    world[6][6] = new Peasant(10, "john");
    //if (objective complete)
  }
}
