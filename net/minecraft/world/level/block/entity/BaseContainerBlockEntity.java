/*    */ package net.minecraft.world.level.block.entity;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.sounds.SoundSource;
/*    */ import net.minecraft.world.Container;
/*    */ import net.minecraft.world.LockCode;
/*    */ import net.minecraft.world.MenuProvider;
/*    */ import net.minecraft.world.Nameable;
/*    */ import net.minecraft.world.entity.player.Inventory;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public abstract class BaseContainerBlockEntity
/*    */   extends BlockEntity
/*    */   implements Container, MenuProvider, Nameable {
/* 21 */   private LockCode lockKey = LockCode.NO_LOCK;
/*    */   @Nullable
/*    */   private Component name;
/*    */   
/*    */   protected BaseContainerBlockEntity(BlockEntityType<?> $$0, BlockPos $$1, BlockState $$2) {
/* 26 */     super($$0, $$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   public void load(CompoundTag $$0) {
/* 31 */     super.load($$0);
/*    */     
/* 33 */     this.lockKey = LockCode.fromTag($$0);
/*    */     
/* 35 */     if ($$0.contains("CustomName", 8)) {
/* 36 */       this.name = (Component)Component.Serializer.fromJson($$0.getString("CustomName"));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected void saveAdditional(CompoundTag $$0) {
/* 42 */     super.saveAdditional($$0);
/* 43 */     this.lockKey.addToTag($$0);
/*    */     
/* 45 */     if (this.name != null) {
/* 46 */       $$0.putString("CustomName", Component.Serializer.toJson(this.name));
/*    */     }
/*    */   }
/*    */   
/*    */   public void setCustomName(Component $$0) {
/* 51 */     this.name = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public Component getName() {
/* 56 */     if (this.name != null) {
/* 57 */       return this.name;
/*    */     }
/* 59 */     return getDefaultName();
/*    */   }
/*    */ 
/*    */   
/*    */   public Component getDisplayName() {
/* 64 */     return getName();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Component getCustomName() {
/* 70 */     return this.name;
/*    */   }
/*    */   
/*    */   protected abstract Component getDefaultName();
/*    */   
/*    */   public boolean canOpen(Player $$0) {
/* 76 */     return canUnlock($$0, this.lockKey, getDisplayName());
/*    */   }
/*    */   
/*    */   public static boolean canUnlock(Player $$0, LockCode $$1, Component $$2) {
/* 80 */     if ($$0.isSpectator() || $$1.unlocksWith($$0.getMainHandItem())) {
/* 81 */       return true;
/*    */     }
/*    */     
/* 84 */     $$0.displayClientMessage((Component)Component.translatable("container.isLocked", new Object[] { $$2 }), true);
/* 85 */     $$0.playNotifySound(SoundEvents.CHEST_LOCKED, SoundSource.BLOCKS, 1.0F, 1.0F);
/* 86 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public AbstractContainerMenu createMenu(int $$0, Inventory $$1, Player $$2) {
/* 92 */     if (canOpen($$2)) {
/* 93 */       return createMenu($$0, $$1);
/*    */     }
/*    */     
/* 96 */     return null;
/*    */   }
/*    */   
/*    */   protected abstract AbstractContainerMenu createMenu(int paramInt, Inventory paramInventory);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\BaseContainerBlockEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */