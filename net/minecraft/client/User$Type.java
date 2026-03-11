/*    */ package net.minecraft.client;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.Locale;
/*    */ import java.util.Map;
/*    */ import java.util.function.Function;
/*    */ import java.util.stream.Collectors;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum Type
/*    */ {
/* 60 */   LEGACY("legacy"),
/* 61 */   MOJANG("mojang"),
/* 62 */   MSA("msa"); private static final Map<String, Type> BY_NAME;
/*    */   
/*    */   static {
/* 65 */     BY_NAME = (Map<String, Type>)Arrays.<Type>stream(values()).collect(Collectors.toMap($$0 -> $$0.name, Function.identity()));
/*    */   }
/*    */   private final String name;
/*    */   
/*    */   Type(String $$0) {
/* 70 */     this.name = $$0;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public static Type byName(String $$0) {
/* 75 */     return BY_NAME.get($$0.toLowerCase(Locale.ROOT));
/*    */   }
/*    */   
/*    */   public String getName() {
/* 79 */     return this.name;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\User$Type.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */