/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.nbt.ListTag;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryStatus;
/*    */ import net.minecraft.world.entity.npc.Villager;
/*    */ import net.minecraft.world.entity.projectile.FireworkRocketEntity;
/*    */ import net.minecraft.world.entity.raid.Raid;
/*    */ import net.minecraft.world.item.DyeColor;
/*    */ import net.minecraft.world.item.FireworkRocketItem;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.Items;
/*    */ import net.minecraft.world.level.ItemLike;
/*    */ 
/*    */ public class CelebrateVillagersSurvivedRaid extends Behavior<Villager> {
/*    */   public CelebrateVillagersSurvivedRaid(int $$0, int $$1) {
/* 27 */     super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(), $$0, $$1);
/*    */   }
/*    */   @Nullable
/*    */   private Raid currentRaid;
/*    */   protected boolean checkExtraStartConditions(ServerLevel $$0, Villager $$1) {
/* 32 */     BlockPos $$2 = $$1.blockPosition();
/* 33 */     this.currentRaid = $$0.getRaidAt($$2);
/* 34 */     return (this.currentRaid != null && this.currentRaid.isVictory() && MoveToSkySeeingSpot.hasNoBlocksAbove($$0, (LivingEntity)$$1, $$2));
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean canStillUse(ServerLevel $$0, Villager $$1, long $$2) {
/* 39 */     return (this.currentRaid != null && !this.currentRaid.isStopped());
/*    */   }
/*    */ 
/*    */   
/*    */   protected void stop(ServerLevel $$0, Villager $$1, long $$2) {
/* 44 */     this.currentRaid = null;
/* 45 */     $$1.getBrain().updateActivityFromSchedule($$0.getDayTime(), $$0.getGameTime());
/*    */   }
/*    */ 
/*    */   
/*    */   protected void tick(ServerLevel $$0, Villager $$1, long $$2) {
/* 50 */     RandomSource $$3 = $$1.getRandom();
/*    */     
/* 52 */     if ($$3.nextInt(100) == 0) {
/* 53 */       $$1.playCelebrateSound();
/*    */     }
/*    */     
/* 56 */     if ($$3.nextInt(200) == 0 && MoveToSkySeeingSpot.hasNoBlocksAbove($$0, (LivingEntity)$$1, $$1.blockPosition())) {
/* 57 */       DyeColor $$4 = (DyeColor)Util.getRandom((Object[])DyeColor.values(), $$3);
/* 58 */       int $$5 = $$3.nextInt(3);
/* 59 */       ItemStack $$6 = getFirework($$4, $$5);
/*    */       
/* 61 */       FireworkRocketEntity $$7 = new FireworkRocketEntity($$1.level(), (Entity)$$1, $$1.getX(), $$1.getEyeY(), $$1.getZ(), $$6);
/* 62 */       $$1.level().addFreshEntity((Entity)$$7);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private ItemStack getFirework(DyeColor $$0, int $$1) {
/* 68 */     ItemStack $$2 = new ItemStack((ItemLike)Items.FIREWORK_ROCKET, 1);
/*    */     
/* 70 */     ItemStack $$3 = new ItemStack((ItemLike)Items.FIREWORK_STAR);
/* 71 */     CompoundTag $$4 = $$3.getOrCreateTagElement("Explosion");
/*    */     
/* 73 */     List<Integer> $$5 = Lists.newArrayList();
/* 74 */     $$5.add(Integer.valueOf($$0.getFireworkColor()));
/*    */     
/* 76 */     $$4.putIntArray("Colors", $$5);
/* 77 */     $$4.putByte("Type", (byte)FireworkRocketItem.Shape.BURST.getId());
/*    */     
/* 79 */     CompoundTag $$6 = $$2.getOrCreateTagElement("Fireworks");
/* 80 */     ListTag $$7 = new ListTag();
/*    */     
/* 82 */     CompoundTag $$8 = $$3.getTagElement("Explosion");
/* 83 */     if ($$8 != null) {
/* 84 */       $$7.add($$8);
/*    */     }
/*    */     
/* 87 */     $$6.putByte("Flight", (byte)$$1);
/* 88 */     if (!$$7.isEmpty()) {
/* 89 */       $$6.put("Explosions", (Tag)$$7);
/*    */     }
/*    */     
/* 92 */     return $$2;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\CelebrateVillagersSurvivedRaid.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */