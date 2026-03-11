/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.animal.sniffer.Sniffer;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class SnifferEggBlock extends Block {
/*  27 */   public static final MapCodec<SnifferEggBlock> CODEC = simpleCodec(SnifferEggBlock::new);
/*     */   public static final int MAX_HATCH_LEVEL = 2;
/*     */   
/*     */   public MapCodec<SnifferEggBlock> codec() {
/*  31 */     return CODEC;
/*     */   }
/*     */ 
/*     */   
/*  35 */   public static final IntegerProperty HATCH = BlockStateProperties.HATCH;
/*     */   
/*     */   private static final int REGULAR_HATCH_TIME_TICKS = 24000;
/*     */   
/*     */   private static final int BOOSTED_HATCH_TIME_TICKS = 12000;
/*     */   private static final int RANDOM_HATCH_OFFSET_TICKS = 300;
/*  41 */   private static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 2.0D, 15.0D, 16.0D, 14.0D);
/*     */   
/*     */   public SnifferEggBlock(BlockBehaviour.Properties $$0) {
/*  44 */     super($$0);
/*  45 */     registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)HATCH, Integer.valueOf(0)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/*  50 */     $$0.add(new Property[] { (Property)HATCH });
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  55 */     return SHAPE;
/*     */   }
/*     */   
/*     */   public int getHatchLevel(BlockState $$0) {
/*  59 */     return ((Integer)$$0.getValue((Property)HATCH)).intValue();
/*     */   }
/*     */   
/*     */   private boolean isReadyToHatch(BlockState $$0) {
/*  63 */     return (getHatchLevel($$0) == 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/*  68 */     if (!isReadyToHatch($$0)) {
/*  69 */       $$1.playSound(null, $$2, SoundEvents.SNIFFER_EGG_CRACK, SoundSource.BLOCKS, 0.7F, 0.9F + $$3.nextFloat() * 0.2F);
/*  70 */       $$1.setBlock($$2, (BlockState)$$0.setValue((Property)HATCH, Integer.valueOf(getHatchLevel($$0) + 1)), 2);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  75 */     $$1.playSound(null, $$2, SoundEvents.SNIFFER_EGG_HATCH, SoundSource.BLOCKS, 0.7F, 0.9F + $$3.nextFloat() * 0.2F);
/*  76 */     $$1.destroyBlock($$2, false);
/*     */     
/*  78 */     Sniffer $$4 = (Sniffer)EntityType.SNIFFER.create((Level)$$1);
/*  79 */     if ($$4 != null) {
/*  80 */       Vec3 $$5 = $$2.getCenter();
/*     */       
/*  82 */       $$4.setBaby(true);
/*  83 */       $$4.moveTo($$5.x(), $$5.y(), $$5.z(), Mth.wrapDegrees($$1.random.nextFloat() * 360.0F), 0.0F);
/*     */       
/*  85 */       $$1.addFreshEntity((Entity)$$4);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPlace(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/*  91 */     boolean $$5 = hatchBoost((BlockGetter)$$1, $$2);
/*     */     
/*  93 */     if (!$$1.isClientSide() && $$5) {
/*  94 */       $$1.levelEvent(3009, $$2, 0);
/*     */     }
/*     */     
/*  97 */     int $$6 = $$5 ? 12000 : 24000;
/*  98 */     int $$7 = $$6 / 3;
/*     */     
/* 100 */     $$1.gameEvent(GameEvent.BLOCK_PLACE, $$2, GameEvent.Context.of($$0));
/* 101 */     $$1.scheduleTick($$2, this, $$7 + $$1.random.nextInt(300));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
/* 106 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean hatchBoost(BlockGetter $$0, BlockPos $$1) {
/* 110 */     return $$0.getBlockState($$1.below()).is(BlockTags.SNIFFER_EGG_HATCH_BOOST);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\SnifferEggBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */