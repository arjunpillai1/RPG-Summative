abstract class NPC extends NonCombatCharacter{

    NPC(int health, String name){
        //super(whatever
        this.name=name;
    }

    public void giveQuest(){
        // construct the appropriate quest object and then somehow attribute it to the character
    }

    public void speak(){
        //print out the proper speech
    }
}