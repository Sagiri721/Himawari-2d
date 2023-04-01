package Engine.Utils;

public class AlarmPack {
    
    private AlarmFunctionality alarm;
    private float delay;
    private boolean repeating;

    public AlarmPack(AlarmFunctionality alarm, float delay){

        this.alarm = alarm;
        this.delay = delay;
        this.repeating = false;
    }

    public AlarmPack(AlarmFunctionality alarm, float delay, boolean repeating){

        this.alarm = alarm;
        this.delay = delay;
        this.repeating = repeating;
    }

    public AlarmFunctionality getAlarm(){ return alarm; }
    public float getDelay(){ return delay; }
    public boolean isRepeating(){ return repeating; }
}
