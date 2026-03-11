/*     */ package net.minecraft.world.entity.projectile;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Collection;
/*     */ import java.util.Set;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.world.effect.MobEffectInstance;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.alchemy.Potion;
/*     */ import net.minecraft.world.item.alchemy.PotionUtils;
/*     */ import net.minecraft.world.item.alchemy.Potions;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ 
/*     */ public class Arrow
/*     */   extends AbstractArrow {
/*     */   private static final int EXPOSED_POTION_DECAY_TIME = 600;
/*     */   private static final int NO_EFFECT_COLOR = -1;
/*  31 */   private static final EntityDataAccessor<Integer> ID_EFFECT_COLOR = SynchedEntityData.defineId(Arrow.class, EntityDataSerializers.INT);
/*     */   
/*     */   private static final byte EVENT_POTION_PUFF = 0;
/*  34 */   private static final ItemStack DEFAULT_ARROW_STACK = new ItemStack((ItemLike)Items.ARROW);
/*  35 */   private Potion potion = Potions.EMPTY;
/*  36 */   private final Set<MobEffectInstance> effects = Sets.newHashSet();
/*     */   private boolean fixedColor;
/*     */   
/*     */   public Arrow(EntityType<? extends Arrow> $$0, Level $$1) {
/*  40 */     super((EntityType)$$0, $$1, DEFAULT_ARROW_STACK);
/*     */   }
/*     */   
/*     */   public Arrow(Level $$0, double $$1, double $$2, double $$3, ItemStack $$4) {
/*  44 */     super(EntityType.ARROW, $$1, $$2, $$3, $$0, $$4);
/*     */   }
/*     */   
/*     */   public Arrow(Level $$0, LivingEntity $$1, ItemStack $$2) {
/*  48 */     super(EntityType.ARROW, $$1, $$0, $$2);
/*     */   }
/*     */   
/*     */   public void setEffectsFromItem(ItemStack $$0) {
/*  52 */     if ($$0.is(Items.TIPPED_ARROW)) {
/*  53 */       this.potion = PotionUtils.getPotion($$0);
/*  54 */       Collection<MobEffectInstance> $$1 = PotionUtils.getCustomEffects($$0);
/*  55 */       if (!$$1.isEmpty()) {
/*  56 */         for (MobEffectInstance $$2 : $$1) {
/*  57 */           this.effects.add(new MobEffectInstance($$2));
/*     */         }
/*     */       }
/*     */       
/*  61 */       int $$3 = getCustomColor($$0);
/*  62 */       if ($$3 == -1) {
/*  63 */         updateColor();
/*     */       } else {
/*  65 */         setFixedColor($$3);
/*     */       } 
/*  67 */     } else if ($$0.is(Items.ARROW)) {
/*  68 */       this.potion = Potions.EMPTY;
/*  69 */       this.effects.clear();
/*  70 */       this.entityData.set(ID_EFFECT_COLOR, Integer.valueOf(-1));
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int getCustomColor(ItemStack $$0) {
/*  75 */     CompoundTag $$1 = $$0.getTag();
/*  76 */     if ($$1 != null && $$1.contains("CustomPotionColor", 99)) {
/*  77 */       return $$1.getInt("CustomPotionColor");
/*     */     }
/*  79 */     return -1;
/*     */   }
/*     */   
/*     */   private void updateColor() {
/*  83 */     this.fixedColor = false;
/*  84 */     if (this.potion == Potions.EMPTY && this.effects.isEmpty()) {
/*  85 */       this.entityData.set(ID_EFFECT_COLOR, Integer.valueOf(-1));
/*     */     } else {
/*  87 */       this.entityData.set(ID_EFFECT_COLOR, Integer.valueOf(PotionUtils.getColor(PotionUtils.getAllEffects(this.potion, this.effects))));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addEffect(MobEffectInstance $$0) {
/*  92 */     this.effects.add($$0);
/*  93 */     getEntityData().set(ID_EFFECT_COLOR, Integer.valueOf(PotionUtils.getColor(PotionUtils.getAllEffects(this.potion, this.effects))));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/*  98 */     super.defineSynchedData();
/*  99 */     this.entityData.define(ID_EFFECT_COLOR, Integer.valueOf(-1));
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 104 */     super.tick();
/*     */     
/* 106 */     if ((level()).isClientSide) {
/* 107 */       if (this.inGround) {
/* 108 */         if (this.inGroundTime % 5 == 0) {
/* 109 */           makeParticle(1);
/*     */         }
/*     */       } else {
/* 112 */         makeParticle(2);
/*     */       }
/*     */     
/* 115 */     } else if (this.inGround && this.inGroundTime != 0 && 
/* 116 */       !this.effects.isEmpty() && this.inGroundTime >= 600) {
/* 117 */       level().broadcastEntityEvent(this, (byte)0);
/* 118 */       this.potion = Potions.EMPTY;
/* 119 */       this.effects.clear();
/* 120 */       this.entityData.set(ID_EFFECT_COLOR, Integer.valueOf(-1));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void makeParticle(int $$0) {
/* 127 */     int $$1 = getColor();
/* 128 */     if ($$1 == -1 || $$0 <= 0) {
/*     */       return;
/*     */     }
/*     */     
/* 132 */     double $$2 = ($$1 >> 16 & 0xFF) / 255.0D;
/* 133 */     double $$3 = ($$1 >> 8 & 0xFF) / 255.0D;
/* 134 */     double $$4 = ($$1 >> 0 & 0xFF) / 255.0D;
/*     */     
/* 136 */     for (int $$5 = 0; $$5 < $$0; $$5++) {
/* 137 */       level().addParticle((ParticleOptions)ParticleTypes.ENTITY_EFFECT, getRandomX(0.5D), getRandomY(), getRandomZ(0.5D), $$2, $$3, $$4);
/*     */     }
/*     */   }
/*     */   
/*     */   public int getColor() {
/* 142 */     return ((Integer)this.entityData.get(ID_EFFECT_COLOR)).intValue();
/*     */   }
/*     */   
/*     */   private void setFixedColor(int $$0) {
/* 146 */     this.fixedColor = true;
/* 147 */     this.entityData.set(ID_EFFECT_COLOR, Integer.valueOf($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 152 */     super.addAdditionalSaveData($$0);
/*     */     
/* 154 */     if (this.potion != Potions.EMPTY) {
/* 155 */       $$0.putString("Potion", BuiltInRegistries.POTION.getKey(this.potion).toString());
/*     */     }
/* 157 */     if (this.fixedColor) {
/* 158 */       $$0.putInt("Color", getColor());
/*     */     }
/* 160 */     if (!this.effects.isEmpty()) {
/* 161 */       ListTag $$1 = new ListTag();
/* 162 */       for (MobEffectInstance $$2 : this.effects) {
/* 163 */         $$1.add($$2.save(new CompoundTag()));
/*     */       }
/* 165 */       $$0.put("custom_potion_effects", (Tag)$$1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 171 */     super.readAdditionalSaveData($$0);
/*     */     
/* 173 */     if ($$0.contains("Potion", 8)) {
/* 174 */       this.potion = PotionUtils.getPotion($$0);
/*     */     }
/* 176 */     for (MobEffectInstance $$1 : PotionUtils.getCustomEffects($$0)) {
/* 177 */       addEffect($$1);
/*     */     }
/*     */     
/* 180 */     if ($$0.contains("Color", 99)) {
/* 181 */       setFixedColor($$0.getInt("Color"));
/*     */     } else {
/* 183 */       updateColor();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doPostHurtEffects(LivingEntity $$0) {
/* 189 */     super.doPostHurtEffects($$0);
/*     */     
/* 191 */     Entity $$1 = getEffectSource();
/* 192 */     for (MobEffectInstance $$2 : this.potion.getEffects()) {
/* 193 */       $$0.addEffect(new MobEffectInstance($$2.getEffect(), Math.max($$2.mapDuration($$0 -> $$0 / 8), 1), $$2.getAmplifier(), $$2.isAmbient(), $$2.isVisible()), $$1);
/*     */     }
/* 195 */     if (!this.effects.isEmpty()) {
/* 196 */       for (MobEffectInstance $$3 : this.effects) {
/* 197 */         $$0.addEffect($$3, $$1);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected ItemStack getPickupItem() {
/* 204 */     ItemStack $$0 = super.getPickupItem();
/*     */     
/* 206 */     if (this.effects.isEmpty() && this.potion == Potions.EMPTY) {
/* 207 */       return $$0;
/*     */     }
/*     */     
/* 210 */     PotionUtils.setPotion($$0, this.potion);
/* 211 */     PotionUtils.setCustomEffects($$0, this.effects);
/* 212 */     if (this.fixedColor) {
/* 213 */       $$0.getOrCreateTag().putInt("CustomPotionColor", getColor());
/*     */     }
/* 215 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleEntityEvent(byte $$0) {
/* 220 */     if ($$0 == 0) {
/* 221 */       int $$1 = getColor();
/* 222 */       if ($$1 != -1) {
/* 223 */         double $$2 = ($$1 >> 16 & 0xFF) / 255.0D;
/* 224 */         double $$3 = ($$1 >> 8 & 0xFF) / 255.0D;
/* 225 */         double $$4 = ($$1 >> 0 & 0xFF) / 255.0D;
/*     */         
/* 227 */         for (int $$5 = 0; $$5 < 20; $$5++) {
/* 228 */           level().addParticle((ParticleOptions)ParticleTypes.ENTITY_EFFECT, getRandomX(0.5D), getRandomY(), getRandomZ(0.5D), $$2, $$3, $$4);
/*     */         }
/*     */       } 
/*     */     } else {
/* 232 */       super.handleEntityEvent($$0);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\projectile\Arrow.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */