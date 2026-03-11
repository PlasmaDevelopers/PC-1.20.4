/*     */ package net.minecraft.world.entity.animal;
/*     */ 
/*     */ import com.mojang.serialization.Codec;
/*     */ import java.util.List;
/*     */ import java.util.function.IntFunction;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.tags.BiomeTags;
/*     */ import net.minecraft.tags.FluidTags;
/*     */ import net.minecraft.util.ByIdMap;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.StringRepresentable;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.SpawnGroupData;
/*     */ import net.minecraft.world.entity.VariantHolder;
/*     */ import net.minecraft.world.item.DyeColor;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ 
/*     */ public class TropicalFish
/*     */   extends AbstractSchoolingFish implements VariantHolder<TropicalFish.Pattern> {
/*     */   public static final String BUCKET_VARIANT_TAG = "BucketVariantTag";
/*  39 */   private static final EntityDataAccessor<Integer> DATA_ID_TYPE_VARIANT = SynchedEntityData.defineId(TropicalFish.class, EntityDataSerializers.INT);
/*     */   
/*     */   public enum Base {
/*  42 */     SMALL(0),
/*  43 */     LARGE(1);
/*     */     
/*     */     final int id;
/*     */ 
/*     */     
/*     */     Base(int $$0) {
/*  49 */       this.id = $$0;
/*     */     } }
/*     */   public static final class Variant extends Record { private final TropicalFish.Pattern pattern; private final DyeColor baseColor; private final DyeColor patternColor;
/*     */     
/*  53 */     public Variant(TropicalFish.Pattern $$0, DyeColor $$1, DyeColor $$2) { this.pattern = $$0; this.baseColor = $$1; this.patternColor = $$2; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/entity/animal/TropicalFish$Variant;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #53	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*  53 */       //   0	7	0	this	Lnet/minecraft/world/entity/animal/TropicalFish$Variant; } public TropicalFish.Pattern pattern() { return this.pattern; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/entity/animal/TropicalFish$Variant;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #53	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/entity/animal/TropicalFish$Variant; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/entity/animal/TropicalFish$Variant;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #53	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/entity/animal/TropicalFish$Variant;
/*  53 */       //   0	8	1	$$0	Ljava/lang/Object; } public DyeColor baseColor() { return this.baseColor; } public DyeColor patternColor() { return this.patternColor; }
/*     */      public int getPackedId() {
/*  55 */       return TropicalFish.packVariant(this.pattern, this.baseColor, this.patternColor);
/*     */     } }
/*     */ 
/*     */   
/*  59 */   public static final List<Variant> COMMON_VARIANTS = List.of(new Variant[] { new Variant(Pattern.STRIPEY, DyeColor.ORANGE, DyeColor.GRAY), new Variant(Pattern.FLOPPER, DyeColor.GRAY, DyeColor.GRAY), new Variant(Pattern.FLOPPER, DyeColor.GRAY, DyeColor.BLUE), new Variant(Pattern.CLAYFISH, DyeColor.WHITE, DyeColor.GRAY), new Variant(Pattern.SUNSTREAK, DyeColor.BLUE, DyeColor.GRAY), new Variant(Pattern.KOB, DyeColor.ORANGE, DyeColor.WHITE), new Variant(Pattern.SPOTTY, DyeColor.PINK, DyeColor.LIGHT_BLUE), new Variant(Pattern.BLOCKFISH, DyeColor.PURPLE, DyeColor.YELLOW), new Variant(Pattern.CLAYFISH, DyeColor.WHITE, DyeColor.RED), new Variant(Pattern.SPOTTY, DyeColor.WHITE, DyeColor.YELLOW), new Variant(Pattern.GLITTER, DyeColor.WHITE, DyeColor.GRAY), new Variant(Pattern.CLAYFISH, DyeColor.WHITE, DyeColor.ORANGE), new Variant(Pattern.DASHER, DyeColor.CYAN, DyeColor.PINK), new Variant(Pattern.BRINELY, DyeColor.LIME, DyeColor.LIGHT_BLUE), new Variant(Pattern.BETTY, DyeColor.RED, DyeColor.WHITE), new Variant(Pattern.SNOOPER, DyeColor.GRAY, DyeColor.RED), new Variant(Pattern.BLOCKFISH, DyeColor.RED, DyeColor.WHITE), new Variant(Pattern.FLOPPER, DyeColor.WHITE, DyeColor.YELLOW), new Variant(Pattern.KOB, DyeColor.RED, DyeColor.WHITE), new Variant(Pattern.SUNSTREAK, DyeColor.GRAY, DyeColor.WHITE), new Variant(Pattern.DASHER, DyeColor.CYAN, DyeColor.YELLOW), new Variant(Pattern.FLOPPER, DyeColor.YELLOW, DyeColor.YELLOW) });
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
/*     */   public enum Pattern
/*     */     implements StringRepresentable
/*     */   {
/*  85 */     KOB("kob", TropicalFish.Base.SMALL, 0),
/*  86 */     SUNSTREAK("sunstreak", TropicalFish.Base.SMALL, 1),
/*  87 */     SNOOPER("snooper", TropicalFish.Base.SMALL, 2),
/*  88 */     DASHER("dasher", TropicalFish.Base.SMALL, 3),
/*  89 */     BRINELY("brinely", TropicalFish.Base.SMALL, 4),
/*  90 */     SPOTTY("spotty", TropicalFish.Base.SMALL, 5),
/*  91 */     FLOPPER("flopper", TropicalFish.Base.LARGE, 0),
/*  92 */     STRIPEY("stripey", TropicalFish.Base.LARGE, 1),
/*  93 */     GLITTER("glitter", TropicalFish.Base.LARGE, 2),
/*  94 */     BLOCKFISH("blockfish", TropicalFish.Base.LARGE, 3),
/*  95 */     BETTY("betty", TropicalFish.Base.LARGE, 4),
/*  96 */     CLAYFISH("clayfish", TropicalFish.Base.LARGE, 5);
/*     */     
/*  98 */     public static final Codec<Pattern> CODEC = (Codec<Pattern>)StringRepresentable.fromEnum(Pattern::values);
/*     */     
/* 100 */     private static final IntFunction<Pattern> BY_ID = ByIdMap.sparse(Pattern::getPackedId, (Object[])values(), KOB);
/*     */     private final String name;
/*     */     private final Component displayName;
/*     */     private final TropicalFish.Base base;
/*     */     private final int packedId;
/*     */     
/*     */     static {
/*     */     
/*     */     }
/*     */     
/*     */     Pattern(String $$0, TropicalFish.Base $$1, int $$2) {
/* 111 */       this.name = $$0;
/* 112 */       this.base = $$1;
/* 113 */       this.packedId = $$1.id | $$2 << 8;
/* 114 */       this.displayName = (Component)Component.translatable("entity.minecraft.tropical_fish.type." + this.name);
/*     */     }
/*     */     
/*     */     public static Pattern byId(int $$0) {
/* 118 */       return BY_ID.apply($$0);
/*     */     }
/*     */     
/*     */     public TropicalFish.Base base() {
/* 122 */       return this.base;
/*     */     }
/*     */     
/*     */     public int getPackedId() {
/* 126 */       return this.packedId;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getSerializedName() {
/* 131 */       return this.name;
/*     */     }
/*     */     
/*     */     public Component displayName() {
/* 135 */       return this.displayName;
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean isSchool = true;
/*     */   
/*     */   public TropicalFish(EntityType<? extends TropicalFish> $$0, Level $$1) {
/* 142 */     super((EntityType)$$0, $$1);
/*     */   }
/*     */   
/*     */   public static String getPredefinedName(int $$0) {
/* 146 */     return "entity.minecraft.tropical_fish.predefined." + $$0;
/*     */   }
/*     */   
/*     */   static int packVariant(Pattern $$0, DyeColor $$1, DyeColor $$2) {
/* 150 */     return $$0.getPackedId() & 0xFFFF | ($$1.getId() & 0xFF) << 16 | ($$2.getId() & 0xFF) << 24;
/*     */   }
/*     */   
/*     */   public static DyeColor getBaseColor(int $$0) {
/* 154 */     return DyeColor.byId($$0 >> 16 & 0xFF);
/*     */   }
/*     */   
/*     */   public static DyeColor getPatternColor(int $$0) {
/* 158 */     return DyeColor.byId($$0 >> 24 & 0xFF);
/*     */   }
/*     */   
/*     */   public static Pattern getPattern(int $$0) {
/* 162 */     return Pattern.byId($$0 & 0xFFFF);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/* 167 */     super.defineSynchedData();
/*     */     
/* 169 */     this.entityData.define(DATA_ID_TYPE_VARIANT, Integer.valueOf(0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 174 */     super.addAdditionalSaveData($$0);
/*     */     
/* 176 */     $$0.putInt("Variant", getPackedVariant());
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 181 */     super.readAdditionalSaveData($$0);
/*     */     
/* 183 */     setPackedVariant($$0.getInt("Variant"));
/*     */   }
/*     */   
/*     */   private void setPackedVariant(int $$0) {
/* 187 */     this.entityData.set(DATA_ID_TYPE_VARIANT, Integer.valueOf($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isMaxGroupSizeReached(int $$0) {
/* 192 */     return !this.isSchool;
/*     */   }
/*     */   
/*     */   private int getPackedVariant() {
/* 196 */     return ((Integer)this.entityData.get(DATA_ID_TYPE_VARIANT)).intValue();
/*     */   }
/*     */   
/*     */   public DyeColor getBaseColor() {
/* 200 */     return getBaseColor(getPackedVariant());
/*     */   }
/*     */   
/*     */   public DyeColor getPatternColor() {
/* 204 */     return getPatternColor(getPackedVariant());
/*     */   }
/*     */ 
/*     */   
/*     */   public Pattern getVariant() {
/* 209 */     return getPattern(getPackedVariant());
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVariant(Pattern $$0) {
/* 214 */     int $$1 = getPackedVariant();
/* 215 */     DyeColor $$2 = getBaseColor($$1);
/* 216 */     DyeColor $$3 = getPatternColor($$1);
/* 217 */     setPackedVariant(packVariant($$0, $$2, $$3));
/*     */   }
/*     */ 
/*     */   
/*     */   public void saveToBucketTag(ItemStack $$0) {
/* 222 */     super.saveToBucketTag($$0);
/*     */     
/* 224 */     CompoundTag $$1 = $$0.getOrCreateTag();
/* 225 */     $$1.putInt("BucketVariantTag", getPackedVariant());
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getBucketItemStack() {
/* 230 */     return new ItemStack((ItemLike)Items.TROPICAL_FISH_BUCKET);
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/* 235 */     return SoundEvents.TROPICAL_FISH_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 240 */     return SoundEvents.TROPICAL_FISH_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 245 */     return SoundEvents.TROPICAL_FISH_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getFlopSound() {
/* 250 */     return SoundEvents.TROPICAL_FISH_FLOP;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public SpawnGroupData finalizeSpawn(ServerLevelAccessor $$0, DifficultyInstance $$1, MobSpawnType $$2, @Nullable SpawnGroupData $$3, @Nullable CompoundTag $$4) {
/*     */     Variant $$14;
/* 256 */     $$3 = super.finalizeSpawn($$0, $$1, $$2, $$3, $$4);
/*     */     
/* 258 */     if ($$2 == MobSpawnType.BUCKET && $$4 != null && $$4.contains("BucketVariantTag", 3)) {
/* 259 */       setPackedVariant($$4.getInt("BucketVariantTag"));
/* 260 */       return $$3;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 266 */     RandomSource $$5 = $$0.getRandom();
/* 267 */     if ($$3 instanceof TropicalFishGroupData) { TropicalFishGroupData $$6 = (TropicalFishGroupData)$$3;
/* 268 */       Variant $$7 = $$6.variant; }
/* 269 */     else if ($$5.nextFloat() < 0.9D)
/*     */     
/* 271 */     { Variant $$8 = (Variant)Util.getRandom(COMMON_VARIANTS, $$5);
/* 272 */       $$3 = new TropicalFishGroupData(this, $$8); }
/*     */     else
/* 274 */     { this.isSchool = false;
/* 275 */       Pattern[] $$9 = Pattern.values();
/* 276 */       DyeColor[] $$10 = DyeColor.values();
/*     */       
/* 278 */       Pattern $$11 = (Pattern)Util.getRandom((Object[])$$9, $$5);
/* 279 */       DyeColor $$12 = (DyeColor)Util.getRandom((Object[])$$10, $$5);
/* 280 */       DyeColor $$13 = (DyeColor)Util.getRandom((Object[])$$10, $$5);
/* 281 */       $$14 = new Variant($$11, $$12, $$13); }
/*     */ 
/*     */     
/* 284 */     setPackedVariant($$14.getPackedId());
/*     */     
/* 286 */     return $$3;
/*     */   }
/*     */   
/*     */   public static boolean checkTropicalFishSpawnRules(EntityType<TropicalFish> $$0, LevelAccessor $$1, MobSpawnType $$2, BlockPos $$3, RandomSource $$4) {
/* 290 */     return ($$1.getFluidState($$3.below()).is(FluidTags.WATER) && $$1
/* 291 */       .getBlockState($$3.above()).is(Blocks.WATER) && ($$1
/*     */       
/* 293 */       .getBiome($$3).is(BiomeTags.ALLOWS_TROPICAL_FISH_SPAWNS_AT_ANY_HEIGHT) || WaterAnimal.checkSurfaceWaterAnimalSpawnRules((EntityType)$$0, $$1, $$2, $$3, $$4)));
/*     */   }
/*     */   
/*     */   private static class TropicalFishGroupData extends AbstractSchoolingFish.SchoolSpawnGroupData {
/*     */     final TropicalFish.Variant variant;
/*     */     
/*     */     TropicalFishGroupData(TropicalFish $$0, TropicalFish.Variant $$1) {
/* 300 */       super($$0);
/* 301 */       this.variant = $$1;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\TropicalFish.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */