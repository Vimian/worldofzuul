package worldofzuul.world;

import worldofzuul.util.MessageHelper;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class Environment {
    private static final int rainTicksMin = 500;
    private static final int rainTicksMax = 1500;
    private static final double chanceForRain = 0.00001; //TODO: Tweak this
    private static final int dayTimeStart = 6;
    private static final int dayTimeEnd = 18;


    private final Calendar calendar = Calendar.getInstance();
    private final Random random = new Random();

    private int rainTicks = 0;
    private boolean rainStarted = false;
    private boolean nightStarted = false;
    private float rainWaterAmount = 0.1f;

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
    }



    public void update(GameObject gameObject){
        if(gameObject instanceof Field){
            if(isRaining()){
                ((Field) gameObject).addWater(rainWaterAmount);
            } else if(dayTime()){
                ((Field) gameObject).shineLight();      //#Svær kode annotering (Casting).
            }
        }
    }

    public boolean isRaining(){
        return rainTicks > 0;
    }

    private void incrementOneSecond(){
        calendar.add(Calendar.SECOND, 1);
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
