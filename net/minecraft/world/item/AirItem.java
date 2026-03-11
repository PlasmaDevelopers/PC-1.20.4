/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ 
/*    */ public class AirItem extends Item {
/*    */   private final Block block;
/*    */   
/*    */   public AirItem(Block $$0, Item.Properties $$1) {
/* 14 */     super($$1);
/* 15 */     this.block = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescriptionId() {
/* 20 */     return this.block.getDescriptionId();
/*    */   }
/*    */ 
/*    */   
/*    */   public void appendHoverText(ItemStack $$0, @Nullable Level $$1, List<Component> $$2, TooltipFlag $$3) {
/* 25 */     super.appendHoverText($$0, $$1, $$2, $$3);
/* 26 */     this.block.appendHoverText($$0, (BlockGetter)$$1, $$2, $$3);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\AirItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */