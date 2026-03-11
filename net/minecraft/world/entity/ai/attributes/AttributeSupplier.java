/*    */ package net.minecraft.world.entity.ai.attributes;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Map;
/*    */ import java.util.UUID;
/*    */ import java.util.function.Consumer;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ 
/*    */ public class AttributeSupplier
/*    */ {
/*    */   private final Map<Attribute, AttributeInstance> instances;
/*    */   
/*    */   public AttributeSupplier(Map<Attribute, AttributeInstance> $$0) {
/* 16 */     this.instances = (Map<Attribute, AttributeInstance>)ImmutableMap.copyOf($$0);
/*    */   }
/*    */   
/*    */   private AttributeInstance getAttributeInstance(Attribute $$0) {
/* 20 */     AttributeInstance $$1 = this.instances.get($$0);
/* 21 */     if ($$1 == null) {
/* 22 */       throw new IllegalArgumentException("Can't find attribute " + BuiltInRegistries.ATTRIBUTE.getKey($$0));
/*    */     }
/* 24 */     return $$1;
/*    */   }
/*    */   
/*    */   public double getValue(Attribute $$0) {
/* 28 */     return getAttributeInstance($$0).getValue();
/*    */   }
/*    */   
/*    */   public double getBaseValue(Attribute $$0) {
/* 32 */     return getAttributeInstance($$0).getBaseValue();
/*    */   }
/*    */   
/*    */   public double getModifierValue(Attribute $$0, UUID $$1) {
/* 36 */     AttributeModifier $$2 = getAttributeInstance($$0).getModifier($$1);
/* 37 */     if ($$2 == null) {
/* 38 */       throw new IllegalArgumentException("Can't find modifier " + $$1 + " on attribute " + BuiltInRegistries.ATTRIBUTE.getKey($$0));
/*    */     }
/* 40 */     return $$2.getAmount();
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public AttributeInstance createInstance(Consumer<AttributeInstance> $$0, Attribute $$1) {
/* 45 */     AttributeInstance $$2 = this.instances.get($$1);
/* 46 */     if ($$2 == null) {
/* 47 */       return null;
/*    */     }
/* 49 */     AttributeInstance $$3 = new AttributeInstance($$1, $$0);
/* 50 */     $$3.replaceFrom($$2);
/* 51 */     return $$3;
/*    */   }
/*    */   
/*    */   public static Builder builder() {
/* 55 */     return new Builder();
/*    */   }
/*    */   
/*    */   public boolean hasAttribute(Attribute $$0) {
/* 59 */     return this.instances.containsKey($$0);
/*    */   }
/*    */   
/*    */   public boolean hasModifier(Attribute $$0, UUID $$1) {
/* 63 */     AttributeInstance $$2 = this.instances.get($$0);
/* 64 */     return ($$2 != null && $$2.getModifier($$1) != null);
/*    */   }
/*    */   
/*    */   public static class Builder {
/* 68 */     private final Map<Attribute, AttributeInstance> builder = Maps.newHashMap();
/*    */     private boolean instanceFrozen;
/*    */     
/*    */     private AttributeInstance create(Attribute $$0) {
/* 72 */       AttributeInstance $$1 = new AttributeInstance($$0, $$1 -> {
/*    */             if (this.instanceFrozen) {
/*    */               throw new UnsupportedOperationException("Tried to change value for default attribute instance: " + BuiltInRegistries.ATTRIBUTE.getKey($$0));
/*    */             }
/*    */           });
/* 77 */       this.builder.put($$0, $$1);
/* 78 */       return $$1;
/*    */     }
/*    */     
/*    */     public Builder add(Attribute $$0) {
/* 82 */       create($$0);
/* 83 */       return this;
/*    */     }
/*    */     
/*    */     public Builder add(Attribute $$0, double $$1) {
/* 87 */       AttributeInstance $$2 = create($$0);
/* 88 */       $$2.setBaseValue($$1);
/* 89 */       return this;
/*    */     }
/*    */     
/*    */     public AttributeSupplier build() {
/* 93 */       this.instanceFrozen = true;
/* 94 */       return new AttributeSupplier(this.builder);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\attributes\AttributeSupplier.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */