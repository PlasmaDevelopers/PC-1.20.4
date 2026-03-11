/*    */ package net.minecraft.world.level.block.entity;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.world.entity.player.Inventory;
/*    */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*    */ import net.minecraft.world.inventory.FurnaceMenu;
/*    */ import net.minecraft.world.item.crafting.RecipeType;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class FurnaceBlockEntity extends AbstractFurnaceBlockEntity {
/*    */   public FurnaceBlockEntity(BlockPos $$0, BlockState $$1) {
/* 13 */     super(BlockEntityType.FURNACE, $$0, $$1, RecipeType.SMELTING);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Component getDefaultName() {
/* 18 */     return (Component)Component.translatable("container.furnace");
/*    */   }
/*    */ 
/*    */   
/*    */   protected AbstractContainerMenu createMenu(int $$0, Inventory $$1) {
/* 23 */     return (AbstractContainerMenu)new FurnaceMenu($$0, $$1, this, this.dataAccess);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\FurnaceBlockEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */