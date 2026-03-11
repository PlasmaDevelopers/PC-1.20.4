/*     */ package net.minecraft.world.entity.ai.behavior.declarative;
/*     */ 
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.kinds.Const;
/*     */ import com.mojang.datafixers.kinds.IdF;
/*     */ import com.mojang.datafixers.kinds.OptionalBox;
/*     */ import com.mojang.datafixers.util.Function3;
/*     */ import com.mojang.datafixers.util.Function4;
/*     */ import com.mojang.datafixers.util.Unit;
/*     */ import java.util.Optional;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Instance<E extends LivingEntity>
/*     */   implements Applicative<BehaviorBuilder.Mu<E>, BehaviorBuilder.Instance.Mu<E>>
/*     */ {
/*     */   private static final class Mu<E extends LivingEntity>
/*     */     implements Applicative.Mu {}
/*     */   
/*     */   public <Value> Optional<Value> tryGet(MemoryAccessor<OptionalBox.Mu, Value> $$0) {
/* 193 */     return OptionalBox.unbox($$0.value());
/*     */   }
/*     */ 
/*     */   
/*     */   public <Value> Value get(MemoryAccessor<IdF.Mu, Value> $$0) {
/* 198 */     return (Value)IdF.get($$0.value());
/*     */   }
/*     */ 
/*     */   
/*     */   public <Value> BehaviorBuilder<E, MemoryAccessor<OptionalBox.Mu, Value>> registered(MemoryModuleType<Value> $$0) {
/* 203 */     return new BehaviorBuilder.PureMemory<>(new MemoryCondition.Registered<>($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public <Value> BehaviorBuilder<E, MemoryAccessor<IdF.Mu, Value>> present(MemoryModuleType<Value> $$0) {
/* 208 */     return new BehaviorBuilder.PureMemory<>(new MemoryCondition.Present<>($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public <Value> BehaviorBuilder<E, MemoryAccessor<Const.Mu<Unit>, Value>> absent(MemoryModuleType<Value> $$0) {
/* 213 */     return new BehaviorBuilder.PureMemory<>(new MemoryCondition.Absent<>($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public BehaviorBuilder<E, Unit> ifTriggered(Trigger<? super E> $$0) {
/* 218 */     return new BehaviorBuilder.TriggerWrapper<>($$0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <A> BehaviorBuilder<E, A> point(A $$0) {
/* 225 */     return new BehaviorBuilder.Constant<>($$0);
/*     */   }
/*     */   
/*     */   public <A> BehaviorBuilder<E, A> point(Supplier<String> $$0, A $$1) {
/* 229 */     return new BehaviorBuilder.Constant<>($$1, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public <A, R> Function<App<BehaviorBuilder.Mu<E>, A>, App<BehaviorBuilder.Mu<E>, R>> lift1(App<BehaviorBuilder.Mu<E>, Function<A, R>> $$0) {
/* 234 */     return $$1 -> {
/*     */         final BehaviorBuilder.TriggerWithResult<E, A> aTrigger = BehaviorBuilder.get($$1);
/*     */         
/*     */         final BehaviorBuilder.TriggerWithResult<E, Function<A, R>> fTrigger = BehaviorBuilder.get($$0);
/*     */         return BehaviorBuilder.create(new BehaviorBuilder.TriggerWithResult<E, R>()
/*     */             {
/*     */               public R tryTrigger(ServerLevel $$0, E $$1, long $$2)
/*     */               {
/* 242 */                 A $$3 = aTrigger.tryTrigger($$0, $$1, $$2);
/* 243 */                 if ($$3 == null) {
/* 244 */                   return null;
/*     */                 }
/* 246 */                 Function<A, R> $$4 = fTrigger.tryTrigger($$0, $$1, $$2);
/* 247 */                 if ($$4 == null) {
/* 248 */                   return null;
/*     */                 }
/* 250 */                 return $$4.apply($$3);
/*     */               }
/*     */ 
/*     */               
/*     */               public String debugString() {
/* 255 */                 return fTrigger.debugString() + " * " + fTrigger.debugString();
/*     */               }
/*     */ 
/*     */               
/*     */               public String toString() {
/* 260 */                 return debugString();
/*     */               }
/*     */             });
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public <T, R> BehaviorBuilder<E, R> map(final Function<? super T, ? extends R> func, App<BehaviorBuilder.Mu<E>, T> $$1) {
/* 268 */     final BehaviorBuilder.TriggerWithResult<E, T> tTrigger = BehaviorBuilder.get($$1);
/* 269 */     return BehaviorBuilder.create(new BehaviorBuilder.TriggerWithResult<E, R>()
/*     */         {
/*     */           public R tryTrigger(ServerLevel $$0, E $$1, long $$2) {
/* 272 */             T $$3 = tTrigger.tryTrigger($$0, $$1, $$2);
/* 273 */             if ($$3 == null) {
/* 274 */               return null;
/*     */             }
/* 276 */             return func.apply($$3);
/*     */           }
/*     */ 
/*     */           
/*     */           public String debugString() {
/* 281 */             return tTrigger.debugString() + ".map[" + tTrigger.debugString() + "]";
/*     */           }
/*     */ 
/*     */           
/*     */           public String toString() {
/* 286 */             return debugString();
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <A, B, R> BehaviorBuilder<E, R> ap2(App<BehaviorBuilder.Mu<E>, BiFunction<A, B, R>> $$0, App<BehaviorBuilder.Mu<E>, A> $$1, App<BehaviorBuilder.Mu<E>, B> $$2) {
/* 295 */     final BehaviorBuilder.TriggerWithResult<E, A> aTrigger = BehaviorBuilder.get($$1);
/* 296 */     final BehaviorBuilder.TriggerWithResult<E, B> bTrigger = BehaviorBuilder.get($$2);
/* 297 */     final BehaviorBuilder.TriggerWithResult<E, BiFunction<A, B, R>> fTrigger = BehaviorBuilder.get($$0);
/*     */     
/* 299 */     return BehaviorBuilder.create(new BehaviorBuilder.TriggerWithResult<E, R>()
/*     */         {
/*     */           public R tryTrigger(ServerLevel $$0, E $$1, long $$2) {
/* 302 */             A $$3 = aTrigger.tryTrigger($$0, $$1, $$2);
/* 303 */             if ($$3 == null) {
/* 304 */               return null;
/*     */             }
/* 306 */             B $$4 = bTrigger.tryTrigger($$0, $$1, $$2);
/* 307 */             if ($$4 == null) {
/* 308 */               return null;
/*     */             }
/* 310 */             BiFunction<A, B, R> $$5 = fTrigger.tryTrigger($$0, $$1, $$2);
/* 311 */             if ($$5 == null) {
/* 312 */               return null;
/*     */             }
/* 314 */             return $$5.apply($$3, $$4);
/*     */           }
/*     */ 
/*     */           
/*     */           public String debugString() {
/* 319 */             return fTrigger.debugString() + " * " + fTrigger.debugString() + " * " + aTrigger.debugString();
/*     */           }
/*     */ 
/*     */           
/*     */           public String toString() {
/* 324 */             return debugString();
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public <T1, T2, T3, R> BehaviorBuilder<E, R> ap3(App<BehaviorBuilder.Mu<E>, Function3<T1, T2, T3, R>> $$0, App<BehaviorBuilder.Mu<E>, T1> $$1, App<BehaviorBuilder.Mu<E>, T2> $$2, App<BehaviorBuilder.Mu<E>, T3> $$3) {
/* 331 */     final BehaviorBuilder.TriggerWithResult<E, T1> t1Trigger = BehaviorBuilder.get($$1);
/* 332 */     final BehaviorBuilder.TriggerWithResult<E, T2> t2Trigger = BehaviorBuilder.get($$2);
/* 333 */     final BehaviorBuilder.TriggerWithResult<E, T3> t3Trigger = BehaviorBuilder.get($$3);
/* 334 */     final BehaviorBuilder.TriggerWithResult<E, Function3<T1, T2, T3, R>> fTrigger = BehaviorBuilder.get($$0);
/*     */     
/* 336 */     return BehaviorBuilder.create(new BehaviorBuilder.TriggerWithResult<E, R>()
/*     */         {
/*     */           public R tryTrigger(ServerLevel $$0, E $$1, long $$2) {
/* 339 */             T1 $$3 = t1Trigger.tryTrigger($$0, $$1, $$2);
/* 340 */             if ($$3 == null) {
/* 341 */               return null;
/*     */             }
/* 343 */             T2 $$4 = t2Trigger.tryTrigger($$0, $$1, $$2);
/* 344 */             if ($$4 == null) {
/* 345 */               return null;
/*     */             }
/* 347 */             T3 $$5 = t3Trigger.tryTrigger($$0, $$1, $$2);
/* 348 */             if ($$5 == null) {
/* 349 */               return null;
/*     */             }
/* 351 */             Function3<T1, T2, T3, R> $$6 = fTrigger.tryTrigger($$0, $$1, $$2);
/* 352 */             if ($$6 == null) {
/* 353 */               return null;
/*     */             }
/* 355 */             return (R)$$6.apply($$3, $$4, $$5);
/*     */           }
/*     */ 
/*     */           
/*     */           public String debugString() {
/* 360 */             return fTrigger.debugString() + " * " + fTrigger.debugString() + " * " + t1Trigger.debugString() + " * " + t2Trigger.debugString();
/*     */           }
/*     */ 
/*     */           
/*     */           public String toString() {
/* 365 */             return debugString();
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public <T1, T2, T3, T4, R> BehaviorBuilder<E, R> ap4(App<BehaviorBuilder.Mu<E>, Function4<T1, T2, T3, T4, R>> $$0, App<BehaviorBuilder.Mu<E>, T1> $$1, App<BehaviorBuilder.Mu<E>, T2> $$2, App<BehaviorBuilder.Mu<E>, T3> $$3, App<BehaviorBuilder.Mu<E>, T4> $$4) {
/* 372 */     final BehaviorBuilder.TriggerWithResult<E, T1> t1Trigger = BehaviorBuilder.get($$1);
/* 373 */     final BehaviorBuilder.TriggerWithResult<E, T2> t2Trigger = BehaviorBuilder.get($$2);
/* 374 */     final BehaviorBuilder.TriggerWithResult<E, T3> t3Trigger = BehaviorBuilder.get($$3);
/* 375 */     final BehaviorBuilder.TriggerWithResult<E, T4> t4Trigger = BehaviorBuilder.get($$4);
/* 376 */     final BehaviorBuilder.TriggerWithResult<E, Function4<T1, T2, T3, T4, R>> fTrigger = BehaviorBuilder.get($$0);
/*     */     
/* 378 */     return BehaviorBuilder.create(new BehaviorBuilder.TriggerWithResult<E, R>()
/*     */         {
/*     */           public R tryTrigger(ServerLevel $$0, E $$1, long $$2) {
/* 381 */             T1 $$3 = t1Trigger.tryTrigger($$0, $$1, $$2);
/* 382 */             if ($$3 == null) {
/* 383 */               return null;
/*     */             }
/* 385 */             T2 $$4 = t2Trigger.tryTrigger($$0, $$1, $$2);
/* 386 */             if ($$4 == null) {
/* 387 */               return null;
/*     */             }
/* 389 */             T3 $$5 = t3Trigger.tryTrigger($$0, $$1, $$2);
/* 390 */             if ($$5 == null) {
/* 391 */               return null;
/*     */             }
/* 393 */             T4 $$6 = t4Trigger.tryTrigger($$0, $$1, $$2);
/* 394 */             if ($$6 == null) {
/* 395 */               return null;
/*     */             }
/* 397 */             Function4<T1, T2, T3, T4, R> $$7 = fTrigger.tryTrigger($$0, $$1, $$2);
/* 398 */             if ($$7 == null) {
/* 399 */               return null;
/*     */             }
/* 401 */             return (R)$$7.apply($$3, $$4, $$5, $$6);
/*     */           }
/*     */ 
/*     */           
/*     */           public String debugString() {
/* 406 */             return fTrigger.debugString() + " * " + fTrigger.debugString() + " * " + t1Trigger.debugString() + " * " + t2Trigger.debugString() + " * " + t3Trigger.debugString();
/*     */           }
/*     */ 
/*     */           
/*     */           public String toString() {
/* 411 */             return debugString();
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\declarative\BehaviorBuilder$Instance.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */