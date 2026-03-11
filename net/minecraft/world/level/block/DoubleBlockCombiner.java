/*    */ package net.minecraft.world.level.block;
/*    */ import java.util.function.BiPredicate;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.DirectionProperty;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ 
/*    */ public class DoubleBlockCombiner {
/*    */   public enum BlockType {
/* 16 */     SINGLE,
/* 17 */     FIRST,
/* 18 */     SECOND;
/*    */   }
/*    */ 
/*    */   
/*    */   public static <S extends BlockEntity> NeighborCombineResult<S> combineWithNeigbour(BlockEntityType<S> $$0, Function<BlockState, BlockType> $$1, Function<BlockState, Direction> $$2, DirectionProperty $$3, BlockState $$4, LevelAccessor $$5, BlockPos $$6, BiPredicate<LevelAccessor, BlockPos> $$7) {
/* 23 */     BlockEntity blockEntity = $$0.getBlockEntity((BlockGetter)$$5, $$6);
/* 24 */     if (blockEntity == null) {
/* 25 */       return Combiner::acceptNone;
/*    */     }
/*    */     
/* 28 */     if ($$7.test($$5, $$6)) {
/* 29 */       return Combiner::acceptNone;
/*    */     }
/*    */     
/* 32 */     BlockType $$9 = $$1.apply($$4);
/*    */     
/* 34 */     boolean $$10 = ($$9 == BlockType.SINGLE);
/* 35 */     boolean $$11 = ($$9 == BlockType.FIRST);
/*    */     
/* 37 */     if ($$10) {
/* 38 */       return new NeighborCombineResult.Single<>((S)blockEntity);
/*    */     }
/*    */     
/* 41 */     BlockPos $$12 = $$6.relative($$2.apply($$4));
/* 42 */     BlockState $$13 = $$5.getBlockState($$12);
/* 43 */     if ($$13.is($$4.getBlock())) {
/* 44 */       BlockType $$14 = $$1.apply($$13);
/* 45 */       if ($$14 != BlockType.SINGLE && $$9 != $$14 && $$13.getValue((Property)$$3) == $$4.getValue((Property)$$3)) {
/* 46 */         if ($$7.test($$5, $$12)) {
/* 47 */           return Combiner::acceptNone;
/*    */         }
/*    */         
/* 50 */         BlockEntity blockEntity1 = $$0.getBlockEntity((BlockGetter)$$5, $$12);
/* 51 */         if (blockEntity1 != null) {
/* 52 */           BlockEntity blockEntity2 = $$11 ? blockEntity : blockEntity1;
/* 53 */           BlockEntity blockEntity3 = $$11 ? blockEntity1 : blockEntity;
/* 54 */           return new NeighborCombineResult.Double<>((S)blockEntity2, (S)blockEntity3);
/*    */         } 
/*    */       } 
/*    */     } 
/* 58 */     return new NeighborCombineResult.Single<>((S)blockEntity);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static final class Double<S>
/*    */     implements NeighborCombineResult<S>
/*    */   {
/*    */     private final S first;
/*    */ 
/*    */ 
/*    */     
/*    */     private final S second;
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public Double(S $$0, S $$1) {
/* 77 */       this.first = $$0;
/* 78 */       this.second = $$1;
/*    */     }
/*    */     
/*    */     public <T> T apply(DoubleBlockCombiner.Combiner<? super S, T> $$0)
/*    */     {
/* 83 */       return $$0.acceptDouble(this.first, this.second); } } public static interface NeighborCombineResult<S> { <T> T apply(DoubleBlockCombiner.Combiner<? super S, T> param1Combiner); public static final class Double<S> implements NeighborCombineResult<S> { private final S first; public <T> T apply(DoubleBlockCombiner.Combiner<? super S, T> $$0) { return $$0.acceptDouble(this.first, this.second); }
/*    */        private final S second;
/*    */       public Double(S $$0, S $$1) {
/*    */         this.first = $$0;
/*    */         this.second = $$1;
/*    */       } }
/*    */     public static final class Single<S> implements NeighborCombineResult<S> { private final S single;
/*    */       public Single(S $$0) {
/* 91 */         this.single = $$0;
/*    */       }
/*    */       
/*    */       public <T> T apply(DoubleBlockCombiner.Combiner<? super S, T> $$0)
/*    */       {
/* 96 */         return $$0.acceptSingle(this.single); } } } public static final class Single<S> implements NeighborCombineResult<S> { private final S single; public Single(S $$0) { this.single = $$0; } public <T> T apply(DoubleBlockCombiner.Combiner<? super S, T> $$0) { return $$0.acceptSingle(this.single); }
/*    */      }
/*    */ 
/*    */   
/*    */   public static interface Combiner<S, T> {
/*    */     T acceptDouble(S param1S1, S param1S2);
/*    */     
/*    */     T acceptSingle(S param1S);
/*    */     
/*    */     T acceptNone();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\DoubleBlockCombiner.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */