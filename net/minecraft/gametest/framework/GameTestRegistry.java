/*     */ package net.minecraft.gametest.framework;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Comparator;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Function;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.world.level.block.Rotation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GameTestRegistry
/*     */ {
/*  27 */   private static final Collection<TestFunction> TEST_FUNCTIONS = Lists.newArrayList();
/*  28 */   private static final Set<String> TEST_CLASS_NAMES = Sets.newHashSet();
/*  29 */   private static final Map<String, Consumer<ServerLevel>> BEFORE_BATCH_FUNCTIONS = Maps.newHashMap();
/*  30 */   private static final Map<String, Consumer<ServerLevel>> AFTER_BATCH_FUNCTIONS = Maps.newHashMap();
/*  31 */   private static final Collection<TestFunction> LAST_FAILED_TESTS = Sets.newHashSet();
/*     */   
/*     */   public static void register(Class<?> $$0) {
/*  34 */     Arrays.<Method>stream($$0.getDeclaredMethods()).sorted(Comparator.comparing(Method::getName)).forEach(GameTestRegistry::register);
/*     */   }
/*     */   
/*     */   public static void register(Method $$0) {
/*  38 */     String $$1 = $$0.getDeclaringClass().getSimpleName();
/*     */     
/*  40 */     GameTest $$2 = $$0.<GameTest>getAnnotation(GameTest.class);
/*  41 */     if ($$2 != null) {
/*  42 */       TEST_FUNCTIONS.add(turnMethodIntoTestFunction($$0));
/*  43 */       TEST_CLASS_NAMES.add($$1);
/*     */     } 
/*     */     
/*  46 */     GameTestGenerator $$3 = $$0.<GameTestGenerator>getAnnotation(GameTestGenerator.class);
/*  47 */     if ($$3 != null) {
/*  48 */       TEST_FUNCTIONS.addAll(useTestGeneratorMethod($$0));
/*  49 */       TEST_CLASS_NAMES.add($$1);
/*     */     } 
/*     */     
/*  52 */     registerBatchFunction($$0, BeforeBatch.class, BeforeBatch::batch, BEFORE_BATCH_FUNCTIONS);
/*  53 */     registerBatchFunction($$0, AfterBatch.class, AfterBatch::batch, AFTER_BATCH_FUNCTIONS);
/*     */   }
/*     */   
/*     */   private static <T extends java.lang.annotation.Annotation> void registerBatchFunction(Method $$0, Class<T> $$1, Function<T, String> $$2, Map<String, Consumer<ServerLevel>> $$3) {
/*  57 */     T $$4 = $$0.getAnnotation($$1);
/*  58 */     if ($$4 != null) {
/*  59 */       String $$5 = $$2.apply($$4);
/*  60 */       Consumer<ServerLevel> $$6 = (Consumer<ServerLevel>)$$3.putIfAbsent($$5, turnMethodIntoConsumer($$0));
/*  61 */       if ($$6 != null) {
/*  62 */         throw new RuntimeException("Hey, there should only be one " + $$1 + " method per batch. Batch '" + $$5 + "' has more than one!");
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Collection<TestFunction> getTestFunctionsForClassName(String $$0) {
/*  68 */     return (Collection<TestFunction>)TEST_FUNCTIONS.stream()
/*  69 */       .filter($$1 -> isTestFunctionPartOfClass($$1, $$0))
/*  70 */       .collect(Collectors.toList());
/*     */   }
/*     */   
/*     */   public static Collection<TestFunction> getAllTestFunctions() {
/*  74 */     return TEST_FUNCTIONS;
/*     */   }
/*     */   
/*     */   public static Collection<String> getAllTestClassNames() {
/*  78 */     return TEST_CLASS_NAMES;
/*     */   }
/*     */   
/*     */   public static boolean isTestClass(String $$0) {
/*  82 */     return TEST_CLASS_NAMES.contains($$0);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static Consumer<ServerLevel> getBeforeBatchFunction(String $$0) {
/*  87 */     return BEFORE_BATCH_FUNCTIONS.get($$0);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static Consumer<ServerLevel> getAfterBatchFunction(String $$0) {
/*  92 */     return AFTER_BATCH_FUNCTIONS.get($$0);
/*     */   }
/*     */   
/*     */   public static Optional<TestFunction> findTestFunction(String $$0) {
/*  96 */     return getAllTestFunctions().stream()
/*  97 */       .filter($$1 -> $$1.getTestName().equalsIgnoreCase($$0))
/*  98 */       .findFirst();
/*     */   }
/*     */ 
/*     */   
/*     */   public static TestFunction getTestFunction(String $$0) {
/* 103 */     Optional<TestFunction> $$1 = findTestFunction($$0);
/* 104 */     if ($$1.isEmpty()) {
/* 105 */       throw new IllegalArgumentException("Can't find the test function for " + $$0);
/*     */     }
/* 107 */     return $$1.get();
/*     */   }
/*     */   
/*     */   private static Collection<TestFunction> useTestGeneratorMethod(Method $$0) {
/*     */     try {
/* 112 */       Object $$1 = $$0.getDeclaringClass().newInstance();
/* 113 */       return (Collection<TestFunction>)$$0.invoke($$1, new Object[0]);
/* 114 */     } catch (ReflectiveOperationException $$2) {
/* 115 */       throw new RuntimeException($$2);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static TestFunction turnMethodIntoTestFunction(Method $$0) {
/* 120 */     GameTest $$1 = $$0.<GameTest>getAnnotation(GameTest.class);
/*     */     
/* 122 */     String $$2 = $$0.getDeclaringClass().getSimpleName();
/* 123 */     String $$3 = $$2.toLowerCase();
/* 124 */     String $$4 = $$3 + "." + $$3;
/* 125 */     String $$5 = $$1.template().isEmpty() ? $$4 : ($$3 + "." + $$3);
/* 126 */     String $$6 = $$1.batch();
/* 127 */     Rotation $$7 = StructureUtils.getRotationForRotationSteps($$1.rotationSteps());
/*     */     
/* 129 */     return new TestFunction($$6, $$4, $$5, $$7, $$1
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 134 */         .timeoutTicks(), $$1
/* 135 */         .setupTicks(), $$1
/* 136 */         .required(), $$1
/* 137 */         .requiredSuccesses(), $$1
/* 138 */         .attempts(), 
/* 139 */         (Consumer)turnMethodIntoConsumer($$0));
/*     */   }
/*     */   
/*     */   private static Consumer<?> turnMethodIntoConsumer(Method $$0) {
/* 143 */     return $$1 -> {
/*     */         try {
/*     */           Object $$2 = $$0.getDeclaringClass().newInstance();
/*     */           $$0.invoke($$2, new Object[] { $$1 });
/* 147 */         } catch (InvocationTargetException $$3) {
/*     */           if ($$3.getCause() instanceof RuntimeException) {
/*     */             throw (RuntimeException)$$3.getCause();
/*     */           }
/*     */           
/*     */           throw new RuntimeException($$3.getCause());
/* 153 */         } catch (ReflectiveOperationException $$4) {
/*     */           throw new RuntimeException($$4);
/*     */         } 
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isTestFunctionPartOfClass(TestFunction $$0, String $$1) {
/* 163 */     return $$0.getTestName().toLowerCase().startsWith($$1.toLowerCase() + ".");
/*     */   }
/*     */   
/*     */   public static Collection<TestFunction> getLastFailedTests() {
/* 167 */     return LAST_FAILED_TESTS;
/*     */   }
/*     */   
/*     */   public static void rememberFailedTest(TestFunction $$0) {
/* 171 */     LAST_FAILED_TESTS.add($$0);
/*     */   }
/*     */   
/*     */   public static void forgetFailedTests() {
/* 175 */     LAST_FAILED_TESTS.clear();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\gametest\framework\GameTestRegistry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */