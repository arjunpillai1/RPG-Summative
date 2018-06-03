abstract class Quest {
  private int xpReward;
  private Item itemReward;
  private String name;
  private String[] objectives;
  private Boolean complete = false;

  Quest(int experience, String name, String[] objectives, Item itemReward) {
    this.xpReward = experience;
    this.name = name;
    this.objectives = objectives;
    this.itemReward = itemReward;
    this.complete = complete;
  }

  abstract void spawn(Object[][] world);
  
  abstract void initialize(Object[][] world);
  
  public Boolean getComplete() {
    return this.complete;
  }
  public void setComplete(){
    this.complete = true;
  }
  public String giveTask(int index) {
    return this.objectives[index];
  }
  
  public Item giveItemReward(){
    return this.itemReward;
  }
  public int giveXPReward(){
    return this.xpReward;
  }
}
