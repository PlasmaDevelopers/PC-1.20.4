/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import java.util.Objects;
/*    */ 
/*    */ public class EntityElderGuardianSplitFix
/*    */   extends SimpleEntityRenameFix {
/*    */   public EntityElderGuardianSplitFix(Schema $$0, boolean $$1) {
/* 11 */     super("EntityElderGuardianSplitFix", $$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Pair<String, Dynamic<?>> getNewNameAndTag(String $$0, Dynamic<?> $$1) {
/* 16 */     return Pair.of((Objects.equals($$0, "Guardian") && $$1.get("Elder").asBoolean(false)) ? "ElderGuardian" : $$0, $$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\EntityElderGuardianSplitFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */