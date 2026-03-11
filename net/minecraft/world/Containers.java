/*    */ package net.minecraft.world;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.NonNullList;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.item.ItemEntity;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class Containers
/*    */ {
/*    */   public static void dropContents(Level $$0, BlockPos $$1, Container $$2) {
/* 16 */     dropContents($$0, $$1.getX(), $$1.getY(), $$1.getZ(), $$2);
/*    */   }
/*    */   
/*    */   public static void dropContents(Level $$0, Entity $$1, Container $$2) {
/* 20 */     dropContents($$0, $$1.getX(), $$1.getY(), $$1.getZ(), $$2);
/*    */   }
/*    */   
/*    */   private static void dropContents(Level $$0, double $$1, double $$2, double $$3, Container $$4) {
/* 24 */     for (int $$5 = 0; $$5 < $$4.getContainerSize(); $$5++) {
/* 25 */       dropItemStack($$0, $$1, $$2, $$3, $$4.getItem($$5));
/*    */     }
/*    */   }
/*    */   
/*    */   public static void dropContents(Level $$0, BlockPos $$1, NonNullList<ItemStack> $$2) {
/* 30 */     $$2.forEach($$2 -> dropItemStack($$0, $$1.getX(), $$1.getY(), $$1.getZ(), $$2));
/*    */   }
/*    */   
/*    */   public static void dropItemStack(Level $$0, double $$1, double $$2, double $$3, ItemStack $$4) {
/* 34 */     double $$5 = EntityType.ITEM.getWidth();
/* 35 */     double $$6 = 1.0D - $$5;
/* 36 */     double $$7 = $$5 / 2.0D;
/* 37 */     double $$8 = Math.floor($$1) + $$0.random.nextDouble() * $$6 + $$7;
/* 38 */     double $$9 = Math.floor($$2) + $$0.random.nextDouble() * $$6;
/* 39 */     double $$10 = Math.floor($$3) + $$0.random.nextDouble() * $$6 + $$7;
/*    */     
/* 41 */     while (!$$4.isEmpty()) {
/* 42 */       ItemEntity $$11 = new ItemEntity($$0, $$8, $$9, $$10, $$4.split($$0.random.nextInt(21) + 10));
/*    */       
/* 44 */       float $$12 = 0.05F;
/* 45 */       $$11.setDeltaMovement($$0.random
/* 46 */           .triangle(0.0D, 0.11485000171139836D), $$0.random
/* 47 */           .triangle(0.2D, 0.11485000171139836D), $$0.random
/* 48 */           .triangle(0.0D, 0.11485000171139836D));
/*    */ 
/*    */       
/* 51 */       $$0.addFreshEntity((Entity)$$11);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void dropContentsOnDestroy(BlockState $$0, BlockState $$1, Level $$2, BlockPos $$3) {
/* 56 */     if ($$0.is($$1.getBlock())) {
/*    */       return;
/*    */     }
/* 59 */     BlockEntity $$4 = $$2.getBlockEntity($$3);
/* 60 */     if ($$4 instanceof Container) { Container $$5 = (Container)$$4;
/* 61 */       dropContents($$2, $$3, $$5);
/* 62 */       $$2.updateNeighbourForOutputSignal($$3, $$0.getBlock()); }
/*    */   
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\Containers.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */