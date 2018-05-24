/**
 * Player Class
 * Guy and Arjun
 * May 24 2018
 */ 

class Player extends Character {
  private String name;
  
  Player (int health, int strength, int intelligence, int defence, int level, int accuracy, String name) {
    this.name = name;
    super(health, strength, intelligence, defence, level, accuracy);
  }
  
  void attack(int strength, int intelligence) {
    
  }
  
  void move() { 
    
  }
  
  String getName() {
    return name;
  }
  
  String setName(String charName) {
    this.name = charName;
  }
  
}



