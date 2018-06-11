class FrostGrass extends Floor{
 
  private int difficulty;
  FrostGrass(){
  }

  FrostGrass(int num) {
    this.difficulty = num;
  }

  public int getDifficulty() {
    return this.difficulty;
  }
}