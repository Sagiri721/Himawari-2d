package Engine.Input;

import java.awt.event.*;
import java.awt.MouseInfo;

public class Input {

    public static int axisX = 0;
    public static int axisY = 0;

    public static int mouseX = 0;
    public static int mouseY = 0;

    public static char[] keyMap = { KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP, KeyEvent.VK_DOWN };

    protected static boolean[] mousePressed = new boolean[3];

    public static void updateMousePosition() {

        mouseX = MouseInfo.getPointerInfo().getLocation().x;
        mouseY = MouseInfo.getPointerInfo().getLocation().y;
    }

    public static boolean mousePressed(int button) {
        return mousePressed[button];
    }

    public static boolean isKeyPressed(Input.Keys key) {

        return KeyboardReader.keys[key.ordinal()];
    }

    public static boolean isKeyJustPressed(Input.Keys key) {

        boolean value = KeyboardReader.keys2[key.ordinal()];
        if (value) KeyboardReader.keys2[key.ordinal()] = false;
        return value;
    }

    public static enum MouseButtons{

        LEFT,
        MIDDLE,
        RIGHT
    }

    public static enum Keys {

        LEFT,
        RIGHT,
        UP,
        DOWN,
        A,
        B,
        C,
        D,
        E,
        F,
        G,
        H,
        I,
        J,
        K,
        L,
        M,
        N,
        O,
        P,
        Q,
        R,
        S,
        T,
        U,
        V,
        W,
        X,
        Y,
        Z,
        ZERO,
        ONE,
        TWO,
        THREE,
        FOUR,
        FIVE,
        SIX,
        SEVEN,
        EIGHT,
        NINE,
        TAB,
        LSHIFT,
        LCTRL,
        ALT,
        ALTGR,
        FN,
        ENTER,
        BACKSPACE,
        ESC,
        F1,
        F2,
        F3,
        F4,
        F5,
        F6,
        F7,
        F8,
        F9,
        F10,
        F11,
        F12
    }

}
