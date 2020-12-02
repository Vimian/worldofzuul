package worldofzuul.item;

public class Plant extends Item {
    private GrowthStage state1 = GrowthStage.SEED;
    private GrowthStage state2 = GrowthStage.SPROUT;
    private GrowthStage state3 = GrowthStage.ADULT;
    private GrowthStage state4 = GrowthStage.RIPE;
    private float seedQuality = 1;
    private float waterNeeded = 1000;
    private float nutritionNeeded = 1000;
    private int growthTime = 1000;

    //private int day;
    //private int dayNotWatered;
    //private boolean watered;
    //private boolean dead;
    private int growTicks = 0;
    private int GrowthStageTime;

    public Plant(String name) {
        super(name);
        this.growthTime = 0;
    }
    public void crops(){
      switch(GrowthStageTime){
          case crops.CORN:
              for (int c = 0; c < 5; c++);
              this.GrowthStageTime = 3;
              this.waterNeeded = 700;
              this.nutritionNeeded = 600;
              break;
          case RICE:
              for (int c = 0; c < 5; c++);
              this.GrowthStageTime = 1;
              this.waterNeeded = 400;
              this.nutritionNeeded = 600;
              break;
          case CASHEW:
              for (int c = 0; c < 5; c++);
              this.GrowthStageTime = 4;
              this.waterNeeded = 600;
              this.nutritionNeeded = 900;
              break;
          case MANGO:
              for (int c = 0; c < 5; c++);
              this.GrowthStageTime = 4;
              this.waterNeeded = 600;
              this.nutritionNeeded = 1000;
              break;
          case COWPEA:
              for (int c = 0; c < 5; c++);
              this.GrowthStageTime = 2;
              this.waterNeeded = 500;
              this.nutritionNeeded = 500;
              break;
      }
    }

    public void grow(float water, float nutrition) {

        consumeNutrition(nutrition);
        consumeWater(water);

        if (readyForNextStage()) {
            advanceStage();
       }
        /*
        switch (growthTime) {
            case 1:
            case 2:
            case 3:
            case 4:
                readyForNextStage();
                break;
        }
         */
        growTicks++;
    }

    public boolean isRipe() {
        return waterNeeded <= 0 && nutritionNeeded <= 0 && growTicks >= growthTime;
    }


    private void consumeWater(float water) {
        waterNeeded = -water * seedQuality;
    }

    private void consumeNutrition(float nutrition) {
        nutritionNeeded = -nutrition * seedQuality;
    }


    private boolean readyForNextStage() {
        return false;
    }

    private void advanceStage() { }

    /*
    public void plusday(){
    if(watered){
    dayNotWatered = 0;
    this.day++;
    }
    if(!watered && )
}
    */

}
