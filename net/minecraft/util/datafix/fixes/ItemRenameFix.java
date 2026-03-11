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
/*    */ import net.minecraft.util.datafix.schemas.NamespacedSchema;
/*    */ 
/*    */ public abstract class ItemRenameFix
/*    */   extends DataFix {
/*    */   private final String name;
/*    */   
/*    */   public ItemRenameFix(Schema $$0, String $$1) {
/* 19 */     super($$0, false);
/* 20 */     this.name = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeRewriteRule makeRule() {
/* 25 */     Type<Pair<String, String>> $$0 = DSL.named(References.ITEM_NAME.typeName(), NamespacedSchema.namespacedString());
/* 26 */     if (!Objects.equals(getInputSchema().getType(References.ITEM_NAME), $$0)) {
/* 27 */       throw new IllegalStateException("item name type is not what was expected.");
/*    */     }
/* 29 */     return fixTypeEverywhere(this.name, $$0, $$0 -> ());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static DataFix create(Schema $$0, String $$1, final Function<String, String> fixItem) {
/* 35 */     return new ItemRenameFix($$0, $$1)
/*    */       {
/*    */         protected String fixItem(String $$0) {
/* 38 */           return fixItem.apply($$0);
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   protected abstract String fixItem(String paramString);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\ItemRenameFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */