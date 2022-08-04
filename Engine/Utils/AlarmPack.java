package Engine.Utils;

public class AlarmPack {
    
    private AlarmFunctionality alarm;
    private float delay;

    public AlarmPack(AlarmFunctionality alarm, float delay){

        this.alarm = alarm;
        this.delay = delay;
    }

    public AlarmFunctionality getAlarm(){ return alarm; }
    public float getDelay(){ return delay; }
}
