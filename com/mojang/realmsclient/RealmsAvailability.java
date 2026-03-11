/*    */ package com.mojang.realmsclient;
/*    */ 
/*    */ import com.mojang.logging.LogUtils;
/*    */ import com.mojang.realmsclient.client.RealmsClient;
/*    */ import com.mojang.realmsclient.exception.RealmsServiceException;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.client.gui.screens.Screen;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RealmsAvailability
/*    */ {
/* 21 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/*    */   @Nullable
/*    */   private static CompletableFuture<Result> future;
/*    */   
/*    */   public static CompletableFuture<Result> get() {
/* 27 */     if (future == null || shouldRefresh(future)) {
/* 28 */       future = check();
/*    */     }
/* 30 */     return future;
/*    */   }
/*    */   
/*    */   private static boolean shouldRefresh(CompletableFuture<Result> $$0) {
/* 34 */     Result $$1 = $$0.getNow(null);
/* 35 */     return ($$1 != null && $$1.exception() != null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static CompletableFuture<Result> check() {
/* 42 */     return CompletableFuture.supplyAsync(() -> {
/*    */           RealmsClient $$0 = RealmsClient.create();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */           
/*    */           try {
/*    */             return ($$0.clientCompatible() != RealmsClient.CompatibleVersionResponse.COMPATIBLE) ? new Result(Type.INCOMPATIBLE_CLIENT) : (!$$0.hasParentalConsent() ? new Result(Type.NEEDS_PARENTAL_CONSENT) : new Result(Type.SUCCESS));
/* 52 */           } catch (RealmsServiceException $$1) {
/*    */             LOGGER.error("Couldn't connect to realms", (Throwable)$$1);
/*    */ 
/*    */ 
/*    */ 
/*    */             
/*    */             return ($$1.realmsError.errorCode() == 401) ? new Result(Type.AUTHENTICATION_ERROR) : new Result($$1);
/*    */           } 
/* 60 */         }Util.ioPool());
/*    */   } public static final class Result extends Record { private final RealmsAvailability.Type type; @Nullable
/*    */     private final RealmsServiceException exception;
/* 63 */     public Result(RealmsAvailability.Type $$0, @Nullable RealmsServiceException $$1) { this.type = $$0; this.exception = $$1; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lcom/mojang/realmsclient/RealmsAvailability$Result;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #63	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/* 63 */       //   0	7	0	this	Lcom/mojang/realmsclient/RealmsAvailability$Result; } public RealmsAvailability.Type type() { return this.type; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lcom/mojang/realmsclient/RealmsAvailability$Result;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #63	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lcom/mojang/realmsclient/RealmsAvailability$Result; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lcom/mojang/realmsclient/RealmsAvailability$Result;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #63	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lcom/mojang/realmsclient/RealmsAvailability$Result;
/* 63 */       //   0	8	1	$$0	Ljava/lang/Object; } @Nullable public RealmsServiceException exception() { return this.exception; }
/*    */      public Result(RealmsAvailability.Type $$0) {
/* 65 */       this($$0, null);
/*    */     }
/*    */     
/*    */     public Result(RealmsServiceException $$0) {
/* 69 */       this(RealmsAvailability.Type.UNEXPECTED_ERROR, $$0);
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     @Nullable
/*    */     public Screen createErrorScreen(Screen $$0) {
/*    */       // Byte code:
/*    */       //   0: getstatic com/mojang/realmsclient/RealmsAvailability$1.$SwitchMap$com$mojang$realmsclient$RealmsAvailability$Type : [I
/*    */       //   3: aload_0
/*    */       //   4: getfield type : Lcom/mojang/realmsclient/RealmsAvailability$Type;
/*    */       //   7: invokevirtual ordinal : ()I
/*    */       //   10: iaload
/*    */       //   11: tableswitch default -> 44, 1 -> 52, 2 -> 56, 3 -> 67, 4 -> 78, 5 -> 99
/*    */       //   44: new java/lang/IncompatibleClassChangeError
/*    */       //   47: dup
/*    */       //   48: invokespecial <init> : ()V
/*    */       //   51: athrow
/*    */       //   52: aconst_null
/*    */       //   53: goto -> 117
/*    */       //   56: new com/mojang/realmsclient/gui/screens/RealmsClientOutdatedScreen
/*    */       //   59: dup
/*    */       //   60: aload_1
/*    */       //   61: invokespecial <init> : (Lnet/minecraft/client/gui/screens/Screen;)V
/*    */       //   64: goto -> 117
/*    */       //   67: new com/mojang/realmsclient/gui/screens/RealmsParentalConsentScreen
/*    */       //   70: dup
/*    */       //   71: aload_1
/*    */       //   72: invokespecial <init> : (Lnet/minecraft/client/gui/screens/Screen;)V
/*    */       //   75: goto -> 117
/*    */       //   78: new com/mojang/realmsclient/gui/screens/RealmsGenericErrorScreen
/*    */       //   81: dup
/*    */       //   82: ldc 'mco.error.invalid.session.title'
/*    */       //   84: invokestatic translatable : (Ljava/lang/String;)Lnet/minecraft/network/chat/MutableComponent;
/*    */       //   87: ldc 'mco.error.invalid.session.message'
/*    */       //   89: invokestatic translatable : (Ljava/lang/String;)Lnet/minecraft/network/chat/MutableComponent;
/*    */       //   92: aload_1
/*    */       //   93: invokespecial <init> : (Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/Component;Lnet/minecraft/client/gui/screens/Screen;)V
/*    */       //   96: goto -> 117
/*    */       //   99: new com/mojang/realmsclient/gui/screens/RealmsGenericErrorScreen
/*    */       //   102: dup
/*    */       //   103: aload_0
/*    */       //   104: getfield exception : Lcom/mojang/realmsclient/exception/RealmsServiceException;
/*    */       //   107: invokestatic requireNonNull : (Ljava/lang/Object;)Ljava/lang/Object;
/*    */       //   110: checkcast com/mojang/realmsclient/exception/RealmsServiceException
/*    */       //   113: aload_1
/*    */       //   114: invokespecial <init> : (Lcom/mojang/realmsclient/exception/RealmsServiceException;Lnet/minecraft/client/gui/screens/Screen;)V
/*    */       //   117: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #74	-> 0
/*    */       //   #75	-> 52
/*    */       //   #76	-> 56
/*    */       //   #77	-> 67
/*    */       //   #78	-> 78
/*    */       //   #79	-> 99
/*    */       //   #74	-> 117
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	118	0	this	Lcom/mojang/realmsclient/RealmsAvailability$Result;
/*    */       //   0	118	1	$$0	Lnet/minecraft/client/gui/screens/Screen;
/*    */     } }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public enum Type
/*    */   {
/* 85 */     SUCCESS,
/* 86 */     INCOMPATIBLE_CLIENT,
/* 87 */     NEEDS_PARENTAL_CONSENT,
/* 88 */     AUTHENTICATION_ERROR,
/* 89 */     UNEXPECTED_ERROR;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\RealmsAvailability.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */