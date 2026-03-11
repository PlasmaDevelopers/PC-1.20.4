/*    */ package net.minecraft.advancements.critereon;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function4;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Optional;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ import net.minecraft.world.effect.MobEffectInstance;
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
/*    */ public final class MobEffectInstancePredicate
/*    */   extends Record
/*    */ {
/*    */   private final MinMaxBounds.Ints amplifier;
/*    */   private final MinMaxBounds.Ints duration;
/*    */   private final Optional<Boolean> ambient;
/*    */   private final Optional<Boolean> visible;
/*    */   public static final Codec<MobEffectInstancePredicate> CODEC;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/MobEffectsPredicate$MobEffectInstancePredicate;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #64	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/MobEffectsPredicate$MobEffectInstancePredicate;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/MobEffectsPredicate$MobEffectInstancePredicate;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #64	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/MobEffectsPredicate$MobEffectInstancePredicate;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/MobEffectsPredicate$MobEffectInstancePredicate;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #64	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/advancements/critereon/MobEffectsPredicate$MobEffectInstancePredicate;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   public MobEffectInstancePredicate(MinMaxBounds.Ints $$0, MinMaxBounds.Ints $$1, Optional<Boolean> $$2, Optional<Boolean> $$3) {
/* 64 */     this.amplifier = $$0; this.duration = $$1; this.ambient = $$2; this.visible = $$3; } public MinMaxBounds.Ints amplifier() { return this.amplifier; } public MinMaxBounds.Ints duration() { return this.duration; } public Optional<Boolean> ambient() { return this.ambient; } public Optional<Boolean> visible() { return this.visible; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 70 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField(MinMaxBounds.Ints.CODEC, "amplifier", MinMaxBounds.Ints.ANY).forGetter(MobEffectInstancePredicate::amplifier), (App)ExtraCodecs.strictOptionalField(MinMaxBounds.Ints.CODEC, "duration", MinMaxBounds.Ints.ANY).forGetter(MobEffectInstancePredicate::duration), (App)ExtraCodecs.strictOptionalField((Codec)Codec.BOOL, "ambient").forGetter(MobEffectInstancePredicate::ambient), (App)ExtraCodecs.strictOptionalField((Codec)Codec.BOOL, "visible").forGetter(MobEffectInstancePredicate::visible)).apply((Applicative)$$0, MobEffectInstancePredicate::new));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MobEffectInstancePredicate() {
/* 78 */     this(MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, Optional.empty(), Optional.empty());
/*    */   }
/*    */   
/*    */   public boolean matches(@Nullable MobEffectInstance $$0) {
/* 82 */     if ($$0 == null) {
/* 83 */       return false;
/*    */     }
/* 85 */     if (!this.amplifier.matches($$0.getAmplifier())) {
/* 86 */       return false;
/*    */     }
/* 88 */     if (!this.duration.matches($$0.getDuration())) {
/* 89 */       return false;
/*    */     }
/* 91 */     if (this.ambient.isPresent() && ((Boolean)this.ambient.get()).booleanValue() != $$0.isAmbient()) {
/* 92 */       return false;
/*    */     }
/* 94 */     if (this.visible.isPresent() && ((Boolean)this.visible.get()).booleanValue() != $$0.isVisible()) {
/* 95 */       return false;
/*    */     }
/* 97 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\MobEffectsPredicate$MobEffectInstancePredicate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */