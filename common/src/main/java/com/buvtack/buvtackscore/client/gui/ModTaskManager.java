package com.buvtack.buvtackscore.client.gui;

import java.util.*;

public class ModTaskManager<T extends ModAction> {

    public Map<String, ModTask<T>> tasks;
    public List<ModTask<T>> sortedTasks;

    public ModTaskManager() {
        tasks = new HashMap<>();
        sortedTasks = new ArrayList<>();
    }

    public void addTask(String name, ModTask<T> task) {
        tasks.put(name, task);
        sortedTasks.add(task);
        sortedTasks.sort(Comparator.comparingInt(ModTask::getPriority));
    }

    public ModTask<T> removeTask(String name) {
        ModTask<T> removed = tasks.remove(name);
        sortedTasks.remove(removed);
        return removed;
    }
}
