/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.ParticleTypes;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.sounds.SoundSource;
/*    */ import net.minecraft.stats.Stats;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.MenuProvider;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*    */ import net.minecraft.world.level.block.entity.SmokerBlockEntity;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ 
/*    */ public class SmokerBlock extends AbstractFurnaceBlock {
/* 22 */   public static final MapCodec<SmokerBlock> CODEC = simpleCodec(SmokerBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<SmokerBlock> codec() {
/* 26 */     return CODEC;
/*    */   }
/*    */   
/*    */   protected SmokerBlock(BlockBehaviour.Properties $$0) {
/* 30 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockEntity newBlockEntity(BlockPos $$0, BlockState $$1) {
/* 35 */     return (BlockEntity)new SmokerBlockEntity($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level $$0, BlockState $$1, BlockEntityType<T> $$2) {
/* 41 */     return createFurnaceTicker($$0, $$2, BlockEntityType.SMOKER);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void openContainer(Level $$0, BlockPos $$1, Player $$2) {
/* 46 */     BlockEntity $$3 = $$0.getBlockEntity($$1);
/* 47 */     if ($$3 instanceof SmokerBlockEntity) {
/* 48 */       $$2.openMenu((MenuProvider)$$3);
/* 49 */       $$2.awardStat(Stats.INTERACT_WITH_SMOKER);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void animateTick(BlockState $$0, Level $$1, BlockPos $$2, RandomSource $$3) {
/* 55 */     if (!((Boolean)$$0.getValue((Property)LIT)).booleanValue()) {
/*    */       return;
/*    */     }
/*    */     
/* 59 */     double $$4 = $$2.getX() + 0.5D;
/* 60 */     double $$5 = $$2.getY();
/* 61 */     double $$6 = $$2.getZ() + 0.5D;
/*    */     
/* 63 */     if ($$3.nextDouble() < 0.1D) {
/* 64 */       $$1.playLocalSound($$4, $$5, $$6, SoundEvents.SMOKER_SMOKE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
/*    */     }
/*    */     
/* 67 */     $$1.addParticle((ParticleOptions)ParticleTypes.SMOKE, $$4, $$5 + 1.1D, $$6, 0.0D, 0.0D, 0.0D);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\SmokerBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */