/*     */ package net.minecraft.world.entity.item;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.HolderGetter;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.NbtUtils;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityDimensions;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.MoverType;
/*     */ import net.minecraft.world.entity.Pose;
/*     */ import net.minecraft.world.entity.TraceableEntity;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ 
/*     */ public class PrimedTnt extends Entity implements TraceableEntity {
/*  26 */   private static final EntityDataAccessor<Integer> DATA_FUSE_ID = SynchedEntityData.defineId(PrimedTnt.class, EntityDataSerializers.INT);
/*  27 */   private static final EntityDataAccessor<BlockState> DATA_BLOCK_STATE_ID = SynchedEntityData.defineId(PrimedTnt.class, EntityDataSerializers.BLOCK_STATE);
/*     */   
/*     */   private static final int DEFAULT_FUSE_TIME = 80;
/*     */   
/*     */   private static final String TAG_BLOCK_STATE = "block_state";
/*     */   public static final String TAG_FUSE = "fuse";
/*     */   @Nullable
/*     */   private LivingEntity owner;
/*     */   
/*     */   public PrimedTnt(EntityType<? extends PrimedTnt> $$0, Level $$1) {
/*  37 */     super($$0, $$1);
/*  38 */     this.blocksBuilding = true;
/*     */   }
/*     */   
/*     */   public PrimedTnt(Level $$0, double $$1, double $$2, double $$3, @Nullable LivingEntity $$4) {
/*  42 */     this(EntityType.TNT, $$0);
/*     */     
/*  44 */     setPos($$1, $$2, $$3);
/*     */     
/*  46 */     double $$5 = $$0.random.nextDouble() * 6.2831854820251465D;
/*     */     
/*  48 */     setDeltaMovement(
/*  49 */         -Math.sin($$5) * 0.02D, 0.20000000298023224D, 
/*     */         
/*  51 */         -Math.cos($$5) * 0.02D);
/*     */ 
/*     */     
/*  54 */     setFuse(80);
/*     */     
/*  56 */     this.xo = $$1;
/*  57 */     this.yo = $$2;
/*  58 */     this.zo = $$3;
/*  59 */     this.owner = $$4;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/*  64 */     this.entityData.define(DATA_FUSE_ID, Integer.valueOf(80));
/*  65 */     this.entityData.define(DATA_BLOCK_STATE_ID, Blocks.TNT.defaultBlockState());
/*     */   }
/*     */ 
/*     */   
/*     */   protected Entity.MovementEmission getMovementEmission() {
/*  70 */     return Entity.MovementEmission.NONE;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPickable() {
/*  75 */     return !isRemoved();
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/*  80 */     if (!isNoGravity()) {
/*  81 */       setDeltaMovement(getDeltaMovement().add(0.0D, -0.04D, 0.0D));
/*     */     }
/*  83 */     move(MoverType.SELF, getDeltaMovement());
/*  84 */     setDeltaMovement(getDeltaMovement().scale(0.98D));
/*     */     
/*  86 */     if (onGround())
/*     */     {
/*  88 */       setDeltaMovement(getDeltaMovement().multiply(0.7D, -0.5D, 0.7D));
/*     */     }
/*     */     
/*  91 */     int $$0 = getFuse() - 1;
/*  92 */     setFuse($$0);
/*  93 */     if ($$0 <= 0) {
/*  94 */       discard();
/*  95 */       if (!(level()).isClientSide) {
/*  96 */         explode();
/*     */       }
/*     */     } else {
/*  99 */       updateInWaterStateAndDoFluidPushing();
/* 100 */       if ((level()).isClientSide) {
/* 101 */         level().addParticle((ParticleOptions)ParticleTypes.SMOKE, getX(), getY() + 0.5D, getZ(), 0.0D, 0.0D, 0.0D);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void explode() {
/* 107 */     float $$0 = 4.0F;
/* 108 */     level().explode(this, getX(), getY(0.0625D), getZ(), 4.0F, Level.ExplosionInteraction.TNT);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addAdditionalSaveData(CompoundTag $$0) {
/* 113 */     $$0.putShort("fuse", (short)getFuse());
/* 114 */     $$0.put("block_state", (Tag)NbtUtils.writeBlockState(getBlockState()));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void readAdditionalSaveData(CompoundTag $$0) {
/* 119 */     setFuse($$0.getShort("fuse"));
/* 120 */     if ($$0.contains("block_state", 10)) {
/* 121 */       setBlockState(NbtUtils.readBlockState((HolderGetter)level().holderLookup(Registries.BLOCK), $$0.getCompound("block_state")));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public LivingEntity getOwner() {
/* 128 */     return this.owner;
/*     */   }
/*     */ 
/*     */   
/*     */   public void restoreFrom(Entity $$0) {
/* 133 */     super.restoreFrom($$0);
/* 134 */     if ($$0 instanceof PrimedTnt) { PrimedTnt $$1 = (PrimedTnt)$$0;
/* 135 */       this.owner = $$1.owner; }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getEyeHeight(Pose $$0, EntityDimensions $$1) {
/* 141 */     return 0.15F;
/*     */   }
/*     */   
/*     */   public void setFuse(int $$0) {
/* 145 */     this.entityData.set(DATA_FUSE_ID, Integer.valueOf($$0));
/*     */   }
/*     */   
/*     */   public int getFuse() {
/* 149 */     return ((Integer)this.entityData.get(DATA_FUSE_ID)).intValue();
/*     */   }
/*     */   
/*     */   public void setBlockState(BlockState $$0) {
/* 153 */     this.entityData.set(DATA_BLOCK_STATE_ID, $$0);
/*     */   }
/*     */   
/*     */   public BlockState getBlockState() {
/* 157 */     return (BlockState)this.entityData.get(DATA_BLOCK_STATE_ID);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\item\PrimedTnt.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */