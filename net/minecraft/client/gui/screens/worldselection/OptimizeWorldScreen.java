/*     */ package net.minecraft.client.gui.screens.worldselection;
/*     */ import com.mojang.datafixers.DataFixer;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap;
/*     */ import java.util.Objects;
/*     */ import java.util.function.ToIntFunction;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.core.RegistryAccess;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.server.WorldStem;
/*     */ import net.minecraft.server.packs.repository.PackRepository;
/*     */ import net.minecraft.server.packs.repository.ServerPacksSource;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.worldupdate.WorldUpgrader;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelSettings;
/*     */ import net.minecraft.world.level.dimension.LevelStem;
/*     */ import net.minecraft.world.level.storage.LevelStorageSource;
/*     */ import net.minecraft.world.level.storage.WorldData;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class OptimizeWorldScreen extends Screen {
/*  34 */   private static final Logger LOGGER = LogUtils.getLogger(); private static final ToIntFunction<ResourceKey<Level>> DIMENSION_COLORS; private final BooleanConsumer callback; private final WorldUpgrader upgrader;
/*     */   static {
/*  36 */     DIMENSION_COLORS = (ToIntFunction<ResourceKey<Level>>)Util.make(new Reference2IntOpenHashMap(), $$0 -> {
/*     */           $$0.put(Level.OVERWORLD, -13408734);
/*     */           $$0.put(Level.NETHER, -10075085);
/*     */           $$0.put(Level.END, -8943531);
/*     */           $$0.defaultReturnValue(-2236963);
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static OptimizeWorldScreen create(Minecraft $$0, BooleanConsumer $$1, DataFixer $$2, LevelStorageSource.LevelStorageAccess $$3, boolean $$4) {
/*     */     
/*  49 */     try { WorldOpenFlows $$5 = $$0.createWorldOpenFlows();
/*  50 */       PackRepository $$6 = ServerPacksSource.createPackRepository($$3);
/*  51 */       WorldStem $$7 = $$5.loadWorldStem($$3.getDataTag(), false, $$6); 
/*  52 */       try { WorldData $$8 = $$7.worldData();
/*  53 */         RegistryAccess.Frozen $$9 = $$7.registries().compositeAccess();
/*  54 */         $$3.saveDataTag((RegistryAccess)$$9, $$8);
/*  55 */         OptimizeWorldScreen optimizeWorldScreen = new OptimizeWorldScreen($$1, $$2, $$3, $$8.getLevelSettings(), $$4, $$9.registryOrThrow(Registries.LEVEL_STEM));
/*  56 */         if ($$7 != null) $$7.close();  return optimizeWorldScreen; } catch (Throwable throwable) { if ($$7 != null)
/*  57 */           try { $$7.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Exception $$10)
/*  58 */     { LOGGER.warn("Failed to load datapacks, can't optimize world", $$10);
/*  59 */       return null; }
/*     */   
/*     */   }
/*     */   
/*     */   private OptimizeWorldScreen(BooleanConsumer $$0, DataFixer $$1, LevelStorageSource.LevelStorageAccess $$2, LevelSettings $$3, boolean $$4, Registry<LevelStem> $$5) {
/*  64 */     super((Component)Component.translatable("optimizeWorld.title", new Object[] { $$3.levelName() }));
/*  65 */     this.callback = $$0;
/*  66 */     this.upgrader = new WorldUpgrader($$2, $$1, $$5, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init() {
/*  71 */     super.init();
/*     */     
/*  73 */     addRenderableWidget((GuiEventListener)Button.builder(CommonComponents.GUI_CANCEL, $$0 -> {
/*     */             this.upgrader.cancel();
/*     */             this.callback.accept(false);
/*  76 */           }).bounds(this.width / 2 - 100, this.height / 4 + 150, 200, 20).build());
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/*  81 */     if (this.upgrader.isFinished()) {
/*  82 */       this.callback.accept(true);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClose() {
/*  88 */     this.callback.accept(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removed() {
/*  93 */     this.upgrader.cancel();
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/*  98 */     super.render($$0, $$1, $$2, $$3);
/*     */     
/* 100 */     $$0.drawCenteredString(this.font, this.title, this.width / 2, 20, 16777215);
/*     */     
/* 102 */     int $$4 = this.width / 2 - 150;
/* 103 */     int $$5 = this.width / 2 + 150;
/* 104 */     int $$6 = this.height / 4 + 100;
/* 105 */     int $$7 = $$6 + 10;
/*     */     
/* 107 */     Objects.requireNonNull(this.font); $$0.drawCenteredString(this.font, this.upgrader.getStatus(), this.width / 2, $$6 - 9 - 2, 10526880);
/*     */     
/* 109 */     if (this.upgrader.getTotalChunks() > 0) {
/* 110 */       $$0.fill($$4 - 1, $$6 - 1, $$5 + 1, $$7 + 1, -16777216);
/*     */       
/* 112 */       $$0.drawString(this.font, (Component)Component.translatable("optimizeWorld.info.converted", new Object[] { Integer.valueOf(this.upgrader.getConverted()) }), $$4, 40, 10526880);
/* 113 */       Objects.requireNonNull(this.font); $$0.drawString(this.font, (Component)Component.translatable("optimizeWorld.info.skipped", new Object[] { Integer.valueOf(this.upgrader.getSkipped()) }), $$4, 40 + 9 + 3, 10526880);
/* 114 */       Objects.requireNonNull(this.font); $$0.drawString(this.font, (Component)Component.translatable("optimizeWorld.info.total", new Object[] { Integer.valueOf(this.upgrader.getTotalChunks()) }), $$4, 40 + (9 + 3) * 2, 10526880);
/*     */       
/* 116 */       int $$8 = 0;
/* 117 */       for (ResourceKey<Level> $$9 : (Iterable<ResourceKey<Level>>)this.upgrader.levels()) {
/* 118 */         int $$10 = Mth.floor(this.upgrader.dimensionProgress($$9) * ($$5 - $$4));
/* 119 */         $$0.fill($$4 + $$8, $$6, $$4 + $$8 + $$10, $$7, DIMENSION_COLORS.applyAsInt($$9));
/* 120 */         $$8 += $$10;
/*     */       } 
/*     */       
/* 123 */       int $$11 = this.upgrader.getConverted() + this.upgrader.getSkipped();
/*     */       
/* 125 */       MutableComponent mutableComponent1 = Component.translatable("optimizeWorld.progress.counter", new Object[] { Integer.valueOf($$11), Integer.valueOf(this.upgrader.getTotalChunks()) });
/* 126 */       MutableComponent mutableComponent2 = Component.translatable("optimizeWorld.progress.percentage", new Object[] { Integer.valueOf(Mth.floor(this.upgrader.getProgress() * 100.0F)) });
/* 127 */       Objects.requireNonNull(this.font); $$0.drawCenteredString(this.font, (Component)mutableComponent1, this.width / 2, $$6 + 2 * 9 + 2, 10526880);
/* 128 */       Objects.requireNonNull(this.font); $$0.drawCenteredString(this.font, (Component)mutableComponent2, this.width / 2, $$6 + ($$7 - $$6) / 2 - 9 / 2, 10526880);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\worldselection\OptimizeWorldScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */