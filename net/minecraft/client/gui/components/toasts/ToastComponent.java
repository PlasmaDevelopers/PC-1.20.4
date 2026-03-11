/*     */ package net.minecraft.client.gui.components.toasts;
/*     */ 
/*     */ import com.google.common.collect.Queues;
/*     */ import java.util.ArrayList;
/*     */ import java.util.BitSet;
/*     */ import java.util.Deque;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.util.Mth;
/*     */ 
/*     */ 
/*     */ public class ToastComponent
/*     */ {
/*     */   private static final int SLOT_COUNT = 5;
/*     */   private static final int NO_SPACE = -1;
/*     */   final Minecraft minecraft;
/*  20 */   private final List<ToastInstance<?>> visible = new ArrayList<>();
/*  21 */   private final BitSet occupiedSlots = new BitSet(5);
/*     */   
/*  23 */   private final Deque<Toast> queued = Queues.newArrayDeque();
/*     */   
/*     */   public ToastComponent(Minecraft $$0) {
/*  26 */     this.minecraft = $$0;
/*     */   }
/*     */   
/*     */   public void render(GuiGraphics $$0) {
/*  30 */     if (this.minecraft.options.hideGui) {
/*     */       return;
/*     */     }
/*     */     
/*  34 */     int $$1 = $$0.guiWidth();
/*  35 */     this.visible.removeIf($$2 -> {
/*     */           if ($$2 != null && $$2.render($$0, $$1)) {
/*     */             this.occupiedSlots.clear($$2.index, $$2.index + $$2.slotCount);
/*     */             
/*     */             return true;
/*     */           } 
/*     */           return false;
/*     */         });
/*  43 */     if (!this.queued.isEmpty() && freeSlots() > 0) {
/*  44 */       this.queued.removeIf($$0 -> {
/*     */             int $$1 = $$0.slotCount();
/*     */             int $$2 = findFreeIndex($$1);
/*     */             if ($$2 != -1) {
/*     */               this.visible.add(new ToastInstance($$0, $$2, $$1));
/*     */               this.occupiedSlots.set($$2, $$2 + $$1);
/*     */               return true;
/*     */             } 
/*     */             return false;
/*     */           });
/*     */     }
/*     */   }
/*     */   
/*     */   private int findFreeIndex(int $$0) {
/*  58 */     if (freeSlots() >= $$0) {
/*  59 */       int $$1 = 0;
/*  60 */       for (int $$2 = 0; $$2 < 5; $$2++) {
/*  61 */         if (this.occupiedSlots.get($$2)) {
/*  62 */           $$1 = 0;
/*  63 */         } else if (++$$1 == $$0) {
/*  64 */           return $$2 + 1 - $$1;
/*     */         } 
/*     */       } 
/*     */     } 
/*  68 */     return -1;
/*     */   }
/*     */   
/*     */   private int freeSlots() {
/*  72 */     return 5 - this.occupiedSlots.cardinality();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <T extends Toast> T getToast(Class<? extends T> $$0, Object $$1) {
/*  78 */     for (ToastInstance<?> $$2 : this.visible) {
/*  79 */       if ($$2 != null && $$0.isAssignableFrom($$2.getToast().getClass()) && $$2.getToast().getToken().equals($$1)) {
/*  80 */         return (T)$$2.getToast();
/*     */       }
/*     */     } 
/*  83 */     for (Toast $$3 : this.queued) {
/*  84 */       if ($$0.isAssignableFrom($$3.getClass()) && $$3.getToken().equals($$1)) {
/*  85 */         return (T)$$3;
/*     */       }
/*     */     } 
/*  88 */     return null;
/*     */   }
/*     */   
/*     */   public void clear() {
/*  92 */     this.occupiedSlots.clear();
/*  93 */     this.visible.clear();
/*  94 */     this.queued.clear();
/*     */   }
/*     */   
/*     */   public void addToast(Toast $$0) {
/*  98 */     this.queued.add($$0);
/*     */   }
/*     */   
/*     */   public Minecraft getMinecraft() {
/* 102 */     return this.minecraft;
/*     */   }
/*     */   
/*     */   public double getNotificationDisplayTimeMultiplier() {
/* 106 */     return ((Double)this.minecraft.options.notificationDisplayTime().get()).doubleValue();
/*     */   }
/*     */   
/*     */   private class ToastInstance<T extends Toast>
/*     */   {
/*     */     private static final long ANIMATION_TIME = 600L;
/*     */     private final T toast;
/*     */     final int index;
/*     */     final int slotCount;
/* 115 */     private long animationTime = -1L;
/* 116 */     private long visibleTime = -1L;
/* 117 */     private Toast.Visibility visibility = Toast.Visibility.SHOW;
/*     */     
/*     */     ToastInstance(T $$0, int $$1, int $$2) {
/* 120 */       this.toast = $$0;
/* 121 */       this.index = $$1;
/* 122 */       this.slotCount = $$2;
/*     */     }
/*     */     
/*     */     public T getToast() {
/* 126 */       return this.toast;
/*     */     }
/*     */     
/*     */     private float getVisibility(long $$0) {
/* 130 */       float $$1 = Mth.clamp((float)($$0 - this.animationTime) / 600.0F, 0.0F, 1.0F);
/* 131 */       $$1 *= $$1;
/* 132 */       if (this.visibility == Toast.Visibility.HIDE) {
/* 133 */         return 1.0F - $$1;
/*     */       }
/* 135 */       return $$1;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean render(int $$0, GuiGraphics $$1) {
/* 140 */       long $$2 = Util.getMillis();
/*     */       
/* 142 */       if (this.animationTime == -1L) {
/* 143 */         this.animationTime = $$2;
/* 144 */         this.visibility.playSound(ToastComponent.this.minecraft.getSoundManager());
/*     */       } 
/*     */       
/* 147 */       if (this.visibility == Toast.Visibility.SHOW && $$2 - this.animationTime <= 600L) {
/* 148 */         this.visibleTime = $$2;
/*     */       }
/*     */       
/* 151 */       $$1.pose().pushPose();
/* 152 */       $$1.pose().translate($$0 - this.toast.width() * getVisibility($$2), (this.index * 32), 800.0F);
/* 153 */       Toast.Visibility $$3 = this.toast.render($$1, ToastComponent.this, $$2 - this.visibleTime);
/* 154 */       $$1.pose().popPose();
/*     */       
/* 156 */       if ($$3 != this.visibility) {
/* 157 */         this.animationTime = $$2 - (int)((1.0F - getVisibility($$2)) * 600.0F);
/* 158 */         this.visibility = $$3;
/* 159 */         this.visibility.playSound(ToastComponent.this.minecraft.getSoundManager());
/*     */       } 
/*     */       
/* 162 */       return (this.visibility == Toast.Visibility.HIDE && $$2 - this.animationTime > 600L);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\toasts\ToastComponent.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */