abstract class Potion extends Consumable{

  int timer;

  private int timer;


  Potion(String name, int cost, int timer){
    super(name, cost);
    this.timer = timer;
  }

  Potion(String name, int cost) {
    super(name,cost);
  }

  Potion(String name, int cost){
    super(name, cost);
   }

}
