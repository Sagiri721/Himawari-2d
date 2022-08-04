package Engine.Input;

import java.awt.event.KeyListener;

import Engine.Input.Input.Keys;

import java.awt.event.*;

public class KeyboardReader implements KeyListener {

    private static char[] keyMap = { 'a', 'd', 'w', 's' };

    protected static boolean[] keys = new boolean[61];

    public static void mapAxis(Keys LEFT, Keys RIGHT, Keys UP, Keys DOWN) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

        // Deal with axis X
        if (e.getKeyChar() == keyMap[0]) {
            Input.axisX = -1;
        } else if (e.getKeyChar() == keyMap[1]) {
            Input.axisX = 1;
        }

        // Deal with axis Y
        if (e.getKeyChar() == keyMap[2]) {
            Input.axisY = -1;
        } else if (e.getKeyChar() == keyMap[3]) {
            Input.axisY = 1;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            keys[0] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            keys[1] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            keys[2] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            keys[3] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            keys[4] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_B) {
            keys[5] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_C) {
            keys[6] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            keys[7] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_E) {
            keys[8] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_F) {
            keys[9] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_G) {
            keys[10] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_H) {
            keys[11] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_I) {
            keys[12] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_J) {
            keys[13] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_K) {
            keys[14] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_L) {
            keys[15] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_M) {
            keys[16] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_N) {
            keys[17] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_O) {
            keys[18] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_P) {
            keys[19] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_Q) {
            keys[20] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_R) {
            keys[21] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            keys[22] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_T) {
            keys[23] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_U) {
            keys[24] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_V) {
            keys[25] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_W) {
            keys[26] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_X) {
            keys[27] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_Y) {
            keys[28] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_Z) {
            keys[29] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_0) {
            keys[30] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_1) {
            keys[31] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_2) {
            keys[32] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_3) {
            keys[33] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_4) {
            keys[34] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_5) {
            keys[35] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_6) {
            keys[36] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_7) {
            keys[37] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_8) {
            keys[38] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_9) {
            keys[39] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_TAB) {
            keys[40] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            keys[41] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
            keys[42] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_ALT) {
            keys[43] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_ALT_GRAPH) {
            keys[44] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_FINAL) {
            keys[45] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            keys[46] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            keys[47] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            keys[48] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_F1) {
            keys[49] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_F2) {
            keys[50] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_F3) {
            keys[51] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_F4) {
            keys[52] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_F5) {
            keys[53] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_F6) {
            keys[54] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_F7) {
            keys[55] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_F8) {
            keys[56] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_F9) {
            keys[57] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_F10) {
            keys[58] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_F11) {
            keys[59] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_F12) {
            keys[60] = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        // Set axis as zero
        if (e.getKeyChar() == keyMap[0] || e.getKeyChar() == keyMap[1])
            Input.axisX = 0;

        if (e.getKeyChar() == keyMap[2] || e.getKeyChar() == keyMap[3])
            Input.axisY = 0;

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            keys[0] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            keys[1] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            keys[2] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            keys[3] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            keys[4] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_B) {
            keys[5] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_C) {
            keys[6] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            keys[7] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_E) {
            keys[8] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_F) {
            keys[9] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_G) {
            keys[10] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_H) {
            keys[11] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_I) {
            keys[12] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_J) {
            keys[13] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_K) {
            keys[14] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_L) {
            keys[15] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_M) {
            keys[16] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_N) {
            keys[17] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_O) {
            keys[18] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_P) {
            keys[19] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_Q) {
            keys[20] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_R) {
            keys[21] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            keys[22] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_T) {
            keys[23] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_U) {
            keys[24] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_V) {
            keys[25] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_W) {
            keys[26] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_X) {
            keys[27] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_Y) {
            keys[28] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_Z) {
            keys[29] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_0) {
            keys[30] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_1) {
            keys[31] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_2) {
            keys[32] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_3) {
            keys[33] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_4) {
            keys[34] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_5) {
            keys[35] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_6) {
            keys[36] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_7) {
            keys[37] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_8) {
            keys[38] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_9) {
            keys[39] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_TAB) {
            keys[40] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            keys[41] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
            keys[42] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_ALT) {
            keys[43] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_ALT_GRAPH) {
            keys[44] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_FINAL) {
            keys[45] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            keys[46] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            keys[47] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            keys[48] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_F1) {
            keys[49] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_F2) {
            keys[50] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_F3) {
            keys[51] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_F4) {
            keys[52] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_F5) {
            keys[53] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_F6) {
            keys[54] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_F7) {
            keys[55] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_F8) {
            keys[56] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_F9) {
            keys[57] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_F10) {
            keys[58] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_F11) {
            keys[59] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_F12) {
            keys[60] = false;
        }
    }

}
