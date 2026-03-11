/*    */ package net.minecraft.world.inventory.tooltip;
/*    */ 
/*    */ import net.minecraft.core.NonNullList;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ public class BundleTooltip implements TooltipComponent {
/*    */   private final NonNullList<ItemStack> items;
/*    */   private final int weight;
/*    */   
/*    */   public BundleTooltip(NonNullList<ItemStack> $$0, int $$1) {
/* 11 */     this.items = $$0;
/* 12 */     this.weight = $$1;
/*    */   }
/*    */   
/*    */   public NonNullList<ItemStack> getItems() {
/* 16 */     return this.items;
/*    */   }
/*    */   
/*    */   public int getWeight() {
/* 20 */     return this.weight;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\inventory\tooltip\BundleTooltip.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */