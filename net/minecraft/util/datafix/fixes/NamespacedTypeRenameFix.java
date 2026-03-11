/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.Type;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import com.mojang.serialization.DynamicOps;
/*    */ import java.util.Objects;
/*    */ import java.util.function.Function;
/*    */ import java.util.function.UnaryOperator;
/*    */ import net.minecraft.util.datafix.schemas.NamespacedSchema;
/*    */ 
/*    */ public class NamespacedTypeRenameFix
/*    */   extends DataFix {
/*    */   private final String name;
/*    */   private final DSL.TypeReference type;
/*    */   private final UnaryOperator<String> renamer;
/*    */   
/*    */   public NamespacedTypeRenameFix(Schema $$0, String $$1, DSL.TypeReference $$2, UnaryOperator<String> $$3) {
/* 22 */     super($$0, false);
/* 23 */     this.name = $$1;
/* 24 */     this.type = $$2;
/* 25 */     this.renamer = $$3;
/*    */   }
/*    */ 
/*    */   
/*    */   protected TypeRewriteRule makeRule() {
/* 30 */     Type<Pair<String, String>> $$0 = DSL.named(this.type.typeName(), NamespacedSchema.namespacedString());
/* 31 */     if (!Objects.equals($$0, getInputSchema().getType(this.type))) {
/* 32 */       throw new IllegalStateException("\"" + this.type.typeName() + "\" is not what was expected.");
/*    */     }
/* 34 */     return fixTypeEverywhere(this.name, $$0, $$0 -> ());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\NamespacedTypeRenameFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */