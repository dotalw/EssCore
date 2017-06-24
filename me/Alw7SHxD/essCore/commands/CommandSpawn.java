package me.Alw7SHxD.essCore.commands;

import me.Alw7SHxD.essCore.API.EssAPI;
import me.Alw7SHxD.essCore.API.EssSpawnAPI;
import me.Alw7SHxD.essCore.Core;
import me.Alw7SHxD.essCore.messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * essCore was created by Alw7SHxD (C) 2017
 */
public class CommandSpawn implements CommandExecutor, messages {
    private Core core;
    private EssSpawnAPI spawnAPI;

    public CommandSpawn(Core core) {
        this.core = core;
        this.spawnAPI = new EssSpawnAPI(core);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!EssAPI.hasPermission(commandSender, "esscore.spawn")) return true;

        if(strings.length == 0){
            if(!(commandSender instanceof Player)){
                commandSender.sendMessage(m_not_player);
                return true;
            }

            if(spawnAPI.teleport((Player) commandSender)){
                commandSender.sendMessage(EssAPI.color(m_spawn_teleport));
            }else commandSender.sendMessage(EssAPI.color(m_spawn_fail));
        }else if(strings.length == 1){
            if(!EssAPI.hasPermission(commandSender, "esscore.spawn.target")) return true;

            Player target = EssAPI.getPlayer(core, commandSender, strings[0]);
            if (target == null) return true;

            if(spawnAPI.teleport(target)){
                target.sendMessage(EssAPI.color(String.format(m_spawn_target, EssAPI.getSenderDisplayName(core, commandSender))));
                if(commandSender != target) commandSender.sendMessage(EssAPI.color(String.format(m_spawn_sender, target.getName())));
            }else commandSender.sendMessage(EssAPI.color(m_spawn_fail));
        }
        return true;
    }
}
