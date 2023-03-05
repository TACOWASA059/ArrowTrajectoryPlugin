package com.github.tacowasa059.arrow_data.commands;

import com.github.tacowasa059.arrow_data.ArrowTrajectoryPlugin;
import com.github.tacowasa059.arrow_data.listeners.PlayerItemHeld;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.jetbrains.annotations.NotNull;

public class OnOffCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player){
            Player player=(Player) sender;
            if(args.length==1){
                if(args[0].equalsIgnoreCase("start")){
                    ArrowTrajectoryPlugin.start=true;
                    player.sendMessage(ChatColor.GREEN+"矢の軌跡表示が有効化されました。");
                    for(Player player1: Bukkit.getOnlinePlayers()){
                        ArrowTrajectoryPlugin.plugin.getServer().getPluginManager().callEvent(new PlayerItemHeldEvent(player1,0,1));
                    }
                }
                else if(args[0].equalsIgnoreCase("stop")){
                    ArrowTrajectoryPlugin.start=false;
                    ArrowTrajectoryPlugin.plugin.cleartask();
                    PlayerItemHeld.clearTask();
                    player.sendMessage(ChatColor.GREEN+"矢の軌跡表示が無効化されました。");
                }
                else{
                    player.sendMessage(ChatColor.RED+"引数が間違っています");
                }
            }
            else{
                player.sendMessage(ChatColor.RED+"引数の個数が間違っています");
            }
        }
        return true;
    }
}
