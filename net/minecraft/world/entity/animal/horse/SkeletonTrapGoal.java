/*     */ package net.minecraft.world.entity.animal.horse;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.EquipmentSlot;
/*     */ import net.minecraft.world.entity.LightningBolt;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.SpawnGroupData;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.monster.Skeleton;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ 
/*     */ public class SkeletonTrapGoal extends Goal {
/*     */   public SkeletonTrapGoal(SkeletonHorse $$0) {
/*  23 */     this.horse = $$0;
/*     */   }
/*     */   private final SkeletonHorse horse;
/*     */   
/*     */   public boolean canUse() {
/*  28 */     return this.horse.level().hasNearbyAlivePlayer(this.horse.getX(), this.horse.getY(), this.horse.getZ(), 10.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/*  33 */     ServerLevel $$0 = (ServerLevel)this.horse.level();
/*  34 */     DifficultyInstance $$1 = $$0.getCurrentDifficultyAt(this.horse.blockPosition());
/*  35 */     this.horse.setTrap(false);
/*  36 */     this.horse.setTamed(true);
/*  37 */     this.horse.setAge(0);
/*  38 */     LightningBolt $$2 = (LightningBolt)EntityType.LIGHTNING_BOLT.create((Level)$$0);
/*  39 */     if ($$2 == null) {
/*     */       return;
/*     */     }
/*  42 */     $$2.moveTo(this.horse.getX(), this.horse.getY(), this.horse.getZ());
/*  43 */     $$2.setVisualOnly(true);
/*  44 */     $$0.addFreshEntity((Entity)$$2);
/*  45 */     Skeleton $$3 = createSkeleton($$1, this.horse);
/*  46 */     if ($$3 == null) {
/*     */       return;
/*     */     }
/*  49 */     $$3.startRiding((Entity)this.horse);
/*     */     
/*  51 */     $$0.addFreshEntityWithPassengers((Entity)$$3);
/*     */     
/*  53 */     for (int $$4 = 0; $$4 < 3; $$4++) {
/*  54 */       AbstractHorse $$5 = createHorse($$1);
/*  55 */       if ($$5 != null) {
/*     */ 
/*     */         
/*  58 */         Skeleton $$6 = createSkeleton($$1, $$5);
/*  59 */         if ($$6 != null) {
/*     */ 
/*     */           
/*  62 */           $$6.startRiding((Entity)$$5);
/*  63 */           $$5.push(this.horse.getRandom().triangle(0.0D, 1.1485D), 0.0D, this.horse.getRandom().triangle(0.0D, 1.1485D));
/*  64 */           $$0.addFreshEntityWithPassengers((Entity)$$5);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   } @Nullable
/*     */   private AbstractHorse createHorse(DifficultyInstance $$0) {
/*  70 */     SkeletonHorse $$1 = (SkeletonHorse)EntityType.SKELETON_HORSE.create(this.horse.level());
/*  71 */     if ($$1 != null) {
/*  72 */       $$1.finalizeSpawn((ServerLevelAccessor)this.horse.level(), $$0, MobSpawnType.TRIGGERED, (SpawnGroupData)null, (CompoundTag)null);
/*  73 */       $$1.setPos(this.horse.getX(), this.horse.getY(), this.horse.getZ());
/*  74 */       $$1.invulnerableTime = 60;
/*  75 */       $$1.setPersistenceRequired();
/*  76 */       $$1.setTamed(true);
/*  77 */       $$1.setAge(0);
/*     */     } 
/*  79 */     return $$1;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private Skeleton createSkeleton(DifficultyInstance $$0, AbstractHorse $$1) {
/*  84 */     Skeleton $$2 = (Skeleton)EntityType.SKELETON.create($$1.level());
/*  85 */     if ($$2 != null) {
/*  86 */       $$2.finalizeSpawn((ServerLevelAccessor)$$1.level(), $$0, MobSpawnType.TRIGGERED, null, null);
/*  87 */       $$2.setPos($$1.getX(), $$1.getY(), $$1.getZ());
/*  88 */       $$2.invulnerableTime = 60;
/*  89 */       $$2.setPersistenceRequired();
/*     */       
/*  91 */       if ($$2.getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
/*  92 */         $$2.setItemSlot(EquipmentSlot.HEAD, new ItemStack((ItemLike)Items.IRON_HELMET));
/*     */       }
/*     */       
/*  95 */       $$2.setItemSlot(EquipmentSlot.MAINHAND, EnchantmentHelper.enchantItem($$2.getRandom(), disenchant($$2.getMainHandItem()), (int)(5.0F + $$0.getSpecialMultiplier() * $$2.getRandom().nextInt(18)), false));
/*  96 */       $$2.setItemSlot(EquipmentSlot.HEAD, EnchantmentHelper.enchantItem($$2.getRandom(), disenchant($$2.getItemBySlot(EquipmentSlot.HEAD)), (int)(5.0F + $$0.getSpecialMultiplier() * $$2.getRandom().nextInt(18)), false));
/*     */     } 
/*  98 */     return $$2;
/*     */   }
/*     */   
/*     */   private ItemStack disenchant(ItemStack $$0) {
/* 102 */     $$0.removeTagKey("Enchantments");
/* 103 */     return $$0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\horse\SkeletonTrapGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */