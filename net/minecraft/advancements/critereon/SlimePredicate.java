/*    */ package net.minecraft.advancements.critereon;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.monster.Slime;
/*    */ 
/*    */ public final class SlimePredicate extends Record implements EntitySubPredicate {
/*    */   private final MinMaxBounds.Ints size;
/*    */   public static final MapCodec<SlimePredicate> CODEC;
/*    */   
/* 13 */   public SlimePredicate(MinMaxBounds.Ints $$0) { this.size = $$0; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/SlimePredicate;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #13	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 13 */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/SlimePredicate; } public MinMaxBounds.Ints size() { return this.size; }
/*    */   public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/SlimePredicate;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #13	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/SlimePredicate; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/SlimePredicate;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #13	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/advancements/critereon/SlimePredicate;
/* 14 */     //   0	8	1	$$0	Ljava/lang/Object; } static { CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField(MinMaxBounds.Ints.CODEC, "size", MinMaxBounds.Ints.ANY).forGetter(SlimePredicate::size)).apply((Applicative)$$0, SlimePredicate::new)); }
/*    */ 
/*    */ 
/*    */   
/*    */   public static SlimePredicate sized(MinMaxBounds.Ints $$0) {
/* 19 */     return new SlimePredicate($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(Entity $$0, ServerLevel $$1, @Nullable Vec3 $$2) {
/* 24 */     if ($$0 instanceof Slime) { Slime $$3 = (Slime)$$0;
/* 25 */       return this.size.matches($$3.getSize()); }
/*    */     
/* 27 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public EntitySubPredicate.Type type() {
/* 32 */     return EntitySubPredicate.Types.SLIME;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\SlimePredicate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */