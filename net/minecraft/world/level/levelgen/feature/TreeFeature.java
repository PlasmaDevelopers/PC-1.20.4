/*     */ package net.minecraft.world.level.levelgen.feature;
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.mojang.serialization.Codec;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.OptionalInt;
/*     */ import java.util.Set;
/*     */ import java.util.function.BiConsumer;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelSimulatedReader;
/*     */ import net.minecraft.world.level.LevelWriter;
/*     */ import net.minecraft.world.level.WorldGenLevel;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.LeavesBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
/*     */ import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
/*     */ import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacer;
/*     */ import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
/*     */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
/*     */ import net.minecraft.world.phys.shapes.BitSetDiscreteVoxelShape;
/*     */ import net.minecraft.world.phys.shapes.DiscreteVoxelShape;
/*     */ 
/*     */ public class TreeFeature extends Feature<TreeConfiguration> {
/*     */   private static final int BLOCK_UPDATE_FLAGS = 19;
/*     */   
/*     */   public TreeFeature(Codec<TreeConfiguration> $$0) {
/*  38 */     super($$0);
/*     */   }
/*     */   
/*     */   private static boolean isVine(LevelSimulatedReader $$0, BlockPos $$1) {
/*  42 */     return $$0.isStateAtPosition($$1, $$0 -> $$0.is(Blocks.VINE));
/*     */   }
/*     */   
/*     */   public static boolean isAirOrLeaves(LevelSimulatedReader $$0, BlockPos $$1) {
/*  46 */     return $$0.isStateAtPosition($$1, $$0 -> ($$0.isAir() || $$0.is(BlockTags.LEAVES)));
/*     */   }
/*     */   
/*     */   private static void setBlockKnownShape(LevelWriter $$0, BlockPos $$1, BlockState $$2) {
/*  50 */     $$0.setBlock($$1, $$2, 19);
/*     */   }
/*     */   
/*     */   public static boolean validTreePos(LevelSimulatedReader $$0, BlockPos $$1) {
/*  54 */     return $$0.isStateAtPosition($$1, $$0 -> ($$0.isAir() || $$0.is(BlockTags.REPLACEABLE_BY_TREES)));
/*     */   }
/*     */   
/*     */   private boolean doPlace(WorldGenLevel $$0, RandomSource $$1, BlockPos $$2, BiConsumer<BlockPos, BlockState> $$3, BiConsumer<BlockPos, BlockState> $$4, FoliagePlacer.FoliageSetter $$5, TreeConfiguration $$6) {
/*  58 */     int $$7 = $$6.trunkPlacer.getTreeHeight($$1);
/*  59 */     int $$8 = $$6.foliagePlacer.foliageHeight($$1, $$7, $$6);
/*  60 */     int $$9 = $$7 - $$8;
/*     */     
/*  62 */     int $$10 = $$6.foliagePlacer.foliageRadius($$1, $$9);
/*     */     
/*  64 */     BlockPos $$11 = $$6.rootPlacer.map($$2 -> $$2.getTrunkOrigin($$0, $$1)).orElse($$2);
/*     */     
/*  66 */     int $$12 = Math.min($$2.getY(), $$11.getY());
/*  67 */     int $$13 = Math.max($$2.getY(), $$11.getY()) + $$7 + 1;
/*  68 */     if ($$12 < $$0.getMinBuildHeight() + 1 || $$13 > $$0.getMaxBuildHeight()) {
/*  69 */       return false;
/*     */     }
/*     */     
/*  72 */     OptionalInt $$14 = $$6.minimumSize.minClippedHeight();
/*     */     
/*  74 */     int $$15 = getMaxFreeTreeHeight((LevelSimulatedReader)$$0, $$7, $$11, $$6);
/*  75 */     if ($$15 < $$7 && ($$14.isEmpty() || $$15 < $$14.getAsInt())) {
/*  76 */       return false;
/*     */     }
/*     */     
/*  79 */     if ($$6.rootPlacer.isPresent() && 
/*  80 */       !((RootPlacer)$$6.rootPlacer.get()).placeRoots((LevelSimulatedReader)$$0, $$3, $$1, $$2, $$11, $$6)) {
/*  81 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  85 */     List<FoliagePlacer.FoliageAttachment> $$16 = $$6.trunkPlacer.placeTrunk((LevelSimulatedReader)$$0, $$4, $$1, $$15, $$11, $$6);
/*  86 */     $$16.forEach($$7 -> $$0.foliagePlacer.createFoliage((LevelSimulatedReader)$$1, $$2, $$3, $$0, $$4, $$7, $$5, $$6));
/*     */ 
/*     */     
/*  89 */     return true;
/*     */   }
/*     */   
/*     */   private int getMaxFreeTreeHeight(LevelSimulatedReader $$0, int $$1, BlockPos $$2, TreeConfiguration $$3) {
/*  93 */     BlockPos.MutableBlockPos $$4 = new BlockPos.MutableBlockPos();
/*     */     
/*  95 */     for (int $$5 = 0; $$5 <= $$1 + 1; $$5++) {
/*  96 */       int $$6 = $$3.minimumSize.getSizeAtHeight($$1, $$5);
/*  97 */       for (int $$7 = -$$6; $$7 <= $$6; $$7++) {
/*  98 */         for (int $$8 = -$$6; $$8 <= $$6; $$8++) {
/*  99 */           $$4.setWithOffset((Vec3i)$$2, $$7, $$5, $$8);
/* 100 */           if (!$$3.trunkPlacer.isFree($$0, (BlockPos)$$4) || (!$$3.ignoreVines && isVine($$0, (BlockPos)$$4))) {
/* 101 */             return $$5 - 2;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 107 */     return $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setBlock(LevelWriter $$0, BlockPos $$1, BlockState $$2) {
/* 112 */     setBlockKnownShape($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean place(FeaturePlaceContext<TreeConfiguration> $$0) {
/* 117 */     final WorldGenLevel level = $$0.level();
/* 118 */     RandomSource $$2 = $$0.random();
/* 119 */     BlockPos $$3 = $$0.origin();
/* 120 */     TreeConfiguration $$4 = $$0.config();
/*     */     
/* 122 */     Set<BlockPos> $$5 = Sets.newHashSet();
/* 123 */     Set<BlockPos> $$6 = Sets.newHashSet();
/* 124 */     final Set<BlockPos> foliage = Sets.newHashSet();
/* 125 */     Set<BlockPos> $$8 = Sets.newHashSet();
/*     */     
/* 127 */     BiConsumer<BlockPos, BlockState> $$9 = ($$2, $$3) -> {
/*     */         $$0.add($$2.immutable());
/*     */         $$1.setBlock($$2, $$3, 19);
/*     */       };
/* 131 */     BiConsumer<BlockPos, BlockState> $$10 = ($$2, $$3) -> {
/*     */         $$0.add($$2.immutable());
/*     */         $$1.setBlock($$2, $$3, 19);
/*     */       };
/* 135 */     FoliagePlacer.FoliageSetter $$11 = new FoliagePlacer.FoliageSetter()
/*     */       {
/*     */         public void set(BlockPos $$0, BlockState $$1) {
/* 138 */           foliage.add($$0.immutable());
/* 139 */           level.setBlock($$0, $$1, 19);
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean isSet(BlockPos $$0) {
/* 144 */           return foliage.contains($$0);
/*     */         }
/*     */       };
/* 147 */     BiConsumer<BlockPos, BlockState> $$12 = ($$2, $$3) -> {
/*     */         $$0.add($$2.immutable());
/*     */         
/*     */         $$1.setBlock($$2, $$3, 19);
/*     */       };
/* 152 */     boolean $$13 = doPlace($$1, $$2, $$3, $$9, $$10, $$11, $$4);
/* 153 */     if (!$$13 || ($$6.isEmpty() && $$7.isEmpty())) {
/* 154 */       return false;
/*     */     }
/*     */     
/* 157 */     if (!$$4.decorators.isEmpty()) {
/* 158 */       TreeDecorator.Context $$14 = new TreeDecorator.Context((LevelSimulatedReader)$$1, $$12, $$2, $$6, $$7, $$5);
/* 159 */       $$4.decorators.forEach($$1 -> $$1.place($$0));
/*     */     } 
/*     */     
/* 162 */     return ((Boolean)BoundingBox.encapsulatingPositions(Iterables.concat($$5, $$6, $$7, $$8)).map($$4 -> {
/*     */           DiscreteVoxelShape $$5 = updateLeaves((LevelAccessor)$$0, $$4, $$1, $$2, $$3);
/*     */           
/*     */           StructureTemplate.updateShapeAtEdge((LevelAccessor)$$0, 3, $$5, $$4.minX(), $$4.minY(), $$4.minZ());
/*     */           return Boolean.valueOf(true);
/* 167 */         }).orElse(Boolean.valueOf(false))).booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static DiscreteVoxelShape updateLeaves(LevelAccessor $$0, BoundingBox $$1, Set<BlockPos> $$2, Set<BlockPos> $$3, Set<BlockPos> $$4) {
/* 175 */     BitSetDiscreteVoxelShape bitSetDiscreteVoxelShape = new BitSetDiscreteVoxelShape($$1.getXSpan(), $$1.getYSpan(), $$1.getZSpan());
/* 176 */     int $$6 = 7;
/*     */ 
/*     */     
/* 179 */     List<Set<BlockPos>> $$7 = Lists.newArrayList();
/* 180 */     for (int $$8 = 0; $$8 < 7; $$8++) {
/* 181 */       $$7.add(Sets.newHashSet());
/*     */     }
/*     */     
/* 184 */     for (BlockPos $$9 : Lists.newArrayList((Iterable)Sets.union($$3, $$4))) {
/* 185 */       if ($$1.isInside((Vec3i)$$9)) {
/* 186 */         bitSetDiscreteVoxelShape.fill($$9.getX() - $$1.minX(), $$9.getY() - $$1.minY(), $$9.getZ() - $$1.minZ());
/*     */       }
/*     */     } 
/*     */     
/* 190 */     BlockPos.MutableBlockPos $$10 = new BlockPos.MutableBlockPos();
/* 191 */     int $$11 = 0;
/* 192 */     ((Set<BlockPos>)$$7.get(0)).addAll($$2);
/*     */     
/*     */     while (true) {
/* 195 */       if ($$11 < 7 && ((Set)$$7.get($$11)).isEmpty()) {
/* 196 */         $$11++; continue;
/*     */       } 
/* 198 */       if ($$11 >= 7) {
/*     */         break;
/*     */       }
/* 201 */       Iterator<BlockPos> $$12 = ((Set<BlockPos>)$$7.get($$11)).iterator();
/* 202 */       BlockPos $$13 = $$12.next();
/* 203 */       $$12.remove();
/*     */       
/* 205 */       if (!$$1.isInside((Vec3i)$$13)) {
/*     */         continue;
/*     */       }
/* 208 */       if ($$11 != 0) {
/* 209 */         BlockState $$14 = $$0.getBlockState($$13);
/* 210 */         setBlockKnownShape((LevelWriter)$$0, $$13, (BlockState)$$14.setValue((Property)BlockStateProperties.DISTANCE, Integer.valueOf($$11)));
/*     */       } 
/* 212 */       bitSetDiscreteVoxelShape.fill($$13.getX() - $$1.minX(), $$13.getY() - $$1.minY(), $$13.getZ() - $$1.minZ());
/*     */       
/* 214 */       for (Direction $$15 : Direction.values()) {
/* 215 */         $$10.setWithOffset((Vec3i)$$13, $$15);
/* 216 */         if ($$1.isInside((Vec3i)$$10)) {
/*     */ 
/*     */           
/* 219 */           int $$16 = $$10.getX() - $$1.minX();
/* 220 */           int $$17 = $$10.getY() - $$1.minY();
/* 221 */           int $$18 = $$10.getZ() - $$1.minZ();
/* 222 */           if (!bitSetDiscreteVoxelShape.isFull($$16, $$17, $$18)) {
/*     */ 
/*     */ 
/*     */             
/* 226 */             BlockState $$19 = $$0.getBlockState((BlockPos)$$10);
/* 227 */             OptionalInt $$20 = LeavesBlock.getOptionalDistanceAt($$19);
/*     */             
/* 229 */             if (!$$20.isEmpty())
/*     */             
/*     */             { 
/*     */               
/* 233 */               int $$21 = Math.min($$20.getAsInt(), $$11 + 1);
/*     */               
/* 235 */               if ($$21 < 7)
/* 236 */               { ((Set<BlockPos>)$$7.get($$21)).add($$10.immutable());
/* 237 */                 $$11 = Math.min($$11, $$21); }  } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 241 */     }  return (DiscreteVoxelShape)bitSetDiscreteVoxelShape;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\TreeFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */