/*     */ package net.minecraft.world.flag;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.function.Consumer;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class FeatureFlagRegistry
/*     */ {
/*  20 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private final FeatureFlagUniverse universe;
/*     */   private final Map<ResourceLocation, FeatureFlag> names;
/*     */   private final FeatureFlagSet allFlags;
/*     */   
/*     */   FeatureFlagRegistry(FeatureFlagUniverse $$0, FeatureFlagSet $$1, Map<ResourceLocation, FeatureFlag> $$2) {
/*  27 */     this.universe = $$0;
/*  28 */     this.names = $$2;
/*  29 */     this.allFlags = $$1;
/*     */   }
/*     */   
/*     */   public boolean isSubset(FeatureFlagSet $$0) {
/*  33 */     return $$0.isSubsetOf(this.allFlags);
/*     */   }
/*     */   
/*     */   public FeatureFlagSet allFlags() {
/*  37 */     return this.allFlags;
/*     */   }
/*     */   
/*     */   public FeatureFlagSet fromNames(Iterable<ResourceLocation> $$0) {
/*  41 */     return fromNames($$0, $$0 -> LOGGER.warn("Unknown feature flag: {}", $$0));
/*     */   }
/*     */   
/*     */   public FeatureFlagSet subset(FeatureFlag... $$0) {
/*  45 */     return FeatureFlagSet.create(this.universe, Arrays.asList($$0));
/*     */   }
/*     */   
/*     */   public FeatureFlagSet fromNames(Iterable<ResourceLocation> $$0, Consumer<ResourceLocation> $$1) {
/*  49 */     Set<FeatureFlag> $$2 = Sets.newIdentityHashSet();
/*  50 */     for (ResourceLocation $$3 : $$0) {
/*  51 */       FeatureFlag $$4 = this.names.get($$3);
/*  52 */       if ($$4 == null) {
/*  53 */         $$1.accept($$3); continue;
/*     */       } 
/*  55 */       $$2.add($$4);
/*     */     } 
/*     */     
/*  58 */     return FeatureFlagSet.create(this.universe, $$2);
/*     */   }
/*     */   
/*     */   public Set<ResourceLocation> toNames(FeatureFlagSet $$0) {
/*  62 */     Set<ResourceLocation> $$1 = new HashSet<>();
/*     */     
/*  64 */     this.names.forEach(($$2, $$3) -> {
/*     */           if ($$0.contains($$3)) {
/*     */             $$1.add($$2);
/*     */           }
/*     */         });
/*  69 */     return $$1;
/*     */   }
/*     */   
/*     */   public Codec<FeatureFlagSet> codec() {
/*  73 */     return ResourceLocation.CODEC.listOf().comapFlatMap($$0 -> {
/*     */           Set<ResourceLocation> $$1 = new HashSet<>();
/*     */           Objects.requireNonNull($$1);
/*     */           FeatureFlagSet $$2 = fromNames($$0, $$1::add);
/*     */           return !$$1.isEmpty() ? DataResult.error((), $$2) : DataResult.success($$2);
/*     */         }$$0 -> List.copyOf(toNames($$0)));
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Builder
/*     */   {
/*     */     private final FeatureFlagUniverse universe;
/*     */     
/*     */     private int id;
/*     */     
/*  88 */     private final Map<ResourceLocation, FeatureFlag> flags = new LinkedHashMap<>();
/*     */     
/*     */     public Builder(String $$0) {
/*  91 */       this.universe = new FeatureFlagUniverse($$0);
/*     */     }
/*     */     
/*     */     public FeatureFlag createVanilla(String $$0) {
/*  95 */       return create(new ResourceLocation("minecraft", $$0));
/*     */     }
/*     */     
/*     */     public FeatureFlag create(ResourceLocation $$0) {
/*  99 */       if (this.id >= 64)
/*     */       {
/* 101 */         throw new IllegalStateException("Too many feature flags");
/*     */       }
/* 103 */       FeatureFlag $$1 = new FeatureFlag(this.universe, this.id++);
/* 104 */       FeatureFlag $$2 = this.flags.put($$0, $$1);
/* 105 */       if ($$2 != null) {
/* 106 */         throw new IllegalStateException("Duplicate feature flag " + $$0);
/*     */       }
/* 108 */       return $$1;
/*     */     }
/*     */     
/*     */     public FeatureFlagRegistry build() {
/* 112 */       FeatureFlagSet $$0 = FeatureFlagSet.create(this.universe, this.flags.values());
/* 113 */       return new FeatureFlagRegistry(this.universe, $$0, Map.copyOf(this.flags));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\flag\FeatureFlagRegistry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */