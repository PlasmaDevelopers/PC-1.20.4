/*    */ package net.minecraft.world.entity.ai.attributes;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Map;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Builder
/*    */ {
/* 68 */   private final Map<Attribute, AttributeInstance> builder = Maps.newHashMap();
/*    */   private boolean instanceFrozen;
/*    */   
/*    */   private AttributeInstance create(Attribute $$0) {
/* 72 */     AttributeInstance $$1 = new AttributeInstance($$0, $$1 -> {
/*    */           if (this.instanceFrozen) {
/*    */             throw new UnsupportedOperationException("Tried to change value for default attribute instance: " + BuiltInRegistries.ATTRIBUTE.getKey($$0));
/*    */           }
/*    */         });
/* 77 */     this.builder.put($$0, $$1);
/* 78 */     return $$1;
/*    */   }
/*    */   
/*    */   public Builder add(Attribute $$0) {
/* 82 */     create($$0);
/* 83 */     return this;
/*    */   }
/*    */   
/*    */   public Builder add(Attribute $$0, double $$1) {
/* 87 */     AttributeInstance $$2 = create($$0);
/* 88 */     $$2.setBaseValue($$1);
/* 89 */     return this;
/*    */   }
/*    */   
/*    */   public AttributeSupplier build() {
/* 93 */     this.instanceFrozen = true;
/* 94 */     return new AttributeSupplier(this.builder);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\attributes\AttributeSupplier$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */