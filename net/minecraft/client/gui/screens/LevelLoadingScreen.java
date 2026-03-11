/*     */ package net.minecraft.client.gui.screens;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*     */ import java.util.Objects;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.GameNarrator;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.narration.NarratedElementType;
/*     */ import net.minecraft.client.gui.narration.NarrationElementOutput;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.server.level.progress.StoringChunkProgressListener;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.level.chunk.ChunkStatus;
/*     */ 
/*     */ public class LevelLoadingScreen
/*     */   extends Screen {
/*     */   private static final long NARRATION_DELAY_MS = 2000L;
/*     */   private final StoringChunkProgressListener progressListener;
/*  20 */   private long lastNarration = -1L;
/*     */   private boolean done;
/*     */   
/*     */   public LevelLoadingScreen(StoringChunkProgressListener $$0) {
/*  24 */     super(GameNarrator.NO_TITLE);
/*  25 */     this.progressListener = $$0;
/*     */   }
/*     */   private static final Object2IntMap<ChunkStatus> COLORS;
/*     */   
/*     */   public boolean shouldCloseOnEsc() {
/*  30 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean shouldNarrateNavigation() {
/*  35 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void removed() {
/*  40 */     this.done = true;
/*  41 */     triggerImmediateNarration(true);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateNarratedWidget(NarrationElementOutput $$0) {
/*  46 */     if (this.done) {
/*  47 */       $$0.add(NarratedElementType.TITLE, (Component)Component.translatable("narrator.loading.done"));
/*     */     } else {
/*  49 */       $$0.add(NarratedElementType.TITLE, getFormattedProgress());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private Component getFormattedProgress() {
/*  55 */     return (Component)Component.translatable("loading.progress", new Object[] { Integer.valueOf(Mth.clamp(this.progressListener.getProgress(), 0, 100)) });
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/*  60 */     super.render($$0, $$1, $$2, $$3);
/*     */     
/*  62 */     long $$4 = Util.getMillis();
/*  63 */     if ($$4 - this.lastNarration > 2000L) {
/*  64 */       this.lastNarration = $$4;
/*  65 */       triggerImmediateNarration(true);
/*     */     } 
/*     */     
/*  68 */     int $$5 = this.width / 2;
/*  69 */     int $$6 = this.height / 2;
/*     */     
/*  71 */     int $$7 = 30;
/*     */     
/*  73 */     renderChunks($$0, this.progressListener, $$5, $$6 + 30, 2, 0);
/*  74 */     Objects.requireNonNull(this.font); $$0.drawCenteredString(this.font, getFormattedProgress(), $$5, $$6 - 9 / 2 - 30, 16777215);
/*     */   }
/*     */   
/*     */   public static void renderChunks(GuiGraphics $$0, StoringChunkProgressListener $$1, int $$2, int $$3, int $$4, int $$5) {
/*  78 */     int $$6 = $$4 + $$5;
/*     */     
/*  80 */     int $$7 = $$1.getFullDiameter();
/*  81 */     int $$8 = $$7 * $$6 - $$5;
/*     */     
/*  83 */     int $$9 = $$1.getDiameter();
/*  84 */     int $$10 = $$9 * $$6 - $$5;
/*     */     
/*  86 */     int $$11 = $$2 - $$10 / 2;
/*  87 */     int $$12 = $$3 - $$10 / 2;
/*     */     
/*  89 */     int $$13 = $$8 / 2 + 1;
/*     */     
/*  91 */     int $$14 = -16772609;
/*     */     
/*  93 */     $$0.drawManaged(() -> {
/*     */           if ($$0 != 0) {
/*     */             $$1.fill($$2 - $$3, $$4 - $$3, $$2 - $$3 + 1, $$4 + $$3, -16772609);
/*     */             $$1.fill($$2 + $$3 - 1, $$4 - $$3, $$2 + $$3, $$4 + $$3, -16772609);
/*     */             $$1.fill($$2 - $$3, $$4 - $$3, $$2 + $$3, $$4 - $$3 + 1, -16772609);
/*     */             $$1.fill($$2 - $$3, $$4 + $$3 - 1, $$2 + $$3, $$4 + $$3, -16772609);
/*     */           } 
/*     */           for (int $$11 = 0; $$11 < $$6; $$11++) {
/*     */             for (int $$12 = 0; $$12 < $$6; $$12++) {
/*     */               ChunkStatus $$13 = $$7.getStatus($$11, $$12);
/*     */               int $$14 = $$8 + $$11 * $$5;
/*     */               int $$15 = $$9 + $$12 * $$5;
/*     */               $$1.fill($$14, $$15, $$14 + $$10, $$15 + $$10, COLORS.getInt($$13) | 0xFF000000);
/*     */             } 
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 117 */     COLORS = (Object2IntMap<ChunkStatus>)Util.make(new Object2IntOpenHashMap(), $$0 -> {
/*     */           $$0.defaultReturnValue(0);
/*     */           $$0.put(ChunkStatus.EMPTY, 5526612);
/*     */           $$0.put(ChunkStatus.STRUCTURE_STARTS, 10066329);
/*     */           $$0.put(ChunkStatus.STRUCTURE_REFERENCES, 6250897);
/*     */           $$0.put(ChunkStatus.BIOMES, 8434258);
/*     */           $$0.put(ChunkStatus.NOISE, 13750737);
/*     */           $$0.put(ChunkStatus.SURFACE, 7497737);
/*     */           $$0.put(ChunkStatus.CARVERS, 3159410);
/*     */           $$0.put(ChunkStatus.FEATURES, 2213376);
/*     */           $$0.put(ChunkStatus.INITIALIZE_LIGHT, 13421772);
/*     */           $$0.put(ChunkStatus.LIGHT, 16769184);
/*     */           $$0.put(ChunkStatus.SPAWN, 15884384);
/*     */           $$0.put(ChunkStatus.FULL, 16777215);
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\LevelLoadingScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */