/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import java.util.Optional;
/*    */ 
/*    */ public class CauldronRenameFix extends DataFix {
/*    */   public CauldronRenameFix(Schema $$0, boolean $$1) {
/* 13 */     super($$0, $$1);
/*    */   }
/*    */   
/*    */   private static Dynamic<?> fix(Dynamic<?> $$0) {
/* 17 */     Optional<String> $$1 = $$0.get("Name").asString().result();
/* 18 */     if ($$1.equals(Optional.of("minecraft:cauldron"))) {
/* 19 */       Dynamic<?> $$2 = $$0.get("Properties").orElseEmptyMap();
/* 20 */       if ($$2.get("level").asString("0").equals("0")) {
/* 21 */         return $$0.remove("Properties");
/*    */       }
/* 23 */       return $$0.set("Name", $$0.createString("minecraft:water_cauldron"));
/*    */     } 
/* 25 */     return $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected TypeRewriteRule makeRule() {
/* 30 */     return fixTypeEverywhereTyped("cauldron_rename_fix", getInputSchema().getType(References.BLOCK_STATE), $$0 -> $$0.update(DSL.remainderFinder(), CauldronRenameFix::fix));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\CauldronRenameFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */