/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.OpticFinder;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import net.minecraft.util.datafix.schemas.NamespacedSchema;
/*    */ 
/*    */ public class ItemStackUUIDFix
/*    */   extends AbstractUUIDFix {
/*    */   public ItemStackUUIDFix(Schema $$0) {
/* 15 */     super($$0, References.ITEM_STACK);
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeRewriteRule makeRule() {
/* 20 */     OpticFinder<Pair<String, String>> $$0 = DSL.fieldFinder("id", DSL.named(References.ITEM_NAME.typeName(), NamespacedSchema.namespacedString()));
/*    */     
/* 22 */     return fixTypeEverywhereTyped("ItemStackUUIDFix", getInputSchema().getType(this.typeReference), $$1 -> {
/*    */           OpticFinder<?> $$2 = $$1.getType().findField("tag");
/*    */           return $$1.updateTyped($$2, ());
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private Dynamic<?> updateAttributeModifiers(Dynamic<?> $$0) {
/* 38 */     return $$0.update("AttributeModifiers", $$1 -> $$0.createList($$1.asStream().map(())));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private Dynamic<?> updateSkullOwner(Dynamic<?> $$0) {
/* 46 */     return $$0.update("SkullOwner", $$0 -> (Dynamic)replaceUUIDString($$0, "Id", "Id").orElse($$0));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\ItemStackUUIDFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */