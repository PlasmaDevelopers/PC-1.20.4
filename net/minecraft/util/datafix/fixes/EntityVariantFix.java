/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFixUtils;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import com.mojang.serialization.DynamicOps;
/*    */ import java.util.function.Function;
/*    */ import java.util.function.IntFunction;
/*    */ 
/*    */ public class EntityVariantFix
/*    */   extends NamedEntityFix {
/*    */   private final String fieldName;
/*    */   private final IntFunction<String> idConversions;
/*    */   
/*    */   public EntityVariantFix(Schema $$0, String $$1, DSL.TypeReference $$2, String $$3, String $$4, IntFunction<String> $$5) {
/* 18 */     super($$0, false, $$1, $$2, $$3);
/* 19 */     this.fieldName = $$4;
/* 20 */     this.idConversions = $$5;
/*    */   }
/*    */   
/*    */   private static <T> Dynamic<T> updateAndRename(Dynamic<T> $$0, String $$1, String $$2, Function<Dynamic<T>, Dynamic<T>> $$3) {
/* 24 */     return $$0.map($$4 -> {
/*    */           DynamicOps<T> $$5 = $$0.getOps();
/*    */           Function<T, T> $$6 = ();
/*    */           return $$5.get($$4, $$2).map(()).result().orElse($$4);
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected Typed<?> fix(Typed<?> $$0) {
/* 36 */     return $$0.update(DSL.remainderFinder(), $$0 -> updateAndRename($$0, this.fieldName, "variant", ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\EntityVariantFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */