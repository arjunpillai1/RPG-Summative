class FetchQuestC extends Quest {
  
  FetchQuestC(int experience, String name, String[] objectives, Item itemReward) {
    super(experience, name, objectives, itemReward);
  }
  
  @Override
  void spawn(Object[][] world) {
    world[10][10] = new Peasant(10, "john", true, this);
  }
  @Override
  void initialize(Object[][] world) {
    
  }
}