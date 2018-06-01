abstract class Quest {
  private int xpReward;
  private Item itemReward;
  private String name;
  private String[] objectives;

  Quest(int experience, String name, String[] objectives, Item itemReward) {
    this.xpReward = experience;
    this.name = name;
    this.objectives = objectives;
    this.itemReward = itemReward;
  }

  abstract void initialize(Object[][] world);
  public Item giveItemReward(){
    return this.itemReward;
  }
  public int giveXPReward(){
    return this.xpReward;
  }
}
