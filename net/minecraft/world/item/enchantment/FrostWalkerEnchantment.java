/*    */ package net.minecraft.world.item.enchantment;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Position;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.EquipmentSlot;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.FrostedIceBlock;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.phys.shapes.CollisionContext;
/*    */ 
/*    */ public class FrostWalkerEnchantment extends Enchantment {
/*    */   public FrostWalkerEnchantment(Enchantment.Rarity $$0, EquipmentSlot... $$1) {
/* 16 */     super($$0, EnchantmentCategory.ARMOR_FEET, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMinCost(int $$0) {
/* 21 */     return $$0 * 10;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxCost(int $$0) {
/* 26 */     return getMinCost($$0) + 15;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isTreasureOnly() {
/* 31 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxLevel() {
/* 36 */     return 2;
/*    */   }
/*    */   
/*    */   public static void onEntityMoved(LivingEntity $$0, Level $$1, BlockPos $$2, int $$3) {
/* 40 */     if (!$$0.onGround()) {
/*    */       return;
/*    */     }
/*    */     
/* 44 */     BlockState $$4 = Blocks.FROSTED_ICE.defaultBlockState();
/*    */     
/* 46 */     int $$5 = Math.min(16, 2 + $$3);
/* 47 */     BlockPos.MutableBlockPos $$6 = new BlockPos.MutableBlockPos();
/* 48 */     for (BlockPos $$7 : BlockPos.betweenClosed($$2.offset(-$$5, -1, -$$5), $$2.offset($$5, -1, $$5))) {
/* 49 */       if ($$7.closerToCenterThan((Position)$$0.position(), $$5)) {
/* 50 */         $$6.set($$7.getX(), $$7.getY() + 1, $$7.getZ());
/* 51 */         BlockState $$8 = $$1.getBlockState((BlockPos)$$6);
/* 52 */         if (!$$8.isAir()) {
/*    */           continue;
/*    */         }
/* 55 */         BlockState $$9 = $$1.getBlockState($$7);
/* 56 */         if ($$9 == FrostedIceBlock.meltsInto() && 
/* 57 */           $$4.canSurvive((LevelReader)$$1, $$7) && $$1.isUnobstructed($$4, $$7, CollisionContext.empty())) {
/* 58 */           $$1.setBlockAndUpdate($$7, $$4);
/* 59 */           $$1.scheduleTick($$7, Blocks.FROSTED_ICE, Mth.nextInt($$0.getRandom(), 60, 120));
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean checkCompatibility(Enchantment $$0) {
/* 68 */     return (super.checkCompatibility($$0) && $$0 != Enchantments.DEPTH_STRIDER);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\enchantment\FrostWalkerEnchantment.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */