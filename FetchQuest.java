class FetchQuest extends Quest {
  Inventory bag;
  
  FetchQuest(int experience, String name, String[] objectives, Item itemReward) {
    super(experience, name, objectives, itemReward);
    this.bag = bag;
  }
  
  @Override
  void spawn(World[][] world) {
    world[10][10] = new Peasant(10, "John", true, this);
  }
  @Override
  void initialize(World[][] world) {
    System.out.println(getTask(0));
    System.out.println("Quest started: " + getName());
    setActive(true);
    //if((bag.getInventory()).contains(RustySword)){
    for (int i=0; i<(bag.amount()); i++){
      if (bag.call(i) instanceof RustySword){
        System.out.println(getTask(1));
      }
    }
  }
  
  Boolean updateObjective(int task) {
    return false;
  }
}