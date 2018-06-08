class MainQuestA extends Quest {
  int killsA = 0, killsB = 0, killsC = 0, currentTask;
  //items to fetch
  MainQuestA(int experience, String name, String[] objectives, Item itemReward) {
    super(experience, name, objectives, itemReward);
  }

  @Override
  void spawn(World[][] world) {
    world[6][6] = new Peasant(10, "Bob", true, this);
  }
  
  @Override
  void initialize(World[][] world) {
    System.out.println(getTask(0));
    System.out.println(getTask(1));
    System.out.println("Quest started: " + getName());
    world[8][8] = new Peasant(10, "Farmer", false, this);
    world[8][8] = new Peasant(100000, "King Mangat", false, this);
    world[10][10] = new Peasant(1000, "Chancellor", false, this);
    setActive(true);
  }
  
  Boolean updateObjective(int task) {
    //update these conditions for the objectives
    if (task == 1) {
      killsA++;
      System.out.println("A:" + killsA);
    }
    if (task == 2) {
      killsB++;
      System.out.println("B:" + killsB);
    }
    if (task == 3) {
      killsC++;
    }
    // quest conditions fulfilled
    //normal enemies
    if (killsA >= 5 && killsB >= 5 && getCurrentTask() < 3) {
      setCurrentTask(3);
      killsA = 0;
      killsB = 0;
    }
    //poison enemies
    else if (killsB >= 5 && killsB >= 5 && getCurrentTask() > 5 && getCurrentTask() < 7) {
      setCurrentTask(7);
      killsA = 0;
      killsB = 0;
    }
    //poison boss
    else if ((killsA >= 1) && getCurrentTask() == 9) {
      setCurrentTask(10);
      killsA = 0;
    }
    //frost enemies
    else if ((killsA >= 7) && (killsB >= 7) && (getCurrentTask() > 11) && (getCurrentTask() < 14)) {
      setCurrentTask(14);
      killsA = 0;
      killsB = 0;
    }
    //frost boss
    else if (killsA >= 1 && getCurrentTask() >= 16) {
      setCurrentTask(17);
      killsA = 0;
    }
    // fire monsters
    else if (killsA >= 10 && killsB >= 10 && getCurrentTask() > 18 && getCurrentTask() < 21) {
      setCurrentTask(21);
      killsA = 0;
      killsB = 0;
    }
    //fire boss
    else if (killsA >= 1 && getCurrentTask() == 24) {
      setCurrentTask(25);
      killsA = 0;
    }
    //mangat
    else if (killsA >= 1 && getCurrentTask() == 28) {
      setCurrentTask(29);
      killsA = 0;
    }
    else if (getCurrentTask() == 30) {
      return true;
    }
    return false;
  }
  
  @Override
  public int getXPReward() {
    if (getCurrentTask() < 3) {
      return 10;
    }
    else if (getCurrentTask() < 7 && getCurrentTask() > 5) {
      return 25;
    }
    else if (getCurrentTask() < 14 && (getCurrentTask() > 11)) {
      return 50;
    }
    else if (getCurrentTask() < 21 && getCurrentTask() > 18) {
      return 1000;
    }
    else if (getCurrentTask() == 25) {
      return 10000;
    }
    else if (getCurrentTask() == 29) {
      return 1000000;
    }
    return 0;
  }
}
