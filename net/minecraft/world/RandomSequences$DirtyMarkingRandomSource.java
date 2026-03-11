/*     */ package net.minecraft.world;
/*     */ 
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.levelgen.PositionalRandomFactory;
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
/*     */ class DirtyMarkingRandomSource
/*     */   implements RandomSource
/*     */ {
/*     */   private final RandomSource random;
/*     */   
/*     */   DirtyMarkingRandomSource(RandomSource $$0) {
/*  40 */     this.random = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public RandomSource fork() {
/*  45 */     RandomSequences.this.setDirty();
/*  46 */     return this.random.fork();
/*     */   }
/*     */ 
/*     */   
/*     */   public PositionalRandomFactory forkPositional() {
/*  51 */     RandomSequences.this.setDirty();
/*  52 */     return this.random.forkPositional();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSeed(long $$0) {
/*  57 */     RandomSequences.this.setDirty();
/*  58 */     this.random.setSeed($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int nextInt() {
/*  63 */     RandomSequences.this.setDirty();
/*  64 */     return this.random.nextInt();
/*     */   }
/*     */ 
/*     */   
/*     */   public int nextInt(int $$0) {
/*  69 */     RandomSequences.this.setDirty();
/*  70 */     return this.random.nextInt($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public long nextLong() {
/*  75 */     RandomSequences.this.setDirty();
/*  76 */     return this.random.nextLong();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean nextBoolean() {
/*  81 */     RandomSequences.this.setDirty();
/*  82 */     return this.random.nextBoolean();
/*     */   }
/*     */ 
/*     */   
/*     */   public float nextFloat() {
/*  87 */     RandomSequences.this.setDirty();
/*  88 */     return this.random.nextFloat();
/*     */   }
/*     */ 
/*     */   
/*     */   public double nextDouble() {
/*  93 */     RandomSequences.this.setDirty();
/*  94 */     return this.random.nextDouble();
/*     */   }
/*     */ 
/*     */   
/*     */   public double nextGaussian() {
/*  99 */     RandomSequences.this.setDirty();
/* 100 */     return this.random.nextGaussian();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object $$0) {
/* 105 */     if (this == $$0) {
/* 106 */       return true;
/*     */     }
/* 108 */     if ($$0 instanceof DirtyMarkingRandomSource) { DirtyMarkingRandomSource $$1 = (DirtyMarkingRandomSource)$$0;
/* 109 */       return this.random.equals($$1.random); }
/*     */     
/* 111 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\RandomSequences$DirtyMarkingRandomSource.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */