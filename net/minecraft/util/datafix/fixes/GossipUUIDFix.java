/*    */ package net.minecraft.util.datafix.fixes;
/*    */ import com.mojang.datafixers.DataFixUtils;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import java.util.stream.Stream;
/*    */ 
/*    */ public class GossipUUIDFix extends NamedEntityFix {
/*    */   public GossipUUIDFix(Schema $$0, String $$1) {
/* 10 */     super($$0, false, "Gossip for for " + $$1, References.ENTITY, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Typed<?> fix(Typed<?> $$0) {
/* 15 */     return $$0.update(DSL.remainderFinder(), $$0 -> $$0.update("Gossips", ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\GossipUUIDFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */