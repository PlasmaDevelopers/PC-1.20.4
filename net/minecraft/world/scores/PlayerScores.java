/*    */ package net.minecraft.world.scores;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*    */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*    */ import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
/*    */ import java.util.Collections;
/*    */ import java.util.Map;
/*    */ import java.util.function.Consumer;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ class PlayerScores
/*    */ {
/* 14 */   private final Reference2ObjectOpenHashMap<Objective, Score> scores = new Reference2ObjectOpenHashMap(16, 0.5F);
/*    */   
/*    */   @Nullable
/*    */   public Score get(Objective $$0) {
/* 18 */     return (Score)this.scores.get($$0);
/*    */   }
/*    */   
/*    */   public Score getOrCreate(Objective $$0, Consumer<Score> $$1) {
/* 22 */     return (Score)this.scores.computeIfAbsent($$0, $$1 -> {
/*    */           Score $$2 = new Score();
/*    */           $$0.accept($$2);
/*    */           return $$2;
/*    */         });
/*    */   }
/*    */   
/*    */   public boolean remove(Objective $$0) {
/* 30 */     return (this.scores.remove($$0) != null);
/*    */   }
/*    */   
/*    */   public boolean hasScores() {
/* 34 */     return !this.scores.isEmpty();
/*    */   }
/*    */   
/*    */   public Object2IntMap<Objective> listScores() {
/* 38 */     Object2IntOpenHashMap object2IntOpenHashMap = new Object2IntOpenHashMap();
/* 39 */     this.scores.forEach(($$1, $$2) -> $$0.put($$1, $$2.value()));
/* 40 */     return (Object2IntMap<Objective>)object2IntOpenHashMap;
/*    */   }
/*    */   
/*    */   void setScore(Objective $$0, Score $$1) {
/* 44 */     this.scores.put($$0, $$1);
/*    */   }
/*    */   
/*    */   Map<Objective, Score> listRawScores() {
/* 48 */     return Collections.unmodifiableMap((Map<? extends Objective, ? extends Score>)this.scores);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\scores\PlayerScores.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */