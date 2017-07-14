package me.Alw7SHxD.essCore;

import me.Alw7SHxD.essCore.util.EssEconomy;
import me.Alw7SHxD.essCore.util.hooks.PlaceholderApiHook;
import me.Alw7SHxD.essCore.util.updaters.UpdateChecker;
import me.Alw7SHxD.essCore.commands.RegisterCommands;
import me.Alw7SHxD.essCore.listeners.RegisterListeners;
import me.Alw7SHxD.essCore.util.hooks.VaultHook;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * (C) Copyright 2017 Alw7SHxD.
 *
 * essCore is licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class Core extends JavaPlugin {
    private UpdateChecker updateChecker = new UpdateChecker(this);
    public boolean usingPlaceholderAPI = false;
    public boolean hookedWithVault = false;
    public lists lists;
    private EssEconomy essEconomy;
    private VaultHook vaultHook;
    private Runnables runnables;

    public void onEnable() {
        if (!getDataFolder().exists()) getDataFolder().mkdir();
        saveDefaultConfig();

        if (getConfig().getDouble("essCore") != 7)
            getLogger().info("Your configuration file is outdated, please remove your old config.yml file.");

        updateChecker.check(this, getDescription().getVersion());
        this.runnables = new Runnables(this);
        this.runnables.asyncOneSecond();

        if(getServer().getPluginManager().isPluginEnabled("Vault")) {
            this.essEconomy = new EssEconomy(this);
            this.vaultHook = new VaultHook(this);
            vaultHook.hook();
            this.hookedWithVault = true;
            this.runnables.asyncFiveMinutes();
        }

        if (getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new PlaceholderApiHook(this).hook();
            this.usingPlaceholderAPI = true;
            getLogger().info("detected PlaceholderAPI.");
        }

        this.lists = new lists(this);
        this.lists.startup();

        new RegisterListeners(this);
        new RegisterCommands(this);
    }

    public void onDisable() {
        if(hookedWithVault)
            vaultHook.unHook();
        for(Player player: getServer().getOnlinePlayers())
            player.kickPlayer("Server is either reloading or shutting down.");
        this.runnables.getAsyncFiveMinutes().cancel();
        getLogger().info("essCore v" + getDescription().getVersion() + " has been disabled.");
    }

    public EssEconomy getEssEconomy() {
        return essEconomy;
    }
}
