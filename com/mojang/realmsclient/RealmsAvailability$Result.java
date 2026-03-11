/*    */ package com.mojang.realmsclient;
/*    */ 
/*    */ import com.mojang.realmsclient.exception.RealmsServiceException;
/*    */ import com.mojang.realmsclient.gui.screens.RealmsGenericErrorScreen;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.gui.screens.Screen;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Result
/*    */   extends Record
/*    */ {
/*    */   private final RealmsAvailability.Type type;
/*    */   @Nullable
/*    */   private final RealmsServiceException exception;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lcom/mojang/realmsclient/RealmsAvailability$Result;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #63	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/mojang/realmsclient/RealmsAvailability$Result;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lcom/mojang/realmsclient/RealmsAvailability$Result;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #63	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/mojang/realmsclient/RealmsAvailability$Result;
/*    */   }
/*    */   
/*    */   public Result(RealmsAvailability.Type $$0, @Nullable RealmsServiceException $$1) {
/* 63 */     this.type = $$0; this.exception = $$1; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lcom/mojang/realmsclient/RealmsAvailability$Result;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #63	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lcom/mojang/realmsclient/RealmsAvailability$Result;
/* 63 */     //   0	8	1	$$0	Ljava/lang/Object; } public RealmsAvailability.Type type() { return this.type; } @Nullable public RealmsServiceException exception() { return this.exception; }
/*    */    public Result(RealmsAvailability.Type $$0) {
/* 65 */     this($$0, null);
/*    */   }
/*    */   
/*    */   public Result(RealmsServiceException $$0) {
/* 69 */     this(RealmsAvailability.Type.UNEXPECTED_ERROR, $$0);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public Screen createErrorScreen(Screen $$0) {
/* 74 */     switch (RealmsAvailability.null.$SwitchMap$com$mojang$realmsclient$RealmsAvailability$Type[this.type.ordinal()]) { default: throw new IncompatibleClassChangeError();case 1: case 2: case 3: case 4: case 5: break; }  return 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 79 */       (Screen)new RealmsGenericErrorScreen(Objects.<RealmsServiceException>requireNonNull(this.exception), $$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\RealmsAvailability$Result.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */