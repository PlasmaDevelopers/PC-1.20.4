/*     */ package net.minecraft.world.entity.projectile;
/*     */ 
/*     */ import com.google.common.base.MoreObjects;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ClientGamePacketListener;
/*     */ import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.tags.EntityTypeTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.TraceableEntity;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.EntityHitResult;
/*     */ import net.minecraft.world.phys.HitResult;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Projectile
/*     */   extends Entity
/*     */   implements TraceableEntity
/*     */ {
/*     */   @Nullable
/*     */   private UUID ownerUUID;
/*     */   @Nullable
/*     */   private Entity cachedOwner;
/*     */   private boolean leftOwner;
/*     */   private boolean hasBeenShot;
/*     */   
/*     */   Projectile(EntityType<? extends Projectile> $$0, Level $$1) {
/*  42 */     super($$0, $$1);
/*     */   }
/*     */   
/*     */   public void setOwner(@Nullable Entity $$0) {
/*  46 */     if ($$0 != null) {
/*  47 */       this.ownerUUID = $$0.getUUID();
/*  48 */       this.cachedOwner = $$0;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Entity getOwner() {
/*  55 */     if (this.cachedOwner != null && !this.cachedOwner.isRemoved()) {
/*  56 */       return this.cachedOwner;
/*     */     }
/*  58 */     if (this.ownerUUID != null) { Level level = level(); if (level instanceof ServerLevel) { ServerLevel $$0 = (ServerLevel)level;
/*  59 */         this.cachedOwner = $$0.getEntity(this.ownerUUID);
/*  60 */         return this.cachedOwner; }
/*     */        }
/*  62 */      return null;
/*     */   }
/*     */   
/*     */   public Entity getEffectSource() {
/*  66 */     return (Entity)MoreObjects.firstNonNull(getOwner(), this);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addAdditionalSaveData(CompoundTag $$0) {
/*  71 */     if (this.ownerUUID != null) {
/*  72 */       $$0.putUUID("Owner", this.ownerUUID);
/*     */     }
/*  74 */     if (this.leftOwner) {
/*  75 */       $$0.putBoolean("LeftOwner", true);
/*     */     }
/*  77 */     $$0.putBoolean("HasBeenShot", this.hasBeenShot);
/*     */   }
/*     */   
/*     */   protected boolean ownedBy(Entity $$0) {
/*  81 */     return $$0.getUUID().equals(this.ownerUUID);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void readAdditionalSaveData(CompoundTag $$0) {
/*  86 */     if ($$0.hasUUID("Owner")) {
/*  87 */       this.ownerUUID = $$0.getUUID("Owner");
/*  88 */       this.cachedOwner = null;
/*     */     } 
/*  90 */     this.leftOwner = $$0.getBoolean("LeftOwner");
/*  91 */     this.hasBeenShot = $$0.getBoolean("HasBeenShot");
/*     */   }
/*     */ 
/*     */   
/*     */   public void restoreFrom(Entity $$0) {
/*  96 */     super.restoreFrom($$0);
/*  97 */     if ($$0 instanceof Projectile) { Projectile $$1 = (Projectile)$$0;
/*  98 */       this.cachedOwner = $$1.cachedOwner; }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 104 */     if (!this.hasBeenShot) {
/* 105 */       gameEvent(GameEvent.PROJECTILE_SHOOT, getOwner());
/* 106 */       this.hasBeenShot = true;
/*     */     } 
/* 108 */     if (!this.leftOwner) {
/* 109 */       this.leftOwner = checkLeftOwner();
/*     */     }
/*     */     
/* 112 */     super.tick();
/*     */   }
/*     */   
/*     */   private boolean checkLeftOwner() {
/* 116 */     Entity $$0 = getOwner();
/* 117 */     if ($$0 != null) {
/* 118 */       for (Entity $$1 : level().getEntities(this, getBoundingBox().expandTowards(getDeltaMovement()).inflate(1.0D), $$0 -> (!$$0.isSpectator() && $$0.isPickable()))) {
/* 119 */         if ($$1.getRootVehicle() == $$0.getRootVehicle()) {
/* 120 */           return false;
/*     */         }
/*     */       } 
/*     */     }
/* 124 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void shoot(double $$0, double $$1, double $$2, float $$3, float $$4) {
/* 132 */     Vec3 $$5 = (new Vec3($$0, $$1, $$2)).normalize().add(this.random.triangle(0.0D, 0.0172275D * $$4), this.random.triangle(0.0D, 0.0172275D * $$4), this.random.triangle(0.0D, 0.0172275D * $$4)).scale($$3);
/*     */     
/* 134 */     setDeltaMovement($$5);
/*     */     
/* 136 */     double $$6 = $$5.horizontalDistance();
/*     */     
/* 138 */     setYRot((float)(Mth.atan2($$5.x, $$5.z) * 57.2957763671875D));
/* 139 */     setXRot((float)(Mth.atan2($$5.y, $$6) * 57.2957763671875D));
/* 140 */     this.yRotO = getYRot();
/* 141 */     this.xRotO = getXRot();
/*     */   }
/*     */   
/*     */   public void shootFromRotation(Entity $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 145 */     float $$6 = -Mth.sin($$2 * 0.017453292F) * Mth.cos($$1 * 0.017453292F);
/* 146 */     float $$7 = -Mth.sin(($$1 + $$3) * 0.017453292F);
/* 147 */     float $$8 = Mth.cos($$2 * 0.017453292F) * Mth.cos($$1 * 0.017453292F);
/* 148 */     shoot($$6, $$7, $$8, $$4, $$5);
/*     */ 
/*     */     
/* 151 */     Vec3 $$9 = $$0.getDeltaMovement();
/* 152 */     setDeltaMovement(getDeltaMovement().add($$9.x, 
/*     */           
/* 154 */           $$0.onGround() ? 0.0D : $$9.y, $$9.z));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onHit(HitResult $$0) {
/* 160 */     HitResult.Type $$1 = $$0.getType();
/* 161 */     if ($$1 == HitResult.Type.ENTITY) {
/* 162 */       onHitEntity((EntityHitResult)$$0);
/* 163 */       level().gameEvent(GameEvent.PROJECTILE_LAND, $$0.getLocation(), GameEvent.Context.of(this, null));
/* 164 */     } else if ($$1 == HitResult.Type.BLOCK) {
/* 165 */       BlockHitResult $$2 = (BlockHitResult)$$0;
/* 166 */       onHitBlock($$2);
/* 167 */       BlockPos $$3 = $$2.getBlockPos();
/* 168 */       level().gameEvent(GameEvent.PROJECTILE_LAND, $$3, GameEvent.Context.of(this, level().getBlockState($$3)));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onHitEntity(EntityHitResult $$0) {}
/*     */   
/*     */   protected void onHitBlock(BlockHitResult $$0) {
/* 176 */     BlockState $$1 = level().getBlockState($$0.getBlockPos());
/* 177 */     $$1.onProjectileHit(level(), $$1, $$0, this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void lerpMotion(double $$0, double $$1, double $$2) {
/* 182 */     setDeltaMovement($$0, $$1, $$2);
/* 183 */     if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
/* 184 */       double $$3 = Math.sqrt($$0 * $$0 + $$2 * $$2);
/* 185 */       setXRot((float)(Mth.atan2($$1, $$3) * 57.2957763671875D));
/* 186 */       setYRot((float)(Mth.atan2($$0, $$2) * 57.2957763671875D));
/* 187 */       this.xRotO = getXRot();
/* 188 */       this.yRotO = getYRot();
/* 189 */       moveTo(getX(), getY(), getZ(), getYRot(), getXRot());
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean canHitEntity(Entity $$0) {
/* 194 */     if (!$$0.canBeHitByProjectile()) {
/* 195 */       return false;
/*     */     }
/* 197 */     Entity $$1 = getOwner();
/* 198 */     return ($$1 == null || this.leftOwner || !$$1.isPassengerOfSameVehicle($$0));
/*     */   }
/*     */   
/*     */   protected void updateRotation() {
/* 202 */     Vec3 $$0 = getDeltaMovement();
/* 203 */     double $$1 = $$0.horizontalDistance();
/*     */     
/* 205 */     setXRot(lerpRotation(this.xRotO, (float)(Mth.atan2($$0.y, $$1) * 57.2957763671875D)));
/* 206 */     setYRot(lerpRotation(this.yRotO, (float)(Mth.atan2($$0.x, $$0.z) * 57.2957763671875D)));
/*     */   }
/*     */   
/*     */   protected static float lerpRotation(float $$0, float $$1) {
/* 210 */     while ($$1 - $$0 < -180.0F) {
/* 211 */       $$0 -= 360.0F;
/*     */     }
/* 213 */     while ($$1 - $$0 >= 180.0F) {
/* 214 */       $$0 += 360.0F;
/*     */     }
/* 216 */     return Mth.lerp(0.2F, $$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public Packet<ClientGamePacketListener> getAddEntityPacket() {
/* 221 */     Entity $$0 = getOwner();
/* 222 */     return (Packet<ClientGamePacketListener>)new ClientboundAddEntityPacket(this, ($$0 == null) ? 0 : $$0.getId());
/*     */   }
/*     */ 
/*     */   
/*     */   public void recreateFromPacket(ClientboundAddEntityPacket $$0) {
/* 227 */     super.recreateFromPacket($$0);
/*     */     
/* 229 */     Entity $$1 = level().getEntity($$0.getData());
/* 230 */     if ($$1 != null) {
/* 231 */       setOwner($$1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mayInteract(Level $$0, BlockPos $$1) {
/* 237 */     Entity $$2 = getOwner();
/* 238 */     if ($$2 instanceof net.minecraft.world.entity.player.Player) {
/* 239 */       return $$2.mayInteract($$0, $$1);
/*     */     }
/* 241 */     return ($$2 == null || $$0.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING));
/*     */   }
/*     */   
/*     */   public boolean mayBreak(Level $$0) {
/* 245 */     return (getType().is(EntityTypeTags.IMPACT_PROJECTILES) && $$0.getGameRules().getBoolean(GameRules.RULE_PROJECTILESCANBREAKBLOCKS));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\projectile\Projectile.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */