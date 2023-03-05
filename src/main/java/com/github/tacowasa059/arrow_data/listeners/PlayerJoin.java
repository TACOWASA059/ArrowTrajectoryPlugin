package com.github.tacowasa059.arrow_data.listeners;

import com.github.tacowasa059.arrow_data.ArrowTrajectoryPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        if(!ArrowTrajectoryPlugin.start)return;
        ArrowTrajectoryPlugin.plugin.getServer().getPluginManager().callEvent(new PlayerItemHeldEvent(event.getPlayer(),0,1));
    }
}
