class MainQuestC extends Quest {
  //items to fetch
  MainQuestC(int experience, String name, String[] objectives, Item itemReward) {
    super(experience, name, objectives, itemReward);
  }

  @Override
  void spawn(Object[][] world) {
    
  }
  @Override
  void initialize(Object[][] world) {
    
  }
  
  Boolean updateObjective(int task) {
    return false;
  }
}
