/*    */ package net.minecraft.client;
/*    */ 
/*    */ import java.util.function.IntFunction;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.util.ByIdMap;
/*    */ 
/*    */ public enum NarratorStatus {
/*    */   private static final IntFunction<NarratorStatus> BY_ID;
/*  9 */   OFF(0, "options.narrator.off"),
/* 10 */   ALL(1, "options.narrator.all"),
/* 11 */   CHAT(2, "options.narrator.chat"),
/* 12 */   SYSTEM(3, "options.narrator.system");
/*    */   
/*    */   static {
/* 15 */     BY_ID = ByIdMap.continuous(NarratorStatus::getId, (Object[])values(), ByIdMap.OutOfBoundsStrategy.WRAP);
/*    */   }
/*    */   
/*    */   private final int id;
/*    */   
/*    */   NarratorStatus(int $$0, String $$1) {
/* 21 */     this.id = $$0;
/* 22 */     this.name = (Component)Component.translatable($$1);
/*    */   }
/*    */   private final Component name;
/*    */   public int getId() {
/* 26 */     return this.id;
/*    */   }
/*    */   
/*    */   public Component getName() {
/* 30 */     return this.name;
/*    */   }
/*    */   
/*    */   public static NarratorStatus byId(int $$0) {
/* 34 */     return BY_ID.apply($$0);
/*    */   }
/*    */   
/*    */   public boolean shouldNarrateChat() {
/* 38 */     return (this == ALL || this == CHAT);
/*    */   }
/*    */   
/*    */   public boolean shouldNarrateSystem() {
/* 42 */     return (this == ALL || this == SYSTEM);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\NarratorStatus.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */