public class Item {

    public void addItem(String name){

        for (int i = 0; i < inventory.length; i++){
            if(inventory[i] == ""){
                inventory[i] = name;
                System.out.println("You have " + name + ".");
                return;
            }
        }
    }
    private static final String inventory[] = {"","",""};
}
