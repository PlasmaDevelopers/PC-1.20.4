/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.OpticFinder;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import java.util.Objects;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.util.datafix.ComponentDataFixUtils;
/*    */ import net.minecraft.util.datafix.schemas.NamespacedSchema;
/*    */ 
/*    */ public class EntityCustomNameToComponentFix
/*    */   extends DataFix {
/*    */   public EntityCustomNameToComponentFix(Schema $$0, boolean $$1) {
/* 18 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeRewriteRule makeRule() {
/* 23 */     OpticFinder<String> $$0 = DSL.fieldFinder("id", NamespacedSchema.namespacedString());
/* 24 */     return fixTypeEverywhereTyped("EntityCustomNameToComponentFix", getInputSchema().getType(References.ENTITY), $$1 -> $$1.update(DSL.remainderFinder(), ()));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Dynamic<?> fixTagCustomName(Dynamic<?> $$0) {
/* 34 */     String $$1 = $$0.get("CustomName").asString("");
/* 35 */     if ($$1.isEmpty()) {
/* 36 */       return $$0.remove("CustomName");
/*    */     }
/* 38 */     return $$0.set("CustomName", ComponentDataFixUtils.createPlainTextComponent($$0.getOps(), $$1));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\EntityCustomNameToComponentFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */