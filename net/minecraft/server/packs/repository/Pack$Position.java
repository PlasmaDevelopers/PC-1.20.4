/*     */ package net.minecraft.server.packs.repository;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.function.Function;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum Position
/*     */ {
/* 174 */   TOP,
/* 175 */   BOTTOM;
/*     */ 
/*     */   
/*     */   public <T> int insert(List<T> $$0, T $$1, Function<T, Pack> $$2, boolean $$3) {
/* 179 */     Position $$4 = $$3 ? opposite() : this;
/* 180 */     if ($$4 == BOTTOM) {
/* 181 */       int $$5 = 0;
/* 182 */       while ($$5 < $$0.size()) {
/* 183 */         Pack $$6 = $$2.apply($$0.get($$5));
/* 184 */         if ($$6.isFixedPosition() && $$6.getDefaultPosition() == this) {
/* 185 */           $$5++;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 190 */       $$0.add($$5, $$1);
/* 191 */       return $$5;
/*     */     } 
/* 193 */     int $$7 = $$0.size() - 1;
/* 194 */     while ($$7 >= 0) {
/* 195 */       Pack $$8 = $$2.apply($$0.get($$7));
/* 196 */       if ($$8.isFixedPosition() && $$8.getDefaultPosition() == this) {
/* 197 */         $$7--;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 202 */     $$0.add($$7 + 1, $$1);
/* 203 */     return $$7 + 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public Position opposite() {
/* 208 */     return (this == TOP) ? BOTTOM : TOP;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\repository\Pack$Position.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */