package com.buvtack.buvtackscore.client.gui;

public class ModTask<T extends ModAction> implements Comparable<ModTask<T>> {

    public int priority;
    public T action;

    public ModTask(int priority, T action) {
        this.priority = priority;
        this.action = action;
    }

    public ModTask(T action) {
        priority = 0;
        this.action = action;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public int compareTo(ModTask other) {
        return Integer.compare(priority, other.priority);
    }
}
