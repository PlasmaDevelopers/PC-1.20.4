/*    */ package net.minecraft.world.level.levelgen.feature;
/*    */ import com.mojang.serialization.Codec;
/*    */ import it.unimi.dsi.fastutil.ints.IntArrayList;
/*    */ import it.unimi.dsi.fastutil.ints.IntListIterator;
/*    */ import java.util.stream.IntStream;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.RandomizableContainer;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.ChunkPos;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.levelgen.Heightmap;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
/*    */ import net.minecraft.world.level.storage.loot.BuiltInLootTables;
/*    */ 
/*    */ public class BonusChestFeature extends Feature<NoneFeatureConfiguration> {
/*    */   public BonusChestFeature(Codec<NoneFeatureConfiguration> $$0) {
/* 23 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> $$0) {
/* 28 */     RandomSource $$1 = $$0.random();
/* 29 */     WorldGenLevel $$2 = $$0.level();
/* 30 */     ChunkPos $$3 = new ChunkPos($$0.origin());
/* 31 */     IntArrayList $$4 = Util.toShuffledList(IntStream.rangeClosed($$3.getMinBlockX(), $$3.getMaxBlockX()), $$1);
/* 32 */     IntArrayList $$5 = Util.toShuffledList(IntStream.rangeClosed($$3.getMinBlockZ(), $$3.getMaxBlockZ()), $$1);
/* 33 */     BlockPos.MutableBlockPos $$6 = new BlockPos.MutableBlockPos();
/*    */     
/* 35 */     for (IntListIterator<Integer> intListIterator = $$4.iterator(); intListIterator.hasNext(); ) { Integer $$7 = intListIterator.next();
/* 36 */       for (IntListIterator<Integer> intListIterator1 = $$5.iterator(); intListIterator1.hasNext(); ) { Integer $$8 = intListIterator1.next();
/* 37 */         $$6.set($$7.intValue(), 0, $$8.intValue());
/* 38 */         BlockPos $$9 = $$2.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (BlockPos)$$6);
/*    */         
/* 40 */         if ($$2.isEmptyBlock($$9) || $$2.getBlockState($$9).getCollisionShape((BlockGetter)$$2, $$9).isEmpty()) {
/* 41 */           $$2.setBlock($$9, Blocks.CHEST.defaultBlockState(), 2);
/*    */           
/* 43 */           RandomizableContainer.setBlockEntityLootTable((BlockGetter)$$2, $$1, $$9, BuiltInLootTables.SPAWN_BONUS_CHEST);
/*    */           
/* 45 */           BlockState $$10 = Blocks.TORCH.defaultBlockState();
/*    */           
/* 47 */           for (Direction $$11 : Direction.Plane.HORIZONTAL) {
/* 48 */             BlockPos $$12 = $$9.relative($$11);
/* 49 */             if ($$10.canSurvive((LevelReader)$$2, $$12)) {
/* 50 */               $$2.setBlock($$12, $$10, 2);
/*    */             }
/*    */           } 
/* 53 */           return true;
/*    */         }  }
/*    */        }
/*    */ 
/*    */     
/* 58 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\BonusChestFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */