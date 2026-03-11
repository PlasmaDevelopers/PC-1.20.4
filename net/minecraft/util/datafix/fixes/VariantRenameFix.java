/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFixUtils;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class VariantRenameFix extends NamedEntityFix {
/*    */   private final Map<String, String> renames;
/*    */   
/*    */   public VariantRenameFix(Schema $$0, String $$1, DSL.TypeReference $$2, String $$3, Map<String, String> $$4) {
/* 14 */     super($$0, false, $$1, $$2, $$3);
/* 15 */     this.renames = $$4;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Typed<?> fix(Typed<?> $$0) {
/* 20 */     return $$0.update(DSL.remainderFinder(), $$0 -> $$0.update("variant", ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\VariantRenameFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */