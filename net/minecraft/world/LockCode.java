/*    */ package net.minecraft.world;
/*    */ 
/*    */ import javax.annotation.concurrent.Immutable;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ 
/*    */ @Immutable
/*    */ public class LockCode
/*    */ {
/* 11 */   public static final LockCode NO_LOCK = new LockCode("");
/*    */   
/*    */   public static final String TAG_LOCK = "Lock";
/*    */   private final String key;
/*    */   
/*    */   public LockCode(String $$0) {
/* 17 */     this.key = $$0;
/*    */   }
/*    */   
/*    */   public boolean unlocksWith(ItemStack $$0) {
/* 21 */     return (this.key.isEmpty() || (!$$0.isEmpty() && $$0.hasCustomHoverName() && this.key.equals($$0.getHoverName().getString())));
/*    */   }
/*    */   
/*    */   public void addToTag(CompoundTag $$0) {
/* 25 */     if (!this.key.isEmpty()) {
/* 26 */       $$0.putString("Lock", this.key);
/*    */     }
/*    */   }
/*    */   
/*    */   public static LockCode fromTag(CompoundTag $$0) {
/* 31 */     if ($$0.contains("Lock", 8)) {
/* 32 */       return new LockCode($$0.getString("Lock"));
/*    */     }
/* 34 */     return NO_LOCK;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\LockCode.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */