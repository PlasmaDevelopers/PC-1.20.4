/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.OpticFinder;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.Type;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import com.mojang.serialization.OptionalDynamic;
/*    */ import java.util.Map;
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.Util;
/*    */ import org.apache.commons.lang3.mutable.MutableBoolean;
/*    */ 
/*    */ public class WorldGenSettingsHeightAndBiomeFix extends DataFix {
/*    */   private static final String NAME = "WorldGenSettingsHeightAndBiomeFix";
/*    */   
/*    */   public WorldGenSettingsHeightAndBiomeFix(Schema $$0) {
/* 22 */     super($$0, true);
/*    */   }
/*    */   public static final String WAS_PREVIOUSLY_INCREASED_KEY = "has_increased_height_already";
/*    */   
/*    */   protected TypeRewriteRule makeRule() {
/* 27 */     Type<?> $$0 = getInputSchema().getType(References.WORLD_GEN_SETTINGS);
/* 28 */     OpticFinder<?> $$1 = $$0.findField("dimensions");
/*    */     
/* 30 */     Type<?> $$2 = getOutputSchema().getType(References.WORLD_GEN_SETTINGS);
/* 31 */     Type<?> $$3 = $$2.findFieldType("dimensions");
/*    */     
/* 33 */     return fixTypeEverywhereTyped("WorldGenSettingsHeightAndBiomeFix", $$0, $$2, $$2 -> {
/*    */           OptionalDynamic<?> $$3 = ((Dynamic)$$2.get(DSL.remainderFinder())).get("has_increased_height_already");
/*    */           boolean $$4 = $$3.result().isEmpty();
/*    */           boolean $$5 = $$3.asBoolean(true);
/*    */           return $$2.update(DSL.remainderFinder(), ()).updateTyped($$0, $$1, ());
/*    */         });
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
/*    */   private static Dynamic<?> updateLayers(Dynamic<?> $$0) {
/* 83 */     Dynamic<?> $$1 = $$0.createMap((Map)ImmutableMap.of($$0
/* 84 */           .createString("height"), $$0
/* 85 */           .createInt(64), $$0
/* 86 */           .createString("block"), $$0
/* 87 */           .createString("minecraft:air")));
/*    */     
/* 89 */     return $$0.createList(Stream.concat(Stream.of($$1), $$0.asStream()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\WorldGenSettingsHeightAndBiomeFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */