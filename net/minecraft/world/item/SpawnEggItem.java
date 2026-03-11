/*     */ package net.minecraft.world.item;
/*     */ 
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.stats.Stats;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.InteractionResultHolder;
/*     */ import net.minecraft.world.entity.AgeableMob;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.flag.FeatureFlagSet;
/*     */ import net.minecraft.world.item.context.UseOnContext;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.ClipContext;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.Spawner;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.HitResult;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class SpawnEggItem
/*     */   extends Item
/*     */ {
/*  39 */   private static final Map<EntityType<? extends Mob>, SpawnEggItem> BY_ID = Maps.newIdentityHashMap();
/*     */   
/*     */   private final int backgroundColor;
/*     */   private final int highlightColor;
/*     */   private final EntityType<?> defaultType;
/*     */   
/*     */   public SpawnEggItem(EntityType<? extends Mob> $$0, int $$1, int $$2, Item.Properties $$3) {
/*  46 */     super($$3);
/*  47 */     this.defaultType = $$0;
/*  48 */     this.backgroundColor = $$1;
/*  49 */     this.highlightColor = $$2;
/*     */     
/*  51 */     BY_ID.put($$0, this);
/*     */   }
/*     */   
/*     */   public InteractionResult useOn(UseOnContext $$0) {
/*     */     BlockPos $$9;
/*  56 */     Level $$1 = $$0.getLevel();
/*  57 */     if (!($$1 instanceof ServerLevel)) {
/*  58 */       return InteractionResult.SUCCESS;
/*     */     }
/*     */     
/*  61 */     ItemStack $$2 = $$0.getItemInHand();
/*  62 */     BlockPos $$3 = $$0.getClickedPos();
/*  63 */     Direction $$4 = $$0.getClickedFace();
/*     */     
/*  65 */     BlockState $$5 = $$1.getBlockState($$3);
/*  66 */     BlockEntity blockEntity = $$1.getBlockEntity($$3); if (blockEntity instanceof Spawner) { Spawner $$6 = (Spawner)blockEntity;
/*  67 */       EntityType<?> $$7 = getType($$2.getTag());
/*  68 */       $$6.setEntityId($$7, $$1.getRandom());
/*  69 */       $$1.sendBlockUpdated($$3, $$5, $$5, 3);
/*  70 */       $$1.gameEvent((Entity)$$0.getPlayer(), GameEvent.BLOCK_CHANGE, $$3);
/*  71 */       $$2.shrink(1);
/*  72 */       return InteractionResult.CONSUME; }
/*     */ 
/*     */ 
/*     */     
/*  76 */     if ($$5.getCollisionShape((BlockGetter)$$1, $$3).isEmpty()) {
/*  77 */       BlockPos $$8 = $$3;
/*     */     } else {
/*  79 */       $$9 = $$3.relative($$4);
/*     */     } 
/*     */     
/*  82 */     EntityType<?> $$10 = getType($$2.getTag());
/*  83 */     if ($$10.spawn((ServerLevel)$$1, $$2, $$0.getPlayer(), $$9, MobSpawnType.SPAWN_EGG, true, (!Objects.equals($$3, $$9) && $$4 == Direction.UP)) != null) {
/*  84 */       $$2.shrink(1);
/*  85 */       $$1.gameEvent((Entity)$$0.getPlayer(), GameEvent.ENTITY_PLACE, $$3);
/*     */     } 
/*     */     
/*  88 */     return InteractionResult.CONSUME;
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResultHolder<ItemStack> use(Level $$0, Player $$1, InteractionHand $$2) {
/*  93 */     ItemStack $$3 = $$1.getItemInHand($$2);
/*     */     
/*  95 */     BlockHitResult $$4 = getPlayerPOVHitResult($$0, $$1, ClipContext.Fluid.SOURCE_ONLY);
/*  96 */     if ($$4.getType() != HitResult.Type.BLOCK) {
/*  97 */       return InteractionResultHolder.pass($$3);
/*     */     }
/*     */     
/* 100 */     if (!($$0 instanceof ServerLevel)) {
/* 101 */       return InteractionResultHolder.success($$3);
/*     */     }
/*     */     
/* 104 */     BlockHitResult $$5 = $$4;
/* 105 */     BlockPos $$6 = $$5.getBlockPos();
/* 106 */     if (!($$0.getBlockState($$6).getBlock() instanceof net.minecraft.world.level.block.LiquidBlock)) {
/* 107 */       return InteractionResultHolder.pass($$3);
/*     */     }
/* 109 */     if (!$$0.mayInteract($$1, $$6) || !$$1.mayUseItemAt($$6, $$5.getDirection(), $$3)) {
/* 110 */       return InteractionResultHolder.fail($$3);
/*     */     }
/* 112 */     EntityType<?> $$7 = getType($$3.getTag());
/* 113 */     Entity $$8 = $$7.spawn((ServerLevel)$$0, $$3, $$1, $$6, MobSpawnType.SPAWN_EGG, false, false);
/* 114 */     if ($$8 == null) {
/* 115 */       return InteractionResultHolder.pass($$3);
/*     */     }
/* 117 */     if (!($$1.getAbilities()).instabuild) {
/* 118 */       $$3.shrink(1);
/*     */     }
/* 120 */     $$1.awardStat(Stats.ITEM_USED.get(this));
/* 121 */     $$0.gameEvent((Entity)$$1, GameEvent.ENTITY_PLACE, $$8.position());
/* 122 */     return InteractionResultHolder.consume($$3);
/*     */   }
/*     */   
/*     */   public boolean spawnsEntity(@Nullable CompoundTag $$0, EntityType<?> $$1) {
/* 126 */     return Objects.equals(getType($$0), $$1);
/*     */   }
/*     */   
/*     */   public int getColor(int $$0) {
/* 130 */     return ($$0 == 0) ? this.backgroundColor : this.highlightColor;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static SpawnEggItem byId(@Nullable EntityType<?> $$0) {
/* 135 */     return BY_ID.get($$0);
/*     */   }
/*     */   
/*     */   public static Iterable<SpawnEggItem> eggs() {
/* 139 */     return Iterables.unmodifiableIterable(BY_ID.values());
/*     */   }
/*     */   
/*     */   public EntityType<?> getType(@Nullable CompoundTag $$0) {
/* 143 */     if ($$0 != null && 
/* 144 */       $$0.contains("EntityTag", 10)) {
/* 145 */       CompoundTag $$1 = $$0.getCompound("EntityTag");
/* 146 */       if ($$1.contains("id", 8)) {
/* 147 */         return EntityType.byString($$1.getString("id")).orElse(this.defaultType);
/*     */       }
/*     */     } 
/*     */     
/* 151 */     return this.defaultType;
/*     */   }
/*     */ 
/*     */   
/*     */   public FeatureFlagSet requiredFeatures() {
/* 156 */     return this.defaultType.requiredFeatures();
/*     */   }
/*     */   public Optional<Mob> spawnOffspringFromSpawnEgg(Player $$0, Mob $$1, EntityType<? extends Mob> $$2, ServerLevel $$3, Vec3 $$4, ItemStack $$5) {
/*     */     Mob $$7;
/* 160 */     if (!spawnsEntity($$5.getTag(), $$2)) {
/* 161 */       return Optional.empty();
/*     */     }
/*     */ 
/*     */     
/* 165 */     if ($$1 instanceof AgeableMob) {
/* 166 */       AgeableMob ageableMob = ((AgeableMob)$$1).getBreedOffspring($$3, (AgeableMob)$$1);
/*     */     } else {
/* 168 */       $$7 = (Mob)$$2.create((Level)$$3);
/*     */     } 
/* 170 */     if ($$7 == null) {
/* 171 */       return Optional.empty();
/*     */     }
/*     */     
/* 174 */     $$7.setBaby(true);
/* 175 */     if (!$$7.isBaby()) {
/* 176 */       return Optional.empty();
/*     */     }
/*     */     
/* 179 */     $$7.moveTo($$4.x(), $$4.y(), $$4.z(), 0.0F, 0.0F);
/*     */     
/* 181 */     $$3.addFreshEntityWithPassengers((Entity)$$7);
/*     */     
/* 183 */     if ($$5.hasCustomHoverName()) {
/* 184 */       $$7.setCustomName($$5.getHoverName());
/*     */     }
/* 186 */     if (!($$0.getAbilities()).instabuild) {
/* 187 */       $$5.shrink(1);
/*     */     }
/* 189 */     return Optional.of($$7);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\SpawnEggItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */