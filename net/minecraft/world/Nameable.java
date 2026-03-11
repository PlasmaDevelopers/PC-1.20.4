/*    */ package net.minecraft.world;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ public interface Nameable
/*    */ {
/*    */   Component getName();
/*    */   
/*    */   default boolean hasCustomName() {
/* 11 */     return (getCustomName() != null);
/*    */   }
/*    */   
/*    */   default Component getDisplayName() {
/* 15 */     return getName();
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   default Component getCustomName() {
/* 20 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\Nameable.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */