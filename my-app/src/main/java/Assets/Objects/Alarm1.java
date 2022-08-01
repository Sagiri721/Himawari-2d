package Assets.Objects;

import Engine.Entity.Object;
import Engine.Utils.*;

public class Alarm1 implements AlarmFunctionality{

    @Override
    public void alarmRun() {
        
        Object.DestroyObject(Object.FindObject("Wall"));
    }
    
}
