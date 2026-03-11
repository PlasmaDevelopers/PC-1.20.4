/*    */ package net.minecraft.world.entity.projectile;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.nbt.Tag;
/*    */ import net.minecraft.network.syncher.EntityDataAccessor;
/*    */ import net.minecraft.network.syncher.EntityDataSerializers;
/*    */ import net.minecraft.network.syncher.SynchedEntityData;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.item.Item;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.Level;
/*    */ 
/*    */ public abstract class ThrowableItemProjectile extends ThrowableProjectile implements ItemSupplier {
/* 14 */   private static final EntityDataAccessor<ItemStack> DATA_ITEM_STACK = SynchedEntityData.defineId(ThrowableItemProjectile.class, EntityDataSerializers.ITEM_STACK);
/*    */   
/*    */   public ThrowableItemProjectile(EntityType<? extends ThrowableItemProjectile> $$0, Level $$1) {
/* 17 */     super((EntityType)$$0, $$1);
/*    */   }
/*    */   
/*    */   public ThrowableItemProjectile(EntityType<? extends ThrowableItemProjectile> $$0, double $$1, double $$2, double $$3, Level $$4) {
/* 21 */     super((EntityType)$$0, $$1, $$2, $$3, $$4);
/*    */   }
/*    */   
/*    */   public ThrowableItemProjectile(EntityType<? extends ThrowableItemProjectile> $$0, LivingEntity $$1, Level $$2) {
/* 25 */     super((EntityType)$$0, $$1, $$2);
/*    */   }
/*    */   
/*    */   public void setItem(ItemStack $$0) {
/* 29 */     if (!$$0.is(getDefaultItem()) || $$0.hasTag()) {
/* 30 */       getEntityData().set(DATA_ITEM_STACK, $$0.copyWithCount(1));
/*    */     }
/*    */   }
/*    */   
/*    */   protected abstract Item getDefaultItem();
/*    */   
/*    */   protected ItemStack getItemRaw() {
/* 37 */     return (ItemStack)getEntityData().get(DATA_ITEM_STACK);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getItem() {
/* 42 */     ItemStack $$0 = getItemRaw();
/* 43 */     return $$0.isEmpty() ? new ItemStack((ItemLike)getDefaultItem()) : $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void defineSynchedData() {
/* 48 */     getEntityData().define(DATA_ITEM_STACK, ItemStack.EMPTY);
/*    */   }
/*    */ 
/*    */   
/*    */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 53 */     super.addAdditionalSaveData($$0);
/* 54 */     ItemStack $$1 = getItemRaw();
/* 55 */     if (!$$1.isEmpty()) {
/* 56 */       $$0.put("Item", (Tag)$$1.save(new CompoundTag()));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 62 */     super.readAdditionalSaveData($$0);
/* 63 */     ItemStack $$1 = ItemStack.of($$0.getCompound("Item"));
/* 64 */     setItem($$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\projectile\ThrowableItemProjectile.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */