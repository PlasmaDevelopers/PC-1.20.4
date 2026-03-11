/*     */ package net.minecraft.world.level;
/*     */ 
/*     */ import com.google.common.collect.Iterables;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.stream.Stream;
/*     */ import java.util.stream.StreamSupport;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.border.WorldBorder;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import net.minecraft.world.phys.shapes.BooleanOp;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.Shapes;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public interface CollisionGetter
/*     */   extends BlockGetter
/*     */ {
/*     */   default boolean isUnobstructed(@Nullable Entity $$0, VoxelShape $$1) {
/*  27 */     return true;
/*     */   }
/*     */   
/*     */   default boolean isUnobstructed(BlockState $$0, BlockPos $$1, CollisionContext $$2) {
/*  31 */     VoxelShape $$3 = $$0.getCollisionShape(this, $$1, $$2);
/*  32 */     return ($$3.isEmpty() || isUnobstructed(null, $$3.move($$1.getX(), $$1.getY(), $$1.getZ())));
/*     */   }
/*     */   
/*     */   default boolean isUnobstructed(Entity $$0) {
/*  36 */     return isUnobstructed($$0, Shapes.create($$0.getBoundingBox()));
/*     */   }
/*     */   
/*     */   default boolean noCollision(AABB $$0) {
/*  40 */     return noCollision(null, $$0);
/*     */   }
/*     */   
/*     */   default boolean noCollision(Entity $$0) {
/*  44 */     return noCollision($$0, $$0.getBoundingBox());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default boolean noCollision(@Nullable Entity $$0, AABB $$1) {
/*  51 */     for (VoxelShape $$2 : getBlockCollisions($$0, $$1)) {
/*  52 */       if (!$$2.isEmpty()) {
/*  53 */         return false;
/*     */       }
/*     */     } 
/*     */     
/*  57 */     if (!getEntityCollisions($$0, $$1).isEmpty()) {
/*  58 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  62 */     if ($$0 != null) {
/*  63 */       VoxelShape $$3 = borderCollision($$0, $$1);
/*  64 */       return ($$3 == null || !Shapes.joinIsNotEmpty($$3, Shapes.create($$1), BooleanOp.AND));
/*     */     } 
/*     */     
/*  67 */     return true;
/*     */   }
/*     */   
/*     */   default boolean noBlockCollision(@Nullable Entity $$0, AABB $$1) {
/*  71 */     for (VoxelShape $$2 : getBlockCollisions($$0, $$1)) {
/*  72 */       if (!$$2.isEmpty()) {
/*  73 */         return false;
/*     */       }
/*     */     } 
/*  76 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   default Iterable<VoxelShape> getCollisions(@Nullable Entity $$0, AABB $$1) {
/*  82 */     List<VoxelShape> $$2 = getEntityCollisions($$0, $$1);
/*  83 */     Iterable<VoxelShape> $$3 = getBlockCollisions($$0, $$1);
/*  84 */     return $$2.isEmpty() ? $$3 : Iterables.concat($$2, $$3);
/*     */   }
/*     */   
/*     */   default Iterable<VoxelShape> getBlockCollisions(@Nullable Entity $$0, AABB $$1) {
/*  88 */     return () -> new BlockCollisions(this, $$0, $$1, false, ());
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private VoxelShape borderCollision(Entity $$0, AABB $$1) {
/*  93 */     WorldBorder $$2 = getWorldBorder();
/*  94 */     return $$2.isInsideCloseToBorder($$0, $$1) ? $$2.getCollisionShape() : null;
/*     */   }
/*     */   
/*     */   default boolean collidesWithSuffocatingBlock(@Nullable Entity $$0, AABB $$1) {
/*  98 */     BlockCollisions<VoxelShape> $$2 = new BlockCollisions<>(this, $$0, $$1, true, ($$0, $$1) -> $$1);
/*  99 */     while ($$2.hasNext()) {
/* 100 */       if (!((VoxelShape)$$2.next()).isEmpty()) {
/* 101 */         return true;
/*     */       }
/*     */     } 
/* 104 */     return false;
/*     */   }
/*     */   
/*     */   default Optional<BlockPos> findSupportingBlock(Entity $$0, AABB $$1) {
/* 108 */     BlockPos $$2 = null;
/* 109 */     double $$3 = Double.MAX_VALUE;
/* 110 */     BlockCollisions<BlockPos> $$4 = new BlockCollisions<>(this, $$0, $$1, false, ($$0, $$1) -> $$0);
/* 111 */     while ($$4.hasNext()) {
/* 112 */       BlockPos $$5 = (BlockPos)$$4.next();
/* 113 */       double $$6 = $$5.distToCenterSqr((Position)$$0.position());
/* 114 */       if ($$6 < $$3 || ($$6 == $$3 && ($$2 == null || $$2.compareTo((Vec3i)$$5) < 0))) {
/* 115 */         $$2 = $$5.immutable();
/* 116 */         $$3 = $$6;
/*     */       } 
/*     */     } 
/* 119 */     return Optional.ofNullable($$2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default Optional<Vec3> findFreePosition(@Nullable Entity $$0, VoxelShape $$1, Vec3 $$2, double $$3, double $$4, double $$5) {
/* 129 */     if ($$1.isEmpty()) {
/* 130 */       return Optional.empty();
/*     */     }
/*     */     
/* 133 */     AABB $$6 = $$1.bounds().inflate($$3, $$4, $$5);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 139 */     VoxelShape $$7 = StreamSupport.stream(getBlockCollisions($$0, $$6).spliterator(), false).filter($$0 -> (getWorldBorder() == null || getWorldBorder().isWithinBounds($$0.bounds()))).flatMap($$0 -> $$0.toAabbs().stream()).map($$3 -> $$3.inflate($$0 / 2.0D, $$1 / 2.0D, $$2 / 2.0D)).map(Shapes::create).reduce(Shapes.empty(), Shapes::or);
/*     */ 
/*     */     
/* 142 */     VoxelShape $$8 = Shapes.join($$1, $$7, BooleanOp.ONLY_FIRST);
/*     */     
/* 144 */     return $$8.closestPointTo($$2);
/*     */   }
/*     */   
/*     */   WorldBorder getWorldBorder();
/*     */   
/*     */   @Nullable
/*     */   BlockGetter getChunkForCollisions(int paramInt1, int paramInt2);
/*     */   
/*     */   List<VoxelShape> getEntityCollisions(@Nullable Entity paramEntity, AABB paramAABB);
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\CollisionGetter.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */