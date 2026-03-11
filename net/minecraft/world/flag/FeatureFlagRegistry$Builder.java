/*     */ package net.minecraft.world.flag;
/*     */ 
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.resources.ResourceLocation;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Builder
/*     */ {
/*     */   private final FeatureFlagUniverse universe;
/*     */   private int id;
/*  88 */   private final Map<ResourceLocation, FeatureFlag> flags = new LinkedHashMap<>();
/*     */   
/*     */   public Builder(String $$0) {
/*  91 */     this.universe = new FeatureFlagUniverse($$0);
/*     */   }
/*     */   
/*     */   public FeatureFlag createVanilla(String $$0) {
/*  95 */     return create(new ResourceLocation("minecraft", $$0));
/*     */   }
/*     */   
/*     */   public FeatureFlag create(ResourceLocation $$0) {
/*  99 */     if (this.id >= 64)
/*     */     {
/* 101 */       throw new IllegalStateException("Too many feature flags");
/*     */     }
/* 103 */     FeatureFlag $$1 = new FeatureFlag(this.universe, this.id++);
/* 104 */     FeatureFlag $$2 = this.flags.put($$0, $$1);
/* 105 */     if ($$2 != null) {
/* 106 */       throw new IllegalStateException("Duplicate feature flag " + $$0);
/*     */     }
/* 108 */     return $$1;
/*     */   }
/*     */   
/*     */   public FeatureFlagRegistry build() {
/* 112 */     FeatureFlagSet $$0 = FeatureFlagSet.create(this.universe, this.flags.values());
/* 113 */     return new FeatureFlagRegistry(this.universe, $$0, Map.copyOf(this.flags));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\flag\FeatureFlagRegistry$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */