/*    */ package net.minecraft.world.level;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.util.InclusiveRange;
/*    */ 
/*    */ public final class SpawnData extends Record {
/*    */   private final CompoundTag entityToSpawn;
/*    */   private final Optional<CustomSpawnRules> customSpawnRules;
/*    */   public static final String ENTITY_TAG = "entity";
/*    */   
/* 16 */   public CompoundTag entityToSpawn() { return this.entityToSpawn; } public static final Codec<SpawnData> CODEC; public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/SpawnData;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #16	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/SpawnData; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/SpawnData;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #16	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/SpawnData; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/SpawnData;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #16	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/SpawnData;
/* 16 */     //   0	8	1	$$0	Ljava/lang/Object; } public Optional<CustomSpawnRules> customSpawnRules() { return this.customSpawnRules; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 22 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)CompoundTag.CODEC.fieldOf("entity").forGetter(()), (App)CustomSpawnRules.CODEC.optionalFieldOf("custom_spawn_rules").forGetter(())).apply((Applicative)$$0, SpawnData::new));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 27 */   public static final Codec<SimpleWeightedRandomList<SpawnData>> LIST_CODEC = SimpleWeightedRandomList.wrappedCodecAllowingEmpty(CODEC);
/*    */   
/*    */   public SpawnData() {
/* 30 */     this(new CompoundTag(), Optional.empty());
/*    */   }
/*    */   
/*    */   public SpawnData(CompoundTag $$0, Optional<CustomSpawnRules> $$1) {
/* 34 */     if ($$0.contains("id")) {
/*    */       
/* 36 */       ResourceLocation $$2 = ResourceLocation.tryParse($$0.getString("id"));
/* 37 */       if ($$2 != null) {
/* 38 */         $$0.putString("id", $$2.toString());
/*    */       } else {
/* 40 */         $$0.remove("id");
/*    */       } 
/*    */     } 
/*    */     this.entityToSpawn = $$0;
/*    */     this.customSpawnRules = $$1;
/*    */   } public CompoundTag getEntityToSpawn() {
/* 46 */     return this.entityToSpawn;
/*    */   }
/*    */   
/*    */   public Optional<CustomSpawnRules> getCustomSpawnRules() {
/* 50 */     return this.customSpawnRules;
/*    */   }
/*    */   public static final class CustomSpawnRules extends Record { private final InclusiveRange<Integer> blockLightLimit; private final InclusiveRange<Integer> skyLightLimit;
/* 53 */     public CustomSpawnRules(InclusiveRange<Integer> $$0, InclusiveRange<Integer> $$1) { this.blockLightLimit = $$0; this.skyLightLimit = $$1; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/SpawnData$CustomSpawnRules;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #53	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/world/level/SpawnData$CustomSpawnRules; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/SpawnData$CustomSpawnRules;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #53	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/world/level/SpawnData$CustomSpawnRules; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/SpawnData$CustomSpawnRules;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #53	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/world/level/SpawnData$CustomSpawnRules;
/* 53 */       //   0	8	1	$$0	Ljava/lang/Object; } public InclusiveRange<Integer> blockLightLimit() { return this.blockLightLimit; } public InclusiveRange<Integer> skyLightLimit() { return this.skyLightLimit; }
/*    */ 
/*    */ 
/*    */     
/* 57 */     private static final InclusiveRange<Integer> LIGHT_RANGE = new InclusiveRange(Integer.valueOf(0), Integer.valueOf(15)); public static final Codec<CustomSpawnRules> CODEC;
/*    */     
/*    */     private static DataResult<InclusiveRange<Integer>> checkLightBoundaries(InclusiveRange<Integer> $$0) {
/* 60 */       if (!LIGHT_RANGE.contains($$0)) {
/* 61 */         return DataResult.error(() -> "Light values must be withing range " + LIGHT_RANGE);
/*    */       }
/* 63 */       return DataResult.success($$0);
/*    */     }
/*    */     
/*    */     private static MapCodec<InclusiveRange<Integer>> lightLimit(String $$0) {
/* 67 */       return ExtraCodecs.validate(InclusiveRange.INT.optionalFieldOf($$0, LIGHT_RANGE), CustomSpawnRules::checkLightBoundaries);
/*    */     }
/*    */     static {
/* 70 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)lightLimit("block_light_limit").forGetter(()), (App)lightLimit("sky_light_limit").forGetter(())).apply((Applicative)$$0, CustomSpawnRules::new));
/*    */     } }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\SpawnData.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */