/*     */ package net.minecraft.util.monitoring.jmx;
/*     */ 
/*     */ import java.util.function.Supplier;
/*     */ import javax.management.MBeanAttributeInfo;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class AttributeDescription
/*     */ {
/*     */   final String name;
/*     */   final Supplier<Object> getter;
/*     */   private final String description;
/*     */   private final Class<?> type;
/*     */   
/*     */   AttributeDescription(String $$0, Supplier<Object> $$1, String $$2, Class<?> $$3) {
/* 117 */     this.name = $$0;
/* 118 */     this.getter = $$1;
/* 119 */     this.description = $$2;
/* 120 */     this.type = $$3;
/*     */   }
/*     */   
/*     */   private MBeanAttributeInfo asMBeanAttributeInfo() {
/* 124 */     return new MBeanAttributeInfo(this.name, this.type.getSimpleName(), this.description, true, false, false);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\monitoring\jmx\MinecraftServerStatistics$AttributeDescription.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */