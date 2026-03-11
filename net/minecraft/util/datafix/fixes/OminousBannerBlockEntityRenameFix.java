/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import java.util.Optional;
/*    */ 
/*    */ public class OminousBannerBlockEntityRenameFix
/*    */   extends NamedEntityFix {
/*    */   public OminousBannerBlockEntityRenameFix(Schema $$0, boolean $$1) {
/* 12 */     super($$0, $$1, "OminousBannerBlockEntityRenameFix", References.BLOCK_ENTITY, "minecraft:banner");
/*    */   }
/*    */ 
/*    */   
/*    */   protected Typed<?> fix(Typed<?> $$0) {
/* 17 */     return $$0.update(DSL.remainderFinder(), this::fixTag);
/*    */   }
/*    */   
/*    */   private Dynamic<?> fixTag(Dynamic<?> $$0) {
/* 21 */     Optional<String> $$1 = $$0.get("CustomName").asString().result();
/* 22 */     if ($$1.isPresent()) {
/* 23 */       String $$2 = $$1.get();
/* 24 */       $$2 = $$2.replace("\"translate\":\"block.minecraft.illager_banner\"", "\"translate\":\"block.minecraft.ominous_banner\"");
/* 25 */       return $$0.set("CustomName", $$0.createString($$2));
/*    */     } 
/* 27 */     return $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\OminousBannerBlockEntityRenameFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */