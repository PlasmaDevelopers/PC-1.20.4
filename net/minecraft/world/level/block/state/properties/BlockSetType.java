/*     */ package net.minecraft.world.level.block.state.properties;
/*     */ 
/*     */ 
/*     */ public final class BlockSetType extends Record {
/*     */   private final String name;
/*     */   private final boolean canOpenByHand;
/*     */   private final boolean canOpenByWindCharge;
/*     */   private final boolean canButtonBeActivatedByArrows;
/*     */   private final PressurePlateSensitivity pressurePlateSensitivity;
/*     */   private final SoundType soundType;
/*     */   private final SoundEvent doorClose;
/*     */   
/*  13 */   public BlockSetType(String $$0, boolean $$1, boolean $$2, boolean $$3, PressurePlateSensitivity $$4, SoundType $$5, SoundEvent $$6, SoundEvent $$7, SoundEvent $$8, SoundEvent $$9, SoundEvent $$10, SoundEvent $$11, SoundEvent $$12, SoundEvent $$13) { this.name = $$0; this.canOpenByHand = $$1; this.canOpenByWindCharge = $$2; this.canButtonBeActivatedByArrows = $$3; this.pressurePlateSensitivity = $$4; this.soundType = $$5; this.doorClose = $$6; this.doorOpen = $$7; this.trapdoorClose = $$8; this.trapdoorOpen = $$9; this.pressurePlateClickOff = $$10; this.pressurePlateClickOn = $$11; this.buttonClickOff = $$12; this.buttonClickOn = $$13; } private final SoundEvent doorOpen; private final SoundEvent trapdoorClose; private final SoundEvent trapdoorOpen; private final SoundEvent pressurePlateClickOff; private final SoundEvent pressurePlateClickOn; private final SoundEvent buttonClickOff; private final SoundEvent buttonClickOn; public final String toString() { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/block/state/properties/BlockSetType;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #13	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/world/level/block/state/properties/BlockSetType; } public final int hashCode() { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/block/state/properties/BlockSetType;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #13	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/world/level/block/state/properties/BlockSetType; } public final boolean equals(Object $$0) { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/block/state/properties/BlockSetType;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #13	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lnet/minecraft/world/level/block/state/properties/BlockSetType;
/*  13 */     //   0	8	1	$$0	Ljava/lang/Object; } public String name() { return this.name; } public boolean canOpenByHand() { return this.canOpenByHand; } public boolean canOpenByWindCharge() { return this.canOpenByWindCharge; } public boolean canButtonBeActivatedByArrows() { return this.canButtonBeActivatedByArrows; } public PressurePlateSensitivity pressurePlateSensitivity() { return this.pressurePlateSensitivity; } public SoundType soundType() { return this.soundType; } public SoundEvent doorClose() { return this.doorClose; } public SoundEvent doorOpen() { return this.doorOpen; } public SoundEvent trapdoorClose() { return this.trapdoorClose; } public SoundEvent trapdoorOpen() { return this.trapdoorOpen; } public SoundEvent pressurePlateClickOff() { return this.pressurePlateClickOff; } public SoundEvent pressurePlateClickOn() { return this.pressurePlateClickOn; } public SoundEvent buttonClickOff() { return this.buttonClickOff; } public SoundEvent buttonClickOn() { return this.buttonClickOn; }
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
/*  29 */   private static final Map<String, BlockSetType> TYPES = (Map<String, BlockSetType>)new Object2ObjectArrayMap();
/*  30 */   public static final Codec<BlockSetType> CODEC = ExtraCodecs.stringResolverCodec(BlockSetType::name, TYPES::get); static { Objects.requireNonNull(TYPES); }
/*     */   
/*  32 */   public static final BlockSetType IRON = register(new BlockSetType("iron", false, false, false, PressurePlateSensitivity.EVERYTHING, SoundType.METAL, SoundEvents.IRON_DOOR_CLOSE, SoundEvents.IRON_DOOR_OPEN, SoundEvents.IRON_TRAPDOOR_CLOSE, SoundEvents.IRON_TRAPDOOR_OPEN, SoundEvents.METAL_PRESSURE_PLATE_CLICK_OFF, SoundEvents.METAL_PRESSURE_PLATE_CLICK_ON, SoundEvents.STONE_BUTTON_CLICK_OFF, SoundEvents.STONE_BUTTON_CLICK_ON));
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
/*  51 */   public static final BlockSetType COPPER = register(new BlockSetType("copper", true, true, false, PressurePlateSensitivity.EVERYTHING, SoundType.COPPER, SoundEvents.COPPER_DOOR_CLOSE, SoundEvents.COPPER_DOOR_OPEN, SoundEvents.COPPER_TRAPDOOR_CLOSE, SoundEvents.COPPER_TRAPDOOR_OPEN, SoundEvents.METAL_PRESSURE_PLATE_CLICK_OFF, SoundEvents.METAL_PRESSURE_PLATE_CLICK_ON, SoundEvents.STONE_BUTTON_CLICK_OFF, SoundEvents.STONE_BUTTON_CLICK_ON));
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
/*  71 */   public static final BlockSetType GOLD = register(new BlockSetType("gold", false, true, false, PressurePlateSensitivity.EVERYTHING, SoundType.METAL, SoundEvents.IRON_DOOR_CLOSE, SoundEvents.IRON_DOOR_OPEN, SoundEvents.IRON_TRAPDOOR_CLOSE, SoundEvents.IRON_TRAPDOOR_OPEN, SoundEvents.METAL_PRESSURE_PLATE_CLICK_OFF, SoundEvents.METAL_PRESSURE_PLATE_CLICK_ON, SoundEvents.STONE_BUTTON_CLICK_OFF, SoundEvents.STONE_BUTTON_CLICK_ON));
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
/*  95 */   public static final BlockSetType STONE = register(new BlockSetType("stone", true, true, false, PressurePlateSensitivity.MOBS, SoundType.STONE, SoundEvents.IRON_DOOR_CLOSE, SoundEvents.IRON_DOOR_OPEN, SoundEvents.IRON_TRAPDOOR_CLOSE, SoundEvents.IRON_TRAPDOOR_OPEN, SoundEvents.STONE_PRESSURE_PLATE_CLICK_OFF, SoundEvents.STONE_PRESSURE_PLATE_CLICK_ON, SoundEvents.STONE_BUTTON_CLICK_OFF, SoundEvents.STONE_BUTTON_CLICK_ON));
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
/* 116 */   public static final BlockSetType POLISHED_BLACKSTONE = register(new BlockSetType("polished_blackstone", true, true, false, PressurePlateSensitivity.MOBS, SoundType.STONE, SoundEvents.IRON_DOOR_CLOSE, SoundEvents.IRON_DOOR_OPEN, SoundEvents.IRON_TRAPDOOR_CLOSE, SoundEvents.IRON_TRAPDOOR_OPEN, SoundEvents.STONE_PRESSURE_PLATE_CLICK_OFF, SoundEvents.STONE_PRESSURE_PLATE_CLICK_ON, SoundEvents.STONE_BUTTON_CLICK_OFF, SoundEvents.STONE_BUTTON_CLICK_ON));
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
/* 140 */   public static final BlockSetType OAK = register(new BlockSetType("oak"));
/* 141 */   public static final BlockSetType SPRUCE = register(new BlockSetType("spruce"));
/* 142 */   public static final BlockSetType BIRCH = register(new BlockSetType("birch"));
/* 143 */   public static final BlockSetType ACACIA = register(new BlockSetType("acacia"));
/* 144 */   public static final BlockSetType CHERRY = register(new BlockSetType("cherry", true, true, true, PressurePlateSensitivity.EVERYTHING, SoundType.CHERRY_WOOD, SoundEvents.CHERRY_WOOD_DOOR_CLOSE, SoundEvents.CHERRY_WOOD_DOOR_OPEN, SoundEvents.CHERRY_WOOD_TRAPDOOR_CLOSE, SoundEvents.CHERRY_WOOD_TRAPDOOR_OPEN, SoundEvents.CHERRY_WOOD_PRESSURE_PLATE_CLICK_OFF, SoundEvents.CHERRY_WOOD_PRESSURE_PLATE_CLICK_ON, SoundEvents.CHERRY_WOOD_BUTTON_CLICK_OFF, SoundEvents.CHERRY_WOOD_BUTTON_CLICK_ON));
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
/* 160 */   public static final BlockSetType JUNGLE = register(new BlockSetType("jungle"));
/* 161 */   public static final BlockSetType DARK_OAK = register(new BlockSetType("dark_oak"));
/* 162 */   public static final BlockSetType CRIMSON = register(new BlockSetType("crimson", true, true, true, PressurePlateSensitivity.EVERYTHING, SoundType.NETHER_WOOD, SoundEvents.NETHER_WOOD_DOOR_CLOSE, SoundEvents.NETHER_WOOD_DOOR_OPEN, SoundEvents.NETHER_WOOD_TRAPDOOR_CLOSE, SoundEvents.NETHER_WOOD_TRAPDOOR_OPEN, SoundEvents.NETHER_WOOD_PRESSURE_PLATE_CLICK_OFF, SoundEvents.NETHER_WOOD_PRESSURE_PLATE_CLICK_ON, SoundEvents.NETHER_WOOD_BUTTON_CLICK_OFF, SoundEvents.NETHER_WOOD_BUTTON_CLICK_ON));
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
/* 178 */   public static final BlockSetType WARPED = register(new BlockSetType("warped", true, true, true, PressurePlateSensitivity.EVERYTHING, SoundType.NETHER_WOOD, SoundEvents.NETHER_WOOD_DOOR_CLOSE, SoundEvents.NETHER_WOOD_DOOR_OPEN, SoundEvents.NETHER_WOOD_TRAPDOOR_CLOSE, SoundEvents.NETHER_WOOD_TRAPDOOR_OPEN, SoundEvents.NETHER_WOOD_PRESSURE_PLATE_CLICK_OFF, SoundEvents.NETHER_WOOD_PRESSURE_PLATE_CLICK_ON, SoundEvents.NETHER_WOOD_BUTTON_CLICK_OFF, SoundEvents.NETHER_WOOD_BUTTON_CLICK_ON));
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
/* 194 */   public static final BlockSetType MANGROVE = register(new BlockSetType("mangrove"));
/* 195 */   public static final BlockSetType BAMBOO = register(new BlockSetType("bamboo", true, true, true, PressurePlateSensitivity.EVERYTHING, SoundType.BAMBOO_WOOD, SoundEvents.BAMBOO_WOOD_DOOR_CLOSE, SoundEvents.BAMBOO_WOOD_DOOR_OPEN, SoundEvents.BAMBOO_WOOD_TRAPDOOR_CLOSE, SoundEvents.BAMBOO_WOOD_TRAPDOOR_OPEN, SoundEvents.BAMBOO_WOOD_PRESSURE_PLATE_CLICK_OFF, SoundEvents.BAMBOO_WOOD_PRESSURE_PLATE_CLICK_ON, SoundEvents.BAMBOO_WOOD_BUTTON_CLICK_OFF, SoundEvents.BAMBOO_WOOD_BUTTON_CLICK_ON));
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
/*     */   public BlockSetType(String $$0) {
/* 213 */     this($$0, true, true, true, PressurePlateSensitivity.EVERYTHING, SoundType.WOOD, SoundEvents.WOODEN_DOOR_CLOSE, SoundEvents.WOODEN_DOOR_OPEN, SoundEvents.WOODEN_TRAPDOOR_CLOSE, SoundEvents.WOODEN_TRAPDOOR_OPEN, SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_OFF, SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_ON, SoundEvents.WOODEN_BUTTON_CLICK_OFF, SoundEvents.WOODEN_BUTTON_CLICK_ON);
/*     */   }
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
/*     */   private static BlockSetType register(BlockSetType $$0) {
/* 232 */     TYPES.put($$0.name, $$0);
/* 233 */     return $$0;
/*     */   }
/*     */   
/*     */   public static Stream<BlockSetType> values() {
/* 237 */     return TYPES.values().stream();
/*     */   }
/*     */   
/*     */   public enum PressurePlateSensitivity {
/* 241 */     EVERYTHING, MOBS;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\state\properties\BlockSetType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */