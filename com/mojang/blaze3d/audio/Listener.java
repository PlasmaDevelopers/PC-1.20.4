/*    */ package com.mojang.blaze3d.audio;
/*    */ 
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ import org.lwjgl.openal.AL10;
/*    */ 
/*    */ public class Listener {
/*  7 */   private float gain = 1.0F;
/*  8 */   private ListenerTransform transform = ListenerTransform.INITIAL;
/*    */   
/*    */   public void setTransform(ListenerTransform $$0) {
/* 11 */     this.transform = $$0;
/* 12 */     Vec3 $$1 = $$0.position();
/* 13 */     Vec3 $$2 = $$0.forward();
/* 14 */     Vec3 $$3 = $$0.up();
/* 15 */     AL10.alListener3f(4100, (float)$$1.x, (float)$$1.y, (float)$$1.z);
/* 16 */     AL10.alListenerfv(4111, new float[] { (float)$$2.x, (float)$$2.y, (float)$$2.z, (float)$$3.x(), (float)$$3.y(), (float)$$3.z() });
/*    */   }
/*    */   
/*    */   public void setGain(float $$0) {
/* 20 */     AL10.alListenerf(4106, $$0);
/* 21 */     this.gain = $$0;
/*    */   }
/*    */   
/*    */   public float getGain() {
/* 25 */     return this.gain;
/*    */   }
/*    */   
/*    */   public void reset() {
/* 29 */     setTransform(ListenerTransform.INITIAL);
/*    */   }
/*    */   
/*    */   public ListenerTransform getTransform() {
/* 33 */     return this.transform;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\audio\Listener.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */