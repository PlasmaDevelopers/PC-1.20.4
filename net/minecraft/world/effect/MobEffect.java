/*     */ package net.minecraft.world.effect;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.UUID;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.ai.attributes.Attribute;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeInstance;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeMap;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeModifier;
/*     */ 
/*     */ public class MobEffect
/*     */ {
/*  22 */   private final Map<Attribute, AttributeModifierTemplate> attributeModifiers = Maps.newHashMap();
/*     */   
/*     */   private final MobEffectCategory category;
/*     */   private final int color;
/*     */   @Nullable
/*     */   private String descriptionId;
/*     */   private Supplier<MobEffectInstance.FactorData> factorDataFactory = () -> null;
/*  29 */   private final Holder.Reference<MobEffect> builtInRegistryHolder = BuiltInRegistries.MOB_EFFECT.createIntrusiveHolder(this);
/*     */   
/*     */   protected MobEffect(MobEffectCategory $$0, int $$1) {
/*  32 */     this.category = $$0;
/*  33 */     this.color = $$1;
/*     */   }
/*     */   
/*     */   public Optional<MobEffectInstance.FactorData> createFactorData() {
/*  37 */     return Optional.ofNullable(this.factorDataFactory.get());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyEffectTick(LivingEntity $$0, int $$1) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyInstantenousEffect(@Nullable Entity $$0, @Nullable Entity $$1, LivingEntity $$2, int $$3, double $$4) {
/*  52 */     applyEffectTick($$2, $$3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldApplyEffectTickThisTick(int $$0, int $$1) {
/*  65 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEffectStarted(LivingEntity $$0, int $$1) {}
/*     */   
/*     */   public boolean isInstantenous() {
/*  72 */     return false;
/*     */   }
/*     */   
/*     */   protected String getOrCreateDescriptionId() {
/*  76 */     if (this.descriptionId == null) {
/*  77 */       this.descriptionId = Util.makeDescriptionId("effect", BuiltInRegistries.MOB_EFFECT.getKey(this));
/*     */     }
/*  79 */     return this.descriptionId;
/*     */   }
/*     */   
/*     */   public String getDescriptionId() {
/*  83 */     return getOrCreateDescriptionId();
/*     */   }
/*     */   
/*     */   public Component getDisplayName() {
/*  87 */     return (Component)Component.translatable(getDescriptionId());
/*     */   }
/*     */   
/*     */   public MobEffectCategory getCategory() {
/*  91 */     return this.category;
/*     */   }
/*     */   
/*     */   public int getColor() {
/*  95 */     return this.color;
/*     */   }
/*     */   
/*     */   public MobEffect addAttributeModifier(Attribute $$0, String $$1, double $$2, AttributeModifier.Operation $$3) {
/*  99 */     this.attributeModifiers.put($$0, new MobEffectAttributeModifierTemplate(UUID.fromString($$1), $$2, $$3));
/* 100 */     return this;
/*     */   }
/*     */   
/*     */   public MobEffect setFactorDataFactory(Supplier<MobEffectInstance.FactorData> $$0) {
/* 104 */     this.factorDataFactory = $$0;
/* 105 */     return this;
/*     */   }
/*     */   
/*     */   public Map<Attribute, AttributeModifierTemplate> getAttributeModifiers() {
/* 109 */     return this.attributeModifiers;
/*     */   }
/*     */   
/*     */   public void removeAttributeModifiers(AttributeMap $$0) {
/* 113 */     for (Map.Entry<Attribute, AttributeModifierTemplate> $$1 : this.attributeModifiers.entrySet()) {
/* 114 */       AttributeInstance $$2 = $$0.getInstance($$1.getKey());
/*     */       
/* 116 */       if ($$2 != null) {
/* 117 */         $$2.removeModifier(((AttributeModifierTemplate)$$1.getValue()).getAttributeModifierId());
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addAttributeModifiers(AttributeMap $$0, int $$1) {
/* 123 */     for (Map.Entry<Attribute, AttributeModifierTemplate> $$2 : this.attributeModifiers.entrySet()) {
/* 124 */       AttributeInstance $$3 = $$0.getInstance($$2.getKey());
/*     */       
/* 126 */       if ($$3 != null) {
/* 127 */         $$3.removeModifier(((AttributeModifierTemplate)$$2.getValue()).getAttributeModifierId());
/* 128 */         $$3.addPermanentModifier(((AttributeModifierTemplate)$$2.getValue()).create($$1));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isBeneficial() {
/* 134 */     return (this.category == MobEffectCategory.BENEFICIAL);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public Holder.Reference<MobEffect> builtInRegistryHolder() {
/* 139 */     return this.builtInRegistryHolder;
/*     */   }
/*     */   
/*     */   private class MobEffectAttributeModifierTemplate implements AttributeModifierTemplate {
/*     */     private final UUID id;
/*     */     private final double amount;
/*     */     private final AttributeModifier.Operation operation;
/*     */     
/*     */     public MobEffectAttributeModifierTemplate(UUID $$0, double $$1, AttributeModifier.Operation $$2) {
/* 148 */       this.id = $$0;
/* 149 */       this.amount = $$1;
/* 150 */       this.operation = $$2;
/*     */     }
/*     */ 
/*     */     
/*     */     public UUID getAttributeModifierId() {
/* 155 */       return this.id;
/*     */     }
/*     */ 
/*     */     
/*     */     public AttributeModifier create(int $$0) {
/* 160 */       return new AttributeModifier(this.id, MobEffect.this.getDescriptionId() + " " + MobEffect.this.getDescriptionId(), this.amount * ($$0 + 1), this.operation);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\effect\MobEffect.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */