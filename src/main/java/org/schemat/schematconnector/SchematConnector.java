package org.schemat.schematconnector;

import org.bukkit.plugin.java.JavaPlugin;
import org.schemat.schematconnector.commands.Download;
import org.schemat.schematconnector.commands.Upload;
import com.sk89q.worldedit.WorldEdit;

public final class SchematConnector extends JavaPlugin {


    private static WorldEdit worldEditInstance;
    @Override
    public void onEnable() {
        registerCommands();
        //WorldEdit worldEdit = WorldEdit.getInstance();
        worldEditInstance = WorldEdit.getInstance();
    }

    public static WorldEdit getWorldEditInstance()
    {
        return worldEditInstance;
    }

    public void registerCommands()
    {
        this.getCommand("upload").setExecutor(new Upload());
        this.getCommand("download").setExecutor(new Download());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
