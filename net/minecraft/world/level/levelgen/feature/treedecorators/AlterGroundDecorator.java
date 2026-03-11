/*    */ package net.minecraft.world.level.levelgen.feature.treedecorators;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
/*    */ 
/*    */ public class AlterGroundDecorator extends TreeDecorator {
/*    */   public static final Codec<AlterGroundDecorator> CODEC;
/*    */   
/*    */   static {
/* 12 */     CODEC = BlockStateProvider.CODEC.fieldOf("provider").xmap(AlterGroundDecorator::new, $$0 -> $$0.provider).codec();
/*    */   }
/*    */   private final BlockStateProvider provider;
/*    */   
/*    */   public AlterGroundDecorator(BlockStateProvider $$0) {
/* 17 */     this.provider = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected TreeDecoratorType<?> type() {
/* 22 */     return TreeDecoratorType.ALTER_GROUND;
/*    */   }
/*    */ 
/*    */   
/*    */   public void place(TreeDecorator.Context $$0) {
/* 27 */     List<BlockPos> $$1 = Lists.newArrayList();
/*    */     
/* 29 */     ObjectArrayList<BlockPos> objectArrayList1 = $$0.roots();
/* 30 */     ObjectArrayList<BlockPos> objectArrayList2 = $$0.logs();
/*    */     
/* 32 */     if (objectArrayList1.isEmpty()) {
/* 33 */       $$1.addAll((Collection<? extends BlockPos>)objectArrayList2);
/* 34 */     } else if (!objectArrayList2.isEmpty() && ((BlockPos)objectArrayList1.get(0)).getY() == ((BlockPos)objectArrayList2.get(0)).getY()) {
/* 35 */       $$1.addAll((Collection<? extends BlockPos>)objectArrayList2);
/* 36 */       $$1.addAll((Collection<? extends BlockPos>)objectArrayList1);
/*    */     } else {
/* 38 */       $$1.addAll((Collection<? extends BlockPos>)objectArrayList1);
/*    */     } 
/*    */     
/* 41 */     if ($$1.isEmpty()) {
/*    */       return;
/*    */     }
/*    */     
/* 45 */     int $$4 = ((BlockPos)$$1.get(0)).getY();
/* 46 */     $$1.stream().filter($$1 -> ($$1.getY() == $$0)).forEach($$1 -> {
/*    */           placeCircle($$0, $$1.west().north());
/*    */           placeCircle($$0, $$1.east(2).north());
/*    */           placeCircle($$0, $$1.west().south(2));
/*    */           placeCircle($$0, $$1.east(2).south(2));
/*    */           for (int $$2 = 0; $$2 < 5; $$2++) {
/*    */             int $$3 = $$0.random().nextInt(64);
/*    */             int $$4 = $$3 % 8;
/*    */             int $$5 = $$3 / 8;
/*    */             if ($$4 == 0 || $$4 == 7 || $$5 == 0 || $$5 == 7) {
/*    */               placeCircle($$0, $$1.offset(-3 + $$4, 0, -3 + $$5));
/*    */             }
/*    */           } 
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   private void placeCircle(TreeDecorator.Context $$0, BlockPos $$1) {
/* 64 */     for (int $$2 = -2; $$2 <= 2; $$2++) {
/* 65 */       for (int $$3 = -2; $$3 <= 2; $$3++) {
/* 66 */         if (Math.abs($$2) != 2 || Math.abs($$3) != 2) {
/* 67 */           placeBlockAt($$0, $$1.offset($$2, 0, $$3));
/*    */         }
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   private void placeBlockAt(TreeDecorator.Context $$0, BlockPos $$1) {
/* 74 */     for (int $$2 = 2; $$2 >= -3; $$2--) {
/* 75 */       BlockPos $$3 = $$1.above($$2);
/* 76 */       if (Feature.isGrassOrDirt($$0.level(), $$3)) {
/* 77 */         $$0.setBlock($$3, this.provider.getState($$0.random(), $$1)); break;
/*    */       } 
/* 79 */       if (!$$0.isAir($$3) && $$2 < 0)
/*    */         break; 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\treedecorators\AlterGroundDecorator.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */