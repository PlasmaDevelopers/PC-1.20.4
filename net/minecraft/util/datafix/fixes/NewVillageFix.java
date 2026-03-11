/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.DataFixUtils;
/*    */ import com.mojang.datafixers.OpticFinder;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.Type;
/*    */ import com.mojang.datafixers.types.templates.CompoundList;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import java.util.List;
/*    */ import java.util.Objects;
/*    */ import java.util.Optional;
/*    */ import java.util.stream.Collectors;
/*    */ import net.minecraft.util.datafix.schemas.NamespacedSchema;
/*    */ 
/*    */ public class NewVillageFix extends DataFix {
/*    */   public NewVillageFix(Schema $$0, boolean $$1) {
/* 22 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected TypeRewriteRule makeRule() {
/* 27 */     CompoundList.CompoundListType<String, ?> $$0 = DSL.compoundList(DSL.string(), getInputSchema().getType(References.STRUCTURE_FEATURE));
/* 28 */     OpticFinder<? extends List<? extends Pair<String, ?>>> $$1 = $$0.finder();
/*    */     
/* 30 */     return cap($$0);
/*    */   }
/*    */   
/*    */   private <SF> TypeRewriteRule cap(CompoundList.CompoundListType<String, SF> $$0) {
/* 34 */     Type<?> $$1 = getInputSchema().getType(References.CHUNK);
/* 35 */     Type<?> $$2 = getInputSchema().getType(References.STRUCTURE_FEATURE);
/* 36 */     OpticFinder<?> $$3 = $$1.findField("Level");
/* 37 */     OpticFinder<?> $$4 = $$3.type().findField("Structures");
/* 38 */     OpticFinder<?> $$5 = $$4.type().findField("Starts");
/* 39 */     OpticFinder<List<Pair<String, SF>>> $$6 = $$0.finder();
/* 40 */     return TypeRewriteRule.seq(
/* 41 */         fixTypeEverywhereTyped("NewVillageFix", $$1, $$4 -> $$4.updateTyped($$0, ())), 
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 57 */         fixTypeEverywhereTyped("NewVillageStartFix", $$2, $$0 -> $$0.update(DSL.remainderFinder(), ())));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\NewVillageFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */