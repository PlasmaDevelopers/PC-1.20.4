/*    */ package net.minecraft.world.level.block;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import java.util.function.BiFunction;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.world.effect.MobEffect;
/*    */ import net.minecraft.world.effect.MobEffectInstance;
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class EffectEntry
/*    */   extends Record
/*    */ {
/*    */   private final MobEffect effect;
/*    */   private final int duration;
/*    */   public static final Codec<EffectEntry> CODEC;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/block/SuspiciousEffectHolder$EffectEntry;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #35	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/block/SuspiciousEffectHolder$EffectEntry;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/block/SuspiciousEffectHolder$EffectEntry;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #35	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/block/SuspiciousEffectHolder$EffectEntry;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/block/SuspiciousEffectHolder$EffectEntry;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #35	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/block/SuspiciousEffectHolder$EffectEntry;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   public EffectEntry(MobEffect $$0, int $$1) {
/* 35 */     this.effect = $$0; this.duration = $$1; } public MobEffect effect() { return this.effect; } public int duration() { return this.duration; } static {
/* 36 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)BuiltInRegistries.MOB_EFFECT.byNameCodec().fieldOf("id").forGetter(EffectEntry::effect), (App)Codec.INT.optionalFieldOf("duration", Integer.valueOf(160)).forGetter(EffectEntry::duration)).apply((Applicative)$$0, EffectEntry::new));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 41 */   public static final Codec<List<EffectEntry>> LIST_CODEC = CODEC.listOf();
/*    */   
/*    */   public MobEffectInstance createEffectInstance() {
/* 44 */     return new MobEffectInstance(this.effect, this.duration);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\SuspiciousEffectHolder$EffectEntry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */