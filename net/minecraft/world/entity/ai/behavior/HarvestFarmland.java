/*     */ package net.minecraft.world.entity.ai.behavior;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.tags.ItemTags;
/*     */ import net.minecraft.world.SimpleContainer;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryStatus;
/*     */ import net.minecraft.world.entity.ai.memory.WalkTarget;
/*     */ import net.minecraft.world.entity.npc.Villager;
/*     */ import net.minecraft.world.entity.npc.VillagerProfession;
/*     */ import net.minecraft.world.item.BlockItem;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.CropBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ 
/*     */ 
/*     */ public class HarvestFarmland
/*     */   extends Behavior<Villager>
/*     */ {
/*     */   private static final int HARVEST_DURATION = 200;
/*     */   public static final float SPEED_MODIFIER = 0.5F;
/*  38 */   private final List<BlockPos> validFarmlandAroundVillager = Lists.newArrayList(); @Nullable
/*     */   private BlockPos aboveFarmlandPos; private long nextOkStartTime; private int timeWorkedSoFar;
/*     */   public HarvestFarmland() {
/*  41 */     super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.LOOK_TARGET, MemoryStatus.VALUE_ABSENT, MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT, MemoryModuleType.SECONDARY_JOB_SITE, MemoryStatus.VALUE_PRESENT));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean checkExtraStartConditions(ServerLevel $$0, Villager $$1) {
/*  50 */     if (!$$0.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
/*  51 */       return false;
/*     */     }
/*     */     
/*  54 */     if ($$1.getVillagerData().getProfession() != VillagerProfession.FARMER) {
/*  55 */       return false;
/*     */     }
/*     */     
/*  58 */     BlockPos.MutableBlockPos $$2 = $$1.blockPosition().mutable();
/*     */     
/*  60 */     this.validFarmlandAroundVillager.clear();
/*  61 */     for (int $$3 = -1; $$3 <= 1; $$3++) {
/*  62 */       for (int $$4 = -1; $$4 <= 1; $$4++) {
/*  63 */         for (int $$5 = -1; $$5 <= 1; $$5++) {
/*  64 */           $$2.set($$1.getX() + $$3, $$1.getY() + $$4, $$1.getZ() + $$5);
/*  65 */           if (validPos((BlockPos)$$2, $$0)) {
/*  66 */             this.validFarmlandAroundVillager.add(new BlockPos((Vec3i)$$2));
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  72 */     this.aboveFarmlandPos = getValidFarmland($$0);
/*  73 */     return (this.aboveFarmlandPos != null);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private BlockPos getValidFarmland(ServerLevel $$0) {
/*  78 */     return this.validFarmlandAroundVillager.isEmpty() ? null : this.validFarmlandAroundVillager.get($$0.getRandom().nextInt(this.validFarmlandAroundVillager.size()));
/*     */   }
/*     */   
/*     */   private boolean validPos(BlockPos $$0, ServerLevel $$1) {
/*  82 */     BlockState $$2 = $$1.getBlockState($$0);
/*  83 */     Block $$3 = $$2.getBlock();
/*  84 */     Block $$4 = $$1.getBlockState($$0.below()).getBlock();
/*  85 */     return (($$3 instanceof CropBlock && ((CropBlock)$$3).isMaxAge($$2)) || ($$2
/*  86 */       .isAir() && $$4 instanceof net.minecraft.world.level.block.FarmBlock));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void start(ServerLevel $$0, Villager $$1, long $$2) {
/*  91 */     if ($$2 > this.nextOkStartTime && this.aboveFarmlandPos != null) {
/*  92 */       $$1.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new BlockPosTracker(this.aboveFarmlandPos));
/*  93 */       $$1.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(new BlockPosTracker(this.aboveFarmlandPos), 0.5F, 1));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void stop(ServerLevel $$0, Villager $$1, long $$2) {
/*  99 */     $$1.getBrain().eraseMemory(MemoryModuleType.LOOK_TARGET);
/* 100 */     $$1.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
/* 101 */     this.timeWorkedSoFar = 0;
/* 102 */     this.nextOkStartTime = $$2 + 40L;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void tick(ServerLevel $$0, Villager $$1, long $$2) {
/* 107 */     if (this.aboveFarmlandPos != null && !this.aboveFarmlandPos.closerToCenterThan((Position)$$1.position(), 1.0D)) {
/*     */       return;
/*     */     }
/*     */     
/* 111 */     if (this.aboveFarmlandPos != null && $$2 > this.nextOkStartTime) {
/* 112 */       BlockState $$3 = $$0.getBlockState(this.aboveFarmlandPos);
/* 113 */       Block $$4 = $$3.getBlock();
/* 114 */       Block $$5 = $$0.getBlockState(this.aboveFarmlandPos.below()).getBlock();
/*     */       
/* 116 */       if ($$4 instanceof CropBlock && ((CropBlock)$$4).isMaxAge($$3)) {
/* 117 */         $$0.destroyBlock(this.aboveFarmlandPos, true, (Entity)$$1);
/*     */       }
/*     */       
/* 120 */       if ($$3.isAir() && $$5 instanceof net.minecraft.world.level.block.FarmBlock && $$1.hasFarmSeeds()) {
/* 121 */         SimpleContainer $$6 = $$1.getInventory();
/* 122 */         for (int $$7 = 0; $$7 < $$6.getContainerSize(); $$7++) {
/* 123 */           ItemStack $$8 = $$6.getItem($$7);
/* 124 */           boolean $$9 = false;
/* 125 */           if (!$$8.isEmpty() && $$8.is(ItemTags.VILLAGER_PLANTABLE_SEEDS)) {
/* 126 */             Item item = $$8.getItem(); if (item instanceof BlockItem) { BlockItem $$10 = (BlockItem)item;
/* 127 */               BlockState $$11 = $$10.getBlock().defaultBlockState();
/* 128 */               $$0.setBlockAndUpdate(this.aboveFarmlandPos, $$11);
/* 129 */               $$0.gameEvent(GameEvent.BLOCK_PLACE, this.aboveFarmlandPos, GameEvent.Context.of((Entity)$$1, $$11));
/* 130 */               $$9 = true; }
/*     */           
/*     */           } 
/* 133 */           if ($$9) {
/* 134 */             $$0.playSound(null, this.aboveFarmlandPos.getX(), this.aboveFarmlandPos.getY(), this.aboveFarmlandPos.getZ(), SoundEvents.CROP_PLANTED, SoundSource.BLOCKS, 1.0F, 1.0F);
/* 135 */             $$8.shrink(1);
/* 136 */             if ($$8.isEmpty()) {
/* 137 */               $$6.setItem($$7, ItemStack.EMPTY);
/*     */             }
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/* 144 */       if ($$4 instanceof CropBlock && !((CropBlock)$$4).isMaxAge($$3)) {
/* 145 */         this.validFarmlandAroundVillager.remove(this.aboveFarmlandPos);
/*     */         
/* 147 */         this.aboveFarmlandPos = getValidFarmland($$0);
/* 148 */         if (this.aboveFarmlandPos != null) {
/* 149 */           this.nextOkStartTime = $$2 + 20L;
/* 150 */           $$1.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(new BlockPosTracker(this.aboveFarmlandPos), 0.5F, 1));
/* 151 */           $$1.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new BlockPosTracker(this.aboveFarmlandPos));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 156 */     this.timeWorkedSoFar++;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canStillUse(ServerLevel $$0, Villager $$1, long $$2) {
/* 161 */     return (this.timeWorkedSoFar < 200);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\HarvestFarmland.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */