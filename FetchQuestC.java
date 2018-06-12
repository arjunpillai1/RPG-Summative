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
        System.out.println(getTask(0));
    System.out.println("Quest started: " + getName());
    setActive(true);
    //if((bag.getInventory()).contains(RustySword)){
    for (int i=0; i<(bag.amount()); i++){
      if (bag.call(i) instanceof FalconSword){
    System.out.println(getTask(1));
      }
    }
  }
  Boolean updateObjective(int task) {
    return false;
  }
}