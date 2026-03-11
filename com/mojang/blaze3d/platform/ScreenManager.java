/*     */ package com.mojang.blaze3d.platform;
/*     */ 
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import javax.annotation.Nullable;
/*     */ import org.lwjgl.PointerBuffer;
/*     */ import org.lwjgl.glfw.GLFW;
/*     */ import org.lwjgl.glfw.GLFWMonitorCallback;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class ScreenManager
/*     */ {
/*  16 */   private static final Logger LOGGER = LogUtils.getLogger();
/*  17 */   private final Long2ObjectMap<Monitor> monitors = (Long2ObjectMap<Monitor>)new Long2ObjectOpenHashMap();
/*     */   private final MonitorCreator monitorCreator;
/*     */   
/*     */   public ScreenManager(MonitorCreator $$0) {
/*  21 */     RenderSystem.assertInInitPhase();
/*  22 */     this.monitorCreator = $$0;
/*  23 */     GLFW.glfwSetMonitorCallback(this::onMonitorChange);
/*  24 */     PointerBuffer $$1 = GLFW.glfwGetMonitors();
/*  25 */     if ($$1 != null) {
/*  26 */       for (int $$2 = 0; $$2 < $$1.limit(); $$2++) {
/*  27 */         long $$3 = $$1.get($$2);
/*  28 */         this.monitors.put($$3, $$0.createMonitor($$3));
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private void onMonitorChange(long $$0, int $$1) {
/*  34 */     RenderSystem.assertOnRenderThread();
/*  35 */     if ($$1 == 262145) {
/*  36 */       this.monitors.put($$0, this.monitorCreator.createMonitor($$0));
/*  37 */       LOGGER.debug("Monitor {} connected. Current monitors: {}", Long.valueOf($$0), this.monitors);
/*  38 */     } else if ($$1 == 262146) {
/*  39 */       this.monitors.remove($$0);
/*  40 */       LOGGER.debug("Monitor {} disconnected. Current monitors: {}", Long.valueOf($$0), this.monitors);
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Monitor getMonitor(long $$0) {
/*  46 */     RenderSystem.assertInInitPhase();
/*  47 */     return (Monitor)this.monitors.get($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Monitor findBestMonitor(Window $$0) {
/*  53 */     long $$1 = GLFW.glfwGetWindowMonitor($$0.getWindow());
/*  54 */     if ($$1 != 0L) {
/*  55 */       return getMonitor($$1);
/*     */     }
/*     */     
/*  58 */     int $$2 = $$0.getX();
/*  59 */     int $$3 = $$2 + $$0.getScreenWidth();
/*  60 */     int $$4 = $$0.getY();
/*  61 */     int $$5 = $$4 + $$0.getScreenHeight();
/*     */     
/*  63 */     int $$6 = -1;
/*  64 */     Monitor $$7 = null;
/*  65 */     long $$8 = GLFW.glfwGetPrimaryMonitor();
/*  66 */     LOGGER.debug("Selecting monitor - primary: {}, current monitors: {}", Long.valueOf($$8), this.monitors);
/*     */     
/*  68 */     for (ObjectIterator<Monitor> objectIterator = this.monitors.values().iterator(); objectIterator.hasNext(); ) { Monitor $$9 = objectIterator.next();
/*  69 */       int $$10 = $$9.getX();
/*  70 */       int $$11 = $$10 + $$9.getCurrentMode().getWidth();
/*  71 */       int $$12 = $$9.getY();
/*  72 */       int $$13 = $$12 + $$9.getCurrentMode().getHeight();
/*     */       
/*  74 */       int $$14 = clamp($$2, $$10, $$11);
/*  75 */       int $$15 = clamp($$3, $$10, $$11);
/*  76 */       int $$16 = clamp($$4, $$12, $$13);
/*  77 */       int $$17 = clamp($$5, $$12, $$13);
/*     */       
/*  79 */       int $$18 = Math.max(0, $$15 - $$14);
/*  80 */       int $$19 = Math.max(0, $$17 - $$16);
/*  81 */       int $$20 = $$18 * $$19;
/*  82 */       if ($$20 > $$6) {
/*  83 */         $$7 = $$9;
/*  84 */         $$6 = $$20; continue;
/*  85 */       }  if ($$20 == $$6 && $$8 == $$9.getMonitor()) {
/*  86 */         LOGGER.debug("Primary monitor {} is preferred to monitor {}", $$9, $$7);
/*  87 */         $$7 = $$9;
/*     */       }  }
/*     */     
/*  90 */     LOGGER.debug("Selected monitor: {}", $$7);
/*  91 */     return $$7;
/*     */   }
/*     */   
/*     */   public static int clamp(int $$0, int $$1, int $$2) {
/*  95 */     if ($$0 < $$1) {
/*  96 */       return $$1;
/*     */     }
/*  98 */     if ($$0 > $$2) {
/*  99 */       return $$2;
/*     */     }
/* 101 */     return $$0;
/*     */   }
/*     */   
/*     */   public void shutdown() {
/* 105 */     RenderSystem.assertOnRenderThread();
/* 106 */     GLFWMonitorCallback $$0 = GLFW.glfwSetMonitorCallback(null);
/* 107 */     if ($$0 != null)
/* 108 */       $$0.free(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\platform\ScreenManager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */