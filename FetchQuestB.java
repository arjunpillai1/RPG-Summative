class FetchQuestB extends Quest {
  
  FetchQuestB(int experience, String name, String[] objectives, Item itemReward) {
    super(experience, name, objectives, itemReward);
  }
  
  @Override
  void spawn(World[][] world) {
    world[5][91] = new Peasant(10, "Gary", true, this);
  }
  @Override
  void initialize(World[][] world, Inventory bag) {
    
  }
  Boolean updateObjective(int task) {
    return false;
  }
}