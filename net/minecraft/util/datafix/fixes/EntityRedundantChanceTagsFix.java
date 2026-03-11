/*    */ package net.minecraft.util.datafix.fixes;
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import com.mojang.serialization.OptionalDynamic;
/*    */ import java.util.List;
/*    */ import java.util.Objects;
/*    */ 
/*    */ public class EntityRedundantChanceTagsFix extends DataFix {
/* 13 */   private static final Codec<List<Float>> FLOAT_LIST_CODEC = Codec.FLOAT.listOf();
/*    */   
/*    */   public EntityRedundantChanceTagsFix(Schema $$0, boolean $$1) {
/* 16 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeRewriteRule makeRule() {
/* 21 */     return fixTypeEverywhereTyped("EntityRedundantChanceTagsFix", getInputSchema().getType(References.ENTITY), $$0 -> $$0.update(DSL.remainderFinder(), ()));
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
/*    */   private static boolean isZeroList(OptionalDynamic<?> $$0, int $$1) {
/* 34 */     Objects.requireNonNull(FLOAT_LIST_CODEC); return ((Boolean)$$0.flatMap(FLOAT_LIST_CODEC::parse).map($$1 -> Boolean.valueOf(($$1.size() == $$0 && $$1.stream().allMatch(())))).result().orElse(Boolean.valueOf(false))).booleanValue();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\EntityRedundantChanceTagsFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */