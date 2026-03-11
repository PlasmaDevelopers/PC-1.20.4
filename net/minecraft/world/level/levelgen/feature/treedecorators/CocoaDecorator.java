/*    */ package net.minecraft.world.level.levelgen.feature.treedecorators;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.block.CocoaBlock;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ 
/*    */ public class CocoaDecorator extends TreeDecorator {
/*    */   public static final Codec<CocoaDecorator> CODEC;
/*    */   
/*    */   static {
/* 13 */     CODEC = Codec.floatRange(0.0F, 1.0F).fieldOf("probability").xmap(CocoaDecorator::new, $$0 -> Float.valueOf($$0.probability)).codec();
/*    */   }
/*    */   private final float probability;
/*    */   
/*    */   public CocoaDecorator(float $$0) {
/* 18 */     this.probability = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected TreeDecoratorType<?> type() {
/* 23 */     return TreeDecoratorType.COCOA;
/*    */   }
/*    */ 
/*    */   
/*    */   public void place(TreeDecorator.Context $$0) {
/* 28 */     RandomSource $$1 = $$0.random();
/* 29 */     if ($$1.nextFloat() >= this.probability) {
/*    */       return;
/*    */     }
/*    */     
/* 33 */     ObjectArrayList<BlockPos> objectArrayList = $$0.logs();
/*    */     
/* 35 */     int $$3 = ((BlockPos)objectArrayList.get(0)).getY();
/* 36 */     objectArrayList.stream()
/* 37 */       .filter($$1 -> ($$1.getY() - $$0 <= 2))
/* 38 */       .forEach($$2 -> {
/*    */           for (Direction $$3 : Direction.Plane.HORIZONTAL) {
/*    */             if ($$0.nextFloat() <= 0.25F) {
/*    */               Direction $$4 = $$3.getOpposite();
/*    */               BlockPos $$5 = $$2.offset($$4.getStepX(), 0, $$4.getStepZ());
/*    */               if ($$1.isAir($$5))
/*    */                 $$1.setBlock($$5, (BlockState)((BlockState)Blocks.COCOA.defaultBlockState().setValue((Property)CocoaBlock.AGE, Integer.valueOf($$0.nextInt(3)))).setValue((Property)CocoaBlock.FACING, (Comparable)$$3)); 
/*    */             } 
/*    */           } 
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\treedecorators\CocoaDecorator.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */