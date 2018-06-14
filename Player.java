/**
 * Player Class
 * Guy and Arjun
 * May 24 2018
 */

class Player extends CombatCharacter {
  private int exp;

  
  private World futureStep;
  private World previousStep = new HouseFloor();
  
  private int weaponBoost = 0;
  private int armourBoost = 0;
  private boolean equippedWeapon = false;
  private boolean equippedArmour = false;
  private int maxHealth;
  /*
   * 
   * 
   * 
   */
  Player(int health, int strength, int intelligence, int defence, int level, int accuracy, String name, int posX, int posY) {
    super(health, strength, intelligence, defence, level, accuracy, name, posX, posY);
    this.maxHealth = 50;
    
  }
  
  /*
   * 
   * 
   * 
   */
  public boolean getEquippedWeapon(){
    return equippedWeapon;
  }
  /*
   * 
   * 
   * 
   */
  public void setEquippedWeapon(boolean state){
    equippedWeapon=state;
  }
  /*
   * 
   * 
   * 
   */
  public boolean getEquippedArmour(){
    return equippedArmour;
  }
  /*
   * 
   * 
   * 
   */
  public void setEquippedArmour(boolean state){
    equippedArmour=state;
  }
  /*
   * 
   * 
   * 
   */
  public int getWeaponBoost(){
    return weaponBoost;
  }
  /*
   * 
   * 
   * 
   */
  public void setWeaponBoost(int value){
    weaponBoost=value;
  }
  /*
   * 
   * 
   * 
   */
  public int getArmourBoost(){
    return armourBoost;
  }
  /*
   * 
   * 
   * 
   */
  public void setArmourBoost(int value){
    armourBoost=value;
  }
  /*
   * 
   * 
   * 
   */
  public void levelUp () {
    setMaxHealth(getMaxHealth() + 25);
    setHealth(getMaxHealth());
  }
  /*
   * 
   * 
   * 
   */
  public int getMaxHealth() {
    return maxHealth;
  }
  /*
   * 
   * 
   * 
   */
  void setMaxHealth(int amount) {
    this.maxHealth = amount;
  }
  /*
   * 
   * 
   * 
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

  /*
   * 
   * 
   * 
   */

  public void equipWeapon(Inventory bag, int placement){
    unequipWeapon(bag, placement);
    
    Weapon weapon =(Weapon)( bag.call(placement));
    weapon.setEquipped(true);
    setWeaponBoost(weapon.getAttackBoost());
    setEquippedWeapon(true);
    
  }

  

  /*
   * 
   * 
   * 
   */

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

  /*
   * 
   * 
   * 
   */

  public void equipArmour(Inventory bag, int placement){
    unequipArmour(bag, placement);
    Armour armour =(Armour)( bag.call(placement));
    armour.setEquipped(true);
    setArmourBoost(armour.getDefenseBoost());
    setEquippedArmour(true);
  }
  

  
  /*
   * 
   * 
   * 
   */

  public void useAttackPotion(Inventory bag, int placement){
    AttackPotion potion = (AttackPotion)(bag.call(placement));
    setWeaponBoost((weaponBoost + potion.getAttackBoost()));
    bag.tossItem(placement);
    for(int i=0; i<=(potion.getTimer()); i++){
      try{ Thread.sleep(100); }catch(Exception e) {};
    }
    setWeaponBoost((weaponBoost -= potion.getAttackBoost()));
  }

  /*
   * 
   * 
   * 
   */

  public void useAttackPermanentPotion(Inventory bag, int placement){
    AttackPermanentPotion potion = (AttackPermanentPotion)(bag.call(placement));
    setStr(getStr() + potion.getAttackBoost());
    bag.tossItem(placement);
  }

  /*
   * 
   * 
   * 
   */

  public void useDefensePermanentPotion(Inventory bag, int placement){
    DefensePermanentPotion potion = (DefensePermanentPotion)(bag.call(placement));
    setDef(getDef() + potion.getDefenseBoost());
    bag.tossItem(placement);
  }


  /*
   * 
   * 
   * 
   */
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


  /*
   * 
   * 
   * 
   */
  public void attack(CombatCharacter target) {
    target.setHealth((target.getHealth())- (3 + ((int)(Math.floor(( ( ( (2 * getLvl() + 2) * 60 * ( (getStr() + getWeaponBoost())/target.getDef() ) ) / 50 ) + 2) * ( ( (int)(Math.random()*16) + 85) / 100))))));
    
    
  }
  /*
   * 
   * 
   * 
   */
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
  /*
   * 
   * 
   * 
   */
  public int getExp(){
    return exp;
  }
  /*
   * 
   * 
   * 
   */
  
  public World getFutureStep(){
    return this.futureStep;
  }
  /*
   * 
   * 
   * 
   */
  public void setExp(int newExp){
    this.exp = newExp;
    // normal levels (1-5)
    // poison levels (5-10)
    // frost levels (10-15)
    // fire levels (15-20)
    // end game (20-30)
    
    //levels 1-5
    if (exp < 51) {
      if (exp >= 10 && exp < 20) {
        setLvl(2);
      } else if(exp >= 20 && exp < 30) {
        setLvl(3);
      } else if(exp >= 30 && exp < 40) {
        setLvl(4);
      } else if(exp >= 40) {
        setLvl(5);
      }
      
    } else if (exp < 151) {
      if (exp >= 51 && exp < 70) {
        setLvl(6);
      } else if (exp >= 70 && exp < 90) {
        setLvl(7);
      } else if (exp >= 90 && exp < 110) {
        setLvl(8);
      } else if (exp >= 110 && exp < 130) {
        setLvl(9);
      } else if (exp >= 130) {
        setLvl(10);
      }
      
    } else if (exp < 351) {
      if (exp >= 151 && exp < 190) {
        setLvl(11);
      } else if (exp >= 190 && exp < 230) {
        setLvl(12);
      } else if (exp >= 230 && exp < 270) {
        setLvl(13);
      } else if (exp >= 270 && exp < 310) {
        setLvl(14);
      } else if (exp >= 310) {
        setLvl(15);
      }
      
    } else if (exp < 751) {
      if (exp >= 351 && exp < 430) {
        setLvl(16);
      } else if(exp >= 430 && exp < 510) {
        setLvl(17);
      } else if (exp >= 510 && exp < 590) {
        setLvl(18); 
      } else if (exp >= 590 && exp < 670) {
        setLvl(19);
      } else if (exp >= 670) {
        setLvl(20);
      }
    } else if (exp > 715) {
      setLvl(69);
    }

  }
  
  public void interact (Object interObj) {
//    if (interObj instanceof Chest) {
//    
//    } else if (interObj instanceof NPC) {
//      
//    }
  }
  
  
}

