/*    */ package net.minecraft.world.level.levelgen.feature;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.level.ChunkPos;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
/*    */ 
/*    */ public class VoidStartPlatformFeature
/*    */   extends Feature<NoneFeatureConfiguration> {
/* 12 */   private static final BlockPos PLATFORM_OFFSET = new BlockPos(8, 3, 8);
/* 13 */   private static final ChunkPos PLATFORM_ORIGIN_CHUNK = new ChunkPos(PLATFORM_OFFSET);
/*    */   private static final int PLATFORM_RADIUS = 16;
/*    */   private static final int PLATFORM_RADIUS_CHUNKS = 1;
/*    */   
/*    */   public VoidStartPlatformFeature(Codec<NoneFeatureConfiguration> $$0) {
/* 18 */     super($$0);
/*    */   }
/*    */   
/*    */   private static int checkerboardDistance(int $$0, int $$1, int $$2, int $$3) {
/* 22 */     return Math.max(Math.abs($$0 - $$2), Math.abs($$1 - $$3));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> $$0) {
/* 27 */     WorldGenLevel $$1 = $$0.level();
/* 28 */     ChunkPos $$2 = new ChunkPos($$0.origin());
/* 29 */     if (checkerboardDistance($$2.x, $$2.z, PLATFORM_ORIGIN_CHUNK.x, PLATFORM_ORIGIN_CHUNK.z) > 1) {
/* 30 */       return true;
/*    */     }
/*    */     
/* 33 */     BlockPos $$3 = PLATFORM_OFFSET.atY($$0.origin().getY() + PLATFORM_OFFSET.getY());
/* 34 */     BlockPos.MutableBlockPos $$4 = new BlockPos.MutableBlockPos();
/* 35 */     for (int $$5 = $$2.getMinBlockZ(); $$5 <= $$2.getMaxBlockZ(); $$5++) {
/* 36 */       for (int $$6 = $$2.getMinBlockX(); $$6 <= $$2.getMaxBlockX(); $$6++) {
/* 37 */         if (checkerboardDistance($$3.getX(), $$3.getZ(), $$6, $$5) <= 16) {
/*    */ 
/*    */           
/* 40 */           $$4.set($$6, $$3.getY(), $$5);
/* 41 */           if ($$4.equals($$3)) {
/* 42 */             $$1.setBlock((BlockPos)$$4, Blocks.COBBLESTONE.defaultBlockState(), 2);
/*    */           } else {
/* 44 */             $$1.setBlock((BlockPos)$$4, Blocks.STONE.defaultBlockState(), 2);
/*    */           } 
/*    */         } 
/*    */       } 
/* 48 */     }  return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\VoidStartPlatformFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */