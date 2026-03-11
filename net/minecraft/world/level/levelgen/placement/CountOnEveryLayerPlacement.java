/*    */ package net.minecraft.world.level.levelgen.placement;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.function.Function;
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.util.valueproviders.ConstantInt;
/*    */ import net.minecraft.util.valueproviders.IntProvider;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.levelgen.Heightmap;
/*    */ 
/*    */ @Deprecated
/*    */ public class CountOnEveryLayerPlacement
/*    */   extends PlacementModifier {
/*    */   public static final Codec<CountOnEveryLayerPlacement> CODEC;
/*    */   private final IntProvider count;
/*    */   
/*    */   static {
/* 21 */     CODEC = IntProvider.codec(0, 256).fieldOf("count").xmap(CountOnEveryLayerPlacement::new, $$0 -> $$0.count).codec();
/*    */   }
/*    */ 
/*    */   
/*    */   private CountOnEveryLayerPlacement(IntProvider $$0) {
/* 26 */     this.count = $$0;
/*    */   }
/*    */   
/*    */   public static CountOnEveryLayerPlacement of(IntProvider $$0) {
/* 30 */     return new CountOnEveryLayerPlacement($$0);
/*    */   }
/*    */   
/*    */   public static CountOnEveryLayerPlacement of(int $$0) {
/* 34 */     return of((IntProvider)ConstantInt.of($$0));
/*    */   }
/*    */   
/*    */   public Stream<BlockPos> getPositions(PlacementContext $$0, RandomSource $$1, BlockPos $$2) {
/*    */     boolean $$5;
/* 39 */     Stream.Builder<BlockPos> $$3 = Stream.builder();
/*    */     
/* 41 */     int $$4 = 0;
/*    */     do {
/* 43 */       $$5 = false;
/*    */       
/* 45 */       for (int $$6 = 0; $$6 < this.count.sample($$1); $$6++) {
/* 46 */         int $$7 = $$1.nextInt(16) + $$2.getX();
/* 47 */         int $$8 = $$1.nextInt(16) + $$2.getZ();
/* 48 */         int $$9 = $$0.getHeight(Heightmap.Types.MOTION_BLOCKING, $$7, $$8);
/* 49 */         int $$10 = findOnGroundYPosition($$0, $$7, $$9, $$8, $$4);
/* 50 */         if ($$10 != Integer.MAX_VALUE) {
/* 51 */           $$3.add(new BlockPos($$7, $$10, $$8));
/* 52 */           $$5 = true;
/*    */         } 
/*    */       } 
/* 55 */       $$4++;
/* 56 */     } while ($$5);
/*    */     
/* 58 */     return $$3.build();
/*    */   }
/*    */ 
/*    */   
/*    */   public PlacementModifierType<?> type() {
/* 63 */     return PlacementModifierType.COUNT_ON_EVERY_LAYER;
/*    */   }
/*    */ 
/*    */   
/*    */   private static int findOnGroundYPosition(PlacementContext $$0, int $$1, int $$2, int $$3, int $$4) {
/* 68 */     BlockPos.MutableBlockPos $$5 = new BlockPos.MutableBlockPos($$1, $$2, $$3);
/*    */     
/* 70 */     int $$6 = 0;
/* 71 */     BlockState $$7 = $$0.getBlockState((BlockPos)$$5);
/* 72 */     for (int $$8 = $$2; $$8 >= $$0.getMinBuildHeight() + 1; $$8--) {
/* 73 */       $$5.setY($$8 - 1);
/* 74 */       BlockState $$9 = $$0.getBlockState((BlockPos)$$5);
/* 75 */       if (!isEmpty($$9) && isEmpty($$7) && !$$9.is(Blocks.BEDROCK)) {
/* 76 */         if ($$6 == $$4) {
/* 77 */           return $$5.getY() + 1;
/*    */         }
/* 79 */         $$6++;
/*    */       } 
/* 81 */       $$7 = $$9;
/*    */     } 
/* 83 */     return Integer.MAX_VALUE;
/*    */   }
/*    */   
/*    */   private static boolean isEmpty(BlockState $$0) {
/* 87 */     return ($$0.isAir() || $$0.is(Blocks.WATER) || $$0.is(Blocks.LAVA));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\placement\CountOnEveryLayerPlacement.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */