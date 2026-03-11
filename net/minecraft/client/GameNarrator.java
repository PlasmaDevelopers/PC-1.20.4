/*     */ package net.minecraft.client;
/*     */ 
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.text2speech.Narrator;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.client.gui.components.toasts.SystemToast;
/*     */ import net.minecraft.client.gui.components.toasts.ToastComponent;
/*     */ import net.minecraft.client.main.SilentInitException;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import org.lwjgl.util.tinyfd.TinyFileDialogs;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class GameNarrator {
/*  15 */   public static final Component NO_TITLE = CommonComponents.EMPTY;
/*  16 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private final Minecraft minecraft;
/*  19 */   private final Narrator narrator = Narrator.getNarrator();
/*     */   
/*     */   public GameNarrator(Minecraft $$0) {
/*  22 */     this.minecraft = $$0;
/*     */   }
/*     */   
/*     */   public void sayChat(Component $$0) {
/*  26 */     if (getStatus().shouldNarrateChat()) {
/*  27 */       String $$1 = $$0.getString();
/*  28 */       logNarratedMessage($$1);
/*  29 */       this.narrator.say($$1, false);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void say(Component $$0) {
/*  34 */     String $$1 = $$0.getString();
/*  35 */     if (getStatus().shouldNarrateSystem() && !$$1.isEmpty()) {
/*  36 */       logNarratedMessage($$1);
/*  37 */       this.narrator.say($$1, false);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void sayNow(Component $$0) {
/*  42 */     sayNow($$0.getString());
/*     */   }
/*     */   
/*     */   public void sayNow(String $$0) {
/*  46 */     if (getStatus().shouldNarrateSystem() && !$$0.isEmpty()) {
/*  47 */       logNarratedMessage($$0);
/*  48 */       if (this.narrator.active()) {
/*  49 */         this.narrator.clear();
/*  50 */         this.narrator.say($$0, true);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private NarratorStatus getStatus() {
/*  56 */     return this.minecraft.options.narrator().get();
/*     */   }
/*     */   
/*     */   private void logNarratedMessage(String $$0) {
/*  60 */     if (SharedConstants.IS_RUNNING_IN_IDE) {
/*  61 */       LOGGER.debug("Narrating: {}", $$0.replaceAll("\n", "\\\\n"));
/*     */     }
/*     */   }
/*     */   
/*     */   public void updateNarratorStatus(NarratorStatus $$0) {
/*  66 */     clear();
/*     */     
/*  68 */     this.narrator.say(Component.translatable("options.narrator").append(" : ").append($$0.getName()).getString(), true);
/*     */     
/*  70 */     ToastComponent $$1 = Minecraft.getInstance().getToasts();
/*  71 */     if (this.narrator.active()) {
/*  72 */       if ($$0 == NarratorStatus.OFF) {
/*  73 */         SystemToast.addOrUpdate($$1, SystemToast.SystemToastId.NARRATOR_TOGGLE, (Component)Component.translatable("narrator.toast.disabled"), null);
/*     */       } else {
/*  75 */         SystemToast.addOrUpdate($$1, SystemToast.SystemToastId.NARRATOR_TOGGLE, (Component)Component.translatable("narrator.toast.enabled"), $$0.getName());
/*     */       } 
/*     */     } else {
/*  78 */       SystemToast.addOrUpdate($$1, SystemToast.SystemToastId.NARRATOR_TOGGLE, (Component)Component.translatable("narrator.toast.disabled"), (Component)Component.translatable("options.narrator.notavailable"));
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isActive() {
/*  83 */     return this.narrator.active();
/*     */   }
/*     */   
/*     */   public void clear() {
/*  87 */     if (getStatus() == NarratorStatus.OFF || !this.narrator.active()) {
/*     */       return;
/*     */     }
/*  90 */     this.narrator.clear();
/*     */   }
/*     */   
/*     */   public void destroy() {
/*  94 */     this.narrator.destroy();
/*     */   }
/*     */   
/*     */   public void checkStatus(boolean $$0) {
/*  98 */     if ($$0 && !isActive() && 
/*  99 */       !TinyFileDialogs.tinyfd_messageBox("Minecraft", "Failed to initialize text-to-speech library. Do you want to continue?\nIf this problem persists, please report it at bugs.mojang.com", "yesno", "error", true))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 107 */       throw new NarratorInitException("Narrator library is not active");
/*     */     }
/*     */   }
/*     */   
/*     */   public static class NarratorInitException
/*     */     extends SilentInitException {
/*     */     public NarratorInitException(String $$0) {
/* 114 */       super($$0);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\GameNarrator.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */