/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.stats.Stats;
/*    */ import net.minecraft.world.InteractionHand;
/*    */ import net.minecraft.world.InteractionResult;
/*    */ import net.minecraft.world.MenuProvider;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.item.DyeColor;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.entity.BeaconBlockEntity;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.phys.BlockHitResult;
/*    */ 
/*    */ public class BeaconBlock extends BaseEntityBlock implements BeaconBeamBlock {
/* 23 */   public static final MapCodec<BeaconBlock> CODEC = simpleCodec(BeaconBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<BeaconBlock> codec() {
/* 27 */     return CODEC;
/*    */   }
/*    */   
/*    */   public BeaconBlock(BlockBehaviour.Properties $$0) {
/* 31 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public DyeColor getColor() {
/* 36 */     return DyeColor.WHITE;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockEntity newBlockEntity(BlockPos $$0, BlockState $$1) {
/* 41 */     return (BlockEntity)new BeaconBlockEntity($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level $$0, BlockState $$1, BlockEntityType<T> $$2) {
/* 47 */     return createTickerHelper($$2, BlockEntityType.BEACON, BeaconBlockEntity::tick);
/*    */   }
/*    */ 
/*    */   
/*    */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/* 52 */     if ($$1.isClientSide) {
/* 53 */       return InteractionResult.SUCCESS;
/*    */     }
/*    */     
/* 56 */     BlockEntity $$6 = $$1.getBlockEntity($$2);
/* 57 */     if ($$6 instanceof BeaconBlockEntity) {
/* 58 */       $$3.openMenu((MenuProvider)$$6);
/* 59 */       $$3.awardStat(Stats.INTERACT_WITH_BEACON);
/*    */     } 
/*    */     
/* 62 */     return InteractionResult.CONSUME;
/*    */   }
/*    */ 
/*    */   
/*    */   public RenderShape getRenderShape(BlockState $$0) {
/* 67 */     return RenderShape.MODEL;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setPlacedBy(Level $$0, BlockPos $$1, BlockState $$2, LivingEntity $$3, ItemStack $$4) {
/* 72 */     if ($$4.hasCustomHoverName()) {
/* 73 */       BlockEntity $$5 = $$0.getBlockEntity($$1);
/* 74 */       if ($$5 instanceof BeaconBlockEntity)
/* 75 */         ((BeaconBlockEntity)$$5).setCustomName($$4.getHoverName()); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\BeaconBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */