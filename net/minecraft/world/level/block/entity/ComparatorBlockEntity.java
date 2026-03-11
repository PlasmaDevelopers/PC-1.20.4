/*    */ package net.minecraft.world.level.block.entity;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class ComparatorBlockEntity extends BlockEntity {
/*    */   private int output;
/*    */   
/*    */   public ComparatorBlockEntity(BlockPos $$0, BlockState $$1) {
/* 11 */     super(BlockEntityType.COMPARATOR, $$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void saveAdditional(CompoundTag $$0) {
/* 16 */     super.saveAdditional($$0);
/* 17 */     $$0.putInt("OutputSignal", this.output);
/*    */   }
/*    */ 
/*    */   
/*    */   public void load(CompoundTag $$0) {
/* 22 */     super.load($$0);
/* 23 */     this.output = $$0.getInt("OutputSignal");
/*    */   }
/*    */   
/*    */   public int getOutputSignal() {
/* 27 */     return this.output;
/*    */   }
/*    */   
/*    */   public void setOutputSignal(int $$0) {
/* 31 */     this.output = $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\ComparatorBlockEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */