/*     */ package net.minecraft.client.tutorial;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.Options;
/*     */ import net.minecraft.client.gui.components.toasts.Toast;
/*     */ import net.minecraft.client.gui.components.toasts.TutorialToast;
/*     */ import net.minecraft.client.multiplayer.ClientLevel;
/*     */ import net.minecraft.client.player.Input;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.world.inventory.ClickAction;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.GameType;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.phys.HitResult;
/*     */ 
/*     */ public class Tutorial {
/*     */   private final Minecraft minecraft;
/*     */   @Nullable
/*     */   private TutorialStepInstance instance;
/*  25 */   private final List<TimedToast> timedToasts = Lists.newArrayList();
/*     */   private final BundleTutorial bundleTutorial;
/*     */   
/*     */   public Tutorial(Minecraft $$0, Options $$1) {
/*  29 */     this.minecraft = $$0;
/*  30 */     this.bundleTutorial = new BundleTutorial(this, $$1);
/*     */   }
/*     */   
/*     */   public void onInput(Input $$0) {
/*  34 */     if (this.instance != null) {
/*  35 */       this.instance.onInput($$0);
/*     */     }
/*     */   }
/*     */   
/*     */   public void onMouse(double $$0, double $$1) {
/*  40 */     if (this.instance != null) {
/*  41 */       this.instance.onMouse($$0, $$1);
/*     */     }
/*     */   }
/*     */   
/*     */   public void onLookAt(@Nullable ClientLevel $$0, @Nullable HitResult $$1) {
/*  46 */     if (this.instance != null && $$1 != null && $$0 != null) {
/*  47 */       this.instance.onLookAt($$0, $$1);
/*     */     }
/*     */   }
/*     */   
/*     */   public void onDestroyBlock(ClientLevel $$0, BlockPos $$1, BlockState $$2, float $$3) {
/*  52 */     if (this.instance != null) {
/*  53 */       this.instance.onDestroyBlock($$0, $$1, $$2, $$3);
/*     */     }
/*     */   }
/*     */   
/*     */   public void onOpenInventory() {
/*  58 */     if (this.instance != null) {
/*  59 */       this.instance.onOpenInventory();
/*     */     }
/*     */   }
/*     */   
/*     */   public void onGetItem(ItemStack $$0) {
/*  64 */     if (this.instance != null) {
/*  65 */       this.instance.onGetItem($$0);
/*     */     }
/*     */   }
/*     */   
/*     */   public void stop() {
/*  70 */     if (this.instance == null) {
/*     */       return;
/*     */     }
/*  73 */     this.instance.clear();
/*  74 */     this.instance = null;
/*     */   }
/*     */   
/*     */   public void start() {
/*  78 */     if (this.instance != null) {
/*  79 */       stop();
/*     */     }
/*  81 */     this.instance = this.minecraft.options.tutorialStep.create(this);
/*     */   }
/*     */   
/*     */   public void addTimedToast(TutorialToast $$0, int $$1) {
/*  85 */     this.timedToasts.add(new TimedToast($$0, $$1));
/*  86 */     this.minecraft.getToasts().addToast((Toast)$$0);
/*     */   }
/*     */   
/*     */   public void removeTimedToast(TutorialToast $$0) {
/*  90 */     this.timedToasts.removeIf($$1 -> ($$1.toast == $$0));
/*  91 */     $$0.hide();
/*     */   }
/*     */   
/*     */   public void tick() {
/*  95 */     this.timedToasts.removeIf(TimedToast::updateProgress);
/*     */     
/*  97 */     if (this.instance != null) {
/*  98 */       if (this.minecraft.level != null) {
/*  99 */         this.instance.tick();
/*     */       } else {
/* 101 */         stop();
/*     */       } 
/* 103 */     } else if (this.minecraft.level != null) {
/* 104 */       start();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setStep(TutorialSteps $$0) {
/* 109 */     this.minecraft.options.tutorialStep = $$0;
/* 110 */     this.minecraft.options.save();
/* 111 */     if (this.instance != null) {
/* 112 */       this.instance.clear();
/* 113 */       this.instance = $$0.create(this);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Minecraft getMinecraft() {
/* 118 */     return this.minecraft;
/*     */   }
/*     */   
/*     */   public boolean isSurvival() {
/* 122 */     if (this.minecraft.gameMode == null) {
/* 123 */       return false;
/*     */     }
/* 125 */     return (this.minecraft.gameMode.getPlayerMode() == GameType.SURVIVAL);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Component key(String $$0) {
/* 130 */     return (Component)Component.keybind("key." + $$0).withStyle(ChatFormatting.BOLD);
/*     */   }
/*     */   
/*     */   public void onInventoryAction(ItemStack $$0, ItemStack $$1, ClickAction $$2) {
/* 134 */     this.bundleTutorial.onInventoryAction($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   private static final class TimedToast {
/*     */     final TutorialToast toast;
/*     */     private final int durationTicks;
/*     */     private int progress;
/*     */     
/*     */     TimedToast(TutorialToast $$0, int $$1) {
/* 143 */       this.toast = $$0;
/* 144 */       this.durationTicks = $$1;
/*     */     }
/*     */     
/*     */     private boolean updateProgress() {
/* 148 */       this.toast.updateProgress(Math.min(++this.progress / this.durationTicks, 1.0F));
/* 149 */       if (this.progress > this.durationTicks) {
/* 150 */         this.toast.hide();
/* 151 */         return true;
/*     */       } 
/* 153 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\tutorial\Tutorial.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */