class PoisonGrass extends Floor {

  private int difficulty;
  PoisonGrass(){}

  PoisonGrass(int num) {
    this.difficulty = num;
  }

  public int getDifficulty() {
    return this.difficulty;
  }
}