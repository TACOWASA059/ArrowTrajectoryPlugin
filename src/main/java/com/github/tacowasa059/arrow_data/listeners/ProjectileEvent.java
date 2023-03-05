package com.github.tacowasa059.arrow_data.listeners;

import com.github.tacowasa059.arrow_data.ArrowTrajectoryPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.util.Vector;

public class ProjectileEvent implements Listener {
    @EventHandler
    public void onProjectileLaunchEvent(ProjectileLaunchEvent event){
        if(!ArrowTrajectoryPlugin.start)return;
        if (event.getEntityType() == EntityType.ARROW) {
            // 矢を取得する
            Arrow arrow = (Arrow) event.getEntity();
            if(arrow.getShooter() instanceof Player) {
                Player player = (Player)arrow.getShooter();
                Vector velocity=arrow.getVelocity();
                double a=0.05;
                double drag=0.01;
                Location base_loc=arrow.getLocation();
                //player.sendMessage(ChatColor.GREEN+"loc:"+player.getLocation()+" "+player.getEyeLocation()+" "+base_loc);
                /*
                int taskid=Bukkit.getScheduler().scheduleSyncRepeatingTask(ArrowPlugin.plugin, new Runnable(){
                    private int count=0;
                    private final double vy0=velocity.getY();
                    private final double vx0=velocity.getX();
                    private final double vz0=velocity.getZ();
                    private double y=base_loc.getY();
                    private double x=base_loc.getX();
                    private double z= base_loc.getZ();
                    @Override
                    public void run() {
                        double vy=vy0*Math.pow((1-drag),count)-a*(1-Math.pow((1-drag),count))/drag;
                        double vz=vz0*Math.pow((1-drag),count);
                        double vx=vx0*Math.pow((1-drag),count);
                        //player.sendMessage(ChatColor.GREEN+"data: t="+count+" ,vy="+arrow.getVelocity().getY()+" |v|="+vy);
                        player.sendMessage(ChatColor.GREEN+"x="+arrow.getLocation().getX()+",x="+x);
                        player.sendMessage(ChatColor.GREEN+"y="+arrow.getLocation().getY()+",y="+y);
                        player.sendMessage(ChatColor.GREEN+"z="+arrow.getLocation().getZ()+",z="+z);
                        count++;
                        y+=vy;
                        x+=vx;
                        z+=vz;
                    }
                }, 0L, 1L);//20Lが1秒毎

                 */

                //ArrowPlugin.arrow_list.put(arrow,taskid);
            }
        }
    }
    @EventHandler
    public void onProjectileHitEvent(ProjectileHitEvent event){
        if(!ArrowTrajectoryPlugin.start)return;
        // 矢が着弾したときの処理
        if (event.getEntityType() == EntityType.ARROW) {
            // 矢を取得する
            Arrow arrow = (Arrow) event.getEntity();
            // スケジュールタスクをキャンセルする
            if(ArrowTrajectoryPlugin.arrow_list.containsKey(arrow)){
                Integer taskid= ArrowTrajectoryPlugin.arrow_list.get(arrow);
                Bukkit.getScheduler().cancelTask(taskid);
                ArrowTrajectoryPlugin.arrow_list.remove(arrow);
            }
        }
    }

}
