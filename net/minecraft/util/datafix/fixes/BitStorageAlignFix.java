/*     */ package net.minecraft.util.datafix.fixes;
/*     */ 
/*     */ import com.mojang.datafixers.DSL;
/*     */ import com.mojang.datafixers.DataFix;
/*     */ import com.mojang.datafixers.DataFixUtils;
/*     */ import com.mojang.datafixers.OpticFinder;
/*     */ import com.mojang.datafixers.TypeRewriteRule;
/*     */ import com.mojang.datafixers.Typed;
/*     */ import com.mojang.datafixers.schemas.Schema;
/*     */ import com.mojang.datafixers.types.Type;
/*     */ import com.mojang.datafixers.types.templates.List;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import java.util.List;
/*     */ import java.util.stream.LongStream;
/*     */ import net.minecraft.util.Mth;
/*     */ 
/*     */ public class BitStorageAlignFix
/*     */   extends DataFix
/*     */ {
/*     */   private static final int BIT_TO_LONG_SHIFT = 6;
/*     */   private static final int SECTION_WIDTH = 16;
/*     */   private static final int SECTION_HEIGHT = 16;
/*     */   private static final int SECTION_SIZE = 4096;
/*     */   private static final int HEIGHTMAP_BITS = 9;
/*     */   private static final int HEIGHTMAP_SIZE = 256;
/*     */   
/*     */   public BitStorageAlignFix(Schema $$0) {
/*  29 */     super($$0, false);
/*     */   }
/*     */ 
/*     */   
/*     */   protected TypeRewriteRule makeRule() {
/*  34 */     Type<?> $$0 = getInputSchema().getType(References.CHUNK);
/*  35 */     Type<?> $$1 = $$0.findFieldType("Level");
/*     */     
/*  37 */     OpticFinder<?> $$2 = DSL.fieldFinder("Level", $$1);
/*  38 */     OpticFinder<?> $$3 = $$2.type().findField("Sections");
/*     */     
/*  40 */     Type<?> $$4 = ((List.ListType)$$3.type()).getElement();
/*  41 */     OpticFinder<?> $$5 = DSL.typeFinder($$4);
/*     */     
/*  43 */     Type<Pair<String, Dynamic<?>>> $$6 = DSL.named(References.BLOCK_STATE.typeName(), DSL.remainderType());
/*  44 */     OpticFinder<List<Pair<String, Dynamic<?>>>> $$7 = DSL.fieldFinder("Palette", (Type)DSL.list($$6));
/*     */     
/*  46 */     return fixTypeEverywhereTyped("BitStorageAlignFix", $$0, getOutputSchema().getType(References.CHUNK), $$4 -> $$4.updateTyped($$0, ()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Typed<?> updateHeightmaps(Typed<?> $$0) {
/*  54 */     return $$0.update(DSL.remainderFinder(), $$0 -> $$0.update("Heightmaps", ()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Typed<?> updateSections(OpticFinder<?> $$0, OpticFinder<?> $$1, OpticFinder<List<Pair<String, Dynamic<?>>>> $$2, Typed<?> $$3) {
/*  66 */     return $$3.updateTyped($$0, $$2 -> $$2.updateTyped($$0, ()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Dynamic<?> updateBitStorage(Dynamic<?> $$0, Dynamic<?> $$1, int $$2, int $$3) {
/*  82 */     long[] $$4 = $$1.asLongStream().toArray();
/*  83 */     long[] $$5 = addPadding($$2, $$3, $$4);
/*  84 */     return $$0.createLongList(LongStream.of($$5));
/*     */   }
/*     */   
/*     */   public static long[] addPadding(int $$0, int $$1, long[] $$2) {
/*  88 */     int $$3 = $$2.length;
/*  89 */     if ($$3 == 0) {
/*  90 */       return $$2;
/*     */     }
/*     */     
/*  93 */     long $$4 = (1L << $$1) - 1L;
/*  94 */     int $$5 = 64 / $$1;
/*  95 */     int $$6 = ($$0 + $$5 - 1) / $$5;
/*     */     
/*  97 */     long[] $$7 = new long[$$6];
/*     */     
/*  99 */     int $$8 = 0;
/* 100 */     int $$9 = 0;
/* 101 */     long $$10 = 0L;
/*     */     
/* 103 */     int $$11 = 0;
/* 104 */     long $$12 = $$2[0];
/* 105 */     long $$13 = ($$3 > 1) ? $$2[1] : 0L;
/*     */     
/* 107 */     for (int $$14 = 0; $$14 < $$0; $$14++) {
/* 108 */       long $$21; int $$15 = $$14 * $$1;
/* 109 */       int $$16 = $$15 >> 6;
/* 110 */       int $$17 = ($$14 + 1) * $$1 - 1 >> 6;
/* 111 */       int $$18 = $$15 ^ $$16 << 6;
/*     */       
/* 113 */       if ($$16 != $$11) {
/* 114 */         $$12 = $$13;
/* 115 */         $$13 = ($$16 + 1 < $$3) ? $$2[$$16 + 1] : 0L;
/* 116 */         $$11 = $$16;
/*     */       } 
/*     */ 
/*     */       
/* 120 */       if ($$16 == $$17) {
/* 121 */         long $$19 = $$12 >>> $$18 & $$4;
/*     */       } else {
/* 123 */         int $$20 = 64 - $$18;
/* 124 */         $$21 = ($$12 >>> $$18 | $$13 << $$20) & $$4;
/*     */       } 
/*     */       
/* 127 */       int $$22 = $$9 + $$1;
/* 128 */       if ($$22 >= 64) {
/* 129 */         $$7[$$8++] = $$10;
/* 130 */         $$10 = $$21;
/* 131 */         $$9 = $$1;
/*     */       } else {
/* 133 */         $$10 |= $$21 << $$9;
/* 134 */         $$9 = $$22;
/*     */       } 
/*     */     } 
/* 137 */     if ($$10 != 0L) {
/* 138 */       $$7[$$8] = $$10;
/*     */     }
/*     */     
/* 141 */     return $$7;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\BitStorageAlignFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */