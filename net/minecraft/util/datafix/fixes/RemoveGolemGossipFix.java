/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ 
/*    */ public class RemoveGolemGossipFix extends NamedEntityFix {
/*    */   public RemoveGolemGossipFix(Schema $$0, boolean $$1) {
/* 10 */     super($$0, $$1, "Remove Golem Gossip Fix", References.ENTITY, "minecraft:villager");
/*    */   }
/*    */ 
/*    */   
/*    */   protected Typed<?> fix(Typed<?> $$0) {
/* 15 */     return $$0.update(DSL.remainderFinder(), RemoveGolemGossipFix::fixValue);
/*    */   }
/*    */   
/*    */   private static Dynamic<?> fixValue(Dynamic<?> $$0) {
/* 19 */     return $$0.update("Gossips", $$1 -> $$0.createList($$1.asStream().filter(())));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\RemoveGolemGossipFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */