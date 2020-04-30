package me.wand.mutechat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;


public class MuteChat extends JavaPlugin implements Listener {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("mutechat")) {
            if (sender.hasPermission("mutechat.toggle") || (!(sender instanceof Player))) {
                chatEnabled = !chatEnabled;
                Bukkit.broadcastMessage((chatEnabled ? ChatColor.GREEN + "The chat has been unmuted by " + sender.getName() : ChatColor.RED + "The chat has been muted by " + sender.getName()));
            }else{
                sender.sendMessage(ChatColor.RED + "No permission");
            }
        }
        return true;
    }

    @Override
    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
    }
    @Override
    public void onDisable() {

    }

    public volatile boolean chatEnabled = true;

    @EventHandler
    public void playerChat(AsyncPlayerChatEvent event) {
        if (!chatEnabled && !event.getPlayer().hasPermission("mutechat.bypass")) {
            event.getPlayer().sendMessage(ChatColor.RED + "Chat is muted.");
            event.setCancelled(true);
        }
        if (event.getPlayer().hasPermission("mutechat.bypass")) {
            event.setCancelled(false);
        }
    }
}
