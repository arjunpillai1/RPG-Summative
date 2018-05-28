abstract class Enemy extends CombatCharacter{
  
  
  
  Enemy(int health, int strength, int intelligence, int defence, int level, int accuracy, String name){
    super(health, strength, intelligence, defence, level, accuracy, name);
  }
  
  
       abstract void move();
        //enemy moves in a random way until it sees the player
        //the enemy chases after the player and attacks them
        //takes in array and return int
      
  
  
}


