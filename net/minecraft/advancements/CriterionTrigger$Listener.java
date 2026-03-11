/*    */ package net.minecraft.advancements;
/*    */ 
/*    */ import net.minecraft.server.PlayerAdvancements;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Listener<T extends CriterionTriggerInstance>
/*    */   extends Record
/*    */ {
/*    */   private final T trigger;
/*    */   private final AdvancementHolder advancement;
/*    */   private final String criterion;
/*    */   
/*    */   public Listener(T $$0, AdvancementHolder $$1, String $$2) {
/* 19 */     this.trigger = $$0; this.advancement = $$1; this.criterion = $$2; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/CriterionTrigger$Listener;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #19	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/CriterionTrigger$Listener;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/CriterionTrigger$Listener<TT;>; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/CriterionTrigger$Listener;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #19	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/CriterionTrigger$Listener;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/CriterionTrigger$Listener<TT;>; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/CriterionTrigger$Listener;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #19	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/advancements/CriterionTrigger$Listener;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/* 19 */     //   0	8	0	this	Lnet/minecraft/advancements/CriterionTrigger$Listener<TT;>; } public T trigger() { return this.trigger; } public AdvancementHolder advancement() { return this.advancement; } public String criterion() { return this.criterion; }
/*    */    public void run(PlayerAdvancements $$0) {
/* 21 */     $$0.award(this.advancement, this.criterion);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\CriterionTrigger$Listener.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */