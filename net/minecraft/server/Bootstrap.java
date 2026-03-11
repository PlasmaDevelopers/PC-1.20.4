/*     */ package net.minecraft.server;
/*     */ 
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.PrintStream;
/*     */ import java.time.Duration;
/*     */ import java.time.Instant;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.commands.Commands;
/*     */ import net.minecraft.commands.arguments.selector.options.EntitySelectorOptions;
/*     */ import net.minecraft.core.cauldron.CauldronInteraction;
/*     */ import net.minecraft.core.dispenser.DispenseItemBehavior;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.locale.Language;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.effect.MobEffect;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.ai.attributes.Attribute;
/*     */ import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
/*     */ import net.minecraft.world.item.CreativeModeTabs;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.alchemy.PotionBrewing;
/*     */ import net.minecraft.world.item.enchantment.Enchantment;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.ComposterBlock;
/*     */ import net.minecraft.world.level.block.FireBlock;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class Bootstrap {
/*  35 */   public static final PrintStream STDOUT = System.out;
/*     */   
/*     */   private static volatile boolean isBootstrapped;
/*  38 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*  40 */   public static final AtomicLong bootstrapDuration = new AtomicLong(-1L);
/*     */   
/*     */   public static void bootStrap() {
/*  43 */     if (isBootstrapped) {
/*     */       return;
/*     */     }
/*  46 */     isBootstrapped = true;
/*     */     
/*  48 */     Instant $$0 = Instant.now();
/*     */     
/*  50 */     if (BuiltInRegistries.REGISTRY.keySet().isEmpty()) {
/*  51 */       throw new IllegalStateException("Unable to load registries");
/*     */     }
/*     */     
/*  54 */     FireBlock.bootStrap();
/*  55 */     ComposterBlock.bootStrap();
/*     */     
/*  57 */     if (EntityType.getKey(EntityType.PLAYER) == null) {
/*  58 */       throw new IllegalStateException("Failed loading EntityTypes");
/*     */     }
/*     */     
/*  61 */     PotionBrewing.bootStrap();
/*     */     
/*  63 */     EntitySelectorOptions.bootStrap();
/*     */     
/*  65 */     DispenseItemBehavior.bootStrap();
/*     */     
/*  67 */     CauldronInteraction.bootStrap();
/*     */     
/*  69 */     BuiltInRegistries.bootStrap();
/*     */     
/*  71 */     CreativeModeTabs.validate();
/*     */     
/*  73 */     wrapStreams();
/*     */     
/*  75 */     bootstrapDuration.set(Duration.between($$0, Instant.now()).toMillis());
/*     */   }
/*     */   
/*     */   private static <T> void checkTranslations(Iterable<T> $$0, Function<T, String> $$1, Set<String> $$2) {
/*  79 */     Language $$3 = Language.getInstance();
/*  80 */     $$0.forEach($$3 -> {
/*     */           String $$4 = $$0.apply($$3);
/*     */           if (!$$1.has($$4)) {
/*     */             $$2.add($$4);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private static void checkGameruleTranslations(final Set<String> missing) {
/*  89 */     final Language language = Language.getInstance();
/*  90 */     GameRules.visitGameRuleTypes(new GameRules.GameRuleTypeVisitor()
/*     */         {
/*     */           public <T extends GameRules.Value<T>> void visit(GameRules.Key<T> $$0, GameRules.Type<T> $$1) {
/*  93 */             if (!language.has($$0.getDescriptionId())) {
/*  94 */               missing.add($$0.getId());
/*     */             }
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public static Set<String> getMissingTranslations() {
/* 101 */     Set<String> $$0 = new TreeSet<>();
/* 102 */     checkTranslations((Iterable<?>)BuiltInRegistries.ATTRIBUTE, Attribute::getDescriptionId, $$0);
/* 103 */     checkTranslations((Iterable<?>)BuiltInRegistries.ENTITY_TYPE, EntityType::getDescriptionId, $$0);
/* 104 */     checkTranslations((Iterable<?>)BuiltInRegistries.MOB_EFFECT, MobEffect::getDescriptionId, $$0);
/* 105 */     checkTranslations((Iterable<?>)BuiltInRegistries.ITEM, Item::getDescriptionId, $$0);
/* 106 */     checkTranslations((Iterable<?>)BuiltInRegistries.ENCHANTMENT, Enchantment::getDescriptionId, $$0);
/* 107 */     checkTranslations((Iterable<?>)BuiltInRegistries.BLOCK, Block::getDescriptionId, $$0);
/* 108 */     checkTranslations((Iterable<?>)BuiltInRegistries.CUSTOM_STAT, $$0 -> "stat." + $$0.toString().replace(':', '.'), $$0);
/*     */     
/* 110 */     checkGameruleTranslations($$0);
/* 111 */     return $$0;
/*     */   }
/*     */   
/*     */   public static void checkBootstrapCalled(Supplier<String> $$0) {
/* 115 */     if (!isBootstrapped) {
/* 116 */       throw createBootstrapException($$0);
/*     */     }
/*     */   }
/*     */   
/*     */   private static RuntimeException createBootstrapException(Supplier<String> $$0) {
/*     */     try {
/* 122 */       String $$1 = $$0.get();
/* 123 */       return new IllegalArgumentException("Not bootstrapped (called from " + $$1 + ")");
/* 124 */     } catch (Exception $$2) {
/* 125 */       RuntimeException $$3 = new IllegalArgumentException("Not bootstrapped (failed to resolve location)");
/* 126 */       $$3.addSuppressed($$2);
/* 127 */       return $$3;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void validate() {
/* 132 */     checkBootstrapCalled(() -> "validate");
/*     */     
/* 134 */     if (SharedConstants.IS_RUNNING_IN_IDE) {
/* 135 */       getMissingTranslations().forEach($$0 -> LOGGER.error("Missing translations: {}", $$0));
/* 136 */       Commands.validate();
/*     */     } 
/*     */     
/* 139 */     DefaultAttributes.validate();
/*     */   }
/*     */   
/*     */   private static void wrapStreams() {
/* 143 */     if (LOGGER.isDebugEnabled()) {
/* 144 */       System.setErr(new DebugLoggedPrintStream("STDERR", System.err));
/* 145 */       System.setOut(new DebugLoggedPrintStream("STDOUT", STDOUT));
/*     */     } else {
/* 147 */       System.setErr(new LoggedPrintStream("STDERR", System.err));
/* 148 */       System.setOut(new LoggedPrintStream("STDOUT", STDOUT));
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void realStdoutPrintln(String $$0) {
/* 153 */     STDOUT.println($$0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\Bootstrap.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */