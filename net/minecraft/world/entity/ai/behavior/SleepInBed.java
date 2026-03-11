/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Optional;
/*    */ import java.util.Set;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.GlobalPos;
/*    */ import net.minecraft.core.Position;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.tags.BlockTags;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.Brain;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryStatus;
/*    */ import net.minecraft.world.entity.schedule.Activity;
/*    */ import net.minecraft.world.level.block.BedBlock;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ 
/*    */ public class SleepInBed extends Behavior<LivingEntity> {
/*    */   public static final int COOLDOWN_AFTER_BEING_WOKEN = 100;
/*    */   
/*    */   public SleepInBed() {
/* 26 */     super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.HOME, MemoryStatus.VALUE_PRESENT, MemoryModuleType.LAST_WOKEN, MemoryStatus.REGISTERED));
/*    */   }
/*    */ 
/*    */   
/*    */   private long nextOkStartTime;
/*    */ 
/*    */   
/*    */   protected boolean checkExtraStartConditions(ServerLevel $$0, LivingEntity $$1) {
/* 34 */     if ($$1.isPassenger()) {
/* 35 */       return false;
/*    */     }
/* 37 */     Brain<?> $$2 = $$1.getBrain();
/*    */     
/* 39 */     GlobalPos $$3 = $$2.getMemory(MemoryModuleType.HOME).get();
/* 40 */     if ($$0.dimension() != $$3.dimension()) {
/* 41 */       return false;
/*    */     }
/*    */     
/* 44 */     Optional<Long> $$4 = $$2.getMemory(MemoryModuleType.LAST_WOKEN);
/* 45 */     if ($$4.isPresent()) {
/* 46 */       long $$5 = $$0.getGameTime() - ((Long)$$4.get()).longValue();
/* 47 */       if ($$5 > 0L && $$5 < 100L)
/*    */       {
/* 49 */         return false;
/*    */       }
/*    */     } 
/*    */     
/* 53 */     BlockState $$6 = $$0.getBlockState($$3.pos());
/* 54 */     return ($$3.pos().closerToCenterThan((Position)$$1.position(), 2.0D) && $$6.is(BlockTags.BEDS) && !((Boolean)$$6.getValue((Property)BedBlock.OCCUPIED)).booleanValue());
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean canStillUse(ServerLevel $$0, LivingEntity $$1, long $$2) {
/* 59 */     Optional<GlobalPos> $$3 = $$1.getBrain().getMemory(MemoryModuleType.HOME);
/*    */     
/* 61 */     if ($$3.isEmpty()) {
/* 62 */       return false;
/*    */     }
/*    */     
/* 65 */     BlockPos $$4 = ((GlobalPos)$$3.get()).pos();
/* 66 */     return ($$1.getBrain().isActive(Activity.REST) && $$1.getY() > $$4.getY() + 0.4D && $$4.closerToCenterThan((Position)$$1.position(), 1.14D));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void start(ServerLevel $$0, LivingEntity $$1, long $$2) {
/* 71 */     if ($$2 > this.nextOkStartTime) {
/* 72 */       Brain<?> $$3 = $$1.getBrain();
/*    */       
/* 74 */       if ($$3.hasMemoryValue(MemoryModuleType.DOORS_TO_CLOSE)) {
/* 75 */         Optional<List<LivingEntity>> $$6; Set<GlobalPos> $$4 = $$3.getMemory(MemoryModuleType.DOORS_TO_CLOSE).get();
/*    */         
/* 77 */         if ($$3.hasMemoryValue(MemoryModuleType.NEAREST_LIVING_ENTITIES)) {
/* 78 */           Optional<List<LivingEntity>> $$5 = $$3.getMemory(MemoryModuleType.NEAREST_LIVING_ENTITIES);
/*    */         } else {
/* 80 */           $$6 = Optional.empty();
/*    */         } 
/*    */         
/* 83 */         InteractWithDoor.closeDoorsThatIHaveOpenedOrPassedThrough($$0, $$1, null, null, $$4, $$6);
/*    */       } 
/* 85 */       $$1.startSleeping(((GlobalPos)$$1.getBrain().getMemory(MemoryModuleType.HOME).get()).pos());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean timedOut(long $$0) {
/* 91 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void stop(ServerLevel $$0, LivingEntity $$1, long $$2) {
/* 96 */     if ($$1.isSleeping()) {
/* 97 */       $$1.stopSleeping();
/* 98 */       this.nextOkStartTime = $$2 + 40L;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\SleepInBed.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */