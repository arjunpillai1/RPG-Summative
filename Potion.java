abstract class Potion extends Consumable{
<<<<<<< HEAD
  int timer;
=======
  private int timer;
>>>>>>> 63016944fc690d4323d2441c15aa1840ba01746c

  Potion(String name, int cost, int timer){
    super(name, cost);
    this.timer = timer;
  }
<<<<<<< HEAD
  Potion(String name, int cost) {
    super(name,cost);
  }
=======
  
  Potion(String name, int cost){
    super(name, cost);
   }
>>>>>>> 63016944fc690d4323d2441c15aa1840ba01746c
}