/*     */ package net.minecraft.world.item;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.GlobalPos;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.NbtOps;
/*     */ import net.minecraft.nbt.NbtUtils;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.ai.village.poi.PoiTypes;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.context.UseOnContext;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class CompassItem extends Item implements Vanishable {
/*  26 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   public static final String TAG_LODESTONE_POS = "LodestonePos";
/*     */   public static final String TAG_LODESTONE_DIMENSION = "LodestoneDimension";
/*     */   public static final String TAG_LODESTONE_TRACKED = "LodestoneTracked";
/*     */   
/*     */   public CompassItem(Item.Properties $$0) {
/*  33 */     super($$0);
/*     */   }
/*     */   
/*     */   public static boolean isLodestoneCompass(ItemStack $$0) {
/*  37 */     CompoundTag $$1 = $$0.getTag();
/*  38 */     return ($$1 != null && ($$1.contains("LodestoneDimension") || $$1.contains("LodestonePos")));
/*     */   }
/*     */   
/*     */   private static Optional<ResourceKey<Level>> getLodestoneDimension(CompoundTag $$0) {
/*  42 */     return Level.RESOURCE_KEY_CODEC.parse((DynamicOps)NbtOps.INSTANCE, $$0.get("LodestoneDimension")).result();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static GlobalPos getLodestonePosition(CompoundTag $$0) {
/*  47 */     boolean $$1 = $$0.contains("LodestonePos");
/*  48 */     boolean $$2 = $$0.contains("LodestoneDimension");
/*  49 */     if ($$1 && $$2) {
/*  50 */       Optional<ResourceKey<Level>> $$3 = getLodestoneDimension($$0);
/*  51 */       if ($$3.isPresent()) {
/*  52 */         BlockPos $$4 = NbtUtils.readBlockPos($$0.getCompound("LodestonePos"));
/*  53 */         return GlobalPos.of($$3.get(), $$4);
/*     */       } 
/*     */     } 
/*  56 */     return null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static GlobalPos getSpawnPosition(Level $$0) {
/*  61 */     return $$0.dimensionType().natural() ? GlobalPos.of($$0.dimension(), $$0.getSharedSpawnPos()) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFoil(ItemStack $$0) {
/*  66 */     return (isLodestoneCompass($$0) || super.isFoil($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void inventoryTick(ItemStack $$0, Level $$1, Entity $$2, int $$3, boolean $$4) {
/*  71 */     if ($$1.isClientSide) {
/*     */       return;
/*     */     }
/*     */     
/*  75 */     if (isLodestoneCompass($$0)) {
/*  76 */       CompoundTag $$5 = $$0.getOrCreateTag();
/*  77 */       if ($$5.contains("LodestoneTracked") && !$$5.getBoolean("LodestoneTracked")) {
/*     */         return;
/*     */       }
/*     */       
/*  81 */       Optional<ResourceKey<Level>> $$6 = getLodestoneDimension($$5);
/*  82 */       if ($$6.isPresent() && $$6.get() == $$1.dimension() && $$5.contains("LodestonePos")) {
/*  83 */         BlockPos $$7 = NbtUtils.readBlockPos($$5.getCompound("LodestonePos"));
/*  84 */         if (!$$1.isInWorldBounds($$7) || !((ServerLevel)$$1).getPoiManager().existsAtPosition(PoiTypes.LODESTONE, $$7)) {
/*  85 */           $$5.remove("LodestonePos");
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult useOn(UseOnContext $$0) {
/*  93 */     BlockPos $$1 = $$0.getClickedPos();
/*  94 */     Level $$2 = $$0.getLevel();
/*     */     
/*  96 */     if ($$2.getBlockState($$1).is(Blocks.LODESTONE)) {
/*  97 */       $$2.playSound(null, $$1, SoundEvents.LODESTONE_COMPASS_LOCK, SoundSource.PLAYERS, 1.0F, 1.0F);
/*     */       
/*  99 */       Player $$3 = $$0.getPlayer();
/* 100 */       ItemStack $$4 = $$0.getItemInHand();
/* 101 */       boolean $$5 = (!($$3.getAbilities()).instabuild && $$4.getCount() == 1);
/*     */       
/* 103 */       if ($$5) {
/* 104 */         addLodestoneTags($$2.dimension(), $$1, $$4.getOrCreateTag());
/*     */       } else {
/* 106 */         ItemStack $$6 = new ItemStack(Items.COMPASS, 1);
/* 107 */         CompoundTag $$7 = $$4.hasTag() ? $$4.getTag().copy() : new CompoundTag();
/* 108 */         $$6.setTag($$7);
/* 109 */         if (!($$3.getAbilities()).instabuild) {
/* 110 */           $$4.shrink(1);
/*     */         }
/* 112 */         addLodestoneTags($$2.dimension(), $$1, $$7);
/* 113 */         if (!$$3.getInventory().add($$6)) {
/* 114 */           $$3.drop($$6, false);
/*     */         }
/*     */       } 
/*     */       
/* 118 */       return InteractionResult.sidedSuccess($$2.isClientSide);
/*     */     } 
/* 120 */     return super.useOn($$0);
/*     */   }
/*     */   
/*     */   private void addLodestoneTags(ResourceKey<Level> $$0, BlockPos $$1, CompoundTag $$2) {
/* 124 */     $$2.put("LodestonePos", (Tag)NbtUtils.writeBlockPos($$1));
/* 125 */     Objects.requireNonNull(LOGGER); Level.RESOURCE_KEY_CODEC.encodeStart((DynamicOps)NbtOps.INSTANCE, $$0).resultOrPartial(LOGGER::error).ifPresent($$1 -> $$0.put("LodestoneDimension", $$1));
/* 126 */     $$2.putBoolean("LodestoneTracked", true);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescriptionId(ItemStack $$0) {
/* 131 */     return isLodestoneCompass($$0) ? "item.minecraft.lodestone_compass" : super.getDescriptionId($$0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\CompassItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */