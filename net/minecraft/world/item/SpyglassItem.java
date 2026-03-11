/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.stats.Stats;
/*    */ import net.minecraft.world.InteractionHand;
/*    */ import net.minecraft.world.InteractionResultHolder;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.level.Level;
/*    */ 
/*    */ public class SpyglassItem
/*    */   extends Item {
/*    */   public static final int USE_DURATION = 1200;
/*    */   public static final float ZOOM_FOV_MODIFIER = 0.1F;
/*    */   
/*    */   public SpyglassItem(Item.Properties $$0) {
/* 17 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getUseDuration(ItemStack $$0) {
/* 22 */     return 1200;
/*    */   }
/*    */ 
/*    */   
/*    */   public UseAnim getUseAnimation(ItemStack $$0) {
/* 27 */     return UseAnim.SPYGLASS;
/*    */   }
/*    */ 
/*    */   
/*    */   public InteractionResultHolder<ItemStack> use(Level $$0, Player $$1, InteractionHand $$2) {
/* 32 */     $$1.playSound(SoundEvents.SPYGLASS_USE, 1.0F, 1.0F);
/* 33 */     $$1.awardStat(Stats.ITEM_USED.get(this));
/* 34 */     return ItemUtils.startUsingInstantly($$0, $$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack finishUsingItem(ItemStack $$0, Level $$1, LivingEntity $$2) {
/* 39 */     stopUsing($$2);
/* 40 */     return $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void releaseUsing(ItemStack $$0, Level $$1, LivingEntity $$2, int $$3) {
/* 45 */     stopUsing($$2);
/*    */   }
/*    */   
/*    */   private void stopUsing(LivingEntity $$0) {
/* 49 */     $$0.playSound(SoundEvents.SPYGLASS_STOP_USING, 1.0F, 1.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\SpyglassItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */