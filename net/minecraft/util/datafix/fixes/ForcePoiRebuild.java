/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.Type;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import com.mojang.serialization.DynamicOps;
/*    */ import java.util.Objects;
/*    */ import java.util.function.Function;
/*    */ 
/*    */ public class ForcePoiRebuild
/*    */   extends DataFix
/*    */ {
/*    */   public ForcePoiRebuild(Schema $$0, boolean $$1) {
/* 18 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected TypeRewriteRule makeRule() {
/* 23 */     Type<Pair<String, Dynamic<?>>> $$0 = DSL.named(References.POI_CHUNK.typeName(), DSL.remainderType());
/*    */     
/* 25 */     if (!Objects.equals($$0, getInputSchema().getType(References.POI_CHUNK))) {
/* 26 */       throw new IllegalStateException("Poi type is not what was expected.");
/*    */     }
/* 28 */     return fixTypeEverywhere("POI rebuild", $$0, $$0 -> ());
/*    */   }
/*    */   
/*    */   private static <T> Dynamic<T> cap(Dynamic<T> $$0) {
/* 32 */     return $$0.update("Sections", $$0 -> $$0.updateMapValues(()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\ForcePoiRebuild.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */