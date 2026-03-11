/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import java.util.Optional;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OminousBannerRenameFix
/*    */   extends ItemStackTagFix
/*    */ {
/*    */   public OminousBannerRenameFix(Schema $$0) {
/* 13 */     super($$0, "OminousBannerRenameFix", $$0 -> $$0.equals("minecraft:white_banner"));
/*    */   }
/*    */ 
/*    */   
/*    */   protected <T> Dynamic<T> fixItemStackTag(Dynamic<T> $$0) {
/* 18 */     Optional<? extends Dynamic<?>> $$1 = $$0.get("display").result();
/* 19 */     if ($$1.isPresent()) {
/* 20 */       Dynamic<?> $$2 = $$1.get();
/* 21 */       Optional<String> $$3 = $$2.get("Name").asString().result();
/* 22 */       if ($$3.isPresent()) {
/* 23 */         String $$4 = $$3.get();
/* 24 */         $$4 = $$4.replace("\"translate\":\"block.minecraft.illager_banner\"", "\"translate\":\"block.minecraft.ominous_banner\"");
/* 25 */         $$2 = $$2.set("Name", $$2.createString($$4));
/*    */       } 
/* 27 */       return $$0.set("display", $$2);
/*    */     } 
/* 29 */     return $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\OminousBannerRenameFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */