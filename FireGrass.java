class FireGrass extends Floor {

  private int difficulty;
  FireGrass(){}

  FireGrass(int num) {
    this.difficulty = num;
  }

  public int getDifficulty() {
    return this.difficulty;
  }
}