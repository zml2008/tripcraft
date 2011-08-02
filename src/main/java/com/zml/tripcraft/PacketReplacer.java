package com.zml.tripcraft;

import net.glowstone.msg.EntityEquipmentMessage;
import net.glowstone.net.Session;
import net.minecraft.server.ItemStack;
import net.minecraft.server.NetServerHandler;
import net.minecraft.server.Packet5EntityEquipment;

/**
 * @author zml2008
 * @license AOL v.a3 <http://aol.nexua.org>
 */
public class PacketReplacer {
    public void sendGlowstoneTrip(Session sess) {
        if (sess.getPlayer().hasPermission("tripcraft.trip")) {
            sess.send(new EntityEquipmentMessage(sess.getPlayer().getEntityId(), 4, 0, 0));
        }
    }

    public void sendCraftBukitTrip(Packet5EntityEquipment packet, NetServerHandler handler) {
        if (handler.getPlayer().hasPermission("tripcraft.trip")) {
            handler.networkManager.queue(new Packet5EntityEquipment(handler.player.id, 0, new ItemStack(0, 0, 0)));
        }
    }
}
