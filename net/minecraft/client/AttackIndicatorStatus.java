/*    */ package net.minecraft.client;
/*    */ 
/*    */ import java.util.function.IntFunction;
/*    */ import net.minecraft.util.ByIdMap;
/*    */ import net.minecraft.util.OptionEnum;
/*    */ 
/*    */ public enum AttackIndicatorStatus implements OptionEnum {
/*    */   private static final IntFunction<AttackIndicatorStatus> BY_ID;
/*  9 */   OFF(0, "options.off"),
/* 10 */   CROSSHAIR(1, "options.attack.crosshair"),
/* 11 */   HOTBAR(2, "options.attack.hotbar");
/*    */   
/*    */   static {
/* 14 */     BY_ID = ByIdMap.continuous(AttackIndicatorStatus::getId, (Object[])values(), ByIdMap.OutOfBoundsStrategy.WRAP);
/*    */   }
/*    */   
/*    */   private final int id;
/*    */   
/*    */   AttackIndicatorStatus(int $$0, String $$1) {
/* 20 */     this.id = $$0;
/* 21 */     this.key = $$1;
/*    */   }
/*    */   private final String key;
/*    */   
/*    */   public int getId() {
/* 26 */     return this.id;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getKey() {
/* 31 */     return this.key;
/*    */   }
/*    */   
/*    */   public static AttackIndicatorStatus byId(int $$0) {
/* 35 */     return BY_ID.apply($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\AttackIndicatorStatus.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */