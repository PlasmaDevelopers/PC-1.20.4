/*     */ package com.mojang.blaze3d.platform;
/*     */ import com.google.common.collect.EvictingQueue;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.util.List;
/*     */ import java.util.Queue;
/*     */ import javax.annotation.Nullable;
/*     */ import org.lwjgl.opengl.ARBDebugOutput;
/*     */ import org.lwjgl.opengl.GL;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.opengl.GLCapabilities;
/*     */ import org.lwjgl.opengl.GLDebugMessageARBCallback;
/*     */ import org.lwjgl.opengl.GLDebugMessageARBCallbackI;
/*     */ import org.lwjgl.opengl.GLDebugMessageCallback;
/*     */ import org.lwjgl.opengl.GLDebugMessageCallbackI;
/*     */ import org.lwjgl.opengl.KHRDebug;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class GlDebug {
/*  22 */   private static final Logger LOGGER = LogUtils.getLogger(); private static final int CIRCULAR_LOG_SIZE = 10;
/*     */   
/*     */   private static String printUnknownToken(int $$0) {
/*  25 */     return "Unknown (0x" + Integer.toHexString($$0).toUpperCase() + ")";
/*     */   }
/*     */   
/*     */   public static String sourceToString(int $$0) {
/*  29 */     switch ($$0) {
/*     */       case 33350:
/*  31 */         return "API";
/*     */       case 33351:
/*  33 */         return "WINDOW SYSTEM";
/*     */       case 33352:
/*  35 */         return "SHADER COMPILER";
/*     */       case 33353:
/*  37 */         return "THIRD PARTY";
/*     */       case 33354:
/*  39 */         return "APPLICATION";
/*     */       case 33355:
/*  41 */         return "OTHER";
/*     */     } 
/*  43 */     return printUnknownToken($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String typeToString(int $$0) {
/*  48 */     switch ($$0) {
/*     */       case 33356:
/*  50 */         return "ERROR";
/*     */       case 33357:
/*  52 */         return "DEPRECATED BEHAVIOR";
/*     */       case 33358:
/*  54 */         return "UNDEFINED BEHAVIOR";
/*     */       case 33359:
/*  56 */         return "PORTABILITY";
/*     */       case 33360:
/*  58 */         return "PERFORMANCE";
/*     */       case 33361:
/*  60 */         return "OTHER";
/*     */       case 33384:
/*  62 */         return "MARKER";
/*     */     } 
/*  64 */     return printUnknownToken($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String severityToString(int $$0) {
/*  69 */     switch ($$0) {
/*     */       case 37190:
/*  71 */         return "HIGH";
/*     */       case 37191:
/*  73 */         return "MEDIUM";
/*     */       case 37192:
/*  75 */         return "LOW";
/*     */       case 33387:
/*  77 */         return "NOTIFICATION";
/*     */     } 
/*  79 */     return printUnknownToken($$0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  84 */   private static final Queue<LogEntry> MESSAGE_BUFFER = (Queue<LogEntry>)EvictingQueue.create(10); @Nullable
/*     */   private static volatile LogEntry lastEntry;
/*     */   
/*     */   private static void printDebugLog(int $$0, int $$1, int $$2, int $$3, int $$4, long $$5, long $$6) {
/*     */     LogEntry $$8;
/*  89 */     String $$7 = GLDebugMessageCallback.getMessage($$4, $$5);
/*     */ 
/*     */     
/*  92 */     synchronized (MESSAGE_BUFFER) {
/*  93 */       $$8 = lastEntry;
/*  94 */       if ($$8 == null || !$$8.isSame($$0, $$1, $$2, $$3, $$7)) {
/*  95 */         $$8 = new LogEntry($$0, $$1, $$2, $$3, $$7);
/*  96 */         MESSAGE_BUFFER.add($$8);
/*  97 */         lastEntry = $$8;
/*     */       } else {
/*  99 */         $$8.count++;
/*     */       } 
/*     */     } 
/*     */     
/* 103 */     LOGGER.info("OpenGL debug message: {}", $$8);
/*     */   }
/*     */   
/*     */   public static List<String> getLastOpenGlDebugMessages() {
/* 107 */     synchronized (MESSAGE_BUFFER) {
/* 108 */       List<String> $$0 = Lists.newArrayListWithCapacity(MESSAGE_BUFFER.size());
/* 109 */       for (LogEntry $$1 : MESSAGE_BUFFER) {
/* 110 */         $$0.add("" + $$1 + " x " + $$1);
/*     */       }
/* 112 */       return $$0;
/*     */     } 
/*     */   }
/*     */   
/* 116 */   private static final List<Integer> DEBUG_LEVELS = (List<Integer>)ImmutableList.of(Integer.valueOf(37190), Integer.valueOf(37191), Integer.valueOf(37192), Integer.valueOf(33387));
/* 117 */   private static final List<Integer> DEBUG_LEVELS_ARB = (List<Integer>)ImmutableList.of(Integer.valueOf(37190), Integer.valueOf(37191), Integer.valueOf(37192));
/*     */   
/*     */   private static boolean debugEnabled;
/*     */   
/*     */   public static boolean isDebugEnabled() {
/* 122 */     return debugEnabled;
/*     */   }
/*     */   
/*     */   public static void enableDebugCallback(int $$0, boolean $$1) {
/* 126 */     RenderSystem.assertInInitPhase();
/* 127 */     if ($$0 <= 0) {
/*     */       return;
/*     */     }
/*     */     
/* 131 */     GLCapabilities $$2 = GL.getCapabilities();
/* 132 */     if ($$2.GL_KHR_debug) {
/* 133 */       debugEnabled = true;
/* 134 */       GL11.glEnable(37600);
/* 135 */       if ($$1) {
/* 136 */         GL11.glEnable(33346);
/*     */       }
/* 138 */       for (int $$3 = 0; $$3 < DEBUG_LEVELS.size(); $$3++) {
/* 139 */         boolean $$4 = ($$3 < $$0);
/* 140 */         KHRDebug.glDebugMessageControl(4352, 4352, ((Integer)DEBUG_LEVELS.get($$3)).intValue(), (int[])null, $$4);
/*     */       } 
/* 142 */       KHRDebug.glDebugMessageCallback((GLDebugMessageCallbackI)GLX.make(GLDebugMessageCallback.create(GlDebug::printDebugLog), DebugMemoryUntracker::untrack), 0L);
/* 143 */     } else if ($$2.GL_ARB_debug_output) {
/* 144 */       debugEnabled = true;
/* 145 */       if ($$1) {
/* 146 */         GL11.glEnable(33346);
/*     */       }
/* 148 */       for (int $$5 = 0; $$5 < DEBUG_LEVELS_ARB.size(); $$5++) {
/* 149 */         boolean $$6 = ($$5 < $$0);
/* 150 */         ARBDebugOutput.glDebugMessageControlARB(4352, 4352, ((Integer)DEBUG_LEVELS_ARB.get($$5)).intValue(), (int[])null, $$6);
/*     */       } 
/* 152 */       ARBDebugOutput.glDebugMessageCallbackARB((GLDebugMessageARBCallbackI)GLX.make(GLDebugMessageARBCallback.create(GlDebug::printDebugLog), DebugMemoryUntracker::untrack), 0L);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class LogEntry {
/*     */     private final int id;
/*     */     private final int source;
/*     */     private final int type;
/*     */     private final int severity;
/*     */     private final String message;
/* 162 */     int count = 1;
/*     */     
/*     */     LogEntry(int $$0, int $$1, int $$2, int $$3, String $$4) {
/* 165 */       this.id = $$2;
/* 166 */       this.source = $$0;
/* 167 */       this.type = $$1;
/* 168 */       this.severity = $$3;
/* 169 */       this.message = $$4;
/*     */     }
/*     */     
/*     */     boolean isSame(int $$0, int $$1, int $$2, int $$3, String $$4) {
/* 173 */       return ($$1 == this.type && $$0 == this.source && $$2 == this.id && $$3 == this.severity && $$4
/*     */ 
/*     */ 
/*     */         
/* 177 */         .equals(this.message));
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 182 */       return "id=" + this.id + ", source=" + 
/* 183 */         GlDebug.sourceToString(this.source) + ", type=" + 
/* 184 */         GlDebug.typeToString(this.type) + ", severity=" + 
/* 185 */         GlDebug.severityToString(this.severity) + ", message='" + this.message + "'";
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\platform\GlDebug.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */