/*     */ package net.minecraft.client;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Objects;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.network.chat.Style;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class FlatComponents
/*     */ {
/*     */   final List<StringSplitter.LineComponent> parts;
/*     */   private String flatParts;
/*     */   
/*     */   public FlatComponents(List<StringSplitter.LineComponent> $$0) {
/* 340 */     this.parts = $$0;
/* 341 */     this.flatParts = $$0.stream().map($$0 -> $$0.contents).collect(Collectors.joining());
/*     */   }
/*     */   
/*     */   public char charAt(int $$0) {
/* 345 */     return this.flatParts.charAt($$0);
/*     */   }
/*     */   
/*     */   public FormattedText splitAt(int $$0, int $$1, Style $$2) {
/* 349 */     ComponentCollector $$3 = new ComponentCollector();
/* 350 */     ListIterator<StringSplitter.LineComponent> $$4 = this.parts.listIterator();
/* 351 */     int $$5 = $$0;
/* 352 */     boolean $$6 = false;
/* 353 */     while ($$4.hasNext()) {
/* 354 */       StringSplitter.LineComponent $$7 = $$4.next();
/* 355 */       String $$8 = $$7.contents;
/* 356 */       int $$9 = $$8.length();
/*     */       
/* 358 */       if (!$$6) {
/* 359 */         if ($$5 > $$9) {
/* 360 */           $$3.append($$7);
/* 361 */           $$4.remove();
/* 362 */           $$5 -= $$9;
/*     */         } else {
/* 364 */           String $$10 = $$8.substring(0, $$5);
/* 365 */           if (!$$10.isEmpty()) {
/* 366 */             $$3.append(FormattedText.of($$10, $$7.style));
/*     */           }
/* 368 */           $$5 += $$1;
/* 369 */           $$6 = true;
/*     */         } 
/*     */       }
/*     */       
/* 373 */       if ($$6) {
/* 374 */         if ($$5 > $$9) {
/* 375 */           $$4.remove();
/* 376 */           $$5 -= $$9; continue;
/*     */         } 
/* 378 */         String $$11 = $$8.substring($$5);
/* 379 */         if ($$11.isEmpty()) {
/* 380 */           $$4.remove(); break;
/*     */         } 
/* 382 */         $$4.set(new StringSplitter.LineComponent($$11, $$2));
/*     */ 
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/* 389 */     this.flatParts = this.flatParts.substring($$0 + $$1);
/* 390 */     return $$3.getResultOrEmpty();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public FormattedText getRemainder() {
/* 395 */     ComponentCollector $$0 = new ComponentCollector();
/* 396 */     Objects.requireNonNull($$0); this.parts.forEach($$0::append);
/* 397 */     this.parts.clear();
/* 398 */     return $$0.getResult();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\StringSplitter$FlatComponents.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */