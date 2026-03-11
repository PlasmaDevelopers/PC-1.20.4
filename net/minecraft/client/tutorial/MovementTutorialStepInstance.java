/*     */ package net.minecraft.client.tutorial;
/*     */ 
/*     */ import net.minecraft.client.gui.components.toasts.Toast;
/*     */ import net.minecraft.client.gui.components.toasts.TutorialToast;
/*     */ import net.minecraft.client.player.Input;
/*     */ import net.minecraft.network.chat.Component;
/*     */ 
/*     */ 
/*     */ public class MovementTutorialStepInstance
/*     */   implements TutorialStepInstance
/*     */ {
/*     */   private static final int MINIMUM_TIME_MOVED = 40;
/*     */   private static final int MINIMUM_TIME_LOOKED = 40;
/*     */   private static final int MOVE_HINT_DELAY = 100;
/*     */   private static final int LOOK_HINT_DELAY = 20;
/*     */   private static final int INCOMPLETE = -1;
/*  17 */   private static final Component MOVE_TITLE = (Component)Component.translatable("tutorial.move.title", new Object[] { Tutorial.key("forward"), Tutorial.key("left"), Tutorial.key("back"), Tutorial.key("right") });
/*  18 */   private static final Component MOVE_DESCRIPTION = (Component)Component.translatable("tutorial.move.description", new Object[] { Tutorial.key("jump") });
/*     */   
/*  20 */   private static final Component LOOK_TITLE = (Component)Component.translatable("tutorial.look.title");
/*  21 */   private static final Component LOOK_DESCRIPTION = (Component)Component.translatable("tutorial.look.description");
/*     */   
/*     */   private final Tutorial tutorial;
/*     */   private TutorialToast moveToast;
/*     */   private TutorialToast lookToast;
/*     */   private int timeWaiting;
/*     */   private int timeMoved;
/*     */   private int timeLooked;
/*     */   private boolean moved;
/*     */   private boolean turned;
/*  31 */   private int moveCompleted = -1;
/*  32 */   private int lookCompleted = -1;
/*     */   
/*     */   public MovementTutorialStepInstance(Tutorial $$0) {
/*  35 */     this.tutorial = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/*  40 */     this.timeWaiting++;
/*     */     
/*  42 */     if (this.moved) {
/*  43 */       this.timeMoved++;
/*  44 */       this.moved = false;
/*     */     } 
/*     */     
/*  47 */     if (this.turned) {
/*  48 */       this.timeLooked++;
/*  49 */       this.turned = false;
/*     */     } 
/*     */     
/*  52 */     if (this.moveCompleted == -1 && this.timeMoved > 40) {
/*  53 */       if (this.moveToast != null) {
/*  54 */         this.moveToast.hide();
/*  55 */         this.moveToast = null;
/*     */       } 
/*  57 */       this.moveCompleted = this.timeWaiting;
/*     */     } 
/*     */     
/*  60 */     if (this.lookCompleted == -1 && this.timeLooked > 40) {
/*  61 */       if (this.lookToast != null) {
/*  62 */         this.lookToast.hide();
/*  63 */         this.lookToast = null;
/*     */       } 
/*  65 */       this.lookCompleted = this.timeWaiting;
/*     */     } 
/*     */     
/*  68 */     if (this.moveCompleted != -1 && this.lookCompleted != -1) {
/*  69 */       if (this.tutorial.isSurvival()) {
/*  70 */         this.tutorial.setStep(TutorialSteps.FIND_TREE);
/*     */       } else {
/*  72 */         this.tutorial.setStep(TutorialSteps.NONE);
/*     */       } 
/*     */     }
/*     */     
/*  76 */     if (this.moveToast != null) {
/*  77 */       this.moveToast.updateProgress(this.timeMoved / 40.0F);
/*     */     }
/*     */     
/*  80 */     if (this.lookToast != null) {
/*  81 */       this.lookToast.updateProgress(this.timeLooked / 40.0F);
/*     */     }
/*     */     
/*  84 */     if (this.timeWaiting >= 100) {
/*  85 */       if (this.moveCompleted == -1 && this.moveToast == null) {
/*  86 */         this.moveToast = new TutorialToast(TutorialToast.Icons.MOVEMENT_KEYS, MOVE_TITLE, MOVE_DESCRIPTION, true);
/*  87 */         this.tutorial.getMinecraft().getToasts().addToast((Toast)this.moveToast);
/*  88 */       } else if (this.moveCompleted != -1 && this.timeWaiting - this.moveCompleted >= 20 && this.lookCompleted == -1 && this.lookToast == null) {
/*  89 */         this.lookToast = new TutorialToast(TutorialToast.Icons.MOUSE, LOOK_TITLE, LOOK_DESCRIPTION, true);
/*  90 */         this.tutorial.getMinecraft().getToasts().addToast((Toast)this.lookToast);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/*  97 */     if (this.moveToast != null) {
/*  98 */       this.moveToast.hide();
/*  99 */       this.moveToast = null;
/*     */     } 
/* 101 */     if (this.lookToast != null) {
/* 102 */       this.lookToast.hide();
/* 103 */       this.lookToast = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onInput(Input $$0) {
/* 109 */     if ($$0.up || $$0.down || $$0.left || $$0.right || $$0.jumping) {
/* 110 */       this.moved = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onMouse(double $$0, double $$1) {
/* 116 */     if (Math.abs($$0) > 0.01D || Math.abs($$1) > 0.01D)
/* 117 */       this.turned = true; 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\tutorial\MovementTutorialStepInstance.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */