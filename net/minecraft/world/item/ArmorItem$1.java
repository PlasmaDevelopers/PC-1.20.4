/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ import net.minecraft.core.dispenser.BlockSource;
/*    */ import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class null
/*    */   extends DefaultDispenseItemBehavior
/*    */ {
/*    */   protected ItemStack execute(BlockSource $$0, ItemStack $$1) {
/* 40 */     return ArmorItem.dispenseArmor($$0, $$1) ? $$1 : super.execute($$0, $$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\ArmorItem$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */