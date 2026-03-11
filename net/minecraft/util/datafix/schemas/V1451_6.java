/*     */ package net.minecraft.util.datafix.schemas;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.datafixers.DSL;
/*     */ import com.mojang.datafixers.DataFixUtils;
/*     */ import com.mojang.datafixers.schemas.Schema;
/*     */ import com.mojang.datafixers.types.templates.Hook;
/*     */ import com.mojang.datafixers.types.templates.TypeTemplate;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.datafix.fixes.References;
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
/*     */ public class V1451_6
/*     */   extends NamespacedSchema
/*     */ {
/*     */   public static final String SPECIAL_OBJECTIVE_MARKER = "_special";
/*     */   
/*     */   public V1451_6(int $$0, Schema $$1) {
/*  35 */     super($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerTypes(Schema $$0, Map<String, Supplier<TypeTemplate>> $$1, Map<String, Supplier<TypeTemplate>> $$2) {
/*  40 */     super.registerTypes($$0, $$1, $$2);
/*     */     
/*  42 */     Supplier<TypeTemplate> $$3 = () -> DSL.compoundList(References.ITEM_NAME.in($$0), DSL.constType(DSL.intType()));
/*     */     
/*  44 */     $$0.registerType(false, References.STATS, () -> DSL.optionalFields("stats", DSL.optionalFields("minecraft:mined", DSL.compoundList(References.BLOCK_NAME.in($$0), DSL.constType(DSL.intType())), "minecraft:crafted", $$1.get(), "minecraft:used", $$1.get(), "minecraft:broken", $$1.get(), "minecraft:picked_up", $$1.get(), DSL.optionalFields("minecraft:dropped", $$1.get(), "minecraft:killed", DSL.compoundList(References.ENTITY_NAME.in($$0), DSL.constType(DSL.intType())), "minecraft:killed_by", DSL.compoundList(References.ENTITY_NAME.in($$0), DSL.constType(DSL.intType())), "minecraft:custom", DSL.compoundList(DSL.constType(namespacedString()), DSL.constType(DSL.intType()))))));
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
/*  60 */     Map<String, Supplier<TypeTemplate>> $$4 = createCriterionTypes($$0);
/*  61 */     $$0.registerType(false, References.OBJECTIVE, () -> DSL.hook(DSL.optionalFields("CriteriaType", (TypeTemplate)DSL.taggedChoiceLazy("type", DSL.string(), $$0)), UNPACK_OBJECTIVE_ID, REPACK_OBJECTIVE_ID));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static Map<String, Supplier<TypeTemplate>> createCriterionTypes(Schema $$0) {
/*  70 */     Supplier<TypeTemplate> $$1 = () -> DSL.optionalFields("id", References.ITEM_NAME.in($$0));
/*  71 */     Supplier<TypeTemplate> $$2 = () -> DSL.optionalFields("id", References.BLOCK_NAME.in($$0));
/*  72 */     Supplier<TypeTemplate> $$3 = () -> DSL.optionalFields("id", References.ENTITY_NAME.in($$0));
/*     */     
/*  74 */     Map<String, Supplier<TypeTemplate>> $$4 = Maps.newHashMap();
/*  75 */     $$4.put("minecraft:mined", $$2);
/*     */     
/*  77 */     $$4.put("minecraft:crafted", $$1);
/*  78 */     $$4.put("minecraft:used", $$1);
/*  79 */     $$4.put("minecraft:broken", $$1);
/*  80 */     $$4.put("minecraft:picked_up", $$1);
/*  81 */     $$4.put("minecraft:dropped", $$1);
/*     */     
/*  83 */     $$4.put("minecraft:killed", $$3);
/*  84 */     $$4.put("minecraft:killed_by", $$3);
/*     */     
/*  86 */     $$4.put("minecraft:custom", () -> DSL.optionalFields("id", DSL.constType(namespacedString())));
/*     */     
/*  88 */     $$4.put("_special", () -> DSL.optionalFields("id", DSL.constType(DSL.string())));
/*  89 */     return $$4;
/*     */   }
/*     */   
/*  92 */   protected static final Hook.HookFunction UNPACK_OBJECTIVE_ID = new Hook.HookFunction()
/*     */     {
/*     */       public <T> T apply(DynamicOps<T> $$0, T $$1) {
/*  95 */         Dynamic<T> $$2 = new Dynamic($$0, $$1);
/*     */         
/*  97 */         return (T)((Dynamic)DataFixUtils.orElse($$2
/*  98 */             .get("CriteriaName").asString().get().left()
/*  99 */             .map($$0 -> {
/*     */                 int $$1 = $$0.indexOf(':');
/*     */                 if ($$1 < 0) {
/*     */                   return Pair.of("_special", $$0);
/*     */                 }
/*     */                 try {
/*     */                   ResourceLocation $$2 = ResourceLocation.of($$0.substring(0, $$1), '.');
/*     */                   ResourceLocation $$3 = ResourceLocation.of($$0.substring($$1 + 1), '.');
/*     */                   return Pair.of($$2.toString(), $$3.toString());
/* 108 */                 } catch (Exception $$4) {
/*     */                   
/*     */                   return Pair.of("_special", $$0);
/*     */                 } 
/* 112 */               }).map($$1 -> $$0.set("CriteriaType", $$0.createMap((Map)ImmutableMap.of($$0.createString("type"), $$0.createString((String)$$1.getFirst()), $$0.createString("id"), $$0.createString((String)$$1.getSecond()))))), $$2))
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 121 */           .getValue();
/*     */       }
/*     */     };
/*     */   
/*     */   public static String packNamespacedWithDot(String $$0) {
/* 126 */     ResourceLocation $$1 = ResourceLocation.tryParse($$0);
/* 127 */     return ($$1 != null) ? ($$1.getNamespace() + "." + $$1.getNamespace()) : $$0;
/*     */   }
/*     */   
/* 130 */   protected static final Hook.HookFunction REPACK_OBJECTIVE_ID = new Hook.HookFunction()
/*     */     {
/*     */       public <T> T apply(DynamicOps<T> $$0, T $$1) {
/* 133 */         Dynamic<T> $$2 = new Dynamic($$0, $$1);
/*     */         
/* 135 */         Optional<Dynamic<T>> $$3 = $$2.get("CriteriaType").get().get().left().flatMap($$1 -> {
/*     */               Optional<String> $$2 = $$1.get("type").asString().get().left();
/*     */               
/*     */               Optional<String> $$3 = $$1.get("id").asString().get().left();
/*     */               
/*     */               if ($$2.isPresent() && $$3.isPresent()) {
/*     */                 String $$4 = $$2.get();
/*     */                 
/*     */                 return $$4.equals("_special") ? Optional.of($$0.createString($$3.get())) : Optional.of($$1.createString(V1451_6.packNamespacedWithDot($$4) + ":" + V1451_6.packNamespacedWithDot($$4)));
/*     */               } 
/*     */               
/*     */               return Optional.empty();
/*     */             });
/*     */         
/* 149 */         return (T)((Dynamic)DataFixUtils.orElse($$3.map($$1 -> $$0.set("CriteriaName", $$1).remove("CriteriaType")), $$2)).getValue();
/*     */       }
/*     */     };
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\schemas\V1451_6.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */