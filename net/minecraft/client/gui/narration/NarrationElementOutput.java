/*    */ package net.minecraft.client.gui.narration;
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.List;
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ public interface NarrationElementOutput {
/*    */   default void add(NarratedElementType $$0, Component $$1) {
/*  8 */     add($$0, NarrationThunk.from($$1.getString()));
/*    */   }
/*    */   
/*    */   default void add(NarratedElementType $$0, String $$1) {
/* 12 */     add($$0, NarrationThunk.from($$1));
/*    */   }
/*    */   
/*    */   void add(NarratedElementType $$0, Component... $$1) {
/* 16 */     add($$0, NarrationThunk.from((List<Component>)ImmutableList.copyOf((Object[])$$1)));
/*    */   }
/*    */   
/*    */   void add(NarratedElementType paramNarratedElementType, NarrationThunk<?> paramNarrationThunk);
/*    */   
/*    */   NarrationElementOutput nest();
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\narration\NarrationElementOutput.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */