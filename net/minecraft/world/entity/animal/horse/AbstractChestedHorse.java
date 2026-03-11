/*     */ package net.minecraft.world.entity.animal.horse;
/*     */ import java.util.Objects;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.EntityDimensions;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.SlotAccess;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ 
/*     */ public abstract class AbstractChestedHorse extends AbstractHorse {
/*  25 */   private static final EntityDataAccessor<Boolean> DATA_ID_CHEST = SynchedEntityData.defineId(AbstractChestedHorse.class, EntityDataSerializers.BOOLEAN);
/*     */   public static final int INV_CHEST_COUNT = 15;
/*     */   
/*     */   protected AbstractChestedHorse(EntityType<? extends AbstractChestedHorse> $$0, Level $$1) {
/*  29 */     super((EntityType)$$0, $$1);
/*     */     
/*  31 */     this.canGallop = false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void randomizeAttributes(RandomSource $$0) {
/*  36 */     Objects.requireNonNull($$0); getAttribute(Attributes.MAX_HEALTH).setBaseValue(generateMaxHealth($$0::nextInt));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/*  41 */     super.defineSynchedData();
/*     */     
/*  43 */     this.entityData.define(DATA_ID_CHEST, Boolean.valueOf(false));
/*     */   }
/*     */   
/*     */   public static AttributeSupplier.Builder createBaseChestedHorseAttributes() {
/*  47 */     return createBaseHorseAttributes()
/*  48 */       .add(Attributes.MOVEMENT_SPEED, 0.17499999701976776D)
/*  49 */       .add(Attributes.JUMP_STRENGTH, 0.5D);
/*     */   }
/*     */   
/*     */   public boolean hasChest() {
/*  53 */     return ((Boolean)this.entityData.get(DATA_ID_CHEST)).booleanValue();
/*     */   }
/*     */   
/*     */   public void setChest(boolean $$0) {
/*  57 */     this.entityData.set(DATA_ID_CHEST, Boolean.valueOf($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getInventorySize() {
/*  62 */     if (hasChest()) {
/*  63 */       return 17;
/*     */     }
/*  65 */     return super.getInventorySize();
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getPassengersRidingOffsetY(EntityDimensions $$0, float $$1) {
/*  70 */     return $$0.height - (isBaby() ? 0.15625F : 0.3875F) * $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void dropEquipment() {
/*  75 */     super.dropEquipment();
/*  76 */     if (hasChest()) {
/*  77 */       if (!(level()).isClientSide) {
/*  78 */         spawnAtLocation((ItemLike)Blocks.CHEST);
/*     */       }
/*  80 */       setChest(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/*  86 */     super.addAdditionalSaveData($$0);
/*     */     
/*  88 */     $$0.putBoolean("ChestedHorse", hasChest());
/*  89 */     if (hasChest()) {
/*  90 */       ListTag $$1 = new ListTag();
/*     */       
/*  92 */       for (int $$2 = 2; $$2 < this.inventory.getContainerSize(); $$2++) {
/*  93 */         ItemStack $$3 = this.inventory.getItem($$2);
/*     */         
/*  95 */         if (!$$3.isEmpty()) {
/*  96 */           CompoundTag $$4 = new CompoundTag();
/*     */           
/*  98 */           $$4.putByte("Slot", (byte)$$2);
/*     */           
/* 100 */           $$3.save($$4);
/* 101 */           $$1.add($$4);
/*     */         } 
/*     */       } 
/* 104 */       $$0.put("Items", (Tag)$$1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 110 */     super.readAdditionalSaveData($$0);
/*     */     
/* 112 */     setChest($$0.getBoolean("ChestedHorse"));
/*     */ 
/*     */     
/* 115 */     createInventory();
/*     */     
/* 117 */     if (hasChest()) {
/* 118 */       ListTag $$1 = $$0.getList("Items", 10);
/*     */       
/* 120 */       for (int $$2 = 0; $$2 < $$1.size(); $$2++) {
/* 121 */         CompoundTag $$3 = $$1.getCompound($$2);
/* 122 */         int $$4 = $$3.getByte("Slot") & 0xFF;
/*     */         
/* 124 */         if ($$4 >= 2 && $$4 < this.inventory.getContainerSize()) {
/* 125 */           this.inventory.setItem($$4, ItemStack.of($$3));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 130 */     updateContainerEquipment();
/*     */   }
/*     */ 
/*     */   
/*     */   public SlotAccess getSlot(int $$0) {
/* 135 */     if ($$0 == 499) {
/* 136 */       return new SlotAccess()
/*     */         {
/*     */           public ItemStack get() {
/* 139 */             return AbstractChestedHorse.this.hasChest() ? new ItemStack((ItemLike)Items.CHEST) : ItemStack.EMPTY;
/*     */           }
/*     */ 
/*     */           
/*     */           public boolean set(ItemStack $$0) {
/* 144 */             if ($$0.isEmpty()) {
/* 145 */               if (AbstractChestedHorse.this.hasChest()) {
/* 146 */                 AbstractChestedHorse.this.setChest(false);
/* 147 */                 AbstractChestedHorse.this.createInventory();
/*     */               } 
/* 149 */               return true;
/*     */             } 
/* 151 */             if ($$0.is(Items.CHEST)) {
/* 152 */               if (!AbstractChestedHorse.this.hasChest()) {
/* 153 */                 AbstractChestedHorse.this.setChest(true);
/* 154 */                 AbstractChestedHorse.this.createInventory();
/*     */               } 
/* 156 */               return true;
/*     */             } 
/* 158 */             return false;
/*     */           }
/*     */         };
/*     */     }
/* 162 */     return super.getSlot($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult mobInteract(Player $$0, InteractionHand $$1) {
/* 167 */     boolean $$2 = (!isBaby() && isTamed() && $$0.isSecondaryUseActive());
/* 168 */     if (isVehicle() || $$2) {
/* 169 */       return super.mobInteract($$0, $$1);
/*     */     }
/*     */     
/* 172 */     ItemStack $$3 = $$0.getItemInHand($$1);
/* 173 */     if (!$$3.isEmpty()) {
/* 174 */       if (isFood($$3)) {
/* 175 */         return fedFood($$0, $$3);
/*     */       }
/*     */       
/* 178 */       if (!isTamed()) {
/* 179 */         makeMad();
/* 180 */         return InteractionResult.sidedSuccess((level()).isClientSide);
/*     */       } 
/*     */       
/* 183 */       if (!hasChest() && $$3.is(Items.CHEST)) {
/* 184 */         equipChest($$0, $$3);
/* 185 */         return InteractionResult.sidedSuccess((level()).isClientSide);
/*     */       } 
/*     */     } 
/* 188 */     return super.mobInteract($$0, $$1);
/*     */   }
/*     */   
/*     */   private void equipChest(Player $$0, ItemStack $$1) {
/* 192 */     setChest(true);
/* 193 */     playChestEquipsSound();
/* 194 */     if (!($$0.getAbilities()).instabuild) {
/* 195 */       $$1.shrink(1);
/*     */     }
/* 197 */     createInventory();
/*     */   }
/*     */   
/*     */   protected void playChestEquipsSound() {
/* 201 */     playSound(SoundEvents.DONKEY_CHEST, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
/*     */   }
/*     */   
/*     */   public int getInventoryColumns() {
/* 205 */     return 5;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\horse\AbstractChestedHorse.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */