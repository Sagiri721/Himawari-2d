package Engine.Utils;

import java.util.concurrent.TimeUnit;

public class Alarm{

    private static AlarmPack currentAlarm;
    private static boolean running = false;

    public static void runAlarm(AlarmPack alarm){

        if(running){

            System.out.println("[THREAD OCCUPIED] There is already an alarm waiting to be called");
            return;
        }

        running = true;
        currentAlarm = alarm;

        //Thread the alarm
        new Thread(new Runnable() {

            @Override
            public void run() {
                
                //Wait
                try {
                    TimeUnit.SECONDS.sleep((long) currentAlarm.getDelay());
                
                    if(currentAlarm == null) return;

                    currentAlarm.getAlarm().alarmRun();
                    if(currentAlarm.isRepeating()) { runAlarm(currentAlarm);}

                } catch (InterruptedException e) {
                    e.printStackTrace();
                    
                }finally{

                    running = false;
                }
            }
            
        }).start();
    }

    public static void cancelAlarm(){

        currentAlarm = null;
        running = false;
    }
}
