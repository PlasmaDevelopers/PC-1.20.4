/*     */ package net.minecraft.util.datafix.fixes;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.mojang.datafixers.DataFix;
/*     */ import com.mojang.datafixers.TypeRewriteRule;
/*     */ import com.mojang.datafixers.schemas.Schema;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import com.mojang.serialization.OptionalDynamic;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.function.Function;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import java.util.stream.Stream;
/*     */ 
/*     */ public class SavedDataFeaturePoolElementFix extends DataFix {
/*  21 */   private static final Pattern INDEX_PATTERN = Pattern.compile("\\[(\\d+)\\]");
/*  22 */   private static final Set<String> PIECE_TYPE = Sets.newHashSet((Object[])new String[] { "minecraft:jigsaw", "minecraft:nvi", "minecraft:pcp", "minecraft:bastionremnant", "minecraft:runtime" });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  29 */   private static final Set<String> FEATURES = Sets.newHashSet((Object[])new String[] { "minecraft:tree", "minecraft:flower", "minecraft:block_pile", "minecraft:random_patch" });
/*     */   
/*     */   public SavedDataFeaturePoolElementFix(Schema $$0) {
/*  32 */     super($$0, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public TypeRewriteRule makeRule() {
/*  37 */     return writeFixAndRead("SavedDataFeaturePoolElementFix", getInputSchema().getType(References.STRUCTURE_FEATURE), getOutputSchema().getType(References.STRUCTURE_FEATURE), SavedDataFeaturePoolElementFix::fixTag);
/*     */   }
/*     */   
/*     */   private static <T> Dynamic<T> fixTag(Dynamic<T> $$0) {
/*  41 */     return $$0.update("Children", SavedDataFeaturePoolElementFix::updateChildren);
/*     */   }
/*     */   
/*     */   private static <T> Dynamic<T> updateChildren(Dynamic<T> $$0) {
/*  45 */     Objects.requireNonNull($$0); return $$0.asStreamOpt().map(SavedDataFeaturePoolElementFix::updateChildren).map($$0::createList).result().orElse($$0);
/*     */   }
/*     */   
/*     */   private static Stream<? extends Dynamic<?>> updateChildren(Stream<? extends Dynamic<?>> $$0) {
/*  49 */     return $$0.map($$0 -> {
/*     */           String $$1 = $$0.get("id").asString("");
/*     */           if (!PIECE_TYPE.contains($$1)) {
/*     */             return $$0;
/*     */           }
/*     */           OptionalDynamic<?> $$2 = $$0.get("pool_element");
/*     */           return !$$2.get("element_type").asString("").equals("minecraft:feature_pool_element") ? $$0 : $$0.update("pool_element", ());
/*     */         });
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
/*     */   private static <T> OptionalDynamic<T> get(Dynamic<T> $$0, String... $$1) {
/*  68 */     if ($$1.length == 0) {
/*  69 */       throw new IllegalArgumentException("Missing path");
/*     */     }
/*     */     
/*  72 */     OptionalDynamic<T> $$2 = $$0.get($$1[0]);
/*  73 */     for (int $$3 = 1; $$3 < $$1.length; $$3++) {
/*  74 */       String $$4 = $$1[$$3];
/*     */       
/*  76 */       Matcher $$5 = INDEX_PATTERN.matcher($$4);
/*  77 */       if ($$5.matches()) {
/*  78 */         int $$6 = Integer.parseInt($$5.group(1));
/*  79 */         List<? extends Dynamic<T>> $$7 = $$2.asList(Function.identity());
/*  80 */         if ($$6 >= 0 && $$6 < $$7.size()) {
/*  81 */           $$2 = new OptionalDynamic($$0.getOps(), DataResult.success($$7.get($$6)));
/*     */         } else {
/*  83 */           $$2 = new OptionalDynamic($$0.getOps(), DataResult.error(() -> "Missing id:" + $$0));
/*     */         } 
/*     */       } else {
/*  86 */         $$2 = $$2.get($$4);
/*     */       } 
/*     */     } 
/*     */     
/*  90 */     return $$2;
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   protected static Dynamic<?> fixFeature(Dynamic<?> $$0) {
/*  95 */     Optional<String> $$1 = getReplacement(
/*  96 */         get((Dynamic)$$0, new String[] { "type" }).asString(""), 
/*  97 */         get((Dynamic)$$0, new String[] { "name" }).asString(""), 
/*     */         
/*  99 */         get((Dynamic)$$0, new String[] { "config", "state_provider", "type" }).asString(""), 
/* 100 */         get((Dynamic)$$0, new String[] { "config", "state_provider", "state", "Name" }).asString(""), 
/* 101 */         get((Dynamic)$$0, new String[] { "config", "state_provider", "entries", "[0]", "data", "Name" }).asString(""), 
/*     */         
/* 103 */         get((Dynamic)$$0, new String[] { "config", "foliage_placer", "type" }).asString(""), 
/* 104 */         get((Dynamic)$$0, new String[] { "config", "leaves_provider", "state", "Name" }).asString(""));
/*     */ 
/*     */     
/* 107 */     if ($$1.isPresent()) {
/* 108 */       return $$0.createString($$1.get());
/*     */     }
/* 110 */     return $$0;
/*     */   }
/*     */   
/*     */   private static Optional<String> getReplacement(String $$0, String $$1, String $$2, String $$3, String $$4, String $$5, String $$6) {
/*     */     String $$9;
/* 115 */     if (!$$0.isEmpty()) {
/* 116 */       String $$7 = $$0;
/* 117 */     } else if (!$$1.isEmpty()) {
/* 118 */       if ("minecraft:normal_tree".equals($$1)) {
/* 119 */         String $$8 = "minecraft:tree";
/*     */       } else {
/* 121 */         $$9 = $$1;
/*     */       } 
/*     */     } else {
/* 124 */       return Optional.empty();
/*     */     } 
/*     */     
/* 127 */     if (FEATURES.contains($$9)) {
/* 128 */       if ("minecraft:random_patch".equals($$9)) {
/* 129 */         if ("minecraft:simple_state_provider".equals($$2)) {
/* 130 */           if ("minecraft:sweet_berry_bush".equals($$3))
/* 131 */             return Optional.of("minecraft:patch_berry_bush"); 
/* 132 */           if ("minecraft:cactus".equals($$3)) {
/* 133 */             return Optional.of("minecraft:patch_cactus");
/*     */           }
/* 135 */         } else if ("minecraft:weighted_state_provider".equals($$2) && (
/* 136 */           "minecraft:grass".equals($$4) || "minecraft:fern".equals($$4))) {
/* 137 */           return Optional.of("minecraft:patch_taiga_grass");
/*     */         }
/*     */       
/* 140 */       } else if ("minecraft:block_pile".equals($$9)) {
/* 141 */         if ("minecraft:simple_state_provider".equals($$2) || "minecraft:rotated_block_provider".equals($$2)) {
/* 142 */           if ("minecraft:hay_block".equals($$3))
/* 143 */             return Optional.of("minecraft:pile_hay"); 
/* 144 */           if ("minecraft:melon".equals($$3))
/* 145 */             return Optional.of("minecraft:pile_melon"); 
/* 146 */           if ("minecraft:snow".equals($$3)) {
/* 147 */             return Optional.of("minecraft:pile_snow");
/*     */           }
/* 149 */         } else if ("minecraft:weighted_state_provider".equals($$2)) {
/* 150 */           if ("minecraft:packed_ice".equals($$4) || "minecraft:blue_ice".equals($$4))
/* 151 */             return Optional.of("minecraft:pile_ice"); 
/* 152 */           if ("minecraft:jack_o_lantern".equals($$4) || "minecraft:pumpkin".equals($$4))
/* 153 */             return Optional.of("minecraft:pile_pumpkin"); 
/*     */         } 
/*     */       } else {
/* 156 */         if ("minecraft:flower".equals($$9))
/* 157 */           return Optional.of("minecraft:flower_plain"); 
/* 158 */         if ("minecraft:tree".equals($$9)) {
/* 159 */           if ("minecraft:acacia_foliage_placer".equals($$5))
/* 160 */             return Optional.of("minecraft:acacia"); 
/* 161 */           if ("minecraft:blob_foliage_placer".equals($$5) && "minecraft:oak_leaves".equals($$6))
/* 162 */             return Optional.of("minecraft:oak"); 
/* 163 */           if ("minecraft:pine_foliage_placer".equals($$5))
/* 164 */             return Optional.of("minecraft:pine"); 
/* 165 */           if ("minecraft:spruce_foliage_placer".equals($$5)) {
/* 166 */             return Optional.of("minecraft:spruce");
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/* 171 */     return Optional.empty();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\SavedDataFeaturePoolElementFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */