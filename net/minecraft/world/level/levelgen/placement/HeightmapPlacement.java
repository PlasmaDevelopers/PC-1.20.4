/*    */ package net.minecraft.world.level.levelgen.placement;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.function.Function;
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.levelgen.Heightmap;
/*    */ 
/*    */ public class HeightmapPlacement extends PlacementModifier {
/*    */   static {
/* 15 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Heightmap.Types.CODEC.fieldOf("heightmap").forGetter(())).apply((Applicative)$$0, HeightmapPlacement::new));
/*    */   }
/*    */   
/*    */   public static final Codec<HeightmapPlacement> CODEC;
/*    */   private final Heightmap.Types heightmap;
/*    */   
/*    */   private HeightmapPlacement(Heightmap.Types $$0) {
/* 22 */     this.heightmap = $$0;
/*    */   }
/*    */   
/*    */   public static HeightmapPlacement onHeightmap(Heightmap.Types $$0) {
/* 26 */     return new HeightmapPlacement($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public Stream<BlockPos> getPositions(PlacementContext $$0, RandomSource $$1, BlockPos $$2) {
/* 31 */     int $$3 = $$2.getX();
/* 32 */     int $$4 = $$2.getZ();
/* 33 */     int $$5 = $$0.getHeight(this.heightmap, $$3, $$4);
/* 34 */     if ($$5 > $$0.getMinBuildHeight()) {
/* 35 */       return Stream.of(new BlockPos($$3, $$5, $$4));
/*    */     }
/* 37 */     return Stream.of(new BlockPos[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public PlacementModifierType<?> type() {
/* 42 */     return PlacementModifierType.HEIGHTMAP;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\placement\HeightmapPlacement.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */