/*     */ package net.minecraft.world.entity.item;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.CrashReportCategory;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.HolderGetter;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.NbtUtils;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ClientGamePacketListener;
/*     */ import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.tags.FluidTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntitySelector;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.MoverType;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.item.context.DirectionalPlaceContext;
/*     */ import net.minecraft.world.level.ClipContext;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.AnvilBlock;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.Fallable;
/*     */ import net.minecraft.world.level.block.FallingBlock;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.HitResult;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class FallingBlockEntity extends Entity {
/*  53 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*  55 */   private BlockState blockState = Blocks.SAND.defaultBlockState();
/*     */   public int time;
/*     */   public boolean dropItem = true;
/*     */   private boolean cancelDrop;
/*     */   private boolean hurtEntities;
/*  60 */   private int fallDamageMax = 40;
/*     */   
/*     */   private float fallDamagePerDistance;
/*     */   @Nullable
/*     */   public CompoundTag blockData;
/*  65 */   protected static final EntityDataAccessor<BlockPos> DATA_START_POS = SynchedEntityData.defineId(FallingBlockEntity.class, EntityDataSerializers.BLOCK_POS);
/*     */   
/*     */   public FallingBlockEntity(EntityType<? extends FallingBlockEntity> $$0, Level $$1) {
/*  68 */     super($$0, $$1);
/*     */   }
/*     */   
/*     */   private FallingBlockEntity(Level $$0, double $$1, double $$2, double $$3, BlockState $$4) {
/*  72 */     this(EntityType.FALLING_BLOCK, $$0);
/*  73 */     this.blockState = $$4;
/*  74 */     this.blocksBuilding = true;
/*     */     
/*  76 */     setPos($$1, $$2, $$3);
/*     */     
/*  78 */     setDeltaMovement(Vec3.ZERO);
/*     */     
/*  80 */     this.xo = $$1;
/*  81 */     this.yo = $$2;
/*  82 */     this.zo = $$3;
/*     */     
/*  84 */     setStartPos(blockPosition());
/*     */   }
/*     */   
/*     */   public static FallingBlockEntity fall(Level $$0, BlockPos $$1, BlockState $$2) {
/*  88 */     FallingBlockEntity $$3 = new FallingBlockEntity($$0, $$1.getX() + 0.5D, $$1.getY(), $$1.getZ() + 0.5D, $$2.hasProperty((Property)BlockStateProperties.WATERLOGGED) ? (BlockState)$$2.setValue((Property)BlockStateProperties.WATERLOGGED, Boolean.valueOf(false)) : $$2);
/*     */     
/*  90 */     $$0.setBlock($$1, $$2.getFluidState().createLegacyBlock(), 3);
/*  91 */     $$0.addFreshEntity($$3);
/*  92 */     return $$3;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAttackable() {
/*  97 */     return false;
/*     */   }
/*     */   
/*     */   public void setStartPos(BlockPos $$0) {
/* 101 */     this.entityData.set(DATA_START_POS, $$0);
/*     */   }
/*     */   
/*     */   public BlockPos getStartPos() {
/* 105 */     return (BlockPos)this.entityData.get(DATA_START_POS);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Entity.MovementEmission getMovementEmission() {
/* 110 */     return Entity.MovementEmission.NONE;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/* 115 */     this.entityData.define(DATA_START_POS, BlockPos.ZERO);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPickable() {
/* 120 */     return !isRemoved();
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 125 */     if (this.blockState.isAir()) {
/* 126 */       discard();
/*     */       
/*     */       return;
/*     */     } 
/* 130 */     Block $$0 = this.blockState.getBlock();
/* 131 */     this.time++;
/*     */     
/* 133 */     if (!isNoGravity()) {
/* 134 */       setDeltaMovement(getDeltaMovement().add(0.0D, -0.04D, 0.0D));
/*     */     }
/*     */ 
/*     */     
/* 138 */     move(MoverType.SELF, getDeltaMovement());
/*     */ 
/*     */     
/* 141 */     if (!(level()).isClientSide) {
/* 142 */       BlockPos $$1 = blockPosition();
/*     */       
/* 144 */       boolean $$2 = this.blockState.getBlock() instanceof net.minecraft.world.level.block.ConcretePowderBlock;
/* 145 */       boolean $$3 = ($$2 && level().getFluidState($$1).is(FluidTags.WATER));
/* 146 */       double $$4 = getDeltaMovement().lengthSqr();
/*     */       
/* 148 */       if ($$2 && $$4 > 1.0D) {
/*     */ 
/*     */         
/* 151 */         BlockHitResult $$5 = level().clip(new ClipContext(new Vec3(this.xo, this.yo, this.zo), position(), ClipContext.Block.COLLIDER, ClipContext.Fluid.SOURCE_ONLY, this));
/* 152 */         if ($$5.getType() != HitResult.Type.MISS && level().getFluidState($$5.getBlockPos()).is(FluidTags.WATER)) {
/*     */           
/* 154 */           $$1 = $$5.getBlockPos();
/* 155 */           $$3 = true;
/*     */         } 
/*     */       } 
/*     */       
/* 159 */       if (onGround() || $$3) {
/* 160 */         BlockState $$6 = level().getBlockState($$1);
/*     */ 
/*     */         
/* 163 */         setDeltaMovement(getDeltaMovement().multiply(0.7D, -0.5D, 0.7D));
/*     */         
/* 165 */         if (!$$6.is(Blocks.MOVING_PISTON)) {
/* 166 */           if (!this.cancelDrop) {
/* 167 */             boolean $$7 = $$6.canBeReplaced((BlockPlaceContext)new DirectionalPlaceContext(level(), $$1, Direction.DOWN, ItemStack.EMPTY, Direction.UP));
/*     */             
/* 169 */             boolean $$8 = (FallingBlock.isFree(level().getBlockState($$1.below())) && (!$$2 || !$$3));
/* 170 */             boolean $$9 = (this.blockState.canSurvive((LevelReader)level(), $$1) && !$$8);
/* 171 */             if ($$7 && $$9) {
/* 172 */               if (this.blockState.hasProperty((Property)BlockStateProperties.WATERLOGGED) && level().getFluidState($$1).getType() == Fluids.WATER) {
/* 173 */                 this.blockState = (BlockState)this.blockState.setValue((Property)BlockStateProperties.WATERLOGGED, Boolean.valueOf(true));
/*     */               }
/* 175 */               if (level().setBlock($$1, this.blockState, 3)) {
/*     */                 
/* 177 */                 (((ServerLevel)level()).getChunkSource()).chunkMap.broadcast(this, (Packet)new ClientboundBlockUpdatePacket($$1, level().getBlockState($$1)));
/* 178 */                 discard();
/* 179 */                 if ($$0 instanceof Fallable) {
/* 180 */                   ((Fallable)$$0).onLand(level(), $$1, this.blockState, $$6, this);
/*     */                 }
/* 182 */                 if (this.blockData != null && this.blockState.hasBlockEntity()) {
/* 183 */                   BlockEntity $$10 = level().getBlockEntity($$1);
/*     */                   
/* 185 */                   if ($$10 != null) {
/* 186 */                     CompoundTag $$11 = $$10.saveWithoutMetadata();
/* 187 */                     for (String $$12 : this.blockData.getAllKeys()) {
/* 188 */                       $$11.put($$12, this.blockData.get($$12).copy());
/*     */                     }
/*     */                     try {
/* 191 */                       $$10.load($$11);
/* 192 */                     } catch (Exception $$13) {
/* 193 */                       LOGGER.error("Failed to load block entity from falling block", $$13);
/*     */                     } 
/* 195 */                     $$10.setChanged();
/*     */                   } 
/*     */                 } 
/* 198 */               } else if (this.dropItem && level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
/* 199 */                 discard();
/* 200 */                 callOnBrokenAfterFall($$0, $$1);
/* 201 */                 spawnAtLocation((ItemLike)$$0);
/*     */               } 
/*     */             } else {
/* 204 */               discard();
/* 205 */               if (this.dropItem && level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
/* 206 */                 callOnBrokenAfterFall($$0, $$1);
/* 207 */                 spawnAtLocation((ItemLike)$$0);
/*     */               } 
/*     */             } 
/*     */           } else {
/* 211 */             discard();
/* 212 */             callOnBrokenAfterFall($$0, $$1);
/*     */           } 
/*     */         }
/* 215 */       } else if (!(level()).isClientSide && ((this.time > 100 && ($$1.getY() <= level().getMinBuildHeight() || $$1.getY() > level().getMaxBuildHeight())) || this.time > 600)) {
/*     */         
/* 217 */         if (this.dropItem && level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
/* 218 */           spawnAtLocation((ItemLike)$$0);
/*     */         }
/* 220 */         discard();
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 228 */     setDeltaMovement(getDeltaMovement().scale(0.98D));
/*     */   }
/*     */   
/*     */   public void callOnBrokenAfterFall(Block $$0, BlockPos $$1) {
/* 232 */     if ($$0 instanceof Fallable) {
/* 233 */       ((Fallable)$$0).onBrokenAfterFall(level(), $$1, this);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean causeFallDamage(float $$0, float $$1, DamageSource $$2) {
/* 239 */     if (!this.hurtEntities) {
/* 240 */       return false;
/*     */     }
/*     */     
/* 243 */     int $$3 = Mth.ceil($$0 - 1.0F);
/* 244 */     if ($$3 < 0) {
/* 245 */       return false;
/*     */     }
/*     */     
/* 248 */     Predicate<Entity> $$4 = EntitySelector.NO_CREATIVE_OR_SPECTATOR.and(EntitySelector.LIVING_ENTITY_STILL_ALIVE);
/* 249 */     Block block = this.blockState.getBlock(); Fallable $$5 = (Fallable)block;
/*     */     
/* 251 */     DamageSource $$6 = (block instanceof Fallable) ? $$5.getFallDamageSource(this) : damageSources().fallingBlock(this);
/*     */     
/* 253 */     float $$7 = Math.min(Mth.floor($$3 * this.fallDamagePerDistance), this.fallDamageMax);
/* 254 */     level().getEntities(this, getBoundingBox(), $$4).forEach($$2 -> $$2.hurt($$0, $$1));
/*     */ 
/*     */ 
/*     */     
/* 258 */     boolean $$8 = this.blockState.is(BlockTags.ANVIL);
/* 259 */     if ($$8 && $$7 > 0.0F && this.random.nextFloat() < 0.05F + $$3 * 0.05F) {
/* 260 */       BlockState $$9 = AnvilBlock.damage(this.blockState);
/* 261 */       if ($$9 == null) {
/* 262 */         this.cancelDrop = true;
/*     */       } else {
/* 264 */         this.blockState = $$9;
/*     */       } 
/*     */     } 
/* 267 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addAdditionalSaveData(CompoundTag $$0) {
/* 272 */     $$0.put("BlockState", (Tag)NbtUtils.writeBlockState(this.blockState));
/* 273 */     $$0.putInt("Time", this.time);
/* 274 */     $$0.putBoolean("DropItem", this.dropItem);
/* 275 */     $$0.putBoolean("HurtEntities", this.hurtEntities);
/* 276 */     $$0.putFloat("FallHurtAmount", this.fallDamagePerDistance);
/* 277 */     $$0.putInt("FallHurtMax", this.fallDamageMax);
/* 278 */     if (this.blockData != null) {
/* 279 */       $$0.put("TileEntityData", (Tag)this.blockData);
/*     */     }
/* 281 */     $$0.putBoolean("CancelDrop", this.cancelDrop);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void readAdditionalSaveData(CompoundTag $$0) {
/* 286 */     this.blockState = NbtUtils.readBlockState((HolderGetter)level().holderLookup(Registries.BLOCK), $$0.getCompound("BlockState"));
/*     */     
/* 288 */     this.time = $$0.getInt("Time");
/*     */     
/* 290 */     if ($$0.contains("HurtEntities", 99)) {
/* 291 */       this.hurtEntities = $$0.getBoolean("HurtEntities");
/* 292 */       this.fallDamagePerDistance = $$0.getFloat("FallHurtAmount");
/* 293 */       this.fallDamageMax = $$0.getInt("FallHurtMax");
/* 294 */     } else if (this.blockState.is(BlockTags.ANVIL)) {
/* 295 */       this.hurtEntities = true;
/*     */     } 
/*     */ 
/*     */     
/* 299 */     if ($$0.contains("DropItem", 99)) {
/* 300 */       this.dropItem = $$0.getBoolean("DropItem");
/*     */     }
/*     */     
/* 303 */     if ($$0.contains("TileEntityData", 10)) {
/* 304 */       this.blockData = $$0.getCompound("TileEntityData").copy();
/*     */     }
/*     */     
/* 307 */     this.cancelDrop = $$0.getBoolean("CancelDrop");
/*     */     
/* 309 */     if (this.blockState.isAir()) {
/* 310 */       this.blockState = Blocks.SAND.defaultBlockState();
/*     */     }
/*     */   }
/*     */   
/*     */   public void setHurtsEntities(float $$0, int $$1) {
/* 315 */     this.hurtEntities = true;
/* 316 */     this.fallDamagePerDistance = $$0;
/* 317 */     this.fallDamageMax = $$1;
/*     */   }
/*     */   
/*     */   public void disableDrop() {
/* 321 */     this.cancelDrop = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean displayFireAnimation() {
/* 326 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void fillCrashReportCategory(CrashReportCategory $$0) {
/* 331 */     super.fillCrashReportCategory($$0);
/* 332 */     $$0.setDetail("Immitating BlockState", this.blockState.toString());
/*     */   }
/*     */   
/*     */   public BlockState getBlockState() {
/* 336 */     return this.blockState;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Component getTypeName() {
/* 341 */     return (Component)Component.translatable("entity.minecraft.falling_block_type", new Object[] { this.blockState.getBlock().getName() });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onlyOpCanSetNbt() {
/* 346 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Packet<ClientGamePacketListener> getAddEntityPacket() {
/* 351 */     return (Packet<ClientGamePacketListener>)new ClientboundAddEntityPacket(this, Block.getId(getBlockState()));
/*     */   }
/*     */ 
/*     */   
/*     */   public void recreateFromPacket(ClientboundAddEntityPacket $$0) {
/* 356 */     super.recreateFromPacket($$0);
/* 357 */     this.blockState = Block.stateById($$0.getData());
/* 358 */     this.blocksBuilding = true;
/*     */     
/* 360 */     double $$1 = $$0.getX();
/* 361 */     double $$2 = $$0.getY();
/* 362 */     double $$3 = $$0.getZ();
/*     */     
/* 364 */     setPos($$1, $$2, $$3);
/* 365 */     setStartPos(blockPosition());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\item\FallingBlockEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */