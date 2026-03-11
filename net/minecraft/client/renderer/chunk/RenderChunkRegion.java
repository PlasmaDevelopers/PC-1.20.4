/*    */ package net.minecraft.client.renderer.chunk;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.core.SectionPos;
/*    */ import net.minecraft.world.level.BlockAndTintGetter;
/*    */ import net.minecraft.world.level.ColorResolver;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.lighting.LevelLightEngine;
/*    */ import net.minecraft.world.level.material.FluidState;
/*    */ 
/*    */ public class RenderChunkRegion
/*    */   implements BlockAndTintGetter
/*    */ {
/*    */   private final int centerX;
/*    */   private final int centerZ;
/*    */   protected final RenderChunk[][] chunks;
/*    */   protected final Level level;
/*    */   
/*    */   RenderChunkRegion(Level $$0, int $$1, int $$2, RenderChunk[][] $$3) {
/* 24 */     this.level = $$0;
/* 25 */     this.centerX = $$1;
/* 26 */     this.centerZ = $$2;
/* 27 */     this.chunks = $$3;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState getBlockState(BlockPos $$0) {
/* 32 */     int $$1 = SectionPos.blockToSectionCoord($$0.getX()) - this.centerX;
/* 33 */     int $$2 = SectionPos.blockToSectionCoord($$0.getZ()) - this.centerZ;
/*    */     
/* 35 */     return this.chunks[$$1][$$2].getBlockState($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public FluidState getFluidState(BlockPos $$0) {
/* 40 */     int $$1 = SectionPos.blockToSectionCoord($$0.getX()) - this.centerX;
/* 41 */     int $$2 = SectionPos.blockToSectionCoord($$0.getZ()) - this.centerZ;
/*    */     
/* 43 */     return this.chunks[$$1][$$2].getBlockState($$0).getFluidState();
/*    */   }
/*    */ 
/*    */   
/*    */   public float getShade(Direction $$0, boolean $$1) {
/* 48 */     return this.level.getShade($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public LevelLightEngine getLightEngine() {
/* 53 */     return this.level.getLightEngine();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public BlockEntity getBlockEntity(BlockPos $$0) {
/* 59 */     int $$1 = SectionPos.blockToSectionCoord($$0.getX()) - this.centerX;
/* 60 */     int $$2 = SectionPos.blockToSectionCoord($$0.getZ()) - this.centerZ;
/*    */     
/* 62 */     return this.chunks[$$1][$$2].getBlockEntity($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getBlockTint(BlockPos $$0, ColorResolver $$1) {
/* 67 */     return this.level.getBlockTint($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMinBuildHeight() {
/* 72 */     return this.level.getMinBuildHeight();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getHeight() {
/* 77 */     return this.level.getHeight();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\chunk\RenderChunkRegion.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */