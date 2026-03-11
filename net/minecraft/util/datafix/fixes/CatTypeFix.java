/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ 
/*    */ public class CatTypeFix extends NamedEntityFix {
/*    */   public CatTypeFix(Schema $$0, boolean $$1) {
/* 10 */     super($$0, $$1, "CatTypeFix", References.ENTITY, "minecraft:cat");
/*    */   }
/*    */   
/*    */   public Dynamic<?> fixTag(Dynamic<?> $$0) {
/* 14 */     if ($$0.get("CatType").asInt(0) == 9) {
/* 15 */       return $$0.set("CatType", $$0.createInt(10));
/*    */     }
/* 17 */     return $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Typed<?> fix(Typed<?> $$0) {
/* 22 */     return $$0.update(DSL.remainderFinder(), this::fixTag);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\CatTypeFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */