/*     */ package net.minecraft.world.level.block.entity;
/*     */ 
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.mojang.datafixers.types.Type;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.datafix.fixes.References;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.piston.PistonMovingBlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class BlockEntityType<T extends BlockEntity>
/*     */ {
/*  24 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   @Nullable
/*     */   public static ResourceLocation getKey(BlockEntityType<?> $$0) {
/*  28 */     return BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey($$0);
/*     */   }
/*     */   
/*  31 */   public static final BlockEntityType<FurnaceBlockEntity> FURNACE = register("furnace", Builder.of(FurnaceBlockEntity::new, new Block[] { Blocks.FURNACE }));
/*  32 */   public static final BlockEntityType<ChestBlockEntity> CHEST = register("chest", Builder.of(ChestBlockEntity::new, new Block[] { Blocks.CHEST }));
/*  33 */   public static final BlockEntityType<TrappedChestBlockEntity> TRAPPED_CHEST = register("trapped_chest", Builder.of(TrappedChestBlockEntity::new, new Block[] { Blocks.TRAPPED_CHEST }));
/*  34 */   public static final BlockEntityType<EnderChestBlockEntity> ENDER_CHEST = register("ender_chest", Builder.of(EnderChestBlockEntity::new, new Block[] { Blocks.ENDER_CHEST }));
/*  35 */   public static final BlockEntityType<JukeboxBlockEntity> JUKEBOX = register("jukebox", Builder.of(JukeboxBlockEntity::new, new Block[] { Blocks.JUKEBOX }));
/*  36 */   public static final BlockEntityType<DispenserBlockEntity> DISPENSER = register("dispenser", Builder.of(DispenserBlockEntity::new, new Block[] { Blocks.DISPENSER }));
/*  37 */   public static final BlockEntityType<DropperBlockEntity> DROPPER = register("dropper", Builder.of(DropperBlockEntity::new, new Block[] { Blocks.DROPPER }));
/*  38 */   public static final BlockEntityType<SignBlockEntity> SIGN = register("sign", Builder.of(SignBlockEntity::new, new Block[] { Blocks.OAK_SIGN, Blocks.SPRUCE_SIGN, Blocks.BIRCH_SIGN, Blocks.ACACIA_SIGN, Blocks.CHERRY_SIGN, Blocks.JUNGLE_SIGN, Blocks.DARK_OAK_SIGN, Blocks.OAK_WALL_SIGN, Blocks.SPRUCE_WALL_SIGN, Blocks.BIRCH_WALL_SIGN, Blocks.ACACIA_WALL_SIGN, Blocks.CHERRY_WALL_SIGN, Blocks.JUNGLE_WALL_SIGN, Blocks.DARK_OAK_WALL_SIGN, Blocks.CRIMSON_SIGN, Blocks.CRIMSON_WALL_SIGN, Blocks.WARPED_SIGN, Blocks.WARPED_WALL_SIGN, Blocks.MANGROVE_SIGN, Blocks.MANGROVE_WALL_SIGN, Blocks.BAMBOO_SIGN, Blocks.BAMBOO_WALL_SIGN }));
/*  39 */   public static final BlockEntityType<HangingSignBlockEntity> HANGING_SIGN = register("hanging_sign", Builder.of(HangingSignBlockEntity::new, new Block[] { Blocks.OAK_HANGING_SIGN, Blocks.SPRUCE_HANGING_SIGN, Blocks.BIRCH_HANGING_SIGN, Blocks.ACACIA_HANGING_SIGN, Blocks.CHERRY_HANGING_SIGN, Blocks.JUNGLE_HANGING_SIGN, Blocks.DARK_OAK_HANGING_SIGN, Blocks.CRIMSON_HANGING_SIGN, Blocks.WARPED_HANGING_SIGN, Blocks.MANGROVE_HANGING_SIGN, Blocks.BAMBOO_HANGING_SIGN, Blocks.OAK_WALL_HANGING_SIGN, Blocks.SPRUCE_WALL_HANGING_SIGN, Blocks.BIRCH_WALL_HANGING_SIGN, Blocks.ACACIA_WALL_HANGING_SIGN, Blocks.CHERRY_WALL_HANGING_SIGN, Blocks.JUNGLE_WALL_HANGING_SIGN, Blocks.DARK_OAK_WALL_HANGING_SIGN, Blocks.CRIMSON_WALL_HANGING_SIGN, Blocks.WARPED_WALL_HANGING_SIGN, Blocks.MANGROVE_WALL_HANGING_SIGN, Blocks.BAMBOO_WALL_HANGING_SIGN }));
/*  40 */   public static final BlockEntityType<SpawnerBlockEntity> MOB_SPAWNER = register("mob_spawner", Builder.of(SpawnerBlockEntity::new, new Block[] { Blocks.SPAWNER }));
/*  41 */   public static final BlockEntityType<PistonMovingBlockEntity> PISTON = register("piston", Builder.of(PistonMovingBlockEntity::new, new Block[] { Blocks.MOVING_PISTON }));
/*  42 */   public static final BlockEntityType<BrewingStandBlockEntity> BREWING_STAND = register("brewing_stand", Builder.of(BrewingStandBlockEntity::new, new Block[] { Blocks.BREWING_STAND }));
/*  43 */   public static final BlockEntityType<EnchantmentTableBlockEntity> ENCHANTING_TABLE = register("enchanting_table", Builder.of(EnchantmentTableBlockEntity::new, new Block[] { Blocks.ENCHANTING_TABLE }));
/*  44 */   public static final BlockEntityType<TheEndPortalBlockEntity> END_PORTAL = register("end_portal", Builder.of(TheEndPortalBlockEntity::new, new Block[] { Blocks.END_PORTAL }));
/*  45 */   public static final BlockEntityType<BeaconBlockEntity> BEACON = register("beacon", Builder.of(BeaconBlockEntity::new, new Block[] { Blocks.BEACON }));
/*  46 */   public static final BlockEntityType<SkullBlockEntity> SKULL = register("skull", Builder.of(SkullBlockEntity::new, new Block[] { Blocks.SKELETON_SKULL, Blocks.SKELETON_WALL_SKULL, Blocks.CREEPER_HEAD, Blocks.CREEPER_WALL_HEAD, Blocks.DRAGON_HEAD, Blocks.DRAGON_WALL_HEAD, Blocks.ZOMBIE_HEAD, Blocks.ZOMBIE_WALL_HEAD, Blocks.WITHER_SKELETON_SKULL, Blocks.WITHER_SKELETON_WALL_SKULL, Blocks.PLAYER_HEAD, Blocks.PLAYER_WALL_HEAD, Blocks.PIGLIN_HEAD, Blocks.PIGLIN_WALL_HEAD }));
/*  47 */   public static final BlockEntityType<DaylightDetectorBlockEntity> DAYLIGHT_DETECTOR = register("daylight_detector", Builder.of(DaylightDetectorBlockEntity::new, new Block[] { Blocks.DAYLIGHT_DETECTOR }));
/*  48 */   public static final BlockEntityType<HopperBlockEntity> HOPPER = register("hopper", Builder.of(HopperBlockEntity::new, new Block[] { Blocks.HOPPER }));
/*  49 */   public static final BlockEntityType<ComparatorBlockEntity> COMPARATOR = register("comparator", Builder.of(ComparatorBlockEntity::new, new Block[] { Blocks.COMPARATOR }));
/*  50 */   public static final BlockEntityType<BannerBlockEntity> BANNER = register("banner", Builder.of(BannerBlockEntity::new, new Block[] { Blocks.WHITE_BANNER, Blocks.ORANGE_BANNER, Blocks.MAGENTA_BANNER, Blocks.LIGHT_BLUE_BANNER, Blocks.YELLOW_BANNER, Blocks.LIME_BANNER, Blocks.PINK_BANNER, Blocks.GRAY_BANNER, Blocks.LIGHT_GRAY_BANNER, Blocks.CYAN_BANNER, Blocks.PURPLE_BANNER, Blocks.BLUE_BANNER, Blocks.BROWN_BANNER, Blocks.GREEN_BANNER, Blocks.RED_BANNER, Blocks.BLACK_BANNER, Blocks.WHITE_WALL_BANNER, Blocks.ORANGE_WALL_BANNER, Blocks.MAGENTA_WALL_BANNER, Blocks.LIGHT_BLUE_WALL_BANNER, Blocks.YELLOW_WALL_BANNER, Blocks.LIME_WALL_BANNER, Blocks.PINK_WALL_BANNER, Blocks.GRAY_WALL_BANNER, Blocks.LIGHT_GRAY_WALL_BANNER, Blocks.CYAN_WALL_BANNER, Blocks.PURPLE_WALL_BANNER, Blocks.BLUE_WALL_BANNER, Blocks.BROWN_WALL_BANNER, Blocks.GREEN_WALL_BANNER, Blocks.RED_WALL_BANNER, Blocks.BLACK_WALL_BANNER }));
/*  51 */   public static final BlockEntityType<StructureBlockEntity> STRUCTURE_BLOCK = register("structure_block", Builder.of(StructureBlockEntity::new, new Block[] { Blocks.STRUCTURE_BLOCK }));
/*  52 */   public static final BlockEntityType<TheEndGatewayBlockEntity> END_GATEWAY = register("end_gateway", Builder.of(TheEndGatewayBlockEntity::new, new Block[] { Blocks.END_GATEWAY }));
/*  53 */   public static final BlockEntityType<CommandBlockEntity> COMMAND_BLOCK = register("command_block", Builder.of(CommandBlockEntity::new, new Block[] { Blocks.COMMAND_BLOCK, Blocks.CHAIN_COMMAND_BLOCK, Blocks.REPEATING_COMMAND_BLOCK }));
/*  54 */   public static final BlockEntityType<ShulkerBoxBlockEntity> SHULKER_BOX = register("shulker_box", Builder.of(ShulkerBoxBlockEntity::new, new Block[] { Blocks.SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX }));
/*  55 */   public static final BlockEntityType<BedBlockEntity> BED = register("bed", Builder.of(BedBlockEntity::new, new Block[] { Blocks.RED_BED, Blocks.BLACK_BED, Blocks.BLUE_BED, Blocks.BROWN_BED, Blocks.CYAN_BED, Blocks.GRAY_BED, Blocks.GREEN_BED, Blocks.LIGHT_BLUE_BED, Blocks.LIGHT_GRAY_BED, Blocks.LIME_BED, Blocks.MAGENTA_BED, Blocks.ORANGE_BED, Blocks.PINK_BED, Blocks.PURPLE_BED, Blocks.WHITE_BED, Blocks.YELLOW_BED }));
/*  56 */   public static final BlockEntityType<ConduitBlockEntity> CONDUIT = register("conduit", Builder.of(ConduitBlockEntity::new, new Block[] { Blocks.CONDUIT }));
/*  57 */   public static final BlockEntityType<BarrelBlockEntity> BARREL = register("barrel", Builder.of(BarrelBlockEntity::new, new Block[] { Blocks.BARREL }));
/*  58 */   public static final BlockEntityType<SmokerBlockEntity> SMOKER = register("smoker", Builder.of(SmokerBlockEntity::new, new Block[] { Blocks.SMOKER }));
/*  59 */   public static final BlockEntityType<BlastFurnaceBlockEntity> BLAST_FURNACE = register("blast_furnace", Builder.of(BlastFurnaceBlockEntity::new, new Block[] { Blocks.BLAST_FURNACE }));
/*  60 */   public static final BlockEntityType<LecternBlockEntity> LECTERN = register("lectern", Builder.of(LecternBlockEntity::new, new Block[] { Blocks.LECTERN }));
/*  61 */   public static final BlockEntityType<BellBlockEntity> BELL = register("bell", Builder.of(BellBlockEntity::new, new Block[] { Blocks.BELL }));
/*  62 */   public static final BlockEntityType<JigsawBlockEntity> JIGSAW = register("jigsaw", Builder.of(JigsawBlockEntity::new, new Block[] { Blocks.JIGSAW }));
/*  63 */   public static final BlockEntityType<CampfireBlockEntity> CAMPFIRE = register("campfire", Builder.of(CampfireBlockEntity::new, new Block[] { Blocks.CAMPFIRE, Blocks.SOUL_CAMPFIRE }));
/*  64 */   public static final BlockEntityType<BeehiveBlockEntity> BEEHIVE = register("beehive", Builder.of(BeehiveBlockEntity::new, new Block[] { Blocks.BEE_NEST, Blocks.BEEHIVE }));
/*  65 */   public static final BlockEntityType<SculkSensorBlockEntity> SCULK_SENSOR = register("sculk_sensor", Builder.of(SculkSensorBlockEntity::new, new Block[] { Blocks.SCULK_SENSOR }));
/*  66 */   public static final BlockEntityType<CalibratedSculkSensorBlockEntity> CALIBRATED_SCULK_SENSOR = register("calibrated_sculk_sensor", Builder.of(CalibratedSculkSensorBlockEntity::new, new Block[] { Blocks.CALIBRATED_SCULK_SENSOR }));
/*  67 */   public static final BlockEntityType<SculkCatalystBlockEntity> SCULK_CATALYST = register("sculk_catalyst", Builder.of(SculkCatalystBlockEntity::new, new Block[] { Blocks.SCULK_CATALYST }));
/*  68 */   public static final BlockEntityType<SculkShriekerBlockEntity> SCULK_SHRIEKER = register("sculk_shrieker", Builder.of(SculkShriekerBlockEntity::new, new Block[] { Blocks.SCULK_SHRIEKER }));
/*  69 */   public static final BlockEntityType<ChiseledBookShelfBlockEntity> CHISELED_BOOKSHELF = register("chiseled_bookshelf", Builder.of(ChiseledBookShelfBlockEntity::new, new Block[] { Blocks.CHISELED_BOOKSHELF }));
/*  70 */   public static final BlockEntityType<BrushableBlockEntity> BRUSHABLE_BLOCK = register("brushable_block", Builder.of(BrushableBlockEntity::new, new Block[] { Blocks.SUSPICIOUS_SAND, Blocks.SUSPICIOUS_GRAVEL }));
/*  71 */   public static final BlockEntityType<DecoratedPotBlockEntity> DECORATED_POT = register("decorated_pot", Builder.of(DecoratedPotBlockEntity::new, new Block[] { Blocks.DECORATED_POT }));
/*  72 */   public static final BlockEntityType<CrafterBlockEntity> CRAFTER = register("crafter", Builder.of(CrafterBlockEntity::new, new Block[] { Blocks.CRAFTER }));
/*  73 */   public static final BlockEntityType<TrialSpawnerBlockEntity> TRIAL_SPAWNER = register("trial_spawner", Builder.of(TrialSpawnerBlockEntity::new, new Block[] { Blocks.TRIAL_SPAWNER })); private final BlockEntitySupplier<? extends T> factory;
/*     */   
/*     */   private static <T extends BlockEntity> BlockEntityType<T> register(String $$0, Builder<T> $$1) {
/*  76 */     if ($$1.validBlocks.isEmpty()) {
/*  77 */       LOGGER.warn("Block entity type {} requires at least one valid block to be defined!", $$0);
/*     */     }
/*  79 */     Type<?> $$2 = Util.fetchChoiceType(References.BLOCK_ENTITY, $$0);
/*  80 */     return (BlockEntityType<T>)Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, $$0, $$1.build($$2));
/*     */   }
/*     */ 
/*     */   
/*     */   private final Set<Block> validBlocks;
/*     */   
/*     */   private final Type<?> dataType;
/*  87 */   private final Holder.Reference<BlockEntityType<?>> builtInRegistryHolder = BuiltInRegistries.BLOCK_ENTITY_TYPE.createIntrusiveHolder(this);
/*     */   
/*     */   public BlockEntityType(BlockEntitySupplier<? extends T> $$0, Set<Block> $$1, Type<?> $$2) {
/*  90 */     this.factory = $$0;
/*  91 */     this.validBlocks = $$1;
/*  92 */     this.dataType = $$2;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public T create(BlockPos $$0, BlockState $$1) {
/*  97 */     return this.factory.create($$0, $$1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValid(BlockState $$0) {
/* 104 */     return this.validBlocks.contains($$0.getBlock());
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Holder.Reference<BlockEntityType<?>> builtInRegistryHolder() {
/* 109 */     return this.builtInRegistryHolder;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Builder<T extends BlockEntity>
/*     */   {
/*     */     private final BlockEntityType.BlockEntitySupplier<? extends T> factory;
/*     */     
/*     */     final Set<Block> validBlocks;
/*     */ 
/*     */     
/*     */     private Builder(BlockEntityType.BlockEntitySupplier<? extends T> $$0, Set<Block> $$1) {
/* 122 */       this.factory = $$0;
/* 123 */       this.validBlocks = $$1;
/*     */     }
/*     */     
/*     */     public static <T extends BlockEntity> Builder<T> of(BlockEntityType.BlockEntitySupplier<? extends T> $$0, Block... $$1) {
/* 127 */       return new Builder<>($$0, (Set<Block>)ImmutableSet.copyOf((Object[])$$1));
/*     */     }
/*     */     
/*     */     public BlockEntityType<T> build(Type<?> $$0) {
/* 131 */       return new BlockEntityType<>(this.factory, this.validBlocks, $$0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public T getBlockEntity(BlockGetter $$0, BlockPos $$1) {
/* 138 */     BlockEntity $$2 = $$0.getBlockEntity($$1);
/* 139 */     if ($$2 == null || $$2.getType() != this) {
/* 140 */       return null;
/*     */     }
/* 142 */     return (T)$$2;
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   private static interface BlockEntitySupplier<T extends BlockEntity> {
/*     */     T create(BlockPos param1BlockPos, BlockState param1BlockState);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\BlockEntityType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */