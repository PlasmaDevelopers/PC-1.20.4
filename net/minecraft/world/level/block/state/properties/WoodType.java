/*    */ package net.minecraft.world.level.block.state.properties;
/*    */ 
/*    */ import net.minecraft.world.level.block.SoundType;
/*    */ 
/*    */ public final class WoodType extends Record {
/*    */   private final String name;
/*    */   private final BlockSetType setType;
/*    */   private final SoundType soundType;
/*    */   private final SoundType hangingSignSoundType;
/*    */   private final SoundEvent fenceGateClose;
/*    */   private final SoundEvent fenceGateOpen;
/*    */   
/* 13 */   public WoodType(String $$0, BlockSetType $$1, SoundType $$2, SoundType $$3, SoundEvent $$4, SoundEvent $$5) { this.name = $$0; this.setType = $$1; this.soundType = $$2; this.hangingSignSoundType = $$3; this.fenceGateClose = $$4; this.fenceGateOpen = $$5; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/block/state/properties/WoodType;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #13	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 13 */     //   0	7	0	this	Lnet/minecraft/world/level/block/state/properties/WoodType; } public String name() { return this.name; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/block/state/properties/WoodType;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #13	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/block/state/properties/WoodType; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/block/state/properties/WoodType;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #13	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/block/state/properties/WoodType;
/* 13 */     //   0	8	1	$$0	Ljava/lang/Object; } public BlockSetType setType() { return this.setType; } public SoundType soundType() { return this.soundType; } public SoundType hangingSignSoundType() { return this.hangingSignSoundType; } public SoundEvent fenceGateClose() { return this.fenceGateClose; } public SoundEvent fenceGateOpen() { return this.fenceGateOpen; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 21 */   private static final Map<String, WoodType> TYPES = (Map<String, WoodType>)new Object2ObjectArrayMap();
/* 22 */   public static final Codec<WoodType> CODEC = ExtraCodecs.stringResolverCodec(WoodType::name, TYPES::get); static { Objects.requireNonNull(TYPES); }
/*    */   
/* 24 */   public static final WoodType OAK = register(new WoodType("oak", BlockSetType.OAK));
/* 25 */   public static final WoodType SPRUCE = register(new WoodType("spruce", BlockSetType.SPRUCE));
/* 26 */   public static final WoodType BIRCH = register(new WoodType("birch", BlockSetType.BIRCH));
/* 27 */   public static final WoodType ACACIA = register(new WoodType("acacia", BlockSetType.ACACIA));
/* 28 */   public static final WoodType CHERRY = register(new WoodType("cherry", BlockSetType.CHERRY, SoundType.CHERRY_WOOD, SoundType.CHERRY_WOOD_HANGING_SIGN, SoundEvents.CHERRY_WOOD_FENCE_GATE_CLOSE, SoundEvents.CHERRY_WOOD_FENCE_GATE_OPEN));
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 36 */   public static final WoodType JUNGLE = register(new WoodType("jungle", BlockSetType.JUNGLE));
/* 37 */   public static final WoodType DARK_OAK = register(new WoodType("dark_oak", BlockSetType.DARK_OAK));
/* 38 */   public static final WoodType CRIMSON = register(new WoodType("crimson", BlockSetType.CRIMSON, SoundType.NETHER_WOOD, SoundType.NETHER_WOOD_HANGING_SIGN, SoundEvents.NETHER_WOOD_FENCE_GATE_CLOSE, SoundEvents.NETHER_WOOD_FENCE_GATE_OPEN));
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 46 */   public static final WoodType WARPED = register(new WoodType("warped", BlockSetType.WARPED, SoundType.NETHER_WOOD, SoundType.NETHER_WOOD_HANGING_SIGN, SoundEvents.NETHER_WOOD_FENCE_GATE_CLOSE, SoundEvents.NETHER_WOOD_FENCE_GATE_OPEN));
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 54 */   public static final WoodType MANGROVE = register(new WoodType("mangrove", BlockSetType.MANGROVE));
/* 55 */   public static final WoodType BAMBOO = register(new WoodType("bamboo", BlockSetType.BAMBOO, SoundType.BAMBOO_WOOD, SoundType.BAMBOO_WOOD_HANGING_SIGN, SoundEvents.BAMBOO_WOOD_FENCE_GATE_CLOSE, SoundEvents.BAMBOO_WOOD_FENCE_GATE_OPEN));
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WoodType(String $$0, BlockSetType $$1) {
/* 65 */     this($$0, $$1, SoundType.WOOD, SoundType.HANGING_SIGN, SoundEvents.FENCE_GATE_CLOSE, SoundEvents.FENCE_GATE_OPEN);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static WoodType register(WoodType $$0) {
/* 76 */     TYPES.put($$0.name(), $$0);
/* 77 */     return $$0;
/*    */   }
/*    */   
/*    */   public static Stream<WoodType> values() {
/* 81 */     return TYPES.values().stream();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\state\properties\WoodType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */