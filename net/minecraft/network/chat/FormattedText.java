/*    */ package net.minecraft.network.chat;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.util.Unit;
/*    */ 
/*    */ public interface FormattedText
/*    */ {
/* 10 */   public static final Optional<Unit> STOP_ITERATION = Optional.of(Unit.INSTANCE);
/*    */   
/* 12 */   public static final FormattedText EMPTY = new FormattedText()
/*    */     {
/*    */       public <T> Optional<T> visit(FormattedText.ContentConsumer<T> $$0) {
/* 15 */         return Optional.empty();
/*    */       }
/*    */ 
/*    */       
/*    */       public <T> Optional<T> visit(FormattedText.StyledContentConsumer<T> $$0, Style $$1) {
/* 20 */         return Optional.empty();
/*    */       }
/*    */     };
/*    */   
/*    */   <T> Optional<T> visit(ContentConsumer<T> paramContentConsumer);
/*    */   
/*    */   <T> Optional<T> visit(StyledContentConsumer<T> paramStyledContentConsumer, Style paramStyle);
/*    */   
/*    */   static FormattedText of(final String text) {
/* 29 */     return new FormattedText()
/*    */       {
/*    */         public <T> Optional<T> visit(FormattedText.ContentConsumer<T> $$0) {
/* 32 */           return $$0.accept(text);
/*    */         }
/*    */ 
/*    */         
/*    */         public <T> Optional<T> visit(FormattedText.StyledContentConsumer<T> $$0, Style $$1) {
/* 37 */           return $$0.accept($$1, text);
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   static FormattedText of(final String text, final Style style) {
/* 43 */     return new FormattedText()
/*    */       {
/*    */         public <T> Optional<T> visit(FormattedText.ContentConsumer<T> $$0) {
/* 46 */           return $$0.accept(text);
/*    */         }
/*    */ 
/*    */         
/*    */         public <T> Optional<T> visit(FormattedText.StyledContentConsumer<T> $$0, Style $$1) {
/* 51 */           return $$0.accept(style.applyTo($$1), text);
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   static FormattedText composite(FormattedText... $$0) {
/* 57 */     return composite((List<? extends FormattedText>)ImmutableList.copyOf((Object[])$$0));
/*    */   }
/*    */   
/*    */   static FormattedText composite(final List<? extends FormattedText> parts) {
/* 61 */     return new FormattedText()
/*    */       {
/*    */         public <T> Optional<T> visit(FormattedText.ContentConsumer<T> $$0) {
/* 64 */           for (FormattedText $$1 : parts) {
/* 65 */             Optional<T> $$2 = $$1.visit($$0);
/* 66 */             if ($$2.isPresent()) {
/* 67 */               return $$2;
/*    */             }
/*    */           } 
/*    */           
/* 71 */           return Optional.empty();
/*    */         }
/*    */ 
/*    */         
/*    */         public <T> Optional<T> visit(FormattedText.StyledContentConsumer<T> $$0, Style $$1) {
/* 76 */           for (FormattedText $$2 : parts) {
/* 77 */             Optional<T> $$3 = $$2.visit($$0, $$1);
/* 78 */             if ($$3.isPresent()) {
/* 79 */               return $$3;
/*    */             }
/*    */           } 
/*    */           
/* 83 */           return Optional.empty();
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   default String getString() {
/* 89 */     StringBuilder $$0 = new StringBuilder();
/*    */     
/* 91 */     visit($$1 -> {
/*    */           $$0.append($$1);
/*    */           
/*    */           return Optional.empty();
/*    */         });
/* 96 */     return $$0.toString();
/*    */   }
/*    */   
/*    */   public static interface ContentConsumer<T> {
/*    */     Optional<T> accept(String param1String);
/*    */   }
/*    */   
/*    */   public static interface StyledContentConsumer<T> {
/*    */     Optional<T> accept(Style param1Style, String param1String);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\FormattedText.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */