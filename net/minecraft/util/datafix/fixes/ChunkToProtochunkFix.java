/*    */ package net.minecraft.util.datafix.fixes;
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.DataFixUtils;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import it.unimi.dsi.fastutil.shorts.ShortArrayList;
/*    */ import it.unimi.dsi.fastutil.shorts.ShortList;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import java.util.stream.Collectors;
/*    */ import java.util.stream.IntStream;
/*    */ import java.util.stream.Stream;
/*    */ 
/*    */ public class ChunkToProtochunkFix extends DataFix {
/*    */   private static final int NUM_SECTIONS = 16;
/*    */   
/*    */   public ChunkToProtochunkFix(Schema $$0, boolean $$1) {
/* 20 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeRewriteRule makeRule() {
/* 25 */     return writeFixAndRead("ChunkToProtoChunkFix", getInputSchema().getType(References.CHUNK), getOutputSchema().getType(References.CHUNK), $$0 -> $$0.update("Level", ChunkToProtochunkFix::fixChunkData));
/*    */   }
/*    */ 
/*    */   
/*    */   private static <T> Dynamic<T> fixChunkData(Dynamic<T> $$0) {
/*    */     String $$5;
/* 31 */     boolean $$1 = $$0.get("TerrainPopulated").asBoolean(false);
/*    */     
/* 33 */     boolean $$2 = ($$0.get("LightPopulated").asNumber().result().isEmpty() || $$0.get("LightPopulated").asBoolean(false));
/*    */ 
/*    */     
/* 36 */     if ($$1) {
/* 37 */       if ($$2) {
/* 38 */         String $$3 = "mobs_spawned";
/*    */       } else {
/* 40 */         String $$4 = "decorated";
/*    */       } 
/*    */     } else {
/* 43 */       $$5 = "carved";
/*    */     } 
/* 45 */     return repackTicks(repackBiomes($$0))
/* 46 */       .set("Status", $$0.createString($$5))
/* 47 */       .set("hasLegacyStructureData", $$0.createBoolean(true));
/*    */   }
/*    */   
/*    */   private static <T> Dynamic<T> repackBiomes(Dynamic<T> $$0) {
/* 51 */     return $$0.update("Biomes", $$1 -> (Dynamic)DataFixUtils.orElse($$1.asByteBufferOpt().result().map(()), $$1));
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
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static <T> Dynamic<T> repackTicks(Dynamic<T> $$0) {
/* 68 */     return (Dynamic<T>)DataFixUtils.orElse($$0
/* 69 */         .get("TileTicks").asStreamOpt().result().map($$1 -> { List<ShortList> $$2 = (List<ShortList>)IntStream.range(0, 16).mapToObj(()).collect(Collectors.toList()); $$1.forEach(()); return $$0.remove("TileTicks").set("ToBeTicked", $$0.createList($$2.stream().map(()))); }), $$0);
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
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static short packOffsetCoordinates(int $$0, int $$1, int $$2) {
/* 86 */     return (short)($$0 & 0xF | ($$1 & 0xF) << 4 | ($$2 & 0xF) << 8);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\ChunkToProtochunkFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */