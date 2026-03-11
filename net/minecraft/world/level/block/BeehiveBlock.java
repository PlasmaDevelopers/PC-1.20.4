/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.List;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.stats.Stats;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.animal.Bee;
/*     */ import net.minecraft.world.entity.item.ItemEntity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.BlockItem;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.item.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.world.item.enchantment.Enchantments;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.DirectionProperty;
/*     */ import net.minecraft.world.level.block.state.properties.IntegerProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.level.storage.loot.LootParams;
/*     */ import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class BeehiveBlock extends BaseEntityBlock {
/*  58 */   public static final MapCodec<BeehiveBlock> CODEC = simpleCodec(BeehiveBlock::new);
/*     */ 
/*     */   
/*     */   public MapCodec<BeehiveBlock> codec() {
/*  62 */     return CODEC;
/*     */   }
/*     */   
/*  65 */   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
/*  66 */   public static final IntegerProperty HONEY_LEVEL = BlockStateProperties.LEVEL_HONEY;
/*     */   
/*     */   public static final int MAX_HONEY_LEVELS = 5;
/*     */   private static final int SHEARED_HONEYCOMB_COUNT = 3;
/*     */   
/*     */   public BeehiveBlock(BlockBehaviour.Properties $$0) {
/*  72 */     super($$0);
/*  73 */     registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)HONEY_LEVEL, Integer.valueOf(0))).setValue((Property)FACING, (Comparable)Direction.NORTH));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasAnalogOutputSignal(BlockState $$0) {
/*  78 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAnalogOutputSignal(BlockState $$0, Level $$1, BlockPos $$2) {
/*  83 */     return ((Integer)$$0.getValue((Property)HONEY_LEVEL)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public void playerDestroy(Level $$0, Player $$1, BlockPos $$2, BlockState $$3, @Nullable BlockEntity $$4, ItemStack $$5) {
/*  88 */     super.playerDestroy($$0, $$1, $$2, $$3, $$4, $$5);
/*     */     
/*  90 */     if (!$$0.isClientSide && 
/*  91 */       $$4 instanceof BeehiveBlockEntity) { BeehiveBlockEntity $$6 = (BeehiveBlockEntity)$$4;
/*  92 */       if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, $$5) == 0) {
/*  93 */         $$6.emptyAllLivingFromHive($$1, $$3, BeehiveBlockEntity.BeeReleaseStatus.EMERGENCY);
/*     */         
/*  95 */         $$0.updateNeighbourForOutputSignal($$2, this);
/*     */         
/*  97 */         angerNearbyBees($$0, $$2);
/*     */       } 
/*     */       
/* 100 */       CriteriaTriggers.BEE_NEST_DESTROYED.trigger((ServerPlayer)$$1, $$3, $$5, $$6.getOccupantCount()); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   private void angerNearbyBees(Level $$0, BlockPos $$1) {
/* 106 */     AABB $$2 = (new AABB($$1)).inflate(8.0D, 6.0D, 8.0D);
/* 107 */     List<Bee> $$3 = $$0.getEntitiesOfClass(Bee.class, $$2);
/* 108 */     if (!$$3.isEmpty()) {
/* 109 */       List<Player> $$4 = $$0.getEntitiesOfClass(Player.class, $$2);
/* 110 */       if ($$4.isEmpty()) {
/*     */         return;
/*     */       }
/* 113 */       for (Bee $$5 : $$3) {
/* 114 */         if ($$5.getTarget() == null) {
/* 115 */           Player $$6 = (Player)Util.getRandom($$4, $$0.random);
/* 116 */           $$5.setTarget((LivingEntity)$$6);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void dropHoneycomb(Level $$0, BlockPos $$1) {
/* 123 */     popResource($$0, $$1, new ItemStack((ItemLike)Items.HONEYCOMB, 3));
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/* 128 */     ItemStack $$6 = $$3.getItemInHand($$4);
/* 129 */     int $$7 = ((Integer)$$0.getValue((Property)HONEY_LEVEL)).intValue();
/* 130 */     boolean $$8 = false;
/*     */     
/* 132 */     if ($$7 >= 5) {
/* 133 */       Item $$9 = $$6.getItem();
/* 134 */       if ($$6.is(Items.SHEARS)) {
/* 135 */         $$1.playSound($$3, $$3.getX(), $$3.getY(), $$3.getZ(), SoundEvents.BEEHIVE_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
/* 136 */         dropHoneycomb($$1, $$2);
/* 137 */         $$6.hurtAndBreak(1, (LivingEntity)$$3, $$1 -> $$1.broadcastBreakEvent($$0));
/* 138 */         $$8 = true;
/* 139 */         $$1.gameEvent((Entity)$$3, GameEvent.SHEAR, $$2);
/* 140 */       } else if ($$6.is(Items.GLASS_BOTTLE)) {
/* 141 */         $$6.shrink(1);
/* 142 */         $$1.playSound($$3, $$3.getX(), $$3.getY(), $$3.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
/* 143 */         if ($$6.isEmpty()) {
/* 144 */           $$3.setItemInHand($$4, new ItemStack((ItemLike)Items.HONEY_BOTTLE));
/* 145 */         } else if (!$$3.getInventory().add(new ItemStack((ItemLike)Items.HONEY_BOTTLE))) {
/* 146 */           $$3.drop(new ItemStack((ItemLike)Items.HONEY_BOTTLE), false);
/*     */         } 
/* 148 */         $$8 = true;
/* 149 */         $$1.gameEvent((Entity)$$3, GameEvent.FLUID_PICKUP, $$2);
/*     */       } 
/* 151 */       if (!$$1.isClientSide() && $$8) {
/* 152 */         $$3.awardStat(Stats.ITEM_USED.get($$9));
/*     */       }
/*     */     } 
/*     */     
/* 156 */     if ($$8) {
/* 157 */       if (!CampfireBlock.isSmokeyPos($$1, $$2)) {
/*     */         
/* 159 */         if (hiveContainsBees($$1, $$2)) {
/* 160 */           angerNearbyBees($$1, $$2);
/*     */         }
/* 162 */         releaseBeesAndResetHoneyLevel($$1, $$0, $$2, $$3, BeehiveBlockEntity.BeeReleaseStatus.EMERGENCY);
/*     */       } else {
/* 164 */         resetHoneyLevel($$1, $$0, $$2);
/*     */       } 
/* 166 */       return InteractionResult.sidedSuccess($$1.isClientSide);
/*     */     } 
/*     */     
/* 169 */     return super.use($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */   
/*     */   private boolean hiveContainsBees(Level $$0, BlockPos $$1) {
/* 173 */     BlockEntity $$2 = $$0.getBlockEntity($$1);
/* 174 */     if ($$2 instanceof BeehiveBlockEntity) { BeehiveBlockEntity $$3 = (BeehiveBlockEntity)$$2;
/* 175 */       return !$$3.isEmpty(); }
/*     */ 
/*     */     
/* 178 */     return false;
/*     */   }
/*     */   
/*     */   public void releaseBeesAndResetHoneyLevel(Level $$0, BlockState $$1, BlockPos $$2, @Nullable Player $$3, BeehiveBlockEntity.BeeReleaseStatus $$4) {
/* 182 */     resetHoneyLevel($$0, $$1, $$2);
/*     */     
/* 184 */     BlockEntity $$5 = $$0.getBlockEntity($$2);
/* 185 */     if ($$5 instanceof BeehiveBlockEntity) { BeehiveBlockEntity $$6 = (BeehiveBlockEntity)$$5;
/* 186 */       $$6.emptyAllLivingFromHive($$3, $$1, $$4); }
/*     */   
/*     */   }
/*     */   
/*     */   public void resetHoneyLevel(Level $$0, BlockState $$1, BlockPos $$2) {
/* 191 */     $$0.setBlock($$2, (BlockState)$$1.setValue((Property)HONEY_LEVEL, Integer.valueOf(0)), 3);
/*     */   }
/*     */ 
/*     */   
/*     */   public void animateTick(BlockState $$0, Level $$1, BlockPos $$2, RandomSource $$3) {
/* 196 */     if (((Integer)$$0.getValue((Property)HONEY_LEVEL)).intValue() >= 5) {
/* 197 */       for (int $$4 = 0; $$4 < $$3.nextInt(1) + 1; $$4++) {
/* 198 */         trySpawnDripParticles($$1, $$2, $$0);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void trySpawnDripParticles(Level $$0, BlockPos $$1, BlockState $$2) {
/* 204 */     if (!$$2.getFluidState().isEmpty() || $$0.random.nextFloat() < 0.3F) {
/*     */       return;
/*     */     }
/*     */     
/* 208 */     VoxelShape $$3 = $$2.getCollisionShape((BlockGetter)$$0, $$1);
/* 209 */     double $$4 = $$3.max(Direction.Axis.Y);
/* 210 */     if ($$4 >= 1.0D && !$$2.is(BlockTags.IMPERMEABLE)) {
/* 211 */       double $$5 = $$3.min(Direction.Axis.Y);
/* 212 */       if ($$5 > 0.0D) {
/* 213 */         spawnParticle($$0, $$1, $$3, $$1.getY() + $$5 - 0.05D);
/*     */       } else {
/* 215 */         BlockPos $$6 = $$1.below();
/* 216 */         BlockState $$7 = $$0.getBlockState($$6);
/* 217 */         VoxelShape $$8 = $$7.getCollisionShape((BlockGetter)$$0, $$6);
/* 218 */         double $$9 = $$8.max(Direction.Axis.Y);
/* 219 */         if (($$9 < 1.0D || !$$7.isCollisionShapeFullBlock((BlockGetter)$$0, $$6)) && $$7.getFluidState().isEmpty()) {
/* 220 */           spawnParticle($$0, $$1, $$3, $$1.getY() - 0.05D);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void spawnParticle(Level $$0, BlockPos $$1, VoxelShape $$2, double $$3) {
/* 227 */     spawnFluidParticle($$0, $$1.getX() + $$2.min(Direction.Axis.X), $$1
/* 228 */         .getX() + $$2.max(Direction.Axis.X), $$1
/* 229 */         .getZ() + $$2.min(Direction.Axis.Z), $$1
/* 230 */         .getZ() + $$2.max(Direction.Axis.Z), $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   private void spawnFluidParticle(Level $$0, double $$1, double $$2, double $$3, double $$4, double $$5) {
/* 235 */     $$0.addParticle((ParticleOptions)ParticleTypes.DRIPPING_HONEY, Mth.lerp($$0.random.nextDouble(), $$1, $$2), $$5, Mth.lerp($$0.random.nextDouble(), $$3, $$4), 0.0D, 0.0D, 0.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 240 */     return (BlockState)defaultBlockState().setValue((Property)FACING, (Comparable)$$0.getHorizontalDirection().getOpposite());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 245 */     $$0.add(new Property[] { (Property)HONEY_LEVEL, (Property)FACING });
/*     */   }
/*     */ 
/*     */   
/*     */   public RenderShape getRenderShape(BlockState $$0) {
/* 250 */     return RenderShape.MODEL;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockEntity newBlockEntity(BlockPos $$0, BlockState $$1) {
/* 256 */     return (BlockEntity)new BeehiveBlockEntity($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level $$0, BlockState $$1, BlockEntityType<T> $$2) {
/* 262 */     return $$0.isClientSide ? null : createTickerHelper($$2, BlockEntityType.BEEHIVE, BeehiveBlockEntity::serverTick);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState playerWillDestroy(Level $$0, BlockPos $$1, BlockState $$2, Player $$3) {
/* 267 */     if (!$$0.isClientSide && $$3.isCreative() && $$0.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)) {
/* 268 */       BlockEntity $$4 = $$0.getBlockEntity($$1);
/* 269 */       if ($$4 instanceof BeehiveBlockEntity) { BeehiveBlockEntity $$5 = (BeehiveBlockEntity)$$4;
/* 270 */         ItemStack $$6 = new ItemStack(this);
/* 271 */         int $$7 = ((Integer)$$2.getValue((Property)HONEY_LEVEL)).intValue();
/* 272 */         boolean $$8 = !$$5.isEmpty();
/*     */ 
/*     */         
/* 275 */         if ($$8 || $$7 > 0) {
/* 276 */           if ($$8) {
/* 277 */             CompoundTag $$9 = new CompoundTag();
/* 278 */             $$9.put("Bees", (Tag)$$5.writeBees());
/* 279 */             BlockItem.setBlockEntityData($$6, BlockEntityType.BEEHIVE, $$9);
/*     */           } 
/*     */ 
/*     */           
/* 283 */           CompoundTag $$10 = new CompoundTag();
/* 284 */           $$10.putInt("honey_level", $$7);
/* 285 */           $$6.addTagElement("BlockStateTag", (Tag)$$10);
/*     */           
/* 287 */           ItemEntity $$11 = new ItemEntity($$0, $$1.getX(), $$1.getY(), $$1.getZ(), $$6);
/* 288 */           $$11.setDefaultPickUpDelay();
/* 289 */           $$0.addFreshEntity((Entity)$$11);
/*     */         }  }
/*     */     
/*     */     } 
/*     */     
/* 294 */     return super.playerWillDestroy($$0, $$1, $$2, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ItemStack> getDrops(BlockState $$0, LootParams.Builder $$1) {
/* 299 */     Entity $$2 = (Entity)$$1.getOptionalParameter(LootContextParams.THIS_ENTITY);
/*     */ 
/*     */     
/* 302 */     if ($$2 instanceof net.minecraft.world.entity.item.PrimedTnt || $$2 instanceof net.minecraft.world.entity.monster.Creeper || $$2 instanceof net.minecraft.world.entity.projectile.WitherSkull || $$2 instanceof net.minecraft.world.entity.boss.wither.WitherBoss || $$2 instanceof net.minecraft.world.entity.vehicle.MinecartTNT) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 308 */       BlockEntity $$3 = (BlockEntity)$$1.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
/* 309 */       if ($$3 instanceof BeehiveBlockEntity) { BeehiveBlockEntity $$4 = (BeehiveBlockEntity)$$3;
/* 310 */         $$4.emptyAllLivingFromHive(null, $$0, BeehiveBlockEntity.BeeReleaseStatus.EMERGENCY); }
/*     */     
/*     */     } 
/* 313 */     return super.getDrops($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 318 */     if ($$3.getBlockState($$5).getBlock() instanceof FireBlock) {
/*     */       
/* 320 */       BlockEntity $$6 = $$3.getBlockEntity($$4);
/* 321 */       if ($$6 instanceof BeehiveBlockEntity) { BeehiveBlockEntity $$7 = (BeehiveBlockEntity)$$6;
/* 322 */         $$7.emptyAllLivingFromHive(null, $$0, BeehiveBlockEntity.BeeReleaseStatus.EMERGENCY); }
/*     */     
/*     */     } 
/* 325 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/* 330 */     return (BlockState)$$0.setValue((Property)FACING, (Comparable)$$1.rotate((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState mirror(BlockState $$0, Mirror $$1) {
/* 335 */     return $$0.rotate($$1.getRotation((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\BeehiveBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */