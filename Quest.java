abstract class Quest {
  private int xpReward;
  private Item itemReward;
  private String name;
  private String[] objectives;
  private Boolean complete = false;
  private Boolean active = false;

  Quest(int experience, String name, String[] objectives, Item itemReward) {
    this.xpReward = experience;
    this.name = name;
    this.objectives = objectives;
    this.itemReward = itemReward;
    this.complete = complete;
  }

  abstract void spawn(Object[][] world);
  
  abstract void initialize(Object[][] world);
  
  abstract Boolean updateObjective(int task);
  
  public Boolean getComplete() {
    return this.complete;
  }
  public void setComplete(Boolean completed){
    this.complete = completed;
  }
  public String getTask(int index) {
    return this.objectives[index];
  }
  
  public void setActive(Boolean active){
    this.active = active;
  }
  
  public Boolean getActive(){
    return this.active;
  }
  
  public String getName() {
    return this.name;
  }
  
  
  public Item getItemReward(){
    return this.itemReward;
  }
  public int getXPReward(){
    return this.xpReward;
  }
}