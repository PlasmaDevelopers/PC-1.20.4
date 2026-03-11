/*    */ package net.minecraft.server.packs;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.HashSet;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Objects;
/*    */ import java.util.Set;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
/*    */ import net.minecraft.server.packs.resources.IoSupplier;
/*    */ 
/*    */ public class CompositePackResources
/*    */   implements PackResources {
/*    */   private final PackResources primaryPackResources;
/*    */   private final List<PackResources> packResourcesStack;
/*    */   
/*    */   public CompositePackResources(PackResources $$0, List<PackResources> $$1) {
/* 24 */     this.primaryPackResources = $$0;
/*    */     
/* 26 */     List<PackResources> $$2 = new ArrayList<>($$1.size() + 1);
/* 27 */     $$2.addAll(Lists.reverse($$1));
/* 28 */     $$2.add($$0);
/* 29 */     this.packResourcesStack = List.copyOf($$2);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public IoSupplier<InputStream> getRootResource(String... $$0) {
/* 35 */     return this.primaryPackResources.getRootResource($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public IoSupplier<InputStream> getResource(PackType $$0, ResourceLocation $$1) {
/* 41 */     for (PackResources $$2 : this.packResourcesStack) {
/* 42 */       IoSupplier<InputStream> $$3 = $$2.getResource($$0, $$1);
/* 43 */       if ($$3 != null) {
/* 44 */         return $$3;
/*    */       }
/*    */     } 
/*    */     
/* 48 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void listResources(PackType $$0, String $$1, String $$2, PackResources.ResourceOutput $$3) {
/* 53 */     Map<ResourceLocation, IoSupplier<InputStream>> $$4 = new HashMap<>();
/* 54 */     for (PackResources $$5 : this.packResourcesStack) {
/* 55 */       Objects.requireNonNull($$4); $$5.listResources($$0, $$1, $$2, $$4::putIfAbsent);
/*    */     } 
/* 57 */     $$4.forEach($$3);
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<String> getNamespaces(PackType $$0) {
/* 62 */     Set<String> $$1 = new HashSet<>();
/* 63 */     for (PackResources $$2 : this.packResourcesStack) {
/* 64 */       $$1.addAll($$2.getNamespaces($$0));
/*    */     }
/* 66 */     return $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public <T> T getMetadataSection(MetadataSectionSerializer<T> $$0) throws IOException {
/* 72 */     return this.primaryPackResources.getMetadataSection($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public String packId() {
/* 77 */     return this.primaryPackResources.packId();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isBuiltin() {
/* 82 */     return this.primaryPackResources.isBuiltin();
/*    */   }
/*    */ 
/*    */   
/*    */   public void close() {
/* 87 */     this.packResourcesStack.forEach(PackResources::close);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\CompositePackResources.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */