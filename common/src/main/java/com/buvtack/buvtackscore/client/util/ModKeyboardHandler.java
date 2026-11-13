package com.buvtack.buvtackscore.client.util;

import java.util.ArrayDeque;
import java.util.Queue;

public class ModKeyboardHandler {

    public static Queue<Character> enteredChars = new ArrayDeque<>();
    public static boolean enteredChar = false;

    public static Queue<Integer> pressedKeys = new ArrayDeque<>();
    public static boolean pressedKey;

    public static boolean shiftPressed;
    public static boolean controlPressed;
}
