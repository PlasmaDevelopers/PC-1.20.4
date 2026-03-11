/*    */ package net.minecraft.world.level.block;
/*    */ 
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.item.DyeColor;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.block.entity.BannerBlockEntity;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public abstract class AbstractBannerBlock extends BaseEntityBlock {
/*    */   private final DyeColor color;
/*    */   
/*    */   protected AbstractBannerBlock(DyeColor $$0, BlockBehaviour.Properties $$1) {
/* 21 */     super($$1);
/* 22 */     this.color = $$0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isPossibleToRespawnInThis(BlockState $$0) {
/* 30 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockEntity newBlockEntity(BlockPos $$0, BlockState $$1) {
/* 35 */     return (BlockEntity)new BannerBlockEntity($$0, $$1, this.color);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setPlacedBy(Level $$0, BlockPos $$1, BlockState $$2, @Nullable LivingEntity $$3, ItemStack $$4) {
/* 40 */     if ($$0.isClientSide) {
/*    */       
/* 42 */       $$0.getBlockEntity($$1, BlockEntityType.BANNER).ifPresent($$1 -> $$1.fromItem($$0));
/*    */     }
/* 44 */     else if ($$4.hasCustomHoverName()) {
/* 45 */       $$0.getBlockEntity($$1, BlockEntityType.BANNER).ifPresent($$1 -> $$1.setCustomName($$0.getHoverName()));
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack getCloneItemStack(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/* 52 */     BlockEntity $$3 = $$0.getBlockEntity($$1);
/* 53 */     if ($$3 instanceof BannerBlockEntity) {
/* 54 */       return ((BannerBlockEntity)$$3).getItem();
/*    */     }
/*    */     
/* 57 */     return super.getCloneItemStack($$0, $$1, $$2);
/*    */   }
/*    */   
/*    */   public DyeColor getColor() {
/* 61 */     return this.color;
/*    */   }
/*    */   
/*    */   protected abstract MapCodec<? extends AbstractBannerBlock> codec();
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\AbstractBannerBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */