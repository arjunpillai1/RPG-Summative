/**
 * Player Class
 * Guy and Arjun
 * May 24 2018
 */ 

class Player extends Character {
  private String name;
  
  Player(int health, int strength, int intelligence, int defence, int level, int accuracy, String name) {
    super(health, strength, intelligence, defence, level, accuracy);
    this.name = name;
    
  }
  
  public void attack(int strength, int intelligence) {
    
  }
  
  public void move() { 
    
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String charName) {
    this.name = charName;
  }
  
}



