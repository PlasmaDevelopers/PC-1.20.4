/*    */ package net.minecraft.world.level.chunk;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.server.level.FullChunkStatus;
/*    */ import net.minecraft.world.level.ChunkPos;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.biome.Biome;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.material.FluidState;
/*    */ import net.minecraft.world.level.material.Fluids;
/*    */ 
/*    */ public class EmptyLevelChunk
/*    */   extends LevelChunk {
/*    */   private final Holder<Biome> biome;
/*    */   
/*    */   public EmptyLevelChunk(Level $$0, ChunkPos $$1, Holder<Biome> $$2) {
/* 21 */     super($$0, $$1);
/* 22 */     this.biome = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState getBlockState(BlockPos $$0) {
/* 27 */     return Blocks.VOID_AIR.defaultBlockState();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public BlockState setBlockState(BlockPos $$0, BlockState $$1, boolean $$2) {
/* 33 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public FluidState getFluidState(BlockPos $$0) {
/* 38 */     return Fluids.EMPTY.defaultFluidState();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getLightEmission(BlockPos $$0) {
/* 43 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public BlockEntity getBlockEntity(BlockPos $$0, LevelChunk.EntityCreationType $$1) {
/* 49 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void addAndRegisterBlockEntity(BlockEntity $$0) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void setBlockEntity(BlockEntity $$0) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void removeBlockEntity(BlockPos $$0) {}
/*    */ 
/*    */   
/*    */   public boolean isEmpty() {
/* 66 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isYSpaceEmpty(int $$0, int $$1) {
/* 71 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public FullChunkStatus getFullStatus() {
/* 76 */     return FullChunkStatus.FULL;
/*    */   }
/*    */ 
/*    */   
/*    */   public Holder<Biome> getNoiseBiome(int $$0, int $$1, int $$2) {
/* 81 */     return this.biome;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\chunk\EmptyLevelChunk.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */