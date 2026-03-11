/*     */ package net.minecraft.client.gui.components.toasts;
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
/*     */ public class SystemToastId
/*     */ {
/* 130 */   public static final SystemToastId NARRATOR_TOGGLE = new SystemToastId();
/* 131 */   public static final SystemToastId WORLD_BACKUP = new SystemToastId();
/* 132 */   public static final SystemToastId PACK_LOAD_FAILURE = new SystemToastId();
/* 133 */   public static final SystemToastId WORLD_ACCESS_FAILURE = new SystemToastId();
/* 134 */   public static final SystemToastId PACK_COPY_FAILURE = new SystemToastId();
/* 135 */   public static final SystemToastId PERIODIC_NOTIFICATION = new SystemToastId();
/* 136 */   public static final SystemToastId UNSECURE_SERVER_WARNING = new SystemToastId(10000L);
/*     */   
/*     */   final long displayTime;
/*     */   
/*     */   public SystemToastId(long $$0) {
/* 141 */     this.displayTime = $$0;
/*     */   }
/*     */   
/*     */   public SystemToastId() {
/* 145 */     this(5000L);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\toasts\SystemToast$SystemToastId.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */