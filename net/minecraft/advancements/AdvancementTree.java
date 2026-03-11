/*     */ package net.minecraft.advancements;
/*     */ 
/*     */ import com.mojang.logging.LogUtils;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class AdvancementTree {
/*  18 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*  20 */   private final Map<ResourceLocation, AdvancementNode> nodes = (Map<ResourceLocation, AdvancementNode>)new Object2ObjectOpenHashMap();
/*  21 */   private final Set<AdvancementNode> roots = (Set<AdvancementNode>)new ObjectLinkedOpenHashSet();
/*  22 */   private final Set<AdvancementNode> tasks = (Set<AdvancementNode>)new ObjectLinkedOpenHashSet();
/*     */   @Nullable
/*     */   private Listener listener;
/*     */   
/*     */   private void remove(AdvancementNode $$0) {
/*  27 */     for (AdvancementNode $$1 : $$0.children()) {
/*  28 */       remove($$1);
/*     */     }
/*     */     
/*  31 */     LOGGER.info("Forgot about advancement {}", $$0.holder());
/*  32 */     this.nodes.remove($$0.holder().id());
/*  33 */     if ($$0.parent() == null) {
/*  34 */       this.roots.remove($$0);
/*  35 */       if (this.listener != null) {
/*  36 */         this.listener.onRemoveAdvancementRoot($$0);
/*     */       }
/*     */     } else {
/*  39 */       this.tasks.remove($$0);
/*  40 */       if (this.listener != null) {
/*  41 */         this.listener.onRemoveAdvancementTask($$0);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void remove(Set<ResourceLocation> $$0) {
/*  47 */     for (ResourceLocation $$1 : $$0) {
/*  48 */       AdvancementNode $$2 = this.nodes.get($$1);
/*  49 */       if ($$2 == null) {
/*  50 */         LOGGER.warn("Told to remove advancement {} but I don't know what that is", $$1); continue;
/*     */       } 
/*  52 */       remove($$2);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAll(Collection<AdvancementHolder> $$0) {
/*  58 */     List<AdvancementHolder> $$1 = new ArrayList<>($$0);
/*  59 */     while (!$$1.isEmpty()) {
/*  60 */       if (!$$1.removeIf(this::tryInsert)) {
/*  61 */         LOGGER.error("Couldn't load advancements: {}", $$1);
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*  66 */     LOGGER.info("Loaded {} advancements", Integer.valueOf(this.nodes.size()));
/*     */   }
/*     */   
/*     */   private boolean tryInsert(AdvancementHolder $$0) {
/*  70 */     Optional<ResourceLocation> $$1 = $$0.value().parent();
/*  71 */     Objects.requireNonNull(this.nodes); AdvancementNode $$2 = $$1.<AdvancementNode>map(this.nodes::get).orElse(null);
/*  72 */     if ($$2 == null && $$1.isPresent()) {
/*  73 */       return false;
/*     */     }
/*     */     
/*  76 */     AdvancementNode $$3 = new AdvancementNode($$0, $$2);
/*  77 */     if ($$2 != null) {
/*  78 */       $$2.addChild($$3);
/*     */     }
/*     */     
/*  81 */     this.nodes.put($$0.id(), $$3);
/*  82 */     if ($$2 == null) {
/*  83 */       this.roots.add($$3);
/*  84 */       if (this.listener != null) {
/*  85 */         this.listener.onAddAdvancementRoot($$3);
/*     */       }
/*     */     } else {
/*  88 */       this.tasks.add($$3);
/*  89 */       if (this.listener != null) {
/*  90 */         this.listener.onAddAdvancementTask($$3);
/*     */       }
/*     */     } 
/*     */     
/*  94 */     return true;
/*     */   }
/*     */   
/*     */   public void clear() {
/*  98 */     this.nodes.clear();
/*  99 */     this.roots.clear();
/* 100 */     this.tasks.clear();
/* 101 */     if (this.listener != null) {
/* 102 */       this.listener.onAdvancementsCleared();
/*     */     }
/*     */   }
/*     */   
/*     */   public Iterable<AdvancementNode> roots() {
/* 107 */     return this.roots;
/*     */   }
/*     */   
/*     */   public Collection<AdvancementNode> nodes() {
/* 111 */     return this.nodes.values();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public AdvancementNode get(ResourceLocation $$0) {
/* 116 */     return this.nodes.get($$0);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public AdvancementNode get(AdvancementHolder $$0) {
/* 121 */     return this.nodes.get($$0.id());
/*     */   }
/*     */   
/*     */   public void setListener(@Nullable Listener $$0) {
/* 125 */     this.listener = $$0;
/* 126 */     if ($$0 != null) {
/* 127 */       for (AdvancementNode $$1 : this.roots) {
/* 128 */         $$0.onAddAdvancementRoot($$1);
/*     */       }
/* 130 */       for (AdvancementNode $$2 : this.tasks)
/* 131 */         $$0.onAddAdvancementTask($$2); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static interface Listener {
/*     */     void onAddAdvancementRoot(AdvancementNode param1AdvancementNode);
/*     */     
/*     */     void onRemoveAdvancementRoot(AdvancementNode param1AdvancementNode);
/*     */     
/*     */     void onAddAdvancementTask(AdvancementNode param1AdvancementNode);
/*     */     
/*     */     void onRemoveAdvancementTask(AdvancementNode param1AdvancementNode);
/*     */     
/*     */     void onAdvancementsCleared();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\AdvancementTree.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */