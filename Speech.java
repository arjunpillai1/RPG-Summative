class Speech {
  private String message;
  private int posX, posY;
  
  Speech(String message, int posX, int posY) {
    this.message = message;
  }
  
  public String getMessage() {
    return this.message;
  }
  
  public int getPosX() {
    return this.posX;
  }
  public int getPosY() {
    return this.posY;
  }
  
}