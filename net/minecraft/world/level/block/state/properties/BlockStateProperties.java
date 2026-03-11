/*     */ package net.minecraft.world.level.block.state.properties;
/*     */ 
/*     */ import java.util.function.Predicate;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.FrontAndTop;
/*     */ import net.minecraft.world.level.block.entity.trialspawner.TrialSpawnerState;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockStateProperties
/*     */ {
/*  15 */   public static final BooleanProperty ATTACHED = BooleanProperty.create("attached");
/*  16 */   public static final BooleanProperty BOTTOM = BooleanProperty.create("bottom");
/*  17 */   public static final BooleanProperty CONDITIONAL = BooleanProperty.create("conditional");
/*  18 */   public static final BooleanProperty DISARMED = BooleanProperty.create("disarmed");
/*  19 */   public static final BooleanProperty DRAG = BooleanProperty.create("drag");
/*  20 */   public static final BooleanProperty ENABLED = BooleanProperty.create("enabled");
/*  21 */   public static final BooleanProperty EXTENDED = BooleanProperty.create("extended");
/*  22 */   public static final BooleanProperty EYE = BooleanProperty.create("eye");
/*  23 */   public static final BooleanProperty FALLING = BooleanProperty.create("falling");
/*  24 */   public static final BooleanProperty HANGING = BooleanProperty.create("hanging");
/*  25 */   public static final BooleanProperty HAS_BOTTLE_0 = BooleanProperty.create("has_bottle_0");
/*  26 */   public static final BooleanProperty HAS_BOTTLE_1 = BooleanProperty.create("has_bottle_1");
/*  27 */   public static final BooleanProperty HAS_BOTTLE_2 = BooleanProperty.create("has_bottle_2");
/*  28 */   public static final BooleanProperty HAS_RECORD = BooleanProperty.create("has_record");
/*  29 */   public static final BooleanProperty HAS_BOOK = BooleanProperty.create("has_book");
/*  30 */   public static final BooleanProperty INVERTED = BooleanProperty.create("inverted");
/*  31 */   public static final BooleanProperty IN_WALL = BooleanProperty.create("in_wall");
/*  32 */   public static final BooleanProperty LIT = BooleanProperty.create("lit");
/*  33 */   public static final BooleanProperty LOCKED = BooleanProperty.create("locked");
/*  34 */   public static final BooleanProperty OCCUPIED = BooleanProperty.create("occupied");
/*  35 */   public static final BooleanProperty OPEN = BooleanProperty.create("open");
/*  36 */   public static final BooleanProperty PERSISTENT = BooleanProperty.create("persistent");
/*  37 */   public static final BooleanProperty POWERED = BooleanProperty.create("powered");
/*  38 */   public static final BooleanProperty SHORT = BooleanProperty.create("short");
/*  39 */   public static final BooleanProperty SIGNAL_FIRE = BooleanProperty.create("signal_fire");
/*  40 */   public static final BooleanProperty SNOWY = BooleanProperty.create("snowy");
/*  41 */   public static final BooleanProperty TRIGGERED = BooleanProperty.create("triggered");
/*  42 */   public static final BooleanProperty UNSTABLE = BooleanProperty.create("unstable");
/*  43 */   public static final BooleanProperty WATERLOGGED = BooleanProperty.create("waterlogged");
/*  44 */   public static final BooleanProperty BERRIES = BooleanProperty.create("berries");
/*  45 */   public static final BooleanProperty BLOOM = BooleanProperty.create("bloom");
/*  46 */   public static final BooleanProperty SHRIEKING = BooleanProperty.create("shrieking");
/*  47 */   public static final BooleanProperty CAN_SUMMON = BooleanProperty.create("can_summon");
/*     */   
/*  49 */   public static final EnumProperty<Direction.Axis> HORIZONTAL_AXIS = EnumProperty.create("axis", Direction.Axis.class, new Direction.Axis[] { Direction.Axis.X, Direction.Axis.Z });
/*  50 */   public static final EnumProperty<Direction.Axis> AXIS = EnumProperty.create("axis", Direction.Axis.class);
/*     */   
/*  52 */   public static final BooleanProperty UP = BooleanProperty.create("up");
/*  53 */   public static final BooleanProperty DOWN = BooleanProperty.create("down");
/*  54 */   public static final BooleanProperty NORTH = BooleanProperty.create("north");
/*  55 */   public static final BooleanProperty EAST = BooleanProperty.create("east");
/*  56 */   public static final BooleanProperty SOUTH = BooleanProperty.create("south");
/*  57 */   public static final BooleanProperty WEST = BooleanProperty.create("west");
/*     */   public static final DirectionProperty FACING_HOPPER;
/*  59 */   public static final DirectionProperty FACING = DirectionProperty.create("facing", new Direction[] { Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.UP, Direction.DOWN });
/*     */   static {
/*  61 */     FACING_HOPPER = DirectionProperty.create("facing", $$0 -> ($$0 != Direction.UP));
/*  62 */   } public static final DirectionProperty HORIZONTAL_FACING = DirectionProperty.create("facing", (Predicate<Direction>)Direction.Plane.HORIZONTAL);
/*  63 */   public static final IntegerProperty FLOWER_AMOUNT = IntegerProperty.create("flower_amount", 1, 4);
/*     */   
/*  65 */   public static final EnumProperty<FrontAndTop> ORIENTATION = EnumProperty.create("orientation", FrontAndTop.class);
/*     */   
/*  67 */   public static final EnumProperty<AttachFace> ATTACH_FACE = EnumProperty.create("face", AttachFace.class);
/*  68 */   public static final EnumProperty<BellAttachType> BELL_ATTACHMENT = EnumProperty.create("attachment", BellAttachType.class);
/*     */   
/*  70 */   public static final EnumProperty<WallSide> EAST_WALL = EnumProperty.create("east", WallSide.class);
/*  71 */   public static final EnumProperty<WallSide> NORTH_WALL = EnumProperty.create("north", WallSide.class);
/*  72 */   public static final EnumProperty<WallSide> SOUTH_WALL = EnumProperty.create("south", WallSide.class);
/*  73 */   public static final EnumProperty<WallSide> WEST_WALL = EnumProperty.create("west", WallSide.class);
/*     */   
/*  75 */   public static final EnumProperty<RedstoneSide> EAST_REDSTONE = EnumProperty.create("east", RedstoneSide.class);
/*  76 */   public static final EnumProperty<RedstoneSide> NORTH_REDSTONE = EnumProperty.create("north", RedstoneSide.class);
/*  77 */   public static final EnumProperty<RedstoneSide> SOUTH_REDSTONE = EnumProperty.create("south", RedstoneSide.class);
/*  78 */   public static final EnumProperty<RedstoneSide> WEST_REDSTONE = EnumProperty.create("west", RedstoneSide.class);
/*     */   
/*  80 */   public static final EnumProperty<DoubleBlockHalf> DOUBLE_BLOCK_HALF = EnumProperty.create("half", DoubleBlockHalf.class);
/*  81 */   public static final EnumProperty<Half> HALF = EnumProperty.create("half", Half.class);
/*     */   
/*  83 */   public static final EnumProperty<RailShape> RAIL_SHAPE = EnumProperty.create("shape", RailShape.class); static {
/*  84 */     RAIL_SHAPE_STRAIGHT = EnumProperty.create("shape", RailShape.class, $$0 -> 
/*  85 */         ($$0 != RailShape.NORTH_EAST && $$0 != RailShape.NORTH_WEST && $$0 != RailShape.SOUTH_EAST && $$0 != RailShape.SOUTH_WEST));
/*     */   }
/*     */ 
/*     */   
/*     */   public static final EnumProperty<RailShape> RAIL_SHAPE_STRAIGHT;
/*     */   
/*     */   public static final int MAX_AGE_1 = 1;
/*     */   public static final int MAX_AGE_2 = 2;
/*     */   public static final int MAX_AGE_3 = 3;
/*     */   public static final int MAX_AGE_4 = 4;
/*     */   public static final int MAX_AGE_5 = 5;
/*     */   public static final int MAX_AGE_7 = 7;
/*     */   public static final int MAX_AGE_15 = 15;
/*     */   public static final int MAX_AGE_25 = 25;
/*  99 */   public static final IntegerProperty AGE_1 = IntegerProperty.create("age", 0, 1);
/* 100 */   public static final IntegerProperty AGE_2 = IntegerProperty.create("age", 0, 2);
/* 101 */   public static final IntegerProperty AGE_3 = IntegerProperty.create("age", 0, 3);
/* 102 */   public static final IntegerProperty AGE_4 = IntegerProperty.create("age", 0, 4);
/* 103 */   public static final IntegerProperty AGE_5 = IntegerProperty.create("age", 0, 5);
/* 104 */   public static final IntegerProperty AGE_7 = IntegerProperty.create("age", 0, 7);
/* 105 */   public static final IntegerProperty AGE_15 = IntegerProperty.create("age", 0, 15);
/* 106 */   public static final IntegerProperty AGE_25 = IntegerProperty.create("age", 0, 25);
/*     */   
/* 108 */   public static final IntegerProperty BITES = IntegerProperty.create("bites", 0, 6);
/* 109 */   public static final IntegerProperty CANDLES = IntegerProperty.create("candles", 1, 4);
/* 110 */   public static final IntegerProperty DELAY = IntegerProperty.create("delay", 1, 4);
/*     */   public static final int MAX_DISTANCE = 7;
/* 112 */   public static final IntegerProperty DISTANCE = IntegerProperty.create("distance", 1, 7);
/* 113 */   public static final IntegerProperty EGGS = IntegerProperty.create("eggs", 1, 4);
/* 114 */   public static final IntegerProperty HATCH = IntegerProperty.create("hatch", 0, 2);
/* 115 */   public static final IntegerProperty LAYERS = IntegerProperty.create("layers", 1, 8);
/*     */   public static final int MIN_LEVEL = 0;
/*     */   public static final int MIN_LEVEL_CAULDRON = 1;
/*     */   public static final int MAX_LEVEL_3 = 3;
/*     */   public static final int MAX_LEVEL_8 = 8;
/* 120 */   public static final IntegerProperty LEVEL_CAULDRON = IntegerProperty.create("level", 1, 3);
/* 121 */   public static final IntegerProperty LEVEL_COMPOSTER = IntegerProperty.create("level", 0, 8);
/* 122 */   public static final IntegerProperty LEVEL_FLOWING = IntegerProperty.create("level", 1, 8);
/* 123 */   public static final IntegerProperty LEVEL_HONEY = IntegerProperty.create("honey_level", 0, 5);
/*     */   public static final int MAX_LEVEL_15 = 15;
/* 125 */   public static final IntegerProperty LEVEL = IntegerProperty.create("level", 0, 15);
/* 126 */   public static final IntegerProperty MOISTURE = IntegerProperty.create("moisture", 0, 7);
/* 127 */   public static final IntegerProperty NOTE = IntegerProperty.create("note", 0, 24);
/* 128 */   public static final IntegerProperty PICKLES = IntegerProperty.create("pickles", 1, 4);
/* 129 */   public static final IntegerProperty POWER = IntegerProperty.create("power", 0, 15);
/* 130 */   public static final IntegerProperty STAGE = IntegerProperty.create("stage", 0, 1);
/*     */   public static final int STABILITY_MAX_DISTANCE = 7;
/* 132 */   public static final IntegerProperty STABILITY_DISTANCE = IntegerProperty.create("distance", 0, 7);
/*     */   public static final int MIN_RESPAWN_ANCHOR_CHARGES = 0;
/*     */   public static final int MAX_RESPAWN_ANCHOR_CHARGES = 4;
/* 135 */   public static final IntegerProperty RESPAWN_ANCHOR_CHARGES = IntegerProperty.create("charges", 0, 4);
/*     */   
/* 137 */   public static final IntegerProperty ROTATION_16 = IntegerProperty.create("rotation", 0, RotationSegment.getMaxSegmentIndex());
/*     */   
/* 139 */   public static final EnumProperty<BedPart> BED_PART = EnumProperty.create("part", BedPart.class);
/* 140 */   public static final EnumProperty<ChestType> CHEST_TYPE = EnumProperty.create("type", ChestType.class);
/* 141 */   public static final EnumProperty<ComparatorMode> MODE_COMPARATOR = EnumProperty.create("mode", ComparatorMode.class);
/* 142 */   public static final EnumProperty<DoorHingeSide> DOOR_HINGE = EnumProperty.create("hinge", DoorHingeSide.class);
/* 143 */   public static final EnumProperty<NoteBlockInstrument> NOTEBLOCK_INSTRUMENT = EnumProperty.create("instrument", NoteBlockInstrument.class);
/* 144 */   public static final EnumProperty<PistonType> PISTON_TYPE = EnumProperty.create("type", PistonType.class);
/* 145 */   public static final EnumProperty<SlabType> SLAB_TYPE = EnumProperty.create("type", SlabType.class);
/* 146 */   public static final EnumProperty<StairsShape> STAIRS_SHAPE = EnumProperty.create("shape", StairsShape.class);
/* 147 */   public static final EnumProperty<StructureMode> STRUCTUREBLOCK_MODE = EnumProperty.create("mode", StructureMode.class);
/* 148 */   public static final EnumProperty<BambooLeaves> BAMBOO_LEAVES = EnumProperty.create("leaves", BambooLeaves.class);
/* 149 */   public static final EnumProperty<Tilt> TILT = EnumProperty.create("tilt", Tilt.class);
/*     */   
/* 151 */   public static final DirectionProperty VERTICAL_DIRECTION = DirectionProperty.create("vertical_direction", new Direction[] { Direction.UP, Direction.DOWN });
/* 152 */   public static final EnumProperty<DripstoneThickness> DRIPSTONE_THICKNESS = EnumProperty.create("thickness", DripstoneThickness.class);
/* 153 */   public static final EnumProperty<SculkSensorPhase> SCULK_SENSOR_PHASE = EnumProperty.create("sculk_sensor_phase", SculkSensorPhase.class);
/* 154 */   public static final BooleanProperty CHISELED_BOOKSHELF_SLOT_0_OCCUPIED = BooleanProperty.create("slot_0_occupied");
/* 155 */   public static final BooleanProperty CHISELED_BOOKSHELF_SLOT_1_OCCUPIED = BooleanProperty.create("slot_1_occupied");
/* 156 */   public static final BooleanProperty CHISELED_BOOKSHELF_SLOT_2_OCCUPIED = BooleanProperty.create("slot_2_occupied");
/* 157 */   public static final BooleanProperty CHISELED_BOOKSHELF_SLOT_3_OCCUPIED = BooleanProperty.create("slot_3_occupied");
/* 158 */   public static final BooleanProperty CHISELED_BOOKSHELF_SLOT_4_OCCUPIED = BooleanProperty.create("slot_4_occupied");
/* 159 */   public static final BooleanProperty CHISELED_BOOKSHELF_SLOT_5_OCCUPIED = BooleanProperty.create("slot_5_occupied");
/* 160 */   public static final IntegerProperty DUSTED = IntegerProperty.create("dusted", 0, 3);
/* 161 */   public static final BooleanProperty CRACKED = BooleanProperty.create("cracked");
/* 162 */   public static final BooleanProperty CRAFTING = BooleanProperty.create("crafting");
/* 163 */   public static final EnumProperty<TrialSpawnerState> TRIAL_SPAWNER_STATE = EnumProperty.create("trial_spawner_state", TrialSpawnerState.class);
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\state\properties\BlockStateProperties.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */