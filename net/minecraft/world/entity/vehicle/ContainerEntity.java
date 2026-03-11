/*     */ package net.minecraft.world.entity.vehicle;
/*     */ 
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.core.NonNullList;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.ContainerHelper;
/*     */ import net.minecraft.world.Containers;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.MenuProvider;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.SlotAccess;
/*     */ import net.minecraft.world.entity.monster.piglin.PiglinAi;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.storage.loot.LootParams;
/*     */ import net.minecraft.world.level.storage.loot.LootTable;
/*     */ import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
/*     */ import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface ContainerEntity
/*     */   extends Container, MenuProvider
/*     */ {
/*     */   Vec3 position();
/*     */   
/*     */   @Nullable
/*     */   ResourceLocation getLootTable();
/*     */   
/*     */   void setLootTable(@Nullable ResourceLocation paramResourceLocation);
/*     */   
/*     */   long getLootTableSeed();
/*     */   
/*     */   void setLootTableSeed(long paramLong);
/*     */   
/*     */   NonNullList<ItemStack> getItemStacks();
/*     */   
/*     */   void clearItemStacks();
/*     */   
/*     */   Level level();
/*     */   
/*     */   boolean isRemoved();
/*     */   
/*     */   default boolean isEmpty() {
/*  63 */     return isChestVehicleEmpty();
/*     */   }
/*     */   
/*     */   default void addChestVehicleSaveData(CompoundTag $$0) {
/*  67 */     if (getLootTable() != null) {
/*  68 */       $$0.putString("LootTable", getLootTable().toString());
/*  69 */       if (getLootTableSeed() != 0L) {
/*  70 */         $$0.putLong("LootTableSeed", getLootTableSeed());
/*     */       }
/*     */     } else {
/*  73 */       ContainerHelper.saveAllItems($$0, getItemStacks());
/*     */     } 
/*     */   }
/*     */   
/*     */   default void readChestVehicleSaveData(CompoundTag $$0) {
/*  78 */     clearItemStacks();
/*     */     
/*  80 */     if ($$0.contains("LootTable", 8)) {
/*  81 */       setLootTable(new ResourceLocation($$0.getString("LootTable")));
/*  82 */       setLootTableSeed($$0.getLong("LootTableSeed"));
/*     */     } else {
/*  84 */       ContainerHelper.loadAllItems($$0, getItemStacks());
/*     */     } 
/*     */   }
/*     */   
/*     */   default void chestVehicleDestroyed(DamageSource $$0, Level $$1, Entity $$2) {
/*  89 */     if (!$$1.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
/*     */       return;
/*     */     }
/*     */     
/*  93 */     Containers.dropContents($$1, $$2, this);
/*     */     
/*  95 */     if (!$$1.isClientSide) {
/*  96 */       Entity $$3 = $$0.getDirectEntity();
/*  97 */       if ($$3 != null && $$3.getType() == EntityType.PLAYER) {
/*  98 */         PiglinAi.angerNearbyPiglins((Player)$$3, true);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   default InteractionResult interactWithContainerVehicle(Player $$0) {
/* 104 */     $$0.openMenu(this);
/* 105 */     if (!($$0.level()).isClientSide) {
/* 106 */       return InteractionResult.CONSUME;
/*     */     }
/* 108 */     return InteractionResult.SUCCESS;
/*     */   }
/*     */   
/*     */   default void unpackChestVehicleLootTable(@Nullable Player $$0) {
/* 112 */     MinecraftServer $$1 = level().getServer();
/* 113 */     if (getLootTable() != null && $$1 != null) {
/* 114 */       LootTable $$2 = $$1.getLootData().getLootTable(getLootTable());
/* 115 */       if ($$0 != null) {
/* 116 */         CriteriaTriggers.GENERATE_LOOT.trigger((ServerPlayer)$$0, getLootTable());
/*     */       }
/* 118 */       setLootTable((ResourceLocation)null);
/*     */ 
/*     */       
/* 121 */       LootParams.Builder $$3 = (new LootParams.Builder((ServerLevel)level())).withParameter(LootContextParams.ORIGIN, position());
/*     */       
/* 123 */       if ($$0 != null) {
/* 124 */         $$3.withLuck($$0.getLuck()).withParameter(LootContextParams.THIS_ENTITY, $$0);
/*     */       }
/* 126 */       $$2.fill(this, $$3.create(LootContextParamSets.CHEST), getLootTableSeed());
/*     */     } 
/*     */   }
/*     */   
/*     */   default void clearChestVehicleContent() {
/* 131 */     unpackChestVehicleLootTable((Player)null);
/* 132 */     getItemStacks().clear();
/*     */   }
/*     */   
/*     */   default boolean isChestVehicleEmpty() {
/* 136 */     for (ItemStack $$0 : getItemStacks()) {
/* 137 */       if (!$$0.isEmpty()) {
/* 138 */         return false;
/*     */       }
/*     */     } 
/* 141 */     return true;
/*     */   }
/*     */   
/*     */   default ItemStack removeChestVehicleItemNoUpdate(int $$0) {
/* 145 */     unpackChestVehicleLootTable((Player)null);
/* 146 */     ItemStack $$1 = (ItemStack)getItemStacks().get($$0);
/* 147 */     if ($$1.isEmpty()) {
/* 148 */       return ItemStack.EMPTY;
/*     */     }
/* 150 */     getItemStacks().set($$0, ItemStack.EMPTY);
/* 151 */     return $$1;
/*     */   }
/*     */   
/*     */   default ItemStack getChestVehicleItem(int $$0) {
/* 155 */     unpackChestVehicleLootTable((Player)null);
/* 156 */     return (ItemStack)getItemStacks().get($$0);
/*     */   }
/*     */   
/*     */   default ItemStack removeChestVehicleItem(int $$0, int $$1) {
/* 160 */     unpackChestVehicleLootTable((Player)null);
/* 161 */     return ContainerHelper.removeItem((List)getItemStacks(), $$0, $$1);
/*     */   }
/*     */   
/*     */   default void setChestVehicleItem(int $$0, ItemStack $$1) {
/* 165 */     unpackChestVehicleLootTable((Player)null);
/* 166 */     getItemStacks().set($$0, $$1);
/* 167 */     if (!$$1.isEmpty() && $$1.getCount() > getMaxStackSize()) {
/* 168 */       $$1.setCount(getMaxStackSize());
/*     */     }
/*     */   }
/*     */   
/*     */   default SlotAccess getChestVehicleSlot(final int slot) {
/* 173 */     if (slot >= 0 && slot < getContainerSize()) {
/* 174 */       return new SlotAccess()
/*     */         {
/*     */           public ItemStack get() {
/* 177 */             return ContainerEntity.this.getChestVehicleItem(slot);
/*     */           }
/*     */ 
/*     */           
/*     */           public boolean set(ItemStack $$0) {
/* 182 */             ContainerEntity.this.setChestVehicleItem(slot, $$0);
/* 183 */             return true;
/*     */           }
/*     */         };
/*     */     }
/*     */     
/* 188 */     return SlotAccess.NULL;
/*     */   }
/*     */   
/*     */   default boolean isChestVehicleStillValid(Player $$0) {
/* 192 */     return (!isRemoved() && position().closerThan((Position)$$0.position(), 8.0D));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\vehicle\ContainerEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */