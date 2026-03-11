/*    */ package net.minecraft.world.entity.schedule;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Comparator;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.stream.Collectors;
/*    */ import net.minecraft.core.Registry;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ 
/*    */ 
/*    */ public class Schedule
/*    */ {
/*    */   public static final int WORK_START_TIME = 2000;
/*    */   public static final int TOTAL_WORK_TIME = 7000;
/* 16 */   public static final Schedule EMPTY = register("empty")
/* 17 */     .changeActivityAt(0, Activity.IDLE)
/* 18 */     .build();
/* 19 */   public static final Schedule SIMPLE = register("simple")
/* 20 */     .changeActivityAt(5000, Activity.WORK)
/* 21 */     .changeActivityAt(11000, Activity.REST)
/* 22 */     .build();
/* 23 */   public static final Schedule VILLAGER_BABY = register("villager_baby")
/* 24 */     .changeActivityAt(10, Activity.IDLE)
/* 25 */     .changeActivityAt(3000, Activity.PLAY)
/* 26 */     .changeActivityAt(6000, Activity.IDLE)
/* 27 */     .changeActivityAt(10000, Activity.PLAY)
/* 28 */     .changeActivityAt(12000, Activity.REST)
/* 29 */     .build();
/* 30 */   public static final Schedule VILLAGER_DEFAULT = register("villager_default")
/* 31 */     .changeActivityAt(10, Activity.IDLE)
/* 32 */     .changeActivityAt(2000, Activity.WORK)
/* 33 */     .changeActivityAt(9000, Activity.MEET)
/* 34 */     .changeActivityAt(11000, Activity.IDLE)
/* 35 */     .changeActivityAt(12000, Activity.REST)
/* 36 */     .build();
/* 37 */   private final Map<Activity, Timeline> timelines = Maps.newHashMap();
/*    */   
/*    */   protected static ScheduleBuilder register(String $$0) {
/* 40 */     Schedule $$1 = (Schedule)Registry.register(BuiltInRegistries.SCHEDULE, $$0, new Schedule());
/* 41 */     return new ScheduleBuilder($$1);
/*    */   }
/*    */   
/*    */   protected void ensureTimelineExistsFor(Activity $$0) {
/* 45 */     if (!this.timelines.containsKey($$0)) {
/* 46 */       this.timelines.put($$0, new Timeline());
/*    */     }
/*    */   }
/*    */   
/*    */   protected Timeline getTimelineFor(Activity $$0) {
/* 51 */     return this.timelines.get($$0);
/*    */   }
/*    */   
/*    */   protected List<Timeline> getAllTimelinesExceptFor(Activity $$0) {
/* 55 */     return (List<Timeline>)this.timelines.entrySet()
/* 56 */       .stream()
/* 57 */       .filter($$1 -> ($$1.getKey() != $$0))
/* 58 */       .map(Map.Entry::getValue)
/* 59 */       .collect(Collectors.toList());
/*    */   }
/*    */   
/*    */   public Activity getActivityAt(int $$0) {
/* 63 */     return this.timelines.entrySet()
/* 64 */       .stream()
/* 65 */       .max(Comparator.comparingDouble($$1 -> ((Timeline)$$1.getValue()).getValueAt($$0)))
/* 66 */       .map(Map.Entry::getKey)
/* 67 */       .orElse(Activity.IDLE);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\schedule\Schedule.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */