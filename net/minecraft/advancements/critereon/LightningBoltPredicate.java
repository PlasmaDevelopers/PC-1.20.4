/*    */ package net.minecraft.advancements.critereon;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LightningBolt;
/*    */ 
/*    */ public final class LightningBoltPredicate extends Record implements EntitySubPredicate {
/*    */   private final MinMaxBounds.Ints blocksSetOnFire;
/*    */   private final Optional<EntityPredicate> entityStruck;
/*    */   public static final MapCodec<LightningBoltPredicate> CODEC;
/*    */   
/* 14 */   public LightningBoltPredicate(MinMaxBounds.Ints $$0, Optional<EntityPredicate> $$1) { this.blocksSetOnFire = $$0; this.entityStruck = $$1; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/LightningBoltPredicate;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #14	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 14 */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/LightningBoltPredicate; } public MinMaxBounds.Ints blocksSetOnFire() { return this.blocksSetOnFire; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/LightningBoltPredicate;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #14	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/LightningBoltPredicate; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/LightningBoltPredicate;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #14	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/advancements/critereon/LightningBoltPredicate;
/* 14 */     //   0	8	1	$$0	Ljava/lang/Object; } public Optional<EntityPredicate> entityStruck() { return this.entityStruck; }
/*    */ 
/*    */   
/*    */   static {
/* 18 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField(MinMaxBounds.Ints.CODEC, "blocks_set_on_fire", MinMaxBounds.Ints.ANY).forGetter(LightningBoltPredicate::blocksSetOnFire), (App)ExtraCodecs.strictOptionalField(EntityPredicate.CODEC, "entity_struck").forGetter(LightningBoltPredicate::entityStruck)).apply((Applicative)$$0, LightningBoltPredicate::new));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static LightningBoltPredicate blockSetOnFire(MinMaxBounds.Ints $$0) {
/* 24 */     return new LightningBoltPredicate($$0, Optional.empty());
/*    */   }
/*    */ 
/*    */   
/*    */   public EntitySubPredicate.Type type() {
/* 29 */     return EntitySubPredicate.Types.LIGHTNING;
/*    */   }
/*    */   
/*    */   public boolean matches(Entity $$0, ServerLevel $$1, @Nullable Vec3 $$2) {
/*    */     LightningBolt $$3;
/* 34 */     if ($$0 instanceof LightningBolt) { $$3 = (LightningBolt)$$0; }
/* 35 */     else { return false; }
/*    */ 
/*    */     
/* 38 */     return (this.blocksSetOnFire.matches($$3.getBlocksSetOnFire()) && (this.entityStruck
/* 39 */       .isEmpty() || $$3.getHitEntities().anyMatch($$2 -> ((EntityPredicate)this.entityStruck.get()).matches($$0, $$1, $$2))));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\LightningBoltPredicate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */