/*     */ package net.minecraft.world.level;
/*     */ 
/*     */ import java.util.Optional;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface BlockGetter
/*     */   extends LevelHeightAccessor
/*     */ {
/*     */   default <T extends BlockEntity> Optional<T> getBlockEntity(BlockPos $$0, BlockEntityType<T> $$1) {
/*  37 */     BlockEntity $$2 = getBlockEntity($$0);
/*  38 */     if ($$2 == null || $$2.getType() != $$1) {
/*  39 */       return Optional.empty();
/*     */     }
/*  41 */     return Optional.of((T)$$2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default int getLightEmission(BlockPos $$0) {
/*  49 */     return getBlockState($$0).getLightEmission();
/*     */   }
/*     */   
/*     */   default int getMaxLightLevel() {
/*  53 */     return 15;
/*     */   }
/*     */   
/*     */   default Stream<BlockState> getBlockStates(AABB $$0) {
/*  57 */     return BlockPos.betweenClosedStream($$0).map(this::getBlockState);
/*     */   }
/*     */   
/*     */   default BlockHitResult isBlockInLine(ClipBlockStateContext $$0) {
/*  61 */     return traverseBlocks($$0.getFrom(), $$0.getTo(), $$0, ($$0, $$1) -> {
/*     */           BlockState $$2 = getBlockState($$1);
/*     */           Vec3 $$3 = $$0.getFrom().subtract($$0.getTo());
/*     */           return $$0.isTargetBlock().test($$2) ? new BlockHitResult($$0.getTo(), Direction.getNearest($$3.x, $$3.y, $$3.z), BlockPos.containing((Position)$$0.getTo()), false) : null;
/*     */         }$$0 -> {
/*     */           Vec3 $$1 = $$0.getFrom().subtract($$0.getTo());
/*     */           return BlockHitResult.miss($$0.getTo(), Direction.getNearest($$1.x, $$1.y, $$1.z), BlockPos.containing((Position)$$0.getTo()));
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   default BlockHitResult clip(ClipContext $$0) {
/*  73 */     return traverseBlocks($$0.getFrom(), $$0.getTo(), $$0, ($$0, $$1) -> {
/*     */           BlockState $$2 = getBlockState($$1);
/*     */           FluidState $$3 = getFluidState($$1);
/*     */           Vec3 $$4 = $$0.getFrom();
/*     */           Vec3 $$5 = $$0.getTo();
/*     */           VoxelShape $$6 = $$0.getBlockShape($$2, this, $$1);
/*     */           BlockHitResult $$7 = clipWithInteractionOverride($$4, $$5, $$1, $$6, $$2);
/*     */           VoxelShape $$8 = $$0.getFluidShape($$3, this, $$1);
/*     */           BlockHitResult $$9 = $$8.clip($$4, $$5, $$1);
/*     */           double $$10 = ($$7 == null) ? Double.MAX_VALUE : $$0.getFrom().distanceToSqr($$7.getLocation());
/*     */           double $$11 = ($$9 == null) ? Double.MAX_VALUE : $$0.getFrom().distanceToSqr($$9.getLocation());
/*     */           return ($$10 <= $$11) ? $$7 : $$9;
/*     */         }$$0 -> {
/*     */           Vec3 $$1 = $$0.getFrom().subtract($$0.getTo());
/*     */           return BlockHitResult.miss($$0.getTo(), Direction.getNearest($$1.x, $$1.y, $$1.z), BlockPos.containing((Position)$$0.getTo()));
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   default BlockHitResult clipWithInteractionOverride(Vec3 $$0, Vec3 $$1, BlockPos $$2, VoxelShape $$3, BlockState $$4) {
/*  99 */     BlockHitResult $$5 = $$3.clip($$0, $$1, $$2);
/* 100 */     if ($$5 != null) {
/*     */       
/* 102 */       BlockHitResult $$6 = $$4.getInteractionShape(this, $$2).clip($$0, $$1, $$2);
/* 103 */       if ($$6 != null && $$6.getLocation().subtract($$0).lengthSqr() < $$5.getLocation().subtract($$0).lengthSqr()) {
/* 104 */         return $$5.withDirection($$6.getDirection());
/*     */       }
/*     */     } 
/* 107 */     return $$5;
/*     */   }
/*     */   
/*     */   default double getBlockFloorHeight(VoxelShape $$0, Supplier<VoxelShape> $$1) {
/* 111 */     if (!$$0.isEmpty()) {
/* 112 */       return $$0.max(Direction.Axis.Y);
/*     */     }
/*     */ 
/*     */     
/* 116 */     double $$2 = ((VoxelShape)$$1.get()).max(Direction.Axis.Y);
/* 117 */     if ($$2 >= 1.0D) {
/* 118 */       return $$2 - 1.0D;
/*     */     }
/*     */     
/* 121 */     return Double.NEGATIVE_INFINITY;
/*     */   }
/*     */   
/*     */   default double getBlockFloorHeight(BlockPos $$0) {
/* 125 */     return getBlockFloorHeight(getBlockState($$0).getCollisionShape(this, $$0), () -> {
/*     */           BlockPos $$1 = $$0.below();
/*     */           return getBlockState($$1).getCollisionShape(this, $$1);
/*     */         });
/*     */   }
/*     */   
/*     */   static <T, C> T traverseBlocks(Vec3 $$0, Vec3 $$1, C $$2, BiFunction<C, BlockPos, T> $$3, Function<C, T> $$4) {
/* 132 */     if ($$0.equals($$1)) {
/* 133 */       return $$4.apply($$2);
/*     */     }
/*     */ 
/*     */     
/* 137 */     double $$5 = Mth.lerp(-1.0E-7D, $$1.x, $$0.x);
/* 138 */     double $$6 = Mth.lerp(-1.0E-7D, $$1.y, $$0.y);
/* 139 */     double $$7 = Mth.lerp(-1.0E-7D, $$1.z, $$0.z);
/*     */     
/* 141 */     double $$8 = Mth.lerp(-1.0E-7D, $$0.x, $$1.x);
/* 142 */     double $$9 = Mth.lerp(-1.0E-7D, $$0.y, $$1.y);
/* 143 */     double $$10 = Mth.lerp(-1.0E-7D, $$0.z, $$1.z);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 148 */     int $$11 = Mth.floor($$8);
/* 149 */     int $$12 = Mth.floor($$9);
/* 150 */     int $$13 = Mth.floor($$10);
/*     */     
/* 152 */     BlockPos.MutableBlockPos $$14 = new BlockPos.MutableBlockPos($$11, $$12, $$13);
/* 153 */     T $$15 = $$3.apply($$2, $$14);
/* 154 */     if ($$15 != null) {
/* 155 */       return $$15;
/*     */     }
/*     */     
/* 158 */     double $$16 = $$5 - $$8;
/* 159 */     double $$17 = $$6 - $$9;
/* 160 */     double $$18 = $$7 - $$10;
/*     */     
/* 162 */     int $$19 = Mth.sign($$16);
/* 163 */     int $$20 = Mth.sign($$17);
/* 164 */     int $$21 = Mth.sign($$18);
/*     */     
/* 166 */     double $$22 = ($$19 == 0) ? Double.MAX_VALUE : ($$19 / $$16);
/* 167 */     double $$23 = ($$20 == 0) ? Double.MAX_VALUE : ($$20 / $$17);
/* 168 */     double $$24 = ($$21 == 0) ? Double.MAX_VALUE : ($$21 / $$18);
/*     */     
/* 170 */     double $$25 = $$22 * (($$19 > 0) ? (1.0D - Mth.frac($$8)) : Mth.frac($$8));
/* 171 */     double $$26 = $$23 * (($$20 > 0) ? (1.0D - Mth.frac($$9)) : Mth.frac($$9));
/* 172 */     double $$27 = $$24 * (($$21 > 0) ? (1.0D - Mth.frac($$10)) : Mth.frac($$10));
/*     */     
/* 174 */     while ($$25 <= 1.0D || $$26 <= 1.0D || $$27 <= 1.0D) {
/* 175 */       if ($$25 < $$26) {
/* 176 */         if ($$25 < $$27) {
/* 177 */           $$11 += $$19;
/* 178 */           $$25 += $$22;
/*     */         } else {
/* 180 */           $$13 += $$21;
/* 181 */           $$27 += $$24;
/*     */         }
/*     */       
/* 184 */       } else if ($$26 < $$27) {
/* 185 */         $$12 += $$20;
/* 186 */         $$26 += $$23;
/*     */       } else {
/* 188 */         $$13 += $$21;
/* 189 */         $$27 += $$24;
/*     */       } 
/*     */ 
/*     */       
/* 193 */       T $$28 = $$3.apply($$2, $$14.set($$11, $$12, $$13));
/* 194 */       if ($$28 != null) {
/* 195 */         return $$28;
/*     */       }
/*     */     } 
/*     */     
/* 199 */     return $$4.apply($$2);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   BlockEntity getBlockEntity(BlockPos paramBlockPos);
/*     */   
/*     */   BlockState getBlockState(BlockPos paramBlockPos);
/*     */   
/*     */   FluidState getFluidState(BlockPos paramBlockPos);
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\BlockGetter.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */