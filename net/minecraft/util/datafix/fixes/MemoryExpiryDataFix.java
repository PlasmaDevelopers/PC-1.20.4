/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import java.util.Map;
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
/*    */ public class MemoryExpiryDataFix
/*    */   extends NamedEntityFix
/*    */ {
/*    */   public MemoryExpiryDataFix(Schema $$0, String $$1) {
/* 30 */     super($$0, false, "Memory expiry data fix (" + $$1 + ")", References.ENTITY, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Typed<?> fix(Typed<?> $$0) {
/* 35 */     return $$0.update(DSL.remainderFinder(), this::fixTag);
/*    */   }
/*    */   
/*    */   public Dynamic<?> fixTag(Dynamic<?> $$0) {
/* 39 */     return $$0.update("Brain", this::updateBrain);
/*    */   }
/*    */   
/*    */   private Dynamic<?> updateBrain(Dynamic<?> $$0) {
/* 43 */     return $$0.update("memories", this::updateMemories);
/*    */   }
/*    */   
/*    */   private Dynamic<?> updateMemories(Dynamic<?> $$0) {
/* 47 */     return $$0.updateMapValues(this::updateMemoryEntry);
/*    */   }
/*    */   
/*    */   private Pair<Dynamic<?>, Dynamic<?>> updateMemoryEntry(Pair<Dynamic<?>, Dynamic<?>> $$0) {
/* 51 */     return $$0.mapSecond(this::wrapMemoryValue);
/*    */   }
/*    */   
/*    */   private Dynamic<?> wrapMemoryValue(Dynamic<?> $$0) {
/* 55 */     return $$0.createMap((Map)ImmutableMap.of($$0
/* 56 */           .createString("value"), $$0));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\MemoryExpiryDataFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */