/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.DataFixUtils;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.Type;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import com.mojang.serialization.DynamicOps;
/*    */ import java.util.Objects;
/*    */ import java.util.function.Function;
/*    */ import java.util.stream.Stream;
/*    */ 
/*    */ public abstract class AbstractPoiSectionFix
/*    */   extends DataFix {
/*    */   private final String name;
/*    */   
/*    */   public AbstractPoiSectionFix(Schema $$0, String $$1) {
/* 21 */     super($$0, false);
/* 22 */     this.name = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   protected TypeRewriteRule makeRule() {
/* 27 */     Type<Pair<String, Dynamic<?>>> $$0 = DSL.named(References.POI_CHUNK.typeName(), DSL.remainderType());
/*    */     
/* 29 */     if (!Objects.equals($$0, getInputSchema().getType(References.POI_CHUNK))) {
/* 30 */       throw new IllegalStateException("Poi type is not what was expected.");
/*    */     }
/* 32 */     return fixTypeEverywhere(this.name, $$0, $$0 -> ());
/*    */   }
/*    */   
/*    */   private <T> Dynamic<T> cap(Dynamic<T> $$0) {
/* 36 */     return $$0.update("Sections", $$0 -> $$0.updateMapValues(()));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private Dynamic<?> processSection(Dynamic<?> $$0) {
/* 42 */     return $$0.update("Records", this::processSectionRecords);
/*    */   }
/*    */   
/*    */   private <T> Dynamic<T> processSectionRecords(Dynamic<T> $$0) {
/* 46 */     return (Dynamic<T>)DataFixUtils.orElse($$0.asStreamOpt().result().map($$1 -> $$0.createList(processRecords($$1))), $$0);
/*    */   }
/*    */   
/*    */   protected abstract <T> Stream<Dynamic<T>> processRecords(Stream<Dynamic<T>> paramStream);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\AbstractPoiSectionFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */