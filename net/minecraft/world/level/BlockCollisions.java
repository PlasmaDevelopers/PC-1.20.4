/*     */ package net.minecraft.world.level;
/*     */ 
/*     */ import com.google.common.collect.AbstractIterator;
/*     */ import java.util.function.BiFunction;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Cursor3D;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.shapes.BooleanOp;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.Shapes;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class BlockCollisions<T>
/*     */   extends AbstractIterator<T>
/*     */ {
/*     */   private final AABB box;
/*     */   private final CollisionContext context;
/*     */   private final Cursor3D cursor;
/*     */   private final BlockPos.MutableBlockPos pos;
/*     */   private final VoxelShape entityShape;
/*     */   private final CollisionGetter collisionGetter;
/*     */   private final boolean onlySuffocatingBlocks;
/*     */   @Nullable
/*     */   private BlockGetter cachedBlockGetter;
/*     */   private long cachedBlockGetterPos;
/*     */   private final BiFunction<BlockPos.MutableBlockPos, VoxelShape, T> resultProvider;
/*     */   
/*     */   public BlockCollisions(CollisionGetter $$0, @Nullable Entity $$1, AABB $$2, boolean $$3, BiFunction<BlockPos.MutableBlockPos, VoxelShape, T> $$4) {
/*  35 */     this.context = ($$1 == null) ? CollisionContext.empty() : CollisionContext.of($$1);
/*  36 */     this.pos = new BlockPos.MutableBlockPos();
/*  37 */     this.entityShape = Shapes.create($$2);
/*  38 */     this.collisionGetter = $$0;
/*  39 */     this.box = $$2;
/*  40 */     this.onlySuffocatingBlocks = $$3;
/*  41 */     this.resultProvider = $$4;
/*     */ 
/*     */     
/*  44 */     int $$5 = Mth.floor($$2.minX - 1.0E-7D) - 1;
/*  45 */     int $$6 = Mth.floor($$2.maxX + 1.0E-7D) + 1;
/*  46 */     int $$7 = Mth.floor($$2.minY - 1.0E-7D) - 1;
/*  47 */     int $$8 = Mth.floor($$2.maxY + 1.0E-7D) + 1;
/*  48 */     int $$9 = Mth.floor($$2.minZ - 1.0E-7D) - 1;
/*  49 */     int $$10 = Mth.floor($$2.maxZ + 1.0E-7D) + 1;
/*  50 */     this.cursor = new Cursor3D($$5, $$7, $$9, $$6, $$8, $$10);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private BlockGetter getChunk(int $$0, int $$1) {
/*  55 */     int $$2 = SectionPos.blockToSectionCoord($$0);
/*  56 */     int $$3 = SectionPos.blockToSectionCoord($$1);
/*     */     
/*  58 */     long $$4 = ChunkPos.asLong($$2, $$3);
/*  59 */     if (this.cachedBlockGetter != null && this.cachedBlockGetterPos == $$4) {
/*  60 */       return this.cachedBlockGetter;
/*     */     }
/*  62 */     BlockGetter $$5 = this.collisionGetter.getChunkForCollisions($$2, $$3);
/*  63 */     this.cachedBlockGetter = $$5;
/*  64 */     this.cachedBlockGetterPos = $$4;
/*  65 */     return $$5;
/*     */   }
/*     */ 
/*     */   
/*     */   protected T computeNext() {
/*  70 */     while (this.cursor.advance()) {
/*  71 */       int $$0 = this.cursor.nextX();
/*  72 */       int $$1 = this.cursor.nextY();
/*  73 */       int $$2 = this.cursor.nextZ();
/*     */       
/*  75 */       int $$3 = this.cursor.getNextType();
/*     */       
/*  77 */       if ($$3 == 3) {
/*     */         continue;
/*     */       }
/*     */       
/*  81 */       BlockGetter $$4 = getChunk($$0, $$2);
/*     */       
/*  83 */       if ($$4 == null) {
/*     */         continue;
/*     */       }
/*     */       
/*  87 */       this.pos.set($$0, $$1, $$2);
/*  88 */       BlockState $$5 = $$4.getBlockState((BlockPos)this.pos);
/*     */       
/*  90 */       if (this.onlySuffocatingBlocks && !$$5.isSuffocating($$4, (BlockPos)this.pos)) {
/*     */         continue;
/*     */       }
/*     */       
/*  94 */       if ($$3 == 1 && !$$5.hasLargeCollisionShape()) {
/*     */         continue;
/*     */       }
/*  97 */       if ($$3 == 2 && !$$5.is(Blocks.MOVING_PISTON)) {
/*     */         continue;
/*     */       }
/*     */       
/* 101 */       VoxelShape $$6 = $$5.getCollisionShape(this.collisionGetter, (BlockPos)this.pos, this.context);
/*     */       
/* 103 */       if ($$6 == Shapes.block()) {
/* 104 */         if (this.box.intersects($$0, $$1, $$2, $$0 + 1.0D, $$1 + 1.0D, $$2 + 1.0D))
/* 105 */           return this.resultProvider.apply(this.pos, $$6.move($$0, $$1, $$2)); 
/*     */         continue;
/*     */       } 
/* 108 */       VoxelShape $$7 = $$6.move($$0, $$1, $$2);
/* 109 */       if (!$$7.isEmpty() && Shapes.joinIsNotEmpty($$7, this.entityShape, BooleanOp.AND)) {
/* 110 */         return this.resultProvider.apply(this.pos, $$7);
/*     */       }
/*     */     } 
/*     */     
/* 114 */     return (T)endOfData();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\BlockCollisions.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */