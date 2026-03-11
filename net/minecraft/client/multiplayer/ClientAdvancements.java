/*     */ package net.minecraft.client.multiplayer;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.advancements.AdvancementHolder;
/*     */ import net.minecraft.advancements.AdvancementNode;
/*     */ import net.minecraft.advancements.AdvancementProgress;
/*     */ import net.minecraft.advancements.AdvancementTree;
/*     */ import net.minecraft.advancements.DisplayInfo;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.components.toasts.AdvancementToast;
/*     */ import net.minecraft.client.gui.components.toasts.Toast;
/*     */ import net.minecraft.client.telemetry.WorldSessionTelemetryManager;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ClientboundUpdateAdvancementsPacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundSeenAdvancementsPacket;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class ClientAdvancements {
/*  23 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private final Minecraft minecraft;
/*     */   private final WorldSessionTelemetryManager telemetryManager;
/*  27 */   private final AdvancementTree tree = new AdvancementTree();
/*     */   
/*  29 */   private final Map<AdvancementHolder, AdvancementProgress> progress = (Map<AdvancementHolder, AdvancementProgress>)new Object2ObjectOpenHashMap();
/*     */   
/*     */   @Nullable
/*     */   private Listener listener;
/*     */   @Nullable
/*     */   private AdvancementHolder selectedTab;
/*     */   
/*     */   public ClientAdvancements(Minecraft $$0, WorldSessionTelemetryManager $$1) {
/*  37 */     this.minecraft = $$0;
/*  38 */     this.telemetryManager = $$1;
/*     */   }
/*     */   
/*     */   public void update(ClientboundUpdateAdvancementsPacket $$0) {
/*  42 */     if ($$0.shouldReset()) {
/*  43 */       this.tree.clear();
/*  44 */       this.progress.clear();
/*     */     } 
/*     */     
/*  47 */     this.tree.remove($$0.getRemoved());
/*  48 */     this.tree.addAll($$0.getAdded());
/*  49 */     for (Map.Entry<ResourceLocation, AdvancementProgress> $$1 : (Iterable<Map.Entry<ResourceLocation, AdvancementProgress>>)$$0.getProgress().entrySet()) {
/*  50 */       AdvancementNode $$2 = this.tree.get($$1.getKey());
/*  51 */       if ($$2 != null) {
/*  52 */         AdvancementProgress $$3 = $$1.getValue();
/*  53 */         $$3.update($$2.advancement().requirements());
/*  54 */         this.progress.put($$2.holder(), $$3);
/*  55 */         if (this.listener != null) {
/*  56 */           this.listener.onUpdateAdvancementProgress($$2, $$3);
/*     */         }
/*  58 */         if (!$$0.shouldReset() && $$3.isDone()) {
/*  59 */           if (this.minecraft.level != null) {
/*  60 */             this.telemetryManager.onAdvancementDone(this.minecraft.level, $$2.holder());
/*     */           }
/*  62 */           Optional<DisplayInfo> $$4 = $$2.advancement().display();
/*  63 */           if ($$4.isPresent() && ((DisplayInfo)$$4.get()).shouldShowToast())
/*  64 */             this.minecraft.getToasts().addToast((Toast)new AdvancementToast($$2.holder())); 
/*     */         } 
/*     */         continue;
/*     */       } 
/*  68 */       LOGGER.warn("Server informed client about progress for unknown advancement {}", $$1.getKey());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public AdvancementTree getTree() {
/*  74 */     return this.tree;
/*     */   }
/*     */   
/*     */   public void setSelectedTab(@Nullable AdvancementHolder $$0, boolean $$1) {
/*  78 */     ClientPacketListener $$2 = this.minecraft.getConnection();
/*  79 */     if ($$2 != null && $$0 != null && $$1) {
/*  80 */       $$2.send((Packet<?>)ServerboundSeenAdvancementsPacket.openedTab($$0));
/*     */     }
/*  82 */     if (this.selectedTab != $$0) {
/*  83 */       this.selectedTab = $$0;
/*  84 */       if (this.listener != null) {
/*  85 */         this.listener.onSelectedTabChanged($$0);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setListener(@Nullable Listener $$0) {
/*  91 */     this.listener = $$0;
/*  92 */     this.tree.setListener($$0);
/*  93 */     if ($$0 != null) {
/*  94 */       this.progress.forEach(($$1, $$2) -> {
/*     */             AdvancementNode $$3 = this.tree.get($$1);
/*     */             if ($$3 != null) {
/*     */               $$0.onUpdateAdvancementProgress($$3, $$2);
/*     */             }
/*     */           });
/* 100 */       $$0.onSelectedTabChanged(this.selectedTab);
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public AdvancementHolder get(ResourceLocation $$0) {
/* 106 */     AdvancementNode $$1 = this.tree.get($$0);
/* 107 */     return ($$1 != null) ? $$1.holder() : null;
/*     */   }
/*     */   
/*     */   public static interface Listener extends AdvancementTree.Listener {
/*     */     void onUpdateAdvancementProgress(AdvancementNode param1AdvancementNode, AdvancementProgress param1AdvancementProgress);
/*     */     
/*     */     void onSelectedTabChanged(@Nullable AdvancementHolder param1AdvancementHolder);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\multiplayer\ClientAdvancements.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */