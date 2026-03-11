/*    */ package net.minecraft.world.entity.ai.memory;
/*    */ 
/*    */ import com.google.common.collect.Iterables;
/*    */ import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import java.util.function.Predicate;
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.sensing.Sensor;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NearestVisibleLivingEntities
/*    */ {
/* 21 */   private static final NearestVisibleLivingEntities EMPTY = new NearestVisibleLivingEntities();
/*    */   private final List<LivingEntity> nearbyEntities;
/*    */   private final Predicate<LivingEntity> lineOfSightTest;
/*    */   
/*    */   private NearestVisibleLivingEntities() {
/* 26 */     this.nearbyEntities = List.of();
/* 27 */     this.lineOfSightTest = ($$0 -> false);
/*    */   }
/*    */   
/*    */   public NearestVisibleLivingEntities(LivingEntity $$0, List<LivingEntity> $$1) {
/* 31 */     this.nearbyEntities = $$1;
/* 32 */     Object2BooleanOpenHashMap<LivingEntity> $$2 = new Object2BooleanOpenHashMap($$1.size());
/* 33 */     Predicate<LivingEntity> $$3 = $$1 -> Sensor.isEntityTargetable($$0, $$1);
/* 34 */     this.lineOfSightTest = ($$2 -> $$0.computeIfAbsent($$2, $$1));
/*    */   }
/*    */   
/*    */   public static NearestVisibleLivingEntities empty() {
/* 38 */     return EMPTY;
/*    */   }
/*    */   
/*    */   public Optional<LivingEntity> findClosest(Predicate<LivingEntity> $$0) {
/* 42 */     for (LivingEntity $$1 : this.nearbyEntities) {
/* 43 */       if ($$0.test($$1) && this.lineOfSightTest.test($$1)) {
/* 44 */         return Optional.of($$1);
/*    */       }
/*    */     } 
/* 47 */     return Optional.empty();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Iterable<LivingEntity> findAll(Predicate<LivingEntity> $$0) {
/* 56 */     return Iterables.filter(this.nearbyEntities, $$1 -> ($$0.test($$1) && this.lineOfSightTest.test($$1)));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Stream<LivingEntity> find(Predicate<LivingEntity> $$0) {
/* 66 */     return this.nearbyEntities.stream()
/* 67 */       .filter($$1 -> ($$0.test($$1) && this.lineOfSightTest.test($$1)));
/*    */   }
/*    */   
/*    */   public boolean contains(LivingEntity $$0) {
/* 71 */     return (this.nearbyEntities.contains($$0) && this.lineOfSightTest.test($$0));
/*    */   }
/*    */   
/*    */   public boolean contains(Predicate<LivingEntity> $$0) {
/* 75 */     for (LivingEntity $$1 : this.nearbyEntities) {
/* 76 */       if ($$0.test($$1) && this.lineOfSightTest.test($$1)) {
/* 77 */         return true;
/*    */       }
/*    */     } 
/* 80 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\memory\NearestVisibleLivingEntities.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */