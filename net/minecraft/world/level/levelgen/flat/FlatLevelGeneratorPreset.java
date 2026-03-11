/*    */ package net.minecraft.world.level.levelgen.flat;
/*    */ 
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.core.Holder;
/*    */ 
/*    */ public final class FlatLevelGeneratorPreset extends Record {
/*    */   private final Holder<Item> displayItem;
/*    */   private final FlatLevelGeneratorSettings settings;
/*    */   public static final Codec<FlatLevelGeneratorPreset> DIRECT_CODEC;
/*    */   
/* 11 */   public FlatLevelGeneratorPreset(Holder<Item> $$0, FlatLevelGeneratorSettings $$1) { this.displayItem = $$0; this.settings = $$1; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/flat/FlatLevelGeneratorPreset;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 11 */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/flat/FlatLevelGeneratorPreset; } public Holder<Item> displayItem() { return this.displayItem; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/flat/FlatLevelGeneratorPreset;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/flat/FlatLevelGeneratorPreset; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/flat/FlatLevelGeneratorPreset;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/levelgen/flat/FlatLevelGeneratorPreset;
/* 11 */     //   0	8	1	$$0	Ljava/lang/Object; } public FlatLevelGeneratorSettings settings() { return this.settings; }
/*    */ 
/*    */   
/*    */   static {
/* 15 */     DIRECT_CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)RegistryFixedCodec.create(Registries.ITEM).fieldOf("display").forGetter(()), (App)FlatLevelGeneratorSettings.CODEC.fieldOf("settings").forGetter(())).apply((Applicative)$$0, FlatLevelGeneratorPreset::new));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 20 */   public static final Codec<Holder<FlatLevelGeneratorPreset>> CODEC = (Codec<Holder<FlatLevelGeneratorPreset>>)RegistryFileCodec.create(Registries.FLAT_LEVEL_GENERATOR_PRESET, DIRECT_CODEC);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\flat\FlatLevelGeneratorPreset.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */