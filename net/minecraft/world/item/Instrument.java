/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ public final class Instrument extends Record {
/*    */   private final Holder<SoundEvent> soundEvent;
/*    */   private final int useDuration;
/*    */   private final float range;
/*    */   public static final Codec<Instrument> CODEC;
/*    */   
/*  9 */   public Instrument(Holder<SoundEvent> $$0, int $$1, float $$2) { this.soundEvent = $$0; this.useDuration = $$1; this.range = $$2; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/item/Instrument;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  9 */     //   0	7	0	this	Lnet/minecraft/world/item/Instrument; } public Holder<SoundEvent> soundEvent() { return this.soundEvent; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/item/Instrument;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/item/Instrument; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/item/Instrument;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/item/Instrument;
/*  9 */     //   0	8	1	$$0	Ljava/lang/Object; } public int useDuration() { return this.useDuration; } public float range() { return this.range; } static {
/* 10 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)SoundEvent.CODEC.fieldOf("sound_event").forGetter(Instrument::soundEvent), (App)ExtraCodecs.POSITIVE_INT.fieldOf("use_duration").forGetter(Instrument::useDuration), (App)ExtraCodecs.POSITIVE_FLOAT.fieldOf("range").forGetter(Instrument::range)).apply((Applicative)$$0, Instrument::new));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\Instrument.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */