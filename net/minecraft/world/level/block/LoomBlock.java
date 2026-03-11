/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.world.InteractionHand;
/*    */ import net.minecraft.world.InteractionResult;
/*    */ import net.minecraft.world.MenuProvider;
/*    */ import net.minecraft.world.entity.player.Inventory;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*    */ import net.minecraft.world.inventory.ContainerLevelAccess;
/*    */ import net.minecraft.world.inventory.LoomMenu;
/*    */ import net.minecraft.world.item.context.BlockPlaceContext;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.StateDefinition;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ 
/*    */ public class LoomBlock extends HorizontalDirectionalBlock {
/* 21 */   public static final MapCodec<LoomBlock> CODEC = simpleCodec(LoomBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<LoomBlock> codec() {
/* 25 */     return CODEC;
/*    */   }
/*    */   
/* 28 */   private static final Component CONTAINER_TITLE = (Component)Component.translatable("container.loom");
/*    */   
/*    */   protected LoomBlock(BlockBehaviour.Properties $$0) {
/* 31 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/* 36 */     if ($$1.isClientSide) {
/* 37 */       return InteractionResult.SUCCESS;
/*    */     }
/*    */     
/* 40 */     $$3.openMenu($$0.getMenuProvider($$1, $$2));
/* 41 */     $$3.awardStat(Stats.INTERACT_WITH_LOOM);
/* 42 */     return InteractionResult.CONSUME;
/*    */   }
/*    */ 
/*    */   
/*    */   public MenuProvider getMenuProvider(BlockState $$0, Level $$1, BlockPos $$2) {
/* 47 */     return (MenuProvider)new SimpleMenuProvider(($$2, $$3, $$4) -> new LoomMenu($$2, $$3, ContainerLevelAccess.create($$0, $$1)), CONTAINER_TITLE);
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 52 */     return (BlockState)defaultBlockState().setValue((Property)FACING, (Comparable)$$0.getHorizontalDirection().getOpposite());
/*    */   }
/*    */ 
/*    */   
/*    */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 57 */     $$0.add(new Property[] { (Property)FACING });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\LoomBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */