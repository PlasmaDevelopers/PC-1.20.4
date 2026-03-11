/*    */ package net.minecraft.network.chat;
/*    */ 
/*    */ import com.mojang.serialization.DataResult;
/*    */ import com.mojang.serialization.Lifecycle;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ import net.minecraft.util.StringRepresentable;
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
/*    */ public enum Action
/*    */   implements StringRepresentable
/*    */ {
/*    */   public static final MapCodec<Action> UNSAFE_CODEC;
/*    */   public static final MapCodec<Action> CODEC;
/* 62 */   OPEN_URL("open_url", true),
/* 63 */   OPEN_FILE("open_file", false),
/* 64 */   RUN_COMMAND("run_command", true),
/* 65 */   SUGGEST_COMMAND("suggest_command", true),
/* 66 */   CHANGE_PAGE("change_page", true),
/* 67 */   COPY_TO_CLIPBOARD("copy_to_clipboard", true);
/*    */   
/*    */   static {
/* 70 */     UNSAFE_CODEC = StringRepresentable.fromEnum(Action::values).fieldOf("action");
/* 71 */     CODEC = ExtraCodecs.validate(UNSAFE_CODEC, Action::filterForSerialization);
/*    */   }
/*    */   
/*    */   private final boolean allowFromServer;
/*    */   
/*    */   Action(String $$0, boolean $$1) {
/* 77 */     this.name = $$0;
/* 78 */     this.allowFromServer = $$1;
/*    */   }
/*    */   private final String name;
/*    */   public boolean isAllowedFromServer() {
/* 82 */     return this.allowFromServer;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSerializedName() {
/* 87 */     return this.name;
/*    */   }
/*    */   
/*    */   public static DataResult<Action> filterForSerialization(Action $$0) {
/* 91 */     if (!$$0.isAllowedFromServer()) {
/* 92 */       return DataResult.error(() -> "Action not allowed: " + $$0);
/*    */     }
/* 94 */     return DataResult.success($$0, Lifecycle.stable());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\ClickEvent$Action.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */