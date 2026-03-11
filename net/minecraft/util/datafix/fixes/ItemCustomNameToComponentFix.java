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
/*    */ import java.util.Optional;
/*    */ import net.minecraft.util.datafix.ComponentDataFixUtils;
/*    */ 
/*    */ public class ItemCustomNameToComponentFix extends DataFix {
/*    */   public ItemCustomNameToComponentFix(Schema $$0, boolean $$1) {
/* 16 */     super($$0, $$1);
/*    */   }
/*    */   
/*    */   private Dynamic<?> fixTag(Dynamic<?> $$0) {
/* 20 */     Optional<? extends Dynamic<?>> $$1 = $$0.get("display").result();
/* 21 */     if ($$1.isPresent()) {
/* 22 */       Dynamic<?> $$2 = $$1.get();
/* 23 */       Optional<String> $$3 = $$2.get("Name").asString().result();
/* 24 */       if ($$3.isPresent()) {
/* 25 */         $$2 = $$2.set("Name", ComponentDataFixUtils.createPlainTextComponent($$2.getOps(), $$3.get()));
/*    */       } else {
/* 27 */         Optional<String> $$4 = $$2.get("LocName").asString().result();
/* 28 */         if ($$4.isPresent()) {
/* 29 */           $$2 = $$2.set("Name", ComponentDataFixUtils.createTranslatableComponent($$2.getOps(), $$4.get()));
/* 30 */           $$2 = $$2.remove("LocName");
/*    */         } 
/*    */       } 
/* 33 */       return $$0.set("display", $$2);
/*    */     } 
/* 35 */     return $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeRewriteRule makeRule() {
/* 40 */     Type<?> $$0 = getInputSchema().getType(References.ITEM_STACK);
/* 41 */     OpticFinder<?> $$1 = $$0.findField("tag");
/*    */     
/* 43 */     return fixTypeEverywhereTyped("ItemCustomNameToComponentFix", $$0, $$1 -> $$1.updateTyped($$0, ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\ItemCustomNameToComponentFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */