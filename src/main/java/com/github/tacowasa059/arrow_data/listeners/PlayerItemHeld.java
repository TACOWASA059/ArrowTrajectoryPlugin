package com.github.tacowasa059.arrow_data.listeners;

import com.github.tacowasa059.arrow_data.ArrowTrajectoryPlugin;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class PlayerItemHeld implements Listener{
    private static HashMap<Player,Integer> list=new HashMap<>();
    @EventHandler
    public void onPlayerItemHeldEvent(PlayerItemHeldEvent event){
        if(!ArrowTrajectoryPlugin.start)return;
        Player player=event.getPlayer();
        Bukkit.getScheduler().runTaskLater(ArrowTrajectoryPlugin.plugin, new Runnable() {
            @Override
            public void run() {
                ItemStack item=player.getItemInHand();
                if(item.getType().equals(Material.BOW)){
                    if(list.containsKey(player))return;
                    int taskid=Bukkit.getScheduler().scheduleSyncRepeatingTask(ArrowTrajectoryPlugin.plugin, new Runnable() {
                        @Override
                        public void run() {
                            Vector velocity=player.getEyeLocation().getDirection().clone();//向きはgetLocation
                            velocity.normalize();
                            velocity.multiply(3.0);

                            Location base_loc=player.getEyeLocation().clone();
                            base_loc.add(0,-0.1,0);
                            double a=0.05;
                            double drag=0.01;
                            int count=0;
                            Location loc=base_loc.clone();
                            Boolean flag=false;
                            while(count<500){
                                Vector v=getVelocity(count,a,drag,velocity);
                                count++;
                                Vector perpendicular = player.getEyeLocation().getDirection().clone().setY(0.0).rotateAroundY(Math.PI/2.0).normalize();
                                double mul=0.2;
                                if(count>=2&&count<5){
                                    player.spawnParticle(Particle.FLAME,base_loc.clone().add(perpendicular.clone().multiply(mul)),10*count,0, 0, 0, 0);
                                    player.spawnParticle(Particle.FLAME,base_loc.clone().subtract(perpendicular.clone().multiply(mul)),10*count,0, 0, 0, 0);
                                }
                                else if(count>=5){
                                    player.spawnParticle(Particle.FLAME,base_loc.clone().add(perpendicular.clone().multiply(mul)),50,0, 0, 0, 0);
                                    player.spawnParticle(Particle.FLAME,base_loc.clone().subtract(perpendicular.clone().multiply(mul)),50,0, 0, 0, 0);
                                }
                                Block block;
                                for(int i=0;i<2*v.length();i++){
                                    loc = base_loc.clone().add(v.clone().normalize().multiply((double) (i+1)/2.0));
                                    block=loc.getBlock();
                                    if(!block.getType().equals(Material.AIR)){
                                        flag=true;
                                        player.sendActionBar(ChatColor.YELLOW+"予想命中地点 : "+ChatColor.AQUA+String.format("%.2f", loc.getX())+" "+String.format("%.2f", loc.getY())+" "+String.format("%.2f", loc.getZ()));
                                        break;
                                    }
                                }
                                if(flag)break;
                                base_loc.add(v);
                            }
                        }
                    },0L,20L);
                    list.put(player,taskid);
                }
                else{
                    if(list.containsKey(player)){
                        int taskid=list.get(player);
                        Bukkit.getScheduler().cancelTask(taskid);
                        list.remove(player);
                    }
                }
            }
        },5L);


    }
    private  Vector getVelocity(int count,double a,double drag,Vector velocity){
        final double vy0=velocity.getY();
        final double vx0=velocity.getX();
        final double vz0=velocity.getZ();
        double vy=vy0*Math.pow((1-drag),count)-a*(1-Math.pow((1-drag),count))/drag;
        double vz=vz0*Math.pow((1-drag),count);
        double vx=vx0*Math.pow((1-drag),count);
        return new Vector(vx,vy,vz);
    }
    public static void clearTask(){
        for(Player player: list.keySet()){
            int taskid=list.get(player);
            Bukkit.getScheduler().cancelTask(taskid);
        }
        list.clear();
    }
}
