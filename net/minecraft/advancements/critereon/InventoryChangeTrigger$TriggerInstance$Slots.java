/*     */ package net.minecraft.advancements.critereon;
/*     */ 
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function3;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Slots
/*     */   extends Record
/*     */ {
/*     */   private final MinMaxBounds.Ints occupied;
/*     */   private final MinMaxBounds.Ints full;
/*     */   private final MinMaxBounds.Ints empty;
/*     */   public static final Codec<Slots> CODEC;
/*     */   
/*     */   public final String toString() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/InventoryChangeTrigger$TriggerInstance$Slots;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #106	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/InventoryChangeTrigger$TriggerInstance$Slots;
/*     */   }
/*     */   
/*     */   public final int hashCode() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/InventoryChangeTrigger$TriggerInstance$Slots;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #106	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/InventoryChangeTrigger$TriggerInstance$Slots;
/*     */   }
/*     */   
/*     */   public final boolean equals(Object $$0) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/InventoryChangeTrigger$TriggerInstance$Slots;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #106	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lnet/minecraft/advancements/critereon/InventoryChangeTrigger$TriggerInstance$Slots;
/*     */     //   0	8	1	$$0	Ljava/lang/Object;
/*     */   }
/*     */   
/*     */   public Slots(MinMaxBounds.Ints $$0, MinMaxBounds.Ints $$1, MinMaxBounds.Ints $$2) {
/* 106 */     this.occupied = $$0; this.full = $$1; this.empty = $$2; } public MinMaxBounds.Ints occupied() { return this.occupied; } public MinMaxBounds.Ints full() { return this.full; } public MinMaxBounds.Ints empty() { return this.empty; }
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 111 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField(MinMaxBounds.Ints.CODEC, "occupied", MinMaxBounds.Ints.ANY).forGetter(Slots::occupied), (App)ExtraCodecs.strictOptionalField(MinMaxBounds.Ints.CODEC, "full", MinMaxBounds.Ints.ANY).forGetter(Slots::full), (App)ExtraCodecs.strictOptionalField(MinMaxBounds.Ints.CODEC, "empty", MinMaxBounds.Ints.ANY).forGetter(Slots::empty)).apply((Applicative)$$0, Slots::new));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 117 */   public static final Slots ANY = new Slots(MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY);
/*     */   
/*     */   public boolean matches(int $$0, int $$1, int $$2) {
/* 120 */     if (!this.full.matches($$0)) {
/* 121 */       return false;
/*     */     }
/* 123 */     if (!this.empty.matches($$1)) {
/* 124 */       return false;
/*     */     }
/* 126 */     if (!this.occupied.matches($$2)) {
/* 127 */       return false;
/*     */     }
/* 129 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\InventoryChangeTrigger$TriggerInstance$Slots.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */