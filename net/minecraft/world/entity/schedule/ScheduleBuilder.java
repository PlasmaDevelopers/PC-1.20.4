/*    */ package net.minecraft.world.entity.schedule;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ import java.util.Objects;
/*    */ import java.util.Set;
/*    */ import java.util.stream.Collectors;
/*    */ 
/*    */ public class ScheduleBuilder {
/* 10 */   private final List<ActivityTransition> transitions = Lists.newArrayList(); private final Schedule schedule;
/*    */   
/*    */   public ScheduleBuilder(Schedule $$0) {
/* 13 */     this.schedule = $$0;
/*    */   }
/*    */   
/*    */   public ScheduleBuilder changeActivityAt(int $$0, Activity $$1) {
/* 17 */     this.transitions.add(new ActivityTransition($$0, $$1));
/* 18 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Schedule build() {
/* 25 */     Objects.requireNonNull(this.schedule); ((Set)this.transitions.stream().map(ActivityTransition::getActivity).collect(Collectors.toSet())).forEach(this.schedule::ensureTimelineExistsFor);
/*    */     
/* 27 */     this.transitions.forEach($$0 -> {
/*    */           Activity $$1 = $$0.getActivity();
/*    */ 
/*    */ 
/*    */           
/*    */           this.schedule.getAllTimelinesExceptFor($$1).forEach(());
/*    */ 
/*    */           
/*    */           this.schedule.getTimelineFor($$1).addKeyframe($$0.getTime(), 1.0F);
/*    */         });
/*    */ 
/*    */     
/* 39 */     return this.schedule;
/*    */   }
/*    */   
/*    */   private static class ActivityTransition {
/*    */     private final int time;
/*    */     private final Activity activity;
/*    */     
/*    */     public ActivityTransition(int $$0, Activity $$1) {
/* 47 */       this.time = $$0;
/* 48 */       this.activity = $$1;
/*    */     }
/*    */     
/*    */     public int getTime() {
/* 52 */       return this.time;
/*    */     }
/*    */     
/*    */     public Activity getActivity() {
/* 56 */       return this.activity;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\schedule\ScheduleBuilder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */