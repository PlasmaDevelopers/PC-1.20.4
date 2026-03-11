/*     */ package net.minecraft.world.entity.vehicle;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.world.entity.EntitySelector;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.item.ItemEntity;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*     */ import net.minecraft.world.inventory.HopperMenu;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.entity.Hopper;
/*     */ import net.minecraft.world.level.block.entity.HopperBlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ 
/*     */ public class MinecartHopper
/*     */   extends AbstractMinecartContainer implements Hopper {
/*     */   private boolean enabled = true;
/*     */   
/*     */   public MinecartHopper(EntityType<? extends MinecartHopper> $$0, Level $$1) {
/*  24 */     super($$0, $$1);
/*     */   }
/*     */   
/*     */   public MinecartHopper(Level $$0, double $$1, double $$2, double $$3) {
/*  28 */     super(EntityType.HOPPER_MINECART, $$1, $$2, $$3, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public AbstractMinecart.Type getMinecartType() {
/*  33 */     return AbstractMinecart.Type.HOPPER;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState getDefaultDisplayBlockState() {
/*  38 */     return Blocks.HOPPER.defaultBlockState();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDefaultDisplayOffset() {
/*  43 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getContainerSize() {
/*  48 */     return 5;
/*     */   }
/*     */ 
/*     */   
/*     */   public void activateMinecart(int $$0, int $$1, int $$2, boolean $$3) {
/*  53 */     boolean $$4 = !$$3;
/*     */     
/*  55 */     if ($$4 != isEnabled()) {
/*  56 */       setEnabled($$4);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isEnabled() {
/*  61 */     return this.enabled;
/*     */   }
/*     */   
/*     */   public void setEnabled(boolean $$0) {
/*  65 */     this.enabled = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getLevelX() {
/*  70 */     return getX();
/*     */   }
/*     */ 
/*     */   
/*     */   public double getLevelY() {
/*  75 */     return getY() + 0.5D;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getLevelZ() {
/*  80 */     return getZ();
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/*  85 */     super.tick();
/*     */     
/*  87 */     if (!(level()).isClientSide && isAlive() && isEnabled() && 
/*  88 */       suckInItems()) {
/*  89 */       setChanged();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean suckInItems() {
/*  95 */     if (HopperBlockEntity.suckInItems(level(), this)) {
/*  96 */       return true;
/*     */     }
/*     */     
/*  99 */     List<ItemEntity> $$0 = level().getEntitiesOfClass(ItemEntity.class, getBoundingBox().inflate(0.25D, 0.0D, 0.25D), EntitySelector.ENTITY_STILL_ALIVE);
/*     */     
/* 101 */     for (ItemEntity $$1 : $$0) {
/* 102 */       if (HopperBlockEntity.addItem(this, $$1)) {
/* 103 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 107 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Item getDropItem() {
/* 112 */     return Items.HOPPER_MINECART;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addAdditionalSaveData(CompoundTag $$0) {
/* 117 */     super.addAdditionalSaveData($$0);
/* 118 */     $$0.putBoolean("Enabled", this.enabled);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void readAdditionalSaveData(CompoundTag $$0) {
/* 123 */     super.readAdditionalSaveData($$0);
/* 124 */     this.enabled = $$0.contains("Enabled") ? $$0.getBoolean("Enabled") : true;
/*     */   }
/*     */ 
/*     */   
/*     */   public AbstractContainerMenu createMenu(int $$0, Inventory $$1) {
/* 129 */     return (AbstractContainerMenu)new HopperMenu($$0, $$1, this);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\vehicle\MinecartHopper.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */