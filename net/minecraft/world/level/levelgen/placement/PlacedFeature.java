/*    */ package net.minecraft.world.level.levelgen.placement;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.HolderSet;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*    */ import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
/*    */ import org.apache.commons.lang3.mutable.MutableBoolean;
/*    */ 
/*    */ public final class PlacedFeature extends Record {
/*    */   private final Holder<ConfiguredFeature<?, ?>> feature;
/*    */   private final List<PlacementModifier> placement;
/*    */   public static final Codec<PlacedFeature> DIRECT_CODEC;
/*    */   
/* 23 */   public PlacedFeature(Holder<ConfiguredFeature<?, ?>> $$0, List<PlacementModifier> $$1) { this.feature = $$0; this.placement = $$1; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/placement/PlacedFeature;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #23	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 23 */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/placement/PlacedFeature; } public Holder<ConfiguredFeature<?, ?>> feature() { return this.feature; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/placement/PlacedFeature;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #23	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/levelgen/placement/PlacedFeature;
/* 23 */     //   0	8	1	$$0	Ljava/lang/Object; } public List<PlacementModifier> placement() { return this.placement; }
/*    */ 
/*    */   
/*    */   static {
/* 27 */     DIRECT_CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ConfiguredFeature.CODEC.fieldOf("feature").forGetter(()), (App)PlacementModifier.CODEC.listOf().fieldOf("placement").forGetter(())).apply((Applicative)$$0, PlacedFeature::new));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 32 */   public static final Codec<Holder<PlacedFeature>> CODEC = (Codec<Holder<PlacedFeature>>)RegistryFileCodec.create(Registries.PLACED_FEATURE, DIRECT_CODEC);
/* 33 */   public static final Codec<HolderSet<PlacedFeature>> LIST_CODEC = RegistryCodecs.homogeneousList(Registries.PLACED_FEATURE, DIRECT_CODEC);
/*    */   
/* 35 */   public static final Codec<List<HolderSet<PlacedFeature>>> LIST_OF_LISTS_CODEC = RegistryCodecs.homogeneousList(Registries.PLACED_FEATURE, DIRECT_CODEC, true).listOf();
/*    */   
/*    */   public boolean place(WorldGenLevel $$0, ChunkGenerator $$1, RandomSource $$2, BlockPos $$3) {
/* 38 */     return placeWithContext(new PlacementContext($$0, $$1, Optional.empty()), $$2, $$3);
/*    */   }
/*    */   
/*    */   public boolean placeWithBiomeCheck(WorldGenLevel $$0, ChunkGenerator $$1, RandomSource $$2, BlockPos $$3) {
/* 42 */     return placeWithContext(new PlacementContext($$0, $$1, Optional.of(this)), $$2, $$3);
/*    */   }
/*    */   
/*    */   private boolean placeWithContext(PlacementContext $$0, RandomSource $$1, BlockPos $$2) {
/* 46 */     Stream<BlockPos> $$3 = Stream.of($$2);
/* 47 */     for (PlacementModifier $$4 : this.placement) {
/* 48 */       $$3 = $$3.flatMap($$3 -> $$0.getPositions($$1, $$2, $$3));
/*    */     }
/*    */     
/* 51 */     ConfiguredFeature<?, ?> $$5 = (ConfiguredFeature<?, ?>)this.feature.value();
/* 52 */     MutableBoolean $$6 = new MutableBoolean();
/* 53 */     $$3.forEach($$4 -> {
/*    */           if ($$0.place($$1.getLevel(), $$1.generator(), $$2, $$4)) {
/*    */             $$3.setTrue();
/*    */           }
/*    */         });
/*    */ 
/*    */ 
/*    */     
/* 61 */     return $$6.isTrue();
/*    */   }
/*    */   
/*    */   public Stream<ConfiguredFeature<?, ?>> getFeatures() {
/* 65 */     return ((ConfiguredFeature)this.feature.value()).getFeatures();
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 70 */     return "Placed " + this.feature;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\placement\PlacedFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */