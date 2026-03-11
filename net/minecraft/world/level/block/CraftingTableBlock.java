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
/*    */ import net.minecraft.world.inventory.CraftingMenu;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.phys.BlockHitResult;
/*    */ 
/*    */ public class CraftingTableBlock extends Block {
/* 19 */   public static final MapCodec<CraftingTableBlock> CODEC = simpleCodec(CraftingTableBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<? extends CraftingTableBlock> codec() {
/* 23 */     return CODEC;
/*    */   }
/*    */   
/* 26 */   private static final Component CONTAINER_TITLE = (Component)Component.translatable("container.crafting");
/*    */   
/*    */   protected CraftingTableBlock(BlockBehaviour.Properties $$0) {
/* 29 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/* 34 */     if ($$1.isClientSide) {
/* 35 */       return InteractionResult.SUCCESS;
/*    */     }
/*    */     
/* 38 */     $$3.openMenu($$0.getMenuProvider($$1, $$2));
/* 39 */     $$3.awardStat(Stats.INTERACT_WITH_CRAFTING_TABLE);
/* 40 */     return InteractionResult.CONSUME;
/*    */   }
/*    */ 
/*    */   
/*    */   public MenuProvider getMenuProvider(BlockState $$0, Level $$1, BlockPos $$2) {
/* 45 */     return (MenuProvider)new SimpleMenuProvider(($$2, $$3, $$4) -> new CraftingMenu($$2, $$3, ContainerLevelAccess.create($$0, $$1)), CONTAINER_TITLE);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\CraftingTableBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */