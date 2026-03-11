/*    */ package net.minecraft.world.inventory;
/*    */ 
/*    */ import net.minecraft.world.Container;
/*    */ import net.minecraft.world.entity.player.Inventory;
/*    */ import net.minecraft.world.item.crafting.RecipeType;
/*    */ 
/*    */ public class FurnaceMenu extends AbstractFurnaceMenu {
/*    */   public FurnaceMenu(int $$0, Inventory $$1) {
/*  9 */     super(MenuType.FURNACE, RecipeType.SMELTING, RecipeBookType.FURNACE, $$0, $$1);
/*    */   }
/*    */   
/*    */   public FurnaceMenu(int $$0, Inventory $$1, Container $$2, ContainerData $$3) {
/* 13 */     super(MenuType.FURNACE, RecipeType.SMELTING, RecipeBookType.FURNACE, $$0, $$1, $$2, $$3);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\inventory\FurnaceMenu.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */