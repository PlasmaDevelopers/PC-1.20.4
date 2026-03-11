/*     */ package net.minecraft.server.packs;
/*     */ 
/*     */ import java.nio.file.Path;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.server.packs.repository.Pack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PathResourcesSupplier
/*     */   implements Pack.ResourcesSupplier
/*     */ {
/*     */   private final Path content;
/*     */   private final boolean isBuiltin;
/*     */   
/*     */   public PathResourcesSupplier(Path $$0, boolean $$1) {
/* 160 */     this.content = $$0;
/* 161 */     this.isBuiltin = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public PackResources openPrimary(String $$0) {
/* 166 */     return new PathPackResources($$0, this.content, this.isBuiltin);
/*     */   }
/*     */ 
/*     */   
/*     */   public PackResources openFull(String $$0, Pack.Info $$1) {
/* 171 */     PackResources $$2 = openPrimary($$0);
/*     */     
/* 173 */     List<String> $$3 = $$1.overlays();
/* 174 */     if ($$3.isEmpty()) {
/* 175 */       return $$2;
/*     */     }
/*     */     
/* 178 */     List<PackResources> $$4 = new ArrayList<>($$3.size());
/* 179 */     for (String $$5 : $$3) {
/* 180 */       Path $$6 = this.content.resolve($$5);
/* 181 */       $$4.add(new PathPackResources($$0, $$6, this.isBuiltin));
/*     */     } 
/*     */     
/* 184 */     return new CompositePackResources($$2, $$4);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\PathPackResources$PathResourcesSupplier.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */