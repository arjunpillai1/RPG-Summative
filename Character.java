private String name;
  private int health;
  
  
  Character(int health, String name){
    this.name = name;
    this.health = health;
  }  
  
  public int getHealth(){ //health methods are made in character in case of NPC killing functionality
    return health;
  }
  
  
  public void setHealth(int newHealth){
    this.health = newHealth;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String charName) {
    this.name = charName;
  }