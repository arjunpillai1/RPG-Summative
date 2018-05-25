/*
 * [Grass.java]
 * Grass class to represent environment
 * Albert Quon
 * 05/25/2018
 */

class Grass extends Floor {
  
  private int difficulty;
  Grass(){}
  
  Grass(int num) {
    this.difficulty = num;
  }
  
  int getDifficulty() {
    return this.difficulty;
  }
}
