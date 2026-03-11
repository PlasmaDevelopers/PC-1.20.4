/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import java.util.Optional;
/*    */ import java.util.function.Predicate;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.util.valueproviders.UniformInt;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
/*    */ 
/*    */ @Deprecated
/*    */ public class SetEntityLookTargetSometimes {
/*    */   public static BehaviorControl<LivingEntity> create(float $$0, UniformInt $$1) {
/* 20 */     return create($$0, $$1, $$0 -> true);
/*    */   }
/*    */   
/*    */   public static BehaviorControl<LivingEntity> create(EntityType<?> $$0, float $$1, UniformInt $$2) {
/* 24 */     return create($$1, $$2, $$1 -> $$0.equals($$1.getType()));
/*    */   }
/*    */   
/*    */   private static BehaviorControl<LivingEntity> create(float $$0, UniformInt $$1, Predicate<LivingEntity> $$2) {
/* 28 */     float $$3 = $$0 * $$0;
/*    */     
/* 30 */     Ticker $$4 = new Ticker($$1);
/*    */     
/* 32 */     return BehaviorBuilder.create($$3 -> $$3.group((App)$$3.absent(MemoryModuleType.LOOK_TARGET), (App)$$3.present(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES)).apply((Applicative)$$3, ()));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static final class Ticker
/*    */   {
/*    */     private final UniformInt interval;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     private int ticksUntilNextStart;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public Ticker(UniformInt $$0) {
/* 56 */       if ($$0.getMinValue() <= 1) {
/* 57 */         throw new IllegalArgumentException();
/*    */       }
/* 59 */       this.interval = $$0;
/*    */     }
/*    */     
/*    */     public boolean tickDownAndCheck(RandomSource $$0) {
/* 63 */       if (this.ticksUntilNextStart == 0) {
/* 64 */         this.ticksUntilNextStart = this.interval.sample($$0) - 1;
/* 65 */         return false;
/*    */       } 
/*    */       
/* 68 */       return (--this.ticksUntilNextStart == 0);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\SetEntityLookTargetSometimes.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */