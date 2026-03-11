/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import com.google.common.collect.Maps;
/*    */ import com.google.common.collect.Streams;
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.OpticFinder;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.Type;
/*    */ import com.mojang.datafixers.types.templates.List;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import com.mojang.serialization.DynamicOps;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.function.Function;
/*    */ 
/*    */ 
/*    */ public class ChunkBedBlockEntityInjecterFix
/*    */   extends DataFix
/*    */ {
/*    */   public ChunkBedBlockEntityInjecterFix(Schema $$0, boolean $$1) {
/* 26 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeRewriteRule makeRule() {
/* 31 */     Type<?> $$0 = getOutputSchema().getType(References.CHUNK);
/* 32 */     Type<?> $$1 = $$0.findFieldType("Level");
/* 33 */     Type<?> $$2 = $$1.findFieldType("TileEntities");
/* 34 */     if (!($$2 instanceof List.ListType)) {
/* 35 */       throw new IllegalStateException("Tile entity type is not a list type.");
/*    */     }
/* 37 */     List.ListType<?> $$3 = (List.ListType)$$2;
/*    */     
/* 39 */     return cap($$1, $$3);
/*    */   }
/*    */   
/*    */   private <TE> TypeRewriteRule cap(Type<?> $$0, List.ListType<TE> $$1) {
/* 43 */     Type<TE> $$2 = $$1.getElement();
/* 44 */     OpticFinder<?> $$3 = DSL.fieldFinder("Level", $$0);
/* 45 */     OpticFinder<List<TE>> $$4 = DSL.fieldFinder("TileEntities", (Type)$$1);
/*    */ 
/*    */     
/* 48 */     int $$5 = 416;
/*    */     
/* 50 */     return TypeRewriteRule.seq(
/* 51 */         fixTypeEverywhere("InjectBedBlockEntityType", (Type)getInputSchema().findChoiceType(References.BLOCK_ENTITY), (Type)getOutputSchema().findChoiceType(References.BLOCK_ENTITY), $$0 -> ()), 
/* 52 */         fixTypeEverywhereTyped("BedBlockEntityInjecter", getOutputSchema().getType(References.CHUNK), $$3 -> {
/*    */             Typed<?> $$4 = $$3.getTyped($$0);
/*    */             Dynamic<?> $$5 = (Dynamic)$$4.get(DSL.remainderFinder());
/*    */             int $$6 = $$5.get("xPos").asInt(0);
/*    */             int $$7 = $$5.get("zPos").asInt(0);
/*    */             List<TE> $$8 = Lists.newArrayList((Iterable)$$4.getOrCreate($$1));
/*    */             List<? extends Dynamic<?>> $$9 = $$5.get("Sections").asList(Function.identity());
/*    */             for (Dynamic<?> $$10 : $$9) {
/*    */               int $$11 = $$10.get("Y").asInt(0);
/*    */               Streams.mapWithIndex($$10.get("Blocks").asIntStream(), ()).forEachOrdered(());
/*    */             } 
/*    */             return !$$8.isEmpty() ? $$3.set($$0, $$4.set($$1, $$8)) : $$3;
/*    */           }));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\ChunkBedBlockEntityInjecterFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */