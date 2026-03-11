/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.sounds.SoundSource;
/*    */ import net.minecraft.world.InteractionHand;
/*    */ import net.minecraft.world.InteractionResultHolder;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.item.alchemy.PotionUtils;
/*    */ import net.minecraft.world.level.Level;
/*    */ 
/*    */ public class LingeringPotionItem
/*    */   extends ThrowablePotionItem
/*    */ {
/*    */   public LingeringPotionItem(Item.Properties $$0) {
/* 18 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void appendHoverText(ItemStack $$0, @Nullable Level $$1, List<Component> $$2, TooltipFlag $$3) {
/* 23 */     PotionUtils.addPotionTooltip($$0, $$2, 0.25F, ($$1 == null) ? 20.0F : $$1.tickRateManager().tickrate());
/*    */   }
/*    */ 
/*    */   
/*    */   public InteractionResultHolder<ItemStack> use(Level $$0, Player $$1, InteractionHand $$2) {
/* 28 */     $$0.playSound(null, $$1.getX(), $$1.getY(), $$1.getZ(), SoundEvents.LINGERING_POTION_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / ($$0.getRandom().nextFloat() * 0.4F + 0.8F));
/* 29 */     return super.use($$0, $$1, $$2);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\LingeringPotionItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */