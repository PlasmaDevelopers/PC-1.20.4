/*    */ package net.minecraft.world.entity.player;
/*    */ 
/*    */ import java.util.function.IntFunction;
/*    */ import net.minecraft.util.ByIdMap;
/*    */ import net.minecraft.util.OptionEnum;
/*    */ 
/*    */ public enum ChatVisiblity
/*    */   implements OptionEnum {
/*  9 */   FULL(0, "options.chat.visibility.full"),
/* 10 */   SYSTEM(1, "options.chat.visibility.system"),
/* 11 */   HIDDEN(2, "options.chat.visibility.hidden"); private static final IntFunction<ChatVisiblity> BY_ID;
/*    */   static {
/* 13 */     BY_ID = ByIdMap.continuous(ChatVisiblity::getId, (Object[])values(), ByIdMap.OutOfBoundsStrategy.WRAP);
/*    */   }
/*    */   private final int id; private final String key;
/*    */   
/*    */   ChatVisiblity(int $$0, String $$1) {
/* 18 */     this.id = $$0;
/* 19 */     this.key = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getId() {
/* 24 */     return this.id;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getKey() {
/* 29 */     return this.key;
/*    */   }
/*    */   
/*    */   public static ChatVisiblity byId(int $$0) {
/* 33 */     return BY_ID.apply($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\player\ChatVisiblity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */