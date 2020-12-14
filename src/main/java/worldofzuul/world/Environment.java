package worldofzuul.world;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import worldofzuul.util.MessageHelper;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * The type Environment.
 *
 * Manages time and weather.
 *
 */
public class Environment {
    /**
     * The Rain state.
     */
    private final BooleanProperty rainState = new SimpleBooleanProperty(false);
    /**
     * The Night state.
     */
    private final BooleanProperty nightState = new SimpleBooleanProperty(false);
    /**
     * The Calendar.
     */
    private final Calendar calendar = Calendar.getInstance();
    /**
     * The Random.
     *
     * Random generator for randomizing rain events.
     *
     */
    private final Random random = new Random();
    /**
     * The Rain ticks min.
     *
     * The minimum length of update ticks a burst of rain can last.
     *
     */
    private int rainTicksMin = 500;
    /**
     * The Rain ticks max.
     *
     * The maximum length of update ticks a burst of rain can last.
     *
     */
    private int rainTicksMax = 1500;
    /**
     * The Chance for rain.
     */
    private double chanceForRain = 0.00001;
    /**
     * The Day time start.
     *
     * The time at which a day is set to start, described in whole hours using a 24H clock.
     *
     */
    private int dayTimeStart = 6;
    /**
     * The Day time end.
     *
     * The time at which a day is set to end, described in whole hours using a 24H clock.
     *
     */
    private int dayTimeEnd = 18;
    /**
     * The Seconds to increment.
     *
     * Amount of seconds to increment {@link Environment#calendar} with per update tick.
     *
     */
    private int secondsToIncrement = 2;
    /**
     * The Rain water amount.
     *
     * Amount of water to add every update tick when it is raining.
     *
     */
    private float rainWaterAmount = 0.1f;
    /**
     * The Is printing enabled.
     *
     * If true enable logging of difference in nightstate and rainstate.
     *
     */
    private boolean isPrintingEnabled = false;

    /**
     * The Rain ticks.
     *
     * The amount of update ticks it has rained for.
     *
     */
    private int rainTicks = 0;

    /**
     * Instantiates a new Environment.
     */
    public Environment() {
        calendar.set(2020, Calendar.JANUARY, 1, 8, 1);
    }

    /**
     * Instantiates a new Environment.
     *
     * @param date the date
     */
    public Environment(Date date) {
        calendar.setTime(date);
    }

    /**
     * Update.
     *
     * Increments time and determines whether it should rain.
     *
     */
    public void update() {
        incrementTime();
        if (isRaining()) {
            rainTicks--;
        } else if (getRainState()) {
            if (isPrintingEnabled) {
                MessageHelper.Info.rainStopped();
            }
            setRainState(false);
        } else if (shouldItRain()) {
            startRaining();
        }
    }

    /**
     * Updates a GameObject.
     *
     * If it is raining and the parameter is an instance of {@link Field} then water is added to it.
     *
     * @param gameObject the game object
     */
    public void update(GameObject gameObject) {
        if (gameObject instanceof Field) {
            if (getRainState()) {
                ((Field) gameObject).addWater(rainWaterAmount);
            }
        }
    }

    /**
     * Is raining boolean.
     *
     * @return the boolean
     */
    @JsonIgnore
    public boolean isRaining() {
        return rainTicks > 0;
    }

    /**
     * Increment time of {@link Environment#calendar} by {@link Environment#secondsToIncrement}
     */
    private void incrementTime() {
        calendar.add(Calendar.SECOND, secondsToIncrement);
    }

    /**
     * Should it rain boolean.
     *
     * @return the boolean
     */
    private boolean shouldItRain() {
        return chanceForRain > random.nextDouble();
    }


    /**
     * Start raining.
     */
    private void startRaining() {
        rainTicks = random.nextInt((rainTicksMax - rainTicksMin) + 1) + rainTicksMin;
        setRainState(true);

        if (isPrintingEnabled) {
            MessageHelper.Info.rainStarted();
        }
    }


    /**
     * Gets rain ticks min.
     *
     * @return the rain ticks min
     */
    public int getRainTicksMin() {
        return rainTicksMin;
    }

    /**
     * Sets rain ticks min.
     *
     * @param rainTicksMin the rain ticks min
     */
    public void setRainTicksMin(int rainTicksMin) {
        this.rainTicksMin = rainTicksMin;
    }

    /**
     * Gets rain ticks max.
     *
     * @return the rain ticks max
     */
    public int getRainTicksMax() {
        return rainTicksMax;
    }

    /**
     * Sets rain ticks max.
     *
     * @param rainTicksMax the rain ticks max
     */
    public void setRainTicksMax(int rainTicksMax) {
        this.rainTicksMax = rainTicksMax;
    }

    /**
     * Gets chance for rain.
     *
     * @return the chance for rain
     */
    public double getChanceForRain() {
        return chanceForRain;
    }

    /**
     * Sets chance for rain.
     *
     * @param chanceForRain the chance for rain
     */
    public void setChanceForRain(double chanceForRain) {
        this.chanceForRain = chanceForRain;
    }

    /**
     * Gets day time start.
     *
     * @return the day time start
     */
    public int getDayTimeStart() {
        return dayTimeStart;
    }

    /**
     * Sets day time start.
     *
     * @param dayTimeStart the day time start
     */
    public void setDayTimeStart(int dayTimeStart) {
        this.dayTimeStart = dayTimeStart;
    }

    /**
     * Gets day time end.
     *
     * @return the day time end
     */
    public int getDayTimeEnd() {
        return dayTimeEnd;
    }

    /**
     * Sets day time end.
     *
     * @param dayTimeEnd the day time end
     */
    public void setDayTimeEnd(int dayTimeEnd) {
        this.dayTimeEnd = dayTimeEnd;
    }

    /**
     * Gets seconds to increment.
     *
     * @return the seconds to increment
     */
    public int getSecondsToIncrement() {
        return secondsToIncrement;
    }

    /**
     * Sets seconds to increment.
     *
     * @param secondsToIncrement the seconds to increment
     */
    public void setSecondsToIncrement(int secondsToIncrement) {
        this.secondsToIncrement = secondsToIncrement;
    }

    /**
     * Gets rain water amount.
     *
     * @return the rain water amount
     */
    public float getRainWaterAmount() {
        return rainWaterAmount;
    }

    /**
     * Sets rain water amount.
     *
     * @param rainWaterAmount the rain water amount
     */
    public void setRainWaterAmount(float rainWaterAmount) {
        this.rainWaterAmount = rainWaterAmount;
    }

    /**
     * Gets rain state.
     *
     * @return the rain state
     */
    @JsonIgnore
    public boolean getRainState() {
        return rainState.get();
    }

    /**
     * Sets rain state.
     *
     * @param rainState the rain state
     */
    @JsonIgnore
    public void setRainState(boolean rainState) {
        this.rainState.set(rainState);
    }

    /**
     * Rain state property boolean property.
     *
     * @return the boolean property
     */
    @JsonIgnore
    public BooleanProperty rainStateProperty() {
        return rainState;
    }

    /**
     * Gets night state.
     *
     * @return the night state
     */
    @JsonIgnore
    public boolean getNightState() {
        return nightState.get();
    }

    /**
     * Sets night state.
     *
     * @param nightState the night state
     */
    @JsonIgnore
    public void setNightState(boolean nightState) {
        this.nightState.set(nightState);
    }

    /**
     * Night state property boolean property.
     *
     * @return the boolean property
     */
    @JsonIgnore
    public BooleanProperty nightStateProperty() {
        return nightState;
    }

    /**
     * Gets calendar start.
     *
     * @return the calendar start
     */
    public Date getCalendarStart() {
        return calendar.getTime();
    }

    /**
     * Sets calendar start.
     *
     * @param date the date
     */
    public void setCalendarStart(Date date) {
        calendar.setTime(date);
    }

    /**
     * Gets calendar.
     *
     * @return the calendar
     */
    public Calendar getCalendar() {
        return calendar;
    }

    /**
     * Sets printing enabled.
     *
     * @param activeRoom the active room
     */
    public void setPrintingEnabled(boolean activeRoom) {
        isPrintingEnabled = activeRoom;
    }
}
