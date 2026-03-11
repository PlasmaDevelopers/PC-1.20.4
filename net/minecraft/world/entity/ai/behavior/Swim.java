/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.tags.FluidTags;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryStatus;
/*    */ 
/*    */ public class Swim
/*    */   extends Behavior<Mob> {
/*    */   public Swim(float $$0) {
/* 15 */     super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of());
/* 16 */     this.chance = $$0;
/*    */   }
/*    */   private final float chance;
/*    */   
/*    */   protected boolean checkExtraStartConditions(ServerLevel $$0, Mob $$1) {
/* 21 */     return (($$1.isInWater() && $$1.getFluidHeight(FluidTags.WATER) > $$1.getFluidJumpThreshold()) || $$1.isInLava());
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean canStillUse(ServerLevel $$0, Mob $$1, long $$2) {
/* 26 */     return checkExtraStartConditions($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void tick(ServerLevel $$0, Mob $$1, long $$2) {
/* 31 */     if ($$1.getRandom().nextFloat() < this.chance)
/* 32 */       $$1.getJumpControl().jump(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\Swim.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */