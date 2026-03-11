/*     */ package net.minecraft.world.level.levelgen.feature.foliageplacers;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.valueproviders.IntProvider;
/*     */ import net.minecraft.world.level.LevelSimulatedReader;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ 
/*     */ public abstract class FoliagePlacer {
/*  20 */   public static final Codec<FoliagePlacer> CODEC = BuiltInRegistries.FOLIAGE_PLACER_TYPE.byNameCodec().dispatch(FoliagePlacer::type, FoliagePlacerType::codec);
/*     */   
/*     */   protected final IntProvider radius;
/*     */   protected final IntProvider offset;
/*     */   
/*     */   protected static <P extends FoliagePlacer> Products.P2<RecordCodecBuilder.Mu<P>, IntProvider, IntProvider> foliagePlacerParts(RecordCodecBuilder.Instance<P> $$0) {
/*  26 */     return $$0.group(
/*  27 */         (App)IntProvider.codec(0, 16).fieldOf("radius").forGetter($$0 -> $$0.radius), 
/*  28 */         (App)IntProvider.codec(0, 16).fieldOf("offset").forGetter($$0 -> $$0.offset));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FoliagePlacer(IntProvider $$0, IntProvider $$1) {
/*  39 */     this.radius = $$0;
/*  40 */     this.offset = $$1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void createFoliage(LevelSimulatedReader $$0, FoliageSetter $$1, RandomSource $$2, TreeConfiguration $$3, int $$4, FoliageAttachment $$5, int $$6, int $$7) {
/*  46 */     createFoliage($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, offset($$2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int foliageRadius(RandomSource $$0, int $$1) {
/*  54 */     return this.radius.sample($$0);
/*     */   }
/*     */   
/*     */   private int offset(RandomSource $$0) {
/*  58 */     return this.offset.sample($$0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean shouldSkipLocationSigned(RandomSource $$0, int $$1, int $$2, int $$3, int $$4, boolean $$5) {
/*     */     int $$8;
/*     */     int $$9;
/*  66 */     if ($$5) {
/*     */ 
/*     */       
/*  69 */       int $$6 = Math.min(Math.abs($$1), Math.abs($$1 - 1));
/*  70 */       int $$7 = Math.min(Math.abs($$3), Math.abs($$3 - 1));
/*     */     }
/*     */     else {
/*     */       
/*  74 */       $$8 = Math.abs($$1);
/*  75 */       $$9 = Math.abs($$3);
/*     */     } 
/*  77 */     return shouldSkipLocation($$0, $$8, $$2, $$9, $$4, $$5);
/*     */   }
/*     */   
/*     */   protected void placeLeavesRow(LevelSimulatedReader $$0, FoliageSetter $$1, RandomSource $$2, TreeConfiguration $$3, BlockPos $$4, int $$5, int $$6, boolean $$7) {
/*  81 */     int $$8 = $$7 ? 1 : 0;
/*  82 */     BlockPos.MutableBlockPos $$9 = new BlockPos.MutableBlockPos();
/*  83 */     for (int $$10 = -$$5; $$10 <= $$5 + $$8; $$10++) {
/*  84 */       for (int $$11 = -$$5; $$11 <= $$5 + $$8; $$11++) {
/*  85 */         if (!shouldSkipLocationSigned($$2, $$10, $$6, $$11, $$5, $$7)) {
/*     */ 
/*     */           
/*  88 */           $$9.setWithOffset((Vec3i)$$4, $$10, $$6, $$11);
/*  89 */           tryPlaceLeaf($$0, $$1, $$2, $$3, (BlockPos)$$9);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void placeLeavesRowWithHangingLeavesBelow(LevelSimulatedReader $$0, FoliageSetter $$1, RandomSource $$2, TreeConfiguration $$3, BlockPos $$4, int $$5, int $$6, boolean $$7, float $$8, float $$9) {
/*  98 */     placeLeavesRow($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7);
/*     */     
/* 100 */     int $$10 = $$7 ? 1 : 0;
/* 101 */     BlockPos $$11 = $$4.below();
/* 102 */     BlockPos.MutableBlockPos $$12 = new BlockPos.MutableBlockPos();
/* 103 */     for (Direction $$13 : Direction.Plane.HORIZONTAL) {
/* 104 */       Direction $$14 = $$13.getClockWise();
/* 105 */       int $$15 = ($$14.getAxisDirection() == Direction.AxisDirection.POSITIVE) ? ($$5 + $$10) : $$5;
/*     */       
/* 107 */       $$12.setWithOffset((Vec3i)$$4, 0, $$6 - 1, 0)
/* 108 */         .move($$14, $$15)
/* 109 */         .move($$13, -$$5);
/*     */       
/* 111 */       for (int $$16 = -$$5; $$16 < $$5 + $$10; $$16++, $$12.move($$13)) {
/*     */ 
/*     */         
/* 114 */         boolean $$17 = $$1.isSet((BlockPos)$$12.move(Direction.UP));
/* 115 */         $$12.move(Direction.DOWN);
/*     */         
/* 117 */         if ($$17)
/*     */         {
/*     */           
/* 120 */           if (tryPlaceExtension($$0, $$1, $$2, $$3, $$8, $$11, $$12)) {
/*     */ 
/*     */             
/* 123 */             $$12.move(Direction.DOWN);
/* 124 */             tryPlaceExtension($$0, $$1, $$2, $$3, $$9, $$11, $$12);
/* 125 */             $$12.move(Direction.UP);
/*     */           }  } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   private static boolean tryPlaceExtension(LevelSimulatedReader $$0, FoliageSetter $$1, RandomSource $$2, TreeConfiguration $$3, float $$4, BlockPos $$5, BlockPos.MutableBlockPos $$6) {
/* 131 */     if ($$6.distManhattan((Vec3i)$$5) >= 7) {
/* 132 */       return false;
/*     */     }
/* 134 */     if ($$2.nextFloat() > $$4) {
/* 135 */       return false;
/*     */     }
/* 137 */     return tryPlaceLeaf($$0, $$1, $$2, $$3, (BlockPos)$$6);
/*     */   }
/*     */   
/*     */   protected static boolean tryPlaceLeaf(LevelSimulatedReader $$0, FoliageSetter $$1, RandomSource $$2, TreeConfiguration $$3, BlockPos $$4) {
/* 141 */     if (!TreeFeature.validTreePos($$0, $$4)) {
/* 142 */       return false;
/*     */     }
/* 144 */     BlockState $$5 = $$3.foliageProvider.getState($$2, $$4);
/* 145 */     if ($$5.hasProperty((Property)BlockStateProperties.WATERLOGGED)) {
/* 146 */       $$5 = (BlockState)$$5.setValue((Property)BlockStateProperties.WATERLOGGED, Boolean.valueOf($$0.isFluidAtPosition($$4, $$0 -> $$0.isSourceOfType((Fluid)Fluids.WATER))));
/*     */     }
/* 148 */     $$1.set($$4, $$5);
/* 149 */     return true;
/*     */   } protected abstract FoliagePlacerType<?> type(); protected abstract void createFoliage(LevelSimulatedReader paramLevelSimulatedReader, FoliageSetter paramFoliageSetter, RandomSource paramRandomSource, TreeConfiguration paramTreeConfiguration, int paramInt1, FoliageAttachment paramFoliageAttachment, int paramInt2, int paramInt3, int paramInt4);
/*     */   public abstract int foliageHeight(RandomSource paramRandomSource, int paramInt, TreeConfiguration paramTreeConfiguration);
/*     */   protected abstract boolean shouldSkipLocation(RandomSource paramRandomSource, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean);
/*     */   public static interface FoliageSetter {
/*     */     void set(BlockPos param1BlockPos, BlockState param1BlockState);
/*     */     boolean isSet(BlockPos param1BlockPos); }
/*     */   public static final class FoliageAttachment { private final BlockPos pos;
/*     */     public FoliageAttachment(BlockPos $$0, int $$1, boolean $$2) {
/* 158 */       this.pos = $$0;
/* 159 */       this.radiusOffset = $$1;
/* 160 */       this.doubleTrunk = $$2;
/*     */     }
/*     */     private final int radiusOffset; private final boolean doubleTrunk;
/*     */     public BlockPos pos() {
/* 164 */       return this.pos;
/*     */     }
/*     */     
/*     */     public int radiusOffset() {
/* 168 */       return this.radiusOffset;
/*     */     }
/*     */     
/*     */     public boolean doubleTrunk() {
/* 172 */       return this.doubleTrunk;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\foliageplacers\FoliagePlacer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */