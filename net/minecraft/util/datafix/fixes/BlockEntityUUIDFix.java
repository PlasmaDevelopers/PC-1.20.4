/*    */ package net.minecraft.util.datafix.fixes;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ 
/*    */ public class BlockEntityUUIDFix extends AbstractUUIDFix {
/*    */   public BlockEntityUUIDFix(Schema $$0) {
/*  9 */     super($$0, References.BLOCK_ENTITY);
/*    */   }
/*    */ 
/*    */   
/*    */   protected TypeRewriteRule makeRule() {
/* 14 */     return fixTypeEverywhereTyped("BlockEntityUUIDFix", getInputSchema().getType(this.typeReference), $$0 -> {
/*    */           $$0 = updateNamedChoice($$0, "minecraft:conduit", this::updateConduit);
/*    */           return updateNamedChoice($$0, "minecraft:skull", this::updateSkull);
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   private Dynamic<?> updateSkull(Dynamic<?> $$0) {
/* 22 */     return $$0.get("Owner").get().map($$0 -> (Dynamic)replaceUUIDString($$0, "Id", "Id").orElse($$0))
/*    */       
/* 24 */       .map($$1 -> $$0.remove("Owner").set("SkullOwner", $$1))
/*    */       
/* 26 */       .result().orElse($$0);
/*    */   }
/*    */   
/*    */   private Dynamic<?> updateConduit(Dynamic<?> $$0) {
/* 30 */     return replaceUUIDMLTag($$0, "target_uuid", "Target").orElse($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\BlockEntityUUIDFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */