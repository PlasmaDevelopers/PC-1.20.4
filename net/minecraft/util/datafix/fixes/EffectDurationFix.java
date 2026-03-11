/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.OpticFinder;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.Type;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import java.util.Objects;
/*    */ import java.util.Optional;
/*    */ import java.util.Set;
/*    */ import net.minecraft.util.datafix.schemas.NamespacedSchema;
/*    */ 
/*    */ 
/*    */ public class EffectDurationFix
/*    */   extends DataFix
/*    */ {
/* 21 */   private static final Set<String> ITEM_TYPES = Set.of("minecraft:potion", "minecraft:splash_potion", "minecraft:lingering_potion", "minecraft:tipped_arrow");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EffectDurationFix(Schema $$0) {
/* 29 */     super($$0, false);
/*    */   }
/*    */ 
/*    */   
/*    */   protected TypeRewriteRule makeRule() {
/* 34 */     Schema $$0 = getInputSchema();
/* 35 */     Type<?> $$1 = getInputSchema().getType(References.ITEM_STACK);
/* 36 */     OpticFinder<Pair<String, String>> $$2 = DSL.fieldFinder("id", DSL.named(References.ITEM_NAME.typeName(), NamespacedSchema.namespacedString()));
/* 37 */     OpticFinder<?> $$3 = $$1.findField("tag");
/* 38 */     return TypeRewriteRule.seq(
/* 39 */         fixTypeEverywhereTyped("EffectDurationEntity", $$0.getType(References.ENTITY), $$0 -> $$0.update(DSL.remainderFinder(), this::updateEntity)), new TypeRewriteRule[] {
/*    */ 
/*    */           
/* 42 */           fixTypeEverywhereTyped("EffectDurationPlayer", $$0.getType(References.PLAYER), $$0 -> $$0.update(DSL.remainderFinder(), this::updateEntity)), 
/*    */ 
/*    */           
/* 45 */           fixTypeEverywhereTyped("EffectDurationItem", $$1, $$2 -> {
/*    */               Optional<Pair<String, String>> $$3 = $$2.getOptional($$0);
/*    */               Objects.requireNonNull(ITEM_TYPES);
/*    */               if ($$3.filter(ITEM_TYPES::contains).isPresent()) {
/*    */                 Optional<? extends Typed<?>> $$4 = $$2.getOptionalTyped($$1);
/*    */                 if ($$4.isPresent()) {
/*    */                   Dynamic<?> $$5 = (Dynamic)((Typed)$$4.get()).get(DSL.remainderFinder());
/*    */                   Typed<?> $$6 = ((Typed)$$4.get()).set(DSL.remainderFinder(), $$5.update("CustomPotionEffects", this::fix));
/*    */                   return $$2.set($$1, $$6);
/*    */                 } 
/*    */               } 
/*    */               return $$2;
/*    */             }) });
/*    */   }
/*    */   
/*    */   private Dynamic<?> fixEffect(Dynamic<?> $$0) {
/* 61 */     return $$0.update("FactorCalculationData", $$1 -> {
/*    */           int $$2 = $$1.get("effect_changed_timestamp").asInt(-1);
/*    */           $$1 = $$1.remove("effect_changed_timestamp");
/*    */           int $$3 = $$0.get("Duration").asInt(-1);
/*    */           int $$4 = $$2 - $$3;
/*    */           return $$1.set("ticks_active", $$1.createInt($$4));
/*    */         });
/*    */   }
/*    */   
/*    */   private Dynamic<?> fix(Dynamic<?> $$0) {
/* 71 */     return $$0.createList($$0.asStream().map(this::fixEffect));
/*    */   }
/*    */ 
/*    */   
/*    */   private Dynamic<?> updateEntity(Dynamic<?> $$0) {
/* 76 */     $$0 = $$0.update("Effects", this::fix);
/* 77 */     $$0 = $$0.update("ActiveEffects", this::fix);
/* 78 */     $$0 = $$0.update("CustomPotionEffects", this::fix);
/* 79 */     return $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\EffectDurationFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */