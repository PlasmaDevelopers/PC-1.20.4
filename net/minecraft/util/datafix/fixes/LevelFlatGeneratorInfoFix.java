/*    */ package net.minecraft.util.datafix.fixes;
/*    */ import com.google.common.annotations.VisibleForTesting;
/*    */ import com.google.common.base.Splitter;
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.DataFixUtils;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Objects;
/*    */ import java.util.stream.Collectors;
/*    */ import java.util.stream.StreamSupport;
/*    */ import org.apache.commons.lang3.math.NumberUtils;
/*    */ 
/*    */ public class LevelFlatGeneratorInfoFix extends DataFix {
/*    */   public LevelFlatGeneratorInfoFix(Schema $$0, boolean $$1) {
/* 20 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   private static final String GENERATOR_OPTIONS = "generatorOptions";
/*    */   @VisibleForTesting
/*    */   static final String DEFAULT = "minecraft:bedrock,2*minecraft:dirt,minecraft:grass_block;1;village";
/* 27 */   private static final Splitter SPLITTER = Splitter.on(';').limit(5);
/* 28 */   private static final Splitter LAYER_SPLITTER = Splitter.on(',');
/* 29 */   private static final Splitter OLD_AMOUNT_SPLITTER = Splitter.on('x').limit(2);
/* 30 */   private static final Splitter AMOUNT_SPLITTER = Splitter.on('*').limit(2);
/* 31 */   private static final Splitter BLOCK_SPLITTER = Splitter.on(':').limit(3);
/*    */ 
/*    */   
/*    */   public TypeRewriteRule makeRule() {
/* 35 */     return fixTypeEverywhereTyped("LevelFlatGeneratorInfoFix", getInputSchema().getType(References.LEVEL), $$0 -> $$0.update(DSL.remainderFinder(), this::fix));
/*    */   }
/*    */   
/*    */   private Dynamic<?> fix(Dynamic<?> $$0) {
/* 39 */     if ($$0.get("generatorName").asString("").equalsIgnoreCase("flat"))
/* 40 */       return $$0.update("generatorOptions", $$0 -> {
/*    */             Objects.requireNonNull($$0); return (Dynamic)DataFixUtils.orElse($$0.asString().map(this::fixString).map($$0::createString).result(), $$0);
/* 42 */           });  return $$0;
/*    */   } @VisibleForTesting
/*    */   String fixString(String $$0) {
/*    */     int $$5;
/*    */     String $$6;
/* 47 */     if ($$0.isEmpty()) {
/* 48 */       return "minecraft:bedrock,2*minecraft:dirt,minecraft:grass_block;1;village";
/*    */     }
/*    */     
/* 51 */     Iterator<String> $$1 = SPLITTER.split($$0).iterator();
/*    */     
/* 53 */     String $$2 = $$1.next();
/*    */ 
/*    */     
/* 56 */     if ($$1.hasNext()) {
/* 57 */       int $$3 = NumberUtils.toInt($$2, 0);
/* 58 */       String $$4 = $$1.next();
/*    */     } else {
/* 60 */       $$5 = 0;
/* 61 */       $$6 = $$2;
/*    */     } 
/*    */     
/* 64 */     if ($$5 < 0 || $$5 > 3) {
/* 65 */       return "minecraft:bedrock,2*minecraft:dirt,minecraft:grass_block;1;village";
/*    */     }
/*    */     
/* 68 */     StringBuilder $$7 = new StringBuilder();
/*    */     
/* 70 */     Splitter $$8 = ($$5 < 3) ? OLD_AMOUNT_SPLITTER : AMOUNT_SPLITTER;
/*    */     
/* 72 */     $$7.append(StreamSupport.stream(LAYER_SPLITTER.split($$6).spliterator(), false).map($$2 -> {
/*    */             int $$6;
/*    */             
/*    */             String $$7;
/*    */             
/*    */             List<String> $$3 = $$0.splitToList($$2);
/*    */             
/*    */             if ($$3.size() == 2) {
/*    */               int $$4 = NumberUtils.toInt($$3.get(0));
/*    */               String $$5 = $$3.get(1);
/*    */             } else {
/*    */               $$6 = 1;
/*    */               $$7 = $$3.get(0);
/*    */             } 
/*    */             List<String> $$8 = BLOCK_SPLITTER.splitToList($$7);
/*    */             int $$9 = ((String)$$8.get(0)).equals("minecraft") ? 1 : 0;
/*    */             String $$10 = $$8.get($$9);
/*    */             int $$11 = ($$1 == 3) ? EntityBlockStateFix.getBlockId("minecraft:" + $$10) : NumberUtils.toInt($$10, 0);
/*    */             int $$12 = $$9 + 1;
/*    */             int $$13 = ($$8.size() > $$12) ? NumberUtils.toInt($$8.get($$12), 0) : 0;
/*    */             return (($$6 == 1) ? "" : ("" + $$6 + "*")) + (($$6 == 1) ? "" : ("" + $$6 + "*"));
/* 93 */           }).collect(Collectors.joining(",")));
/*    */     
/* 95 */     while ($$1.hasNext()) {
/* 96 */       $$7.append(';').append($$1.next());
/*    */     }
/*    */     
/* 99 */     return $$7.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\LevelFlatGeneratorInfoFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */