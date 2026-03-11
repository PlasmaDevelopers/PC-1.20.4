/*    */ package net.minecraft.world.level.levelgen.feature;
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.biome.Biome;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.SnowyDirtBlock;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ import net.minecraft.world.level.levelgen.Heightmap;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
/*    */ 
/*    */ public class SnowAndFreezeFeature extends Feature<NoneFeatureConfiguration> {
/*    */   public SnowAndFreezeFeature(Codec<NoneFeatureConfiguration> $$0) {
/* 17 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> $$0) {
/* 22 */     WorldGenLevel $$1 = $$0.level();
/* 23 */     BlockPos $$2 = $$0.origin();
/* 24 */     BlockPos.MutableBlockPos $$3 = new BlockPos.MutableBlockPos();
/* 25 */     BlockPos.MutableBlockPos $$4 = new BlockPos.MutableBlockPos();
/*    */     
/* 27 */     for (int $$5 = 0; $$5 < 16; $$5++) {
/* 28 */       for (int $$6 = 0; $$6 < 16; $$6++) {
/* 29 */         int $$7 = $$2.getX() + $$5;
/* 30 */         int $$8 = $$2.getZ() + $$6;
/* 31 */         int $$9 = $$1.getHeight(Heightmap.Types.MOTION_BLOCKING, $$7, $$8);
/*    */         
/* 33 */         $$3.set($$7, $$9, $$8);
/* 34 */         $$4.set((Vec3i)$$3).move(Direction.DOWN, 1);
/*    */         
/* 36 */         Biome $$10 = (Biome)$$1.getBiome((BlockPos)$$3).value();
/*    */         
/* 38 */         if ($$10.shouldFreeze((LevelReader)$$1, (BlockPos)$$4, false)) {
/* 39 */           $$1.setBlock((BlockPos)$$4, Blocks.ICE.defaultBlockState(), 2);
/*    */         }
/* 41 */         if ($$10.shouldSnow((LevelReader)$$1, (BlockPos)$$3)) {
/* 42 */           $$1.setBlock((BlockPos)$$3, Blocks.SNOW.defaultBlockState(), 2);
/*    */           
/* 44 */           BlockState $$11 = $$1.getBlockState((BlockPos)$$4);
/* 45 */           if ($$11.hasProperty((Property)SnowyDirtBlock.SNOWY)) {
/* 46 */             $$1.setBlock((BlockPos)$$4, (BlockState)$$11.setValue((Property)SnowyDirtBlock.SNOWY, Boolean.valueOf(true)), 2);
/*    */           }
/*    */         } 
/*    */       } 
/*    */     } 
/* 51 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\SnowAndFreezeFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */