/**
 * Player Class
 * @author Guy
 * @author Arjun Pillai
 * May 24 2018
 */

class Player extends CombatCharacter {
  private int exp;

  //variables to ensure characters do not leave an empty space
  private World futureStep;
  private World previousStep = new HouseFloor();

  //Equipment variables
  private int weaponBoost = 0;
  private int armourBoost = 0;
  private boolean equippedWeapon = false;
  private boolean equippedArmour = false;

  /**
  * Creates a player with certain stats
  */
  Player(int health, int strength, int intelligence, int defence, int level, int accuracy, String name, int posX, int posY) {
    super(health, strength, intelligence, defence, level, accuracy, name, posX, posY);
  }

  /**
  *checks if there has been a weapon equipped
  *@return true if the player is wielding a weapon
  */
  public boolean getEquippedWeapon(){
    return equippedWeapon;
  }
  
  /**
  *changes the state of whether the player is wielding a weapon
  *@param the state of weapon equip
  */
  public void setEquippedWeapon(boolean state){
    equippedWeapon=state;
  }
  
  /**
  *checks if there has been a armour equipped
  *@return true if the player is wielding a armour
  */
  public boolean getEquippedArmour(){
    return equippedArmour;
  }
  
  /**
  *changes the state of whether the player is wielding armour
  *@param the state of armour equip
  */
  public void setEquippedArmour(boolean state){
    equippedArmour=state;
  }
  
   /**
  * gets the boost the player receives from their weapon
  *@return the value of the weapon boost
  */
  public int getWeaponBoost(){
    return weaponBoost;
  }
  
  /**
  * sets the value of the weapon boost
  * @param the value that the weapon boost should be set to
  */
  public void setWeaponBoost(int value){
    weaponBoost=value;
  }
  
  /**
  * gets the boost the player receives from their armour
  *@return the value of the armour boost
  */
  public int getArmourBoost(){
    return armourBoost;
  }
  
  /**
  * sets the value of the armour boost
  * @param the value that the armour boost should be set to
  */
  public void setArmourBoost(int value){
    armourBoost=value;
  }
  
  /**
  *cause the weapon at that slot to stop affecting the character
  * @param bag the container that the weapon is stored within
  * @param placement the slot in which the weapon is stored
  */
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
  
  /**
  * equips a weapon at the necessary slot
  * @param 
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

  public void equip(Inventory bag, int placement){
    if (bag.call(placement) instanceof Weapon){
      equipWeapon(bag, placement);
    } else if (bag.call(placement) instanceof Armour){
      equipArmour(bag,placement);
    } else if (bag.call(placement) instanceof AttackPotion){
      useAttackPotion(bag, placement);
    } else if (bag.call(placement) instanceof AttackPermanentPotion){
      useAttackPermanentPotion(bag, placement);
    } else if (bag.call(placement) instanceof DefensePermanentPotion){
      useDefensePermanentPotion(bag, placement);
    }
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
  
  public World getFutureStep(){
    return this.futureStep;
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

