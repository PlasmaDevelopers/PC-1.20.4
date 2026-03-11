/*    */ package net.minecraft.world.level.levelgen.feature;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.Objects;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.ChunkPos;
/*    */ import net.minecraft.world.level.ServerLevelAccessor;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.Mirror;
/*    */ import net.minecraft.world.level.block.Rotation;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.levelgen.Heightmap;
/*    */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*    */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
/*    */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
/*    */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
/*    */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
/*    */ import org.apache.commons.lang3.mutable.MutableInt;
/*    */ 
/*    */ public class FossilFeature extends Feature<FossilFeatureConfiguration> {
/*    */   public FossilFeature(Codec<FossilFeatureConfiguration> $$0) {
/* 25 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean place(FeaturePlaceContext<FossilFeatureConfiguration> $$0) {
/* 30 */     RandomSource $$1 = $$0.random();
/* 31 */     WorldGenLevel $$2 = $$0.level();
/* 32 */     BlockPos $$3 = $$0.origin();
/* 33 */     Rotation $$4 = Rotation.getRandom($$1);
/* 34 */     FossilFeatureConfiguration $$5 = $$0.config();
/*    */     
/* 36 */     int $$6 = $$1.nextInt($$5.fossilStructures.size());
/*    */ 
/*    */     
/* 39 */     StructureTemplateManager $$7 = $$2.getLevel().getServer().getStructureManager();
/* 40 */     StructureTemplate $$8 = $$7.getOrCreate($$5.fossilStructures.get($$6));
/* 41 */     StructureTemplate $$9 = $$7.getOrCreate($$5.overlayStructures.get($$6));
/* 42 */     ChunkPos $$10 = new ChunkPos($$3);
/*    */ 
/*    */     
/* 45 */     BoundingBox $$11 = new BoundingBox($$10.getMinBlockX() - 16, $$2.getMinBuildHeight(), $$10.getMinBlockZ() - 16, $$10.getMaxBlockX() + 16, $$2.getMaxBuildHeight(), $$10.getMaxBlockZ() + 16);
/*    */     
/* 47 */     StructurePlaceSettings $$12 = (new StructurePlaceSettings()).setRotation($$4).setBoundingBox($$11).setRandom($$1);
/*    */     
/* 49 */     Vec3i $$13 = $$8.getSize($$4);
/*    */     
/* 51 */     BlockPos $$14 = $$3.offset(-$$13.getX() / 2, 0, -$$13.getZ() / 2);
/*    */     
/* 53 */     int $$15 = $$3.getY();
/*    */     
/* 55 */     for (int $$16 = 0; $$16 < $$13.getX(); $$16++) {
/* 56 */       for (int $$17 = 0; $$17 < $$13.getZ(); $$17++) {
/* 57 */         $$15 = Math.min($$15, $$2.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, $$14.getX() + $$16, $$14.getZ() + $$17));
/*    */       }
/*    */     } 
/* 60 */     int $$18 = Math.max($$15 - 15 - $$1.nextInt(10), $$2.getMinBuildHeight() + 10);
/*    */     
/* 62 */     BlockPos $$19 = $$8.getZeroPositionWithTransform($$14.atY($$18), Mirror.NONE, $$4);
/*    */     
/* 64 */     if (countEmptyCorners($$2, $$8.getBoundingBox($$12, $$19)) > $$5.maxEmptyCornersAllowed) {
/* 65 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 69 */     $$12.clearProcessors();
/* 70 */     Objects.requireNonNull($$12); ((StructureProcessorList)$$5.fossilProcessors.value()).list().forEach($$12::addProcessor);
/* 71 */     $$8.placeInWorld((ServerLevelAccessor)$$2, $$19, $$19, $$12, $$1, 4);
/*    */ 
/*    */     
/* 74 */     $$12.clearProcessors();
/* 75 */     Objects.requireNonNull($$12); ((StructureProcessorList)$$5.overlayProcessors.value()).list().forEach($$12::addProcessor);
/* 76 */     $$9.placeInWorld((ServerLevelAccessor)$$2, $$19, $$19, $$12, $$1, 4);
/*    */     
/* 78 */     return true;
/*    */   }
/*    */   
/*    */   private static int countEmptyCorners(WorldGenLevel $$0, BoundingBox $$1) {
/* 82 */     MutableInt $$2 = new MutableInt(0);
/* 83 */     $$1.forAllCorners($$2 -> {
/*    */           BlockState $$3 = $$0.getBlockState($$2);
/*    */           if ($$3.isAir() || $$3.is(Blocks.LAVA) || $$3.is(Blocks.WATER)) {
/*    */             $$1.add(1);
/*    */           }
/*    */         });
/* 89 */     return $$2.getValue().intValue();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\FossilFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */