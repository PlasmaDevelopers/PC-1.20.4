/*    */ package net.minecraft.advancements;
/*    */ 
/*    */ import java.time.Instant;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ 
/*    */ 
/*    */ public class CriterionProgress
/*    */ {
/*    */   @Nullable
/*    */   private Instant obtained;
/*    */   
/*    */   public CriterionProgress() {}
/*    */   
/*    */   public CriterionProgress(Instant $$0) {
/* 16 */     this.obtained = $$0;
/*    */   }
/*    */   
/*    */   public boolean isDone() {
/* 20 */     return (this.obtained != null);
/*    */   }
/*    */   
/*    */   public void grant() {
/* 24 */     this.obtained = Instant.now();
/*    */   }
/*    */   
/*    */   public void revoke() {
/* 28 */     this.obtained = null;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public Instant getObtained() {
/* 33 */     return this.obtained;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 39 */     return "CriterionProgress{obtained=" + ((this.obtained == null) ? "false" : (String)this.obtained) + "}";
/*    */   }
/*    */ 
/*    */   
/*    */   public void serializeToNetwork(FriendlyByteBuf $$0) {
/* 44 */     $$0.writeNullable(this.obtained, FriendlyByteBuf::writeInstant);
/*    */   }
/*    */   
/*    */   public static CriterionProgress fromNetwork(FriendlyByteBuf $$0) {
/* 48 */     CriterionProgress $$1 = new CriterionProgress();
/* 49 */     $$1.obtained = (Instant)$$0.readNullable(FriendlyByteBuf::readInstant);
/* 50 */     return $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\CriterionProgress.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */