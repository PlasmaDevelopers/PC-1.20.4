/*     */ package net.minecraft.client.multiplayer;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.nbt.NbtIo;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.util.thread.ProcessorMailbox;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class ServerList {
/*  20 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*  22 */   private static final ProcessorMailbox<Runnable> IO_MAILBOX = ProcessorMailbox.create(Util.backgroundExecutor(), "server-list-io");
/*     */   
/*     */   private static final int MAX_HIDDEN_SERVERS = 16;
/*     */   private final Minecraft minecraft;
/*  26 */   private final List<ServerData> serverList = Lists.newArrayList();
/*  27 */   private final List<ServerData> hiddenServerList = Lists.newArrayList();
/*     */   
/*     */   public ServerList(Minecraft $$0) {
/*  30 */     this.minecraft = $$0;
/*     */   }
/*     */   
/*     */   public void load() {
/*     */     try {
/*  35 */       this.serverList.clear();
/*  36 */       this.hiddenServerList.clear();
/*     */       
/*  38 */       CompoundTag $$0 = NbtIo.read(this.minecraft.gameDirectory.toPath().resolve("servers.dat"));
/*  39 */       if ($$0 == null) {
/*     */         return;
/*     */       }
/*     */       
/*  43 */       ListTag $$1 = $$0.getList("servers", 10);
/*  44 */       for (int $$2 = 0; $$2 < $$1.size(); $$2++) {
/*  45 */         CompoundTag $$3 = $$1.getCompound($$2);
/*  46 */         ServerData $$4 = ServerData.read($$3);
/*     */         
/*  48 */         if ($$3.getBoolean("hidden")) {
/*  49 */           this.hiddenServerList.add($$4);
/*     */         } else {
/*  51 */           this.serverList.add($$4);
/*     */         } 
/*     */       } 
/*  54 */     } catch (Exception $$5) {
/*  55 */       LOGGER.error("Couldn't load server list", $$5);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void save() {
/*     */     try {
/*  61 */       ListTag $$0 = new ListTag();
/*  62 */       for (ServerData $$1 : this.serverList) {
/*  63 */         CompoundTag $$2 = $$1.write();
/*     */         
/*  65 */         $$2.putBoolean("hidden", false);
/*  66 */         $$0.add($$2);
/*     */       } 
/*  68 */       for (ServerData $$3 : this.hiddenServerList) {
/*  69 */         CompoundTag $$4 = $$3.write();
/*     */         
/*  71 */         $$4.putBoolean("hidden", true);
/*  72 */         $$0.add($$4);
/*     */       } 
/*     */       
/*  75 */       CompoundTag $$5 = new CompoundTag();
/*  76 */       $$5.put("servers", (Tag)$$0);
/*     */       
/*  78 */       Path $$6 = this.minecraft.gameDirectory.toPath();
/*  79 */       Path $$7 = Files.createTempFile($$6, "servers", ".dat", (FileAttribute<?>[])new FileAttribute[0]);
/*  80 */       NbtIo.write($$5, $$7);
/*     */       
/*  82 */       Path $$8 = $$6.resolve("servers.dat_old");
/*  83 */       Path $$9 = $$6.resolve("servers.dat");
/*  84 */       Util.safeReplaceFile($$9, $$7, $$8);
/*  85 */     } catch (Exception $$10) {
/*  86 */       LOGGER.error("Couldn't save server list", $$10);
/*     */     } 
/*     */   }
/*     */   
/*     */   public ServerData get(int $$0) {
/*  91 */     return this.serverList.get($$0);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ServerData get(String $$0) {
/*  96 */     for (ServerData $$1 : this.serverList) {
/*  97 */       if ($$1.ip.equals($$0)) {
/*  98 */         return $$1;
/*     */       }
/*     */     } 
/* 101 */     for (ServerData $$2 : this.hiddenServerList) {
/* 102 */       if ($$2.ip.equals($$0)) {
/* 103 */         return $$2;
/*     */       }
/*     */     } 
/* 106 */     return null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ServerData unhide(String $$0) {
/* 111 */     for (int $$1 = 0; $$1 < this.hiddenServerList.size(); $$1++) {
/* 112 */       ServerData $$2 = this.hiddenServerList.get($$1);
/* 113 */       if ($$2.ip.equals($$0)) {
/* 114 */         this.hiddenServerList.remove($$1);
/* 115 */         this.serverList.add($$2);
/* 116 */         return $$2;
/*     */       } 
/*     */     } 
/* 119 */     return null;
/*     */   }
/*     */   
/*     */   public void remove(ServerData $$0) {
/* 123 */     if (!this.serverList.remove($$0)) {
/* 124 */       this.hiddenServerList.remove($$0);
/*     */     }
/*     */   }
/*     */   
/*     */   public void add(ServerData $$0, boolean $$1) {
/* 129 */     if ($$1) {
/* 130 */       this.hiddenServerList.add(0, $$0);
/*     */       
/* 132 */       while (this.hiddenServerList.size() > 16) {
/* 133 */         this.hiddenServerList.remove(this.hiddenServerList.size() - 1);
/*     */       }
/*     */     } else {
/* 136 */       this.serverList.add($$0);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int size() {
/* 141 */     return this.serverList.size();
/*     */   }
/*     */   
/*     */   public void swap(int $$0, int $$1) {
/* 145 */     ServerData $$2 = get($$0);
/* 146 */     this.serverList.set($$0, get($$1));
/* 147 */     this.serverList.set($$1, $$2);
/* 148 */     save();
/*     */   }
/*     */   
/*     */   public void replace(int $$0, ServerData $$1) {
/* 152 */     this.serverList.set($$0, $$1);
/*     */   }
/*     */   
/*     */   private static boolean set(ServerData $$0, List<ServerData> $$1) {
/* 156 */     for (int $$2 = 0; $$2 < $$1.size(); $$2++) {
/* 157 */       ServerData $$3 = $$1.get($$2);
/*     */       
/* 159 */       if ($$3.name.equals($$0.name) && $$3.ip.equals($$0.ip)) {
/* 160 */         $$1.set($$2, $$0);
/* 161 */         return true;
/*     */       } 
/*     */     } 
/* 164 */     return false;
/*     */   }
/*     */   
/*     */   public static void saveSingleServer(ServerData $$0) {
/* 168 */     IO_MAILBOX.tell(() -> {
/*     */           ServerList $$1 = new ServerList(Minecraft.getInstance());
/*     */           $$1.load();
/*     */           if (!set($$0, $$1.serverList))
/*     */             set($$0, $$1.hiddenServerList); 
/*     */           $$1.save();
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\multiplayer\ServerList.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */