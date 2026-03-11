/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.DataFixUtils;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import java.util.Objects;
/*    */ import java.util.Optional;
/*    */ import java.util.function.UnaryOperator;
/*    */ import net.minecraft.util.datafix.schemas.NamespacedSchema;
/*    */ 
/*    */ public class RemapChunkStatusFix extends DataFix {
/*    */   private final String name;
/*    */   
/*    */   public RemapChunkStatusFix(Schema $$0, String $$1, UnaryOperator<String> $$2) {
/* 19 */     super($$0, false);
/* 20 */     this.name = $$1;
/* 21 */     this.mapper = $$2;
/*    */   }
/*    */   private final UnaryOperator<String> mapper;
/*    */   
/*    */   protected TypeRewriteRule makeRule() {
/* 26 */     return fixTypeEverywhereTyped(this.name, getInputSchema().getType(References.CHUNK), $$0 -> $$0.update(DSL.remainderFinder(), ()));
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
/*    */   private <T> Dynamic<T> fixStatus(Dynamic<T> $$0) {
/* 38 */     Objects.requireNonNull($$0); Optional<Dynamic<T>> $$1 = $$0.asString().result().map(NamespacedSchema::ensureNamespaced).map(this.mapper).map($$0::createString);
/*    */     
/* 40 */     return (Dynamic<T>)DataFixUtils.orElse($$1, $$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\RemapChunkStatusFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */