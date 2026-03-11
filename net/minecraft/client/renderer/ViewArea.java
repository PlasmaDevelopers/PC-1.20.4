/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.chunk.SectionRenderDispatcher;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelHeightAccessor;
/*     */ 
/*     */ public class ViewArea {
/*     */   protected final LevelRenderer levelRenderer;
/*     */   protected final Level level;
/*     */   protected int sectionGridSizeY;
/*     */   protected int sectionGridSizeX;
/*     */   protected int sectionGridSizeZ;
/*     */   private int viewDistance;
/*     */   public SectionRenderDispatcher.RenderSection[] sections;
/*     */   
/*     */   public ViewArea(SectionRenderDispatcher $$0, Level $$1, int $$2, LevelRenderer $$3) {
/*  22 */     this.levelRenderer = $$3;
/*  23 */     this.level = $$1;
/*     */     
/*  25 */     setViewDistance($$2);
/*  26 */     createSections($$0);
/*     */   }
/*     */   
/*     */   protected void createSections(SectionRenderDispatcher $$0) {
/*  30 */     if (!Minecraft.getInstance().isSameThread()) {
/*  31 */       throw new IllegalStateException("createSections called from wrong thread: " + Thread.currentThread().getName());
/*     */     }
/*  33 */     int $$1 = this.sectionGridSizeX * this.sectionGridSizeY * this.sectionGridSizeZ;
/*  34 */     this.sections = new SectionRenderDispatcher.RenderSection[$$1];
/*     */     
/*  36 */     for (int $$2 = 0; $$2 < this.sectionGridSizeX; $$2++) {
/*  37 */       for (int $$3 = 0; $$3 < this.sectionGridSizeY; $$3++) {
/*  38 */         for (int $$4 = 0; $$4 < this.sectionGridSizeZ; $$4++) {
/*  39 */           int $$5 = getSectionIndex($$2, $$3, $$4);
/*  40 */           Objects.requireNonNull($$0); this.sections[$$5] = new SectionRenderDispatcher.RenderSection($$0, $$5, $$2 * 16, this.level.getMinBuildHeight() + $$3 * 16, $$4 * 16);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void releaseAllBuffers() {
/*  47 */     for (SectionRenderDispatcher.RenderSection $$0 : this.sections) {
/*  48 */       $$0.releaseBuffers();
/*     */     }
/*     */   }
/*     */   
/*     */   private int getSectionIndex(int $$0, int $$1, int $$2) {
/*  53 */     return ($$2 * this.sectionGridSizeY + $$1) * this.sectionGridSizeX + $$0;
/*     */   }
/*     */   
/*     */   protected void setViewDistance(int $$0) {
/*  57 */     int $$1 = $$0 * 2 + 1;
/*  58 */     this.sectionGridSizeX = $$1;
/*  59 */     this.sectionGridSizeY = this.level.getSectionsCount();
/*  60 */     this.sectionGridSizeZ = $$1;
/*  61 */     this.viewDistance = $$0;
/*     */   }
/*     */   
/*     */   public int getViewDistance() {
/*  65 */     return this.viewDistance;
/*     */   }
/*     */   
/*     */   public LevelHeightAccessor getLevelHeightAccessor() {
/*  69 */     return (LevelHeightAccessor)this.level;
/*     */   }
/*     */   
/*     */   public void repositionCamera(double $$0, double $$1) {
/*  73 */     int $$2 = Mth.ceil($$0);
/*  74 */     int $$3 = Mth.ceil($$1);
/*     */     
/*  76 */     for (int $$4 = 0; $$4 < this.sectionGridSizeX; $$4++) {
/*  77 */       int $$5 = this.sectionGridSizeX * 16;
/*     */       
/*  79 */       int $$6 = $$2 - 8 - $$5 / 2;
/*  80 */       int $$7 = $$6 + Math.floorMod($$4 * 16 - $$6, $$5);
/*     */       
/*  82 */       for (int $$8 = 0; $$8 < this.sectionGridSizeZ; $$8++) {
/*  83 */         int $$9 = this.sectionGridSizeZ * 16;
/*     */         
/*  85 */         int $$10 = $$3 - 8 - $$9 / 2;
/*  86 */         int $$11 = $$10 + Math.floorMod($$8 * 16 - $$10, $$9);
/*     */         
/*  88 */         for (int $$12 = 0; $$12 < this.sectionGridSizeY; $$12++) {
/*  89 */           int $$13 = this.level.getMinBuildHeight() + $$12 * 16;
/*     */           
/*  91 */           SectionRenderDispatcher.RenderSection $$14 = this.sections[getSectionIndex($$4, $$12, $$8)];
/*     */           
/*  93 */           BlockPos $$15 = $$14.getOrigin();
/*  94 */           if ($$7 != $$15.getX() || $$13 != $$15.getY() || $$11 != $$15.getZ()) {
/*  95 */             $$14.setOrigin($$7, $$13, $$11);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setDirty(int $$0, int $$1, int $$2, boolean $$3) {
/* 103 */     int $$4 = Math.floorMod($$0, this.sectionGridSizeX);
/* 104 */     int $$5 = Math.floorMod($$1 - this.level.getMinSection(), this.sectionGridSizeY);
/* 105 */     int $$6 = Math.floorMod($$2, this.sectionGridSizeZ);
/*     */     
/* 107 */     SectionRenderDispatcher.RenderSection $$7 = this.sections[getSectionIndex($$4, $$5, $$6)];
/* 108 */     $$7.setDirty($$3);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected SectionRenderDispatcher.RenderSection getRenderSectionAt(BlockPos $$0) {
/* 113 */     int $$1 = Mth.floorDiv($$0.getY() - this.level.getMinBuildHeight(), 16);
/* 114 */     if ($$1 < 0 || $$1 >= this.sectionGridSizeY) {
/* 115 */       return null;
/*     */     }
/* 117 */     int $$2 = Mth.positiveModulo(Mth.floorDiv($$0.getX(), 16), this.sectionGridSizeX);
/* 118 */     int $$3 = Mth.positiveModulo(Mth.floorDiv($$0.getZ(), 16), this.sectionGridSizeZ);
/*     */     
/* 120 */     return this.sections[getSectionIndex($$2, $$1, $$3)];
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\ViewArea.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */