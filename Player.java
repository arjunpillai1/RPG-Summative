/**
 * Player Class
 * @author Guy
 * @author Arjun Pillai
 * May 24 2018
 */

class Player extends CombatCharacter {
  //leveling
  private int exp;

  private int maxHealth;
  //movement
  private World futureStep;
  private World previousStep = new HouseFloor();
  //inventory
  private int weaponBoost = 0;
  private int armourBoost = 0;
  private boolean equippedWeapon = false;
  private boolean equippedArmour = false;

  /**
   * Player constructor
   * @param health, strength, intelligence, defence, level, accuracy, name, inital position x and y
   */
  Player(int health, int strength, int intelligence, int defence, int level, int accuracy, String name, int posX, int posY) {
    super(health, strength, intelligence, defence, level, accuracy, name, posX, posY);
    this.maxHealth = 50;
    
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
   * Sets the weapon bonus damage
   * @param the weapon damage to set
   */
  public void setWeaponBoost(int value){
    weaponBoost=value;
  }
  /**
   * Gets the armour boost value
   * @return the armounr boost value
   */
  public int getArmourBoost(){
    return armourBoost;
  }
  /**
   * Sets the armour boost value
   * @param the armourboost value to be set
   */
  public void setArmourBoost(int value){
    armourBoost=value;
  }
  /**
   * Levels up the player
   */
  public void levelUp() {
    setMaxHealth(getMaxHealth() + 25);
    setHealth(getMaxHealth());
  }
  /**
   * Gets the maximum health
   * @author Guy
   * @return the max health
   */
  public int getMaxHealth() {
    return maxHealth;
  }
  /**
   * Determines maximum health
   * @author guy
   * @param max health amount
   */
  void setMaxHealth(int amount) {
    this.maxHealth = amount;
  }
  /**
   * Unequips an item for player
   * @param inventory, index of item
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

  

  /**
   * Unequips armour
   * @param inventory, index
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

  /**
   * Method to equip armour
   * @param inventory, placement
   */
  public void equipArmour(Inventory bag, int placement){
    unequipArmour(bag, placement);
    Armour armour =(Armour)( bag.call(placement));
    armour.setEquipped(true);
    setArmourBoost(armour.getDefenseBoost());
    setEquippedArmour(true);
  }
  

  
  /**
   * Method to use a temporary attack potion
   * @param inventory, index
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

  /**
   * Method to use an attack potion
   * @param inventory and index
   */
  public void useAttackPermanentPotion(Inventory bag, int placement){
    AttackPermanentPotion potion = (AttackPermanentPotion)(bag.call(placement));
    setStr(getStr() + potion.getAttackBoost());
    bag.tossItem(placement);
  }

  /**
   * Method to use a defense potion for player
   * @param inventory, index
   */
  public void useDefensePermanentPotion(Inventory bag, int placement){
    DefensePermanentPotion potion = (DefensePermanentPotion)(bag.call(placement));
    setDef(getDef() + potion.getDefenseBoost());
    bag.tossItem(placement);
  }


  /**
   * Player equips an item based on placement
   * @param the bag, index in bag
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


  /**
   * Attacks a combat character
   * @param the object that the player attacks
   */
  public void attack(CombatCharacter target) {
    target.setHealth((target.getHealth())- (3 + ((int)(Math.floor(( ( ( (2 * getLvl() + 2) * 60 * ( (getStr() + getWeaponBoost())/target.getDef() ) ) / 50 ) + 2) * ( ( (int)(Math.random()*16) + 85) / 100))))));
    
    
  }
  /**
   * The interact method with the world for player
   * @param the world array, direction to move
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
  /**
   * Gets the player's XP
   * @return experience
   */
  public int getExp(){
    return exp;
  }
  /**
   * Returns the previous step taken
   * @return previous world object step
   */
  
  public World getPreviousStep(){
    return this.previousStep;
  }
  /**
   * Sets the new experience for player, level is determined here
   * @param the experience to be set
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
        levelUp();
      } else if(exp >= 20 && exp < 30) {
        setLvl(3);
        levelUp();
      } else if(exp >= 30 && exp < 40) {
        setLvl(4);
        levelUp();
      } else if(exp >= 40) {
        setLvl(5);
        levelUp();
      }
      
    } else if (exp < 151) {
      if (exp >= 51 && exp < 70) {
        setLvl(6);
        levelUp();
      } else if (exp >= 70 && exp < 90) {
        setLvl(7);
        levelUp();
      } else if (exp >= 90 && exp < 110) {
        setLvl(8);
        levelUp();
      } else if (exp >= 110 && exp < 130) {
        setLvl(9);
        levelUp();
      } else if (exp >= 130) {
        setLvl(10);
        levelUp();
      }
      
    } else if (exp < 351) {
      if (exp >= 151 && exp < 190) {
        setLvl(11);
        levelUp();
      } else if (exp >= 190 && exp < 230) {
        setLvl(12);
        levelUp();
      } else if (exp >= 230 && exp < 270) {
        setLvl(13);
        levelUp();
      } else if (exp >= 270 && exp < 310) {
        setLvl(14);
        levelUp();
      } else if (exp >= 310) {
        setLvl(15);
        levelUp();
      }
      
    } else if (exp < 751) {
      if (exp >= 351 && exp < 430) {
        setLvl(16);
        levelUp();
      } else if(exp >= 430 && exp < 510) {
        setLvl(17);
        levelUp();
      } else if (exp >= 510 && exp < 590) {
        setLvl(18);
        levelUp();
      } else if (exp >= 590 && exp < 670) {
        setLvl(19);
        levelUp();
      } else if (exp >= 670) {
        setLvl(20);
        levelUp();
      }
    } else if (exp > 715) {
      setLvl(69);
      levelUp();
    }

  }
  
  
}

