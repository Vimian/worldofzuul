package worldofzuul.world;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import worldofzuul.util.MessageHelper;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class Environment {
    private final BooleanProperty rainState = new SimpleBooleanProperty(false);
    private int rainTicksMin = 500;
    private int rainTicksMax = 1500;
    private double chanceForRain = 0.00001; //TODO: Tweak this
    private int dayTimeStart = 6;
    private int dayTimeEnd = 18;
    private int secondsToIncrement = 2;
    private float rainWaterAmount = 0.1f;


    private final Calendar calendar = Calendar.getInstance();
    private final Random random = new Random();

    private int rainTicks = 0;
    private boolean rainStarted = false;
    private boolean nightStarted = false;


    public Environment(){
        calendar.set(2020, Calendar.JANUARY,1,8, 1);
    }
    public Environment(Date date){
        calendar.setTime(date);
    }

    public void update(){
        incrementTime();
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
    @JsonIgnore
    public boolean isRaining(){
        return rainTicks > 0;
    }

    private void incrementTime(){
        calendar.add(Calendar.SECOND, secondsToIncrement);
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


    public int getRainTicksMin() {
        return rainTicksMin;
    }

    public void setRainTicksMin(int rainTicksMin) {
        this.rainTicksMin = rainTicksMin;
    }

    public int getRainTicksMax() {
        return rainTicksMax;
    }

    public void setRainTicksMax(int rainTicksMax) {
        this.rainTicksMax = rainTicksMax;
    }

    public double getChanceForRain() {
        return chanceForRain;
    }

    public void setChanceForRain(double chanceForRain) {
        this.chanceForRain = chanceForRain;
    }

    public int getDayTimeStart() {
        return dayTimeStart;
    }

    public void setDayTimeStart(int dayTimeStart) {
        this.dayTimeStart = dayTimeStart;
    }

    public int getDayTimeEnd() {
        return dayTimeEnd;
    }

    public void setDayTimeEnd(int dayTimeEnd) {
        this.dayTimeEnd = dayTimeEnd;
    }

    public int getSecondsToIncrement() {
        return secondsToIncrement;
    }

    public void setSecondsToIncrement(int secondsToIncrement) {
        this.secondsToIncrement = secondsToIncrement;
    }

    public float getRainWaterAmount() {
        return rainWaterAmount;
    }

    public void setRainWaterAmount(float rainWaterAmount) {
        this.rainWaterAmount = rainWaterAmount;
    }

    @JsonIgnore
    public boolean getRainState() {
        return rainState.get();
    }

    @JsonIgnore
    public BooleanProperty rainStateProperty() {
        return rainState;
    }

    @JsonIgnore
    public void setRainState(boolean rainState) {
        this.rainState.set(rainState);
    }

    public Date getCalendarStart(){
        return calendar.getTime();
    }
    public void setCalendarStart(Date date){
        calendar.setTime(date);
    }
}
