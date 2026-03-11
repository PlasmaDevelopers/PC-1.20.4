/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import com.google.common.collect.Maps;
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.Type;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import com.mojang.serialization.DynamicOps;
/*    */ import java.util.Map;
/*    */ import java.util.Objects;
/*    */ import java.util.Optional;
/*    */ import java.util.function.Function;
/*    */ 
/*    */ public class ReorganizePoi
/*    */   extends DataFix {
/*    */   public ReorganizePoi(Schema $$0, boolean $$1) {
/* 21 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected TypeRewriteRule makeRule() {
/* 26 */     Type<Pair<String, Dynamic<?>>> $$0 = DSL.named(References.POI_CHUNK.typeName(), DSL.remainderType());
/*    */     
/* 28 */     if (!Objects.equals($$0, getInputSchema().getType(References.POI_CHUNK))) {
/* 29 */       throw new IllegalStateException("Poi type is not what was expected.");
/*    */     }
/* 31 */     return fixTypeEverywhere("POI reorganization", $$0, $$0 -> ());
/*    */   }
/*    */   
/*    */   private static <T> Dynamic<T> cap(Dynamic<T> $$0) {
/* 35 */     Map<Dynamic<T>, Dynamic<T>> $$1 = Maps.newHashMap();
/* 36 */     for (int $$2 = 0; $$2 < 16; $$2++) {
/* 37 */       String $$3 = String.valueOf($$2);
/* 38 */       Optional<Dynamic<T>> $$4 = $$0.get($$3).result();
/* 39 */       if ($$4.isPresent()) {
/* 40 */         Dynamic<T> $$5 = $$4.get();
/* 41 */         Dynamic<T> $$6 = $$0.createMap((Map)ImmutableMap.of($$0.createString("Records"), $$5));
/* 42 */         $$1.put($$0.createInt($$2), $$6);
/* 43 */         $$0 = $$0.remove($$3);
/*    */       } 
/*    */     } 
/*    */     
/* 47 */     return $$0.set("Sections", $$0.createMap($$1));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\ReorganizePoi.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */