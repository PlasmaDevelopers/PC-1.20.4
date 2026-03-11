/*     */ package net.minecraft.gametest.framework;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Supplier;
/*     */ 
/*     */ public class GameTestSequence
/*     */ {
/*     */   final GameTestInfo parent;
/*     */   
/*     */   public class Condition
/*     */   {
/*     */     private static final long NOT_TRIGGERED = -1L;
/*  16 */     private long triggerTime = -1L;
/*     */     
/*     */     void trigger(long $$0) {
/*  19 */       if (this.triggerTime != -1L) {
/*  20 */         throw new IllegalStateException("Condition already triggered at " + this.triggerTime);
/*     */       }
/*  22 */       this.triggerTime = $$0;
/*     */     }
/*     */     
/*     */     public void assertTriggeredThisTick() {
/*  26 */       long $$0 = GameTestSequence.this.parent.getTick();
/*  27 */       if (this.triggerTime != $$0) {
/*  28 */         if (this.triggerTime == -1L) {
/*  29 */           throw new GameTestAssertException("Condition not triggered (t=" + $$0 + ")");
/*     */         }
/*  31 */         throw new GameTestAssertException("Condition triggered at " + this.triggerTime + ", (t=" + $$0 + ")");
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  38 */   private final List<GameTestEvent> events = Lists.newArrayList();
/*     */   private long lastTick;
/*     */   
/*     */   GameTestSequence(GameTestInfo $$0) {
/*  42 */     this.parent = $$0;
/*  43 */     this.lastTick = $$0.getTick();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GameTestSequence thenWaitUntil(Runnable $$0) {
/*  50 */     this.events.add(GameTestEvent.create($$0));
/*  51 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GameTestSequence thenWaitUntil(long $$0, Runnable $$1) {
/*  58 */     this.events.add(GameTestEvent.create($$0, $$1));
/*  59 */     return this;
/*     */   }
/*     */   
/*     */   public GameTestSequence thenIdle(int $$0) {
/*  63 */     return thenExecuteAfter($$0, () -> {
/*     */         
/*     */         });
/*     */   } public GameTestSequence thenExecute(Runnable $$0) {
/*  67 */     this.events.add(GameTestEvent.create(() -> executeWithoutFail($$0)));
/*  68 */     return this;
/*     */   }
/*     */   
/*     */   public GameTestSequence thenExecuteAfter(int $$0, Runnable $$1) {
/*  72 */     this.events.add(GameTestEvent.create(() -> {
/*     */             if (this.parent.getTick() < this.lastTick + $$0) {
/*     */               throw new GameTestAssertException("Waiting");
/*     */             }
/*     */             executeWithoutFail($$1);
/*     */           }));
/*  78 */     return this;
/*     */   }
/*     */   
/*     */   public GameTestSequence thenExecuteFor(int $$0, Runnable $$1) {
/*  82 */     this.events.add(GameTestEvent.create(() -> {
/*     */             if (this.parent.getTick() < this.lastTick + $$0) {
/*     */               executeWithoutFail($$1);
/*     */               throw new GameTestAssertException("Waiting");
/*     */             } 
/*     */           }));
/*  88 */     return this;
/*     */   }
/*     */   
/*     */   public void thenSucceed() {
/*  92 */     Objects.requireNonNull(this.parent); this.events.add(GameTestEvent.create(this.parent::succeed));
/*     */   }
/*     */   
/*     */   public void thenFail(Supplier<Exception> $$0) {
/*  96 */     this.events.add(GameTestEvent.create(() -> this.parent.fail($$0.get())));
/*     */   }
/*     */   
/*     */   public Condition thenTrigger() {
/* 100 */     Condition $$0 = new Condition();
/* 101 */     this.events.add(GameTestEvent.create(() -> $$0.trigger(this.parent.getTick())));
/* 102 */     return $$0;
/*     */   }
/*     */   
/*     */   public void tickAndContinue(long $$0) {
/*     */     try {
/* 107 */       tick($$0);
/* 108 */     } catch (GameTestAssertException gameTestAssertException) {}
/*     */   }
/*     */ 
/*     */   
/*     */   public void tickAndFailIfNotComplete(long $$0) {
/*     */     try {
/* 114 */       tick($$0);
/* 115 */     } catch (GameTestAssertException $$1) {
/* 116 */       this.parent.fail($$1);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void executeWithoutFail(Runnable $$0) {
/*     */     try {
/* 122 */       $$0.run();
/* 123 */     } catch (GameTestAssertException $$1) {
/* 124 */       this.parent.fail($$1);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void tick(long $$0) {
/* 129 */     Iterator<GameTestEvent> $$1 = this.events.iterator();
/* 130 */     while ($$1.hasNext()) {
/* 131 */       GameTestEvent $$2 = $$1.next();
/* 132 */       $$2.assertion.run();
/* 133 */       $$1.remove();
/* 134 */       long $$3 = $$0 - this.lastTick;
/* 135 */       long $$4 = this.lastTick;
/* 136 */       this.lastTick = $$0;
/* 137 */       if ($$2.expectedDelay != null && $$2.expectedDelay.longValue() != $$3) {
/* 138 */         this.parent.fail(new GameTestAssertException("Succeeded in invalid tick: expected " + $$4 + $$2.expectedDelay.longValue() + ", but current tick is " + $$0));
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\gametest\framework\GameTestSequence.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */