/*     */ package net.minecraft.world.entity.vehicle;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.tags.DamageTypeTags;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.projectile.AbstractArrow;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Explosion;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ 
/*     */ public class MinecartTNT
/*     */   extends AbstractMinecart
/*     */ {
/*     */   private static final byte EVENT_PRIME = 10;
/*  29 */   private int fuse = -1;
/*     */   
/*     */   public MinecartTNT(EntityType<? extends MinecartTNT> $$0, Level $$1) {
/*  32 */     super($$0, $$1);
/*     */   }
/*     */   
/*     */   public MinecartTNT(Level $$0, double $$1, double $$2, double $$3) {
/*  36 */     super(EntityType.TNT_MINECART, $$0, $$1, $$2, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public AbstractMinecart.Type getMinecartType() {
/*  41 */     return AbstractMinecart.Type.TNT;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState getDefaultDisplayBlockState() {
/*  46 */     return Blocks.TNT.defaultBlockState();
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/*  51 */     super.tick();
/*     */     
/*  53 */     if (this.fuse > 0) {
/*  54 */       this.fuse--;
/*  55 */       level().addParticle((ParticleOptions)ParticleTypes.SMOKE, getX(), getY() + 0.5D, getZ(), 0.0D, 0.0D, 0.0D);
/*  56 */     } else if (this.fuse == 0) {
/*  57 */       explode(getDeltaMovement().horizontalDistanceSqr());
/*     */     } 
/*     */     
/*  60 */     if (this.horizontalCollision) {
/*  61 */       double $$0 = getDeltaMovement().horizontalDistanceSqr();
/*     */       
/*  63 */       if ($$0 >= 0.009999999776482582D) {
/*  64 */         explode($$0);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hurt(DamageSource $$0, float $$1) {
/*  71 */     Entity $$2 = $$0.getDirectEntity();
/*  72 */     if ($$2 instanceof AbstractArrow) { AbstractArrow $$3 = (AbstractArrow)$$2;
/*  73 */       if ($$3.isOnFire()) {
/*  74 */         DamageSource $$4 = damageSources().explosion(this, $$0.getEntity());
/*  75 */         explode($$4, $$3.getDeltaMovement().lengthSqr());
/*     */       }  }
/*     */     
/*  78 */     return super.hurt($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void destroy(DamageSource $$0) {
/*  83 */     double $$1 = getDeltaMovement().horizontalDistanceSqr();
/*     */     
/*  85 */     if (damageSourceIgnitesTnt($$0) || $$1 >= 0.009999999776482582D) {
/*  86 */       if (this.fuse < 0) {
/*  87 */         primeFuse();
/*  88 */         this.fuse = this.random.nextInt(20) + this.random.nextInt(20);
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*  93 */     destroy(getDropItem());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Item getDropItem() {
/*  99 */     return Items.TNT_MINECART;
/*     */   }
/*     */   
/*     */   protected void explode(double $$0) {
/* 103 */     explode((DamageSource)null, $$0);
/*     */   }
/*     */   
/*     */   protected void explode(@Nullable DamageSource $$0, double $$1) {
/* 107 */     if (!(level()).isClientSide) {
/* 108 */       double $$2 = Math.sqrt($$1);
/* 109 */       if ($$2 > 5.0D) {
/* 110 */         $$2 = 5.0D;
/*     */       }
/* 112 */       level().explode(this, $$0, null, getX(), getY(), getZ(), (float)(4.0D + this.random.nextDouble() * 1.5D * $$2), false, Level.ExplosionInteraction.TNT);
/* 113 */       discard();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean causeFallDamage(float $$0, float $$1, DamageSource $$2) {
/* 119 */     if ($$0 >= 3.0F) {
/* 120 */       float $$3 = $$0 / 10.0F;
/* 121 */       explode(($$3 * $$3));
/*     */     } 
/*     */     
/* 124 */     return super.causeFallDamage($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void activateMinecart(int $$0, int $$1, int $$2, boolean $$3) {
/* 129 */     if ($$3 && this.fuse < 0) {
/* 130 */       primeFuse();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleEntityEvent(byte $$0) {
/* 136 */     if ($$0 == 10) {
/* 137 */       primeFuse();
/*     */     } else {
/* 139 */       super.handleEntityEvent($$0);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void primeFuse() {
/* 144 */     this.fuse = 80;
/*     */     
/* 146 */     if (!(level()).isClientSide) {
/* 147 */       level().broadcastEntityEvent(this, (byte)10);
/* 148 */       if (!isSilent()) {
/* 149 */         level().playSound(null, getX(), getY(), getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getFuse() {
/* 155 */     return this.fuse;
/*     */   }
/*     */   
/*     */   public boolean isPrimed() {
/* 159 */     return (this.fuse > -1);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getBlockExplosionResistance(Explosion $$0, BlockGetter $$1, BlockPos $$2, BlockState $$3, FluidState $$4, float $$5) {
/* 164 */     if (isPrimed() && ($$3.is(BlockTags.RAILS) || $$1.getBlockState($$2.above()).is(BlockTags.RAILS))) {
/* 165 */       return 0.0F;
/*     */     }
/*     */     
/* 168 */     return super.getBlockExplosionResistance($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldBlockExplode(Explosion $$0, BlockGetter $$1, BlockPos $$2, BlockState $$3, float $$4) {
/* 173 */     if (isPrimed() && ($$3.is(BlockTags.RAILS) || $$1.getBlockState($$2.above()).is(BlockTags.RAILS))) {
/* 174 */       return false;
/*     */     }
/*     */     
/* 177 */     return super.shouldBlockExplode($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void readAdditionalSaveData(CompoundTag $$0) {
/* 182 */     super.readAdditionalSaveData($$0);
/* 183 */     if ($$0.contains("TNTFuse", 99)) {
/* 184 */       this.fuse = $$0.getInt("TNTFuse");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addAdditionalSaveData(CompoundTag $$0) {
/* 190 */     super.addAdditionalSaveData($$0);
/* 191 */     $$0.putInt("TNTFuse", this.fuse);
/*     */   }
/*     */ 
/*     */   
/*     */   boolean shouldSourceDestroy(DamageSource $$0) {
/* 196 */     return damageSourceIgnitesTnt($$0);
/*     */   }
/*     */   
/*     */   private static boolean damageSourceIgnitesTnt(DamageSource $$0) {
/* 200 */     return ($$0.is(DamageTypeTags.IS_FIRE) || $$0.is(DamageTypeTags.IS_EXPLOSION));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\vehicle\MinecartTNT.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */