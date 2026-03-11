/*    */ package net.minecraft.world.level.block.entity;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.world.entity.player.Inventory;
/*    */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*    */ import net.minecraft.world.inventory.SmokerMenu;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.crafting.RecipeType;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class SmokerBlockEntity extends AbstractFurnaceBlockEntity {
/*    */   public SmokerBlockEntity(BlockPos $$0, BlockState $$1) {
/* 14 */     super(BlockEntityType.SMOKER, $$0, $$1, RecipeType.SMOKING);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Component getDefaultName() {
/* 19 */     return (Component)Component.translatable("container.smoker");
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getBurnDuration(ItemStack $$0) {
/* 24 */     return super.getBurnDuration($$0) / 2;
/*    */   }
/*    */ 
/*    */   
/*    */   protected AbstractContainerMenu createMenu(int $$0, Inventory $$1) {
/* 29 */     return (AbstractContainerMenu)new SmokerMenu($$0, $$1, this, this.dataAccess);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\SmokerBlockEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */