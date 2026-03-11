/*     */ package net.minecraft.world.entity.boss.enderdragon;
/*     */ import java.util.Optional;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.NbtUtils;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.tags.DamageTypeTags;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.BaseFireBlock;
/*     */ import net.minecraft.world.level.dimension.end.EndDragonFight;
/*     */ 
/*     */ public class EndCrystal extends Entity {
/*  25 */   private static final EntityDataAccessor<Optional<BlockPos>> DATA_BEAM_TARGET = SynchedEntityData.defineId(EndCrystal.class, EntityDataSerializers.OPTIONAL_BLOCK_POS);
/*  26 */   private static final EntityDataAccessor<Boolean> DATA_SHOW_BOTTOM = SynchedEntityData.defineId(EndCrystal.class, EntityDataSerializers.BOOLEAN);
/*     */   
/*     */   public int time;
/*     */   
/*     */   public EndCrystal(EntityType<? extends EndCrystal> $$0, Level $$1) {
/*  31 */     super($$0, $$1);
/*  32 */     this.blocksBuilding = true;
/*     */     
/*  34 */     this.time = this.random.nextInt(100000);
/*     */   }
/*     */   
/*     */   public EndCrystal(Level $$0, double $$1, double $$2, double $$3) {
/*  38 */     this(EntityType.END_CRYSTAL, $$0);
/*  39 */     setPos($$1, $$2, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Entity.MovementEmission getMovementEmission() {
/*  44 */     return Entity.MovementEmission.NONE;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/*  49 */     getEntityData().define(DATA_BEAM_TARGET, Optional.empty());
/*  50 */     getEntityData().define(DATA_SHOW_BOTTOM, Boolean.valueOf(true));
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/*  55 */     this.time++;
/*     */     
/*  57 */     if (level() instanceof ServerLevel) {
/*  58 */       BlockPos $$0 = blockPosition();
/*  59 */       if (((ServerLevel)level()).getDragonFight() != null && level().getBlockState($$0).isAir()) {
/*  60 */         level().setBlockAndUpdate($$0, BaseFireBlock.getState((BlockGetter)level(), $$0));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addAdditionalSaveData(CompoundTag $$0) {
/*  67 */     if (getBeamTarget() != null) {
/*  68 */       $$0.put("BeamTarget", (Tag)NbtUtils.writeBlockPos(getBeamTarget()));
/*     */     }
/*  70 */     $$0.putBoolean("ShowBottom", showsBottom());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void readAdditionalSaveData(CompoundTag $$0) {
/*  75 */     if ($$0.contains("BeamTarget", 10)) {
/*  76 */       setBeamTarget(NbtUtils.readBlockPos($$0.getCompound("BeamTarget")));
/*     */     }
/*  78 */     if ($$0.contains("ShowBottom", 1)) {
/*  79 */       setShowBottom($$0.getBoolean("ShowBottom"));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPickable() {
/*  85 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hurt(DamageSource $$0, float $$1) {
/*  90 */     if (isInvulnerableTo($$0)) {
/*  91 */       return false;
/*     */     }
/*  93 */     if ($$0.getEntity() instanceof EnderDragon) {
/*  94 */       return false;
/*     */     }
/*  96 */     if (!isRemoved() && !(level()).isClientSide) {
/*  97 */       remove(Entity.RemovalReason.KILLED);
/*     */       
/*  99 */       if (!$$0.is(DamageTypeTags.IS_EXPLOSION)) {
/* 100 */         DamageSource $$2 = ($$0.getEntity() != null) ? damageSources().explosion(this, $$0.getEntity()) : null;
/* 101 */         level().explode(this, $$2, null, getX(), getY(), getZ(), 6.0F, false, Level.ExplosionInteraction.BLOCK);
/*     */       } 
/*     */       
/* 104 */       onDestroyedBy($$0);
/*     */     } 
/* 106 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void kill() {
/* 111 */     onDestroyedBy(damageSources().generic());
/* 112 */     super.kill();
/*     */   }
/*     */   
/*     */   private void onDestroyedBy(DamageSource $$0) {
/* 116 */     if (level() instanceof ServerLevel) {
/* 117 */       EndDragonFight $$1 = ((ServerLevel)level()).getDragonFight();
/* 118 */       if ($$1 != null) {
/* 119 */         $$1.onCrystalDestroyed(this, $$0);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setBeamTarget(@Nullable BlockPos $$0) {
/* 125 */     getEntityData().set(DATA_BEAM_TARGET, Optional.ofNullable($$0));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public BlockPos getBeamTarget() {
/* 130 */     return ((Optional<BlockPos>)getEntityData().get(DATA_BEAM_TARGET)).orElse(null);
/*     */   }
/*     */   
/*     */   public void setShowBottom(boolean $$0) {
/* 134 */     getEntityData().set(DATA_SHOW_BOTTOM, Boolean.valueOf($$0));
/*     */   }
/*     */   
/*     */   public boolean showsBottom() {
/* 138 */     return ((Boolean)getEntityData().get(DATA_SHOW_BOTTOM)).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldRenderAtSqrDistance(double $$0) {
/* 143 */     return (super.shouldRenderAtSqrDistance($$0) || getBeamTarget() != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getPickResult() {
/* 148 */     return new ItemStack((ItemLike)Items.END_CRYSTAL);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\boss\enderdragon\EndCrystal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */