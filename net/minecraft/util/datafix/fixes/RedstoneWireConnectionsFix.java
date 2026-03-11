/*    */ package net.minecraft.util.datafix.fixes;
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ 
/*    */ public class RedstoneWireConnectionsFix extends DataFix {
/*    */   public RedstoneWireConnectionsFix(Schema $$0) {
/* 11 */     super($$0, false);
/*    */   }
/*    */ 
/*    */   
/*    */   protected TypeRewriteRule makeRule() {
/* 16 */     Schema $$0 = getInputSchema();
/* 17 */     return fixTypeEverywhereTyped("RedstoneConnectionsFix", $$0.getType(References.BLOCK_STATE), $$0 -> $$0.update(DSL.remainderFinder(), this::updateRedstoneConnections));
/*    */   }
/*    */   
/*    */   private <T> Dynamic<T> updateRedstoneConnections(Dynamic<T> $$0) {
/* 21 */     boolean $$1 = $$0.get("Name").asString().result().filter("minecraft:redstone_wire"::equals).isPresent();
/* 22 */     if (!$$1) {
/* 23 */       return $$0;
/*    */     }
/*    */     
/* 26 */     return $$0.update("Properties", $$0 -> {
/*    */           String $$1 = $$0.get("east").asString("none");
/*    */           String $$2 = $$0.get("west").asString("none");
/*    */           String $$3 = $$0.get("north").asString("none");
/*    */           String $$4 = $$0.get("south").asString("none");
/* 31 */           boolean $$5 = (isConnected($$1) || isConnected($$2));
/* 32 */           boolean $$6 = (isConnected($$3) || isConnected($$4));
/*    */           
/* 34 */           String $$7 = (!isConnected($$1) && !$$6) ? "side" : $$1;
/* 35 */           String $$8 = (!isConnected($$2) && !$$6) ? "side" : $$2;
/* 36 */           String $$9 = (!isConnected($$3) && !$$5) ? "side" : $$3;
/* 37 */           String $$10 = (!isConnected($$4) && !$$5) ? "side" : $$4;
/*    */           return $$0.update("east", ()).update("west", ()).update("north", ()).update("south", ());
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static boolean isConnected(String $$0) {
/* 48 */     return !"none".equals($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\RedstoneWireConnectionsFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */