/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.world.item.alchemy.PotionUtils;
/*    */ import net.minecraft.world.item.alchemy.Potions;
/*    */ import net.minecraft.world.level.Level;
/*    */ 
/*    */ public class TippedArrowItem
/*    */   extends ArrowItem
/*    */ {
/*    */   public TippedArrowItem(Item.Properties $$0) {
/* 14 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getDefaultInstance() {
/* 19 */     return PotionUtils.setPotion(super.getDefaultInstance(), Potions.POISON);
/*    */   }
/*    */ 
/*    */   
/*    */   public void appendHoverText(ItemStack $$0, @Nullable Level $$1, List<Component> $$2, TooltipFlag $$3) {
/* 24 */     PotionUtils.addPotionTooltip($$0, $$2, 0.125F, ($$1 == null) ? 20.0F : $$1.tickRateManager().tickrate());
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescriptionId(ItemStack $$0) {
/* 29 */     return PotionUtils.getPotion($$0).getName(getDescriptionId() + ".effect.");
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\TippedArrowItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */