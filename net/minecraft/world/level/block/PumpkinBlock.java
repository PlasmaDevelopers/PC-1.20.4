/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.world.InteractionHand;
/*    */ import net.minecraft.world.InteractionResult;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.item.ItemEntity;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.Items;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.phys.BlockHitResult;
/*    */ 
/*    */ public class PumpkinBlock extends Block {
/* 21 */   public static final MapCodec<PumpkinBlock> CODEC = simpleCodec(PumpkinBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<PumpkinBlock> codec() {
/* 25 */     return CODEC;
/*    */   }
/*    */   
/*    */   protected PumpkinBlock(BlockBehaviour.Properties $$0) {
/* 29 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/* 34 */     ItemStack $$6 = $$3.getItemInHand($$4);
/* 35 */     if ($$6.is(Items.SHEARS)) {
/* 36 */       if (!$$1.isClientSide) {
/* 37 */         Direction $$7 = $$5.getDirection();
/* 38 */         Direction $$8 = ($$7.getAxis() == Direction.Axis.Y) ? $$3.getDirection().getOpposite() : $$7;
/*    */         
/* 40 */         $$1.playSound(null, $$2, SoundEvents.PUMPKIN_CARVE, SoundSource.BLOCKS, 1.0F, 1.0F);
/* 41 */         $$1.setBlock($$2, (BlockState)Blocks.CARVED_PUMPKIN.defaultBlockState().setValue((Property)CarvedPumpkinBlock.FACING, (Comparable)$$8), 11);
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 46 */         ItemEntity $$9 = new ItemEntity($$1, $$2.getX() + 0.5D + $$8.getStepX() * 0.65D, $$2.getY() + 0.1D, $$2.getZ() + 0.5D + $$8.getStepZ() * 0.65D, new ItemStack((ItemLike)Items.PUMPKIN_SEEDS, 4));
/*    */ 
/*    */ 
/*    */         
/* 50 */         $$9.setDeltaMovement(0.05D * $$8
/* 51 */             .getStepX() + $$1.random.nextDouble() * 0.02D, 0.05D, 0.05D * $$8
/*    */             
/* 53 */             .getStepZ() + $$1.random.nextDouble() * 0.02D);
/*    */ 
/*    */         
/* 56 */         $$1.addFreshEntity((Entity)$$9);
/*    */         
/* 58 */         $$6.hurtAndBreak(1, (LivingEntity)$$3, $$1 -> $$1.broadcastBreakEvent($$0));
/* 59 */         $$1.gameEvent((Entity)$$3, GameEvent.SHEAR, $$2);
/* 60 */         $$3.awardStat(Stats.ITEM_USED.get(Items.SHEARS));
/*    */       } 
/*    */       
/* 63 */       return InteractionResult.sidedSuccess($$1.isClientSide);
/*    */     } 
/*    */     
/* 66 */     return super.use($$0, $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\PumpkinBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */