/*    */ package net.minecraft.advancements.critereon;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import com.google.common.collect.Maps;
/*    */ import com.google.common.collect.Sets;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Optional;
/*    */ import java.util.Set;
/*    */ import java.util.function.Predicate;
/*    */ import net.minecraft.advancements.CriterionTrigger;
/*    */ import net.minecraft.advancements.CriterionTriggerInstance;
/*    */ import net.minecraft.server.PlayerAdvancements;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.level.storage.loot.LootContext;
/*    */ 
/*    */ public abstract class SimpleCriterionTrigger<T extends SimpleCriterionTrigger.SimpleInstance> implements CriterionTrigger<T> {
/* 19 */   private final Map<PlayerAdvancements, Set<CriterionTrigger.Listener<T>>> players = Maps.newIdentityHashMap();
/*    */ 
/*    */   
/*    */   public final void addPlayerListener(PlayerAdvancements $$0, CriterionTrigger.Listener<T> $$1) {
/* 23 */     ((Set<CriterionTrigger.Listener<T>>)this.players.computeIfAbsent($$0, $$0 -> Sets.newHashSet())).add($$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public final void removePlayerListener(PlayerAdvancements $$0, CriterionTrigger.Listener<T> $$1) {
/* 28 */     Set<CriterionTrigger.Listener<T>> $$2 = this.players.get($$0);
/* 29 */     if ($$2 != null) {
/* 30 */       $$2.remove($$1);
/* 31 */       if ($$2.isEmpty()) {
/* 32 */         this.players.remove($$0);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public final void removePlayerListeners(PlayerAdvancements $$0) {
/* 39 */     this.players.remove($$0);
/*    */   }
/*    */   
/*    */   protected void trigger(ServerPlayer $$0, Predicate<T> $$1) {
/* 43 */     PlayerAdvancements $$2 = $$0.getAdvancements();
/* 44 */     Set<CriterionTrigger.Listener<T>> $$3 = this.players.get($$2);
/*    */     
/* 46 */     if ($$3 == null || $$3.isEmpty()) {
/*    */       return;
/*    */     }
/*    */     
/* 50 */     LootContext $$4 = EntityPredicate.createContext($$0, (Entity)$$0);
/*    */     
/* 52 */     List<CriterionTrigger.Listener<T>> $$5 = null;
/* 53 */     for (CriterionTrigger.Listener<T> $$6 : $$3) {
/* 54 */       SimpleInstance simpleInstance = (SimpleInstance)$$6.trigger();
/*    */ 
/*    */       
/* 57 */       if (!$$1.test((T)simpleInstance)) {
/*    */         continue;
/*    */       }
/* 60 */       Optional<ContextAwarePredicate> $$8 = simpleInstance.player();
/* 61 */       if ($$8.isEmpty() || ((ContextAwarePredicate)$$8.get()).matches($$4)) {
/* 62 */         if ($$5 == null) {
/* 63 */           $$5 = Lists.newArrayList();
/*    */         }
/* 65 */         $$5.add($$6);
/*    */       } 
/*    */     } 
/*    */     
/* 69 */     if ($$5 != null)
/* 70 */       for (CriterionTrigger.Listener<T> $$9 : $$5) {
/* 71 */         $$9.run($$2);
/*    */       } 
/*    */   }
/*    */   
/*    */   public static interface SimpleInstance
/*    */     extends CriterionTriggerInstance
/*    */   {
/*    */     default void validate(CriterionValidator $$0) {
/* 79 */       $$0.validateEntity(player(), ".player");
/*    */     }
/*    */     
/*    */     Optional<ContextAwarePredicate> player();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\SimpleCriterionTrigger.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */