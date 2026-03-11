/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.DataFixUtils;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ 
/*    */ public class OptionsRenameFieldFix extends DataFix {
/*    */   private final String fixName;
/*    */   
/*    */   public OptionsRenameFieldFix(Schema $$0, boolean $$1, String $$2, String $$3, String $$4) {
/* 15 */     super($$0, $$1);
/* 16 */     this.fixName = $$2;
/* 17 */     this.fieldFrom = $$3;
/* 18 */     this.fieldTo = $$4;
/*    */   }
/*    */   private final String fieldFrom; private final String fieldTo;
/*    */   
/*    */   public TypeRewriteRule makeRule() {
/* 23 */     return fixTypeEverywhereTyped(this.fixName, getInputSchema().getType(References.OPTIONS), $$0 -> $$0.update(DSL.remainderFinder(), ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\OptionsRenameFieldFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */