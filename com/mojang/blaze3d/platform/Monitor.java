/*    */ package com.mojang.blaze3d.platform;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import com.mojang.blaze3d.systems.RenderSystem;
/*    */ import java.util.List;
/*    */ import java.util.Locale;
/*    */ import java.util.Optional;
/*    */ import org.lwjgl.glfw.GLFW;
/*    */ import org.lwjgl.glfw.GLFWVidMode;
/*    */ 
/*    */ public final class Monitor
/*    */ {
/*    */   private final long monitor;
/*    */   private final List<VideoMode> videoModes;
/*    */   private VideoMode currentMode;
/*    */   private int x;
/*    */   private int y;
/*    */   
/*    */   public Monitor(long $$0) {
/* 20 */     this.monitor = $$0;
/* 21 */     this.videoModes = Lists.newArrayList();
/* 22 */     refreshVideoModes();
/*    */   }
/*    */   
/*    */   public void refreshVideoModes() {
/* 26 */     RenderSystem.assertInInitPhase();
/* 27 */     this.videoModes.clear();
/* 28 */     GLFWVidMode.Buffer $$0 = GLFW.glfwGetVideoModes(this.monitor);
/* 29 */     for (int $$1 = $$0.limit() - 1; $$1 >= 0; $$1--) {
/* 30 */       $$0.position($$1);
/* 31 */       VideoMode $$2 = new VideoMode($$0);
/* 32 */       if ($$2.getRedBits() >= 8 && $$2.getGreenBits() >= 8 && $$2.getBlueBits() >= 8) {
/* 33 */         this.videoModes.add($$2);
/*    */       }
/*    */     } 
/* 36 */     int[] $$3 = new int[1];
/* 37 */     int[] $$4 = new int[1];
/* 38 */     GLFW.glfwGetMonitorPos(this.monitor, $$3, $$4);
/* 39 */     this.x = $$3[0];
/* 40 */     this.y = $$4[0];
/* 41 */     GLFWVidMode $$5 = GLFW.glfwGetVideoMode(this.monitor);
/* 42 */     this.currentMode = new VideoMode($$5);
/*    */   }
/*    */   
/*    */   public VideoMode getPreferredVidMode(Optional<VideoMode> $$0) {
/* 46 */     RenderSystem.assertInInitPhase();
/* 47 */     if ($$0.isPresent()) {
/* 48 */       VideoMode $$1 = $$0.get();
/*    */       
/* 50 */       for (VideoMode $$2 : this.videoModes) {
/* 51 */         if ($$2.equals($$1)) {
/* 52 */           return $$2;
/*    */         }
/*    */       } 
/*    */     } 
/* 56 */     return getCurrentMode();
/*    */   }
/*    */   
/*    */   public int getVideoModeIndex(VideoMode $$0) {
/* 60 */     RenderSystem.assertInInitPhase();
/* 61 */     return this.videoModes.indexOf($$0);
/*    */   }
/*    */   
/*    */   public VideoMode getCurrentMode() {
/* 65 */     return this.currentMode;
/*    */   }
/*    */   
/*    */   public int getX() {
/* 69 */     return this.x;
/*    */   }
/*    */   
/*    */   public int getY() {
/* 73 */     return this.y;
/*    */   }
/*    */   
/*    */   public VideoMode getMode(int $$0) {
/* 77 */     return this.videoModes.get($$0);
/*    */   }
/*    */   
/*    */   public int getModeCount() {
/* 81 */     return this.videoModes.size();
/*    */   }
/*    */   
/*    */   public long getMonitor() {
/* 85 */     return this.monitor;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 90 */     return String.format(Locale.ROOT, "Monitor[%s %sx%s %s]", new Object[] { Long.valueOf(this.monitor), Integer.valueOf(this.x), Integer.valueOf(this.y), this.currentMode });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\platform\Monitor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */