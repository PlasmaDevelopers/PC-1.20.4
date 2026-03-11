/*    */ package net.minecraft.client.resources.metadata.animation;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.Map;
/*    */ import java.util.stream.Collectors;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum Hat
/*    */ {
/* 13 */   NONE("none"), PARTIAL("partial"), FULL("full"); private final String name;
/*    */   static {
/* 15 */     BY_NAME = (Map<String, Hat>)Arrays.<Hat>stream(values()).collect(Collectors.toMap(Hat::getName, $$0 -> $$0));
/*    */   }
/*    */   private static final Map<String, Hat> BY_NAME;
/*    */   
/*    */   Hat(String $$0) {
/* 20 */     this.name = $$0;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 24 */     return this.name;
/*    */   }
/*    */   
/*    */   public static Hat getByName(String $$0) {
/* 28 */     return BY_NAME.getOrDefault($$0, NONE);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\metadata\animation\VillagerMetaDataSection$Hat.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */