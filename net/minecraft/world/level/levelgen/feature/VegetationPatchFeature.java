/*     */ package net.minecraft.world.level.levelgen.feature;
/*     */ import com.mojang.serialization.Codec;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import java.util.function.Predicate;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.WorldGenLevel;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
/*     */ import net.minecraft.world.level.levelgen.placement.PlacedFeature;
/*     */ 
/*     */ public class VegetationPatchFeature extends Feature<VegetationPatchConfiguration> {
/*     */   public VegetationPatchFeature(Codec<VegetationPatchConfiguration> $$0) {
/*  20 */     super($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean place(FeaturePlaceContext<VegetationPatchConfiguration> $$0) {
/*  25 */     WorldGenLevel $$1 = $$0.level();
/*  26 */     VegetationPatchConfiguration $$2 = $$0.config();
/*  27 */     RandomSource $$3 = $$0.random();
/*  28 */     BlockPos $$4 = $$0.origin();
/*  29 */     Predicate<BlockState> $$5 = $$1 -> $$1.is($$0.replaceable);
/*     */     
/*  31 */     int $$6 = $$2.xzRadius.sample($$3) + 1;
/*  32 */     int $$7 = $$2.xzRadius.sample($$3) + 1;
/*     */     
/*  34 */     Set<BlockPos> $$8 = placeGroundPatch($$1, $$2, $$3, $$4, $$5, $$6, $$7);
/*  35 */     distributeVegetation($$0, $$1, $$2, $$3, $$8, $$6, $$7);
/*     */     
/*  37 */     return !$$8.isEmpty();
/*     */   }
/*     */   
/*     */   protected Set<BlockPos> placeGroundPatch(WorldGenLevel $$0, VegetationPatchConfiguration $$1, RandomSource $$2, BlockPos $$3, Predicate<BlockState> $$4, int $$5, int $$6) {
/*  41 */     BlockPos.MutableBlockPos $$7 = $$3.mutable();
/*  42 */     BlockPos.MutableBlockPos $$8 = $$7.mutable();
/*  43 */     Direction $$9 = $$1.surface.getDirection();
/*  44 */     Direction $$10 = $$9.getOpposite();
/*  45 */     Set<BlockPos> $$11 = new HashSet<>();
/*  46 */     for (int $$12 = -$$5; $$12 <= $$5; $$12++) {
/*  47 */       boolean $$13 = ($$12 == -$$5 || $$12 == $$5);
/*  48 */       for (int $$14 = -$$6; $$14 <= $$6; $$14++) {
/*  49 */         boolean $$15 = ($$14 == -$$6 || $$14 == $$6);
/*  50 */         boolean $$16 = ($$13 || $$15);
/*  51 */         boolean $$17 = ($$13 && $$15);
/*  52 */         boolean $$18 = ($$16 && !$$17);
/*  53 */         if (!$$17 && (!$$18 || ($$1.extraEdgeColumnChance != 0.0F && $$2.nextFloat() <= $$1.extraEdgeColumnChance))) {
/*     */ 
/*     */           
/*  56 */           $$7.setWithOffset((Vec3i)$$3, $$12, 0, $$14);
/*  57 */           int $$19 = 0;
/*  58 */           while ($$0.isStateAtPosition((BlockPos)$$7, BlockBehaviour.BlockStateBase::isAir) && $$19 < $$1.verticalRange) {
/*  59 */             $$7.move($$9);
/*  60 */             $$19++;
/*     */           } 
/*  62 */           $$19 = 0;
/*  63 */           while ($$0.isStateAtPosition((BlockPos)$$7, $$0 -> !$$0.isAir()) && $$19 < $$1.verticalRange) {
/*  64 */             $$7.move($$10);
/*  65 */             $$19++;
/*     */           } 
/*     */           
/*  68 */           $$8.setWithOffset((Vec3i)$$7, $$1.surface.getDirection());
/*  69 */           BlockState $$20 = $$0.getBlockState((BlockPos)$$8);
/*  70 */           if ($$0.isEmptyBlock((BlockPos)$$7) && $$20.isFaceSturdy((BlockGetter)$$0, (BlockPos)$$8, $$1.surface.getDirection().getOpposite())) {
/*  71 */             int $$21 = $$1.depth.sample($$2) + (($$1.extraBottomBlockChance > 0.0F && $$2.nextFloat() < $$1.extraBottomBlockChance) ? 1 : 0);
/*  72 */             BlockPos $$22 = $$8.immutable();
/*  73 */             boolean $$23 = placeGround($$0, $$1, $$4, $$2, $$8, $$21);
/*  74 */             if ($$23)
/*  75 */               $$11.add($$22); 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*  80 */     return $$11;
/*     */   }
/*     */   
/*     */   protected void distributeVegetation(FeaturePlaceContext<VegetationPatchConfiguration> $$0, WorldGenLevel $$1, VegetationPatchConfiguration $$2, RandomSource $$3, Set<BlockPos> $$4, int $$5, int $$6) {
/*  84 */     for (BlockPos $$7 : $$4) {
/*  85 */       if ($$2.vegetationChance > 0.0F && $$3.nextFloat() < $$2.vegetationChance) {
/*  86 */         placeVegetation($$1, $$2, $$0.chunkGenerator(), $$3, $$7);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean placeVegetation(WorldGenLevel $$0, VegetationPatchConfiguration $$1, ChunkGenerator $$2, RandomSource $$3, BlockPos $$4) {
/*  92 */     return ((PlacedFeature)$$1.vegetationFeature.value()).place($$0, $$2, $$3, $$4.relative($$1.surface.getDirection().getOpposite()));
/*     */   }
/*     */   
/*     */   protected boolean placeGround(WorldGenLevel $$0, VegetationPatchConfiguration $$1, Predicate<BlockState> $$2, RandomSource $$3, BlockPos.MutableBlockPos $$4, int $$5) {
/*  96 */     for (int $$6 = 0; $$6 < $$5; $$6++) {
/*  97 */       BlockState $$7 = $$1.groundState.getState($$3, (BlockPos)$$4);
/*  98 */       BlockState $$8 = $$0.getBlockState((BlockPos)$$4);
/*  99 */       if (!$$7.is($$8.getBlock())) {
/*     */ 
/*     */ 
/*     */         
/* 103 */         if (!$$2.test($$8)) {
/* 104 */           return ($$6 != 0);
/*     */         }
/*     */         
/* 107 */         $$0.setBlock((BlockPos)$$4, $$7, 2);
/* 108 */         $$4.move($$1.surface.getDirection());
/*     */       } 
/* 110 */     }  return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\VegetationPatchFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */