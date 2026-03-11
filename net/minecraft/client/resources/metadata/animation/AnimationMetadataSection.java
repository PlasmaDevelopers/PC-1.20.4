/*    */ package net.minecraft.client.resources.metadata.animation;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ 
/*    */ public class AnimationMetadataSection
/*    */ {
/*  8 */   public static final AnimationMetadataSectionSerializer SERIALIZER = new AnimationMetadataSectionSerializer();
/*    */   
/*    */   public static final String SECTION_NAME = "animation";
/*    */   public static final int DEFAULT_FRAME_TIME = 1;
/*    */   public static final int UNKNOWN_SIZE = -1;
/*    */   
/* 14 */   public static final AnimationMetadataSection EMPTY = new AnimationMetadataSection(Lists.newArrayList(), -1, -1, 1, false)
/*    */     {
/*    */       public FrameSize calculateFrameSize(int $$0, int $$1) {
/* 17 */         return new FrameSize($$0, $$1);
/*    */       }
/*    */     };
/*    */ 
/*    */   
/*    */   private final List<AnimationFrame> frames;
/*    */   
/*    */   private final int frameWidth;
/*    */   
/*    */   private final int frameHeight;
/*    */   
/*    */   public AnimationMetadataSection(List<AnimationFrame> $$0, int $$1, int $$2, int $$3, boolean $$4) {
/* 29 */     this.frames = $$0;
/* 30 */     this.frameWidth = $$1;
/* 31 */     this.frameHeight = $$2;
/* 32 */     this.defaultFrameTime = $$3;
/* 33 */     this.interpolatedFrames = $$4;
/*    */   } private final int defaultFrameTime; private final boolean interpolatedFrames; @FunctionalInterface
/*    */   public static interface FrameOutput {
/*    */     void accept(int param1Int1, int param1Int2); } public FrameSize calculateFrameSize(int $$0, int $$1) {
/* 37 */     if (this.frameWidth != -1) {
/* 38 */       if (this.frameHeight != -1) {
/* 39 */         return new FrameSize(this.frameWidth, this.frameHeight);
/*    */       }
/*    */ 
/*    */       
/* 43 */       return new FrameSize(this.frameWidth, $$1);
/*    */     } 
/*    */     
/* 46 */     if (this.frameHeight != -1)
/*    */     {
/* 48 */       return new FrameSize($$0, this.frameHeight);
/*    */     }
/*    */ 
/*    */     
/* 52 */     int $$2 = Math.min($$0, $$1);
/* 53 */     return new FrameSize($$2, $$2);
/*    */   }
/*    */   
/*    */   public int getDefaultFrameTime() {
/* 57 */     return this.defaultFrameTime;
/*    */   }
/*    */   
/*    */   public boolean isInterpolatedFrames() {
/* 61 */     return this.interpolatedFrames;
/*    */   }
/*    */   
/*    */   public void forEachFrame(FrameOutput $$0) {
/* 65 */     for (AnimationFrame $$1 : this.frames)
/* 66 */       $$0.accept($$1.getIndex(), $$1.getTime(this.defaultFrameTime)); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\metadata\animation\AnimationMetadataSection.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */