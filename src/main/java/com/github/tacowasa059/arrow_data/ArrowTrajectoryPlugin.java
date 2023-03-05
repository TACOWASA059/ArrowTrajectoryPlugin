package com.github.tacowasa059.arrow_data;

import com.github.tacowasa059.arrow_data.commands.OnOffCommand;
import com.github.tacowasa059.arrow_data.listeners.PlayerItemHeld;
import com.github.tacowasa059.arrow_data.listeners.PlayerJoin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class ArrowTrajectoryPlugin extends JavaPlugin {
    public static HashMap<Arrow, Integer> arrow_list=new HashMap<>();
    public static ArrowTrajectoryPlugin plugin;
    public static Boolean start=false;
    @Override
    public void onEnable() {
        this.plugin=this;
        getCommand("arrowtraj").setExecutor(new OnOffCommand());
        //getServer().getPluginManager().registerEvents(new ProjectileEvent(),this);
        getServer().getPluginManager().registerEvents(new PlayerItemHeld(),this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(),this);

    }

    @Override
    public void onDisable() {

    }
    public void cleartask(){
        for(Arrow arrow: arrow_list.keySet()){
            int taskID=arrow_list.get(arrow);
            Bukkit.getScheduler().cancelTask(taskID);
        }
        arrow_list.clear();
    }

}
