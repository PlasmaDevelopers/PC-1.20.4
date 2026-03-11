/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ import net.minecraft.world.InteractionHand;
/*    */ import net.minecraft.world.InteractionResult;
/*    */ import net.minecraft.world.InteractionResultHolder;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.item.context.UseOnContext;
/*    */ import net.minecraft.world.level.ClipContext;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.phys.BlockHitResult;
/*    */ 
/*    */ public class PlaceOnWaterBlockItem extends BlockItem {
/*    */   public PlaceOnWaterBlockItem(Block $$0, Item.Properties $$1) {
/* 15 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public InteractionResult useOn(UseOnContext $$0) {
/* 20 */     return InteractionResult.PASS;
/*    */   }
/*    */ 
/*    */   
/*    */   public InteractionResultHolder<ItemStack> use(Level $$0, Player $$1, InteractionHand $$2) {
/* 25 */     BlockHitResult $$3 = getPlayerPOVHitResult($$0, $$1, ClipContext.Fluid.SOURCE_ONLY);
/* 26 */     BlockHitResult $$4 = $$3.withPosition($$3.getBlockPos().above());
/* 27 */     InteractionResult $$5 = super.useOn(new UseOnContext($$1, $$2, $$4));
/* 28 */     return new InteractionResultHolder($$5, $$1.getItemInHand($$2));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\PlaceOnWaterBlockItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */