/*    */ package net.minecraft.world.level.levelgen.feature.treedecorators;
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.block.VineBlock;
/*    */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*    */ 
/*    */ public class LeaveVineDecorator extends TreeDecorator {
/*    */   public static final Codec<LeaveVineDecorator> CODEC;
/*    */   
/*    */   protected TreeDecoratorType<?> type() {
/* 12 */     return TreeDecoratorType.LEAVE_VINE;
/*    */   } private final float probability;
/*    */   static {
/* 15 */     CODEC = Codec.floatRange(0.0F, 1.0F).fieldOf("probability").xmap(LeaveVineDecorator::new, $$0 -> Float.valueOf($$0.probability)).codec();
/*    */   }
/*    */ 
/*    */   
/*    */   public LeaveVineDecorator(float $$0) {
/* 20 */     this.probability = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void place(TreeDecorator.Context $$0) {
/* 25 */     RandomSource $$1 = $$0.random();
/* 26 */     $$0.leaves().forEach($$2 -> {
/*    */           if ($$0.nextFloat() < this.probability) {
/*    */             BlockPos $$3 = $$2.west();
/*    */             if ($$1.isAir($$3)) {
/*    */               addHangingVine($$3, VineBlock.EAST, $$1);
/*    */             }
/*    */           } 
/*    */           if ($$0.nextFloat() < this.probability) {
/*    */             BlockPos $$4 = $$2.east();
/*    */             if ($$1.isAir($$4)) {
/*    */               addHangingVine($$4, VineBlock.WEST, $$1);
/*    */             }
/*    */           } 
/*    */           if ($$0.nextFloat() < this.probability) {
/*    */             BlockPos $$5 = $$2.north();
/*    */             if ($$1.isAir($$5)) {
/*    */               addHangingVine($$5, VineBlock.SOUTH, $$1);
/*    */             }
/*    */           } 
/*    */           if ($$0.nextFloat() < this.probability) {
/*    */             BlockPos $$6 = $$2.south();
/*    */             if ($$1.isAir($$6)) {
/*    */               addHangingVine($$6, VineBlock.NORTH, $$1);
/*    */             }
/*    */           } 
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static void addHangingVine(BlockPos $$0, BooleanProperty $$1, TreeDecorator.Context $$2) {
/* 58 */     $$2.placeVine($$0, $$1);
/* 59 */     int $$3 = 4;
/*    */     
/* 61 */     $$0 = $$0.below();
/* 62 */     while ($$2.isAir($$0) && $$3 > 0) {
/* 63 */       $$2.placeVine($$0, $$1);
/* 64 */       $$0 = $$0.below();
/* 65 */       $$3--;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\treedecorators\LeaveVineDecorator.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */