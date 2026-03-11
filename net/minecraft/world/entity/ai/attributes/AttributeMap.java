/*     */ package net.minecraft.world.entity.ai.attributes;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Multimap;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class AttributeMap
/*     */ {
/*  23 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*  25 */   private final Map<Attribute, AttributeInstance> attributes = Maps.newHashMap();
/*  26 */   private final Set<AttributeInstance> dirtyAttributes = Sets.newHashSet();
/*     */   private final AttributeSupplier supplier;
/*     */   
/*     */   public AttributeMap(AttributeSupplier $$0) {
/*  30 */     this.supplier = $$0;
/*     */   }
/*     */   
/*     */   private void onAttributeModified(AttributeInstance $$0) {
/*  34 */     if ($$0.getAttribute().isClientSyncable()) {
/*  35 */       this.dirtyAttributes.add($$0);
/*     */     }
/*     */   }
/*     */   
/*     */   public Set<AttributeInstance> getDirtyAttributes() {
/*  40 */     return this.dirtyAttributes;
/*     */   }
/*     */   
/*     */   public Collection<AttributeInstance> getSyncableAttributes() {
/*  44 */     return (Collection<AttributeInstance>)this.attributes.values().stream().filter($$0 -> $$0.getAttribute().isClientSyncable()).collect(Collectors.toList());
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public AttributeInstance getInstance(Attribute $$0) {
/*  49 */     return this.attributes.computeIfAbsent($$0, $$0 -> this.supplier.createInstance(this::onAttributeModified, $$0));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public AttributeInstance getInstance(Holder<Attribute> $$0) {
/*  54 */     return getInstance((Attribute)$$0.value());
/*     */   }
/*     */   
/*     */   public boolean hasAttribute(Attribute $$0) {
/*  58 */     return (this.attributes.get($$0) != null || this.supplier.hasAttribute($$0));
/*     */   }
/*     */   
/*     */   public boolean hasAttribute(Holder<Attribute> $$0) {
/*  62 */     return hasAttribute((Attribute)$$0.value());
/*     */   }
/*     */   
/*     */   public boolean hasModifier(Attribute $$0, UUID $$1) {
/*  66 */     AttributeInstance $$2 = this.attributes.get($$0);
/*  67 */     return ($$2 != null) ? (($$2.getModifier($$1) != null)) : this.supplier.hasModifier($$0, $$1);
/*     */   }
/*     */   
/*     */   public boolean hasModifier(Holder<Attribute> $$0, UUID $$1) {
/*  71 */     return hasModifier((Attribute)$$0.value(), $$1);
/*     */   }
/*     */   
/*     */   public double getValue(Attribute $$0) {
/*  75 */     AttributeInstance $$1 = this.attributes.get($$0);
/*  76 */     return ($$1 != null) ? $$1.getValue() : this.supplier.getValue($$0);
/*     */   }
/*     */   
/*     */   public double getBaseValue(Attribute $$0) {
/*  80 */     AttributeInstance $$1 = this.attributes.get($$0);
/*  81 */     return ($$1 != null) ? $$1.getBaseValue() : this.supplier.getBaseValue($$0);
/*     */   }
/*     */   
/*     */   public double getModifierValue(Attribute $$0, UUID $$1) {
/*  85 */     AttributeInstance $$2 = this.attributes.get($$0);
/*  86 */     return ($$2 != null) ? $$2.getModifier($$1).getAmount() : this.supplier.getModifierValue($$0, $$1);
/*     */   }
/*     */   
/*     */   public double getModifierValue(Holder<Attribute> $$0, UUID $$1) {
/*  90 */     return getModifierValue((Attribute)$$0.value(), $$1);
/*     */   }
/*     */   
/*     */   public void removeAttributeModifiers(Multimap<Attribute, AttributeModifier> $$0) {
/*  94 */     $$0.asMap().forEach(($$0, $$1) -> {
/*     */           AttributeInstance $$2 = this.attributes.get($$0);
/*     */           if ($$2 != null) {
/*     */             $$1.forEach(());
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void addTransientAttributeModifiers(Multimap<Attribute, AttributeModifier> $$0) {
/* 104 */     $$0.forEach(($$0, $$1) -> {
/*     */           AttributeInstance $$2 = getInstance($$0);
/*     */           if ($$2 != null) {
/*     */             $$2.removeModifier($$1.getId());
/*     */             $$2.addTransientModifier($$1);
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void assignValues(AttributeMap $$0) {
/* 115 */     $$0.attributes.values().forEach($$0 -> {
/*     */           AttributeInstance $$1 = getInstance($$0.getAttribute());
/*     */           if ($$1 != null) {
/*     */             $$1.replaceFrom($$0);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public ListTag save() {
/* 124 */     ListTag $$0 = new ListTag();
/* 125 */     for (AttributeInstance $$1 : this.attributes.values()) {
/* 126 */       $$0.add($$1.save());
/*     */     }
/* 128 */     return $$0;
/*     */   }
/*     */   
/*     */   public void load(ListTag $$0) {
/* 132 */     for (int $$1 = 0; $$1 < $$0.size(); $$1++) {
/* 133 */       CompoundTag $$2 = $$0.getCompound($$1);
/* 134 */       String $$3 = $$2.getString("Name");
/* 135 */       Util.ifElse(BuiltInRegistries.ATTRIBUTE.getOptional(ResourceLocation.tryParse($$3)), $$1 -> {
/*     */             AttributeInstance $$2 = getInstance($$1);
/*     */             if ($$2 != null)
/*     */               $$2.load($$0); 
/*     */           }() -> LOGGER.warn("Ignoring unknown attribute '{}'", $$0));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\attributes\AttributeMap.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */