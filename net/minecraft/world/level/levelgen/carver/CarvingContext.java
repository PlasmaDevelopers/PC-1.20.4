/*    */ package net.minecraft.world.level.levelgen.carver;
/*    */ 
/*    */ import java.util.Optional;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.RegistryAccess;
/*    */ import net.minecraft.world.level.LevelHeightAccessor;
/*    */ import net.minecraft.world.level.biome.Biome;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.chunk.ChunkAccess;
/*    */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*    */ import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
/*    */ import net.minecraft.world.level.levelgen.NoiseChunk;
/*    */ import net.minecraft.world.level.levelgen.RandomState;
/*    */ import net.minecraft.world.level.levelgen.SurfaceRules;
/*    */ import net.minecraft.world.level.levelgen.WorldGenerationContext;
/*    */ 
/*    */ public class CarvingContext extends WorldGenerationContext {
/*    */   private final RegistryAccess registryAccess;
/*    */   private final NoiseChunk noiseChunk;
/*    */   private final RandomState randomState;
/*    */   private final SurfaceRules.RuleSource surfaceRule;
/*    */   
/*    */   public CarvingContext(NoiseBasedChunkGenerator $$0, RegistryAccess $$1, LevelHeightAccessor $$2, NoiseChunk $$3, RandomState $$4, SurfaceRules.RuleSource $$5) {
/* 26 */     super((ChunkGenerator)$$0, $$2);
/* 27 */     this.registryAccess = $$1;
/* 28 */     this.noiseChunk = $$3;
/* 29 */     this.randomState = $$4;
/* 30 */     this.surfaceRule = $$5;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public Optional<BlockState> topMaterial(Function<BlockPos, Holder<Biome>> $$0, ChunkAccess $$1, BlockPos $$2, boolean $$3) {
/* 38 */     return this.randomState.surfaceSystem().topMaterial(this.surfaceRule, this, $$0, $$1, this.noiseChunk, $$2, $$3);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public RegistryAccess registryAccess() {
/* 46 */     return this.registryAccess;
/*    */   }
/*    */   
/*    */   public RandomState randomState() {
/* 50 */     return this.randomState;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\carver\CarvingContext.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */