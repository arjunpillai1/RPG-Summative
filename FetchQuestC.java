class FetchQuestC extends Quest {
  
  FetchQuestC(int experience, String name, String[] objectives, Item itemReward) {
    super(experience, name, objectives, itemReward);
  }
  
  @Override
  void spawn(World[][] world) {
    world[10][10] = new Peasant(10, "john", true, this);
  }
  @Override
  void initialize(World[][] world, Inventory bag) {
    
  }
  Boolean updateObjective(int task) {
    return false;
  }
}