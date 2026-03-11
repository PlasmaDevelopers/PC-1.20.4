/*    */ package net.minecraft.world.entity.vehicle;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.world.InteractionHand;
/*    */ import net.minecraft.world.InteractionResult;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.monster.piglin.PiglinAi;
/*    */ import net.minecraft.world.entity.player.Inventory;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*    */ import net.minecraft.world.inventory.ChestMenu;
/*    */ import net.minecraft.world.item.Item;
/*    */ import net.minecraft.world.item.Items;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ import net.minecraft.world.level.gameevent.GameEvent;
/*    */ 
/*    */ public class MinecartChest extends AbstractMinecartContainer {
/*    */   public MinecartChest(EntityType<? extends MinecartChest> $$0, Level $$1) {
/* 22 */     super($$0, $$1);
/*    */   }
/*    */   
/*    */   public MinecartChest(Level $$0, double $$1, double $$2, double $$3) {
/* 26 */     super(EntityType.CHEST_MINECART, $$1, $$2, $$3, $$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Item getDropItem() {
/* 31 */     return Items.CHEST_MINECART;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getContainerSize() {
/* 36 */     return 27;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractMinecart.Type getMinecartType() {
/* 41 */     return AbstractMinecart.Type.CHEST;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState getDefaultDisplayBlockState() {
/* 46 */     return (BlockState)Blocks.CHEST.defaultBlockState().setValue((Property)ChestBlock.FACING, (Comparable)Direction.NORTH);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getDefaultDisplayOffset() {
/* 51 */     return 8;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractContainerMenu createMenu(int $$0, Inventory $$1) {
/* 56 */     return (AbstractContainerMenu)ChestMenu.threeRows($$0, $$1, this);
/*    */   }
/*    */ 
/*    */   
/*    */   public void stopOpen(Player $$0) {
/* 61 */     level().gameEvent(GameEvent.CONTAINER_CLOSE, position(), GameEvent.Context.of((Entity)$$0));
/*    */   }
/*    */ 
/*    */   
/*    */   public InteractionResult interact(Player $$0, InteractionHand $$1) {
/* 66 */     InteractionResult $$2 = interactWithContainerVehicle($$0);
/* 67 */     if ($$2.consumesAction()) {
/* 68 */       gameEvent(GameEvent.CONTAINER_OPEN, (Entity)$$0);
/* 69 */       PiglinAi.angerNearbyPiglins($$0, true);
/*    */     } 
/* 71 */     return $$2;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\vehicle\MinecartChest.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */