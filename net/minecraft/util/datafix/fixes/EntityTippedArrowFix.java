/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import java.util.Objects;
/*    */ 
/*    */ public class EntityTippedArrowFix
/*    */   extends SimplestEntityRenameFix {
/*    */   public EntityTippedArrowFix(Schema $$0, boolean $$1) {
/*  9 */     super("EntityTippedArrowFix", $$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected String rename(String $$0) {
/* 14 */     return Objects.equals($$0, "TippedArrow") ? "Arrow" : $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\EntityTippedArrowFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */