/*     */ package net.minecraft.world.level.chunk;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*     */ import java.util.List;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ enum null
/*     */ {
/*     */   private final ThreadLocal<List<ObjectSet<BlockPos>>> queue;
/*     */   
/*     */   null(boolean $$0, Block... $$1) {
/* 318 */     this.queue = ThreadLocal.withInitial(() -> Lists.newArrayListWithCapacity(7));
/*     */   }
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 322 */     BlockState $$6 = $$0.updateShape($$1, $$3.getBlockState($$5), $$3, $$4, $$5);
/* 323 */     if ($$0 != $$6) {
/* 324 */       int $$7 = ((Integer)$$6.getValue((Property)BlockStateProperties.DISTANCE)).intValue();
/* 325 */       List<ObjectSet<BlockPos>> $$8 = this.queue.get();
/* 326 */       if ($$8.isEmpty()) {
/* 327 */         for (int $$9 = 0; $$9 < 7; $$9++) {
/* 328 */           $$8.add(new ObjectOpenHashSet());
/*     */         }
/*     */       }
/* 331 */       ((ObjectSet)$$8.get($$7)).add($$4.immutable());
/*     */     } 
/* 333 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void processChunk(LevelAccessor $$0) {
/* 338 */     BlockPos.MutableBlockPos $$1 = new BlockPos.MutableBlockPos();
/*     */     
/* 340 */     List<ObjectSet<BlockPos>> $$2 = this.queue.get();
/* 341 */     for (int $$3 = 2; $$3 < $$2.size(); $$3++) {
/* 342 */       int $$4 = $$3 - 1;
/* 343 */       ObjectSet<BlockPos> $$5 = $$2.get($$4);
/* 344 */       ObjectSet<BlockPos> $$6 = $$2.get($$3);
/*     */       
/* 346 */       for (ObjectIterator<BlockPos> objectIterator = $$5.iterator(); objectIterator.hasNext(); ) { BlockPos $$7 = objectIterator.next();
/* 347 */         BlockState $$8 = $$0.getBlockState($$7);
/* 348 */         if (((Integer)$$8.getValue((Property)BlockStateProperties.DISTANCE)).intValue() < $$4) {
/*     */           continue;
/*     */         }
/*     */         
/* 352 */         $$0.setBlock($$7, (BlockState)$$8.setValue((Property)BlockStateProperties.DISTANCE, Integer.valueOf($$4)), 18);
/*     */         
/* 354 */         if ($$3 != 7) {
/* 355 */           for (Direction $$9 : DIRECTIONS) {
/* 356 */             $$1.setWithOffset((Vec3i)$$7, $$9);
/* 357 */             BlockState $$10 = $$0.getBlockState((BlockPos)$$1);
/*     */             
/* 359 */             if ($$10.hasProperty((Property)BlockStateProperties.DISTANCE) && ((Integer)$$8.getValue((Property)BlockStateProperties.DISTANCE)).intValue() > $$3) {
/* 360 */               $$6.add($$1.immutable());
/*     */             }
/*     */           } 
/*     */         } }
/*     */     
/*     */     } 
/*     */     
/* 367 */     $$2.clear();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\chunk\UpgradeData$BlockFixers$4.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */