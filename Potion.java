abstract class Potion extends Consumable{
  private int timer;

  Potion(String name, int cost, int timer){
    super(name, cost);
    this.timer = timer;
  }
  
  Potion(String name, int cost){
    super(name, cost);
   }
}