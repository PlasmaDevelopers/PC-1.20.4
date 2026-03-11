/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ 
/*    */ public class WriteAndReadFix extends DataFix {
/*    */   private final String name;
/*    */   private final DSL.TypeReference type;
/*    */   
/*    */   public WriteAndReadFix(Schema $$0, String $$1, DSL.TypeReference $$2) {
/* 13 */     super($$0, true);
/* 14 */     this.name = $$1;
/* 15 */     this.type = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   protected TypeRewriteRule makeRule() {
/* 20 */     return writeAndRead(this.name, getInputSchema().getType(this.type), getOutputSchema().getType(this.type));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\WriteAndReadFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */