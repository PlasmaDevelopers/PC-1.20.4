/*     */ package net.minecraft.world.entity.ai.behavior;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.world.SimpleContainer;
/*     */ import net.minecraft.world.entity.EquipmentSlot;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryStatus;
/*     */ import net.minecraft.world.entity.ai.memory.WalkTarget;
/*     */ import net.minecraft.world.entity.npc.Villager;
/*     */ import net.minecraft.world.item.BoneMealItem;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.CropBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ 
/*     */ public class UseBonemeal
/*     */   extends Behavior<Villager> {
/*     */   private static final int BONEMEALING_DURATION = 80;
/*     */   private long nextWorkCycleTime;
/*  30 */   private Optional<BlockPos> cropPos = Optional.empty(); private long lastBonemealingSession; private int timeWorkedSoFar;
/*     */   
/*     */   public UseBonemeal() {
/*  33 */     super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.LOOK_TARGET, MemoryStatus.VALUE_ABSENT, MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean checkExtraStartConditions(ServerLevel $$0, Villager $$1) {
/*  41 */     if ($$1.tickCount % 10 != 0 || (this.lastBonemealingSession != 0L && this.lastBonemealingSession + 160L > $$1.tickCount)) {
/*  42 */       return false;
/*     */     }
/*     */     
/*  45 */     if ($$1.getInventory().countItem(Items.BONE_MEAL) <= 0) {
/*  46 */       return false;
/*     */     }
/*  48 */     this.cropPos = pickNextTarget($$0, $$1);
/*  49 */     return this.cropPos.isPresent();
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canStillUse(ServerLevel $$0, Villager $$1, long $$2) {
/*  54 */     return (this.timeWorkedSoFar < 80 && this.cropPos.isPresent());
/*     */   }
/*     */   
/*     */   private Optional<BlockPos> pickNextTarget(ServerLevel $$0, Villager $$1) {
/*  58 */     BlockPos.MutableBlockPos $$2 = new BlockPos.MutableBlockPos();
/*  59 */     Optional<BlockPos> $$3 = Optional.empty();
/*  60 */     int $$4 = 0;
/*  61 */     for (int $$5 = -1; $$5 <= 1; $$5++) {
/*  62 */       for (int $$6 = -1; $$6 <= 1; $$6++) {
/*  63 */         for (int $$7 = -1; $$7 <= 1; $$7++) {
/*  64 */           $$2.setWithOffset((Vec3i)$$1.blockPosition(), $$5, $$6, $$7);
/*  65 */           if (validPos((BlockPos)$$2, $$0) && 
/*  66 */             $$0.random.nextInt(++$$4) == 0) {
/*  67 */             $$3 = Optional.of($$2.immutable());
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  74 */     return $$3;
/*     */   }
/*     */   
/*     */   private boolean validPos(BlockPos $$0, ServerLevel $$1) {
/*  78 */     BlockState $$2 = $$1.getBlockState($$0);
/*  79 */     Block $$3 = $$2.getBlock();
/*  80 */     return ($$3 instanceof CropBlock && !((CropBlock)$$3).isMaxAge($$2));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void start(ServerLevel $$0, Villager $$1, long $$2) {
/*  85 */     setCurrentCropAsTarget($$1);
/*     */     
/*  87 */     $$1.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)Items.BONE_MEAL));
/*     */     
/*  89 */     this.nextWorkCycleTime = $$2;
/*  90 */     this.timeWorkedSoFar = 0;
/*     */   }
/*     */   
/*     */   private void setCurrentCropAsTarget(Villager $$0) {
/*  94 */     this.cropPos.ifPresent($$1 -> {
/*     */           BlockPosTracker $$2 = new BlockPosTracker($$1);
/*     */           $$0.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, $$2);
/*     */           $$0.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget($$2, 0.5F, 1));
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   protected void stop(ServerLevel $$0, Villager $$1, long $$2) {
/* 103 */     $$1.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
/* 104 */     this.lastBonemealingSession = $$1.tickCount;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void tick(ServerLevel $$0, Villager $$1, long $$2) {
/* 109 */     BlockPos $$3 = this.cropPos.get();
/* 110 */     if ($$2 < this.nextWorkCycleTime || !$$3.closerToCenterThan((Position)$$1.position(), 1.0D)) {
/*     */       return;
/*     */     }
/*     */     
/* 114 */     ItemStack $$4 = ItemStack.EMPTY;
/* 115 */     SimpleContainer $$5 = $$1.getInventory();
/* 116 */     int $$6 = $$5.getContainerSize();
/* 117 */     for (int $$7 = 0; $$7 < $$6; $$7++) {
/* 118 */       ItemStack $$8 = $$5.getItem($$7);
/* 119 */       if ($$8.is(Items.BONE_MEAL)) {
/* 120 */         $$4 = $$8;
/*     */         break;
/*     */       } 
/*     */     } 
/* 124 */     if (!$$4.isEmpty() && BoneMealItem.growCrop($$4, (Level)$$0, $$3)) {
/* 125 */       $$0.levelEvent(1505, $$3, 0);
/*     */       
/* 127 */       this.cropPos = pickNextTarget($$0, $$1);
/* 128 */       setCurrentCropAsTarget($$1);
/* 129 */       this.nextWorkCycleTime = $$2 + 40L;
/*     */     } 
/*     */     
/* 132 */     this.timeWorkedSoFar++;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\UseBonemeal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */