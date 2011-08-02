package com.zml.tripcraft;

import com.zml.tripcraft.packets.EntityEquipmentCodec;
import com.zml.tripcraft.packets.Packet5EntityEquipment;
import net.minecraft.server.Packet;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import net.glowstone.net.CodecLookupService;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class TripCraftPlugin extends JavaPlugin {
    private final Logger logger = Logger.getLogger("Minecraft.TripCraft");
    private PluginDescriptionFile desc;
    public void onDisable() {
        logger.log(Level.INFO, "{0} is now disabled.");
    }

    public void onEnable() {
        desc = getDescription();
        logger.log(Level.INFO, "{0} version {1} is now enabled.", new Object[] {getLogPrefix(), desc.getVersion()});
        if (getServer().getName().equals("Glowstone")) {
            replaceGlowstonePacket();
        } else if (getServer().getName().equals("CraftBukkit")) {
            replaceCraftBukkitPacket();
        } else {
            logger.log(Level.SEVERE, "{0}Unable to detect implementation of Bukkit. Cannot start trip");
        }
    }

    public String getLogPrefix() {
        return "[" + desc.getName() + "]: ";
    }

    private boolean replaceGlowstonePacket() {
        try {
            Method bindMethod = CodecLookupService.class.getDeclaredMethod("bind", Class.class);
            bindMethod.setAccessible(true);
            bindMethod.invoke(null, EntityEquipmentCodec.class);
            bindMethod.setAccessible(false);
            return true;
        } catch (NoSuchMethodException e) {
            logger.log(Level.SEVERE, "{0}Unable to replace glowstone packet", getLogPrefix());
            e.printStackTrace();
            return false;
        } catch (IllegalAccessException e) {
            logger.log(Level.SEVERE, "{0}Unable to replace glowstone packet", getLogPrefix());
            e.printStackTrace();
            return false;
        } catch (InvocationTargetException e) {
            logger.log(Level.SEVERE, "{0}Unable to replace glowstone packet", getLogPrefix());
            e.printStackTrace();
            return false;
        }
    }

    private boolean replaceCraftBukkitPacket() {
        Class<Packet> clazz = Packet.class;
        Map a;
        Map b;
        Set c;
        try {
            Field aField = clazz.getDeclaredField("a");
            aField.setAccessible(true);
            try {
                a = (Map)aField.get(null);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return false;
            }
            Field bField = clazz.getDeclaredField("b");
            bField.setAccessible(true);
            try {
                b = (Map)bField.get(null);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return false;
            }
            Field cField = clazz.getDeclaredField("c");
            cField.setAccessible(true);
            try {
                c = (Set)cField.get(null);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return false;
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return false;
        }
        a.put(5, Packet5EntityEquipment.class);
        b.remove(net.minecraft.server.Packet5EntityEquipment.class);
        b.put(Packet5EntityEquipment.class, 5);
        c.add(5);
        try {
            Field aField = clazz.getDeclaredField("a");
            aField.setAccessible(true);
            try {
                aField.set(null, a);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return false;
            }
            Field bField = clazz.getDeclaredField("b");
            bField.setAccessible(true);
            try {
                bField.set(null, b);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return false;
            }
            Field cField = clazz.getDeclaredField("c");
            cField.setAccessible(true);
            try {
                cField.set(null, c);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return false;
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


}
