/*    */ package net.minecraft.advancements.critereon;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Optional;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.projectile.FishingHook;
/*    */ 
/*    */ public final class FishingHookPredicate extends Record implements EntitySubPredicate {
/*    */   private final Optional<Boolean> inOpenWater;
/*    */   
/* 15 */   public FishingHookPredicate(Optional<Boolean> $$0) { this.inOpenWater = $$0; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/FishingHookPredicate;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #15	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 15 */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/FishingHookPredicate; } public Optional<Boolean> inOpenWater() { return this.inOpenWater; }
/*    */   public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/FishingHookPredicate;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #15	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/FishingHookPredicate; } public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/FishingHookPredicate;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #15	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/advancements/critereon/FishingHookPredicate;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/* 18 */   } public static final FishingHookPredicate ANY = new FishingHookPredicate(Optional.empty()); public static final MapCodec<FishingHookPredicate> CODEC;
/*    */   static {
/* 20 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField((Codec)Codec.BOOL, "in_open_water").forGetter(FishingHookPredicate::inOpenWater)).apply((Applicative)$$0, FishingHookPredicate::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static FishingHookPredicate inOpenWater(boolean $$0) {
/* 25 */     return new FishingHookPredicate(Optional.of(Boolean.valueOf($$0)));
/*    */   }
/*    */ 
/*    */   
/*    */   public EntitySubPredicate.Type type() {
/* 30 */     return EntitySubPredicate.Types.FISHING_HOOK;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(Entity $$0, ServerLevel $$1, @Nullable Vec3 $$2) {
/* 35 */     if (this.inOpenWater.isEmpty()) {
/* 36 */       return true;
/*    */     }
/* 38 */     if ($$0 instanceof FishingHook) { FishingHook $$3 = (FishingHook)$$0;
/* 39 */       return (((Boolean)this.inOpenWater.get()).booleanValue() == $$3.isOpenWaterFishing()); }
/*    */     
/* 41 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\FishingHookPredicate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */