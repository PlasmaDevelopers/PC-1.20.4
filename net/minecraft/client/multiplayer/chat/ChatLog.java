/*    */ package net.minecraft.client.multiplayer.chat;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.DataResult;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class ChatLog {
/*    */   private final LoggedChatEvent[] buffer;
/*    */   private int nextId;
/*    */   
/*    */   public static Codec<ChatLog> codec(int $$0) {
/* 12 */     return Codec.list(LoggedChatEvent.CODEC).comapFlatMap($$1 -> { int $$2 = $$1.size(); return ($$2 > $$0) ? DataResult.error(()) : DataResult.success(new ChatLog($$0, $$1)); }ChatLog::loggedChatEvents);
/*    */   }
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
/*    */   public ChatLog(int $$0) {
/* 25 */     this.buffer = new LoggedChatEvent[$$0];
/*    */   }
/*    */   
/*    */   private ChatLog(int $$0, List<LoggedChatEvent> $$1) {
/* 29 */     this.buffer = (LoggedChatEvent[])$$1.toArray($$1 -> new LoggedChatEvent[$$0]);
/* 30 */     this.nextId = $$1.size();
/*    */   }
/*    */   
/*    */   private List<LoggedChatEvent> loggedChatEvents() {
/* 34 */     List<LoggedChatEvent> $$0 = new ArrayList<>(size());
/* 35 */     for (int $$1 = start(); $$1 <= end(); $$1++) {
/* 36 */       $$0.add(lookup($$1));
/*    */     }
/* 38 */     return $$0;
/*    */   }
/*    */   
/*    */   public void push(LoggedChatEvent $$0) {
/* 42 */     this.buffer[index(this.nextId++)] = $$0;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public LoggedChatEvent lookup(int $$0) {
/* 47 */     return ($$0 >= start() && $$0 <= end()) ? this.buffer[index($$0)] : null;
/*    */   }
/*    */   
/*    */   private int index(int $$0) {
/* 51 */     return $$0 % this.buffer.length;
/*    */   }
/*    */   
/*    */   public int start() {
/* 55 */     return Math.max(this.nextId - this.buffer.length, 0);
/*    */   }
/*    */   
/*    */   public int end() {
/* 59 */     return this.nextId - 1;
/*    */   }
/*    */   
/*    */   private int size() {
/* 63 */     return end() - start() + 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\multiplayer\chat\ChatLog.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */