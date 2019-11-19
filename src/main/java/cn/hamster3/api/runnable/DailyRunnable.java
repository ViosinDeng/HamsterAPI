package cn.hamster3.api.runnable;

import org.bukkit.plugin.Plugin;

public class DailyRunnable {
    private Plugin plugin;
    private String name;
    private Runnable runnable;

    public DailyRunnable(Plugin plugin, String name, Runnable runnable) {
        this.plugin = plugin;
        this.name = name;
        this.runnable = runnable;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public String getName() {
        return name;
    }

    public Runnable getRunnable() {
        return runnable;
    }

    public void run() {

        try {
            runnable.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}