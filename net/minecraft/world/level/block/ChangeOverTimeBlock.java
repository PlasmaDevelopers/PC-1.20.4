/*    */ package net.minecraft.world.level.block;
/*    */ 
/*    */ import java.util.Optional;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface ChangeOverTimeBlock<T extends Enum<T>>
/*    */ {
/*    */   public static final int SCAN_DISTANCE = 4;
/*    */   
/*    */   default void changeOverTime(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/* 22 */     float $$4 = 0.05688889F;
/* 23 */     if ($$3.nextFloat() < 0.05688889F) {
/* 24 */       getNextState($$0, $$1, $$2, $$3).ifPresent($$2 -> $$0.setBlockAndUpdate($$1, $$2));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default Optional<BlockState> getNextState(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/* 35 */     int $$4 = getAge().ordinal();
/* 36 */     int $$5 = 0;
/* 37 */     int $$6 = 0;
/* 38 */     for (BlockPos $$7 : BlockPos.withinManhattan($$2, 4, 4, 4)) {
/* 39 */       int $$8 = $$7.distManhattan((Vec3i)$$2);
/* 40 */       if ($$8 > 4) {
/*    */         break;
/*    */       }
/* 43 */       if ($$7.equals($$2)) {
/*    */         continue;
/*    */       }
/*    */       
/* 47 */       Block block = $$1.getBlockState($$7).getBlock(); if (block instanceof ChangeOverTimeBlock) { ChangeOverTimeBlock<?> $$9 = (ChangeOverTimeBlock)block;
/*    */         
/* 49 */         Enum<?> $$10 = (Enum<?>)$$9.getAge();
/* 50 */         if (getAge().getClass() != $$10.getClass()) {
/*    */           continue;
/*    */         }
/* 53 */         int $$11 = $$10.ordinal();
/* 54 */         if ($$11 < $$4)
/* 55 */           return Optional.empty(); 
/* 56 */         if ($$11 > $$4) {
/* 57 */           $$6++; continue;
/*    */         } 
/* 59 */         $$5++; }
/*    */     
/*    */     } 
/*    */ 
/*    */     
/* 64 */     float $$12 = ($$6 + 1) / ($$6 + $$5 + 1);
/* 65 */     float $$13 = $$12 * $$12 * getChanceModifier();
/*    */     
/* 67 */     if ($$3.nextFloat() < $$13) {
/* 68 */       return getNext($$0);
/*    */     }
/*    */     
/* 71 */     return Optional.empty();
/*    */   }
/*    */   
/*    */   Optional<BlockState> getNext(BlockState paramBlockState);
/*    */   
/*    */   float getChanceModifier();
/*    */   
/*    */   T getAge();
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\ChangeOverTimeBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */