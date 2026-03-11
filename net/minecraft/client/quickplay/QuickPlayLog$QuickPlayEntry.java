/*     */ package net.minecraft.client.quickplay;
/*     */ 
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function3;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.time.Instant;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ import net.minecraft.world.level.GameType;
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
/*     */ final class QuickPlayEntry
/*     */   extends Record
/*     */ {
/*     */   private final QuickPlayLog.QuickPlayWorld quickPlayWorld;
/*     */   private final Instant lastPlayedTime;
/*     */   private final GameType gamemode;
/*     */   public static final Codec<QuickPlayEntry> CODEC;
/*     */   
/*     */   public final String toString() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Lnet/minecraft/client/quickplay/QuickPlayLog$QuickPlayEntry;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #113	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/client/quickplay/QuickPlayLog$QuickPlayEntry;
/*     */   }
/*     */   
/*     */   public final int hashCode() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/quickplay/QuickPlayLog$QuickPlayEntry;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #113	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/client/quickplay/QuickPlayLog$QuickPlayEntry;
/*     */   }
/*     */   
/*     */   public final boolean equals(Object $$0) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lnet/minecraft/client/quickplay/QuickPlayLog$QuickPlayEntry;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #113	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lnet/minecraft/client/quickplay/QuickPlayLog$QuickPlayEntry;
/*     */     //   0	8	1	$$0	Ljava/lang/Object;
/*     */   }
/*     */   
/*     */   QuickPlayEntry(QuickPlayLog.QuickPlayWorld $$0, Instant $$1, GameType $$2) {
/* 113 */     this.quickPlayWorld = $$0; this.lastPlayedTime = $$1; this.gamemode = $$2; } public QuickPlayLog.QuickPlayWorld quickPlayWorld() { return this.quickPlayWorld; } public Instant lastPlayedTime() { return this.lastPlayedTime; } public GameType gamemode() { return this.gamemode; } static {
/* 114 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)QuickPlayLog.QuickPlayWorld.MAP_CODEC.forGetter(QuickPlayEntry::quickPlayWorld), (App)ExtraCodecs.INSTANT_ISO8601.fieldOf("lastPlayedTime").forGetter(QuickPlayEntry::lastPlayedTime), (App)GameType.CODEC.fieldOf("gamemode").forGetter(QuickPlayEntry::gamemode)).apply((Applicative)$$0, QuickPlayEntry::new));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\quickplay\QuickPlayLog$QuickPlayEntry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */