/*     */ package net.minecraft.world.entity.animal;
/*     */ 
/*     */ import java.util.Optional;
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.ItemUtils;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.Level;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface Bucketable
/*     */ {
/*     */   boolean fromBucket();
/*     */   
/*     */   void setFromBucket(boolean paramBoolean);
/*     */   
/*     */   void saveToBucketTag(ItemStack paramItemStack);
/*     */   
/*     */   void loadFromBucketTag(CompoundTag paramCompoundTag);
/*     */   
/*     */   ItemStack getBucketItemStack();
/*     */   
/*     */   SoundEvent getPickupSound();
/*     */   
/*     */   @Deprecated
/*     */   static void saveDefaultDataToBucketTag(Mob $$0, ItemStack $$1) {
/*  40 */     CompoundTag $$2 = $$1.getOrCreateTag();
/*  41 */     if ($$0.hasCustomName()) {
/*  42 */       $$1.setHoverName($$0.getCustomName());
/*     */     }
/*  44 */     if ($$0.isNoAi()) {
/*  45 */       $$2.putBoolean("NoAI", $$0.isNoAi());
/*     */     }
/*  47 */     if ($$0.isSilent()) {
/*  48 */       $$2.putBoolean("Silent", $$0.isSilent());
/*     */     }
/*  50 */     if ($$0.isNoGravity()) {
/*  51 */       $$2.putBoolean("NoGravity", $$0.isNoGravity());
/*     */     }
/*  53 */     if ($$0.hasGlowingTag()) {
/*  54 */       $$2.putBoolean("Glowing", $$0.hasGlowingTag());
/*     */     }
/*  56 */     if ($$0.isInvulnerable()) {
/*  57 */       $$2.putBoolean("Invulnerable", $$0.isInvulnerable());
/*     */     }
/*  59 */     $$2.putFloat("Health", $$0.getHealth());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   static void loadDefaultDataFromBucketTag(Mob $$0, CompoundTag $$1) {
/*  68 */     if ($$1.contains("NoAI")) {
/*  69 */       $$0.setNoAi($$1.getBoolean("NoAI"));
/*     */     }
/*  71 */     if ($$1.contains("Silent")) {
/*  72 */       $$0.setSilent($$1.getBoolean("Silent"));
/*     */     }
/*  74 */     if ($$1.contains("NoGravity")) {
/*  75 */       $$0.setNoGravity($$1.getBoolean("NoGravity"));
/*     */     }
/*  77 */     if ($$1.contains("Glowing")) {
/*  78 */       $$0.setGlowingTag($$1.getBoolean("Glowing"));
/*     */     }
/*  80 */     if ($$1.contains("Invulnerable")) {
/*  81 */       $$0.setInvulnerable($$1.getBoolean("Invulnerable"));
/*     */     }
/*  83 */     if ($$1.contains("Health", 99)) {
/*  84 */       $$0.setHealth($$1.getFloat("Health"));
/*     */     }
/*     */   }
/*     */   
/*     */   static <T extends net.minecraft.world.entity.LivingEntity & Bucketable> Optional<InteractionResult> bucketMobPickup(Player $$0, InteractionHand $$1, T $$2) {
/*  89 */     ItemStack $$3 = $$0.getItemInHand($$1);
/*     */     
/*  91 */     if ($$3.getItem() == Items.WATER_BUCKET && $$2.isAlive()) {
/*  92 */       $$2.playSound(((Bucketable)$$2).getPickupSound(), 1.0F, 1.0F);
/*     */       
/*  94 */       ItemStack $$4 = ((Bucketable)$$2).getBucketItemStack();
/*  95 */       ((Bucketable)$$2).saveToBucketTag($$4);
/*     */       
/*  97 */       ItemStack $$5 = ItemUtils.createFilledResult($$3, $$0, $$4, false);
/*  98 */       $$0.setItemInHand($$1, $$5);
/*     */       
/* 100 */       Level $$6 = $$2.level();
/*     */       
/* 102 */       if (!$$6.isClientSide) {
/* 103 */         CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer)$$0, $$4);
/*     */       }
/*     */       
/* 106 */       $$2.discard();
/*     */       
/* 108 */       return Optional.of(InteractionResult.sidedSuccess($$6.isClientSide));
/*     */     } 
/* 110 */     return Optional.empty();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\Bucketable.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */