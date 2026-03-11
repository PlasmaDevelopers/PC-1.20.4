/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.google.common.collect.Streams;
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.util.datafix.ComponentDataFixUtils;
/*    */ 
/*    */ public class DropInvalidSignDataFix
/*    */   extends NamedEntityFix
/*    */ {
/* 16 */   private static final String[] FIELDS_TO_DROP = new String[] { "Text1", "Text2", "Text3", "Text4", "FilteredText1", "FilteredText2", "FilteredText3", "FilteredText4", "Color", "GlowingText" };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DropInvalidSignDataFix(Schema $$0, String $$1, String $$2) {
/* 23 */     super($$0, false, $$1, References.BLOCK_ENTITY, $$2);
/*    */   }
/*    */   
/*    */   private static <T> Dynamic<T> fix(Dynamic<T> $$0) {
/* 27 */     $$0 = $$0.update("front_text", DropInvalidSignDataFix::fixText);
/* 28 */     $$0 = $$0.update("back_text", DropInvalidSignDataFix::fixText);
/* 29 */     for (String $$1 : FIELDS_TO_DROP) {
/* 30 */       $$0 = $$0.remove($$1);
/*    */     }
/* 32 */     return $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   private static <T> Dynamic<T> fixText(Dynamic<T> $$0) {
/* 37 */     boolean $$1 = $$0.get("_filtered_correct").asBoolean(false);
/* 38 */     if ($$1) {
/* 39 */       return $$0.remove("_filtered_correct");
/*    */     }
/*    */ 
/*    */     
/* 43 */     Optional<Stream<Dynamic<T>>> $$2 = $$0.get("filtered_messages").asStreamOpt().result();
/* 44 */     if ($$2.isEmpty()) {
/* 45 */       return $$0;
/*    */     }
/*    */     
/* 48 */     Dynamic<T> $$3 = ComponentDataFixUtils.createEmptyComponent($$0.getOps());
/* 49 */     List<Dynamic<T>> $$4 = ((Stream<Dynamic<T>>)$$0.get("messages").asStreamOpt().result().orElse(Stream.of((Dynamic<T>[])new Dynamic[0]))).toList();
/*    */ 
/*    */ 
/*    */     
/* 53 */     List<Dynamic<T>> $$5 = Streams.mapWithIndex($$2.get(), ($$2, $$3) -> { Dynamic<T> $$4 = ($$3 < $$0.size()) ? $$0.get((int)$$3) : $$1; return $$2.equals($$1) ? $$4 : $$2; }).toList();
/* 54 */     if ($$5.stream().allMatch($$1 -> $$1.equals($$0))) {
/* 55 */       return $$0.remove("filtered_messages");
/*    */     }
/* 57 */     return $$0.set("filtered_messages", $$0.createList($$5.stream()));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected Typed<?> fix(Typed<?> $$0) {
/* 63 */     return $$0.update(DSL.remainderFinder(), DropInvalidSignDataFix::fix);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\DropInvalidSignDataFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */