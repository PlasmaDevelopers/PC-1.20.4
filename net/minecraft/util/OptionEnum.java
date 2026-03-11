/*    */ package net.minecraft.util;
/*    */ 
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ public interface OptionEnum {
/*    */   int getId();
/*    */   
/*    */   String getKey();
/*    */   
/*    */   default Component getCaption() {
/* 11 */     return (Component)Component.translatable(getKey());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\OptionEnum.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */