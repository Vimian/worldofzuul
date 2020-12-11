package worldofzuul.world;

import worldofzuul.util.MessageHelper;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class Environment {
    private static final int rainTicksMin = 500;
    private static final int rainTicksMax = 1500;
    private static final double chanceForRain = 0.0001; //TODO: Tweak this
    private static final int dayTimeStart = 6;
    private static final int dayTimeEnd = 18;


    private final Calendar calendar = Calendar.getInstance();
    private final Random random = new Random();

    private int rainTicks = 0;
    private boolean rainStarted = false;
    private boolean nightStarted = false;
    private float rainWaterAmount = 0.1f;

    private double UVIndex = 2; //Need ui element
    private double temp = 30; // need ui element + Need ui element for field pH value
    private int dateCount = 1;

    public Environment(){
        calendar.set(2020, Calendar.JANUARY,1,8, 1);
    }
    public Environment(Date date){
        calendar.setTime(date);
    }

    public void update(){
        incrementOneSecond();
        if(isRaining()){
            rainTicks--;
        } else if (rainStarted) {
            MessageHelper.Info.rainStopped();
            rainStarted = false;
        } else if (shouldItRain()) {
            startRaining();
        }
        dayGen(0,0,0,0); //chance args per room
        if(nightStarted){
            UVIndex = 1;

        }

    }


    private void dayGen(int UVspread, int UVMean,int tempSpread, int tempMean) { //parametre indstilles per rum
        if (calendar.get(Calendar.DATE) == dateCount) {
            dateCount++;

            UVIndex = random.nextGaussian()*(1+UVspread)+(15+tempSpread);
            while (UVIndex == 0) {
                UVIndex =  random.nextGaussian()*(1+UVspread)+(15+UVMean);
            }
            UVIndex = (int) UVIndex;
            System.out.println("UV: "+ UVIndex);

            temp = random.nextGaussian() *(3+tempSpread)+(30+tempMean);
            while (temp < 0){
                temp = random.nextGaussian() *(3+tempSpread)+(30+tempMean);
            }
            System.out.println("Temp: "+ temp);

        }
    }

    private void incrementOneSecond(){
        calendar.add(Calendar.SECOND, 1);
    }

    public boolean isRaining(){
        return rainTicks > 0;
    }

    public void update(GameObject gameObject){
        if(gameObject instanceof Field){

            if(isRaining()){
                ((Field) gameObject).addWater(rainWaterAmount);
                ((Field) gameObject).setpH((float) (((Field) gameObject).getpH()-(rainTicks*0.000001)) );

                System.out.println("ph: " + ((Field) gameObject).getpH());
            }

            //deplete field of water regardless of plant planted during daytime
            if(dayTime()){
                //depletion rate UP
            } else if ( !dayTime() ){
                //depletion rateDOWN
            }

            if(((Field) gameObject).hasPlant() && !((Field) gameObject).getPlant().isRipe()) {

                //ph diff = growTime increase
                ((Field) gameObject).growTimeModify( Math.abs( ((Field) gameObject).getpH() - ((Field) gameObject).getPlant().getPhPref() ) , false);
                //temp diff = growTime increase
                ((Field) gameObject).growTimeModify(Math.abs(this.temp - ((Field) gameObject).getPlant().getTempPref()),false);
               //UV intensity = growTime decrease
                ((Field) gameObject).growTimeModify(UVIndex, true);

                System.out.println("growTime:" + ((Field) gameObject).getPlant().getGrowthTime());

            }
        }
    }



    private boolean shouldItRain(){
        return chanceForRain > random.nextDouble();
    }

    private boolean dayTime(){
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        boolean result = currentHour >= dayTimeStart && currentHour <= dayTimeEnd;

        if(nightStarted && result){
            MessageHelper.Info.nightEnded();
            nightStarted = false;
        } else if (!nightStarted && !result){
            MessageHelper.Info.nightStarted();
            nightStarted = true;
        }

        return currentHour >= dayTimeStart && currentHour <= dayTimeEnd;
    }



    private void startRaining() {
            rainTicks = random.nextInt((rainTicksMax - rainTicksMin) + 1) + rainTicksMin;
            rainStarted = true;
            MessageHelper.Info.rainStarted();


    }


}
