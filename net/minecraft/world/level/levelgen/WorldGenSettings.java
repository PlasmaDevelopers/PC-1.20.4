/*    */ package net.minecraft.world.level.levelgen;
/*    */ 
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ 
/*    */ public final class WorldGenSettings extends Record {
/*    */   private final WorldOptions options;
/*    */   private final WorldDimensions dimensions;
/*    */   public static final Codec<WorldGenSettings> CODEC;
/*    */   
/* 10 */   public WorldGenSettings(WorldOptions $$0, WorldDimensions $$1) { this.options = $$0; this.dimensions = $$1; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/WorldGenSettings;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #10	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 10 */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/WorldGenSettings; } public WorldOptions options() { return this.options; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/WorldGenSettings;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #10	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/WorldGenSettings; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/WorldGenSettings;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #10	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/levelgen/WorldGenSettings;
/* 10 */     //   0	8	1	$$0	Ljava/lang/Object; } public WorldDimensions dimensions() { return this.dimensions; }
/*    */ 
/*    */   
/*    */   static {
/* 14 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)WorldOptions.CODEC.forGetter(WorldGenSettings::options), (App)WorldDimensions.CODEC.forGetter(WorldGenSettings::dimensions)).apply((Applicative)$$0, $$0.stable(WorldGenSettings::new)));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static <T> DataResult<T> encode(DynamicOps<T> $$0, WorldOptions $$1, WorldDimensions $$2) {
/* 20 */     return CODEC.encodeStart($$0, new WorldGenSettings($$1, $$2));
/*    */   }
/*    */   
/*    */   public static <T> DataResult<T> encode(DynamicOps<T> $$0, WorldOptions $$1, RegistryAccess $$2) {
/* 24 */     return encode($$0, $$1, new WorldDimensions($$2.registryOrThrow(Registries.LEVEL_STEM)));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\WorldGenSettings.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */