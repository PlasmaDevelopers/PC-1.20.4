/*     */ package net.minecraft.gametest.framework;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Collection;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MultipleTestTracker
/*     */ {
/*     */   private static final char NOT_STARTED_TEST_CHAR = ' ';
/*     */   private static final char ONGOING_TEST_CHAR = '_';
/*     */   private static final char SUCCESSFUL_TEST_CHAR = '+';
/*     */   private static final char FAILED_OPTIONAL_TEST_CHAR = 'x';
/*     */   private static final char FAILED_REQUIRED_TEST_CHAR = 'X';
/*  19 */   private final Collection<GameTestInfo> tests = Lists.newArrayList();
/*     */   
/*     */   @Nullable
/*  22 */   private final Collection<GameTestListener> listeners = Lists.newArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MultipleTestTracker(Collection<GameTestInfo> $$0) {
/*  28 */     this.tests.addAll($$0);
/*     */   }
/*     */   
/*     */   public void addTestToTrack(GameTestInfo $$0) {
/*  32 */     this.tests.add($$0);
/*  33 */     Objects.requireNonNull($$0); this.listeners.forEach($$0::addListener);
/*     */   }
/*     */   
/*     */   public void addListener(GameTestListener $$0) {
/*  37 */     this.listeners.add($$0);
/*  38 */     this.tests.forEach($$1 -> $$1.addListener($$0));
/*     */   }
/*     */   
/*     */   public void addFailureListener(final Consumer<GameTestInfo> listener) {
/*  42 */     addListener(new GameTestListener()
/*     */         {
/*     */           public void testStructureLoaded(GameTestInfo $$0) {}
/*     */ 
/*     */ 
/*     */           
/*     */           public void testPassed(GameTestInfo $$0) {}
/*     */ 
/*     */ 
/*     */           
/*     */           public void testFailed(GameTestInfo $$0) {
/*  53 */             listener.accept($$0);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public int getFailedRequiredCount() {
/*  59 */     return (int)this.tests.stream().filter(GameTestInfo::hasFailed).filter(GameTestInfo::isRequired).count();
/*     */   }
/*     */   
/*     */   public int getFailedOptionalCount() {
/*  63 */     return (int)this.tests.stream().filter(GameTestInfo::hasFailed).filter(GameTestInfo::isOptional).count();
/*     */   }
/*     */   
/*     */   public int getDoneCount() {
/*  67 */     return (int)this.tests.stream().filter(GameTestInfo::isDone).count();
/*     */   }
/*     */   
/*     */   public boolean hasFailedRequired() {
/*  71 */     return (getFailedRequiredCount() > 0);
/*     */   }
/*     */   
/*     */   public boolean hasFailedOptional() {
/*  75 */     return (getFailedOptionalCount() > 0);
/*     */   }
/*     */   
/*     */   public Collection<GameTestInfo> getFailedRequired() {
/*  79 */     return (Collection<GameTestInfo>)this.tests.stream().filter(GameTestInfo::hasFailed).filter(GameTestInfo::isRequired).collect(Collectors.toList());
/*     */   }
/*     */   
/*     */   public Collection<GameTestInfo> getFailedOptional() {
/*  83 */     return (Collection<GameTestInfo>)this.tests.stream().filter(GameTestInfo::hasFailed).filter(GameTestInfo::isOptional).collect(Collectors.toList());
/*     */   }
/*     */   
/*     */   public int getTotalCount() {
/*  87 */     return this.tests.size();
/*     */   }
/*     */   
/*     */   public boolean isDone() {
/*  91 */     return (getDoneCount() == getTotalCount());
/*     */   }
/*     */   
/*     */   public String getProgressBar() {
/*  95 */     StringBuffer $$0 = new StringBuffer();
/*  96 */     $$0.append('[');
/*  97 */     this.tests.forEach($$1 -> {
/*     */           if (!$$1.hasStarted()) {
/*     */             $$0.append(' ');
/*     */           } else if ($$1.hasSucceeded()) {
/*     */             $$0.append('+');
/*     */           } else if ($$1.hasFailed()) {
/*     */             $$0.append($$1.isRequired() ? 88 : 120);
/*     */           } else {
/*     */             $$0.append('_');
/*     */           } 
/*     */         });
/* 108 */     $$0.append(']');
/* 109 */     return $$0.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 114 */     return getProgressBar();
/*     */   }
/*     */   
/*     */   public MultipleTestTracker() {}
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\gametest\framework\MultipleTestTracker.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */