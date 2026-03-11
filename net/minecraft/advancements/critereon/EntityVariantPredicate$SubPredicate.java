/*    */ package net.minecraft.advancements.critereon;
/*    */ 
/*    */ import java.util.Optional;
/*    */ import java.util.function.Function;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.phys.Vec3;
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
/*    */ public final class SubPredicate<V>
/*    */   extends Record
/*    */   implements EntitySubPredicate
/*    */ {
/*    */   private final EntitySubPredicate.Type type;
/*    */   private final Function<Entity, Optional<V>> getter;
/*    */   private final V variant;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/EntityVariantPredicate$SubPredicate;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #44	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/EntityVariantPredicate$SubPredicate;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/EntityVariantPredicate$SubPredicate<TV;>;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/EntityVariantPredicate$SubPredicate;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #44	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/EntityVariantPredicate$SubPredicate;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/EntityVariantPredicate$SubPredicate<TV;>;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/EntityVariantPredicate$SubPredicate;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #44	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/advancements/critereon/EntityVariantPredicate$SubPredicate;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	8	0	this	Lnet/minecraft/advancements/critereon/EntityVariantPredicate$SubPredicate<TV;>;
/*    */   }
/*    */   
/*    */   public SubPredicate(EntitySubPredicate.Type $$0, Function<Entity, Optional<V>> $$1, V $$2) {
/* 44 */     this.type = $$0; this.getter = $$1; this.variant = $$2; } public EntitySubPredicate.Type type() { return this.type; } public Function<Entity, Optional<V>> getter() { return this.getter; } public V variant() { return this.variant; }
/*    */   
/*    */   public boolean matches(Entity $$0, ServerLevel $$1, @Nullable Vec3 $$2) {
/* 47 */     return ((Optional)this.getter.apply($$0)).filter($$0 -> $$0.equals(this.variant)).isPresent();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\EntityVariantPredicate$SubPredicate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */