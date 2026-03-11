/*    */ package net.minecraft.network.chat;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.DataResult;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.util.StringRepresentable;
/*    */ 
/*    */ public class ClickEvent {
/*    */   static {
/* 12 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Action.CODEC.forGetter(()), (App)Codec.STRING.fieldOf("value").forGetter(())).apply((Applicative)$$0, ClickEvent::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<ClickEvent> CODEC;
/*    */   private final Action action;
/*    */   private final String value;
/*    */   
/*    */   public ClickEvent(Action $$0, String $$1) {
/* 21 */     this.action = $$0;
/* 22 */     this.value = $$1;
/*    */   }
/*    */   
/*    */   public Action getAction() {
/* 26 */     return this.action;
/*    */   }
/*    */   
/*    */   public String getValue() {
/* 30 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object $$0) {
/* 35 */     if (this == $$0) {
/* 36 */       return true;
/*    */     }
/* 38 */     if ($$0 == null || getClass() != $$0.getClass()) {
/* 39 */       return false;
/*    */     }
/*    */     
/* 42 */     ClickEvent $$1 = (ClickEvent)$$0;
/* 43 */     return (this.action == $$1.action && this.value.equals($$1.value));
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 48 */     return "ClickEvent{action=" + this.action + ", value='" + this.value + "'}";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 56 */     int $$0 = this.action.hashCode();
/* 57 */     $$0 = 31 * $$0 + this.value.hashCode();
/* 58 */     return $$0;
/*    */   }
/*    */   
/*    */   public enum Action implements StringRepresentable {
/* 62 */     OPEN_URL("open_url", true),
/* 63 */     OPEN_FILE("open_file", false),
/* 64 */     RUN_COMMAND("run_command", true),
/* 65 */     SUGGEST_COMMAND("suggest_command", true),
/* 66 */     CHANGE_PAGE("change_page", true),
/* 67 */     COPY_TO_CLIPBOARD("copy_to_clipboard", true);
/*    */ 
/*    */     
/* 70 */     public static final MapCodec<Action> UNSAFE_CODEC = StringRepresentable.fromEnum(Action::values).fieldOf("action");
/* 71 */     public static final MapCodec<Action> CODEC = ExtraCodecs.validate(UNSAFE_CODEC, Action::filterForSerialization); private final boolean allowFromServer; private final String name;
/*    */     static {
/*    */     
/*    */     }
/*    */     
/*    */     Action(String $$0, boolean $$1) {
/* 77 */       this.name = $$0;
/* 78 */       this.allowFromServer = $$1;
/*    */     }
/*    */     
/*    */     public boolean isAllowedFromServer() {
/* 82 */       return this.allowFromServer;
/*    */     }
/*    */ 
/*    */     
/*    */     public String getSerializedName() {
/* 87 */       return this.name;
/*    */     }
/*    */     
/*    */     public static DataResult<Action> filterForSerialization(Action $$0) {
/* 91 */       if (!$$0.isAllowedFromServer()) {
/* 92 */         return DataResult.error(() -> "Action not allowed: " + $$0);
/*    */       }
/* 94 */       return DataResult.success($$0, Lifecycle.stable());
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\ClickEvent.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */