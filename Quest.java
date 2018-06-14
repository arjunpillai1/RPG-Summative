/* [Quest.java]
 * Quest super class for all quests
 * Albert Quon
 * 06/13/2018
 */


abstract class Quest extends World{
  private int xpReward;
  private Item itemReward;
  private String name;
  private String[] objectives;
  private Boolean complete = false;
  private Boolean active = false;
  private int currentTask;
  /*
   * 
   * 
   * 
   */
  Quest(int experience, String name, String[] objectives, Item itemReward) {
    this.xpReward = experience;
    this.name = name;
    this.objectives = objectives;
    this.itemReward = itemReward;
    this.complete = complete;
  }
  
  abstract void spawn(World[][] world);
  
  abstract void initialize(World[][] world);
  
  abstract Boolean updateObjective(int task);
  /*
   * 
   * 
   * 
   */
  public Boolean getComplete() {
    return this.complete;
  }
  /*
   * 
   * 
   * 
   */
  public void setComplete(Boolean completed){
    this.complete = completed;
  }
  /*
   * 
   * 
   * 
   */
  public String getTask(int index) {
    return this.objectives[index];
  }
  /*
   * 
   * 
   * 
   */
  public int trackTask(int task) {
    return 0;
  }
  /*
   * 
   * 
   * 
   */
  public void setActive(Boolean active){
    this.active = active;
  }
  /*
   * 
   * 
   * 
   */
  public int getNumTasks() {
    return this.objectives.length;
  }
  /*
   * 
   * 
   * 
   */
  public Boolean getActive(){
    return this.active;
  }
  /*
   * 
   * 
   * 
   */
  public String getName() {
    return this.name;
  }
  /*
   * 
   * 
   * 
   */
  public int getCurrentTask() {
    return this.currentTask;
  }
  /*
   * 
   * 
   * 
   */
  public void setCurrentTask(int task) {
    this.currentTask = task;
  }
  /*
   * 
   * 
   * 
   */
  public Item getItemReward(){
    return this.itemReward;
  }
  /*
   * 
   * 
   * 
   */
  public int getXPReward(){
    return this.xpReward;
  }
}
