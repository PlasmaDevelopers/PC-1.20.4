/*     */ package net.minecraft.world.level.block.entity;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.LockCode;
/*     */ import net.minecraft.world.MenuProvider;
/*     */ import net.minecraft.world.Nameable;
/*     */ import net.minecraft.world.effect.MobEffect;
/*     */ import net.minecraft.world.effect.MobEffectInstance;
/*     */ import net.minecraft.world.effect.MobEffects;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*     */ import net.minecraft.world.inventory.BeaconMenu;
/*     */ import net.minecraft.world.inventory.ContainerData;
/*     */ import net.minecraft.world.inventory.ContainerLevelAccess;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.BeaconBeamBlock;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.levelgen.Heightmap;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ 
/*     */ public class BeaconBlockEntity
/*     */   extends BlockEntity implements MenuProvider, Nameable {
/*     */   private static final int MAX_LEVELS = 4;
/*  48 */   public static final MobEffect[][] BEACON_EFFECTS = new MobEffect[][] { { MobEffects.MOVEMENT_SPEED, MobEffects.DIG_SPEED }, { MobEffects.DAMAGE_RESISTANCE, MobEffects.JUMP }, { MobEffects.DAMAGE_BOOST }, { MobEffects.REGENERATION } };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  53 */   private static final Set<MobEffect> VALID_EFFECTS = (Set<MobEffect>)Arrays.<MobEffect[]>stream(BEACON_EFFECTS).flatMap(Arrays::stream).collect(Collectors.toSet());
/*     */   
/*     */   public static final int DATA_LEVELS = 0;
/*     */   
/*     */   public static final int DATA_PRIMARY = 1;
/*     */   public static final int DATA_SECONDARY = 2;
/*     */   public static final int NUM_DATA_VALUES = 3;
/*     */   private static final int BLOCKS_CHECK_PER_TICK = 10;
/*  61 */   private static final Component DEFAULT_NAME = (Component)Component.translatable("container.beacon");
/*     */   
/*     */   private static final String TAG_PRIMARY = "primary_effect";
/*     */   
/*     */   private static final String TAG_SECONDARY = "secondary_effect";
/*  66 */   List<BeaconBeamSection> beamSections = Lists.newArrayList();
/*  67 */   private List<BeaconBeamSection> checkingBeamSections = Lists.newArrayList();
/*     */   
/*     */   int levels;
/*     */   
/*     */   private int lastCheckY;
/*     */   
/*     */   @Nullable
/*     */   MobEffect primaryPower;
/*     */   
/*     */   @Nullable
/*     */   MobEffect secondaryPower;
/*     */   @Nullable
/*     */   private Component name;
/*  80 */   private LockCode lockKey = LockCode.NO_LOCK;
/*     */   
/*  82 */   private final ContainerData dataAccess = new ContainerData()
/*     */     {
/*     */       public int get(int $$0) {
/*  85 */         switch ($$0) { case 0: case 1: case 2:  }  return 
/*     */ 
/*     */ 
/*     */           
/*  89 */           0;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public void set(int $$0, int $$1) {
/*  95 */         switch ($$0) { case 0:
/*  96 */             BeaconBlockEntity.this.levels = $$1; break;
/*     */           case 1:
/*  98 */             if (!BeaconBlockEntity.this.level.isClientSide && !BeaconBlockEntity.this.beamSections.isEmpty()) {
/*  99 */               BeaconBlockEntity.playSound(BeaconBlockEntity.this.level, BeaconBlockEntity.this.worldPosition, SoundEvents.BEACON_POWER_SELECT);
/*     */             }
/* 101 */             BeaconBlockEntity.this.primaryPower = BeaconBlockEntity.filterEffect(BeaconMenu.decodeEffect($$1)); break;
/*     */           case 2:
/* 103 */             BeaconBlockEntity.this.secondaryPower = BeaconBlockEntity.filterEffect(BeaconMenu.decodeEffect($$1));
/*     */             break; }
/*     */       
/*     */       }
/*     */       
/*     */       public int getCount() {
/* 109 */         return 3;
/*     */       }
/*     */     };
/*     */   
/*     */   @Nullable
/*     */   static MobEffect filterEffect(@Nullable MobEffect $$0) {
/* 115 */     return VALID_EFFECTS.contains($$0) ? $$0 : null;
/*     */   }
/*     */   
/*     */   public BeaconBlockEntity(BlockPos $$0, BlockState $$1) {
/* 119 */     super(BlockEntityType.BEACON, $$0, $$1);
/*     */   }
/*     */   public static void tick(Level $$0, BlockPos $$1, BlockState $$2, BeaconBlockEntity $$3) {
/*     */     BlockPos $$8;
/* 123 */     int $$4 = $$1.getX();
/* 124 */     int $$5 = $$1.getY();
/* 125 */     int $$6 = $$1.getZ();
/*     */ 
/*     */     
/* 128 */     if ($$3.lastCheckY < $$5) {
/* 129 */       BlockPos $$7 = $$1;
/* 130 */       $$3.checkingBeamSections = Lists.newArrayList();
/* 131 */       $$3.lastCheckY = $$7.getY() - 1;
/*     */     } else {
/* 133 */       $$8 = new BlockPos($$4, $$3.lastCheckY + 1, $$6);
/*     */     } 
/*     */     
/* 136 */     BeaconBeamSection $$9 = $$3.checkingBeamSections.isEmpty() ? null : $$3.checkingBeamSections.get($$3.checkingBeamSections.size() - 1);
/*     */     
/* 138 */     int $$10 = $$0.getHeight(Heightmap.Types.WORLD_SURFACE, $$4, $$6);
/*     */ 
/*     */     
/* 141 */     for (int $$11 = 0; $$11 < 10 && 
/* 142 */       $$8.getY() <= $$10; $$11++) {
/*     */ 
/*     */       
/* 145 */       BlockState $$12 = $$0.getBlockState($$8);
/* 146 */       Block $$13 = $$12.getBlock();
/* 147 */       if ($$13 instanceof BeaconBeamBlock) {
/* 148 */         float[] $$14 = ((BeaconBeamBlock)$$13).getColor().getTextureDiffuseColors();
/*     */         
/* 150 */         if ($$3.checkingBeamSections.size() <= 1) {
/* 151 */           $$9 = new BeaconBeamSection($$14);
/* 152 */           $$3.checkingBeamSections.add($$9);
/* 153 */         } else if ($$9 != null) {
/*     */           
/* 155 */           if (Arrays.equals($$14, $$9.color)) {
/* 156 */             $$9.increaseHeight();
/*     */           } else {
/* 158 */             $$9 = new BeaconBeamSection(new float[] { ($$9.color[0] + $$14[0]) / 2.0F, ($$9.color[1] + $$14[1]) / 2.0F, ($$9.color[2] + $$14[2]) / 2.0F });
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 163 */             $$3.checkingBeamSections.add($$9);
/*     */           } 
/*     */         } 
/* 166 */       } else if ($$9 != null && ($$12.getLightBlock((BlockGetter)$$0, $$8) < 15 || $$12.is(Blocks.BEDROCK))) {
/* 167 */         $$9.increaseHeight();
/*     */       } else {
/* 169 */         $$3.checkingBeamSections.clear();
/* 170 */         $$3.lastCheckY = $$10;
/*     */         break;
/*     */       } 
/* 173 */       $$8 = $$8.above();
/* 174 */       $$3.lastCheckY++;
/*     */     } 
/*     */     
/* 177 */     int $$15 = $$3.levels;
/*     */     
/* 179 */     if ($$0.getGameTime() % 80L == 0L) {
/* 180 */       if (!$$3.beamSections.isEmpty()) {
/* 181 */         $$3.levels = updateBase($$0, $$4, $$5, $$6);
/*     */       }
/*     */       
/* 184 */       if ($$3.levels > 0 && !$$3.beamSections.isEmpty()) {
/* 185 */         applyEffects($$0, $$1, $$3.levels, $$3.primaryPower, $$3.secondaryPower);
/* 186 */         playSound($$0, $$1, SoundEvents.BEACON_AMBIENT);
/*     */       } 
/*     */     } 
/*     */     
/* 190 */     if ($$3.lastCheckY >= $$10) {
/* 191 */       $$3.lastCheckY = $$0.getMinBuildHeight() - 1;
/* 192 */       boolean $$16 = ($$15 > 0);
/*     */       
/* 194 */       $$3.beamSections = $$3.checkingBeamSections;
/*     */       
/* 196 */       if (!$$0.isClientSide) {
/* 197 */         boolean $$17 = ($$3.levels > 0);
/*     */         
/* 199 */         if (!$$16 && $$17) {
/* 200 */           playSound($$0, $$1, SoundEvents.BEACON_ACTIVATE);
/*     */           
/* 202 */           for (ServerPlayer $$18 : $$0.getEntitiesOfClass(ServerPlayer.class, (new AABB($$4, $$5, $$6, $$4, ($$5 - 4), $$6)).inflate(10.0D, 5.0D, 10.0D))) {
/* 203 */             CriteriaTriggers.CONSTRUCT_BEACON.trigger($$18, $$3.levels);
/*     */           }
/* 205 */         } else if ($$16 && !$$17) {
/* 206 */           playSound($$0, $$1, SoundEvents.BEACON_DEACTIVATE);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static int updateBase(Level $$0, int $$1, int $$2, int $$3) {
/* 213 */     int $$4 = 0;
/* 214 */     for (int $$5 = 1; $$5 <= 4; ) {
/* 215 */       int $$6 = $$2 - $$5;
/* 216 */       if ($$6 < $$0.getMinBuildHeight()) {
/*     */         break;
/*     */       }
/*     */       
/* 220 */       boolean $$7 = true;
/* 221 */       for (int $$8 = $$1 - $$5; $$8 <= $$1 + $$5 && $$7; $$8++) {
/* 222 */         for (int $$9 = $$3 - $$5; $$9 <= $$3 + $$5; $$9++) {
/* 223 */           if (!$$0.getBlockState(new BlockPos($$8, $$6, $$9)).is(BlockTags.BEACON_BASE_BLOCKS)) {
/* 224 */             $$7 = false;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/* 230 */       if ($$7) {
/* 231 */         $$4 = $$5;
/*     */         
/*     */         $$5++;
/*     */       } 
/*     */     } 
/*     */     
/* 237 */     return $$4;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRemoved() {
/* 242 */     playSound(this.level, this.worldPosition, SoundEvents.BEACON_DEACTIVATE);
/* 243 */     super.setRemoved();
/*     */   }
/*     */   
/*     */   private static void applyEffects(Level $$0, BlockPos $$1, int $$2, @Nullable MobEffect $$3, @Nullable MobEffect $$4) {
/* 247 */     if ($$0.isClientSide || $$3 == null) {
/*     */       return;
/*     */     }
/*     */     
/* 251 */     double $$5 = ($$2 * 10 + 10);
/* 252 */     int $$6 = 0;
/* 253 */     if ($$2 >= 4 && $$3 == $$4) {
/* 254 */       $$6 = 1;
/*     */     }
/* 256 */     int $$7 = (9 + $$2 * 2) * 20;
/*     */     
/* 258 */     AABB $$8 = (new AABB($$1)).inflate($$5).expandTowards(0.0D, $$0.getHeight(), 0.0D);
/* 259 */     List<Player> $$9 = $$0.getEntitiesOfClass(Player.class, $$8);
/* 260 */     for (Player $$10 : $$9) {
/* 261 */       $$10.addEffect(new MobEffectInstance($$3, $$7, $$6, true, true));
/*     */     }
/*     */     
/* 264 */     if ($$2 >= 4 && $$3 != $$4 && $$4 != null) {
/* 265 */       for (Player $$11 : $$9) {
/* 266 */         $$11.addEffect(new MobEffectInstance($$4, $$7, 0, true, true));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static void playSound(Level $$0, BlockPos $$1, SoundEvent $$2) {
/* 272 */     $$0.playSound(null, $$1, $$2, SoundSource.BLOCKS, 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public List<BeaconBeamSection> getBeamSections() {
/* 276 */     return (this.levels == 0) ? (List<BeaconBeamSection>)ImmutableList.of() : this.beamSections;
/*     */   }
/*     */ 
/*     */   
/*     */   public ClientboundBlockEntityDataPacket getUpdatePacket() {
/* 281 */     return ClientboundBlockEntityDataPacket.create(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public CompoundTag getUpdateTag() {
/* 286 */     return saveWithoutMetadata();
/*     */   }
/*     */   
/*     */   private static void storeEffect(CompoundTag $$0, String $$1, @Nullable MobEffect $$2) {
/* 290 */     if ($$2 != null) {
/* 291 */       ResourceLocation $$3 = BuiltInRegistries.MOB_EFFECT.getKey($$2);
/* 292 */       if ($$3 != null) {
/* 293 */         $$0.putString($$1, $$3.toString());
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static MobEffect loadEffect(CompoundTag $$0, String $$1) {
/* 300 */     if ($$0.contains($$1, 8)) {
/* 301 */       ResourceLocation $$2 = ResourceLocation.tryParse($$0.getString($$1));
/* 302 */       return filterEffect((MobEffect)BuiltInRegistries.MOB_EFFECT.get($$2));
/*     */     } 
/* 304 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void load(CompoundTag $$0) {
/* 309 */     super.load($$0);
/*     */     
/* 311 */     this.primaryPower = loadEffect($$0, "primary_effect");
/* 312 */     this.secondaryPower = loadEffect($$0, "secondary_effect");
/*     */     
/* 314 */     if ($$0.contains("CustomName", 8)) {
/* 315 */       this.name = (Component)Component.Serializer.fromJson($$0.getString("CustomName"));
/*     */     }
/*     */     
/* 318 */     this.lockKey = LockCode.fromTag($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void saveAdditional(CompoundTag $$0) {
/* 323 */     super.saveAdditional($$0);
/*     */     
/* 325 */     storeEffect($$0, "primary_effect", this.primaryPower);
/* 326 */     storeEffect($$0, "secondary_effect", this.secondaryPower);
/* 327 */     $$0.putInt("Levels", this.levels);
/*     */     
/* 329 */     if (this.name != null) {
/* 330 */       $$0.putString("CustomName", Component.Serializer.toJson(this.name));
/*     */     }
/*     */     
/* 333 */     this.lockKey.addToTag($$0);
/*     */   }
/*     */   
/*     */   public void setCustomName(@Nullable Component $$0) {
/* 337 */     this.name = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Component getCustomName() {
/* 343 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public AbstractContainerMenu createMenu(int $$0, Inventory $$1, Player $$2) {
/* 349 */     if (BaseContainerBlockEntity.canUnlock($$2, this.lockKey, getDisplayName())) {
/* 350 */       return (AbstractContainerMenu)new BeaconMenu($$0, (Container)$$1, this.dataAccess, ContainerLevelAccess.create(this.level, getBlockPos()));
/*     */     }
/* 352 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Component getDisplayName() {
/* 357 */     return getName();
/*     */   }
/*     */ 
/*     */   
/*     */   public Component getName() {
/* 362 */     if (this.name != null) {
/* 363 */       return this.name;
/*     */     }
/* 365 */     return DEFAULT_NAME;
/*     */   }
/*     */   
/*     */   public static class BeaconBeamSection {
/*     */     final float[] color;
/*     */     private int height;
/*     */     
/*     */     public BeaconBeamSection(float[] $$0) {
/* 373 */       this.color = $$0;
/* 374 */       this.height = 1;
/*     */     }
/*     */     
/*     */     protected void increaseHeight() {
/* 378 */       this.height++;
/*     */     }
/*     */     
/*     */     public float[] getColor() {
/* 382 */       return this.color;
/*     */     }
/*     */     
/*     */     public int getHeight() {
/* 386 */       return this.height;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLevel(Level $$0) {
/* 392 */     super.setLevel($$0);
/* 393 */     this.lastCheckY = $$0.getMinBuildHeight() - 1;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\BeaconBlockEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */