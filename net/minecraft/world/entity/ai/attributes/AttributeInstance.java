/*     */ package net.minecraft.world.entity.ai.attributes;
/*     */ 
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArraySet;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.function.Consumer;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ 
/*     */ public class AttributeInstance
/*     */ {
/*     */   private final Attribute attribute;
/*  23 */   private final Map<AttributeModifier.Operation, Set<AttributeModifier>> modifiersByOperation = Maps.newEnumMap(AttributeModifier.Operation.class);
/*  24 */   private final Map<UUID, AttributeModifier> modifierById = (Map<UUID, AttributeModifier>)new Object2ObjectArrayMap();
/*  25 */   private final Set<AttributeModifier> permanentModifiers = (Set<AttributeModifier>)new ObjectArraySet();
/*     */   private double baseValue;
/*     */   private boolean dirty = true;
/*     */   private double cachedValue;
/*     */   private final Consumer<AttributeInstance> onDirty;
/*     */   
/*     */   public AttributeInstance(Attribute $$0, Consumer<AttributeInstance> $$1) {
/*  32 */     this.attribute = $$0;
/*  33 */     this.onDirty = $$1;
/*  34 */     this.baseValue = $$0.getDefaultValue();
/*     */   }
/*     */   
/*     */   public Attribute getAttribute() {
/*  38 */     return this.attribute;
/*     */   }
/*     */   
/*     */   public double getBaseValue() {
/*  42 */     return this.baseValue;
/*     */   }
/*     */   
/*     */   public void setBaseValue(double $$0) {
/*  46 */     if ($$0 == this.baseValue) {
/*     */       return;
/*     */     }
/*  49 */     this.baseValue = $$0;
/*  50 */     setDirty();
/*     */   }
/*     */   
/*     */   public Set<AttributeModifier> getModifiers(AttributeModifier.Operation $$0) {
/*  54 */     return this.modifiersByOperation.computeIfAbsent($$0, $$0 -> Sets.newHashSet());
/*     */   }
/*     */   
/*     */   public Set<AttributeModifier> getModifiers() {
/*  58 */     return (Set<AttributeModifier>)ImmutableSet.copyOf(this.modifierById.values());
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public AttributeModifier getModifier(UUID $$0) {
/*  63 */     return this.modifierById.get($$0);
/*     */   }
/*     */   
/*     */   public boolean hasModifier(AttributeModifier $$0) {
/*  67 */     return (this.modifierById.get($$0.getId()) != null);
/*     */   }
/*     */   
/*     */   private void addModifier(AttributeModifier $$0) {
/*  71 */     AttributeModifier $$1 = this.modifierById.putIfAbsent($$0.getId(), $$0);
/*  72 */     if ($$1 != null) {
/*  73 */       throw new IllegalArgumentException("Modifier is already applied on this attribute!");
/*     */     }
/*     */     
/*  76 */     getModifiers($$0.getOperation()).add($$0);
/*  77 */     setDirty();
/*     */   }
/*     */   
/*     */   public void addTransientModifier(AttributeModifier $$0) {
/*  81 */     addModifier($$0);
/*     */   }
/*     */   
/*     */   public void addPermanentModifier(AttributeModifier $$0) {
/*  85 */     addModifier($$0);
/*  86 */     this.permanentModifiers.add($$0);
/*     */   }
/*     */   
/*     */   protected void setDirty() {
/*  90 */     this.dirty = true;
/*  91 */     this.onDirty.accept(this);
/*     */   }
/*     */   
/*     */   private void removeModifier(AttributeModifier $$0) {
/*  95 */     getModifiers($$0.getOperation()).remove($$0);
/*  96 */     this.modifierById.remove($$0.getId());
/*  97 */     this.permanentModifiers.remove($$0);
/*  98 */     setDirty();
/*     */   }
/*     */   
/*     */   public void removeModifier(UUID $$0) {
/* 102 */     AttributeModifier $$1 = getModifier($$0);
/* 103 */     if ($$1 != null) {
/* 104 */       removeModifier($$1);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean removePermanentModifier(UUID $$0) {
/* 109 */     AttributeModifier $$1 = getModifier($$0);
/* 110 */     if ($$1 != null && this.permanentModifiers.contains($$1)) {
/* 111 */       removeModifier($$1);
/* 112 */       return true;
/*     */     } 
/* 114 */     return false;
/*     */   }
/*     */   
/*     */   public void removeModifiers() {
/* 118 */     for (AttributeModifier $$0 : getModifiers()) {
/* 119 */       removeModifier($$0);
/*     */     }
/*     */   }
/*     */   
/*     */   public double getValue() {
/* 124 */     if (this.dirty) {
/* 125 */       this.cachedValue = calculateValue();
/* 126 */       this.dirty = false;
/*     */     } 
/*     */     
/* 129 */     return this.cachedValue;
/*     */   }
/*     */   
/*     */   private double calculateValue() {
/* 133 */     double $$0 = getBaseValue();
/*     */     
/* 135 */     for (AttributeModifier $$1 : getModifiersOrEmpty(AttributeModifier.Operation.ADDITION)) {
/* 136 */       $$0 += $$1.getAmount();
/*     */     }
/*     */     
/* 139 */     double $$2 = $$0;
/*     */     
/* 141 */     for (AttributeModifier $$3 : getModifiersOrEmpty(AttributeModifier.Operation.MULTIPLY_BASE)) {
/* 142 */       $$2 += $$0 * $$3.getAmount();
/*     */     }
/*     */     
/* 145 */     for (AttributeModifier $$4 : getModifiersOrEmpty(AttributeModifier.Operation.MULTIPLY_TOTAL)) {
/* 146 */       $$2 *= 1.0D + $$4.getAmount();
/*     */     }
/*     */     
/* 149 */     return this.attribute.sanitizeValue($$2);
/*     */   }
/*     */   
/*     */   private Collection<AttributeModifier> getModifiersOrEmpty(AttributeModifier.Operation $$0) {
/* 153 */     return this.modifiersByOperation.getOrDefault($$0, Collections.emptySet());
/*     */   }
/*     */   
/*     */   public void replaceFrom(AttributeInstance $$0) {
/* 157 */     this.baseValue = $$0.baseValue;
/*     */     
/* 159 */     this.modifierById.clear();
/* 160 */     this.modifierById.putAll($$0.modifierById);
/*     */     
/* 162 */     this.permanentModifiers.clear();
/* 163 */     this.permanentModifiers.addAll($$0.permanentModifiers);
/*     */     
/* 165 */     this.modifiersByOperation.clear();
/* 166 */     $$0.modifiersByOperation.forEach(($$0, $$1) -> getModifiers($$0).addAll($$1));
/*     */ 
/*     */     
/* 169 */     setDirty();
/*     */   }
/*     */   
/*     */   public CompoundTag save() {
/* 173 */     CompoundTag $$0 = new CompoundTag();
/*     */     
/* 175 */     $$0.putString("Name", BuiltInRegistries.ATTRIBUTE.getKey(this.attribute).toString());
/* 176 */     $$0.putDouble("Base", this.baseValue);
/*     */     
/* 178 */     if (!this.permanentModifiers.isEmpty()) {
/* 179 */       ListTag $$1 = new ListTag();
/* 180 */       for (AttributeModifier $$2 : this.permanentModifiers) {
/* 181 */         $$1.add($$2.save());
/*     */       }
/* 183 */       $$0.put("Modifiers", (Tag)$$1);
/*     */     } 
/* 185 */     return $$0;
/*     */   }
/*     */   
/*     */   public void load(CompoundTag $$0) {
/* 189 */     this.baseValue = $$0.getDouble("Base");
/* 190 */     if ($$0.contains("Modifiers", 9)) {
/* 191 */       ListTag $$1 = $$0.getList("Modifiers", 10);
/*     */       
/* 193 */       for (int $$2 = 0; $$2 < $$1.size(); $$2++) {
/* 194 */         AttributeModifier $$3 = AttributeModifier.load($$1.getCompound($$2));
/* 195 */         if ($$3 != null) {
/*     */ 
/*     */           
/* 198 */           this.modifierById.put($$3.getId(), $$3);
/* 199 */           getModifiers($$3.getOperation()).add($$3);
/* 200 */           this.permanentModifiers.add($$3);
/*     */         } 
/*     */       } 
/* 203 */     }  setDirty();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\attributes\AttributeInstance.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */