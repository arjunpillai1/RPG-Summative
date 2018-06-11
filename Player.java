/**
 * Player Class
 * Guy and Arjun
 * May 24 2018
 */

class Player extends CombatCharacter {
  private int exp;
  private World futureStep;
  private World previousStep = new Grass();
  private int weaponBoost = 0;
  private int armourBoost = 0;
  private boolean equippedWeapon = false;
  private boolean equippedArmour = false;
  
  Player(int health, int strength, int intelligence, int defence, int level, int accuracy, String name, int posX, int posY) {
    super(health, strength, intelligence, defence, level, accuracy, name, posX, posY);
  }
  
  public boolean getEquippedWeapon(){
    return equippedWeapon;
  }
  
  public void setEquippedWeapon(boolean state){
    equippedWeapon=state;
  }
  
  public boolean getEquippedArmour(){
    return equippedArmour;
  }
  
  public void setEquippedArmour(boolean state){
    equippedArmour=state;
  }
  
  public int getWeaponBoost(){
    return weaponBoost;
  }
  
  public void setWeaponBoost(int value){
    weaponBoost=value;
  }
  
  public int getArmourBoost(){
    return armourBoost;
  }
  
  public void setArmourBoost(int value){
    armourBoost=value;
  }
  
  public void unequipWeapon(Inventory bag, int placement){
    if (getEquippedWeapon() == true){
      for (int i=0; i<bag.amount(); i++){
        if ((i!=placement) && (bag.call(i) instanceof Weapon)){
          Weapon weapon =(Weapon)( bag.call(i));
          weapon.setEquipped(false);
        }
        setWeaponBoost(0);
        setEquippedWeapon(false);
      }
    }
  }  
  
  public void equipWeapon(Inventory bag, int placement){
    unequipWeapon(bag, placement);
    
    Weapon weapon =(Weapon)( bag.call(placement));
    weapon.setEquipped(true);
    setWeaponBoost(weapon.getAttackBoost());
    setEquippedWeapon(true);
    
  }
  
  public void unequipArmour(Inventory bag, int placement){
    if (getEquippedArmour() == true){
      for (int i=0; i<bag.amount(); i++){
        if ((i!=placement) && (bag.call(i) instanceof Armour)){
          Armour armour =(Armour)( bag.call(i));
          armour.setEquipped(false);
        }
        setArmourBoost(0);
        setEquippedArmour(false);
      }
    }
  }
  
  public void equipArmour(Inventory bag, int placement){
    unequipArmour(bag, placement);
    Armour armour =(Armour)( bag.call(placement));
    armour.setEquipped(true);
    setArmourBoost(armour.getDefenseBoost());
    setEquippedArmour(true);
  }
  
  
  public void useAttackPotion(Inventory bag, int placement){
    AttackPotion potion = (AttackPotion)(bag.call(placement));
    setWeaponBoost((weaponBoost + potion.getAttackBoost()));
    bag.tossItem(placement);
    for(int i=0; i<=(potion.getTimer()); i++){
      try{ Thread.sleep(100); }catch(Exception e) {};
    }
    setWeaponBoost((weaponBoost -= potion.getAttackBoost()));
  }
  
  public void useAttackPermanentPotion(Inventory bag, int placement){
    AttackPermanentPotion potion = (AttackPermanentPotion)(bag.call(placement));
    setStr(getStr() + potion.getAttackBoost());
    bag.tossItem(placement);
  }
  public void useDefensePermanentPotion(Inventory bag, int placement){
    DefensePermanentPotion potion = (DefensePermanentPotion)(bag.call(placement));
    setDef(getDef() + potion.getDefenseBoost());
    bag.tossItem(placement);
  }
  
  public void attack(CombatCharacter target) {
    //target.setHealth((target.getHealth())- ((int) (Math.floor(( ( ( (2 * getLvl() + 2) * 60 * ( (getStr() + getWeaponBoost())/target.getDef() ) ) / 50 ) + 2) * ( ( (int)(Math.random()*16) + 85) / 100)))));
    target.setHealth(-1);
  }
  
 public void move(World[][] world, int value) {
    if (value == 1) {

      if (world[getX() - 1][getY()] instanceof Floor) { //checks if spot is walkable
        futureStep = world[getX() - 1][getY()];
        world[getX() - 1][getY()] = world[getX()][getY()];
        world[getX()][getY()] = previousStep;
        previousStep = futureStep;
        setX(getX()-1);

      }
    } 
    else if (value == 4) {

      if (world[getX()][getY() + 1] instanceof Floor) {

        futureStep = world[getX()][getY() + 1];
        world[getX()][getY() + 1] = world[getX()][getY()];
        world[getX()][getY()] = previousStep;
        previousStep = futureStep;
        setY(getY()+1);

      }
    }  
    else if (value == 2) {

      if (world[getX() + 1][getY()] instanceof Floor) {
        futureStep = world[getX() + 1][getY()];
        world[getX() + 1][getY()] = world[getX()][getY()];
        world[getX()][getY()] = previousStep;
        previousStep = futureStep;
        setX(getX()+1);

      }
    }
    else if (value == 3) {

      if (world[getX()][getY() - 1] instanceof Floor) {
        futureStep = world[getX()][getY() - 1];
        world[getX()][getY() - 1] = world[getX()][getY()];
        world[getX()][getY()] = previousStep;
        previousStep = futureStep;
        setY(getY()-1);

      }
    }
  }
  
  public int getExp(){
    return exp;
  }
  
  public void setExp(int newExp){
    this.exp = newExp;
  }
  
  public void interact (Object interObj) {
//    if (interObj instanceof Chest) {
//    
//    } else if (interObj instanceof NPC) {
//      
//    }
  }
  
}