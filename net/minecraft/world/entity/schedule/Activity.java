/*    */ package net.minecraft.world.entity.schedule;
/*    */ 
/*    */ import net.minecraft.core.Registry;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ 
/*    */ public class Activity {
/*  7 */   public static final Activity CORE = register("core");
/*  8 */   public static final Activity IDLE = register("idle");
/*  9 */   public static final Activity WORK = register("work");
/* 10 */   public static final Activity PLAY = register("play");
/* 11 */   public static final Activity REST = register("rest");
/* 12 */   public static final Activity MEET = register("meet");
/* 13 */   public static final Activity PANIC = register("panic");
/* 14 */   public static final Activity RAID = register("raid");
/* 15 */   public static final Activity PRE_RAID = register("pre_raid");
/* 16 */   public static final Activity HIDE = register("hide");
/* 17 */   public static final Activity FIGHT = register("fight");
/* 18 */   public static final Activity CELEBRATE = register("celebrate");
/* 19 */   public static final Activity ADMIRE_ITEM = register("admire_item");
/* 20 */   public static final Activity AVOID = register("avoid");
/* 21 */   public static final Activity RIDE = register("ride");
/* 22 */   public static final Activity PLAY_DEAD = register("play_dead");
/* 23 */   public static final Activity LONG_JUMP = register("long_jump");
/* 24 */   public static final Activity RAM = register("ram");
/* 25 */   public static final Activity TONGUE = register("tongue");
/* 26 */   public static final Activity SWIM = register("swim");
/* 27 */   public static final Activity LAY_SPAWN = register("lay_spawn");
/* 28 */   public static final Activity SNIFF = register("sniff");
/* 29 */   public static final Activity INVESTIGATE = register("investigate");
/* 30 */   public static final Activity ROAR = register("roar");
/* 31 */   public static final Activity EMERGE = register("emerge");
/* 32 */   public static final Activity DIG = register("dig");
/*    */   
/*    */   private final String name;
/*    */   private final int hashCode;
/*    */   
/*    */   private Activity(String $$0) {
/* 38 */     this.name = $$0;
/* 39 */     this.hashCode = $$0.hashCode();
/*    */   }
/*    */   
/*    */   public String getName() {
/* 43 */     return this.name;
/*    */   }
/*    */   
/*    */   private static Activity register(String $$0) {
/* 47 */     return (Activity)Registry.register(BuiltInRegistries.ACTIVITY, $$0, new Activity($$0));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object $$0) {
/* 52 */     if (this == $$0) {
/* 53 */       return true;
/*    */     }
/* 55 */     if ($$0 == null || getClass() != $$0.getClass()) {
/* 56 */       return false;
/*    */     }
/*    */     
/* 59 */     Activity $$1 = (Activity)$$0;
/*    */     
/* 61 */     return this.name.equals($$1.name);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 66 */     return this.hashCode;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 71 */     return getName();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\schedule\Activity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */