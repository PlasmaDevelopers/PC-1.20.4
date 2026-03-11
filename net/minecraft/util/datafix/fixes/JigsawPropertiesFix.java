/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ 
/*    */ public class JigsawPropertiesFix extends NamedEntityFix {
/*    */   public JigsawPropertiesFix(Schema $$0, boolean $$1) {
/* 10 */     super($$0, $$1, "JigsawPropertiesFix", References.BLOCK_ENTITY, "minecraft:jigsaw");
/*    */   }
/*    */   
/*    */   private static Dynamic<?> fixTag(Dynamic<?> $$0) {
/* 14 */     String $$1 = $$0.get("attachement_type").asString("minecraft:empty");
/* 15 */     String $$2 = $$0.get("target_pool").asString("minecraft:empty");
/* 16 */     return $$0
/* 17 */       .set("name", $$0.createString($$1))
/* 18 */       .set("target", $$0.createString($$1))
/* 19 */       .remove("attachement_type")
/* 20 */       .set("pool", $$0.createString($$2))
/* 21 */       .remove("target_pool");
/*    */   }
/*    */ 
/*    */   
/*    */   protected Typed<?> fix(Typed<?> $$0) {
/* 26 */     return $$0.update(DSL.remainderFinder(), JigsawPropertiesFix::fixTag);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\JigsawPropertiesFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */