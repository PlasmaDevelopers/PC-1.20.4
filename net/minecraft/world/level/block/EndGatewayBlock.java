/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.ParticleTypes;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.block.entity.BlockEntityTicker;
/*    */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*    */ import net.minecraft.world.level.block.entity.TheEndGatewayBlockEntity;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class EndGatewayBlock extends BaseEntityBlock {
/* 20 */   public static final MapCodec<EndGatewayBlock> CODEC = simpleCodec(EndGatewayBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<EndGatewayBlock> codec() {
/* 24 */     return CODEC;
/*    */   }
/*    */   
/*    */   protected EndGatewayBlock(BlockBehaviour.Properties $$0) {
/* 28 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockEntity newBlockEntity(BlockPos $$0, BlockState $$1) {
/* 33 */     return (BlockEntity)new TheEndGatewayBlockEntity($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level $$0, BlockState $$1, BlockEntityType<T> $$2) {
/* 39 */     return createTickerHelper($$2, BlockEntityType.END_GATEWAY, $$0.isClientSide ? TheEndGatewayBlockEntity::beamAnimationTick : TheEndGatewayBlockEntity::teleportTick);
/*    */   }
/*    */ 
/*    */   
/*    */   public void animateTick(BlockState $$0, Level $$1, BlockPos $$2, RandomSource $$3) {
/* 44 */     BlockEntity $$4 = $$1.getBlockEntity($$2);
/* 45 */     if (!($$4 instanceof TheEndGatewayBlockEntity)) {
/*    */       return;
/*    */     }
/* 48 */     int $$5 = ((TheEndGatewayBlockEntity)$$4).getParticleAmount();
/* 49 */     for (int $$6 = 0; $$6 < $$5; $$6++) {
/* 50 */       double $$7 = $$2.getX() + $$3.nextDouble();
/* 51 */       double $$8 = $$2.getY() + $$3.nextDouble();
/* 52 */       double $$9 = $$2.getZ() + $$3.nextDouble();
/* 53 */       double $$10 = ($$3.nextDouble() - 0.5D) * 0.5D;
/* 54 */       double $$11 = ($$3.nextDouble() - 0.5D) * 0.5D;
/* 55 */       double $$12 = ($$3.nextDouble() - 0.5D) * 0.5D;
/*    */       
/* 57 */       int $$13 = $$3.nextInt(2) * 2 - 1;
/* 58 */       if ($$3.nextBoolean()) {
/* 59 */         $$9 = $$2.getZ() + 0.5D + 0.25D * $$13;
/* 60 */         $$12 = ($$3.nextFloat() * 2.0F * $$13);
/*    */       } else {
/* 62 */         $$7 = $$2.getX() + 0.5D + 0.25D * $$13;
/* 63 */         $$10 = ($$3.nextFloat() * 2.0F * $$13);
/*    */       } 
/*    */       
/* 66 */       $$1.addParticle((ParticleOptions)ParticleTypes.PORTAL, $$7, $$8, $$9, $$10, $$11, $$12);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getCloneItemStack(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/* 72 */     return ItemStack.EMPTY;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canBeReplaced(BlockState $$0, Fluid $$1) {
/* 77 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\EndGatewayBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */