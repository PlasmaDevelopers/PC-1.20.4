/*    */ package net.minecraft.world.level.levelgen.placement;
/*    */ 
/*    */ import java.util.Optional;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.level.ChunkPos;
/*    */ import net.minecraft.world.level.LevelHeightAccessor;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.chunk.CarvingMask;
/*    */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*    */ import net.minecraft.world.level.chunk.ProtoChunk;
/*    */ import net.minecraft.world.level.levelgen.GenerationStep;
/*    */ import net.minecraft.world.level.levelgen.Heightmap;
/*    */ import net.minecraft.world.level.levelgen.WorldGenerationContext;
/*    */ 
/*    */ public class PlacementContext extends WorldGenerationContext {
/*    */   private final WorldGenLevel level;
/*    */   private final ChunkGenerator generator;
/*    */   private final Optional<PlacedFeature> topFeature;
/*    */   
/*    */   public PlacementContext(WorldGenLevel $$0, ChunkGenerator $$1, Optional<PlacedFeature> $$2) {
/* 22 */     super($$1, (LevelHeightAccessor)$$0);
/* 23 */     this.level = $$0;
/* 24 */     this.generator = $$1;
/* 25 */     this.topFeature = $$2;
/*    */   }
/*    */   
/*    */   public int getHeight(Heightmap.Types $$0, int $$1, int $$2) {
/* 29 */     return this.level.getHeight($$0, $$1, $$2);
/*    */   }
/*    */   
/*    */   public CarvingMask getCarvingMask(ChunkPos $$0, GenerationStep.Carving $$1) {
/* 33 */     return ((ProtoChunk)this.level.getChunk($$0.x, $$0.z)).getOrCreateCarvingMask($$1);
/*    */   }
/*    */   
/*    */   public BlockState getBlockState(BlockPos $$0) {
/* 37 */     return this.level.getBlockState($$0);
/*    */   }
/*    */   
/*    */   public int getMinBuildHeight() {
/* 41 */     return this.level.getMinBuildHeight();
/*    */   }
/*    */   
/*    */   public WorldGenLevel getLevel() {
/* 45 */     return this.level;
/*    */   }
/*    */   
/*    */   public Optional<PlacedFeature> topFeature() {
/* 49 */     return this.topFeature;
/*    */   }
/*    */   
/*    */   public ChunkGenerator generator() {
/* 53 */     return this.generator;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\placement\PlacementContext.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */