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
/*    */ import net.minecraft.Util;
/*    */ 
/*    */ public abstract class NamedEntityWriteReadFix
/*    */   extends DataFix
/*    */ {
/*    */   private final String name;
/*    */   private final String entityName;
/*    */   private final DSL.TypeReference type;
/*    */   
/*    */   public NamedEntityWriteReadFix(Schema $$0, boolean $$1, String $$2, DSL.TypeReference $$3, String $$4) {
/* 21 */     super($$0, $$1);
/* 22 */     this.name = $$2;
/* 23 */     this.type = $$3;
/* 24 */     this.entityName = $$4;
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeRewriteRule makeRule() {
/* 29 */     Type<?> $$0 = getInputSchema().getType(this.type);
/* 30 */     Type<?> $$1 = getInputSchema().getChoiceType(this.type, this.entityName);
/*    */     
/* 32 */     Type<?> $$2 = getOutputSchema().getType(this.type);
/* 33 */     Type<?> $$3 = getOutputSchema().getChoiceType(this.type, this.entityName);
/*    */     
/* 35 */     OpticFinder<?> $$4 = DSL.namedChoice(this.entityName, $$1);
/*    */ 
/*    */ 
/*    */     
/* 39 */     return fixTypeEverywhereTyped(this.name, $$0, $$2, $$2 -> $$2.updateTyped($$0, $$1, ()));
/*    */   }
/*    */   
/*    */   protected abstract <T> Dynamic<T> fix(Dynamic<T> paramDynamic);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\NamedEntityWriteReadFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */