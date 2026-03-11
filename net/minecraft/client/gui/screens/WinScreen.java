/*     */ package net.minecraft.client.gui.screens;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.mojang.blaze3d.platform.GlStateManager;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
/*     */ import it.unimi.dsi.fastutil.ints.IntSet;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ import java.util.List;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.client.GameNarrator;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.LogoRenderer;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.sounds.Music;
/*     */ import net.minecraft.sounds.Musics;
/*     */ import net.minecraft.util.FormattedCharSequence;
/*     */ import net.minecraft.util.GsonHelper;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class WinScreen
/*     */   extends Screen {
/*  33 */   private static final Logger LOGGER = LogUtils.getLogger();
/*  34 */   private static final ResourceLocation VIGNETTE_LOCATION = new ResourceLocation("textures/misc/vignette.png");
/*  35 */   private static final Component SECTION_HEADING = (Component)Component.literal("============").withStyle(ChatFormatting.WHITE);
/*     */   private static final String NAME_PREFIX = "           ";
/*  37 */   private static final String OBFUSCATE_TOKEN = "" + ChatFormatting.WHITE + ChatFormatting.WHITE + ChatFormatting.OBFUSCATED + ChatFormatting.GREEN;
/*     */   
/*     */   private static final float SPEEDUP_FACTOR = 5.0F;
/*     */   
/*     */   private static final float SPEEDUP_FACTOR_FAST = 15.0F;
/*     */   private final boolean poem;
/*     */   private final Runnable onFinished;
/*     */   private float scroll;
/*     */   private List<FormattedCharSequence> lines;
/*     */   private IntSet centeredLines;
/*     */   private int totalScrollLength;
/*     */   private boolean speedupActive;
/*  49 */   private final IntSet speedupModifiers = (IntSet)new IntOpenHashSet();
/*     */   
/*     */   private float scrollSpeed;
/*     */   private final float unmodifiedScrollSpeed;
/*     */   private int direction;
/*  54 */   private final LogoRenderer logoRenderer = new LogoRenderer(false);
/*     */   
/*     */   public WinScreen(boolean $$0, Runnable $$1) {
/*  57 */     super(GameNarrator.NO_TITLE);
/*  58 */     this.poem = $$0;
/*  59 */     this.onFinished = $$1;
/*  60 */     if (!$$0) {
/*  61 */       this.unmodifiedScrollSpeed = 0.75F;
/*     */     } else {
/*  63 */       this.unmodifiedScrollSpeed = 0.5F;
/*     */     } 
/*  65 */     this.direction = 1;
/*     */     
/*  67 */     this.scrollSpeed = this.unmodifiedScrollSpeed;
/*     */   }
/*     */   
/*     */   private float calculateScrollSpeed() {
/*  71 */     if (this.speedupActive) {
/*  72 */       return this.unmodifiedScrollSpeed * (5.0F + this.speedupModifiers.size() * 15.0F) * this.direction;
/*     */     }
/*  74 */     return this.unmodifiedScrollSpeed * this.direction;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/*  79 */     this.minecraft.getMusicManager().tick();
/*  80 */     this.minecraft.getSoundManager().tick(false);
/*  81 */     float $$0 = (this.totalScrollLength + this.height + this.height + 24);
/*  82 */     if (this.scroll > $$0) {
/*  83 */       respawn();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean keyPressed(int $$0, int $$1, int $$2) {
/*  89 */     if ($$0 == 265) {
/*  90 */       this.direction = -1;
/*  91 */     } else if ($$0 == 341 || $$0 == 345) {
/*  92 */       this.speedupModifiers.add($$0);
/*  93 */     } else if ($$0 == 32) {
/*  94 */       this.speedupActive = true;
/*     */     } 
/*  96 */     this.scrollSpeed = calculateScrollSpeed();
/*     */     
/*  98 */     return super.keyPressed($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean keyReleased(int $$0, int $$1, int $$2) {
/* 103 */     if ($$0 == 265) {
/* 104 */       this.direction = 1;
/*     */     }
/* 106 */     if ($$0 == 32) {
/* 107 */       this.speedupActive = false;
/* 108 */     } else if ($$0 == 341 || $$0 == 345) {
/* 109 */       this.speedupModifiers.remove($$0);
/*     */     } 
/* 111 */     this.scrollSpeed = calculateScrollSpeed();
/*     */     
/* 113 */     return super.keyReleased($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClose() {
/* 118 */     respawn();
/*     */   }
/*     */   
/*     */   private void respawn() {
/* 122 */     this.onFinished.run();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init() {
/* 127 */     if (this.lines != null) {
/*     */       return;
/*     */     }
/*     */     
/* 131 */     this.lines = Lists.newArrayList();
/* 132 */     this.centeredLines = (IntSet)new IntOpenHashSet();
/* 133 */     if (this.poem) {
/* 134 */       wrapCreditsIO("texts/end.txt", this::addPoemFile);
/*     */     }
/* 136 */     wrapCreditsIO("texts/credits.json", this::addCreditsFile);
/* 137 */     if (this.poem) {
/* 138 */       wrapCreditsIO("texts/postcredits.txt", this::addPoemFile);
/*     */     }
/*     */     
/* 141 */     this.totalScrollLength = this.lines.size() * 12;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void wrapCreditsIO(String $$0, CreditsReader $$1) {
/*     */     
/* 150 */     try { Reader $$2 = this.minecraft.getResourceManager().openAsReader(new ResourceLocation($$0)); 
/* 151 */       try { $$1.read($$2);
/* 152 */         if ($$2 != null) $$2.close();  } catch (Throwable throwable) { if ($$2 != null) try { $$2.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Exception $$3)
/* 153 */     { LOGGER.error("Couldn't load credits", $$3); }
/*     */   
/*     */   }
/*     */   
/*     */   private void addPoemFile(Reader $$0) throws IOException {
/* 158 */     BufferedReader $$1 = new BufferedReader($$0);
/* 159 */     RandomSource $$2 = RandomSource.create(8124371L);
/*     */     
/*     */     String $$3;
/* 162 */     while (($$3 = $$1.readLine()) != null) {
/* 163 */       $$3 = $$3.replaceAll("PLAYERNAME", this.minecraft.getUser().getName());
/*     */       
/*     */       int $$4;
/* 166 */       while (($$4 = $$3.indexOf(OBFUSCATE_TOKEN)) != -1) {
/* 167 */         String $$5 = $$3.substring(0, $$4);
/* 168 */         String $$6 = $$3.substring($$4 + OBFUSCATE_TOKEN.length());
/* 169 */         $$3 = $$5 + $$5 + ChatFormatting.WHITE + ChatFormatting.OBFUSCATED + "XXXXXXXX".substring(0, $$2.nextInt(4) + 3);
/*     */       } 
/* 171 */       addPoemLines($$3);
/* 172 */       addEmptyLine();
/*     */     } 
/*     */     
/* 175 */     for (int $$7 = 0; $$7 < 8; $$7++) {
/* 176 */       addEmptyLine();
/*     */     }
/*     */   }
/*     */   
/*     */   private void addCreditsFile(Reader $$0) {
/* 181 */     JsonArray $$1 = GsonHelper.parseArray($$0);
/* 182 */     for (JsonElement $$2 : $$1) {
/* 183 */       JsonObject $$3 = $$2.getAsJsonObject();
/* 184 */       String $$4 = $$3.get("section").getAsString();
/* 185 */       addCreditsLine(SECTION_HEADING, true);
/* 186 */       addCreditsLine((Component)Component.literal($$4).withStyle(ChatFormatting.YELLOW), true);
/* 187 */       addCreditsLine(SECTION_HEADING, true);
/* 188 */       addEmptyLine();
/* 189 */       addEmptyLine();
/*     */       
/* 191 */       JsonArray $$5 = $$3.getAsJsonArray("disciplines");
/* 192 */       for (JsonElement $$6 : $$5) {
/* 193 */         JsonObject $$7 = $$6.getAsJsonObject();
/* 194 */         String $$8 = $$7.get("discipline").getAsString();
/* 195 */         if (StringUtils.isNotEmpty($$8)) {
/* 196 */           addCreditsLine((Component)Component.literal($$8).withStyle(ChatFormatting.YELLOW), true);
/* 197 */           addEmptyLine();
/* 198 */           addEmptyLine();
/*     */         } 
/*     */         
/* 201 */         JsonArray $$9 = $$7.getAsJsonArray("titles");
/* 202 */         for (JsonElement $$10 : $$9) {
/* 203 */           JsonObject $$11 = $$10.getAsJsonObject();
/* 204 */           String $$12 = $$11.get("title").getAsString();
/* 205 */           JsonArray $$13 = $$11.getAsJsonArray("names");
/* 206 */           addCreditsLine((Component)Component.literal($$12).withStyle(ChatFormatting.GRAY), false);
/* 207 */           for (JsonElement $$14 : $$13) {
/* 208 */             String $$15 = $$14.getAsString();
/* 209 */             addCreditsLine((Component)Component.literal("           ").append($$15).withStyle(ChatFormatting.WHITE), false);
/*     */           } 
/* 211 */           addEmptyLine();
/* 212 */           addEmptyLine();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void addEmptyLine() {
/* 219 */     this.lines.add(FormattedCharSequence.EMPTY);
/*     */   }
/*     */   
/*     */   private void addPoemLines(String $$0) {
/* 223 */     this.lines.addAll(this.minecraft.font.split((FormattedText)Component.literal($$0), 256));
/*     */   }
/*     */   
/*     */   private void addCreditsLine(Component $$0, boolean $$1) {
/* 227 */     if ($$1) {
/* 228 */       this.centeredLines.add(this.lines.size());
/*     */     }
/* 230 */     this.lines.add($$0.getVisualOrderText());
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 235 */     this.scroll = Math.max(0.0F, this.scroll + $$3 * this.scrollSpeed);
/*     */     
/* 237 */     super.render($$0, $$1, $$2, $$3);
/*     */     
/* 239 */     int $$4 = this.width / 2 - 128;
/* 240 */     int $$5 = this.height + 50;
/*     */     
/* 242 */     float $$6 = -this.scroll;
/*     */     
/* 244 */     $$0.pose().pushPose();
/* 245 */     $$0.pose().translate(0.0F, $$6, 0.0F);
/*     */     
/* 247 */     this.logoRenderer.renderLogo($$0, this.width, 1.0F, $$5);
/*     */     
/* 249 */     int $$7 = $$5 + 100;
/*     */     
/* 251 */     for (int $$8 = 0; $$8 < this.lines.size(); $$8++) {
/* 252 */       if ($$8 == this.lines.size() - 1) {
/* 253 */         float $$9 = $$7 + $$6 - (this.height / 2 - 6);
/* 254 */         if ($$9 < 0.0F) {
/* 255 */           $$0.pose().translate(0.0F, -$$9, 0.0F);
/*     */         }
/*     */       } 
/* 258 */       if ($$7 + $$6 + 12.0F + 8.0F > 0.0F && $$7 + $$6 < this.height) {
/* 259 */         FormattedCharSequence $$10 = this.lines.get($$8);
/* 260 */         if (this.centeredLines.contains($$8)) {
/* 261 */           $$0.drawCenteredString(this.font, $$10, $$4 + 128, $$7, 16777215);
/*     */         } else {
/* 263 */           $$0.drawString(this.font, $$10, $$4, $$7, 16777215);
/*     */         } 
/*     */       } 
/* 266 */       $$7 += 12;
/*     */     } 
/*     */     
/* 269 */     $$0.pose().popPose();
/*     */     
/* 271 */     RenderSystem.enableBlend();
/* 272 */     RenderSystem.blendFunc(GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR);
/* 273 */     $$0.blit(VIGNETTE_LOCATION, 0, 0, 0, 0.0F, 0.0F, this.width, this.height, this.width, this.height);
/* 274 */     RenderSystem.disableBlend();
/* 275 */     RenderSystem.defaultBlendFunc();
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderBackground(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 280 */     int $$4 = this.width;
/* 281 */     float $$5 = this.scroll * 0.5F;
/* 282 */     int $$6 = 64;
/*     */     
/* 284 */     float $$7 = this.scroll / this.unmodifiedScrollSpeed;
/* 285 */     float $$8 = $$7 * 0.02F;
/*     */     
/* 287 */     float $$9 = (this.totalScrollLength + this.height + this.height + 24) / this.unmodifiedScrollSpeed;
/* 288 */     float $$10 = ($$9 - 20.0F - $$7) * 0.005F;
/* 289 */     if ($$10 < $$8) {
/* 290 */       $$8 = $$10;
/*     */     }
/* 292 */     if ($$8 > 1.0F) {
/* 293 */       $$8 = 1.0F;
/*     */     }
/* 295 */     $$8 *= $$8;
/* 296 */     $$8 = $$8 * 96.0F / 255.0F;
/*     */     
/* 298 */     $$0.setColor($$8, $$8, $$8, 1.0F);
/* 299 */     $$0.blit(BACKGROUND_LOCATION, 0, 0, 0, 0.0F, $$5, $$4, this.height, 64, 64);
/* 300 */     $$0.setColor(1.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removed() {
/* 305 */     this.minecraft.getMusicManager().stopPlaying(Musics.CREDITS);
/*     */   }
/*     */ 
/*     */   
/*     */   public Music getBackgroundMusic() {
/* 310 */     return Musics.CREDITS;
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   private static interface CreditsReader {
/*     */     void read(Reader param1Reader) throws IOException;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\WinScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */