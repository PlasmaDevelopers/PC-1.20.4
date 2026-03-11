/*    */ package net.minecraft.world.entity.ai.sensing;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
/*    */ import it.unimi.dsi.fastutil.ints.IntSet;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ 
/*    */ public class Sensing {
/*    */   private final Mob mob;
/* 10 */   private final IntSet seen = (IntSet)new IntOpenHashSet();
/* 11 */   private final IntSet unseen = (IntSet)new IntOpenHashSet();
/*    */   
/*    */   public Sensing(Mob $$0) {
/* 14 */     this.mob = $$0;
/*    */   }
/*    */   
/*    */   public void tick() {
/* 18 */     this.seen.clear();
/* 19 */     this.unseen.clear();
/*    */   }
/*    */   
/*    */   public boolean hasLineOfSight(Entity $$0) {
/* 23 */     int $$1 = $$0.getId();
/* 24 */     if (this.seen.contains($$1)) {
/* 25 */       return true;
/*    */     }
/* 27 */     if (this.unseen.contains($$1)) {
/* 28 */       return false;
/*    */     }
/*    */     
/* 31 */     this.mob.level().getProfiler().push("hasLineOfSight");
/* 32 */     boolean $$2 = this.mob.hasLineOfSight($$0);
/* 33 */     this.mob.level().getProfiler().pop();
/* 34 */     if ($$2) {
/* 35 */       this.seen.add($$1);
/*    */     } else {
/* 37 */       this.unseen.add($$1);
/*    */     } 
/* 39 */     return $$2;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\sensing\Sensing.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */