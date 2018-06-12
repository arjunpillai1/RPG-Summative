class Chest extends World{
  private Item[] chestItems;
  Chest(Item[] chestItems){ 
    this.chestItems = chestItems;
  }
  
  public Item[] getChestItems() {
    return chestItems;
  }
  
  public Item getChestItem(int index) {
    return chestItems[index];
  }
}
