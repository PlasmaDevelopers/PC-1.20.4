/*     */ package com.mojang.blaze3d.pipeline;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
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
/*     */ public class RenderPipeline
/*     */ {
/*  20 */   private final List<ConcurrentLinkedQueue<RenderCall>> renderCalls = (List<ConcurrentLinkedQueue<RenderCall>>)ImmutableList.of(new ConcurrentLinkedQueue(), new ConcurrentLinkedQueue(), new ConcurrentLinkedQueue(), new ConcurrentLinkedQueue());
/*     */ 
/*     */ 
/*     */   
/*     */   private volatile boolean isRecording;
/*     */ 
/*     */   
/*  27 */   private volatile int recordingBuffer = this.processedBuffer = this.renderingBuffer + 1;
/*     */   private volatile boolean isProcessing;
/*     */   
/*     */   public boolean canBeginRecording() {
/*  31 */     return (!this.isRecording && this.recordingBuffer == this.processedBuffer);
/*     */   }
/*     */   private volatile int processedBuffer; private volatile int renderingBuffer;
/*     */   public boolean beginRecording() {
/*  35 */     if (this.isRecording) {
/*  36 */       throw new RuntimeException("ALREADY RECORDING !!!");
/*     */     }
/*     */     
/*  39 */     if (canBeginRecording()) {
/*  40 */       this.recordingBuffer = (this.processedBuffer + 1) % this.renderCalls.size();
/*  41 */       this.isRecording = true;
/*  42 */       return true;
/*     */     } 
/*  44 */     return false;
/*     */   }
/*     */   
/*     */   public void recordRenderCall(RenderCall $$0) {
/*  48 */     if (!this.isRecording) {
/*  49 */       throw new RuntimeException("NOT RECORDING !!!");
/*     */     }
/*     */     
/*  52 */     ConcurrentLinkedQueue<RenderCall> $$1 = getRecordingQueue();
/*  53 */     $$1.add($$0);
/*     */   }
/*     */   
/*     */   public void endRecording() {
/*  57 */     if (this.isRecording) {
/*  58 */       this.isRecording = false;
/*     */     } else {
/*  60 */       throw new RuntimeException("NOT RECORDING !!!");
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean canBeginProcessing() {
/*  65 */     return (!this.isProcessing && this.recordingBuffer != this.processedBuffer);
/*     */   }
/*     */   
/*     */   public boolean beginProcessing() {
/*  69 */     if (this.isProcessing) {
/*  70 */       throw new RuntimeException("ALREADY PROCESSING !!!");
/*     */     }
/*  72 */     if (canBeginProcessing()) {
/*  73 */       this.isProcessing = true;
/*  74 */       return true;
/*     */     } 
/*  76 */     return false;
/*     */   }
/*     */   
/*     */   public void processRecordedQueue() {
/*  80 */     if (!this.isProcessing) {
/*  81 */       throw new RuntimeException("NOT PROCESSING !!!");
/*     */     }
/*     */   }
/*     */   
/*     */   public void endProcessing() {
/*  86 */     if (this.isProcessing) {
/*  87 */       this.isProcessing = false;
/*  88 */       this.renderingBuffer = this.processedBuffer;
/*  89 */       this.processedBuffer = this.recordingBuffer;
/*     */     } else {
/*  91 */       throw new RuntimeException("NOT PROCESSING !!!");
/*     */     } 
/*     */   }
/*     */   
/*     */   public ConcurrentLinkedQueue<RenderCall> startRendering() {
/*  96 */     return this.renderCalls.get(this.renderingBuffer);
/*     */   }
/*     */   
/*     */   public ConcurrentLinkedQueue<RenderCall> getRecordingQueue() {
/* 100 */     return this.renderCalls.get(this.recordingBuffer);
/*     */   }
/*     */   
/*     */   public ConcurrentLinkedQueue<RenderCall> getProcessedQueue() {
/* 104 */     return this.renderCalls.get(this.processedBuffer);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\pipeline\RenderPipeline.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */