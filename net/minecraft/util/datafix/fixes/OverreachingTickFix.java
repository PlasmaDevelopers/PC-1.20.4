/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.OpticFinder;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.Type;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ 
/*    */ public class OverreachingTickFix
/*    */   extends DataFix {
/*    */   public OverreachingTickFix(Schema $$0) {
/* 17 */     super($$0, false);
/*    */   }
/*    */ 
/*    */   
/*    */   protected TypeRewriteRule makeRule() {
/* 22 */     Type<?> $$0 = getInputSchema().getType(References.CHUNK);
/* 23 */     OpticFinder<?> $$1 = $$0.findField("block_ticks");
/*    */     
/* 25 */     return fixTypeEverywhereTyped("Handle ticks saved in the wrong chunk", $$0, $$1 -> {
/*    */           Optional<? extends Typed<?>> $$2 = $$1.getOptionalTyped($$0);
/*    */           Optional<? extends Dynamic<?>> $$3 = $$2.isPresent() ? ((Typed)$$2.get()).write().result() : Optional.<Dynamic<?>>empty();
/*    */           return $$1.update(DSL.remainderFinder(), ());
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static Dynamic<?> extractOverreachingTicks(Dynamic<?> $$0, int $$1, int $$2, Optional<? extends Dynamic<?>> $$3, String $$4) {
/* 41 */     if ($$3.isPresent()) {
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 51 */       List<? extends Dynamic<?>> $$5 = ((Dynamic)$$3.get()).asStream().filter($$2 -> { int $$3 = $$2.get("x").asInt(0); int $$4 = $$2.get("z").asInt(0); int $$5 = Math.abs($$0 - ($$3 >> 4)); int $$6 = Math.abs($$1 - ($$4 >> 4)); return (($$5 != 0 || $$6 != 0) && $$5 <= 1 && $$6 <= 1); }).toList();
/* 52 */       if (!$$5.isEmpty()) {
/* 53 */         $$0 = $$0.set("UpgradeData", $$0.get("UpgradeData").orElseEmptyMap().set($$4, $$0.createList($$5.stream())));
/*    */       }
/*    */     } 
/* 56 */     return $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\OverreachingTickFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */