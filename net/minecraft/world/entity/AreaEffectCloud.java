/*     */ package net.minecraft.world.entity;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.brigadier.StringReader;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.commands.arguments.ParticleArgument;
/*     */ import net.minecraft.core.HolderLookup;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.effect.MobEffectInstance;
/*     */ import net.minecraft.world.item.alchemy.Potion;
/*     */ import net.minecraft.world.item.alchemy.PotionUtils;
/*     */ import net.minecraft.world.item.alchemy.Potions;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.material.PushReaction;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class AreaEffectCloud
/*     */   extends Entity implements TraceableEntity {
/*  35 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private static final int TIME_BETWEEN_APPLICATIONS = 5;
/*     */   
/*  39 */   private static final EntityDataAccessor<Float> DATA_RADIUS = SynchedEntityData.defineId(AreaEffectCloud.class, EntityDataSerializers.FLOAT);
/*  40 */   private static final EntityDataAccessor<Integer> DATA_COLOR = SynchedEntityData.defineId(AreaEffectCloud.class, EntityDataSerializers.INT);
/*  41 */   private static final EntityDataAccessor<Boolean> DATA_WAITING = SynchedEntityData.defineId(AreaEffectCloud.class, EntityDataSerializers.BOOLEAN);
/*  42 */   private static final EntityDataAccessor<ParticleOptions> DATA_PARTICLE = SynchedEntityData.defineId(AreaEffectCloud.class, EntityDataSerializers.PARTICLE);
/*     */   
/*     */   private static final float MAX_RADIUS = 32.0F;
/*     */   
/*     */   private static final float MINIMAL_RADIUS = 0.5F;
/*     */   
/*     */   private static final float DEFAULT_RADIUS = 3.0F;
/*     */   
/*     */   public static final float DEFAULT_WIDTH = 6.0F;
/*     */   public static final float HEIGHT = 0.5F;
/*     */   private static final String TAG_EFFECTS = "effects";
/*  53 */   private Potion potion = Potions.EMPTY;
/*  54 */   private final List<MobEffectInstance> effects = Lists.newArrayList();
/*  55 */   private final Map<Entity, Integer> victims = Maps.newHashMap();
/*  56 */   private int duration = 600;
/*  57 */   private int waitTime = 20;
/*  58 */   private int reapplicationDelay = 20;
/*     */   private boolean fixedColor;
/*     */   private int durationOnUse;
/*     */   private float radiusOnUse;
/*     */   private float radiusPerTick;
/*     */   @Nullable
/*     */   private LivingEntity owner;
/*     */   @Nullable
/*     */   private UUID ownerUUID;
/*     */   
/*     */   public AreaEffectCloud(EntityType<? extends AreaEffectCloud> $$0, Level $$1) {
/*  69 */     super($$0, $$1);
/*  70 */     this.noPhysics = true;
/*     */   }
/*     */   
/*     */   public AreaEffectCloud(Level $$0, double $$1, double $$2, double $$3) {
/*  74 */     this(EntityType.AREA_EFFECT_CLOUD, $$0);
/*  75 */     setPos($$1, $$2, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/*  80 */     getEntityData().define(DATA_COLOR, Integer.valueOf(0));
/*  81 */     getEntityData().define(DATA_RADIUS, Float.valueOf(3.0F));
/*  82 */     getEntityData().define(DATA_WAITING, Boolean.valueOf(false));
/*  83 */     getEntityData().define(DATA_PARTICLE, ParticleTypes.ENTITY_EFFECT);
/*     */   }
/*     */   
/*     */   public void setRadius(float $$0) {
/*  87 */     if (!(level()).isClientSide) {
/*  88 */       getEntityData().set(DATA_RADIUS, Float.valueOf(Mth.clamp($$0, 0.0F, 32.0F)));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void refreshDimensions() {
/*  94 */     double $$0 = getX();
/*  95 */     double $$1 = getY();
/*  96 */     double $$2 = getZ();
/*  97 */     super.refreshDimensions();
/*  98 */     setPos($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   public float getRadius() {
/* 102 */     return ((Float)getEntityData().get(DATA_RADIUS)).floatValue();
/*     */   }
/*     */   
/*     */   public void setPotion(Potion $$0) {
/* 106 */     this.potion = $$0;
/* 107 */     if (!this.fixedColor) {
/* 108 */       updateColor();
/*     */     }
/*     */   }
/*     */   
/*     */   private void updateColor() {
/* 113 */     if (this.potion == Potions.EMPTY && this.effects.isEmpty()) {
/* 114 */       getEntityData().set(DATA_COLOR, Integer.valueOf(0));
/*     */     } else {
/* 116 */       getEntityData().set(DATA_COLOR, Integer.valueOf(PotionUtils.getColor(PotionUtils.getAllEffects(this.potion, this.effects))));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addEffect(MobEffectInstance $$0) {
/* 121 */     this.effects.add($$0);
/* 122 */     if (!this.fixedColor) {
/* 123 */       updateColor();
/*     */     }
/*     */   }
/*     */   
/*     */   public int getColor() {
/* 128 */     return ((Integer)getEntityData().get(DATA_COLOR)).intValue();
/*     */   }
/*     */   
/*     */   public void setFixedColor(int $$0) {
/* 132 */     this.fixedColor = true;
/* 133 */     getEntityData().set(DATA_COLOR, Integer.valueOf($$0));
/*     */   }
/*     */   
/*     */   public ParticleOptions getParticle() {
/* 137 */     return (ParticleOptions)getEntityData().get(DATA_PARTICLE);
/*     */   }
/*     */   
/*     */   public void setParticle(ParticleOptions $$0) {
/* 141 */     getEntityData().set(DATA_PARTICLE, $$0);
/*     */   }
/*     */   
/*     */   protected void setWaiting(boolean $$0) {
/* 145 */     getEntityData().set(DATA_WAITING, Boolean.valueOf($$0));
/*     */   }
/*     */   
/*     */   public boolean isWaiting() {
/* 149 */     return ((Boolean)getEntityData().get(DATA_WAITING)).booleanValue();
/*     */   }
/*     */   
/*     */   public int getDuration() {
/* 153 */     return this.duration;
/*     */   }
/*     */   
/*     */   public void setDuration(int $$0) {
/* 157 */     this.duration = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 162 */     super.tick();
/* 163 */     boolean $$0 = isWaiting();
/*     */     
/* 165 */     float $$1 = getRadius();
/* 166 */     if ((level()).isClientSide) {
/* 167 */       int $$5; float $$6; if ($$0 && this.random.nextBoolean()) {
/*     */         return;
/*     */       }
/* 170 */       ParticleOptions $$2 = getParticle();
/*     */ 
/*     */ 
/*     */       
/* 174 */       if ($$0) {
/* 175 */         int $$3 = 2;
/* 176 */         float $$4 = 0.2F;
/*     */       } else {
/* 178 */         $$5 = Mth.ceil(3.1415927F * $$1 * $$1);
/* 179 */         $$6 = $$1;
/*     */       } 
/*     */       
/* 182 */       for (int $$7 = 0; $$7 < $$5; $$7++) {
/* 183 */         double $$20, $$21, $$22; float $$8 = this.random.nextFloat() * 6.2831855F;
/* 184 */         float $$9 = Mth.sqrt(this.random.nextFloat()) * $$6;
/* 185 */         double $$10 = getX() + (Mth.cos($$8) * $$9);
/* 186 */         double $$11 = getY();
/* 187 */         double $$12 = getZ() + (Mth.sin($$8) * $$9);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 192 */         if ($$2.getType() == ParticleTypes.ENTITY_EFFECT) {
/* 193 */           int $$13 = ($$0 && this.random.nextBoolean()) ? 16777215 : getColor();
/* 194 */           double $$14 = (($$13 >> 16 & 0xFF) / 255.0F);
/* 195 */           double $$15 = (($$13 >> 8 & 0xFF) / 255.0F);
/* 196 */           double $$16 = (($$13 & 0xFF) / 255.0F);
/* 197 */         } else if ($$0) {
/* 198 */           double $$17 = 0.0D;
/* 199 */           double $$18 = 0.0D;
/* 200 */           double $$19 = 0.0D;
/*     */         } else {
/* 202 */           $$20 = (0.5D - this.random.nextDouble()) * 0.15D;
/* 203 */           $$21 = 0.009999999776482582D;
/* 204 */           $$22 = (0.5D - this.random.nextDouble()) * 0.15D;
/*     */         } 
/*     */         
/* 207 */         level().addAlwaysVisibleParticle($$2, $$10, $$11, $$12, $$20, $$21, $$22);
/*     */       } 
/*     */     } else {
/* 210 */       if (this.tickCount >= this.waitTime + this.duration) {
/* 211 */         discard();
/*     */         
/*     */         return;
/*     */       } 
/* 215 */       boolean $$23 = (this.tickCount < this.waitTime);
/* 216 */       if ($$0 != $$23) {
/* 217 */         setWaiting($$23);
/*     */       }
/* 219 */       if ($$23) {
/*     */         return;
/*     */       }
/*     */       
/* 223 */       if (this.radiusPerTick != 0.0F) {
/* 224 */         $$1 += this.radiusPerTick;
/* 225 */         if ($$1 < 0.5F) {
/* 226 */           discard();
/*     */           return;
/*     */         } 
/* 229 */         setRadius($$1);
/*     */       } 
/*     */       
/* 232 */       if (this.tickCount % 5 == 0) {
/* 233 */         this.victims.entrySet().removeIf($$0 -> (this.tickCount >= ((Integer)$$0.getValue()).intValue()));
/* 234 */         List<MobEffectInstance> $$24 = Lists.newArrayList();
/* 235 */         for (MobEffectInstance $$25 : this.potion.getEffects()) {
/* 236 */           $$24.add(new MobEffectInstance($$25.getEffect(), $$25.mapDuration($$0 -> $$0 / 4), $$25.getAmplifier(), $$25.isAmbient(), $$25.isVisible()));
/*     */         }
/* 238 */         $$24.addAll(this.effects);
/*     */         
/* 240 */         if ($$24.isEmpty()) {
/* 241 */           this.victims.clear();
/*     */         } else {
/* 243 */           List<LivingEntity> $$26 = level().getEntitiesOfClass(LivingEntity.class, getBoundingBox());
/* 244 */           if (!$$26.isEmpty()) {
/* 245 */             for (LivingEntity $$27 : $$26) {
/* 246 */               if (this.victims.containsKey($$27) || !$$27.isAffectedByPotions()) {
/*     */                 continue;
/*     */               }
/* 249 */               double $$28 = $$27.getX() - getX();
/* 250 */               double $$29 = $$27.getZ() - getZ();
/* 251 */               double $$30 = $$28 * $$28 + $$29 * $$29;
/* 252 */               if ($$30 <= ($$1 * $$1)) {
/* 253 */                 this.victims.put($$27, Integer.valueOf(this.tickCount + this.reapplicationDelay));
/* 254 */                 for (MobEffectInstance $$31 : $$24) {
/* 255 */                   if ($$31.getEffect().isInstantenous()) {
/* 256 */                     $$31.getEffect().applyInstantenousEffect(this, getOwner(), $$27, $$31.getAmplifier(), 0.5D); continue;
/*     */                   } 
/* 258 */                   $$27.addEffect(new MobEffectInstance($$31), this);
/*     */                 } 
/*     */                 
/* 261 */                 if (this.radiusOnUse != 0.0F) {
/* 262 */                   $$1 += this.radiusOnUse;
/* 263 */                   if ($$1 < 0.5F) {
/* 264 */                     discard();
/*     */                     return;
/*     */                   } 
/* 267 */                   setRadius($$1);
/*     */                 } 
/* 269 */                 if (this.durationOnUse != 0) {
/* 270 */                   this.duration += this.durationOnUse;
/* 271 */                   if (this.duration <= 0) {
/* 272 */                     discard();
/*     */                     return;
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public float getRadiusOnUse() {
/* 285 */     return this.radiusOnUse;
/*     */   }
/*     */   
/*     */   public void setRadiusOnUse(float $$0) {
/* 289 */     this.radiusOnUse = $$0;
/*     */   }
/*     */   
/*     */   public float getRadiusPerTick() {
/* 293 */     return this.radiusPerTick;
/*     */   }
/*     */   
/*     */   public void setRadiusPerTick(float $$0) {
/* 297 */     this.radiusPerTick = $$0;
/*     */   }
/*     */   
/*     */   public int getDurationOnUse() {
/* 301 */     return this.durationOnUse;
/*     */   }
/*     */   
/*     */   public void setDurationOnUse(int $$0) {
/* 305 */     this.durationOnUse = $$0;
/*     */   }
/*     */   
/*     */   public int getWaitTime() {
/* 309 */     return this.waitTime;
/*     */   }
/*     */   
/*     */   public void setWaitTime(int $$0) {
/* 313 */     this.waitTime = $$0;
/*     */   }
/*     */   
/*     */   public void setOwner(@Nullable LivingEntity $$0) {
/* 317 */     this.owner = $$0;
/* 318 */     this.ownerUUID = ($$0 == null) ? null : $$0.getUUID();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public LivingEntity getOwner() {
/* 324 */     if (this.owner == null && this.ownerUUID != null && level() instanceof ServerLevel) {
/* 325 */       Entity $$0 = ((ServerLevel)level()).getEntity(this.ownerUUID);
/* 326 */       if ($$0 instanceof LivingEntity) {
/* 327 */         this.owner = (LivingEntity)$$0;
/*     */       }
/*     */     } 
/*     */     
/* 331 */     return this.owner;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void readAdditionalSaveData(CompoundTag $$0) {
/* 336 */     this.tickCount = $$0.getInt("Age");
/* 337 */     this.duration = $$0.getInt("Duration");
/* 338 */     this.waitTime = $$0.getInt("WaitTime");
/* 339 */     this.reapplicationDelay = $$0.getInt("ReapplicationDelay");
/* 340 */     this.durationOnUse = $$0.getInt("DurationOnUse");
/* 341 */     this.radiusOnUse = $$0.getFloat("RadiusOnUse");
/* 342 */     this.radiusPerTick = $$0.getFloat("RadiusPerTick");
/* 343 */     setRadius($$0.getFloat("Radius"));
/* 344 */     if ($$0.hasUUID("Owner")) {
/* 345 */       this.ownerUUID = $$0.getUUID("Owner");
/*     */     }
/*     */     
/* 348 */     if ($$0.contains("Particle", 8)) {
/*     */       try {
/* 350 */         setParticle(ParticleArgument.readParticle(new StringReader($$0.getString("Particle")), (HolderLookup)BuiltInRegistries.PARTICLE_TYPE.asLookup()));
/* 351 */       } catch (CommandSyntaxException $$1) {
/* 352 */         LOGGER.warn("Couldn't load custom particle {}", $$0.getString("Particle"), $$1);
/*     */       } 
/*     */     }
/*     */     
/* 356 */     if ($$0.contains("Color", 99)) {
/* 357 */       setFixedColor($$0.getInt("Color"));
/*     */     }
/*     */     
/* 360 */     if ($$0.contains("Potion", 8)) {
/* 361 */       setPotion(PotionUtils.getPotion($$0));
/*     */     }
/* 363 */     if ($$0.contains("effects", 9)) {
/* 364 */       ListTag $$2 = $$0.getList("effects", 10);
/* 365 */       this.effects.clear();
/* 366 */       for (int $$3 = 0; $$3 < $$2.size(); $$3++) {
/* 367 */         MobEffectInstance $$4 = MobEffectInstance.load($$2.getCompound($$3));
/* 368 */         if ($$4 != null) {
/* 369 */           addEffect($$4);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addAdditionalSaveData(CompoundTag $$0) {
/* 377 */     $$0.putInt("Age", this.tickCount);
/* 378 */     $$0.putInt("Duration", this.duration);
/* 379 */     $$0.putInt("WaitTime", this.waitTime);
/* 380 */     $$0.putInt("ReapplicationDelay", this.reapplicationDelay);
/* 381 */     $$0.putInt("DurationOnUse", this.durationOnUse);
/* 382 */     $$0.putFloat("RadiusOnUse", this.radiusOnUse);
/* 383 */     $$0.putFloat("RadiusPerTick", this.radiusPerTick);
/* 384 */     $$0.putFloat("Radius", getRadius());
/*     */     
/* 386 */     $$0.putString("Particle", getParticle().writeToString());
/*     */     
/* 388 */     if (this.ownerUUID != null) {
/* 389 */       $$0.putUUID("Owner", this.ownerUUID);
/*     */     }
/*     */     
/* 392 */     if (this.fixedColor) {
/* 393 */       $$0.putInt("Color", getColor());
/*     */     }
/*     */     
/* 396 */     if (this.potion != Potions.EMPTY) {
/* 397 */       $$0.putString("Potion", BuiltInRegistries.POTION.getKey(this.potion).toString());
/*     */     }
/* 399 */     if (!this.effects.isEmpty()) {
/* 400 */       ListTag $$1 = new ListTag();
/* 401 */       for (MobEffectInstance $$2 : this.effects) {
/* 402 */         $$1.add($$2.save(new CompoundTag()));
/*     */       }
/* 404 */       $$0.put("effects", (Tag)$$1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onSyncedDataUpdated(EntityDataAccessor<?> $$0) {
/* 410 */     if (DATA_RADIUS.equals($$0)) {
/* 411 */       refreshDimensions();
/*     */     }
/* 413 */     super.onSyncedDataUpdated($$0);
/*     */   }
/*     */   
/*     */   public Potion getPotion() {
/* 417 */     return this.potion;
/*     */   }
/*     */ 
/*     */   
/*     */   public PushReaction getPistonPushReaction() {
/* 422 */     return PushReaction.IGNORE;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityDimensions getDimensions(Pose $$0) {
/* 427 */     return EntityDimensions.scalable(getRadius() * 2.0F, 0.5F);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\AreaEffectCloud.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */