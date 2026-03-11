/*     */ package net.minecraft.core;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LayeredRegistryAccess<T>
/*     */ {
/*     */   private final List<T> keys;
/*     */   private final List<RegistryAccess.Frozen> values;
/*     */   private final RegistryAccess.Frozen composite;
/*     */   
/*     */   public LayeredRegistryAccess(List<T> $$0) {
/*  23 */     this($$0, 
/*     */         
/*  25 */         (List<RegistryAccess.Frozen>)Util.make(() -> {
/*     */             RegistryAccess.Frozen[] $$1 = new RegistryAccess.Frozen[$$0.size()];
/*     */             Arrays.fill((Object[])$$1, RegistryAccess.EMPTY);
/*     */             return Arrays.asList($$1);
/*     */           }));
/*     */   }
/*     */ 
/*     */   
/*     */   private LayeredRegistryAccess(List<T> $$0, List<RegistryAccess.Frozen> $$1) {
/*  34 */     this.keys = List.copyOf($$0);
/*  35 */     this.values = List.copyOf($$1);
/*  36 */     this.composite = (new RegistryAccess.ImmutableRegistryAccess(collectRegistries($$1.stream()))).freeze();
/*     */   }
/*     */   
/*     */   private int getLayerIndexOrThrow(T $$0) {
/*  40 */     int $$1 = this.keys.indexOf($$0);
/*  41 */     if ($$1 == -1) {
/*  42 */       throw new IllegalStateException("Can't find " + $$0 + " inside " + this.keys);
/*     */     }
/*  44 */     return $$1;
/*     */   }
/*     */   
/*     */   public RegistryAccess.Frozen getLayer(T $$0) {
/*  48 */     int $$1 = getLayerIndexOrThrow($$0);
/*  49 */     return this.values.get($$1);
/*     */   }
/*     */   
/*     */   public RegistryAccess.Frozen getAccessForLoading(T $$0) {
/*  53 */     int $$1 = getLayerIndexOrThrow($$0);
/*  54 */     return getCompositeAccessForLayers(0, $$1);
/*     */   }
/*     */   
/*     */   public RegistryAccess.Frozen getAccessFrom(T $$0) {
/*  58 */     int $$1 = getLayerIndexOrThrow($$0);
/*  59 */     return getCompositeAccessForLayers($$1, this.values.size());
/*     */   }
/*     */   
/*     */   private RegistryAccess.Frozen getCompositeAccessForLayers(int $$0, int $$1) {
/*  63 */     return (new RegistryAccess.ImmutableRegistryAccess(collectRegistries(this.values.subList($$0, $$1).stream()))).freeze();
/*     */   }
/*     */   
/*     */   public LayeredRegistryAccess<T> replaceFrom(T $$0, RegistryAccess.Frozen... $$1) {
/*  67 */     return replaceFrom($$0, Arrays.asList($$1));
/*     */   }
/*     */   
/*     */   public LayeredRegistryAccess<T> replaceFrom(T $$0, List<RegistryAccess.Frozen> $$1) {
/*  71 */     int $$2 = getLayerIndexOrThrow($$0);
/*     */     
/*  73 */     if ($$1.size() > this.values.size() - $$2) {
/*  74 */       throw new IllegalStateException("Too many values to replace");
/*     */     }
/*     */     
/*  77 */     List<RegistryAccess.Frozen> $$3 = new ArrayList<>();
/*     */     
/*  79 */     for (int $$4 = 0; $$4 < $$2; $$4++) {
/*  80 */       $$3.add(this.values.get($$4));
/*     */     }
/*     */     
/*  83 */     $$3.addAll($$1);
/*     */     
/*  85 */     while ($$3.size() < this.values.size()) {
/*  86 */       $$3.add(RegistryAccess.EMPTY);
/*     */     }
/*  88 */     return new LayeredRegistryAccess(this.keys, $$3);
/*     */   }
/*     */   
/*     */   public RegistryAccess.Frozen compositeAccess() {
/*  92 */     return this.composite;
/*     */   }
/*     */   
/*     */   private static Map<ResourceKey<? extends Registry<?>>, Registry<?>> collectRegistries(Stream<? extends RegistryAccess> $$0) {
/*  96 */     Map<ResourceKey<? extends Registry<?>>, Registry<?>> $$1 = new HashMap<>();
/*     */     
/*  98 */     $$0.forEach($$1 -> $$1.registries().forEach(()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 106 */     return $$1;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\LayeredRegistryAccess.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */