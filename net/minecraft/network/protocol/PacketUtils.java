/*    */ package net.minecraft.network.protocol;
/*    */ 
/*    */ import com.mojang.logging.LogUtils;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.server.RunningOnDifferentThreadException;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.util.thread.BlockableEventLoop;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ 
/*    */ public class PacketUtils
/*    */ {
/* 13 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/*    */   public static <T extends PacketListener> void ensureRunningOnSameThread(Packet<T> $$0, T $$1, ServerLevel $$2) throws RunningOnDifferentThreadException {
/* 16 */     ensureRunningOnSameThread($$0, $$1, (BlockableEventLoop<?>)$$2.getServer());
/*    */   }
/*    */   
/*    */   public static <T extends PacketListener> void ensureRunningOnSameThread(Packet<T> $$0, T $$1, BlockableEventLoop<?> $$2) throws RunningOnDifferentThreadException {
/* 20 */     if (!$$2.isSameThread()) {
/* 21 */       $$2.executeIfPossible(() -> {
/*    */             // Byte code:
/*    */             //   0: aload_0
/*    */             //   1: aload_1
/*    */             //   2: invokeinterface shouldHandleMessage : (Lnet/minecraft/network/protocol/Packet;)Z
/*    */             //   7: ifeq -> 119
/*    */             //   10: aload_1
/*    */             //   11: aload_0
/*    */             //   12: invokeinterface handle : (Lnet/minecraft/network/PacketListener;)V
/*    */             //   17: goto -> 130
/*    */             //   20: astore_2
/*    */             //   21: aload_2
/*    */             //   22: instanceof net/minecraft/ReportedException
/*    */             //   25: ifeq -> 43
/*    */             //   28: aload_2
/*    */             //   29: checkcast net/minecraft/ReportedException
/*    */             //   32: astore_3
/*    */             //   33: aload_3
/*    */             //   34: invokevirtual getCause : ()Ljava/lang/Throwable;
/*    */             //   37: instanceof java/lang/OutOfMemoryError
/*    */             //   40: ifne -> 52
/*    */             //   43: aload_0
/*    */             //   44: invokeinterface shouldPropagateHandlingExceptions : ()Z
/*    */             //   49: ifeq -> 104
/*    */             //   52: aload_2
/*    */             //   53: instanceof net/minecraft/ReportedException
/*    */             //   56: ifeq -> 78
/*    */             //   59: aload_2
/*    */             //   60: checkcast net/minecraft/ReportedException
/*    */             //   63: astore #4
/*    */             //   65: aload_0
/*    */             //   66: aload #4
/*    */             //   68: invokevirtual getReport : ()Lnet/minecraft/CrashReport;
/*    */             //   71: invokeinterface fillCrashReport : (Lnet/minecraft/CrashReport;)V
/*    */             //   76: aload_2
/*    */             //   77: athrow
/*    */             //   78: aload_2
/*    */             //   79: ldc 'Main thread packet handler'
/*    */             //   81: invokestatic forThrowable : (Ljava/lang/Throwable;Ljava/lang/String;)Lnet/minecraft/CrashReport;
/*    */             //   84: astore #5
/*    */             //   86: aload_0
/*    */             //   87: aload #5
/*    */             //   89: invokeinterface fillCrashReport : (Lnet/minecraft/CrashReport;)V
/*    */             //   94: new net/minecraft/ReportedException
/*    */             //   97: dup
/*    */             //   98: aload #5
/*    */             //   100: invokespecial <init> : (Lnet/minecraft/CrashReport;)V
/*    */             //   103: athrow
/*    */             //   104: getstatic net/minecraft/network/protocol/PacketUtils.LOGGER : Lorg/slf4j/Logger;
/*    */             //   107: ldc 'Failed to handle packet {}, suppressing error'
/*    */             //   109: aload_1
/*    */             //   110: aload_2
/*    */             //   111: invokeinterface error : (Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V
/*    */             //   116: goto -> 130
/*    */             //   119: getstatic net/minecraft/network/protocol/PacketUtils.LOGGER : Lorg/slf4j/Logger;
/*    */             //   122: ldc 'Ignoring packet due to disconnection: {}'
/*    */             //   124: aload_1
/*    */             //   125: invokeinterface debug : (Ljava/lang/String;Ljava/lang/Object;)V
/*    */             //   130: return
/*    */             // Line number table:
/*    */             //   Java source line number -> byte code offset
/*    */             //   #22	-> 0
/*    */             //   #24	-> 10
/*    */             //   #38	-> 17
/*    */             //   #25	-> 20
/*    */             //   #26	-> 21
/*    */             //   #27	-> 52
/*    */             //   #28	-> 65
/*    */             //   #29	-> 76
/*    */             //   #31	-> 78
/*    */             //   #32	-> 86
/*    */             //   #33	-> 94
/*    */             //   #36	-> 104
/*    */             //   #38	-> 116
/*    */             //   #40	-> 119
/*    */             //   #42	-> 130
/*    */             // Local variable table:
/*    */             //   start	length	slot	name	descriptor
/*    */             //   0	131	0	$$0	Lnet/minecraft/network/PacketListener;
/*    */             //   0	131	1	$$1	Lnet/minecraft/network/protocol/Packet;
/*    */             //   21	95	2	$$2	Ljava/lang/Exception;
/*    */             //   33	10	3	$$3	Lnet/minecraft/ReportedException;
/*    */             //   65	13	4	$$4	Lnet/minecraft/ReportedException;
/*    */             //   86	18	5	$$5	Lnet/minecraft/CrashReport;
/*    */             // Exception table:
/*    */             //   from	to	target	type
/*    */             //   10	17	20	java/lang/Exception
/*    */           });
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
/* 43 */       throw RunningOnDifferentThreadException.RUNNING_ON_DIFFERENT_THREAD;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\PacketUtils.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */