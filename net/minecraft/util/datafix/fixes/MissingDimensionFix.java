/*     */ package net.minecraft.util.datafix.fixes;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.mojang.datafixers.DSL;
/*     */ import com.mojang.datafixers.DataFix;
/*     */ import com.mojang.datafixers.DataFixUtils;
/*     */ import com.mojang.datafixers.FieldFinder;
/*     */ import com.mojang.datafixers.OpticFinder;
/*     */ import com.mojang.datafixers.TypeRewriteRule;
/*     */ import com.mojang.datafixers.Typed;
/*     */ import com.mojang.datafixers.schemas.Schema;
/*     */ import com.mojang.datafixers.types.Type;
/*     */ import com.mojang.datafixers.types.templates.CompoundList;
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.datafixers.util.Unit;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.util.datafix.schemas.NamespacedSchema;
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
/*     */ public class MissingDimensionFix
/*     */   extends DataFix
/*     */ {
/*     */   public MissingDimensionFix(Schema $$0, boolean $$1) {
/*  35 */     super($$0, $$1);
/*     */   }
/*     */   
/*     */   protected static <A> Type<Pair<A, Dynamic<?>>> fields(String $$0, Type<A> $$1) {
/*  39 */     return DSL.and((Type)DSL.field($$0, $$1), DSL.remainderType());
/*     */   }
/*     */   
/*     */   protected static <A> Type<Pair<Either<A, Unit>, Dynamic<?>>> optionalFields(String $$0, Type<A> $$1) {
/*  43 */     return DSL.and(DSL.optional((Type)DSL.field($$0, $$1)), DSL.remainderType());
/*     */   }
/*     */   
/*     */   protected static <A1, A2> Type<Pair<Either<A1, Unit>, Pair<Either<A2, Unit>, Dynamic<?>>>> optionalFields(String $$0, Type<A1> $$1, String $$2, Type<A2> $$3) {
/*  47 */     return DSL.and(
/*  48 */         DSL.optional((Type)DSL.field($$0, $$1)), 
/*  49 */         DSL.optional((Type)DSL.field($$2, $$3)), 
/*  50 */         DSL.remainderType());
/*     */   }
/*     */ 
/*     */   
/*     */   protected TypeRewriteRule makeRule() {
/*  55 */     Schema $$0 = getInputSchema();
/*  56 */     Type<?> $$1 = DSL.taggedChoiceType("type", DSL.string(), (Map)ImmutableMap.of("minecraft:debug", 
/*  57 */           DSL.remainderType(), "minecraft:flat", 
/*  58 */           flatType($$0), "minecraft:noise", 
/*  59 */           optionalFields("biome_source", 
/*  60 */             DSL.taggedChoiceType("type", DSL.string(), (Map)ImmutableMap.of("minecraft:fixed", 
/*  61 */                 fields("biome", $$0.getType(References.BIOME)), "minecraft:multi_noise", 
/*  62 */                 DSL.list(fields("biome", $$0.getType(References.BIOME))), "minecraft:checkerboard", 
/*  63 */                 fields("biomes", (Type<?>)DSL.list($$0.getType(References.BIOME))), "minecraft:vanilla_layered", 
/*  64 */                 DSL.remainderType(), "minecraft:the_end", 
/*  65 */                 DSL.remainderType())), "settings", 
/*     */             
/*  67 */             DSL.or(DSL.string(), optionalFields("default_block", $$0
/*  68 */                 .getType(References.BLOCK_NAME), "default_fluid", $$0
/*  69 */                 .getType(References.BLOCK_NAME))))));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  74 */     CompoundList.CompoundListType<String, ?> $$2 = DSL.compoundList(NamespacedSchema.namespacedString(), fields("generator", $$1));
/*  75 */     Type<?> $$3 = DSL.and((Type)$$2, DSL.remainderType());
/*     */     
/*  77 */     Type<?> $$4 = $$0.getType(References.WORLD_GEN_SETTINGS);
/*     */     
/*  79 */     FieldFinder<?> $$5 = new FieldFinder("dimensions", $$3);
/*  80 */     if (!$$4.findFieldType("dimensions").equals($$3)) {
/*  81 */       throw new IllegalStateException();
/*     */     }
/*  83 */     OpticFinder<? extends List<? extends Pair<String, ?>>> $$6 = $$2.finder();
/*  84 */     return fixTypeEverywhereTyped("MissingDimensionFix", $$4, $$3 -> $$3.updateTyped((OpticFinder)$$0, ()));
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
/*     */ 
/*     */   
/*     */   protected static Type<? extends Pair<? extends Either<? extends Pair<? extends Either<?, Unit>, ? extends Pair<? extends Either<? extends List<? extends Pair<? extends Either<?, Unit>, Dynamic<?>>>, Unit>, Dynamic<?>>>, Unit>, Dynamic<?>>> flatType(Schema $$0) {
/*  99 */     return (Type)optionalFields("settings", optionalFields("biome", $$0
/* 100 */           .getType(References.BIOME), "layers", 
/* 101 */           (Type<?>)DSL.list(optionalFields("block", $$0.getType(References.BLOCK_NAME)))));
/*     */   }
/*     */ 
/*     */   
/*     */   private <T> Dynamic<T> recreateSettings(Dynamic<T> $$0) {
/* 106 */     long $$1 = $$0.get("seed").asLong(0L);
/* 107 */     return new Dynamic($$0.getOps(), WorldGenSettingsFix.vanillaLevels($$0, $$1, WorldGenSettingsFix.defaultOverworld($$0, $$1), false));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\MissingDimensionFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */