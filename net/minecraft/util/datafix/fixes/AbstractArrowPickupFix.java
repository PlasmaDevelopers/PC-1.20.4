/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.Type;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import java.util.function.Function;
/*    */ 
/*    */ 
/*    */ public class AbstractArrowPickupFix
/*    */   extends DataFix
/*    */ {
/*    */   public AbstractArrowPickupFix(Schema $$0) {
/* 17 */     super($$0, false);
/*    */   }
/*    */ 
/*    */   
/*    */   protected TypeRewriteRule makeRule() {
/* 22 */     Schema $$0 = getInputSchema();
/* 23 */     return fixTypeEverywhereTyped("AbstractArrowPickupFix", $$0.getType(References.ENTITY), this::updateProjectiles);
/*    */   }
/*    */   
/*    */   private Typed<?> updateProjectiles(Typed<?> $$0) {
/* 27 */     $$0 = updateEntity($$0, "minecraft:arrow", AbstractArrowPickupFix::updatePickup);
/* 28 */     $$0 = updateEntity($$0, "minecraft:spectral_arrow", AbstractArrowPickupFix::updatePickup);
/* 29 */     $$0 = updateEntity($$0, "minecraft:trident", AbstractArrowPickupFix::updatePickup);
/* 30 */     return $$0;
/*    */   }
/*    */   
/*    */   private static Dynamic<?> updatePickup(Dynamic<?> $$0) {
/* 34 */     if ($$0.get("pickup").result().isPresent()) {
/* 35 */       return $$0;
/*    */     }
/*    */     
/* 38 */     boolean $$1 = $$0.get("player").asBoolean(true);
/* 39 */     return $$0.set("pickup", $$0.createByte((byte)($$1 ? 1 : 0))).remove("player");
/*    */   }
/*    */   
/*    */   private Typed<?> updateEntity(Typed<?> $$0, String $$1, Function<Dynamic<?>, Dynamic<?>> $$2) {
/* 43 */     Type<?> $$3 = getInputSchema().getChoiceType(References.ENTITY, $$1);
/* 44 */     Type<?> $$4 = getOutputSchema().getChoiceType(References.ENTITY, $$1);
/* 45 */     return $$0.updateTyped(DSL.namedChoice($$1, $$3), $$4, $$1 -> $$1.update(DSL.remainderFinder(), $$0));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\AbstractArrowPickupFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */