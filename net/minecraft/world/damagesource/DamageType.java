/*    */ package net.minecraft.world.damagesource;public final class DamageType extends Record { private final String msgId; private final DamageScaling scaling; private final float exhaustion;
/*    */   private final DamageEffects effects;
/*    */   private final DeathMessageType deathMessageType;
/*    */   public static final Codec<DamageType> CODEC;
/*    */   
/*  6 */   public DamageType(String $$0, DamageScaling $$1, float $$2, DamageEffects $$3, DeathMessageType $$4) { this.msgId = $$0; this.scaling = $$1; this.exhaustion = $$2; this.effects = $$3; this.deathMessageType = $$4; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/damagesource/DamageType;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #6	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  6 */     //   0	7	0	this	Lnet/minecraft/world/damagesource/DamageType; } public String msgId() { return this.msgId; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/damagesource/DamageType;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #6	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/damagesource/DamageType; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/damagesource/DamageType;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #6	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/damagesource/DamageType;
/*  6 */     //   0	8	1	$$0	Ljava/lang/Object; } public DamageScaling scaling() { return this.scaling; } public float exhaustion() { return this.exhaustion; } public DamageEffects effects() { return this.effects; } public DeathMessageType deathMessageType() { return this.deathMessageType; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 13 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.STRING.fieldOf("message_id").forGetter(DamageType::msgId), (App)DamageScaling.CODEC.fieldOf("scaling").forGetter(DamageType::scaling), (App)Codec.FLOAT.fieldOf("exhaustion").forGetter(DamageType::exhaustion), (App)DamageEffects.CODEC.optionalFieldOf("effects", DamageEffects.HURT).forGetter(DamageType::effects), (App)DeathMessageType.CODEC.optionalFieldOf("death_message_type", DeathMessageType.DEFAULT).forGetter(DamageType::deathMessageType)).apply((Applicative)$$0, DamageType::new));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DamageType(String $$0, DamageScaling $$1, float $$2) {
/* 22 */     this($$0, $$1, $$2, DamageEffects.HURT, DeathMessageType.DEFAULT);
/*    */   }
/*    */   
/*    */   public DamageType(String $$0, DamageScaling $$1, float $$2, DamageEffects $$3) {
/* 26 */     this($$0, $$1, $$2, $$3, DeathMessageType.DEFAULT);
/*    */   }
/*    */   
/*    */   public DamageType(String $$0, float $$1, DamageEffects $$2) {
/* 30 */     this($$0, DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER, $$1, $$2);
/*    */   }
/*    */   
/*    */   public DamageType(String $$0, float $$1) {
/* 34 */     this($$0, DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER, $$1);
/*    */   } }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\damagesource\DamageType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */