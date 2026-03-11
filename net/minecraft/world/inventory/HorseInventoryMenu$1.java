/*    */ package net.minecraft.world.inventory;
/*    */ 
/*    */ import net.minecraft.world.Container;
/*    */ import net.minecraft.world.entity.animal.horse.AbstractHorse;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.Items;
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
/*    */   extends Slot
/*    */ {
/*    */   null(Container $$1, int $$2, int $$3, int $$4) {
/* 25 */     super($$1, $$2, $$3, $$4);
/*    */   }
/*    */   public boolean mayPlace(ItemStack $$0) {
/* 28 */     return ($$0.is(Items.SADDLE) && !hasItem() && horse.isSaddleable());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isActive() {
/* 33 */     return horse.isSaddleable();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\inventory\HorseInventoryMenu$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */