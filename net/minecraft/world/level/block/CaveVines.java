/*    */ package net.minecraft.world.level.block;
/*    */ 
/*    */ import java.util.function.ToIntFunction;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.sounds.SoundSource;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.InteractionResult;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.Items;
/*    */ import net.minecraft.world.level.ItemLike;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*    */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ import net.minecraft.world.level.gameevent.GameEvent;
/*    */ import net.minecraft.world.phys.shapes.VoxelShape;
/*    */ 
/*    */ 
/*    */ public interface CaveVines
/*    */ {
/* 25 */   public static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);
/*    */   
/* 27 */   public static final BooleanProperty BERRIES = BlockStateProperties.BERRIES;
/*    */   
/*    */   static InteractionResult use(@Nullable Entity $$0, BlockState $$1, Level $$2, BlockPos $$3) {
/* 30 */     if (((Boolean)$$1.getValue((Property)BERRIES)).booleanValue()) {
/* 31 */       Block.popResource($$2, $$3, new ItemStack((ItemLike)Items.GLOW_BERRIES, 1));
/* 32 */       float $$4 = Mth.randomBetween($$2.random, 0.8F, 1.2F);
/* 33 */       $$2.playSound(null, $$3, SoundEvents.CAVE_VINES_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, $$4);
/* 34 */       BlockState $$5 = (BlockState)$$1.setValue((Property)BERRIES, Boolean.valueOf(false));
/* 35 */       $$2.setBlock($$3, $$5, 2);
/* 36 */       $$2.gameEvent(GameEvent.BLOCK_CHANGE, $$3, GameEvent.Context.of($$0, $$5));
/* 37 */       return InteractionResult.sidedSuccess($$2.isClientSide);
/*    */     } 
/* 39 */     return InteractionResult.PASS;
/*    */   }
/*    */   
/*    */   static boolean hasGlowBerries(BlockState $$0) {
/* 43 */     return ($$0.hasProperty((Property)BERRIES) && ((Boolean)$$0.getValue((Property)BERRIES)).booleanValue());
/*    */   }
/*    */   
/*    */   static ToIntFunction<BlockState> emission(int $$0) {
/* 47 */     return $$1 -> ((Boolean)$$1.getValue((Property)BlockStateProperties.BERRIES)).booleanValue() ? $$0 : 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\CaveVines.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */