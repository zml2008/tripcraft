package com.zml.tripcraft.packets;

import net.minecraft.server.ItemStack;
import net.minecraft.server.NetHandler;
import net.minecraft.server.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet5EntityEquipment extends Packet {

   public int a;
   public int b;
   public int c;
   public int d;


   public Packet5EntityEquipment() {}

   public Packet5EntityEquipment(int var1, int var2, ItemStack var3) {
      this.a = var1;
      this.b = var2;
      if(var3 == null) {
         this.c = -1;
         this.d = 0;
      } else {
         this.c = var3.id;
         this.d = var3.getData();
      }

   }

   public void a(DataInputStream var1) {
      try {
         this.a = var1.readInt();
         this.b = var1.readShort();
         this.c = var1.readShort();
         this.d = var1.readShort();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   public void a(DataOutputStream var1) {
      try {
         var1.writeInt(this.a);
         var1.writeShort(this.b);
         var1.writeShort(0);
         var1.writeShort(this.d);
      } catch (IOException e) {
          e.printStackTrace();
      }
   }

   public void a(NetHandler var1) {
      var1.a(this);
   }

   public int a() {
      return 8;
   }
}
