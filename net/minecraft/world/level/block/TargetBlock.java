/*     */ package net.minecraft.world.level.block;
/*     */ 
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.stats.Stats;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.projectile.Projectile;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.IntegerProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class TargetBlock extends Block {
/*  28 */   public static final MapCodec<TargetBlock> CODEC = simpleCodec(TargetBlock::new);
/*     */ 
/*     */   
/*     */   public MapCodec<TargetBlock> codec() {
/*  32 */     return CODEC;
/*     */   }
/*     */   
/*  35 */   private static final IntegerProperty OUTPUT_POWER = BlockStateProperties.POWER;
/*     */   
/*     */   private static final int ACTIVATION_TICKS_ARROWS = 20;
/*     */   private static final int ACTIVATION_TICKS_OTHER = 8;
/*     */   
/*     */   public TargetBlock(BlockBehaviour.Properties $$0) {
/*  41 */     super($$0);
/*  42 */     registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)OUTPUT_POWER, Integer.valueOf(0)));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onProjectileHit(Level $$0, BlockState $$1, BlockHitResult $$2, Projectile $$3) {
/*  47 */     int $$4 = updateRedstoneOutput((LevelAccessor)$$0, $$1, $$2, (Entity)$$3);
/*     */     
/*  49 */     Entity $$5 = $$3.getOwner();
/*  50 */     if ($$5 instanceof ServerPlayer) { ServerPlayer $$6 = (ServerPlayer)$$5;
/*  51 */       $$6.awardStat(Stats.TARGET_HIT);
/*  52 */       CriteriaTriggers.TARGET_BLOCK_HIT.trigger($$6, (Entity)$$3, $$2.getLocation(), $$4); }
/*     */   
/*     */   }
/*     */   
/*     */   private static int updateRedstoneOutput(LevelAccessor $$0, BlockState $$1, BlockHitResult $$2, Entity $$3) {
/*  57 */     int $$4 = getRedstoneStrength($$2, $$2.getLocation());
/*  58 */     int $$5 = ($$3 instanceof net.minecraft.world.entity.projectile.AbstractArrow) ? 20 : 8;
/*     */     
/*  60 */     if (!$$0.getBlockTicks().hasScheduledTick($$2.getBlockPos(), $$1.getBlock())) {
/*  61 */       setOutputPower($$0, $$1, $$4, $$2.getBlockPos(), $$5);
/*     */     }
/*     */     
/*  64 */     return $$4;
/*     */   }
/*     */   private static int getRedstoneStrength(BlockHitResult $$0, Vec3 $$1) {
/*     */     double $$9;
/*  68 */     Direction $$2 = $$0.getDirection();
/*  69 */     double $$3 = Math.abs(Mth.frac($$1.x) - 0.5D);
/*  70 */     double $$4 = Math.abs(Mth.frac($$1.y) - 0.5D);
/*  71 */     double $$5 = Math.abs(Mth.frac($$1.z) - 0.5D);
/*     */ 
/*     */     
/*  74 */     Direction.Axis $$6 = $$2.getAxis();
/*  75 */     if ($$6 == Direction.Axis.Y) {
/*  76 */       double $$7 = Math.max($$3, $$5);
/*  77 */     } else if ($$6 == Direction.Axis.Z) {
/*  78 */       double $$8 = Math.max($$3, $$4);
/*     */     } else {
/*  80 */       $$9 = Math.max($$4, $$5);
/*     */     } 
/*     */     
/*  83 */     return Math.max(1, Mth.ceil(15.0D * Mth.clamp((0.5D - $$9) / 0.5D, 0.0D, 1.0D)));
/*     */   }
/*     */   
/*     */   private static void setOutputPower(LevelAccessor $$0, BlockState $$1, int $$2, BlockPos $$3, int $$4) {
/*  87 */     $$0.setBlock($$3, (BlockState)$$1.setValue((Property)OUTPUT_POWER, Integer.valueOf($$2)), 3);
/*  88 */     $$0.scheduleTick($$3, $$1.getBlock(), $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/*  93 */     if (((Integer)$$0.getValue((Property)OUTPUT_POWER)).intValue() != 0) {
/*  94 */       $$1.setBlock($$2, (BlockState)$$0.setValue((Property)OUTPUT_POWER, Integer.valueOf(0)), 3);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSignal(BlockState $$0, BlockGetter $$1, BlockPos $$2, Direction $$3) {
/* 100 */     return ((Integer)$$0.getValue((Property)OUTPUT_POWER)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSignalSource(BlockState $$0) {
/* 105 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 110 */     $$0.add(new Property[] { (Property)OUTPUT_POWER });
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPlace(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/* 115 */     if ($$1.isClientSide() || $$0.is($$3.getBlock())) {
/*     */       return;
/*     */     }
/*     */     
/* 119 */     if (((Integer)$$0.getValue((Property)OUTPUT_POWER)).intValue() > 0 && !$$1.getBlockTicks().hasScheduledTick($$2, this))
/* 120 */       $$1.setBlock($$2, (BlockState)$$0.setValue((Property)OUTPUT_POWER, Integer.valueOf(0)), 18); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\TargetBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */