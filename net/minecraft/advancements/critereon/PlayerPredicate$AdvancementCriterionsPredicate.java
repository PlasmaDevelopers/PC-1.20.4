/*    */ package net.minecraft.advancements.critereon;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.advancements.AdvancementProgress;
/*    */ import net.minecraft.advancements.CriterionProgress;
/*    */ import net.minecraft.util.ExtraCodecs;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class AdvancementCriterionsPredicate
/*    */   extends Record
/*    */   implements PlayerPredicate.AdvancementPredicate
/*    */ {
/*    */   private final Object2BooleanMap<String> criterions;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/PlayerPredicate$AdvancementCriterionsPredicate;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #85	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/PlayerPredicate$AdvancementCriterionsPredicate;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/PlayerPredicate$AdvancementCriterionsPredicate;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #85	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/PlayerPredicate$AdvancementCriterionsPredicate;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/PlayerPredicate$AdvancementCriterionsPredicate;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #85	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/advancements/critereon/PlayerPredicate$AdvancementCriterionsPredicate;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   AdvancementCriterionsPredicate(Object2BooleanMap<String> $$0) {
/* 85 */     this.criterions = $$0; } public Object2BooleanMap<String> criterions() { return this.criterions; }
/* 86 */    public static final Codec<AdvancementCriterionsPredicate> CODEC = ExtraCodecs.object2BooleanMap((Codec)Codec.STRING).xmap(AdvancementCriterionsPredicate::new, AdvancementCriterionsPredicate::criterions);
/*    */ 
/*    */   
/*    */   public boolean test(AdvancementProgress $$0) {
/* 90 */     for (ObjectIterator<Object2BooleanMap.Entry<String>> objectIterator = this.criterions.object2BooleanEntrySet().iterator(); objectIterator.hasNext(); ) { Object2BooleanMap.Entry<String> $$1 = objectIterator.next();
/* 91 */       CriterionProgress $$2 = $$0.getCriterion((String)$$1.getKey());
/* 92 */       if ($$2 == null || $$2.isDone() != $$1.getBooleanValue()) {
/* 93 */         return false;
/*    */       } }
/*    */     
/* 96 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\PlayerPredicate$AdvancementCriterionsPredicate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */