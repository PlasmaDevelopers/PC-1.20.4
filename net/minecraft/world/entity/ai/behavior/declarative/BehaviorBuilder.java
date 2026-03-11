/*     */ package net.minecraft.world.entity.ai.behavior.declarative;
/*     */ 
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.kinds.Const;
/*     */ import com.mojang.datafixers.kinds.IdF;
/*     */ import com.mojang.datafixers.kinds.K1;
/*     */ import com.mojang.datafixers.kinds.OptionalBox;
/*     */ import com.mojang.datafixers.util.Function3;
/*     */ import com.mojang.datafixers.util.Function4;
/*     */ import com.mojang.datafixers.util.Unit;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.BiPredicate;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.ai.Brain;
/*     */ import net.minecraft.world.entity.ai.behavior.OneShot;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ 
/*     */ public class BehaviorBuilder<E extends LivingEntity, M> implements App<BehaviorBuilder.Mu<E>, M> {
/*     */   private final TriggerWithResult<E, M> trigger;
/*     */   
/*     */   public static final class Mu<E extends LivingEntity> implements K1 {}
/*     */   
/*     */   public static <E extends LivingEntity, M> BehaviorBuilder<E, M> unbox(App<Mu<E>, M> $$0) {
/*  32 */     return (BehaviorBuilder)$$0;
/*     */   }
/*     */   
/*     */   public static <E extends LivingEntity> Instance<E> instance() {
/*  36 */     return new Instance<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E extends LivingEntity> OneShot<E> create(Function<Instance<E>, ? extends App<Mu<E>, Trigger<E>>> $$0) {
/*  43 */     final TriggerWithResult<E, Trigger<E>> resolvedBuilder = get($$0.apply(instance()));
/*  44 */     return new OneShot<E>()
/*     */       {
/*     */         public boolean trigger(ServerLevel $$0, E $$1, long $$2)
/*     */         {
/*  48 */           Trigger<E> $$3 = resolvedBuilder.tryTrigger($$0, $$1, $$2);
/*  49 */           if ($$3 == null) {
/*  50 */             return false;
/*     */           }
/*     */           
/*  53 */           return $$3.trigger($$0, $$1, $$2);
/*     */         }
/*     */ 
/*     */         
/*     */         public String debugString() {
/*  58 */           return "OneShot[" + resolvedBuilder.debugString() + "]";
/*     */         }
/*     */ 
/*     */         
/*     */         public String toString() {
/*  63 */           return debugString();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E extends LivingEntity> OneShot<E> sequence(Trigger<? super E> $$0, Trigger<? super E> $$1) {
/*  72 */     return create($$2 -> $$2.group($$2.ifTriggered($$0)).apply($$2, ()));
/*     */   }
/*     */ 
/*     */   
/*     */   public static <E extends LivingEntity> OneShot<E> triggerIf(Predicate<E> $$0, OneShot<? super E> $$1) {
/*  77 */     return sequence((Trigger<? super E>)triggerIf($$0), (Trigger<? super E>)$$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public static <E extends LivingEntity> OneShot<E> triggerIf(Predicate<E> $$0) {
/*  82 */     return create($$1 -> $$1.point(()));
/*     */   }
/*     */ 
/*     */   
/*     */   public static <E extends LivingEntity> OneShot<E> triggerIf(BiPredicate<ServerLevel, E> $$0) {
/*  87 */     return create($$1 -> $$1.point(()));
/*     */   }
/*     */ 
/*     */   
/*     */   static <E extends LivingEntity, M> TriggerWithResult<E, M> get(App<Mu<E>, M> $$0) {
/*  92 */     return (unbox($$0)).trigger;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   BehaviorBuilder(TriggerWithResult<E, M> $$0) {
/* 104 */     this.trigger = $$0;
/*     */   }
/*     */   
/*     */   static <E extends LivingEntity, M> BehaviorBuilder<E, M> create(TriggerWithResult<E, M> $$0) {
/* 108 */     return new BehaviorBuilder<>($$0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class PureMemory<E extends LivingEntity, F extends K1, Value>
/*     */     extends BehaviorBuilder<E, MemoryAccessor<F, Value>>
/*     */   {
/*     */     PureMemory(MemoryCondition<F, Value> $$0)
/*     */     {
/* 118 */       super(new BehaviorBuilder.TriggerWithResult<E, MemoryAccessor<F, Value>>($$0)
/*     */           {
/*     */             public MemoryAccessor<F, Value> tryTrigger(ServerLevel $$0, E $$1, long $$2)
/*     */             {
/* 122 */               Brain<?> $$3 = $$1.getBrain();
/* 123 */               Optional<Value> $$4 = $$3.getMemoryInternal(condition.memory());
/* 124 */               if ($$4 == null) {
/* 125 */                 return null;
/*     */               }
/* 127 */               return condition.createAccessor($$3, $$4);
/*     */             }
/*     */ 
/*     */             
/*     */             public String debugString() {
/* 132 */               return "M[" + condition + "]";
/*     */             }
/*     */             
/*     */             public String toString()
/*     */             {
/* 137 */               return debugString(); } }); } } class null implements TriggerWithResult<E, MemoryAccessor<F, Value>> { public MemoryAccessor<F, Value> tryTrigger(ServerLevel $$0, E $$1, long $$2) { Brain<?> $$3 = $$1.getBrain(); Optional<Value> $$4 = $$3.getMemoryInternal(condition.memory()); if ($$4 == null) return null;  return condition.createAccessor($$3, $$4); } public String debugString() { return "M[" + condition + "]"; } public String toString() { return debugString(); }
/*     */      }
/*     */ 
/*     */   
/*     */   private static final class Constant<E extends LivingEntity, A>
/*     */     extends BehaviorBuilder<E, A>
/*     */   {
/*     */     Constant(A $$0) {
/* 145 */       this($$0, () -> "C[" + $$0 + "]");
/*     */     }
/*     */     
/*     */     Constant(A $$0, Supplier<String> $$1) {
/* 149 */       super(new BehaviorBuilder.TriggerWithResult<E, A>($$0, $$1)
/*     */           {
/*     */             public A tryTrigger(ServerLevel $$0, E $$1, long $$2) {
/* 152 */               return (A)a;
/*     */             }
/*     */ 
/*     */             
/*     */             public String debugString() {
/* 157 */               return debugString.get();
/*     */             }
/*     */             
/*     */             public String toString()
/*     */             {
/* 162 */               return debugString(); } }); } } class null implements TriggerWithResult<E, A> { public A tryTrigger(ServerLevel $$0, E $$1, long $$2) { return (A)a; } public String debugString() { return debugString.get(); } public String toString() { return debugString(); }
/*     */      }
/*     */ 
/*     */   
/*     */   private static final class TriggerWrapper<E extends LivingEntity>
/*     */     extends BehaviorBuilder<E, Unit>
/*     */   {
/*     */     TriggerWrapper(Trigger<? super E> $$0) {
/* 170 */       super(new BehaviorBuilder.TriggerWithResult<E, Unit>($$0)
/*     */           {
/*     */             @Nullable
/*     */             public Unit tryTrigger(ServerLevel $$0, E $$1, long $$2) {
/* 174 */               return dependentTrigger.trigger($$0, $$1, $$2) ? Unit.INSTANCE : null;
/*     */             }
/*     */             
/*     */             public String debugString()
/*     */             {
/* 179 */               return "T[" + dependentTrigger + "]"; } }); } } class null implements TriggerWithResult<E, Unit> { @Nullable public Unit tryTrigger(ServerLevel $$0, E $$1, long $$2) { return dependentTrigger.trigger($$0, $$1, $$2) ? Unit.INSTANCE : null; } public String debugString() { return "T[" + dependentTrigger + "]"; }
/*     */      }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Instance<E extends LivingEntity>
/*     */     implements Applicative<Mu<E>, Instance.Mu<E>>
/*     */   {
/*     */     private static final class Mu<E extends LivingEntity>
/*     */       implements Applicative.Mu {}
/*     */ 
/*     */     
/*     */     public <Value> Optional<Value> tryGet(MemoryAccessor<OptionalBox.Mu, Value> $$0) {
/* 193 */       return OptionalBox.unbox($$0.value());
/*     */     }
/*     */ 
/*     */     
/*     */     public <Value> Value get(MemoryAccessor<IdF.Mu, Value> $$0) {
/* 198 */       return (Value)IdF.get($$0.value());
/*     */     }
/*     */ 
/*     */     
/*     */     public <Value> BehaviorBuilder<E, MemoryAccessor<OptionalBox.Mu, Value>> registered(MemoryModuleType<Value> $$0) {
/* 203 */       return new BehaviorBuilder.PureMemory<>(new MemoryCondition.Registered<>($$0));
/*     */     }
/*     */ 
/*     */     
/*     */     public <Value> BehaviorBuilder<E, MemoryAccessor<IdF.Mu, Value>> present(MemoryModuleType<Value> $$0) {
/* 208 */       return new BehaviorBuilder.PureMemory<>(new MemoryCondition.Present<>($$0));
/*     */     }
/*     */ 
/*     */     
/*     */     public <Value> BehaviorBuilder<E, MemoryAccessor<Const.Mu<Unit>, Value>> absent(MemoryModuleType<Value> $$0) {
/* 213 */       return new BehaviorBuilder.PureMemory<>(new MemoryCondition.Absent<>($$0));
/*     */     }
/*     */ 
/*     */     
/*     */     public BehaviorBuilder<E, Unit> ifTriggered(Trigger<? super E> $$0) {
/* 218 */       return new BehaviorBuilder.TriggerWrapper<>($$0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public <A> BehaviorBuilder<E, A> point(A $$0) {
/* 225 */       return new BehaviorBuilder.Constant<>($$0);
/*     */     }
/*     */     
/*     */     public <A> BehaviorBuilder<E, A> point(Supplier<String> $$0, A $$1) {
/* 229 */       return new BehaviorBuilder.Constant<>($$1, $$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public <A, R> Function<App<BehaviorBuilder.Mu<E>, A>, App<BehaviorBuilder.Mu<E>, R>> lift1(App<BehaviorBuilder.Mu<E>, Function<A, R>> $$0) {
/* 234 */       return $$1 -> {
/*     */           final BehaviorBuilder.TriggerWithResult<E, A> aTrigger = BehaviorBuilder.get($$1);
/*     */           
/*     */           final BehaviorBuilder.TriggerWithResult<E, Function<A, R>> fTrigger = BehaviorBuilder.get($$0);
/*     */           return BehaviorBuilder.create(new BehaviorBuilder.TriggerWithResult<E, R>()
/*     */               {
/*     */                 public R tryTrigger(ServerLevel $$0, E $$1, long $$2)
/*     */                 {
/* 242 */                   A $$3 = aTrigger.tryTrigger($$0, $$1, $$2);
/* 243 */                   if ($$3 == null) {
/* 244 */                     return null;
/*     */                   }
/* 246 */                   Function<A, R> $$4 = fTrigger.tryTrigger($$0, $$1, $$2);
/* 247 */                   if ($$4 == null) {
/* 248 */                     return null;
/*     */                   }
/* 250 */                   return $$4.apply($$3);
/*     */                 }
/*     */ 
/*     */                 
/*     */                 public String debugString() {
/* 255 */                   return fTrigger.debugString() + " * " + fTrigger.debugString();
/*     */                 }
/*     */ 
/*     */                 
/*     */                 public String toString() {
/* 260 */                   return debugString();
/*     */                 }
/*     */               });
/*     */         };
/*     */     }
/*     */ 
/*     */     
/*     */     public <T, R> BehaviorBuilder<E, R> map(final Function<? super T, ? extends R> func, App<BehaviorBuilder.Mu<E>, T> $$1) {
/* 268 */       final BehaviorBuilder.TriggerWithResult<E, T> tTrigger = BehaviorBuilder.get($$1);
/* 269 */       return BehaviorBuilder.create(new BehaviorBuilder.TriggerWithResult<E, R>()
/*     */           {
/*     */             public R tryTrigger(ServerLevel $$0, E $$1, long $$2) {
/* 272 */               T $$3 = tTrigger.tryTrigger($$0, $$1, $$2);
/* 273 */               if ($$3 == null) {
/* 274 */                 return null;
/*     */               }
/* 276 */               return func.apply($$3);
/*     */             }
/*     */ 
/*     */             
/*     */             public String debugString() {
/* 281 */               return tTrigger.debugString() + ".map[" + tTrigger.debugString() + "]";
/*     */             }
/*     */ 
/*     */             
/*     */             public String toString() {
/* 286 */               return debugString();
/*     */             }
/*     */           });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public <A, B, R> BehaviorBuilder<E, R> ap2(App<BehaviorBuilder.Mu<E>, BiFunction<A, B, R>> $$0, App<BehaviorBuilder.Mu<E>, A> $$1, App<BehaviorBuilder.Mu<E>, B> $$2) {
/* 295 */       final BehaviorBuilder.TriggerWithResult<E, A> aTrigger = BehaviorBuilder.get($$1);
/* 296 */       final BehaviorBuilder.TriggerWithResult<E, B> bTrigger = BehaviorBuilder.get($$2);
/* 297 */       final BehaviorBuilder.TriggerWithResult<E, BiFunction<A, B, R>> fTrigger = BehaviorBuilder.get($$0);
/*     */       
/* 299 */       return BehaviorBuilder.create(new BehaviorBuilder.TriggerWithResult<E, R>()
/*     */           {
/*     */             public R tryTrigger(ServerLevel $$0, E $$1, long $$2) {
/* 302 */               A $$3 = aTrigger.tryTrigger($$0, $$1, $$2);
/* 303 */               if ($$3 == null) {
/* 304 */                 return null;
/*     */               }
/* 306 */               B $$4 = bTrigger.tryTrigger($$0, $$1, $$2);
/* 307 */               if ($$4 == null) {
/* 308 */                 return null;
/*     */               }
/* 310 */               BiFunction<A, B, R> $$5 = fTrigger.tryTrigger($$0, $$1, $$2);
/* 311 */               if ($$5 == null) {
/* 312 */                 return null;
/*     */               }
/* 314 */               return $$5.apply($$3, $$4);
/*     */             }
/*     */ 
/*     */             
/*     */             public String debugString() {
/* 319 */               return fTrigger.debugString() + " * " + fTrigger.debugString() + " * " + aTrigger.debugString();
/*     */             }
/*     */ 
/*     */             
/*     */             public String toString() {
/* 324 */               return debugString();
/*     */             }
/*     */           });
/*     */     }
/*     */ 
/*     */     
/*     */     public <T1, T2, T3, R> BehaviorBuilder<E, R> ap3(App<BehaviorBuilder.Mu<E>, Function3<T1, T2, T3, R>> $$0, App<BehaviorBuilder.Mu<E>, T1> $$1, App<BehaviorBuilder.Mu<E>, T2> $$2, App<BehaviorBuilder.Mu<E>, T3> $$3) {
/* 331 */       final BehaviorBuilder.TriggerWithResult<E, T1> t1Trigger = BehaviorBuilder.get($$1);
/* 332 */       final BehaviorBuilder.TriggerWithResult<E, T2> t2Trigger = BehaviorBuilder.get($$2);
/* 333 */       final BehaviorBuilder.TriggerWithResult<E, T3> t3Trigger = BehaviorBuilder.get($$3);
/* 334 */       final BehaviorBuilder.TriggerWithResult<E, Function3<T1, T2, T3, R>> fTrigger = BehaviorBuilder.get($$0);
/*     */       
/* 336 */       return BehaviorBuilder.create(new BehaviorBuilder.TriggerWithResult<E, R>()
/*     */           {
/*     */             public R tryTrigger(ServerLevel $$0, E $$1, long $$2) {
/* 339 */               T1 $$3 = t1Trigger.tryTrigger($$0, $$1, $$2);
/* 340 */               if ($$3 == null) {
/* 341 */                 return null;
/*     */               }
/* 343 */               T2 $$4 = t2Trigger.tryTrigger($$0, $$1, $$2);
/* 344 */               if ($$4 == null) {
/* 345 */                 return null;
/*     */               }
/* 347 */               T3 $$5 = t3Trigger.tryTrigger($$0, $$1, $$2);
/* 348 */               if ($$5 == null) {
/* 349 */                 return null;
/*     */               }
/* 351 */               Function3<T1, T2, T3, R> $$6 = fTrigger.tryTrigger($$0, $$1, $$2);
/* 352 */               if ($$6 == null) {
/* 353 */                 return null;
/*     */               }
/* 355 */               return (R)$$6.apply($$3, $$4, $$5);
/*     */             }
/*     */ 
/*     */             
/*     */             public String debugString() {
/* 360 */               return fTrigger.debugString() + " * " + fTrigger.debugString() + " * " + t1Trigger.debugString() + " * " + t2Trigger.debugString();
/*     */             }
/*     */ 
/*     */             
/*     */             public String toString() {
/* 365 */               return debugString();
/*     */             }
/*     */           });
/*     */     }
/*     */     
/*     */     public <T1, T2, T3, T4, R> BehaviorBuilder<E, R> ap4(App<BehaviorBuilder.Mu<E>, Function4<T1, T2, T3, T4, R>> $$0, App<BehaviorBuilder.Mu<E>, T1> $$1, App<BehaviorBuilder.Mu<E>, T2> $$2, App<BehaviorBuilder.Mu<E>, T3> $$3, App<BehaviorBuilder.Mu<E>, T4> $$4)
/*     */     {
/* 372 */       final BehaviorBuilder.TriggerWithResult<E, T1> t1Trigger = BehaviorBuilder.get($$1);
/* 373 */       final BehaviorBuilder.TriggerWithResult<E, T2> t2Trigger = BehaviorBuilder.get($$2);
/* 374 */       final BehaviorBuilder.TriggerWithResult<E, T3> t3Trigger = BehaviorBuilder.get($$3);
/* 375 */       final BehaviorBuilder.TriggerWithResult<E, T4> t4Trigger = BehaviorBuilder.get($$4);
/* 376 */       final BehaviorBuilder.TriggerWithResult<E, Function4<T1, T2, T3, T4, R>> fTrigger = BehaviorBuilder.get($$0);
/*     */       
/* 378 */       return BehaviorBuilder.create(new BehaviorBuilder.TriggerWithResult<E, R>()
/*     */           {
/*     */             public R tryTrigger(ServerLevel $$0, E $$1, long $$2) {
/* 381 */               T1 $$3 = t1Trigger.tryTrigger($$0, $$1, $$2);
/* 382 */               if ($$3 == null) {
/* 383 */                 return null;
/*     */               }
/* 385 */               T2 $$4 = t2Trigger.tryTrigger($$0, $$1, $$2);
/* 386 */               if ($$4 == null) {
/* 387 */                 return null;
/*     */               }
/* 389 */               T3 $$5 = t3Trigger.tryTrigger($$0, $$1, $$2);
/* 390 */               if ($$5 == null) {
/* 391 */                 return null;
/*     */               }
/* 393 */               T4 $$6 = t4Trigger.tryTrigger($$0, $$1, $$2);
/* 394 */               if ($$6 == null) {
/* 395 */                 return null;
/*     */               }
/* 397 */               Function4<T1, T2, T3, T4, R> $$7 = fTrigger.tryTrigger($$0, $$1, $$2);
/* 398 */               if ($$7 == null) {
/* 399 */                 return null;
/*     */               }
/* 401 */               return (R)$$7.apply($$3, $$4, $$5, $$6);
/*     */             }
/*     */ 
/*     */             
/*     */             public String debugString() {
/* 406 */               return fTrigger.debugString() + " * " + fTrigger.debugString() + " * " + t1Trigger.debugString() + " * " + t2Trigger.debugString() + " * " + t3Trigger.debugString();
/*     */             }
/*     */             
/*     */             public String toString()
/*     */             {
/* 411 */               return debugString(); } }); } } private static final class Mu<E extends LivingEntity> implements Applicative.Mu {} class null implements TriggerWithResult<E, R> { public R tryTrigger(ServerLevel $$0, E $$1, long $$2) { A $$3 = aTrigger.tryTrigger($$0, $$1, $$2); if ($$3 == null) return null;  Function<A, R> $$4 = fTrigger.tryTrigger($$0, $$1, $$2); if ($$4 == null) return null;  return $$4.apply($$3); } public String debugString() { return fTrigger.debugString() + " * " + fTrigger.debugString(); } public String toString() { return debugString(); } } class null implements TriggerWithResult<E, R> { public R tryTrigger(ServerLevel $$0, E $$1, long $$2) { T $$3 = tTrigger.tryTrigger($$0, $$1, $$2); if ($$3 == null) return null;  return func.apply($$3); } public String debugString() { return tTrigger.debugString() + ".map[" + tTrigger.debugString() + "]"; } public String toString() { return debugString(); } } class null implements TriggerWithResult<E, R> { public R tryTrigger(ServerLevel $$0, E $$1, long $$2) { A $$3 = aTrigger.tryTrigger($$0, $$1, $$2); if ($$3 == null) return null;  B $$4 = bTrigger.tryTrigger($$0, $$1, $$2); if ($$4 == null) return null;  BiFunction<A, B, R> $$5 = fTrigger.tryTrigger($$0, $$1, $$2); if ($$5 == null) return null;  return $$5.apply($$3, $$4); } public String debugString() { return fTrigger.debugString() + " * " + fTrigger.debugString() + " * " + aTrigger.debugString(); } public String toString() { return debugString(); } } class null implements TriggerWithResult<E, R> { public R tryTrigger(ServerLevel $$0, E $$1, long $$2) { T1 $$3 = t1Trigger.tryTrigger($$0, $$1, $$2); if ($$3 == null) return null;  T2 $$4 = t2Trigger.tryTrigger($$0, $$1, $$2); if ($$4 == null) return null;  T3 $$5 = t3Trigger.tryTrigger($$0, $$1, $$2); if ($$5 == null) return null;  Function3<T1, T2, T3, R> $$6 = fTrigger.tryTrigger($$0, $$1, $$2); if ($$6 == null) return null;  return (R)$$6.apply($$3, $$4, $$5); } public String debugString() { return fTrigger.debugString() + " * " + fTrigger.debugString() + " * " + t1Trigger.debugString() + " * " + t2Trigger.debugString(); } public String toString() { return debugString(); } } class null implements TriggerWithResult<E, R> { public String toString() { return debugString(); }
/*     */ 
/*     */     
/*     */     public R tryTrigger(ServerLevel $$0, E $$1, long $$2) {
/*     */       T1 $$3 = t1Trigger.tryTrigger($$0, $$1, $$2);
/*     */       if ($$3 == null)
/*     */         return null; 
/*     */       T2 $$4 = t2Trigger.tryTrigger($$0, $$1, $$2);
/*     */       if ($$4 == null)
/*     */         return null; 
/*     */       T3 $$5 = t3Trigger.tryTrigger($$0, $$1, $$2);
/*     */       if ($$5 == null)
/*     */         return null; 
/*     */       T4 $$6 = t4Trigger.tryTrigger($$0, $$1, $$2);
/*     */       if ($$6 == null)
/*     */         return null; 
/*     */       Function4<T1, T2, T3, T4, R> $$7 = fTrigger.tryTrigger($$0, $$1, $$2);
/*     */       if ($$7 == null)
/*     */         return null; 
/*     */       return (R)$$7.apply($$3, $$4, $$5, $$6);
/*     */     }
/*     */     
/*     */     public String debugString() {
/*     */       return fTrigger.debugString() + " * " + fTrigger.debugString() + " * " + t1Trigger.debugString() + " * " + t2Trigger.debugString() + " * " + t3Trigger.debugString();
/*     */     } }
/*     */ 
/*     */   
/*     */   private static interface TriggerWithResult<E extends LivingEntity, R> {
/*     */     @Nullable
/*     */     R tryTrigger(ServerLevel param1ServerLevel, E param1E, long param1Long);
/*     */     
/*     */     String debugString();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\declarative\BehaviorBuilder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */