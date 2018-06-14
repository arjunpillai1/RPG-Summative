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
   * Quest constructor
   * @param experience reward, name, objectives, item reward
   */
  Quest(int experience, String name, String[] objectives, Item itemReward) {
    this.xpReward = experience;
    this.name = name;
    this.objectives = objectives;
    this.itemReward = itemReward;
    this.complete = complete;
  }
  
  /**
   * Makes quest present in the world
   * @param World array
   */
  abstract void spawn(World[][] world);
  /**
   * Starts the quest
   * @param World array
   */
  abstract void initialize(World[][] world);
  
  /**
   * Update quest objectives
   * @param task
   * @return boolean value if quest is complete
   */
  abstract Boolean updateObjective(int task);
  /**
   * Get if the quest is complete
   * @return boolean of completion
   */
  public Boolean getComplete() {
    return this.complete;
  }
  /**
   * Set if quest is complete or not
   * @param boolean value of completion
   */
  public void setComplete(Boolean completed){
    this.complete = completed;
  }
  /**
   * Get a specific objective
   * @param the index
   * @return string of the objective
   */
  public String getTask(int index) {
    return this.objectives[index];
  }
  /**
   * Track the task progress
   * @param the specific task
   * @return the progress of task
   */
  public int trackTask(int task) {
    return 0;
  }
  /**
   * Set the quest to active or inactive
   * @param the boolean value of being active
   */
  public void setActive(Boolean active){
    this.active = active;
  }
  /**
   * Return the number of tasks in the quest
   * @param the number of tasks
   */
  public int getNumTasks() {
    return this.objectives.length;
  }
  /*
   * Return if the quest is active or not
   * @return the boolean value of being active
   */
  public Boolean getActive(){
    return this.active;
  }
  /**
   * Returns the quest name
   * @return the quest name
   */
  public String getName() {
    return this.name;
  }
  /**
   * Return the current task of the quest
   * @return current task
   */
  public int getCurrentTask() {
    return this.currentTask;
  }
  /**
   * Set the current task of the quest
   * @param the task to be set to
   */
  public void setCurrentTask(int task) {
    this.currentTask = task;
  }
  /**
   * Retrieve item reward
   * @return the item reward
   */
  public Item getItemReward(){
    return this.itemReward;
  }
  /**
   * Get the exp reward
   * @return the amount of exp player earned
   */
  public int getXPReward(){
    return this.xpReward;
  }
}
