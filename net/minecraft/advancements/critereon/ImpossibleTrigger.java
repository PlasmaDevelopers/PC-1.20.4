/*    */ package net.minecraft.advancements.critereon;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.advancements.CriterionTrigger;
/*    */ import net.minecraft.advancements.CriterionTriggerInstance;
/*    */ import net.minecraft.server.PlayerAdvancements;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ImpossibleTrigger
/*    */   implements CriterionTrigger<ImpossibleTrigger.TriggerInstance>
/*    */ {
/*    */   public void addPlayerListener(PlayerAdvancements $$0, CriterionTrigger.Listener<TriggerInstance> $$1) {}
/*    */   
/*    */   public void removePlayerListener(PlayerAdvancements $$0, CriterionTrigger.Listener<TriggerInstance> $$1) {}
/*    */   
/*    */   public void removePlayerListeners(PlayerAdvancements $$0) {}
/*    */   
/*    */   public Codec<TriggerInstance> codec() {
/* 23 */     return TriggerInstance.CODEC;
/*    */   }
/*    */   
/*    */   public static final class TriggerInstance extends Record implements CriterionTriggerInstance {
/* 27 */     public static final Codec<TriggerInstance> CODEC = Codec.unit(new TriggerInstance());
/*    */     
/*    */     public final boolean equals(Object $$0) {
/*    */       // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/ImpossibleTrigger$TriggerInstance;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #26	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/advancements/critereon/ImpossibleTrigger$TriggerInstance;
/*    */       //   0	8	1	$$0	Ljava/lang/Object;
/*    */     }
/*    */     
/*    */     public final int hashCode() {
/*    */       // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/ImpossibleTrigger$TriggerInstance;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #26	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/ImpossibleTrigger$TriggerInstance;
/*    */     }
/*    */     
/*    */     public final String toString() {
/*    */       // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/ImpossibleTrigger$TriggerInstance;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #26	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/ImpossibleTrigger$TriggerInstance;
/*    */     }
/*    */     
/*    */     public void validate(CriterionValidator $$0) {}
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\ImpossibleTrigger.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */