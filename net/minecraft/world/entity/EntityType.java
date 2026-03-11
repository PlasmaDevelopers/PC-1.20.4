/*     */ package net.minecraft.world.entity;
/*     */ 
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.Spliterator;
/*     */ import java.util.UUID;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Function;
/*     */ import java.util.stream.Stream;
/*     */ import java.util.stream.StreamSupport;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.HolderSet;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.tags.TagKey;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.datafix.fixes.References;
/*     */ import net.minecraft.world.entity.ambient.Bat;
/*     */ import net.minecraft.world.entity.animal.Bee;
/*     */ import net.minecraft.world.entity.animal.Cat;
/*     */ import net.minecraft.world.entity.animal.Chicken;
/*     */ import net.minecraft.world.entity.animal.Cod;
/*     */ import net.minecraft.world.entity.animal.Cow;
/*     */ import net.minecraft.world.entity.animal.Dolphin;
/*     */ import net.minecraft.world.entity.animal.Fox;
/*     */ import net.minecraft.world.entity.animal.IronGolem;
/*     */ import net.minecraft.world.entity.animal.MushroomCow;
/*     */ import net.minecraft.world.entity.animal.Ocelot;
/*     */ import net.minecraft.world.entity.animal.Panda;
/*     */ import net.minecraft.world.entity.animal.Parrot;
/*     */ import net.minecraft.world.entity.animal.Pig;
/*     */ import net.minecraft.world.entity.animal.PolarBear;
/*     */ import net.minecraft.world.entity.animal.Pufferfish;
/*     */ import net.minecraft.world.entity.animal.Rabbit;
/*     */ import net.minecraft.world.entity.animal.Salmon;
/*     */ import net.minecraft.world.entity.animal.Sheep;
/*     */ import net.minecraft.world.entity.animal.SnowGolem;
/*     */ import net.minecraft.world.entity.animal.Squid;
/*     */ import net.minecraft.world.entity.animal.TropicalFish;
/*     */ import net.minecraft.world.entity.animal.Turtle;
/*     */ import net.minecraft.world.entity.animal.Wolf;
/*     */ import net.minecraft.world.entity.animal.allay.Allay;
/*     */ import net.minecraft.world.entity.animal.axolotl.Axolotl;
/*     */ import net.minecraft.world.entity.animal.camel.Camel;
/*     */ import net.minecraft.world.entity.animal.frog.Frog;
/*     */ import net.minecraft.world.entity.animal.frog.Tadpole;
/*     */ import net.minecraft.world.entity.animal.goat.Goat;
/*     */ import net.minecraft.world.entity.animal.horse.Donkey;
/*     */ import net.minecraft.world.entity.animal.horse.Horse;
/*     */ import net.minecraft.world.entity.animal.horse.Llama;
/*     */ import net.minecraft.world.entity.animal.horse.Mule;
/*     */ import net.minecraft.world.entity.animal.horse.SkeletonHorse;
/*     */ import net.minecraft.world.entity.animal.horse.TraderLlama;
/*     */ import net.minecraft.world.entity.animal.horse.ZombieHorse;
/*     */ import net.minecraft.world.entity.animal.sniffer.Sniffer;
/*     */ import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
/*     */ import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
/*     */ import net.minecraft.world.entity.boss.wither.WitherBoss;
/*     */ import net.minecraft.world.entity.decoration.ArmorStand;
/*     */ import net.minecraft.world.entity.decoration.GlowItemFrame;
/*     */ import net.minecraft.world.entity.decoration.ItemFrame;
/*     */ import net.minecraft.world.entity.decoration.LeashFenceKnotEntity;
/*     */ import net.minecraft.world.entity.decoration.Painting;
/*     */ import net.minecraft.world.entity.item.FallingBlockEntity;
/*     */ import net.minecraft.world.entity.item.ItemEntity;
/*     */ import net.minecraft.world.entity.item.PrimedTnt;
/*     */ import net.minecraft.world.entity.monster.Blaze;
/*     */ import net.minecraft.world.entity.monster.CaveSpider;
/*     */ import net.minecraft.world.entity.monster.Creeper;
/*     */ import net.minecraft.world.entity.monster.Drowned;
/*     */ import net.minecraft.world.entity.monster.ElderGuardian;
/*     */ import net.minecraft.world.entity.monster.EnderMan;
/*     */ import net.minecraft.world.entity.monster.Endermite;
/*     */ import net.minecraft.world.entity.monster.Evoker;
/*     */ import net.minecraft.world.entity.monster.Ghast;
/*     */ import net.minecraft.world.entity.monster.Giant;
/*     */ import net.minecraft.world.entity.monster.Guardian;
/*     */ import net.minecraft.world.entity.monster.Husk;
/*     */ import net.minecraft.world.entity.monster.Illusioner;
/*     */ import net.minecraft.world.entity.monster.MagmaCube;
/*     */ import net.minecraft.world.entity.monster.Phantom;
/*     */ import net.minecraft.world.entity.monster.Pillager;
/*     */ import net.minecraft.world.entity.monster.Ravager;
/*     */ import net.minecraft.world.entity.monster.Shulker;
/*     */ import net.minecraft.world.entity.monster.Silverfish;
/*     */ import net.minecraft.world.entity.monster.Skeleton;
/*     */ import net.minecraft.world.entity.monster.Slime;
/*     */ import net.minecraft.world.entity.monster.Spider;
/*     */ import net.minecraft.world.entity.monster.Stray;
/*     */ import net.minecraft.world.entity.monster.Strider;
/*     */ import net.minecraft.world.entity.monster.Vex;
/*     */ import net.minecraft.world.entity.monster.Vindicator;
/*     */ import net.minecraft.world.entity.monster.Witch;
/*     */ import net.minecraft.world.entity.monster.WitherSkeleton;
/*     */ import net.minecraft.world.entity.monster.Zoglin;
/*     */ import net.minecraft.world.entity.monster.Zombie;
/*     */ import net.minecraft.world.entity.monster.ZombieVillager;
/*     */ import net.minecraft.world.entity.monster.ZombifiedPiglin;
/*     */ import net.minecraft.world.entity.monster.breeze.Breeze;
/*     */ import net.minecraft.world.entity.monster.hoglin.Hoglin;
/*     */ import net.minecraft.world.entity.monster.piglin.Piglin;
/*     */ import net.minecraft.world.entity.monster.piglin.PiglinBrute;
/*     */ import net.minecraft.world.entity.monster.warden.Warden;
/*     */ import net.minecraft.world.entity.npc.Villager;
/*     */ import net.minecraft.world.entity.npc.WanderingTrader;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.entity.projectile.Arrow;
/*     */ import net.minecraft.world.entity.projectile.DragonFireball;
/*     */ import net.minecraft.world.entity.projectile.EvokerFangs;
/*     */ import net.minecraft.world.entity.projectile.EyeOfEnder;
/*     */ import net.minecraft.world.entity.projectile.FireworkRocketEntity;
/*     */ import net.minecraft.world.entity.projectile.FishingHook;
/*     */ import net.minecraft.world.entity.projectile.LargeFireball;
/*     */ import net.minecraft.world.entity.projectile.LlamaSpit;
/*     */ import net.minecraft.world.entity.projectile.ShulkerBullet;
/*     */ import net.minecraft.world.entity.projectile.SmallFireball;
/*     */ import net.minecraft.world.entity.projectile.Snowball;
/*     */ import net.minecraft.world.entity.projectile.SpectralArrow;
/*     */ import net.minecraft.world.entity.projectile.ThrownEgg;
/*     */ import net.minecraft.world.entity.projectile.ThrownEnderpearl;
/*     */ import net.minecraft.world.entity.projectile.ThrownExperienceBottle;
/*     */ import net.minecraft.world.entity.projectile.ThrownPotion;
/*     */ import net.minecraft.world.entity.projectile.ThrownTrident;
/*     */ import net.minecraft.world.entity.projectile.WindCharge;
/*     */ import net.minecraft.world.entity.projectile.WitherSkull;
/*     */ import net.minecraft.world.entity.vehicle.Boat;
/*     */ import net.minecraft.world.entity.vehicle.ChestBoat;
/*     */ import net.minecraft.world.entity.vehicle.Minecart;
/*     */ import net.minecraft.world.entity.vehicle.MinecartChest;
/*     */ import net.minecraft.world.entity.vehicle.MinecartCommandBlock;
/*     */ import net.minecraft.world.entity.vehicle.MinecartFurnace;
/*     */ import net.minecraft.world.entity.vehicle.MinecartHopper;
/*     */ import net.minecraft.world.entity.vehicle.MinecartSpawner;
/*     */ import net.minecraft.world.entity.vehicle.MinecartTNT;
/*     */ import net.minecraft.world.flag.FeatureElement;
/*     */ import net.minecraft.world.flag.FeatureFlag;
/*     */ import net.minecraft.world.flag.FeatureFlagSet;
/*     */ import net.minecraft.world.flag.FeatureFlags;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.entity.EntityTypeTest;
/*     */ import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.shapes.Shapes;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class EntityType<T extends Entity> implements FeatureElement, EntityTypeTest<Entity, T> {
/* 167 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   public static final String ENTITY_TAG = "EntityTag";
/* 169 */   private final Holder.Reference<EntityType<?>> builtInRegistryHolder = BuiltInRegistries.ENTITY_TYPE.createIntrusiveHolder(this); private static final float MAGIC_HORSE_WIDTH = 1.3964844F; private static final int DISPLAY_TRACKING_RANGE = 10;
/*     */   
/*     */   private static <T extends Entity> EntityType<T> register(String $$0, Builder<T> $$1) {
/* 172 */     return (EntityType<T>)Registry.register((Registry)BuiltInRegistries.ENTITY_TYPE, $$0, $$1.build($$0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 179 */   public static final EntityType<Allay> ALLAY = register("allay", Builder.<Allay>of(Allay::new, MobCategory.CREATURE).sized(0.35F, 0.6F).clientTrackingRange(8).updateInterval(2));
/* 180 */   public static final EntityType<AreaEffectCloud> AREA_EFFECT_CLOUD = register("area_effect_cloud", Builder.<AreaEffectCloud>of(AreaEffectCloud::new, MobCategory.MISC).fireImmune().sized(6.0F, 0.5F).clientTrackingRange(10).updateInterval(2147483647));
/* 181 */   public static final EntityType<ArmorStand> ARMOR_STAND = register("armor_stand", Builder.<ArmorStand>of(ArmorStand::new, MobCategory.MISC).sized(0.5F, 1.975F).clientTrackingRange(10));
/* 182 */   public static final EntityType<Arrow> ARROW = register("arrow", Builder.<Arrow>of(Arrow::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20));
/* 183 */   public static final EntityType<Axolotl> AXOLOTL = register("axolotl", Builder.<Axolotl>of(Axolotl::new, MobCategory.AXOLOTLS).sized(0.75F, 0.42F).clientTrackingRange(10));
/* 184 */   public static final EntityType<Bat> BAT = register("bat", Builder.<Bat>of(Bat::new, MobCategory.AMBIENT).sized(0.5F, 0.9F).clientTrackingRange(5));
/* 185 */   public static final EntityType<Bee> BEE = register("bee", Builder.<Bee>of(Bee::new, MobCategory.CREATURE).sized(0.7F, 0.6F).clientTrackingRange(8));
/* 186 */   public static final EntityType<Blaze> BLAZE = register("blaze", Builder.<Blaze>of(Blaze::new, MobCategory.MONSTER).fireImmune().sized(0.6F, 1.8F).clientTrackingRange(8));
/* 187 */   public static final EntityType<Display.BlockDisplay> BLOCK_DISPLAY = register("block_display", Builder.<Display.BlockDisplay>of(BlockDisplay::new, MobCategory.MISC).sized(0.0F, 0.0F).clientTrackingRange(10).updateInterval(1));
/* 188 */   public static final EntityType<Boat> BOAT = register("boat", Builder.<Boat>of(Boat::new, MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10));
/* 189 */   public static final EntityType<Breeze> BREEZE = register("breeze", Builder.<Breeze>of(Breeze::new, MobCategory.MONSTER).sized(0.6F, 1.7F).clientTrackingRange(10).requiredFeatures(new FeatureFlag[] { FeatureFlags.UPDATE_1_21 }));
/* 190 */   public static final EntityType<Camel> CAMEL = register("camel", Builder.<Camel>of(Camel::new, MobCategory.CREATURE).sized(1.7F, 2.375F).clientTrackingRange(10));
/* 191 */   public static final EntityType<Cat> CAT = register("cat", Builder.<Cat>of(Cat::new, MobCategory.CREATURE).sized(0.6F, 0.7F).clientTrackingRange(8));
/* 192 */   public static final EntityType<CaveSpider> CAVE_SPIDER = register("cave_spider", Builder.<CaveSpider>of(CaveSpider::new, MobCategory.MONSTER).sized(0.7F, 0.5F).clientTrackingRange(8));
/* 193 */   public static final EntityType<ChestBoat> CHEST_BOAT = register("chest_boat", Builder.<ChestBoat>of(ChestBoat::new, MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10));
/* 194 */   public static final EntityType<MinecartChest> CHEST_MINECART = register("chest_minecart", Builder.<MinecartChest>of(MinecartChest::new, MobCategory.MISC).sized(0.98F, 0.7F).clientTrackingRange(8));
/* 195 */   public static final EntityType<Chicken> CHICKEN = register("chicken", Builder.<Chicken>of(Chicken::new, MobCategory.CREATURE).sized(0.4F, 0.7F).clientTrackingRange(10));
/* 196 */   public static final EntityType<Cod> COD = register("cod", Builder.<Cod>of(Cod::new, MobCategory.WATER_AMBIENT).sized(0.5F, 0.3F).clientTrackingRange(4));
/* 197 */   public static final EntityType<MinecartCommandBlock> COMMAND_BLOCK_MINECART = register("command_block_minecart", Builder.<MinecartCommandBlock>of(MinecartCommandBlock::new, MobCategory.MISC).sized(0.98F, 0.7F).clientTrackingRange(8));
/* 198 */   public static final EntityType<Cow> COW = register("cow", Builder.<Cow>of(Cow::new, MobCategory.CREATURE).sized(0.9F, 1.4F).clientTrackingRange(10));
/* 199 */   public static final EntityType<Creeper> CREEPER = register("creeper", Builder.<Creeper>of(Creeper::new, MobCategory.MONSTER).sized(0.6F, 1.7F).clientTrackingRange(8));
/* 200 */   public static final EntityType<Dolphin> DOLPHIN = register("dolphin", Builder.<Dolphin>of(Dolphin::new, MobCategory.WATER_CREATURE).sized(0.9F, 0.6F));
/* 201 */   public static final EntityType<Donkey> DONKEY = register("donkey", Builder.<Donkey>of(Donkey::new, MobCategory.CREATURE).sized(1.3964844F, 1.5F).clientTrackingRange(10));
/* 202 */   public static final EntityType<DragonFireball> DRAGON_FIREBALL = register("dragon_fireball", Builder.<DragonFireball>of(DragonFireball::new, MobCategory.MISC).sized(1.0F, 1.0F).clientTrackingRange(4).updateInterval(10));
/* 203 */   public static final EntityType<Drowned> DROWNED = register("drowned", Builder.<Drowned>of(Drowned::new, MobCategory.MONSTER).sized(0.6F, 1.95F).clientTrackingRange(8));
/* 204 */   public static final EntityType<ThrownEgg> EGG = register("egg", Builder.<ThrownEgg>of(ThrownEgg::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10));
/* 205 */   public static final EntityType<ElderGuardian> ELDER_GUARDIAN = register("elder_guardian", Builder.<ElderGuardian>of(ElderGuardian::new, MobCategory.MONSTER).sized(1.9975F, 1.9975F).clientTrackingRange(10));
/* 206 */   public static final EntityType<EndCrystal> END_CRYSTAL = register("end_crystal", Builder.<EndCrystal>of(EndCrystal::new, MobCategory.MISC).sized(2.0F, 2.0F).clientTrackingRange(16).updateInterval(2147483647));
/* 207 */   public static final EntityType<EnderDragon> ENDER_DRAGON = register("ender_dragon", Builder.<EnderDragon>of(EnderDragon::new, MobCategory.MONSTER).fireImmune().sized(16.0F, 8.0F).clientTrackingRange(10));
/* 208 */   public static final EntityType<ThrownEnderpearl> ENDER_PEARL = register("ender_pearl", Builder.<ThrownEnderpearl>of(ThrownEnderpearl::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10));
/* 209 */   public static final EntityType<EnderMan> ENDERMAN = register("enderman", Builder.<EnderMan>of(EnderMan::new, MobCategory.MONSTER).sized(0.6F, 2.9F).clientTrackingRange(8));
/* 210 */   public static final EntityType<Endermite> ENDERMITE = register("endermite", Builder.<Endermite>of(Endermite::new, MobCategory.MONSTER).sized(0.4F, 0.3F).clientTrackingRange(8));
/* 211 */   public static final EntityType<Evoker> EVOKER = register("evoker", Builder.<Evoker>of(Evoker::new, MobCategory.MONSTER).sized(0.6F, 1.95F).clientTrackingRange(8));
/* 212 */   public static final EntityType<EvokerFangs> EVOKER_FANGS = register("evoker_fangs", Builder.<EvokerFangs>of(EvokerFangs::new, MobCategory.MISC).sized(0.5F, 0.8F).clientTrackingRange(6).updateInterval(2));
/* 213 */   public static final EntityType<ThrownExperienceBottle> EXPERIENCE_BOTTLE = register("experience_bottle", Builder.<ThrownExperienceBottle>of(ThrownExperienceBottle::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10));
/* 214 */   public static final EntityType<ExperienceOrb> EXPERIENCE_ORB = register("experience_orb", Builder.<ExperienceOrb>of(ExperienceOrb::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(6).updateInterval(20));
/* 215 */   public static final EntityType<EyeOfEnder> EYE_OF_ENDER = register("eye_of_ender", Builder.<EyeOfEnder>of(EyeOfEnder::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(4));
/* 216 */   public static final EntityType<FallingBlockEntity> FALLING_BLOCK = register("falling_block", Builder.<FallingBlockEntity>of(FallingBlockEntity::new, MobCategory.MISC).sized(0.98F, 0.98F).clientTrackingRange(10).updateInterval(20));
/* 217 */   public static final EntityType<FireworkRocketEntity> FIREWORK_ROCKET = register("firework_rocket", Builder.<FireworkRocketEntity>of(FireworkRocketEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10));
/* 218 */   public static final EntityType<Fox> FOX = register("fox", Builder.<Fox>of(Fox::new, MobCategory.CREATURE).sized(0.6F, 0.7F).clientTrackingRange(8).immuneTo(new Block[] { Blocks.SWEET_BERRY_BUSH }));
/* 219 */   public static final EntityType<Frog> FROG = register("frog", Builder.<Frog>of(Frog::new, MobCategory.CREATURE).sized(0.5F, 0.5F).clientTrackingRange(10));
/* 220 */   public static final EntityType<MinecartFurnace> FURNACE_MINECART = register("furnace_minecart", Builder.<MinecartFurnace>of(MinecartFurnace::new, MobCategory.MISC).sized(0.98F, 0.7F).clientTrackingRange(8));
/* 221 */   public static final EntityType<Ghast> GHAST = register("ghast", Builder.<Ghast>of(Ghast::new, MobCategory.MONSTER).fireImmune().sized(4.0F, 4.0F).clientTrackingRange(10));
/* 222 */   public static final EntityType<Giant> GIANT = register("giant", Builder.<Giant>of(Giant::new, MobCategory.MONSTER).sized(3.6F, 12.0F).clientTrackingRange(10));
/* 223 */   public static final EntityType<GlowItemFrame> GLOW_ITEM_FRAME = register("glow_item_frame", Builder.<GlowItemFrame>of(GlowItemFrame::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(10).updateInterval(2147483647));
/* 224 */   public static final EntityType<GlowSquid> GLOW_SQUID = register("glow_squid", Builder.<GlowSquid>of(GlowSquid::new, MobCategory.UNDERGROUND_WATER_CREATURE).sized(0.8F, 0.8F).clientTrackingRange(10));
/* 225 */   public static final EntityType<Goat> GOAT = register("goat", Builder.<Goat>of(Goat::new, MobCategory.CREATURE).sized(0.9F, 1.3F).clientTrackingRange(10));
/* 226 */   public static final EntityType<Guardian> GUARDIAN = register("guardian", Builder.<Guardian>of(Guardian::new, MobCategory.MONSTER).sized(0.85F, 0.85F).clientTrackingRange(8));
/* 227 */   public static final EntityType<Hoglin> HOGLIN = register("hoglin", Builder.<Hoglin>of(Hoglin::new, MobCategory.MONSTER).sized(1.3964844F, 1.4F).clientTrackingRange(8));
/* 228 */   public static final EntityType<MinecartHopper> HOPPER_MINECART = register("hopper_minecart", Builder.<MinecartHopper>of(MinecartHopper::new, MobCategory.MISC).sized(0.98F, 0.7F).clientTrackingRange(8));
/* 229 */   public static final EntityType<Horse> HORSE = register("horse", Builder.<Horse>of(Horse::new, MobCategory.CREATURE).sized(1.3964844F, 1.6F).clientTrackingRange(10));
/* 230 */   public static final EntityType<Husk> HUSK = register("husk", Builder.<Husk>of(Husk::new, MobCategory.MONSTER).sized(0.6F, 1.95F).clientTrackingRange(8));
/* 231 */   public static final EntityType<Illusioner> ILLUSIONER = register("illusioner", Builder.<Illusioner>of(Illusioner::new, MobCategory.MONSTER).sized(0.6F, 1.95F).clientTrackingRange(8));
/* 232 */   public static final EntityType<Interaction> INTERACTION = register("interaction", Builder.<Interaction>of(Interaction::new, MobCategory.MISC).sized(0.0F, 0.0F).clientTrackingRange(10));
/* 233 */   public static final EntityType<IronGolem> IRON_GOLEM = register("iron_golem", Builder.<IronGolem>of(IronGolem::new, MobCategory.MISC).sized(1.4F, 2.7F).clientTrackingRange(10));
/* 234 */   public static final EntityType<ItemEntity> ITEM = register("item", Builder.<ItemEntity>of(ItemEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(6).updateInterval(20));
/* 235 */   public static final EntityType<Display.ItemDisplay> ITEM_DISPLAY = register("item_display", Builder.<Display.ItemDisplay>of(ItemDisplay::new, MobCategory.MISC).sized(0.0F, 0.0F).clientTrackingRange(10).updateInterval(1));
/* 236 */   public static final EntityType<ItemFrame> ITEM_FRAME = register("item_frame", Builder.<ItemFrame>of(ItemFrame::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(10).updateInterval(2147483647));
/* 237 */   public static final EntityType<LargeFireball> FIREBALL = register("fireball", Builder.<LargeFireball>of(LargeFireball::new, MobCategory.MISC).sized(1.0F, 1.0F).clientTrackingRange(4).updateInterval(10));
/* 238 */   public static final EntityType<LeashFenceKnotEntity> LEASH_KNOT = register("leash_knot", Builder.<LeashFenceKnotEntity>of(LeashFenceKnotEntity::new, MobCategory.MISC).noSave().sized(0.375F, 0.5F).clientTrackingRange(10).updateInterval(2147483647));
/* 239 */   public static final EntityType<LightningBolt> LIGHTNING_BOLT = register("lightning_bolt", Builder.<LightningBolt>of(LightningBolt::new, MobCategory.MISC).noSave().sized(0.0F, 0.0F).clientTrackingRange(16).updateInterval(2147483647));
/* 240 */   public static final EntityType<Llama> LLAMA = register("llama", Builder.<Llama>of(Llama::new, MobCategory.CREATURE).sized(0.9F, 1.87F).clientTrackingRange(10));
/* 241 */   public static final EntityType<LlamaSpit> LLAMA_SPIT = register("llama_spit", Builder.<LlamaSpit>of(LlamaSpit::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10));
/* 242 */   public static final EntityType<MagmaCube> MAGMA_CUBE = register("magma_cube", Builder.<MagmaCube>of(MagmaCube::new, MobCategory.MONSTER).fireImmune().sized(2.04F, 2.04F).clientTrackingRange(8));
/* 243 */   public static final EntityType<Marker> MARKER = register("marker", Builder.<Marker>of(Marker::new, MobCategory.MISC).sized(0.0F, 0.0F).clientTrackingRange(0));
/* 244 */   public static final EntityType<Minecart> MINECART = register("minecart", Builder.<Minecart>of(Minecart::new, MobCategory.MISC).sized(0.98F, 0.7F).clientTrackingRange(8));
/* 245 */   public static final EntityType<MushroomCow> MOOSHROOM = register("mooshroom", Builder.<MushroomCow>of(MushroomCow::new, MobCategory.CREATURE).sized(0.9F, 1.4F).clientTrackingRange(10));
/* 246 */   public static final EntityType<Mule> MULE = register("mule", Builder.<Mule>of(Mule::new, MobCategory.CREATURE).sized(1.3964844F, 1.6F).clientTrackingRange(8));
/* 247 */   public static final EntityType<Ocelot> OCELOT = register("ocelot", Builder.<Ocelot>of(Ocelot::new, MobCategory.CREATURE).sized(0.6F, 0.7F).clientTrackingRange(10));
/* 248 */   public static final EntityType<Painting> PAINTING = register("painting", Builder.<Painting>of(Painting::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(10).updateInterval(2147483647));
/* 249 */   public static final EntityType<Panda> PANDA = register("panda", Builder.<Panda>of(Panda::new, MobCategory.CREATURE).sized(1.3F, 1.25F).clientTrackingRange(10));
/* 250 */   public static final EntityType<Parrot> PARROT = register("parrot", Builder.<Parrot>of(Parrot::new, MobCategory.CREATURE).sized(0.5F, 0.9F).clientTrackingRange(8));
/* 251 */   public static final EntityType<Phantom> PHANTOM = register("phantom", Builder.<Phantom>of(Phantom::new, MobCategory.MONSTER).sized(0.9F, 0.5F).clientTrackingRange(8));
/* 252 */   public static final EntityType<Pig> PIG = register("pig", Builder.<Pig>of(Pig::new, MobCategory.CREATURE).sized(0.9F, 0.9F).clientTrackingRange(10));
/* 253 */   public static final EntityType<Piglin> PIGLIN = register("piglin", Builder.<Piglin>of(Piglin::new, MobCategory.MONSTER).sized(0.6F, 1.95F).clientTrackingRange(8));
/* 254 */   public static final EntityType<PiglinBrute> PIGLIN_BRUTE = register("piglin_brute", Builder.<PiglinBrute>of(PiglinBrute::new, MobCategory.MONSTER).sized(0.6F, 1.95F).clientTrackingRange(8));
/* 255 */   public static final EntityType<Pillager> PILLAGER = register("pillager", Builder.<Pillager>of(Pillager::new, MobCategory.MONSTER).canSpawnFarFromPlayer().sized(0.6F, 1.95F).clientTrackingRange(8));
/* 256 */   public static final EntityType<PolarBear> POLAR_BEAR = register("polar_bear", Builder.<PolarBear>of(PolarBear::new, MobCategory.CREATURE).immuneTo(new Block[] { Blocks.POWDER_SNOW }).sized(1.4F, 1.4F).clientTrackingRange(10));
/* 257 */   public static final EntityType<ThrownPotion> POTION = register("potion", Builder.<ThrownPotion>of(ThrownPotion::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10));
/* 258 */   public static final EntityType<Pufferfish> PUFFERFISH = register("pufferfish", Builder.<Pufferfish>of(Pufferfish::new, MobCategory.WATER_AMBIENT).sized(0.7F, 0.7F).clientTrackingRange(4));
/* 259 */   public static final EntityType<Rabbit> RABBIT = register("rabbit", Builder.<Rabbit>of(Rabbit::new, MobCategory.CREATURE).sized(0.4F, 0.5F).clientTrackingRange(8));
/* 260 */   public static final EntityType<Ravager> RAVAGER = register("ravager", Builder.<Ravager>of(Ravager::new, MobCategory.MONSTER).sized(1.95F, 2.2F).clientTrackingRange(10));
/* 261 */   public static final EntityType<Salmon> SALMON = register("salmon", Builder.<Salmon>of(Salmon::new, MobCategory.WATER_AMBIENT).sized(0.7F, 0.4F).clientTrackingRange(4));
/* 262 */   public static final EntityType<Sheep> SHEEP = register("sheep", Builder.<Sheep>of(Sheep::new, MobCategory.CREATURE).sized(0.9F, 1.3F).clientTrackingRange(10));
/* 263 */   public static final EntityType<Shulker> SHULKER = register("shulker", Builder.<Shulker>of(Shulker::new, MobCategory.MONSTER).fireImmune().canSpawnFarFromPlayer().sized(1.0F, 1.0F).clientTrackingRange(10));
/* 264 */   public static final EntityType<ShulkerBullet> SHULKER_BULLET = register("shulker_bullet", Builder.<ShulkerBullet>of(ShulkerBullet::new, MobCategory.MISC).sized(0.3125F, 0.3125F).clientTrackingRange(8));
/* 265 */   public static final EntityType<Silverfish> SILVERFISH = register("silverfish", Builder.<Silverfish>of(Silverfish::new, MobCategory.MONSTER).sized(0.4F, 0.3F).clientTrackingRange(8));
/* 266 */   public static final EntityType<Skeleton> SKELETON = register("skeleton", Builder.<Skeleton>of(Skeleton::new, MobCategory.MONSTER).sized(0.6F, 1.99F).clientTrackingRange(8));
/* 267 */   public static final EntityType<SkeletonHorse> SKELETON_HORSE = register("skeleton_horse", Builder.<SkeletonHorse>of(SkeletonHorse::new, MobCategory.CREATURE).sized(1.3964844F, 1.6F).clientTrackingRange(10));
/* 268 */   public static final EntityType<Slime> SLIME = register("slime", Builder.<Slime>of(Slime::new, MobCategory.MONSTER).sized(2.04F, 2.04F).clientTrackingRange(10));
/* 269 */   public static final EntityType<SmallFireball> SMALL_FIREBALL = register("small_fireball", Builder.<SmallFireball>of(SmallFireball::new, MobCategory.MISC).sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10));
/* 270 */   public static final EntityType<Sniffer> SNIFFER = register("sniffer", Builder.<Sniffer>of(Sniffer::new, MobCategory.CREATURE).sized(1.9F, 1.75F).clientTrackingRange(10));
/* 271 */   public static final EntityType<SnowGolem> SNOW_GOLEM = register("snow_golem", Builder.<SnowGolem>of(SnowGolem::new, MobCategory.MISC).immuneTo(new Block[] { Blocks.POWDER_SNOW }).sized(0.7F, 1.9F).clientTrackingRange(8));
/* 272 */   public static final EntityType<Snowball> SNOWBALL = register("snowball", Builder.<Snowball>of(Snowball::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10));
/* 273 */   public static final EntityType<MinecartSpawner> SPAWNER_MINECART = register("spawner_minecart", Builder.<MinecartSpawner>of(MinecartSpawner::new, MobCategory.MISC).sized(0.98F, 0.7F).clientTrackingRange(8));
/* 274 */   public static final EntityType<SpectralArrow> SPECTRAL_ARROW = register("spectral_arrow", Builder.<SpectralArrow>of(SpectralArrow::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20));
/* 275 */   public static final EntityType<Spider> SPIDER = register("spider", Builder.<Spider>of(Spider::new, MobCategory.MONSTER).sized(1.4F, 0.9F).clientTrackingRange(8));
/* 276 */   public static final EntityType<Squid> SQUID = register("squid", Builder.<Squid>of(Squid::new, MobCategory.WATER_CREATURE).sized(0.8F, 0.8F).clientTrackingRange(8));
/* 277 */   public static final EntityType<Stray> STRAY = register("stray", Builder.<Stray>of(Stray::new, MobCategory.MONSTER).sized(0.6F, 1.99F).immuneTo(new Block[] { Blocks.POWDER_SNOW }).clientTrackingRange(8));
/* 278 */   public static final EntityType<Strider> STRIDER = register("strider", Builder.<Strider>of(Strider::new, MobCategory.CREATURE).fireImmune().sized(0.9F, 1.7F).clientTrackingRange(10));
/* 279 */   public static final EntityType<Tadpole> TADPOLE = register("tadpole", Builder.<Tadpole>of(Tadpole::new, MobCategory.CREATURE).sized(Tadpole.HITBOX_WIDTH, Tadpole.HITBOX_HEIGHT).clientTrackingRange(10));
/* 280 */   public static final EntityType<Display.TextDisplay> TEXT_DISPLAY = register("text_display", Builder.<Display.TextDisplay>of(TextDisplay::new, MobCategory.MISC).sized(0.0F, 0.0F).clientTrackingRange(10).updateInterval(1));
/* 281 */   public static final EntityType<PrimedTnt> TNT = register("tnt", Builder.<PrimedTnt>of(PrimedTnt::new, MobCategory.MISC).fireImmune().sized(0.98F, 0.98F).clientTrackingRange(10).updateInterval(10));
/* 282 */   public static final EntityType<MinecartTNT> TNT_MINECART = register("tnt_minecart", Builder.<MinecartTNT>of(MinecartTNT::new, MobCategory.MISC).sized(0.98F, 0.7F).clientTrackingRange(8));
/* 283 */   public static final EntityType<TraderLlama> TRADER_LLAMA = register("trader_llama", Builder.<TraderLlama>of(TraderLlama::new, MobCategory.CREATURE).sized(0.9F, 1.87F).clientTrackingRange(10));
/* 284 */   public static final EntityType<ThrownTrident> TRIDENT = register("trident", Builder.<ThrownTrident>of(ThrownTrident::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20));
/* 285 */   public static final EntityType<TropicalFish> TROPICAL_FISH = register("tropical_fish", Builder.<TropicalFish>of(TropicalFish::new, MobCategory.WATER_AMBIENT).sized(0.5F, 0.4F).clientTrackingRange(4));
/* 286 */   public static final EntityType<Turtle> TURTLE = register("turtle", Builder.<Turtle>of(Turtle::new, MobCategory.CREATURE).sized(1.2F, 0.4F).clientTrackingRange(10));
/* 287 */   public static final EntityType<Vex> VEX = register("vex", Builder.<Vex>of(Vex::new, MobCategory.MONSTER).fireImmune().sized(0.4F, 0.8F).clientTrackingRange(8));
/* 288 */   public static final EntityType<Villager> VILLAGER = register("villager", Builder.<Villager>of(Villager::new, MobCategory.MISC).sized(0.6F, 1.95F).clientTrackingRange(10));
/* 289 */   public static final EntityType<Vindicator> VINDICATOR = register("vindicator", Builder.<Vindicator>of(Vindicator::new, MobCategory.MONSTER).sized(0.6F, 1.95F).clientTrackingRange(8));
/* 290 */   public static final EntityType<WanderingTrader> WANDERING_TRADER = register("wandering_trader", Builder.<WanderingTrader>of(WanderingTrader::new, MobCategory.CREATURE).sized(0.6F, 1.95F).clientTrackingRange(10));
/* 291 */   public static final EntityType<Warden> WARDEN = register("warden", Builder.<Warden>of(Warden::new, MobCategory.MONSTER).sized(0.9F, 2.9F).clientTrackingRange(16).fireImmune());
/* 292 */   public static final EntityType<WindCharge> WIND_CHARGE = register("wind_charge", Builder.<WindCharge>of(WindCharge::new, MobCategory.MISC).sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10).requiredFeatures(new FeatureFlag[] { FeatureFlags.UPDATE_1_21 }));
/* 293 */   public static final EntityType<Witch> WITCH = register("witch", Builder.<Witch>of(Witch::new, MobCategory.MONSTER).sized(0.6F, 1.95F).clientTrackingRange(8));
/* 294 */   public static final EntityType<WitherBoss> WITHER = register("wither", Builder.<WitherBoss>of(WitherBoss::new, MobCategory.MONSTER).fireImmune().immuneTo(new Block[] { Blocks.WITHER_ROSE }).sized(0.9F, 3.5F).clientTrackingRange(10));
/* 295 */   public static final EntityType<WitherSkeleton> WITHER_SKELETON = register("wither_skeleton", Builder.<WitherSkeleton>of(WitherSkeleton::new, MobCategory.MONSTER).fireImmune().immuneTo(new Block[] { Blocks.WITHER_ROSE }).sized(0.7F, 2.4F).clientTrackingRange(8));
/* 296 */   public static final EntityType<WitherSkull> WITHER_SKULL = register("wither_skull", Builder.<WitherSkull>of(WitherSkull::new, MobCategory.MISC).sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10));
/* 297 */   public static final EntityType<Wolf> WOLF = register("wolf", Builder.<Wolf>of(Wolf::new, MobCategory.CREATURE).sized(0.6F, 0.85F).clientTrackingRange(10));
/* 298 */   public static final EntityType<Zoglin> ZOGLIN = register("zoglin", Builder.<Zoglin>of(Zoglin::new, MobCategory.MONSTER).fireImmune().sized(1.3964844F, 1.4F).clientTrackingRange(8));
/* 299 */   public static final EntityType<Zombie> ZOMBIE = register("zombie", Builder.<Zombie>of(Zombie::new, MobCategory.MONSTER).sized(0.6F, 1.95F).clientTrackingRange(8));
/* 300 */   public static final EntityType<ZombieHorse> ZOMBIE_HORSE = register("zombie_horse", Builder.<ZombieHorse>of(ZombieHorse::new, MobCategory.CREATURE).sized(1.3964844F, 1.6F).clientTrackingRange(10));
/* 301 */   public static final EntityType<ZombieVillager> ZOMBIE_VILLAGER = register("zombie_villager", Builder.<ZombieVillager>of(ZombieVillager::new, MobCategory.MONSTER).sized(0.6F, 1.95F).clientTrackingRange(8));
/* 302 */   public static final EntityType<ZombifiedPiglin> ZOMBIFIED_PIGLIN = register("zombified_piglin", Builder.<ZombifiedPiglin>of(ZombifiedPiglin::new, MobCategory.MONSTER).fireImmune().sized(0.6F, 1.95F).clientTrackingRange(8));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 311 */   public static final EntityType<Player> PLAYER = register("player", Builder.<Player>createNothing(MobCategory.MISC).noSave().noSummon().sized(0.6F, 1.8F).clientTrackingRange(32).updateInterval(2));
/* 312 */   public static final EntityType<FishingHook> FISHING_BOBBER = register("fishing_bobber", Builder.<FishingHook>of(FishingHook::new, MobCategory.MISC).noSave().noSummon().sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(5));
/*     */   private final EntityFactory<T> factory; private final MobCategory category; private final ImmutableSet<Block> immuneTo; private final boolean serialize; private final boolean summon; private final boolean fireImmune; private final boolean canSpawnFarFromPlayer;
/*     */   
/* 315 */   public static ResourceLocation getKey(EntityType<?> $$0) { return BuiltInRegistries.ENTITY_TYPE.getKey($$0); } private final int clientTrackingRange; private final int updateInterval; @Nullable
/*     */   private String descriptionId; @Nullable
/*     */   private Component description; @Nullable
/*     */   private ResourceLocation lootTable; private final EntityDimensions dimensions; private final FeatureFlagSet requiredFeatures; public static Optional<EntityType<?>> byString(String $$0) {
/* 319 */     return BuiltInRegistries.ENTITY_TYPE.getOptional(ResourceLocation.tryParse($$0));
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
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityType(EntityFactory<T> $$0, MobCategory $$1, boolean $$2, boolean $$3, boolean $$4, boolean $$5, ImmutableSet<Block> $$6, EntityDimensions $$7, int $$8, int $$9, FeatureFlagSet $$10) {
/* 341 */     this.factory = $$0;
/* 342 */     this.category = $$1;
/* 343 */     this.canSpawnFarFromPlayer = $$5;
/* 344 */     this.serialize = $$2;
/* 345 */     this.summon = $$3;
/* 346 */     this.fireImmune = $$4;
/* 347 */     this.immuneTo = $$6;
/* 348 */     this.dimensions = $$7;
/* 349 */     this.clientTrackingRange = $$8;
/* 350 */     this.updateInterval = $$9;
/* 351 */     this.requiredFeatures = $$10;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public T spawn(ServerLevel $$0, @Nullable ItemStack $$1, @Nullable Player $$2, BlockPos $$3, MobSpawnType $$4, boolean $$5, boolean $$6) {
/*     */     Consumer<T> $$9;
/*     */     CompoundTag $$10;
/* 358 */     if ($$1 != null) {
/* 359 */       CompoundTag $$7 = $$1.getTag();
/* 360 */       Consumer<T> $$8 = createDefaultStackConfig($$0, $$1, $$2);
/*     */     } else {
/* 362 */       $$9 = ($$0 -> { 
/* 363 */         }); $$10 = null;
/*     */     } 
/*     */     
/* 366 */     return spawn($$0, $$10, $$9, $$3, $$4, $$5, $$6);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T extends Entity> Consumer<T> createDefaultStackConfig(ServerLevel $$0, ItemStack $$1, @Nullable Player $$2) {
/* 374 */     return appendDefaultStackConfig($$0 -> {  }$$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   public static <T extends Entity> Consumer<T> appendDefaultStackConfig(Consumer<T> $$0, ServerLevel $$1, ItemStack $$2, @Nullable Player $$3) {
/* 378 */     return appendCustomEntityStackConfig(appendCustomNameConfig($$0, $$2), $$1, $$2, $$3);
/*     */   }
/*     */   
/*     */   public static <T extends Entity> Consumer<T> appendCustomNameConfig(Consumer<T> $$0, ItemStack $$1) {
/* 382 */     if ($$1.hasCustomHoverName()) {
/* 383 */       return $$0.andThen($$1 -> $$1.setCustomName($$0.getHoverName()));
/*     */     }
/* 385 */     return $$0;
/*     */   }
/*     */   
/*     */   public static <T extends Entity> Consumer<T> appendCustomEntityStackConfig(Consumer<T> $$0, ServerLevel $$1, ItemStack $$2, @Nullable Player $$3) {
/* 389 */     CompoundTag $$4 = $$2.getTag();
/* 390 */     if ($$4 != null) {
/* 391 */       return $$0.andThen($$3 -> updateCustomEntityTag((Level)$$0, $$1, $$3, $$2));
/*     */     }
/* 393 */     return $$0;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public T spawn(ServerLevel $$0, BlockPos $$1, MobSpawnType $$2) {
/* 398 */     return spawn($$0, (CompoundTag)null, (Consumer<T>)null, $$1, $$2, false, false);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public T spawn(ServerLevel $$0, @Nullable CompoundTag $$1, @Nullable Consumer<T> $$2, BlockPos $$3, MobSpawnType $$4, boolean $$5, boolean $$6) {
/* 403 */     T $$7 = create($$0, $$1, $$2, $$3, $$4, $$5, $$6);
/* 404 */     if ($$7 != null) {
/* 405 */       $$0.addFreshEntityWithPassengers((Entity)$$7);
/*     */     }
/* 407 */     return $$7;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public T create(ServerLevel $$0, @Nullable CompoundTag $$1, @Nullable Consumer<T> $$2, BlockPos $$3, MobSpawnType $$4, boolean $$5, boolean $$6) {
/*     */     double $$9;
/* 415 */     T $$7 = create((Level)$$0);
/*     */     
/* 417 */     if ($$7 == null) {
/* 418 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 422 */     if ($$5) {
/* 423 */       $$7.setPos($$3.getX() + 0.5D, ($$3.getY() + 1), $$3.getZ() + 0.5D);
/*     */       
/* 425 */       double $$8 = getYOffset((LevelReader)$$0, $$3, $$6, $$7.getBoundingBox());
/*     */     } else {
/* 427 */       $$9 = 0.0D;
/*     */     } 
/*     */     
/* 430 */     $$7.moveTo($$3.getX() + 0.5D, $$3.getY() + $$9, $$3.getZ() + 0.5D, Mth.wrapDegrees($$0.random.nextFloat() * 360.0F), 0.0F);
/*     */ 
/*     */     
/* 433 */     if ($$7 instanceof Mob) { Mob $$10 = (Mob)$$7;
/* 434 */       $$10.yHeadRot = $$10.getYRot();
/* 435 */       $$10.yBodyRot = $$10.getYRot();
/*     */       
/* 437 */       $$10.finalizeSpawn((ServerLevelAccessor)$$0, $$0.getCurrentDifficultyAt($$10.blockPosition()), $$4, (SpawnGroupData)null, $$1);
/* 438 */       $$10.playAmbientSound(); }
/*     */ 
/*     */     
/* 441 */     if ($$2 != null) {
/* 442 */       $$2.accept($$7);
/*     */     }
/*     */     
/* 445 */     return $$7;
/*     */   }
/*     */   
/*     */   protected static double getYOffset(LevelReader $$0, BlockPos $$1, boolean $$2, AABB $$3) {
/* 449 */     AABB $$4 = new AABB($$1);
/* 450 */     if ($$2) {
/* 451 */       $$4 = $$4.expandTowards(0.0D, -1.0D, 0.0D);
/*     */     }
/* 453 */     Iterable<VoxelShape> $$5 = $$0.getCollisions(null, $$4);
/*     */     
/* 455 */     return 1.0D + Shapes.collide(Direction.Axis.Y, $$3, $$5, $$2 ? -2.0D : -1.0D);
/*     */   }
/*     */   
/*     */   public static void updateCustomEntityTag(Level $$0, @Nullable Player $$1, @Nullable Entity $$2, @Nullable CompoundTag $$3) {
/* 459 */     if ($$3 == null || !$$3.contains("EntityTag", 10)) {
/*     */       return;
/*     */     }
/*     */     
/* 463 */     MinecraftServer $$4 = $$0.getServer();
/* 464 */     if ($$4 == null || $$2 == null) {
/*     */       return;
/*     */     }
/*     */     
/* 468 */     if (!$$0.isClientSide && $$2.onlyOpCanSetNbt() && ($$1 == null || !$$4.getPlayerList().isOp($$1.getGameProfile()))) {
/*     */       return;
/*     */     }
/*     */     
/* 472 */     CompoundTag $$5 = $$2.saveWithoutId(new CompoundTag());
/* 473 */     UUID $$6 = $$2.getUUID();
/* 474 */     $$5.merge($$3.getCompound("EntityTag"));
/* 475 */     $$2.setUUID($$6);
/* 476 */     $$2.load($$5);
/*     */   }
/*     */   
/*     */   public boolean canSerialize() {
/* 480 */     return this.serialize;
/*     */   }
/*     */   
/*     */   public boolean canSummon() {
/* 484 */     return this.summon;
/*     */   }
/*     */   
/*     */   public boolean fireImmune() {
/* 488 */     return this.fireImmune;
/*     */   }
/*     */   
/*     */   public boolean canSpawnFarFromPlayer() {
/* 492 */     return this.canSpawnFarFromPlayer;
/*     */   }
/*     */   
/*     */   public MobCategory getCategory() {
/* 496 */     return this.category;
/*     */   }
/*     */   
/*     */   public String getDescriptionId() {
/* 500 */     if (this.descriptionId == null) {
/* 501 */       this.descriptionId = Util.makeDescriptionId("entity", BuiltInRegistries.ENTITY_TYPE.getKey(this));
/*     */     }
/* 503 */     return this.descriptionId;
/*     */   }
/*     */   
/*     */   public Component getDescription() {
/* 507 */     if (this.description == null) {
/* 508 */       this.description = (Component)Component.translatable(getDescriptionId());
/*     */     }
/* 510 */     return this.description;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 515 */     return getDescriptionId();
/*     */   }
/*     */   
/*     */   public String toShortString() {
/* 519 */     int $$0 = getDescriptionId().lastIndexOf('.');
/* 520 */     return ($$0 == -1) ? getDescriptionId() : getDescriptionId().substring($$0 + 1);
/*     */   }
/*     */   
/*     */   public ResourceLocation getDefaultLootTable() {
/* 524 */     if (this.lootTable == null) {
/* 525 */       ResourceLocation $$0 = BuiltInRegistries.ENTITY_TYPE.getKey(this);
/*     */       
/* 527 */       this.lootTable = $$0.withPrefix("entities/");
/*     */     } 
/* 529 */     return this.lootTable;
/*     */   }
/*     */   
/*     */   public float getWidth() {
/* 533 */     return this.dimensions.width;
/*     */   }
/*     */   
/*     */   public float getHeight() {
/* 537 */     return this.dimensions.height;
/*     */   }
/*     */ 
/*     */   
/*     */   public FeatureFlagSet requiredFeatures() {
/* 542 */     return this.requiredFeatures;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public T create(Level $$0) {
/* 547 */     if (!isEnabled($$0.enabledFeatures())) {
/* 548 */       return null;
/*     */     }
/* 550 */     return this.factory.create(this, $$0);
/*     */   }
/*     */   
/*     */   public static Optional<Entity> create(CompoundTag $$0, Level $$1) {
/* 554 */     return Util.ifElse(by($$0).map($$1 -> $$1.create($$0)), $$1 -> $$1.load($$0), () -> LOGGER.warn("Skipping Entity with id {}", $$0.getString("id")));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AABB getAABB(double $$0, double $$1, double $$2) {
/* 561 */     float $$3 = getWidth() / 2.0F;
/* 562 */     return new AABB($$0 - $$3, $$1, $$2 - $$3, $$0 + $$3, $$1 + 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 567 */         getHeight(), $$2 + $$3);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBlockDangerous(BlockState $$0) {
/* 573 */     if (this.immuneTo.contains($$0.getBlock())) {
/* 574 */       return false;
/*     */     }
/* 576 */     if (!this.fireImmune && WalkNodeEvaluator.isBurningBlock($$0)) {
/* 577 */       return true;
/*     */     }
/* 579 */     return ($$0.is(Blocks.WITHER_ROSE) || $$0.is(Blocks.SWEET_BERRY_BUSH) || $$0.is(Blocks.CACTUS) || $$0.is(Blocks.POWDER_SNOW));
/*     */   }
/*     */   
/*     */   public EntityDimensions getDimensions() {
/* 583 */     return this.dimensions;
/*     */   }
/*     */   
/*     */   public static Optional<EntityType<?>> by(CompoundTag $$0) {
/* 587 */     return BuiltInRegistries.ENTITY_TYPE.getOptional(new ResourceLocation($$0.getString("id")));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static Entity loadEntityRecursive(CompoundTag $$0, Level $$1, Function<Entity, Entity> $$2) {
/* 592 */     return loadStaticEntity($$0, $$1)
/* 593 */       .<Entity>map($$2)
/* 594 */       .map($$3 -> {
/*     */           if ($$0.contains("Passengers", 9)) {
/*     */             ListTag $$4 = $$0.getList("Passengers", 10);
/*     */             
/*     */             for (int $$5 = 0; $$5 < $$4.size(); $$5++) {
/*     */               Entity $$6 = loadEntityRecursive($$4.getCompound($$5), $$1, $$2);
/*     */               if ($$6 != null) {
/*     */                 $$6.startRiding($$3, true);
/*     */               }
/*     */             } 
/*     */           } 
/*     */           return $$3;
/* 606 */         }).orElse(null);
/*     */   }
/*     */   
/*     */   public static Stream<Entity> loadEntitiesRecursive(final List<? extends Tag> entities, final Level level) {
/* 610 */     final Spliterator<? extends Tag> tagSpliterator = entities.spliterator();
/* 611 */     return StreamSupport.stream(new Spliterator<Entity>()
/*     */         {
/*     */           public boolean tryAdvance(Consumer<? super Entity> $$0) {
/* 614 */             return tagSpliterator.tryAdvance($$2 -> EntityType.loadEntityRecursive((CompoundTag)$$2, $$0, ()));
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public Spliterator<Entity> trySplit() {
/* 624 */             return null;
/*     */           }
/*     */ 
/*     */           
/*     */           public long estimateSize() {
/* 629 */             return entities.size();
/*     */           }
/*     */ 
/*     */           
/*     */           public int characteristics() {
/* 634 */             return 1297;
/*     */           }
/*     */         }false);
/*     */   }
/*     */   
/*     */   private static Optional<Entity> loadStaticEntity(CompoundTag $$0, Level $$1) {
/*     */     try {
/* 641 */       return create($$0, $$1);
/* 642 */     } catch (RuntimeException $$2) {
/* 643 */       LOGGER.warn("Exception loading entity: ", $$2);
/* 644 */       return Optional.empty();
/*     */     } 
/*     */   }
/*     */   
/*     */   public int clientTrackingRange() {
/* 649 */     return this.clientTrackingRange;
/*     */   }
/*     */   
/*     */   public int updateInterval() {
/* 653 */     return this.updateInterval;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean trackDeltas() {
/* 658 */     return (this != PLAYER && this != LLAMA_SPIT && this != WITHER && this != BAT && this != ITEM_FRAME && this != GLOW_ITEM_FRAME && this != LEASH_KNOT && this != PAINTING && this != END_CRYSTAL && this != EVOKER_FANGS);
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
/*     */   public boolean is(TagKey<EntityType<?>> $$0) {
/* 672 */     return this.builtInRegistryHolder.is($$0);
/*     */   }
/*     */   
/*     */   public boolean is(HolderSet<EntityType<?>> $$0) {
/* 676 */     return $$0.contains((Holder)this.builtInRegistryHolder);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public T tryCast(Entity $$0) {
/* 683 */     return ($$0.getType() == this) ? (T)$$0 : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<? extends Entity> getBaseClass() {
/* 688 */     return Entity.class;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Holder.Reference<EntityType<?>> builtInRegistryHolder() {
/* 696 */     return this.builtInRegistryHolder;
/*     */   }
/*     */   public static interface EntityFactory<T extends Entity> {
/*     */     T create(EntityType<T> param1EntityType, Level param1Level); }
/*     */   public static class Builder<T extends Entity> { private final EntityType.EntityFactory<T> factory;
/*     */     private final MobCategory category;
/* 702 */     private ImmutableSet<Block> immuneTo = ImmutableSet.of();
/*     */     private boolean serialize = true;
/*     */     private boolean summon = true;
/*     */     private boolean fireImmune;
/*     */     private boolean canSpawnFarFromPlayer;
/* 707 */     private int clientTrackingRange = 5;
/* 708 */     private int updateInterval = 3;
/* 709 */     private EntityDimensions dimensions = EntityDimensions.scalable(0.6F, 1.8F);
/* 710 */     private FeatureFlagSet requiredFeatures = FeatureFlags.VANILLA_SET;
/*     */     
/*     */     private Builder(EntityType.EntityFactory<T> $$0, MobCategory $$1) {
/* 713 */       this.factory = $$0;
/* 714 */       this.category = $$1;
/* 715 */       this.canSpawnFarFromPlayer = ($$1 == MobCategory.CREATURE || $$1 == MobCategory.MISC);
/*     */     }
/*     */     
/*     */     public static <T extends Entity> Builder<T> of(EntityType.EntityFactory<T> $$0, MobCategory $$1) {
/* 719 */       return new Builder<>($$0, $$1);
/*     */     }
/*     */     
/*     */     public static <T extends Entity> Builder<T> createNothing(MobCategory $$0) {
/* 723 */       return new Builder<>(($$0, $$1) -> null, $$0);
/*     */     }
/*     */     
/*     */     public Builder<T> sized(float $$0, float $$1) {
/* 727 */       this.dimensions = EntityDimensions.scalable($$0, $$1);
/* 728 */       return this;
/*     */     }
/*     */     
/*     */     public Builder<T> noSummon() {
/* 732 */       this.summon = false;
/* 733 */       return this;
/*     */     }
/*     */     
/*     */     public Builder<T> noSave() {
/* 737 */       this.serialize = false;
/* 738 */       return this;
/*     */     }
/*     */     
/*     */     public Builder<T> fireImmune() {
/* 742 */       this.fireImmune = true;
/* 743 */       return this;
/*     */     }
/*     */     
/*     */     public Builder<T> immuneTo(Block... $$0) {
/* 747 */       this.immuneTo = ImmutableSet.copyOf((Object[])$$0);
/* 748 */       return this;
/*     */     }
/*     */     
/*     */     public Builder<T> canSpawnFarFromPlayer() {
/* 752 */       this.canSpawnFarFromPlayer = true;
/* 753 */       return this;
/*     */     }
/*     */     
/*     */     public Builder<T> clientTrackingRange(int $$0) {
/* 757 */       this.clientTrackingRange = $$0;
/* 758 */       return this;
/*     */     }
/*     */     
/*     */     public Builder<T> updateInterval(int $$0) {
/* 762 */       this.updateInterval = $$0;
/* 763 */       return this;
/*     */     }
/*     */     
/*     */     public Builder<T> requiredFeatures(FeatureFlag... $$0) {
/* 767 */       this.requiredFeatures = FeatureFlags.REGISTRY.subset($$0);
/* 768 */       return this;
/*     */     }
/*     */     
/*     */     public EntityType<T> build(String $$0) {
/* 772 */       if (this.serialize) {
/* 773 */         Util.fetchChoiceType(References.ENTITY_TREE, $$0);
/*     */       }
/* 775 */       return new EntityType<>(this.factory, this.category, this.serialize, this.summon, this.fireImmune, this.canSpawnFarFromPlayer, this.immuneTo, this.dimensions, this.clientTrackingRange, this.updateInterval, this.requiredFeatures);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\EntityType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */