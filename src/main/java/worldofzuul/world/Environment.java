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
    private final BooleanProperty nightState = new SimpleBooleanProperty(false);

    private int rainTicksMin = 500;
    private int rainTicksMax = 1500;
    private double chanceForRain = 0.00001;
    private int dayTimeStart = 6;
    private int dayTimeEnd = 18;
    private int secondsToIncrement = 2;
    private float rainWaterAmount = 0.1f;
    private static final int tempOffsetMax = 30;
    private static final int tempOffsetMin = 3;
    private static final int uvOffsetMax = 15;
    private static final int uvOffsetMin = 1;

    private final Calendar calendar = Calendar.getInstance();
    private final Random random = new Random();
    private boolean isPrintingEnabled = false;

    private int rainTicks = 0;

    private double UVIndex = 2; //Need ui element
    private double temp = 30; // need ui element + Need ui element for field pH value
    private int dateCount = 1;

    private int uvSpread = 0;
    private int uvMean = 0;
    private int tempSpread = 0;
    private int tempMean = 0;


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
        } else if (getRainState()) {
            if (isPrintingEnabled) {
                MessageHelper.Info.rainStopped();
            }
            setRainState(false);
        } else if (shouldItRain()) {
            startRaining();
        }
        dayGen();
        if(nightStarted){
            UVIndex = 1;

        }

    }


    private void dayGen() {
        if (calendar.get(Calendar.DATE) == dateCount) {
            dateCount++;

            UVIndex = random.nextGaussian()*(uvOffsetMin+uvMean)+(uvOffsetMax+tempSpread);
            while (UVIndex == 0) {
                UVIndex =  random.nextGaussian()*(uvOffsetMin+uvSpread)+(uvOffsetMax+uvMean);
            }
            UVIndex = (int) UVIndex;
            System.out.println("UV: "+ UVIndex);

            temp = random.nextGaussian() *(tempOffsetMin+tempSpread)+(tempOffsetMax+tempMean);
            while (temp < 0){
                temp = random.nextGaussian() *(tempOffsetMin+tempSpread)+(tempOffsetMax+tempMean);
    }

    public void update(GameObject gameObject){
        if(gameObject instanceof Field){
            if(getRainState()){
                ((Field) gameObject).addWater(rainWaterAmount);
            }
            if(dayTime()){
                ((Field) gameObject).shineLight();      //#Svær kode annotering (Casting).
            }
            System.out.println("Temp: "+ temp);

        }
    }

    private void incrementOneSecond(){
        calendar.add(Calendar.SECOND, 12);
    }

    @JsonIgnore
    public boolean isRaining(){
        return rainTicks > 0;
    }

    public void update(GameObject gameObject, bool withDayGen){
        if(gameObject instanceof Field){

            if(isRaining()){
                ((Field) gameObject).addWater(rainWaterAmount);
                ((Field) gameObject).setpH((float) (((Field) gameObject).getpH()-(rainTicks*0.000001)) );
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

            }
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

        if(getNightState() && result){
            if(isPrintingEnabled) {
                MessageHelper.Info.nightEnded();
            }
            setNightState(false);
        } else if (!getNightState() && !result){
            if(isPrintingEnabled){
                MessageHelper.Info.nightStarted();
            }
            setNightState(true);
        }

        return currentHour >= dayTimeStart && currentHour <= dayTimeEnd;
    }



    private void startRaining() {
            rainTicks = random.nextInt((rainTicksMax - rainTicksMin) + 1) + rainTicksMin;
            setRainState(true);

            if(isPrintingEnabled){
                MessageHelper.Info.rainStarted();
            }
    }

    public int getUvSpread() {
        return uvSpread;
    }

    public void setUvSpread(int uvSpread) {
        this.uvSpread = uvSpread;
    }

    public int getUvMean() {
        return uvMean;
    }

    public void setUvMean(int uvMean) {
        this.uvMean = uvMean;
    }

    public int getTempSpread() {
        return tempSpread;
    }

    public void setTempSpread(int tempSpread) {
        this.tempSpread = tempSpread;
    }

    public int getTempMean() {
        return tempMean;
    }

    public void setTempMean(int tempMean) {
        this.tempMean = tempMean;

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
    @JsonIgnore
    public boolean getNightState() {
        return nightState.get();
    }
    @JsonIgnore
    public BooleanProperty nightStateProperty() {
        return nightState;
    }
    @JsonIgnore
    public void setNightState(boolean nightState) {
        this.nightState.set(nightState);
    }

    public Date getCalendarStart(){
        return calendar.getTime();
    }

    public void setCalendarStart(Date date){
        calendar.setTime(date);
    }

    public Calendar getCalendar(){
        return calendar;
    }

    public void setPrintingEnabled(boolean activeRoom) {
        isPrintingEnabled = activeRoom;
    }
}
