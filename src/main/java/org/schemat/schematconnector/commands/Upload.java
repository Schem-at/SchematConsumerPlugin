package org.schemat.schematconnector.commands;

import com.sk89q.worldedit.extent.clipboard.Clipboard;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.schemat.schematconnector.Utils.HttpUtil;
import org.schemat.schematconnector.Utils.WorldEditUtil;
import org.apache.http.entity.mime.MultipartEntityBuilder;


import java.io.IOException;
import java.util.ArrayList;

import static org.schemat.schematconnector.Utils.WorldEditUtil.clipboardToByteArray;


public class Upload implements CommandExecutor
{

    public static final String SCHEMAT_UPLOAD_URL_ENDPOINT = "/schematic";
    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Clipboard clipboard = WorldEditUtil.getClipboard((Player) sender);
        if (clipboard == null) {
            sender.sendMessage("No clipboard found");
            return false;
        }
        String authorUUID = ((Player) sender).getUniqueId().toString();
        if (args.length < 2) {
            sender.sendMessage("Usage: /upload <name> <description>");
            return false;
        }
        String name = args[0];
        if (name == null) {
            sender.sendMessage("No name provided");
            return false;
        }
        String description = args[1];
        if (description == null) {
            sender.sendMessage("No description provided");
            return false;
        }
        ArrayList<String> authors = new ArrayList<>();
        authors.add(authorUUID);
        if (args.length > 2) {
            for (int i = 2; i < args.length; i++) {
                authors.add(args[i]);
            }
        }
        String stringifiedAuthors = String.join(",", authors);
        sender.sendMessage("name: " + name + ", description: " + description + ", uuid: " + authorUUID);


        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addTextBody("name", name);
        builder.addTextBody("description", description);
        builder.addTextBody("authors", stringifiedAuthors);
        builder.addBinaryBody("schematic", clipboardToByteArray(clipboard), ContentType.DEFAULT_BINARY, "schematic");
        HttpEntity response = HttpUtil.sendMultiPartRequest(SCHEMAT_UPLOAD_URL_ENDPOINT, builder.build());
        sender.sendMessage("Response: ");
        try {
            sender.sendMessage(EntityUtils.toString(response));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
