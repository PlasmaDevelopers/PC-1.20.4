/*     */ package net.minecraft.client.multiplayer.chat;
/*     */ 
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.time.Instant;
/*     */ import java.util.UUID;
/*     */ import java.util.function.BiFunction;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.ComponentSerialization;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class System
/*     */   extends Record
/*     */   implements LoggedChatMessage
/*     */ {
/*     */   private final Component message;
/*     */   private final Instant timeStamp;
/*     */   public static final Codec<System> CODEC;
/*     */   
/*     */   public final String toString() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Lnet/minecraft/client/multiplayer/chat/LoggedChatMessage$System;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #86	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/client/multiplayer/chat/LoggedChatMessage$System;
/*     */   }
/*     */   
/*     */   public final int hashCode() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/multiplayer/chat/LoggedChatMessage$System;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #86	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/client/multiplayer/chat/LoggedChatMessage$System;
/*     */   }
/*     */   
/*     */   public final boolean equals(Object $$0) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lnet/minecraft/client/multiplayer/chat/LoggedChatMessage$System;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #86	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lnet/minecraft/client/multiplayer/chat/LoggedChatMessage$System;
/*     */     //   0	8	1	$$0	Ljava/lang/Object;
/*     */   }
/*     */   
/*     */   public System(Component $$0, Instant $$1) {
/*  86 */     this.message = $$0; this.timeStamp = $$1; } public Component message() { return this.message; } public Instant timeStamp() { return this.timeStamp; } static {
/*  87 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ComponentSerialization.CODEC.fieldOf("message").forGetter(System::message), (App)ExtraCodecs.INSTANT_ISO8601.fieldOf("time_stamp").forGetter(System::timeStamp)).apply((Applicative)$$0, System::new));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Component toContentComponent() {
/*  94 */     return this.message;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canReport(UUID $$0) {
/*  99 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public LoggedChatEvent.Type type() {
/* 104 */     return LoggedChatEvent.Type.SYSTEM;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\multiplayer\chat\LoggedChatMessage$System.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */