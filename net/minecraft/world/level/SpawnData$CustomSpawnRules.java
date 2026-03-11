/*    */ package net.minecraft.world.level;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.DataResult;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.function.BiFunction;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ import net.minecraft.util.InclusiveRange;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class CustomSpawnRules
/*    */   extends Record
/*    */ {
/*    */   private final InclusiveRange<Integer> blockLightLimit;
/*    */   private final InclusiveRange<Integer> skyLightLimit;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/SpawnData$CustomSpawnRules;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #53	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/SpawnData$CustomSpawnRules;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/SpawnData$CustomSpawnRules;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #53	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/SpawnData$CustomSpawnRules;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/SpawnData$CustomSpawnRules;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #53	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/SpawnData$CustomSpawnRules;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   public CustomSpawnRules(InclusiveRange<Integer> $$0, InclusiveRange<Integer> $$1) {
/* 53 */     this.blockLightLimit = $$0; this.skyLightLimit = $$1; } public InclusiveRange<Integer> blockLightLimit() { return this.blockLightLimit; } public InclusiveRange<Integer> skyLightLimit() { return this.skyLightLimit; }
/*    */ 
/*    */ 
/*    */   
/* 57 */   private static final InclusiveRange<Integer> LIGHT_RANGE = new InclusiveRange(Integer.valueOf(0), Integer.valueOf(15)); public static final Codec<CustomSpawnRules> CODEC;
/*    */   
/*    */   private static DataResult<InclusiveRange<Integer>> checkLightBoundaries(InclusiveRange<Integer> $$0) {
/* 60 */     if (!LIGHT_RANGE.contains($$0)) {
/* 61 */       return DataResult.error(() -> "Light values must be withing range " + LIGHT_RANGE);
/*    */     }
/* 63 */     return DataResult.success($$0);
/*    */   }
/*    */   
/*    */   private static MapCodec<InclusiveRange<Integer>> lightLimit(String $$0) {
/* 67 */     return ExtraCodecs.validate(InclusiveRange.INT.optionalFieldOf($$0, LIGHT_RANGE), CustomSpawnRules::checkLightBoundaries);
/*    */   }
/*    */   static {
/* 70 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)lightLimit("block_light_limit").forGetter(()), (App)lightLimit("sky_light_limit").forGetter(())).apply((Applicative)$$0, CustomSpawnRules::new));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\SpawnData$CustomSpawnRules.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */