/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.ChatFormatting;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.MutableComponent;
/*    */ import net.minecraft.world.level.Level;
/*    */ 
/*    */ public class DiscFragmentItem
/*    */   extends Item {
/*    */   public DiscFragmentItem(Item.Properties $$0) {
/* 13 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void appendHoverText(ItemStack $$0, @Nullable Level $$1, List<Component> $$2, TooltipFlag $$3) {
/* 18 */     $$2.add(getDisplayName().withStyle(ChatFormatting.GRAY));
/*    */   }
/*    */   
/*    */   public MutableComponent getDisplayName() {
/* 22 */     return Component.translatable(getDescriptionId() + ".desc");
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\DiscFragmentItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */