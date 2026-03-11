/*    */ package net.minecraft.world.entity.ai.behavior.declarative;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.OptionalBox;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.world.entity.ai.Brain;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryStatus;
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
/*    */ public final class Registered<Value>
/*    */   extends Record
/*    */   implements MemoryCondition<OptionalBox.Mu, Value>
/*    */ {
/*    */   private final MemoryModuleType<Value> memory;
/*    */   
/*    */   public Registered(MemoryModuleType<Value> $$0) {
/* 28 */     this.memory = $$0; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/entity/ai/behavior/declarative/MemoryCondition$Registered;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #28	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/entity/ai/behavior/declarative/MemoryCondition$Registered;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/* 28 */     //   0	7	0	this	Lnet/minecraft/world/entity/ai/behavior/declarative/MemoryCondition$Registered<TValue;>; } public MemoryModuleType<Value> memory() { return this.memory; }
/*    */   public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/entity/ai/behavior/declarative/MemoryCondition$Registered;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #28	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/entity/ai/behavior/declarative/MemoryCondition$Registered;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	7	0	this	Lnet/minecraft/world/entity/ai/behavior/declarative/MemoryCondition$Registered<TValue;>; }
/*    */   public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/entity/ai/behavior/declarative/MemoryCondition$Registered;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #28	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/entity/ai/behavior/declarative/MemoryCondition$Registered;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	8	0	this	Lnet/minecraft/world/entity/ai/behavior/declarative/MemoryCondition$Registered<TValue;>; } public MemoryStatus condition() {
/* 31 */     return MemoryStatus.REGISTERED;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MemoryAccessor<OptionalBox.Mu, Value> createAccessor(Brain<?> $$0, Optional<Value> $$1) {
/* 39 */     return new MemoryAccessor<>($$0, this.memory, (App<OptionalBox.Mu, Value>)OptionalBox.create($$1));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\declarative\MemoryCondition$Registered.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */