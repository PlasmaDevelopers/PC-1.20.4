/*    */ package net.minecraft.network.chat.contents;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Optional;
/*    */ import java.util.function.Function;
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.ComponentContents;
/*    */ import net.minecraft.network.chat.FormattedText;
/*    */ import net.minecraft.network.chat.Style;
/*    */ 
/*    */ public class KeybindContents implements ComponentContents {
/*    */   static {
/* 16 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)Codec.STRING.fieldOf("keybind").forGetter(())).apply((Applicative)$$0, KeybindContents::new));
/*    */   }
/*    */   
/*    */   public static final MapCodec<KeybindContents> CODEC;
/* 20 */   public static final ComponentContents.Type<KeybindContents> TYPE = new ComponentContents.Type(CODEC, "keybind");
/*    */   
/*    */   private final String name;
/*    */   @Nullable
/*    */   private Supplier<Component> nameResolver;
/*    */   
/*    */   public KeybindContents(String $$0) {
/* 27 */     this.name = $$0;
/*    */   }
/*    */   
/*    */   private Component getNestedComponent() {
/* 31 */     if (this.nameResolver == null) {
/* 32 */       this.nameResolver = KeybindResolver.keyResolver.apply(this.name);
/*    */     }
/*    */     
/* 35 */     return this.nameResolver.get();
/*    */   }
/*    */ 
/*    */   
/*    */   public <T> Optional<T> visit(FormattedText.ContentConsumer<T> $$0) {
/* 40 */     return getNestedComponent().visit($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public <T> Optional<T> visit(FormattedText.StyledContentConsumer<T> $$0, Style $$1) {
/* 45 */     return getNestedComponent().visit($$0, $$1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: if_acmpne -> 7
/*    */     //   5: iconst_1
/*    */     //   6: ireturn
/*    */     //   7: aload_1
/*    */     //   8: instanceof net/minecraft/network/chat/contents/KeybindContents
/*    */     //   11: ifeq -> 37
/*    */     //   14: aload_1
/*    */     //   15: checkcast net/minecraft/network/chat/contents/KeybindContents
/*    */     //   18: astore_2
/*    */     //   19: aload_0
/*    */     //   20: getfield name : Ljava/lang/String;
/*    */     //   23: aload_2
/*    */     //   24: getfield name : Ljava/lang/String;
/*    */     //   27: invokevirtual equals : (Ljava/lang/Object;)Z
/*    */     //   30: ifeq -> 37
/*    */     //   33: iconst_1
/*    */     //   34: goto -> 38
/*    */     //   37: iconst_0
/*    */     //   38: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #50	-> 0
/*    */     //   #51	-> 5
/*    */     //   #54	-> 7
/*    */     //   #53	-> 14
/*    */     //   #54	-> 27
/*    */     //   #53	-> 38
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	39	0	this	Lnet/minecraft/network/chat/contents/KeybindContents;
/*    */     //   0	39	1	$$0	Ljava/lang/Object;
/*    */     //   19	18	2	$$1	Lnet/minecraft/network/chat/contents/KeybindContents;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 59 */     return this.name.hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 64 */     return "keybind{" + this.name + "}";
/*    */   }
/*    */   
/*    */   public String getName() {
/* 68 */     return this.name;
/*    */   }
/*    */ 
/*    */   
/*    */   public ComponentContents.Type<?> type() {
/* 73 */     return TYPE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\contents\KeybindContents.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */