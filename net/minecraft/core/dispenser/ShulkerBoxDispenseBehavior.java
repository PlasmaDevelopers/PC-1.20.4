/*    */ package net.minecraft.core.dispenser;
/*    */ import com.mojang.logging.LogUtils;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.world.item.BlockItem;
/*    */ import net.minecraft.world.item.Item;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.DispenserBlock;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class ShulkerBoxDispenseBehavior extends OptionalDispenseItemBehavior {
/* 14 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */ 
/*    */   
/*    */   protected ItemStack execute(BlockSource $$0, ItemStack $$1) {
/* 18 */     setSuccess(false);
/*    */     
/* 20 */     Item $$2 = $$1.getItem();
/* 21 */     if ($$2 instanceof BlockItem) {
/* 22 */       Direction $$3 = (Direction)$$0.state().getValue((Property)DispenserBlock.FACING);
/* 23 */       BlockPos $$4 = $$0.pos().relative($$3);
/*    */       
/* 25 */       Direction $$5 = $$0.level().isEmptyBlock($$4.below()) ? $$3 : Direction.UP;
/*    */       try {
/* 27 */         setSuccess(((BlockItem)$$2).place((BlockPlaceContext)new DirectionalPlaceContext((Level)$$0.level(), $$4, $$3, $$1, $$5)).consumesAction());
/* 28 */       } catch (Exception $$6) {
/* 29 */         LOGGER.error("Error trying to place shulker box at {}", $$4, $$6);
/*    */       } 
/*    */     } 
/* 32 */     return $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\dispenser\ShulkerBoxDispenseBehavior.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */