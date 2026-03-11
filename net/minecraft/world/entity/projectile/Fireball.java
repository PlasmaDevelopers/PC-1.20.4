/*    */ package net.minecraft.world.entity.projectile;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.nbt.Tag;
/*    */ import net.minecraft.network.syncher.EntityDataAccessor;
/*    */ import net.minecraft.network.syncher.EntityDataSerializers;
/*    */ import net.minecraft.network.syncher.SynchedEntityData;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.Items;
/*    */ import net.minecraft.world.level.Level;
/*    */ 
/*    */ public abstract class Fireball extends AbstractHurtingProjectile implements ItemSupplier {
/* 14 */   private static final EntityDataAccessor<ItemStack> DATA_ITEM_STACK = SynchedEntityData.defineId(Fireball.class, EntityDataSerializers.ITEM_STACK);
/*    */   
/*    */   public Fireball(EntityType<? extends Fireball> $$0, Level $$1) {
/* 17 */     super((EntityType)$$0, $$1);
/*    */   }
/*    */   
/*    */   public Fireball(EntityType<? extends Fireball> $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6, Level $$7) {
/* 21 */     super((EntityType)$$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7);
/*    */   }
/*    */   
/*    */   public Fireball(EntityType<? extends Fireball> $$0, LivingEntity $$1, double $$2, double $$3, double $$4, Level $$5) {
/* 25 */     super((EntityType)$$0, $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */   
/*    */   public void setItem(ItemStack $$0) {
/* 29 */     if (!$$0.is(Items.FIRE_CHARGE) || $$0.hasTag()) {
/* 30 */       getEntityData().set(DATA_ITEM_STACK, $$0.copyWithCount(1));
/*    */     }
/*    */   }
/*    */   
/*    */   protected ItemStack getItemRaw() {
/* 35 */     return (ItemStack)getEntityData().get(DATA_ITEM_STACK);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getItem() {
/* 40 */     ItemStack $$0 = getItemRaw();
/* 41 */     return $$0.isEmpty() ? new ItemStack((ItemLike)Items.FIRE_CHARGE) : $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void defineSynchedData() {
/* 46 */     getEntityData().define(DATA_ITEM_STACK, ItemStack.EMPTY);
/*    */   }
/*    */ 
/*    */   
/*    */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 51 */     super.addAdditionalSaveData($$0);
/* 52 */     ItemStack $$1 = getItemRaw();
/* 53 */     if (!$$1.isEmpty()) {
/* 54 */       $$0.put("Item", (Tag)$$1.save(new CompoundTag()));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 60 */     super.readAdditionalSaveData($$0);
/* 61 */     ItemStack $$1 = ItemStack.of($$0.getCompound("Item"));
/* 62 */     setItem($$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\projectile\Fireball.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */