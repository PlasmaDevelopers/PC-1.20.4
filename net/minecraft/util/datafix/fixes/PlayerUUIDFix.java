/*    */ package net.minecraft.util.datafix.fixes;
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.OpticFinder;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ 
/*    */ public class PlayerUUIDFix extends AbstractUUIDFix {
/*    */   public PlayerUUIDFix(Schema $$0) {
/* 11 */     super($$0, References.PLAYER);
/*    */   }
/*    */ 
/*    */   
/*    */   protected TypeRewriteRule makeRule() {
/* 16 */     return fixTypeEverywhereTyped("PlayerUUIDFix", getInputSchema().getType(this.typeReference), $$0 -> {
/*    */           OpticFinder<?> $$1 = $$0.getType().findField("RootVehicle");
/*    */           return $$0.updateTyped($$1, $$1.type(), ()).update(DSL.remainderFinder(), ());
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\PlayerUUIDFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */