/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.ParticleTypes;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.sounds.SoundSource;
/*    */ import net.minecraft.stats.Stats;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.MenuProvider;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.entity.BlastFurnaceBlockEntity;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ 
/*    */ public class BlastFurnaceBlock extends AbstractFurnaceBlock {
/* 23 */   public static final MapCodec<BlastFurnaceBlock> CODEC = simpleCodec(BlastFurnaceBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<BlastFurnaceBlock> codec() {
/* 27 */     return CODEC;
/*    */   }
/*    */   
/*    */   protected BlastFurnaceBlock(BlockBehaviour.Properties $$0) {
/* 31 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockEntity newBlockEntity(BlockPos $$0, BlockState $$1) {
/* 36 */     return (BlockEntity)new BlastFurnaceBlockEntity($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level $$0, BlockState $$1, BlockEntityType<T> $$2) {
/* 42 */     return createFurnaceTicker($$0, $$2, BlockEntityType.BLAST_FURNACE);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void openContainer(Level $$0, BlockPos $$1, Player $$2) {
/* 47 */     BlockEntity $$3 = $$0.getBlockEntity($$1);
/* 48 */     if ($$3 instanceof BlastFurnaceBlockEntity) {
/* 49 */       $$2.openMenu((MenuProvider)$$3);
/* 50 */       $$2.awardStat(Stats.INTERACT_WITH_BLAST_FURNACE);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void animateTick(BlockState $$0, Level $$1, BlockPos $$2, RandomSource $$3) {
/* 56 */     if (!((Boolean)$$0.getValue((Property)LIT)).booleanValue()) {
/*    */       return;
/*    */     }
/*    */     
/* 60 */     double $$4 = $$2.getX() + 0.5D;
/* 61 */     double $$5 = $$2.getY();
/* 62 */     double $$6 = $$2.getZ() + 0.5D;
/*    */     
/* 64 */     if ($$3.nextDouble() < 0.1D) {
/* 65 */       $$1.playLocalSound($$4, $$5, $$6, SoundEvents.BLASTFURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
/*    */     }
/*    */     
/* 68 */     Direction $$7 = (Direction)$$0.getValue((Property)FACING);
/* 69 */     Direction.Axis $$8 = $$7.getAxis();
/*    */     
/* 71 */     double $$9 = 0.52D;
/* 72 */     double $$10 = $$3.nextDouble() * 0.6D - 0.3D;
/*    */     
/* 74 */     double $$11 = ($$8 == Direction.Axis.X) ? ($$7.getStepX() * 0.52D) : $$10;
/* 75 */     double $$12 = $$3.nextDouble() * 9.0D / 16.0D;
/* 76 */     double $$13 = ($$8 == Direction.Axis.Z) ? ($$7.getStepZ() * 0.52D) : $$10;
/*    */     
/* 78 */     $$1.addParticle((ParticleOptions)ParticleTypes.SMOKE, $$4 + $$11, $$5 + $$12, $$6 + $$13, 0.0D, 0.0D, 0.0D);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\BlastFurnaceBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */