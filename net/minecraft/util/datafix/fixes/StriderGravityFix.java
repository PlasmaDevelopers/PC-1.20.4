/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ 
/*    */ public class StriderGravityFix extends NamedEntityFix {
/*    */   public StriderGravityFix(Schema $$0, boolean $$1) {
/* 10 */     super($$0, $$1, "StriderGravityFix", References.ENTITY, "minecraft:strider");
/*    */   }
/*    */   
/*    */   public Dynamic<?> fixTag(Dynamic<?> $$0) {
/* 14 */     if ($$0.get("NoGravity").asBoolean(false)) {
/* 15 */       return $$0.set("NoGravity", $$0.createBoolean(false));
/*    */     }
/* 17 */     return $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Typed<?> fix(Typed<?> $$0) {
/* 22 */     return $$0.update(DSL.remainderFinder(), this::fixTag);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\StriderGravityFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */