/*     */ package net.minecraft.client.gui.screens.packs;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.hash.Hashing;
/*     */ import com.mojang.blaze3d.platform.NativeImage;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.file.DirectoryStream;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.LinkOption;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.StandardWatchEventKinds;
/*     */ import java.nio.file.WatchEvent;
/*     */ import java.nio.file.WatchKey;
/*     */ import java.nio.file.WatchService;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.ComponentPath;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.AbstractSelectionList;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.Tooltip;
/*     */ import net.minecraft.client.gui.components.events.ContainerEventHandler;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.components.toasts.SystemToast;
/*     */ import net.minecraft.client.gui.screens.AlertScreen;
/*     */ import net.minecraft.client.gui.screens.ConfirmScreen;
/*     */ import net.minecraft.client.gui.screens.NoticeWithLinkScreen;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.client.renderer.texture.AbstractTexture;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.packs.PackResources;
/*     */ import net.minecraft.server.packs.repository.Pack;
/*     */ import net.minecraft.server.packs.repository.PackDetector;
/*     */ import net.minecraft.server.packs.repository.PackRepository;
/*     */ import net.minecraft.server.packs.resources.IoSupplier;
/*     */ import net.minecraft.world.level.validation.DirectoryValidator;
/*     */ import net.minecraft.world.level.validation.ForbiddenSymlinkInfo;
/*     */ import org.apache.commons.lang3.mutable.MutableBoolean;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class PackSelectionScreen extends Screen {
/*  56 */   static final Logger LOGGER = LogUtils.getLogger();
/*     */   private static final int LIST_WIDTH = 200;
/*  58 */   private static final Component DRAG_AND_DROP = (Component)Component.translatable("pack.dropInfo").withStyle(ChatFormatting.GRAY);
/*  59 */   private static final Component DIRECTORY_BUTTON_TOOLTIP = (Component)Component.translatable("pack.folderInfo");
/*     */   
/*     */   private static final int RELOAD_COOLDOWN = 20;
/*  62 */   private static final ResourceLocation DEFAULT_ICON = new ResourceLocation("textures/misc/unknown_pack.png");
/*     */   
/*     */   private final PackSelectionModel model;
/*     */   
/*     */   @Nullable
/*     */   private Watcher watcher;
/*     */   
/*     */   private long ticksToReload;
/*     */   private TransferableSelectionList availablePackList;
/*     */   private TransferableSelectionList selectedPackList;
/*     */   private final Path packDir;
/*     */   private Button doneButton;
/*  74 */   private final Map<String, ResourceLocation> packIcons = Maps.newHashMap();
/*     */   
/*     */   public PackSelectionScreen(PackRepository $$0, Consumer<PackRepository> $$1, Path $$2, Component $$3) {
/*  77 */     super($$3);
/*  78 */     this.model = new PackSelectionModel(this::populateLists, this::getPackIcon, $$0, $$1);
/*  79 */     this.packDir = $$2;
/*  80 */     this.watcher = Watcher.create($$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClose() {
/*  85 */     this.model.commit();
/*  86 */     closeWatcher();
/*     */   }
/*     */   
/*     */   private void closeWatcher() {
/*  90 */     if (this.watcher != null) {
/*     */       try {
/*  92 */         this.watcher.close();
/*  93 */         this.watcher = null;
/*  94 */       } catch (Exception exception) {}
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void init() {
/* 101 */     this.availablePackList = (TransferableSelectionList)addRenderableWidget((GuiEventListener)new TransferableSelectionList(this.minecraft, this, 200, this.height, (Component)Component.translatable("pack.available.title")));
/* 102 */     this.availablePackList.setX(this.width / 2 - 4 - 200);
/*     */     
/* 104 */     this.selectedPackList = (TransferableSelectionList)addRenderableWidget((GuiEventListener)new TransferableSelectionList(this.minecraft, this, 200, this.height, (Component)Component.translatable("pack.selected.title")));
/* 105 */     this.selectedPackList.setX(this.width / 2 + 4);
/*     */     
/* 107 */     addRenderableWidget(
/* 108 */         (GuiEventListener)Button.builder((Component)Component.translatable("pack.openFolder"), $$0 -> Util.getPlatform().openUri(this.packDir.toUri()))
/* 109 */         .bounds(this.width / 2 - 154, this.height - 48, 150, 20)
/* 110 */         .tooltip(Tooltip.create(DIRECTORY_BUTTON_TOOLTIP))
/* 111 */         .build());
/*     */     
/* 113 */     this.doneButton = (Button)addRenderableWidget(
/* 114 */         (GuiEventListener)Button.builder(CommonComponents.GUI_DONE, $$0 -> onClose())
/* 115 */         .bounds(this.width / 2 + 4, this.height - 48, 150, 20)
/* 116 */         .build());
/*     */ 
/*     */     
/* 119 */     reload();
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 124 */     if (this.watcher != null) {
/*     */       try {
/* 126 */         if (this.watcher.pollForChanges())
/*     */         {
/* 128 */           this.ticksToReload = 20L;
/*     */         }
/* 130 */       } catch (IOException $$0) {
/* 131 */         LOGGER.warn("Failed to poll for directory {} changes, stopping", this.packDir);
/* 132 */         closeWatcher();
/*     */       } 
/*     */     }
/*     */     
/* 136 */     if (this.ticksToReload > 0L && 
/* 137 */       --this.ticksToReload == 0L) {
/* 138 */       reload();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void populateLists() {
/* 144 */     updateList(this.selectedPackList, this.model.getSelected());
/* 145 */     updateList(this.availablePackList, this.model.getUnselected());
/* 146 */     this.doneButton.active = !this.selectedPackList.children().isEmpty();
/*     */   }
/*     */   
/*     */   private void updateList(TransferableSelectionList $$0, Stream<PackSelectionModel.Entry> $$1) {
/* 150 */     $$0.children().clear();
/* 151 */     TransferableSelectionList.PackEntry $$2 = (TransferableSelectionList.PackEntry)$$0.getSelected();
/* 152 */     String $$3 = ($$2 == null) ? "" : $$2.getPackId();
/* 153 */     $$0.setSelected(null);
/* 154 */     $$1.forEach($$2 -> {
/*     */           TransferableSelectionList.PackEntry $$3 = new TransferableSelectionList.PackEntry(this.minecraft, $$0, $$2);
/*     */           $$0.children().add($$3);
/*     */           if ($$2.getId().equals($$1)) {
/*     */             $$0.setSelected((AbstractSelectionList.Entry)$$3);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void updateFocus(TransferableSelectionList $$0) {
/* 164 */     TransferableSelectionList $$1 = (this.selectedPackList == $$0) ? this.availablePackList : this.selectedPackList;
/* 165 */     changeFocus(ComponentPath.path((GuiEventListener)$$1.getFirstElement(), new ContainerEventHandler[] { (ContainerEventHandler)$$1, (ContainerEventHandler)this }));
/*     */   }
/*     */   
/*     */   public void clearSelected() {
/* 169 */     this.selectedPackList.setSelected(null);
/* 170 */     this.availablePackList.setSelected(null);
/*     */   }
/*     */   
/*     */   private void reload() {
/* 174 */     this.model.findNewPacks();
/* 175 */     populateLists();
/* 176 */     this.ticksToReload = 0L;
/* 177 */     this.packIcons.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 182 */     super.render($$0, $$1, $$2, $$3);
/*     */     
/* 184 */     $$0.drawCenteredString(this.font, this.title, this.width / 2, 8, 16777215);
/* 185 */     $$0.drawCenteredString(this.font, DRAG_AND_DROP, this.width / 2, 20, 16777215);
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderBackground(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 190 */     renderDirtBackground($$0);
/*     */   }
/*     */   
/*     */   protected static void copyPacks(Minecraft $$0, List<Path> $$1, Path $$2) {
/* 194 */     MutableBoolean $$3 = new MutableBoolean();
/* 195 */     $$1.forEach($$2 -> { try { Stream<Path> $$3 = Files.walk($$2, new java.nio.file.FileVisitOption[0]); try { $$3.forEach(()); if ($$3 != null)
/* 196 */                 $$3.close();  } catch (Throwable throwable) { if ($$3 != null) try { $$3.close(); } catch (Throwable throwable1)
/*     */                 { throwable.addSuppressed(throwable1); }
/*     */               
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*     */               throw throwable; }
/*     */              }
/* 205 */           catch (IOException $$4)
/*     */           { LOGGER.warn("Failed to copy datapack file from {} to {}", $$2, $$0);
/*     */             $$1.setTrue(); }
/*     */         
/*     */         });
/* 210 */     if ($$3.isTrue()) {
/* 211 */       SystemToast.onPackCopyFailure($$0, $$2.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onFilesDrop(List<Path> $$0) {
/* 217 */     String $$1 = extractPackNames($$0).collect(Collectors.joining(", "));
/* 218 */     this.minecraft.setScreen((Screen)new ConfirmScreen($$1 -> {
/*     */             if ($$1) {
/*     */               List<Path> $$2 = new ArrayList<>($$0.size());
/*     */               Set<Path> $$3 = new HashSet<>($$0);
/*     */               PackDetector<Path> $$4 = new PackDetector<Path>(this.minecraft.directoryValidator())
/*     */                 {
/*     */                   protected Path createZipPack(Path $$0) {
/* 225 */                     return $$0;
/*     */                   }
/*     */ 
/*     */                   
/*     */                   protected Path createDirectoryPack(Path $$0) {
/* 230 */                     return $$0;
/*     */                   }
/*     */                 };
/*     */               
/*     */               List<ForbiddenSymlinkInfo> $$5 = new ArrayList<>();
/*     */               for (Path $$6 : $$0) {
/*     */                 try {
/*     */                   Path $$7 = (Path)$$4.detectPackResources($$6, $$5);
/*     */                   if ($$7 == null) {
/*     */                     LOGGER.warn("Path {} does not seem like pack", $$6);
/*     */                     continue;
/*     */                   } 
/*     */                   $$2.add($$7);
/*     */                   $$3.remove($$7);
/* 244 */                 } catch (IOException $$8) {
/*     */                   LOGGER.warn("Failed to check {} for packs", $$6, $$8);
/*     */                 } 
/*     */               } 
/*     */               
/*     */               if (!$$5.isEmpty()) {
/*     */                 this.minecraft.setScreen(NoticeWithLinkScreen.createPackSymlinkWarningScreen(()));
/*     */                 
/*     */                 return;
/*     */               } 
/*     */               
/*     */               if (!$$2.isEmpty()) {
/*     */                 copyPacks(this.minecraft, $$2, this.packDir);
/*     */                 
/*     */                 reload();
/*     */               } 
/*     */               
/*     */               if (!$$3.isEmpty()) {
/*     */                 String $$9 = extractPackNames($$3).collect(Collectors.joining(", "));
/*     */                 
/*     */                 this.minecraft.setScreen((Screen)new AlertScreen((), (Component)Component.translatable("pack.dropRejected.title"), (Component)Component.translatable("pack.dropRejected.message", new Object[] { $$9 })));
/*     */                 
/*     */                 return;
/*     */               } 
/*     */             } 
/*     */             
/*     */             this.minecraft.setScreen(this);
/* 271 */           }(Component)Component.translatable("pack.dropConfirm"), 
/* 272 */           (Component)Component.literal($$1)));
/*     */   }
/*     */   
/*     */   private static Stream<String> extractPackNames(Collection<Path> $$0) {
/* 276 */     return $$0.stream().map(Path::getFileName).map(Path::toString);
/*     */   }
/*     */   private ResourceLocation loadPackIcon(TextureManager $$0, Pack $$1) {
/*     */     
/* 280 */     try { PackResources $$2 = $$1.open(); 
/* 281 */       try { IoSupplier<InputStream> $$3 = $$2.getRootResource(new String[] { "pack.png" });
/* 282 */         if ($$3 == null)
/* 283 */         { ResourceLocation resourceLocation = DEFAULT_ICON;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 293 */           if ($$2 != null) $$2.close();  return resourceLocation; }  String $$4 = $$1.getId(); ResourceLocation $$5 = new ResourceLocation("minecraft", "pack/" + Util.sanitizeName($$4, ResourceLocation::validPathChar) + "/" + Hashing.sha1().hashUnencodedChars($$4) + "/icon"); InputStream $$6 = (InputStream)$$3.get(); try { NativeImage $$7 = NativeImage.read($$6); $$0.register($$5, (AbstractTexture)new DynamicTexture($$7)); ResourceLocation resourceLocation = $$5; if ($$6 != null) $$6.close();  if ($$2 != null) $$2.close();  return resourceLocation; } catch (Throwable throwable) { if ($$6 != null) try { $$6.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Throwable throwable) { if ($$2 != null) try { $$2.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Exception $$8)
/* 294 */     { LOGGER.warn("Failed to load icon from pack {}", $$1.getId(), $$8);
/*     */       
/* 296 */       return DEFAULT_ICON; }
/*     */   
/*     */   }
/*     */   private ResourceLocation getPackIcon(Pack $$0) {
/* 300 */     return this.packIcons.computeIfAbsent($$0.getId(), $$1 -> loadPackIcon(this.minecraft.getTextureManager(), $$0));
/*     */   }
/*     */   
/*     */   private static class Watcher implements AutoCloseable {
/*     */     private final WatchService watcher;
/*     */     private final Path packPath;
/*     */     
/*     */     public Watcher(Path $$0) throws IOException {
/* 308 */       this.packPath = $$0;
/* 309 */       this.watcher = $$0.getFileSystem().newWatchService();
/*     */ 
/*     */       
/* 312 */       try { watchDir($$0);
/*     */ 
/*     */         
/* 315 */         DirectoryStream<Path> $$1 = Files.newDirectoryStream($$0); 
/* 316 */         try { for (Path $$2 : $$1) {
/* 317 */             if (Files.isDirectory($$2, new LinkOption[] { LinkOption.NOFOLLOW_LINKS })) {
/* 318 */               watchDir($$2);
/*     */             }
/*     */           } 
/* 321 */           if ($$1 != null) $$1.close();  } catch (Throwable throwable) { if ($$1 != null)
/* 322 */             try { $$1.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Exception $$3)
/* 323 */       { this.watcher.close();
/* 324 */         throw $$3; }
/*     */     
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     public static Watcher create(Path $$0) {
/*     */       try {
/* 331 */         return new Watcher($$0);
/* 332 */       } catch (IOException $$1) {
/* 333 */         PackSelectionScreen.LOGGER.warn("Failed to initialize pack directory {} monitoring", $$0, $$1);
/* 334 */         return null;
/*     */       } 
/*     */     }
/*     */     
/*     */     private void watchDir(Path $$0) throws IOException {
/* 339 */       $$0.register(this.watcher, (WatchEvent.Kind<?>[])new WatchEvent.Kind[] { StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY });
/*     */     }
/*     */     
/*     */     public boolean pollForChanges() throws IOException {
/* 343 */       boolean $$0 = false;
/*     */       
/*     */       WatchKey $$1;
/*     */       
/* 347 */       while (($$1 = this.watcher.poll()) != null) {
/* 348 */         List<WatchEvent<?>> $$2 = $$1.pollEvents();
/* 349 */         for (WatchEvent<?> $$3 : $$2) {
/* 350 */           $$0 = true;
/*     */           
/* 352 */           Path $$4 = this.packPath.resolve((Path)$$3.context());
/* 353 */           if ($$1.watchable() == this.packPath && $$3.kind() == StandardWatchEventKinds.ENTRY_CREATE && Files.isDirectory($$4, new LinkOption[] { LinkOption.NOFOLLOW_LINKS })) {
/* 354 */             watchDir($$4);
/*     */           }
/*     */         } 
/*     */         
/* 358 */         $$1.reset();
/*     */       } 
/*     */       
/* 361 */       return $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public void close() throws IOException {
/* 366 */       this.watcher.close();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\packs\PackSelectionScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */