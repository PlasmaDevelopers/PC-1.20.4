/*    */ package net.minecraft.network.chat;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nullable;
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
/*    */ public enum Parameter
/*    */   implements StringRepresentable
/*    */ {
/*    */   SENDER, TARGET, CONTENT;
/*    */   public static final Codec<Parameter> CODEC;
/*    */   private final String name;
/*    */   private final Selector selector;
/*    */   
/*    */   static {
/* 52 */     SENDER = new Parameter("SENDER", 0, "sender", ($$0, $$1) -> $$1.name());
/* 53 */     TARGET = new Parameter("TARGET", 1, "target", ($$0, $$1) -> $$1.targetName());
/* 54 */     CONTENT = new Parameter("CONTENT", 2, "content", ($$0, $$1) -> $$0);
/*    */   } static {
/* 56 */     CODEC = (Codec<Parameter>)StringRepresentable.fromEnum(Parameter::values);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   Parameter(String $$0, Selector $$1) {
/* 62 */     this.name = $$0;
/* 63 */     this.selector = $$1;
/*    */   }
/*    */   
/*    */   public Component select(Component $$0, ChatType.Bound $$1) {
/* 67 */     Component $$2 = this.selector.select($$0, $$1);
/* 68 */     return Objects.<Component>requireNonNullElse($$2, CommonComponents.EMPTY);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSerializedName() {
/* 73 */     return this.name;
/*    */   }
/*    */   
/*    */   public static interface Selector {
/*    */     @Nullable
/*    */     Component select(Component param2Component, ChatType.Bound param2Bound);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\ChatTypeDecoration$Parameter.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */