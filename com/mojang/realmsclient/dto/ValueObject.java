/*    */ package com.mojang.realmsclient.dto;
/*    */ 
/*    */ import com.google.gson.annotations.SerializedName;
/*    */ import java.lang.reflect.Field;
/*    */ import java.lang.reflect.Modifier;
/*    */ 
/*    */ 
/*    */ public abstract class ValueObject
/*    */ {
/*    */   public String toString() {
/* 11 */     StringBuilder $$0 = new StringBuilder("{");
/* 12 */     for (Field $$1 : getClass().getFields()) {
/* 13 */       if (!isStatic($$1)) {
/*    */         try {
/* 15 */           $$0.append(getName($$1)).append("=").append($$1.get(this)).append(" ");
/* 16 */         } catch (IllegalAccessException illegalAccessException) {}
/*    */       }
/*    */     } 
/*    */     
/* 20 */     $$0.deleteCharAt($$0.length() - 1);
/* 21 */     $$0.append('}');
/* 22 */     return $$0.toString();
/*    */   }
/*    */   
/*    */   private static String getName(Field $$0) {
/* 26 */     SerializedName $$1 = $$0.<SerializedName>getAnnotation(SerializedName.class);
/* 27 */     return ($$1 != null) ? $$1.value() : $$0.getName();
/*    */   }
/*    */   
/*    */   private static boolean isStatic(Field $$0) {
/* 31 */     return Modifier.isStatic($$0.getModifiers());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\dto\ValueObject.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */