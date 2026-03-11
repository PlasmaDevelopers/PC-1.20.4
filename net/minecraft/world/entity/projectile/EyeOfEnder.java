/*     */ package net.minecraft.world.entity.projectile;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.item.ItemEntity;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class EyeOfEnder extends Entity implements ItemSupplier {
/*  22 */   private static final EntityDataAccessor<ItemStack> DATA_ITEM_STACK = SynchedEntityData.defineId(EyeOfEnder.class, EntityDataSerializers.ITEM_STACK);
/*     */   
/*     */   private double tx;
/*     */   private double ty;
/*     */   private double tz;
/*     */   private int life;
/*     */   private boolean surviveAfterDeath;
/*     */   
/*     */   public EyeOfEnder(EntityType<? extends EyeOfEnder> $$0, Level $$1) {
/*  31 */     super($$0, $$1);
/*     */   }
/*     */   
/*     */   public EyeOfEnder(Level $$0, double $$1, double $$2, double $$3) {
/*  35 */     this(EntityType.EYE_OF_ENDER, $$0);
/*     */     
/*  37 */     setPos($$1, $$2, $$3);
/*     */   }
/*     */   
/*     */   public void setItem(ItemStack $$0) {
/*  41 */     if (!$$0.is(Items.ENDER_EYE) || $$0.hasTag()) {
/*  42 */       getEntityData().set(DATA_ITEM_STACK, $$0.copyWithCount(1));
/*     */     }
/*     */   }
/*     */   
/*     */   private ItemStack getItemRaw() {
/*  47 */     return (ItemStack)getEntityData().get(DATA_ITEM_STACK);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getItem() {
/*  52 */     ItemStack $$0 = getItemRaw();
/*  53 */     return $$0.isEmpty() ? new ItemStack((ItemLike)Items.ENDER_EYE) : $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/*  58 */     getEntityData().define(DATA_ITEM_STACK, ItemStack.EMPTY);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldRenderAtSqrDistance(double $$0) {
/*  63 */     double $$1 = getBoundingBox().getSize() * 4.0D;
/*  64 */     if (Double.isNaN($$1)) {
/*  65 */       $$1 = 4.0D;
/*     */     }
/*  67 */     $$1 *= 64.0D;
/*  68 */     return ($$0 < $$1 * $$1);
/*     */   }
/*     */   
/*     */   public void signalTo(BlockPos $$0) {
/*  72 */     double $$1 = $$0.getX();
/*  73 */     int $$2 = $$0.getY();
/*  74 */     double $$3 = $$0.getZ();
/*     */     
/*  76 */     double $$4 = $$1 - getX();
/*  77 */     double $$5 = $$3 - getZ();
/*  78 */     double $$6 = Math.sqrt($$4 * $$4 + $$5 * $$5);
/*     */     
/*  80 */     if ($$6 > 12.0D) {
/*  81 */       this.tx = getX() + $$4 / $$6 * 12.0D;
/*  82 */       this.tz = getZ() + $$5 / $$6 * 12.0D;
/*  83 */       this.ty = getY() + 8.0D;
/*     */     } else {
/*  85 */       this.tx = $$1;
/*  86 */       this.ty = $$2;
/*  87 */       this.tz = $$3;
/*     */     } 
/*     */     
/*  90 */     this.life = 0;
/*  91 */     this.surviveAfterDeath = (this.random.nextInt(5) > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void lerpMotion(double $$0, double $$1, double $$2) {
/*  96 */     setDeltaMovement($$0, $$1, $$2);
/*  97 */     if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
/*  98 */       double $$3 = Math.sqrt($$0 * $$0 + $$2 * $$2);
/*  99 */       setYRot((float)(Mth.atan2($$0, $$2) * 57.2957763671875D));
/* 100 */       setXRot((float)(Mth.atan2($$1, $$3) * 57.2957763671875D));
/* 101 */       this.yRotO = getYRot();
/* 102 */       this.xRotO = getXRot();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 108 */     super.tick();
/*     */     
/* 110 */     Vec3 $$0 = getDeltaMovement();
/* 111 */     double $$1 = getX() + $$0.x;
/* 112 */     double $$2 = getY() + $$0.y;
/* 113 */     double $$3 = getZ() + $$0.z;
/*     */     
/* 115 */     double $$4 = $$0.horizontalDistance();
/* 116 */     setXRot(Projectile.lerpRotation(this.xRotO, (float)(Mth.atan2($$0.y, $$4) * 57.2957763671875D)));
/* 117 */     setYRot(Projectile.lerpRotation(this.yRotO, (float)(Mth.atan2($$0.x, $$0.z) * 57.2957763671875D)));
/*     */     
/* 119 */     if (!(level()).isClientSide) {
/* 120 */       double $$5 = this.tx - $$1;
/* 121 */       double $$6 = this.tz - $$3;
/* 122 */       float $$7 = (float)Math.sqrt($$5 * $$5 + $$6 * $$6);
/* 123 */       float $$8 = (float)Mth.atan2($$6, $$5);
/* 124 */       double $$9 = Mth.lerp(0.0025D, $$4, $$7);
/* 125 */       double $$10 = $$0.y;
/* 126 */       if ($$7 < 1.0F) {
/* 127 */         $$9 *= 0.8D;
/* 128 */         $$10 *= 0.8D;
/*     */       } 
/* 130 */       int $$11 = (getY() < this.ty) ? 1 : -1;
/* 131 */       $$0 = new Vec3(Math.cos($$8) * $$9, $$10 + ($$11 - $$10) * 0.014999999664723873D, Math.sin($$8) * $$9);
/* 132 */       setDeltaMovement($$0);
/*     */     } 
/*     */     
/* 135 */     float $$12 = 0.25F;
/* 136 */     if (isInWater()) {
/* 137 */       for (int $$13 = 0; $$13 < 4; $$13++) {
/* 138 */         level().addParticle((ParticleOptions)ParticleTypes.BUBBLE, $$1 - $$0.x * 0.25D, $$2 - $$0.y * 0.25D, $$3 - $$0.z * 0.25D, $$0.x, $$0.y, $$0.z);
/*     */       }
/*     */     } else {
/* 141 */       level().addParticle((ParticleOptions)ParticleTypes.PORTAL, $$1 - $$0.x * 0.25D + this.random.nextDouble() * 0.6D - 0.3D, $$2 - $$0.y * 0.25D - 0.5D, $$3 - $$0.z * 0.25D + this.random.nextDouble() * 0.6D - 0.3D, $$0.x, $$0.y, $$0.z);
/*     */     } 
/*     */     
/* 144 */     if (!(level()).isClientSide) {
/* 145 */       setPos($$1, $$2, $$3);
/*     */       
/* 147 */       this.life++;
/* 148 */       if (this.life > 80 && !(level()).isClientSide) {
/* 149 */         playSound(SoundEvents.ENDER_EYE_DEATH, 1.0F, 1.0F);
/* 150 */         discard();
/* 151 */         if (this.surviveAfterDeath) {
/* 152 */           level().addFreshEntity((Entity)new ItemEntity(level(), getX(), getY(), getZ(), getItem()));
/*     */         } else {
/* 154 */           level().levelEvent(2003, blockPosition(), 0);
/*     */         } 
/*     */       } 
/*     */     } else {
/* 158 */       setPosRaw($$1, $$2, $$3);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 164 */     ItemStack $$1 = getItemRaw();
/* 165 */     if (!$$1.isEmpty()) {
/* 166 */       $$0.put("Item", (Tag)$$1.save(new CompoundTag()));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 172 */     ItemStack $$1 = ItemStack.of($$0.getCompound("Item"));
/* 173 */     setItem($$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getLightLevelDependentMagicValue() {
/* 178 */     return 1.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAttackable() {
/* 183 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\projectile\EyeOfEnder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */