/*     */ package net.minecraft.world.entity.vehicle;
/*     */ 
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Pose;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.CollisionGetter;
/*     */ import net.minecraft.world.level.block.TrapDoorBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import net.minecraft.world.phys.shapes.Shapes;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class DismountHelper {
/*     */   public static int[][] offsetsForDirection(Direction $$0) {
/*  25 */     Direction $$1 = $$0.getClockWise();
/*  26 */     Direction $$2 = $$1.getOpposite();
/*  27 */     Direction $$3 = $$0.getOpposite();
/*     */     
/*  29 */     return new int[][] { { $$1
/*  30 */           .getStepX(), $$1.getStepZ() }, { $$2
/*  31 */           .getStepX(), $$2.getStepZ() }, { $$3
/*  32 */           .getStepX() + $$1.getStepX(), $$3.getStepZ() + $$1.getStepZ() }, { $$3
/*  33 */           .getStepX() + $$2.getStepX(), $$3.getStepZ() + $$2.getStepZ() }, { $$0
/*  34 */           .getStepX() + $$1.getStepX(), $$0.getStepZ() + $$1.getStepZ() }, { $$0
/*  35 */           .getStepX() + $$2.getStepX(), $$0.getStepZ() + $$2.getStepZ() }, { $$3
/*  36 */           .getStepX(), $$3.getStepZ() }, { $$0
/*  37 */           .getStepX(), $$0.getStepZ() } };
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isBlockFloorValid(double $$0) {
/*  42 */     return (!Double.isInfinite($$0) && $$0 < 1.0D);
/*     */   }
/*     */   
/*     */   public static boolean canDismountTo(CollisionGetter $$0, LivingEntity $$1, AABB $$2) {
/*  46 */     Iterable<VoxelShape> $$3 = $$0.getBlockCollisions((Entity)$$1, $$2);
/*  47 */     for (VoxelShape $$4 : $$3) {
/*  48 */       if (!$$4.isEmpty()) {
/*  49 */         return false;
/*     */       }
/*     */     } 
/*     */     
/*  53 */     if (!$$0.getWorldBorder().isWithinBounds($$2)) {
/*  54 */       return false;
/*     */     }
/*     */     
/*  57 */     return true;
/*     */   }
/*     */   
/*     */   public static boolean canDismountTo(CollisionGetter $$0, Vec3 $$1, LivingEntity $$2, Pose $$3) {
/*  61 */     return canDismountTo($$0, $$2, $$2.getLocalBoundsForPose($$3).move($$1));
/*     */   }
/*     */   
/*     */   public static VoxelShape nonClimbableShape(BlockGetter $$0, BlockPos $$1) {
/*  65 */     BlockState $$2 = $$0.getBlockState($$1);
/*  66 */     if ($$2.is(BlockTags.CLIMBABLE) || ($$2.getBlock() instanceof TrapDoorBlock && ((Boolean)$$2.getValue((Property)TrapDoorBlock.OPEN)).booleanValue())) {
/*  67 */       return Shapes.empty();
/*     */     }
/*  69 */     return $$2.getCollisionShape($$0, $$1);
/*     */   }
/*     */   
/*     */   public static double findCeilingFrom(BlockPos $$0, int $$1, Function<BlockPos, VoxelShape> $$2) {
/*  73 */     BlockPos.MutableBlockPos $$3 = $$0.mutable();
/*  74 */     int $$4 = 0;
/*  75 */     while ($$4 < $$1) {
/*  76 */       VoxelShape $$5 = $$2.apply($$3);
/*  77 */       if (!$$5.isEmpty()) {
/*  78 */         return ($$0.getY() + $$4) + $$5.min(Direction.Axis.Y);
/*     */       }
/*  80 */       $$4++;
/*  81 */       $$3.move(Direction.UP);
/*     */     } 
/*  83 */     return Double.POSITIVE_INFINITY;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static Vec3 findSafeDismountLocation(EntityType<?> $$0, CollisionGetter $$1, BlockPos $$2, boolean $$3) {
/*  88 */     if ($$3 && $$0.isBlockDangerous($$1.getBlockState($$2))) {
/*  89 */       return null;
/*     */     }
/*     */     
/*  92 */     double $$4 = $$1.getBlockFloorHeight(nonClimbableShape((BlockGetter)$$1, $$2), () -> nonClimbableShape((BlockGetter)$$0, $$1.below()));
/*  93 */     if (!isBlockFloorValid($$4)) {
/*  94 */       return null;
/*     */     }
/*     */     
/*  97 */     if ($$3 && $$4 <= 0.0D && $$0.isBlockDangerous($$1.getBlockState($$2.below()))) {
/*  98 */       return null;
/*     */     }
/*     */     
/* 101 */     Vec3 $$5 = Vec3.upFromBottomCenterOf((Vec3i)$$2, $$4);
/* 102 */     AABB $$6 = $$0.getDimensions().makeBoundingBox($$5);
/* 103 */     Iterable<VoxelShape> $$7 = $$1.getBlockCollisions(null, $$6);
/* 104 */     for (VoxelShape $$8 : $$7) {
/* 105 */       if (!$$8.isEmpty()) {
/* 106 */         return null;
/*     */       }
/*     */     } 
/*     */     
/* 110 */     if ($$0 == EntityType.PLAYER)
/*     */     {
/* 112 */       if ($$1.getBlockState($$2).is(BlockTags.INVALID_SPAWN_INSIDE) || $$1.getBlockState($$2.above()).is(BlockTags.INVALID_SPAWN_INSIDE)) {
/* 113 */         return null;
/*     */       }
/*     */     }
/*     */     
/* 117 */     if (!$$1.getWorldBorder().isWithinBounds($$6)) {
/* 118 */       return null;
/*     */     }
/*     */     
/* 121 */     return $$5;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\vehicle\DismountHelper.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */