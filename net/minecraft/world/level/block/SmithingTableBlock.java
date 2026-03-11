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
/*    */ import net.minecraft.world.inventory.SmithingMenu;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.phys.BlockHitResult;
/*    */ 
/*    */ public class SmithingTableBlock extends CraftingTableBlock {
/* 19 */   public static final MapCodec<SmithingTableBlock> CODEC = simpleCodec(SmithingTableBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<SmithingTableBlock> codec() {
/* 23 */     return CODEC;
/*    */   }
/*    */   
/*    */   protected SmithingTableBlock(BlockBehaviour.Properties $$0) {
/* 27 */     super($$0);
/*    */   }
/*    */   
/* 30 */   private static final Component CONTAINER_TITLE = (Component)Component.translatable("container.upgrade");
/*    */ 
/*    */   
/*    */   public MenuProvider getMenuProvider(BlockState $$0, Level $$1, BlockPos $$2) {
/* 34 */     return (MenuProvider)new SimpleMenuProvider(($$2, $$3, $$4) -> new SmithingMenu($$2, $$3, ContainerLevelAccess.create($$0, $$1)), CONTAINER_TITLE);
/*    */   }
/*    */ 
/*    */   
/*    */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/* 39 */     if ($$1.isClientSide) {
/* 40 */       return InteractionResult.SUCCESS;
/*    */     }
/*    */     
/* 43 */     $$3.openMenu($$0.getMenuProvider($$1, $$2));
/* 44 */     $$3.awardStat(Stats.INTERACT_WITH_SMITHING_TABLE);
/* 45 */     return InteractionResult.CONSUME;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\SmithingTableBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */