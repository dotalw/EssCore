package me.Alw7SHxD.essCore.commands;

import me.Alw7SHxD.essCore.API.EssAPI;
import me.Alw7SHxD.essCore.API.EssHomesAPI;
import me.Alw7SHxD.essCore.Core;
import me.Alw7SHxD.essCore.messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * essCore was created by Alw7SHxD (C) 2017
 */
public class CommandDelHome implements CommandExecutor, messages {
    private Core core;

    public CommandDelHome(Core core) {
        this.core = core;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(m_not_player);
            return true;
        }

        if (!EssAPI.hasPermission(commandSender, "esscore.delhome")) return true;
        if (strings.length != 1) {
            commandSender.sendMessage(EssAPI.color(String.format(m_syntax_error_c, s + " &9<name>")));
            return true;
        }

        EssHomesAPI homesAPI = new EssHomesAPI((Player) commandSender);
        if (homesAPI.remove(strings[0].replace(".", "-").toLowerCase()))
            commandSender.sendMessage(EssAPI.color(String.format(m_delhome_success, strings[0].replace(".", "-").toLowerCase())));
        else commandSender.sendMessage(EssAPI.color(m_delhome_failed));
        return true;
    }
}