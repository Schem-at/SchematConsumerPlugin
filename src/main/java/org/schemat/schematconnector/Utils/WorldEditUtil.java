package org.schemat.schematconnector.Utils;

import com.sk89q.worldedit.EmptyClipboardException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extension.platform.Actor;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.BuiltInClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardWriter;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.session.SessionManager;
import org.bukkit.entity.Player;
import org.schemat.schematconnector.SchematConnector;

import java.io.ByteArrayOutputStream;

public class WorldEditUtil {


    public static WorldEdit getWorldEditInstance()
    {
        return SchematConnector.getWorldEditInstance();
    }

    public static SessionManager getSessionManager()
    {
        return getWorldEditInstance().getSessionManager();
    }

    public static LocalSession getLocalSession(Player player)
    {
        Actor actor = BukkitAdapter.adapt(player);
        return getSessionManager().get(actor);
    }

    public static ClipboardHolder getClipboardHolder(Player player)
    {
        try {
            return getLocalSession(player).getClipboard();
        } catch (EmptyClipboardException e) {
            return null;
        }
    }

    public static Clipboard getClipboard(Player player)
    {
        ClipboardHolder holder = getClipboardHolder(player);
        if (holder == null) {
            return null;
        }
        return holder.getClipboard();
    }

    public static ByteArrayOutputStream clipboardToStream(Clipboard clipboard)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try(ClipboardWriter writer = BuiltInClipboardFormat.SPONGE_SCHEMATIC.getWriter(outputStream)) {
            writer.write(clipboard);
        } catch (Exception e) {
            return null;
        }

        return outputStream;
    }

    public static byte[] clipboardToByteArray(Clipboard clipboard)
    {
        ByteArrayOutputStream outputStream = clipboardToStream(clipboard);
        if (outputStream == null) {
            return null;
        }
        return outputStream.toByteArray();
    }





}
