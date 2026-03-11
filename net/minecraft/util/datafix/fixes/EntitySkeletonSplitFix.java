/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import java.util.Objects;
/*    */ 
/*    */ public class EntitySkeletonSplitFix
/*    */   extends SimpleEntityRenameFix {
/*    */   public EntitySkeletonSplitFix(Schema $$0, boolean $$1) {
/* 11 */     super("EntitySkeletonSplitFix", $$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Pair<String, Dynamic<?>> getNewNameAndTag(String $$0, Dynamic<?> $$1) {
/* 16 */     if (Objects.equals($$0, "Skeleton")) {
/* 17 */       int $$2 = $$1.get("SkeletonType").asInt(0);
/* 18 */       if ($$2 == 1) {
/* 19 */         $$0 = "WitherSkeleton";
/* 20 */       } else if ($$2 == 2) {
/* 21 */         $$0 = "Stray";
/*    */       } 
/*    */     } 
/* 24 */     return Pair.of($$0, $$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\EntitySkeletonSplitFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */