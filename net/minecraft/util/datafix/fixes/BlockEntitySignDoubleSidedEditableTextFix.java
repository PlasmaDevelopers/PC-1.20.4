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
/*    */ public class BlockEntitySignDoubleSidedEditableTextFix
/*    */   extends NamedEntityFix {
/*    */   public static final String FILTERED_CORRECT = "_filtered_correct";
/*    */   private static final String DEFAULT_COLOR = "black";
/*    */   
/*    */   public BlockEntitySignDoubleSidedEditableTextFix(Schema $$0, String $$1, String $$2) {
/* 19 */     super($$0, false, $$1, References.BLOCK_ENTITY, $$2);
/*    */   }
/*    */   
/*    */   private static <T> Dynamic<T> fixTag(Dynamic<T> $$0) {
/* 23 */     return $$0
/* 24 */       .set("front_text", fixFrontTextTag($$0))
/* 25 */       .set("back_text", createDefaultText($$0))
/* 26 */       .set("is_waxed", $$0.createBoolean(false));
/*    */   }
/*    */   
/*    */   private static <T> Dynamic<T> fixFrontTextTag(Dynamic<T> $$0) {
/* 30 */     Dynamic<T> $$1 = ComponentDataFixUtils.createEmptyComponent($$0.getOps());
/* 31 */     List<Dynamic<T>> $$2 = getLines($$0, "Text").map($$1 -> (Dynamic)$$1.orElse($$0)).toList();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 37 */     Dynamic<T> $$3 = $$0.emptyMap().set("messages", $$0.createList($$2.stream())).set("color", $$0.get("Color").result().orElse($$0.createString("black"))).set("has_glowing_text", $$0.get("GlowingText").result().orElse($$0.createBoolean(false))).set("_filtered_correct", $$0.createBoolean(true));
/*    */     
/* 39 */     List<Optional<Dynamic<T>>> $$4 = getLines($$0, "FilteredText").toList();
/* 40 */     if ($$4.stream().anyMatch(Optional::isPresent)) {
/* 41 */       $$3 = $$3.set("filtered_messages", $$0.createList(Streams.mapWithIndex($$4.stream(), ($$1, $$2) -> {
/*    */                 Dynamic<T> $$3 = $$0.get((int)$$2);
/*    */                 
/*    */                 return $$1.orElse($$3);
/*    */               })));
/*    */     }
/* 47 */     return $$3;
/*    */   }
/*    */   
/*    */   private static <T> Stream<Optional<Dynamic<T>>> getLines(Dynamic<T> $$0, String $$1) {
/* 51 */     return Stream.of((Optional<Dynamic<T>>[])new Optional[] { $$0
/* 52 */           .get($$1 + "1").result(), $$0
/* 53 */           .get($$1 + "2").result(), $$0
/* 54 */           .get($$1 + "3").result(), $$0
/* 55 */           .get($$1 + "4").result() });
/*    */   }
/*    */ 
/*    */   
/*    */   private static <T> Dynamic<T> createDefaultText(Dynamic<T> $$0) {
/* 60 */     return $$0.emptyMap()
/* 61 */       .set("messages", createEmptyLines($$0))
/* 62 */       .set("color", $$0.createString("black"))
/* 63 */       .set("has_glowing_text", $$0.createBoolean(false));
/*    */   }
/*    */   
/*    */   private static <T> Dynamic<T> createEmptyLines(Dynamic<T> $$0) {
/* 67 */     Dynamic<T> $$1 = ComponentDataFixUtils.createEmptyComponent($$0.getOps());
/* 68 */     return $$0.createList(Stream.of(new Dynamic[] { $$1, $$1, $$1, $$1 }));
/*    */   }
/*    */ 
/*    */   
/*    */   protected Typed<?> fix(Typed<?> $$0) {
/* 73 */     return $$0.update(DSL.remainderFinder(), BlockEntitySignDoubleSidedEditableTextFix::fixTag);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\BlockEntitySignDoubleSidedEditableTextFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */