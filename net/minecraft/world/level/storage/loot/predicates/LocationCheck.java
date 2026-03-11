/*    */ package net.minecraft.world.level.storage.loot.predicates;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.advancements.critereon.LocationPredicate;
/*    */ import net.minecraft.core.BlockPos;
/*    */ 
/*    */ public final class LocationCheck extends Record implements LootItemCondition {
/*    */   private final Optional<LocationPredicate> predicate;
/*    */   private final BlockPos offset;
/*    */   private static final MapCodec<BlockPos> OFFSET_CODEC;
/*    */   public static final Codec<LocationCheck> CODEC;
/*    */   
/* 15 */   public LocationCheck(Optional<LocationPredicate> $$0, BlockPos $$1) { this.predicate = $$0; this.offset = $$1; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/storage/loot/predicates/LocationCheck;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #15	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 15 */     //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/predicates/LocationCheck; } public Optional<LocationPredicate> predicate() { return this.predicate; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/storage/loot/predicates/LocationCheck;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #15	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/predicates/LocationCheck; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/storage/loot/predicates/LocationCheck;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #15	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/storage/loot/predicates/LocationCheck;
/* 15 */     //   0	8	1	$$0	Ljava/lang/Object; } public BlockPos offset() { return this.offset; }
/*    */ 
/*    */   
/*    */   static {
/* 19 */     OFFSET_CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField((Codec)Codec.INT, "offsetX", Integer.valueOf(0)).forGetter(Vec3i::getX), (App)ExtraCodecs.strictOptionalField((Codec)Codec.INT, "offsetY", Integer.valueOf(0)).forGetter(Vec3i::getY), (App)ExtraCodecs.strictOptionalField((Codec)Codec.INT, "offsetZ", Integer.valueOf(0)).forGetter(Vec3i::getZ)).apply((Applicative)$$0, BlockPos::new));
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 25 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField(LocationPredicate.CODEC, "predicate").forGetter(LocationCheck::predicate), (App)OFFSET_CODEC.forGetter(LocationCheck::offset)).apply((Applicative)$$0, LocationCheck::new));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LootItemConditionType getType() {
/* 32 */     return LootItemConditions.LOCATION_CHECK;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(LootContext $$0) {
/* 37 */     Vec3 $$1 = (Vec3)$$0.getParamOrNull(LootContextParams.ORIGIN);
/* 38 */     return ($$1 != null && (this.predicate.isEmpty() || ((LocationPredicate)this.predicate.get()).matches($$0.getLevel(), $$1.x() + this.offset.getX(), $$1.y() + this.offset.getY(), $$1.z() + this.offset.getZ())));
/*    */   }
/*    */   
/*    */   public static LootItemCondition.Builder checkLocation(LocationPredicate.Builder $$0) {
/* 42 */     return () -> new LocationCheck(Optional.of($$0.build()), BlockPos.ZERO);
/*    */   }
/*    */   
/*    */   public static LootItemCondition.Builder checkLocation(LocationPredicate.Builder $$0, BlockPos $$1) {
/* 46 */     return () -> new LocationCheck(Optional.of($$0.build()), $$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\predicates\LocationCheck.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */