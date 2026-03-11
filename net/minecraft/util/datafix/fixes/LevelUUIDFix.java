/*    */ package net.minecraft.util.datafix.fixes;
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import com.mojang.logging.LogUtils;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class LevelUUIDFix extends AbstractUUIDFix {
/* 12 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/*    */   public LevelUUIDFix(Schema $$0) {
/* 15 */     super($$0, References.LEVEL);
/*    */   }
/*    */ 
/*    */   
/*    */   protected TypeRewriteRule makeRule() {
/* 20 */     return fixTypeEverywhereTyped("LevelUUIDFix", getInputSchema().getType(this.typeReference), $$0 -> $$0.updateTyped(DSL.remainderFinder(), ()));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private Dynamic<?> updateWanderingTrader(Dynamic<?> $$0) {
/* 31 */     return replaceUUIDString($$0, "WanderingTraderId", "WanderingTraderId").orElse($$0);
/*    */   }
/*    */   
/*    */   private Dynamic<?> updateDragonFight(Dynamic<?> $$0) {
/* 35 */     return $$0.update("DimensionData", $$0 -> $$0.updateMapValues(()));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private Dynamic<?> updateCustomBossEvents(Dynamic<?> $$0) {
/* 45 */     return $$0.update("CustomBossEvents", $$0 -> $$0.updateMapValues(()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\LevelUUIDFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */