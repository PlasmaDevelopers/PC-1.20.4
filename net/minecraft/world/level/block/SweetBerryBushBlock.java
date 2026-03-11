/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class SweetBerryBushBlock extends BushBlock implements BonemealableBlock {
/*  31 */   public static final MapCodec<SweetBerryBushBlock> CODEC = simpleCodec(SweetBerryBushBlock::new); private static final float HURT_SPEED_THRESHOLD = 0.003F;
/*     */   public static final int MAX_AGE = 3;
/*     */   
/*     */   public MapCodec<SweetBerryBushBlock> codec() {
/*  35 */     return CODEC;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  40 */   public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
/*     */   
/*  42 */   private static final VoxelShape SAPLING_SHAPE = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 8.0D, 13.0D);
/*  43 */   private static final VoxelShape MID_GROWTH_SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);
/*     */   
/*     */   public SweetBerryBushBlock(BlockBehaviour.Properties $$0) {
/*  46 */     super($$0);
/*  47 */     registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)AGE, Integer.valueOf(0)));
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getCloneItemStack(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/*  52 */     return new ItemStack((ItemLike)Items.SWEET_BERRIES);
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  57 */     if (((Integer)$$0.getValue((Property)AGE)).intValue() == 0)
/*  58 */       return SAPLING_SHAPE; 
/*  59 */     if (((Integer)$$0.getValue((Property)AGE)).intValue() < 3) {
/*  60 */       return MID_GROWTH_SHAPE;
/*     */     }
/*     */     
/*  63 */     return super.getShape($$0, $$1, $$2, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRandomlyTicking(BlockState $$0) {
/*  68 */     return (((Integer)$$0.getValue((Property)AGE)).intValue() < 3);
/*     */   }
/*     */ 
/*     */   
/*     */   public void randomTick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/*  73 */     int $$4 = ((Integer)$$0.getValue((Property)AGE)).intValue();
/*  74 */     if ($$4 < 3 && $$3.nextInt(5) == 0 && $$1.getRawBrightness($$2.above(), 0) >= 9) {
/*  75 */       BlockState $$5 = (BlockState)$$0.setValue((Property)AGE, Integer.valueOf($$4 + 1));
/*  76 */       $$1.setBlock($$2, $$5, 2);
/*  77 */       $$1.gameEvent(GameEvent.BLOCK_CHANGE, $$2, GameEvent.Context.of($$5));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void entityInside(BlockState $$0, Level $$1, BlockPos $$2, Entity $$3) {
/*  83 */     if (!($$3 instanceof net.minecraft.world.entity.LivingEntity) || $$3.getType() == EntityType.FOX || $$3.getType() == EntityType.BEE) {
/*     */       return;
/*     */     }
/*  86 */     $$3.makeStuckInBlock($$0, new Vec3(0.800000011920929D, 0.75D, 0.800000011920929D));
/*     */ 
/*     */     
/*  89 */     if (!$$1.isClientSide && ((Integer)$$0.getValue((Property)AGE)).intValue() > 0 && ($$3.xOld != $$3.getX() || $$3.zOld != $$3.getZ())) {
/*  90 */       double $$4 = Math.abs($$3.getX() - $$3.xOld);
/*  91 */       double $$5 = Math.abs($$3.getZ() - $$3.zOld);
/*     */       
/*  93 */       if ($$4 >= 0.003000000026077032D || $$5 >= 0.003000000026077032D) {
/*  94 */         $$3.hurt($$1.damageSources().sweetBerryBush(), 1.0F);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/* 101 */     int $$6 = ((Integer)$$0.getValue((Property)AGE)).intValue();
/* 102 */     boolean $$7 = ($$6 == 3);
/*     */     
/* 104 */     if (!$$7 && $$3.getItemInHand($$4).is(Items.BONE_MEAL)) {
/* 105 */       return InteractionResult.PASS;
/*     */     }
/*     */     
/* 108 */     if ($$6 > 1) {
/* 109 */       int $$8 = 1 + $$1.random.nextInt(2);
/* 110 */       popResource($$1, $$2, new ItemStack((ItemLike)Items.SWEET_BERRIES, $$8 + ($$7 ? 1 : 0)));
/* 111 */       $$1.playSound(null, $$2, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + $$1.random.nextFloat() * 0.4F);
/* 112 */       BlockState $$9 = (BlockState)$$0.setValue((Property)AGE, Integer.valueOf(1));
/* 113 */       $$1.setBlock($$2, $$9, 2);
/* 114 */       $$1.gameEvent(GameEvent.BLOCK_CHANGE, $$2, GameEvent.Context.of((Entity)$$3, $$9));
/* 115 */       return InteractionResult.sidedSuccess($$1.isClientSide);
/*     */     } 
/*     */     
/* 118 */     return super.use($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 123 */     $$0.add(new Property[] { (Property)AGE });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isValidBonemealTarget(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/* 128 */     return (((Integer)$$2.getValue((Property)AGE)).intValue() < 3);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBonemealSuccess(Level $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 133 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void performBonemeal(ServerLevel $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 138 */     int $$4 = Math.min(3, ((Integer)$$3.getValue((Property)AGE)).intValue() + 1);
/* 139 */     $$0.setBlock($$2, (BlockState)$$3.setValue((Property)AGE, Integer.valueOf($$4)), 2);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\SweetBerryBushBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */