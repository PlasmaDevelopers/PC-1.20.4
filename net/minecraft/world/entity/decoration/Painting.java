/*     */ package net.minecraft.world.entity.decoration;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ClientGamePacketListener;
/*     */ import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.tags.PaintingVariantTags;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.VariantHolder;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class Painting extends HangingEntity implements VariantHolder<Holder<PaintingVariant>> {
/*  36 */   private static final EntityDataAccessor<Holder<PaintingVariant>> DATA_PAINTING_VARIANT_ID = SynchedEntityData.defineId(Painting.class, EntityDataSerializers.PAINTING_VARIANT);
/*  37 */   private static final ResourceKey<PaintingVariant> DEFAULT_VARIANT = PaintingVariants.KEBAB;
/*     */   public static final String VARIANT_TAG = "variant";
/*     */   
/*     */   private static Holder<PaintingVariant> getDefaultVariant() {
/*  41 */     return (Holder<PaintingVariant>)BuiltInRegistries.PAINTING_VARIANT.getHolderOrThrow(DEFAULT_VARIANT);
/*     */   }
/*     */   
/*     */   public Painting(EntityType<? extends Painting> $$0, Level $$1) {
/*  45 */     super((EntityType)$$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/*  50 */     this.entityData.define(DATA_PAINTING_VARIANT_ID, getDefaultVariant());
/*     */   }
/*     */ 
/*     */   
/*     */   public void onSyncedDataUpdated(EntityDataAccessor<?> $$0) {
/*  55 */     if (DATA_PAINTING_VARIANT_ID.equals($$0)) {
/*  56 */       recalculateBoundingBox();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVariant(Holder<PaintingVariant> $$0) {
/*  62 */     this.entityData.set(DATA_PAINTING_VARIANT_ID, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Holder<PaintingVariant> getVariant() {
/*  67 */     return (Holder<PaintingVariant>)this.entityData.get(DATA_PAINTING_VARIANT_ID);
/*     */   }
/*     */   
/*     */   public static Optional<Painting> create(Level $$0, BlockPos $$1, Direction $$2) {
/*  71 */     Painting $$3 = new Painting($$0, $$1);
/*     */     
/*  73 */     List<Holder<PaintingVariant>> $$4 = new ArrayList<>();
/*  74 */     Objects.requireNonNull($$4); BuiltInRegistries.PAINTING_VARIANT.getTagOrEmpty(PaintingVariantTags.PLACEABLE).forEach($$4::add);
/*  75 */     if ($$4.isEmpty()) {
/*  76 */       return Optional.empty();
/*     */     }
/*     */     
/*  79 */     $$3.setDirection($$2);
/*  80 */     $$4.removeIf($$1 -> {
/*     */           $$0.setVariant($$1);
/*     */           
/*     */           return !$$0.survives();
/*     */         });
/*  85 */     if ($$4.isEmpty()) {
/*  86 */       return Optional.empty();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  92 */     int $$5 = $$4.stream().mapToInt(Painting::variantArea).max().orElse(0);
/*     */     
/*  94 */     $$4.removeIf($$1 -> (variantArea($$1) < $$0));
/*  95 */     Optional<Holder<PaintingVariant>> $$6 = Util.getRandomSafe($$4, $$3.random);
/*  96 */     if ($$6.isEmpty()) {
/*  97 */       return Optional.empty();
/*     */     }
/*  99 */     $$3.setVariant($$6.get());
/* 100 */     $$3.setDirection($$2);
/* 101 */     return Optional.of($$3);
/*     */   }
/*     */   
/*     */   private static int variantArea(Holder<PaintingVariant> $$0) {
/* 105 */     return ((PaintingVariant)$$0.value()).getWidth() * ((PaintingVariant)$$0.value()).getHeight();
/*     */   }
/*     */   
/*     */   private Painting(Level $$0, BlockPos $$1) {
/* 109 */     super(EntityType.PAINTING, $$0, $$1);
/*     */   }
/*     */   
/*     */   public Painting(Level $$0, BlockPos $$1, Direction $$2, Holder<PaintingVariant> $$3) {
/* 113 */     this($$0, $$1);
/* 114 */     setVariant($$3);
/* 115 */     setDirection($$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 120 */     storeVariant($$0, getVariant());
/* 121 */     $$0.putByte("facing", (byte)this.direction.get2DDataValue());
/* 122 */     super.addAdditionalSaveData($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 127 */     Holder<PaintingVariant> $$1 = loadVariant($$0).orElseGet(Painting::getDefaultVariant);
/* 128 */     setVariant($$1);
/* 129 */     this.direction = Direction.from2DDataValue($$0.getByte("facing"));
/* 130 */     super.readAdditionalSaveData($$0);
/* 131 */     setDirection(this.direction);
/*     */   }
/*     */   
/*     */   public static void storeVariant(CompoundTag $$0, Holder<PaintingVariant> $$1) {
/* 135 */     $$0.putString("variant", ((ResourceKey)$$1.unwrapKey().orElse(DEFAULT_VARIANT)).location().toString());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Optional<Holder<PaintingVariant>> loadVariant(CompoundTag $$0) {
/* 141 */     Objects.requireNonNull(BuiltInRegistries.PAINTING_VARIANT); return Optional.<ResourceLocation>ofNullable(ResourceLocation.tryParse($$0.getString("variant"))).map($$0 -> ResourceKey.create(Registries.PAINTING_VARIANT, $$0)).flatMap(BuiltInRegistries.PAINTING_VARIANT::getHolder);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWidth() {
/* 146 */     return ((PaintingVariant)getVariant().value()).getWidth();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeight() {
/* 151 */     return ((PaintingVariant)getVariant().value()).getHeight();
/*     */   }
/*     */ 
/*     */   
/*     */   public void dropItem(@Nullable Entity $$0) {
/* 156 */     if (!level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
/*     */       return;
/*     */     }
/*     */     
/* 160 */     playSound(SoundEvents.PAINTING_BREAK, 1.0F, 1.0F);
/*     */     
/* 162 */     if ($$0 instanceof Player) { Player $$1 = (Player)$$0;
/* 163 */       if (($$1.getAbilities()).instabuild) {
/*     */         return;
/*     */       } }
/*     */ 
/*     */     
/* 168 */     spawnAtLocation((ItemLike)Items.PAINTING);
/*     */   }
/*     */ 
/*     */   
/*     */   public void playPlacementSound() {
/* 173 */     playSound(SoundEvents.PAINTING_PLACE, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void moveTo(double $$0, double $$1, double $$2, float $$3, float $$4) {
/* 178 */     setPos($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void lerpTo(double $$0, double $$1, double $$2, float $$3, float $$4, int $$5) {
/* 183 */     setPos($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3 trackingPosition() {
/* 188 */     return Vec3.atLowerCornerOf((Vec3i)this.pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public Packet<ClientGamePacketListener> getAddEntityPacket() {
/* 193 */     return (Packet<ClientGamePacketListener>)new ClientboundAddEntityPacket(this, this.direction.get3DDataValue(), getPos());
/*     */   }
/*     */ 
/*     */   
/*     */   public void recreateFromPacket(ClientboundAddEntityPacket $$0) {
/* 198 */     super.recreateFromPacket($$0);
/* 199 */     setDirection(Direction.from3DDataValue($$0.getData()));
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getPickResult() {
/* 204 */     return new ItemStack((ItemLike)Items.PAINTING);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\decoration\Painting.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */