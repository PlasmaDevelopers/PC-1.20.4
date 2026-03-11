/*    */ package net.minecraft.world.level.block;
/*    */ 
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.MenuProvider;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.block.entity.BlockEntityTicker;
/*    */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public abstract class BaseEntityBlock extends Block implements EntityBlock {
/*    */   protected BaseEntityBlock(BlockBehaviour.Properties $$0) {
/* 16 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected abstract MapCodec<? extends BaseEntityBlock> codec();
/*    */ 
/*    */   
/*    */   public RenderShape getRenderShape(BlockState $$0) {
/* 24 */     return RenderShape.INVISIBLE;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean triggerEvent(BlockState $$0, Level $$1, BlockPos $$2, int $$3, int $$4) {
/* 29 */     super.triggerEvent($$0, $$1, $$2, $$3, $$4);
/*    */     
/* 31 */     BlockEntity $$5 = $$1.getBlockEntity($$2);
/* 32 */     if ($$5 == null) {
/* 33 */       return false;
/*    */     }
/* 35 */     return $$5.triggerEvent($$3, $$4);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public MenuProvider getMenuProvider(BlockState $$0, Level $$1, BlockPos $$2) {
/* 41 */     BlockEntity $$3 = $$1.getBlockEntity($$2);
/* 42 */     return ($$3 instanceof MenuProvider) ? (MenuProvider)$$3 : null;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> $$0, BlockEntityType<E> $$1, BlockEntityTicker<? super E> $$2) {
/* 48 */     return ($$1 == $$0) ? (BlockEntityTicker)$$2 : null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\BaseEntityBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */