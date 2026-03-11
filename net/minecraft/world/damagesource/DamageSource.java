/*     */ package net.minecraft.world.damagesource;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.tags.TagKey;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ 
/*     */ public class DamageSource
/*     */ {
/*     */   private final Holder<DamageType> type;
/*     */   @Nullable
/*     */   private final Entity causingEntity;
/*     */   @Nullable
/*     */   private final Entity directEntity;
/*     */   @Nullable
/*     */   private final Vec3 damageSourcePosition;
/*     */   
/*     */   public String toString() {
/*  26 */     return "DamageSource (" + type().msgId() + ")";
/*     */   }
/*     */   
/*     */   public float getFoodExhaustion() {
/*  30 */     return type().exhaustion();
/*     */   }
/*     */   
/*     */   public boolean isIndirect() {
/*  34 */     return (this.causingEntity != this.directEntity);
/*     */   }
/*     */   
/*     */   private DamageSource(Holder<DamageType> $$0, @Nullable Entity $$1, @Nullable Entity $$2, @Nullable Vec3 $$3) {
/*  38 */     this.type = $$0;
/*  39 */     this.causingEntity = $$2;
/*  40 */     this.directEntity = $$1;
/*  41 */     this.damageSourcePosition = $$3;
/*     */   }
/*     */   
/*     */   public DamageSource(Holder<DamageType> $$0, @Nullable Entity $$1, @Nullable Entity $$2) {
/*  45 */     this($$0, $$1, $$2, null);
/*     */   }
/*     */   
/*     */   public DamageSource(Holder<DamageType> $$0, Vec3 $$1) {
/*  49 */     this($$0, null, null, $$1);
/*     */   }
/*     */   
/*     */   public DamageSource(Holder<DamageType> $$0, @Nullable Entity $$1) {
/*  53 */     this($$0, $$1, $$1);
/*     */   }
/*     */   
/*     */   public DamageSource(Holder<DamageType> $$0) {
/*  57 */     this($$0, null, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Entity getDirectEntity() {
/*  66 */     return this.directEntity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Entity getEntity() {
/*  75 */     return this.causingEntity;
/*     */   }
/*     */   
/*     */   public Component getLocalizedDeathMessage(LivingEntity $$0) {
/*  79 */     String $$1 = "death.attack." + type().msgId();
/*  80 */     if (this.causingEntity != null || this.directEntity != null) {
/*  81 */       Component $$2 = (this.causingEntity == null) ? this.directEntity.getDisplayName() : this.causingEntity.getDisplayName();
/*  82 */       Entity entity = this.causingEntity; LivingEntity $$3 = (LivingEntity)entity; ItemStack $$4 = (entity instanceof LivingEntity) ? $$3.getMainHandItem() : ItemStack.EMPTY;
/*     */       
/*  84 */       if (!$$4.isEmpty() && $$4.hasCustomHoverName()) {
/*  85 */         return (Component)Component.translatable($$1 + ".item", new Object[] { $$0.getDisplayName(), $$2, $$4.getDisplayName() });
/*     */       }
/*  87 */       return (Component)Component.translatable($$1, new Object[] { $$0.getDisplayName(), $$2 });
/*     */     } 
/*     */ 
/*     */     
/*  91 */     LivingEntity $$5 = $$0.getKillCredit();
/*  92 */     String $$6 = $$1 + ".player";
/*  93 */     if ($$5 != null) {
/*  94 */       return (Component)Component.translatable($$6, new Object[] { $$0.getDisplayName(), $$5.getDisplayName() });
/*     */     }
/*  96 */     return (Component)Component.translatable($$1, new Object[] { $$0.getDisplayName() });
/*     */   }
/*     */ 
/*     */   
/*     */   public String getMsgId() {
/* 101 */     return type().msgId();
/*     */   }
/*     */   
/*     */   public boolean scalesWithDifficulty() {
/* 105 */     switch (type().scaling()) { default: throw new IncompatibleClassChangeError();case NEVER: case WHEN_CAUSED_BY_LIVING_NON_PLAYER: return 
/*     */           
/* 107 */           (this.causingEntity instanceof LivingEntity && !(this.causingEntity instanceof Player));
/*     */       case ALWAYS:
/*     */         break; }
/*     */     
/*     */     return true;
/*     */   } public boolean isCreativePlayer() {
/* 113 */     Entity entity = getEntity(); if (entity instanceof Player) { Player $$0 = (Player)entity; if (($$0.getAbilities()).instabuild); }  return false;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Vec3 getSourcePosition() {
/* 118 */     if (this.damageSourcePosition != null)
/* 119 */       return this.damageSourcePosition; 
/* 120 */     if (this.directEntity != null) {
/* 121 */       return this.directEntity.position();
/*     */     }
/* 123 */     return null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Vec3 sourcePositionRaw() {
/* 128 */     return this.damageSourcePosition;
/*     */   }
/*     */   
/*     */   public boolean is(TagKey<DamageType> $$0) {
/* 132 */     return this.type.is($$0);
/*     */   }
/*     */   
/*     */   public boolean is(ResourceKey<DamageType> $$0) {
/* 136 */     return this.type.is($$0);
/*     */   }
/*     */   
/*     */   public DamageType type() {
/* 140 */     return (DamageType)this.type.value();
/*     */   }
/*     */   
/*     */   public Holder<DamageType> typeHolder() {
/* 144 */     return this.type;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\damagesource\DamageSource.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */