/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.Type;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import com.mojang.serialization.OptionalDynamic;
/*    */ import java.util.Map;
/*    */ import java.util.Optional;
/*    */ import java.util.Set;
/*    */ import net.minecraft.core.SectionPos;
/*    */ import net.minecraft.util.datafix.schemas.NamespacedSchema;
/*    */ 
/*    */ public class BlendingDataFix
/*    */   extends DataFix {
/*    */   private final String name;
/* 20 */   private static final Set<String> STATUSES_TO_SKIP_BLENDING = Set.of("minecraft:empty", "minecraft:structure_starts", "minecraft:structure_references", "minecraft:biomes");
/*    */   
/*    */   public BlendingDataFix(Schema $$0) {
/* 23 */     super($$0, false);
/* 24 */     this.name = "Blending Data Fix v" + $$0.getVersionKey();
/*    */   }
/*    */ 
/*    */   
/*    */   protected TypeRewriteRule makeRule() {
/* 29 */     Type<?> $$0 = getOutputSchema().getType(References.CHUNK);
/*    */     
/* 31 */     return fixTypeEverywhereTyped(this.name, $$0, $$0 -> $$0.update(DSL.remainderFinder(), ()));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static Dynamic<?> updateChunkTag(Dynamic<?> $$0, OptionalDynamic<?> $$1) {
/* 37 */     $$0 = $$0.remove("blending_data");
/* 38 */     boolean $$2 = "minecraft:overworld".equals($$1.get("dimension").asString().result().orElse(""));
/*    */     
/* 40 */     Optional<? extends Dynamic<?>> $$3 = $$0.get("Status").result();
/* 41 */     if ($$2 && $$3.isPresent()) {
/* 42 */       String $$4 = NamespacedSchema.ensureNamespaced(((Dynamic)$$3.get()).asString("empty"));
/* 43 */       Optional<? extends Dynamic<?>> $$5 = $$0.get("below_zero_retrogen").result();
/*    */       
/* 45 */       if (!STATUSES_TO_SKIP_BLENDING.contains($$4)) {
/*    */         
/* 47 */         $$0 = updateBlendingData($$0, 384, -64);
/* 48 */       } else if ($$5.isPresent()) {
/*    */         
/* 50 */         Dynamic<?> $$6 = $$5.get();
/* 51 */         String $$7 = NamespacedSchema.ensureNamespaced($$6.get("target_status").asString("empty"));
/* 52 */         if (!STATUSES_TO_SKIP_BLENDING.contains($$7)) {
/* 53 */           $$0 = updateBlendingData($$0, 256, 0);
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 58 */     return $$0;
/*    */   }
/*    */   
/*    */   private static Dynamic<?> updateBlendingData(Dynamic<?> $$0, int $$1, int $$2) {
/* 62 */     return $$0.set("blending_data", $$0.createMap(Map.of($$0
/* 63 */             .createString("min_section"), $$0.createInt(SectionPos.blockToSectionCoord($$2)), $$0
/* 64 */             .createString("max_section"), $$0.createInt(SectionPos.blockToSectionCoord($$2 + $$1)))));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\BlendingDataFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */