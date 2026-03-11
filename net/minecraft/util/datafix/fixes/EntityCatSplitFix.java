/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import java.util.Objects;
/*    */ 
/*    */ public class EntityCatSplitFix
/*    */   extends SimpleEntityRenameFix {
/*    */   public EntityCatSplitFix(Schema $$0, boolean $$1) {
/* 11 */     super("EntityCatSplitFix", $$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Pair<String, Dynamic<?>> getNewNameAndTag(String $$0, Dynamic<?> $$1) {
/* 16 */     if (Objects.equals("minecraft:ocelot", $$0)) {
/* 17 */       int $$2 = $$1.get("CatType").asInt(0);
/* 18 */       if ($$2 == 0) {
/* 19 */         String $$3 = $$1.get("Owner").asString("");
/* 20 */         String $$4 = $$1.get("OwnerUUID").asString("");
/* 21 */         if ($$3.length() > 0 || $$4.length() > 0) {
/* 22 */           $$1.set("Trusting", $$1.createBoolean(true));
/*    */         }
/* 24 */       } else if ($$2 > 0 && $$2 < 4) {
/* 25 */         $$1 = $$1.set("CatType", $$1.createInt($$2));
/* 26 */         $$1 = $$1.set("OwnerUUID", $$1.createString($$1.get("OwnerUUID").asString("")));
/* 27 */         return Pair.of("minecraft:cat", $$1);
/*    */       } 
/*    */     } 
/*    */     
/* 31 */     return Pair.of($$0, $$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\EntityCatSplitFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */