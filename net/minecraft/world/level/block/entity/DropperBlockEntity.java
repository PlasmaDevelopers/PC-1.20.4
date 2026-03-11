/*    */ package net.minecraft.world.level.block.entity;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class DropperBlockEntity extends DispenserBlockEntity {
/*    */   public DropperBlockEntity(BlockPos $$0, BlockState $$1) {
/*  9 */     super(BlockEntityType.DROPPER, $$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Component getDefaultName() {
/* 14 */     return (Component)Component.translatable("container.dropper");
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\DropperBlockEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */