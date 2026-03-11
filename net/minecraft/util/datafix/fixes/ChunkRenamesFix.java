/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.OpticFinder;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.Type;
/*    */ import com.mojang.datafixers.util.Either;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import com.mojang.datafixers.util.Unit;
/*    */ import com.mojang.serialization.DataResult;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import com.mojang.serialization.DynamicOps;
/*    */ import com.mojang.serialization.MapLike;
/*    */ import java.util.function.Function;
/*    */ 
/*    */ public class ChunkRenamesFix extends DataFix {
/*    */   public ChunkRenamesFix(Schema $$0) {
/* 21 */     super($$0, true);
/*    */   }
/*    */ 
/*    */   
/*    */   protected TypeRewriteRule makeRule() {
/* 26 */     Type<?> $$0 = getInputSchema().getType(References.CHUNK);
/* 27 */     OpticFinder<?> $$1 = $$0.findField("Level");
/* 28 */     OpticFinder<?> $$2 = $$1.type().findField("Structures");
/*    */     
/* 30 */     Type<?> $$3 = getOutputSchema().getType(References.CHUNK);
/* 31 */     Type<?> $$4 = $$3.findFieldType("structures");
/*    */     
/* 33 */     return fixTypeEverywhereTyped("Chunk Renames; purge Level-tag", $$0, $$3, $$3 -> {
/*    */           Typed<?> $$4 = $$3.getTyped($$0);
/*    */           Typed<?> $$5 = appendChunkName($$4);
/*    */           $$5 = $$5.set(DSL.remainderFinder(), mergeRemainders($$3, (Dynamic)$$4.get(DSL.remainderFinder())));
/*    */           $$5 = renameField($$5, "TileEntities", "block_entities");
/*    */           $$5 = renameField($$5, "TileTicks", "block_ticks");
/*    */           $$5 = renameField($$5, "Entities", "entities");
/*    */           $$5 = renameField($$5, "Sections", "sections");
/*    */           $$5 = $$5.updateTyped($$1, $$2, ());
/*    */           $$5 = renameField($$5, "Structures", "structures");
/*    */           return $$5.update(DSL.remainderFinder(), ());
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static Typed<?> renameField(Typed<?> $$0, String $$1, String $$2) {
/* 51 */     return renameFieldHelper($$0, $$1, $$2, $$0.getType().findFieldType($$1)).update(DSL.remainderFinder(), $$1 -> $$1.remove($$0));
/*    */   }
/*    */   
/*    */   private static <A> Typed<?> renameFieldHelper(Typed<?> $$0, String $$1, String $$2, Type<A> $$3) {
/* 55 */     Type<Either<A, Unit>> $$4 = DSL.optional((Type)DSL.field($$1, $$3));
/* 56 */     Type<Either<A, Unit>> $$5 = DSL.optional((Type)DSL.field($$2, $$3));
/* 57 */     return $$0.update($$4.finder(), $$5, Function.identity());
/*    */   }
/*    */   
/*    */   private static <A> Typed<Pair<String, A>> appendChunkName(Typed<A> $$0) {
/* 61 */     return new Typed(DSL.named("chunk", $$0.getType()), $$0.getOps(), Pair.of("chunk", $$0.getValue()));
/*    */   }
/*    */   
/*    */   private static <T> Dynamic<T> mergeRemainders(Typed<?> $$0, Dynamic<T> $$1) {
/* 65 */     DynamicOps<T> $$2 = $$1.getOps();
/* 66 */     Dynamic<T> $$3 = ((Dynamic)$$0.get(DSL.remainderFinder())).convert($$2);
/* 67 */     DataResult<T> $$4 = $$2.getMap($$1.getValue()).flatMap($$2 -> $$0.mergeToMap($$1.getValue(), $$2));
/* 68 */     return $$4.result().map($$1 -> new Dynamic($$0, $$1)).orElse($$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\ChunkRenamesFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */