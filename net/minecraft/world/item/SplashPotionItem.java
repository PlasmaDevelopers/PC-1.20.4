/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.sounds.SoundSource;
/*    */ import net.minecraft.world.InteractionHand;
/*    */ import net.minecraft.world.InteractionResultHolder;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.level.Level;
/*    */ 
/*    */ public class SplashPotionItem extends ThrowablePotionItem {
/*    */   public SplashPotionItem(Item.Properties $$0) {
/* 12 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public InteractionResultHolder<ItemStack> use(Level $$0, Player $$1, InteractionHand $$2) {
/* 17 */     $$0.playSound(null, $$1.getX(), $$1.getY(), $$1.getZ(), SoundEvents.SPLASH_POTION_THROW, SoundSource.PLAYERS, 0.5F, 0.4F / ($$0.getRandom().nextFloat() * 0.4F + 0.8F));
/* 18 */     return super.use($$0, $$1, $$2);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\SplashPotionItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */