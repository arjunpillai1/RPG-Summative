class MainQuestB extends Quest {
  //items to fetch
  MainQuestB(int experience, String name, String[] objectives, Item itemReward) {
    super(experience, name, String[] objectives, itemReward);
  }

  void initialize(Object[][] map) {
    world[6][6] = new NPC();
    //if (objective complete)
  }
}
