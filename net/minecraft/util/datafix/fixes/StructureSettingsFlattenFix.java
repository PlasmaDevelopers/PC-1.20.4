/*    */ package net.minecraft.util.datafix.fixes;
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.DataFixUtils;
/*    */ import com.mojang.datafixers.OpticFinder;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.Type;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import net.minecraft.Util;
/*    */ 
/*    */ public class StructureSettingsFlattenFix extends DataFix {
/*    */   public StructureSettingsFlattenFix(Schema $$0) {
/* 15 */     super($$0, false);
/*    */   }
/*    */ 
/*    */   
/*    */   protected TypeRewriteRule makeRule() {
/* 20 */     Type<?> $$0 = getInputSchema().getType(References.WORLD_GEN_SETTINGS);
/*    */     
/* 22 */     OpticFinder<?> $$1 = $$0.findField("dimensions");
/*    */     
/* 24 */     return fixTypeEverywhereTyped("StructureSettingsFlatten", $$0, $$1 -> $$1.updateTyped($$0, ()));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static Pair<Dynamic<?>, Dynamic<?>> fixDimension(Pair<Dynamic<?>, Dynamic<?>> $$0) {
/* 32 */     Dynamic<?> $$1 = (Dynamic)$$0.getSecond();
/* 33 */     return Pair.of($$0.getFirst(), $$1
/* 34 */         .update("generator", $$0 -> $$0.update("settings", ())));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static Dynamic<?> fixStructures(Dynamic<?> $$0) {
/* 42 */     Dynamic<?> $$1 = $$0.get("structures").orElseEmptyMap().updateMapValues($$1 -> $$1.mapSecond(()));
/*    */ 
/*    */     
/* 45 */     return (Dynamic)DataFixUtils.orElse($$0
/* 46 */         .get("stronghold").result().map($$2 -> $$0.set("minecraft:stronghold", $$2.set("type", $$1.createString("minecraft:concentric_rings")))), $$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\StructureSettingsFlattenFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */