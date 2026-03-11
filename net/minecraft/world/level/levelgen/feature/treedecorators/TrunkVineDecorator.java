/*    */ package net.minecraft.world.level.levelgen.feature.treedecorators;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.block.VineBlock;
/*    */ 
/*    */ public class TrunkVineDecorator
/*    */   extends TreeDecorator {
/*    */   protected TreeDecoratorType<?> type() {
/* 11 */     return TreeDecoratorType.TRUNK_VINE;
/*    */   }
/*    */   
/* 14 */   public static final Codec<TrunkVineDecorator> CODEC = Codec.unit(() -> INSTANCE);
/*    */   
/* 16 */   public static final TrunkVineDecorator INSTANCE = new TrunkVineDecorator();
/*    */ 
/*    */   
/*    */   public void place(TreeDecorator.Context $$0) {
/* 20 */     RandomSource $$1 = $$0.random();
/* 21 */     $$0.logs().forEach($$2 -> {
/*    */           if ($$0.nextInt(3) > 0) {
/*    */             BlockPos $$3 = $$2.west();
/*    */             if ($$1.isAir($$3))
/*    */               $$1.placeVine($$3, VineBlock.EAST); 
/*    */           } 
/*    */           if ($$0.nextInt(3) > 0) {
/*    */             BlockPos $$4 = $$2.east();
/*    */             if ($$1.isAir($$4))
/*    */               $$1.placeVine($$4, VineBlock.WEST); 
/*    */           } 
/*    */           if ($$0.nextInt(3) > 0) {
/*    */             BlockPos $$5 = $$2.north();
/*    */             if ($$1.isAir($$5))
/*    */               $$1.placeVine($$5, VineBlock.SOUTH); 
/*    */           } 
/*    */           if ($$0.nextInt(3) > 0) {
/*    */             BlockPos $$6 = $$2.south();
/*    */             if ($$1.isAir($$6))
/*    */               $$1.placeVine($$6, VineBlock.NORTH); 
/*    */           } 
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\treedecorators\TrunkVineDecorator.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */