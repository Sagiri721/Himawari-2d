package Engine.Utils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Clock is a game utility to create stuff like timers, countdowns, internal checks and other stuff
 */
public class Clock extends TimerTask {
   
    public static enum ClockType {

        TIMER,
        COUNTDOWN
    }

    public static enum ClockPrecision {

        SECONDS(1000),
        DECISECOND(100),
        CENTISECONDS(10),
        MILLISECONDS(1);

        private int delay = 0;
        private ClockPrecision(int delay){

            this.delay = delay;
        }

        private float getDelay(){ return delay; }
    }

    public static interface ClockEvent {

        public abstract void clockStarted();
        public abstract void clockStopped();
        public abstract void clockTicked();
        public abstract void countdownZero();
    }

    
    public long startValue = 0;
    private float currentValue = 0;
    
    private boolean running = false;
    
    private ClockEvent eventHandler = null;
    private ClockType type = ClockType.TIMER;
    private ClockPrecision precision = ClockPrecision.SECONDS;

    private Timer internalTimer = new Timer();

    public Clock(long startValue, ClockType type, ClockPrecision precision){

        this.startValue = startValue;
        this.currentValue = startValue;

        this.type = type;
        this.precision = precision;
    }

    public Clock(long startValue, ClockType type){

        this.startValue = startValue;
        this.currentValue = startValue;

        this.type = type;
    }

    public ClockType getClockType(){ return type; }
    public ClockPrecision getClockPrecision() { return precision; }
    public void setPrecision(ClockPrecision precision) {
        this.precision = precision;
    }

    public boolean isRunning(){ return running; }

    public double getCurrentClockValue(){ 
        return Math.round(currentValue * precision.getDelay()) / precision.getDelay(); 
    }

    public void addClockEventListener(ClockEvent eventListener){ this.eventHandler = eventListener; }

    public void startClock(){

        running = true;
        internalTimer.scheduleAtFixedRate(this, 0, (int) precision.getDelay());

        if(eventHandler != null) eventHandler.clockStarted();
    }

    public void stopClock(){

        running = false;
        internalTimer.cancel();

        if(eventHandler != null) eventHandler.clockStopped();
    }

    public void restartClock(){

        this.currentValue = this.startValue;
        startClock();
    }

    @Override
    public void run() {
        
        switch (type) {
            case TIMER:

                currentValue += (float) precision.getDelay() / 1000f;
                break;
            case COUNTDOWN:

                currentValue -= (float) precision.getDelay() / 1000f;
                if(currentValue <= 0 && eventHandler != null) {
                    
                    eventHandler.countdownZero();
                    stopClock();
                }
                break;
        }

        if(eventHandler != null) eventHandler.clockTicked();
    }
}
