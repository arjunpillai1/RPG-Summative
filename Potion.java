abstract class Potion extends Consumable{
  

  Potion(String name, int cost, int timer){
    super(name, cost);
    this.timer = timer;

  }
}