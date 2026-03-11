/*    */ package net.minecraft.client.resources;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.io.BufferedReader;
/*    */ import java.io.IOException;
/*    */ import java.util.Calendar;
/*    */ import java.util.Collections;
/*    */ import java.util.Date;
/*    */ import java.util.List;
/*    */ import java.util.Locale;
/*    */ import java.util.stream.Collectors;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.User;
/*    */ import net.minecraft.client.gui.components.SplashRenderer;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.server.packs.resources.ResourceManager;
/*    */ import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.util.profiling.ProfilerFiller;
/*    */ 
/*    */ public class SplashManager
/*    */   extends SimplePreparableReloadListener<List<String>> {
/* 24 */   private static final ResourceLocation SPLASHES_LOCATION = new ResourceLocation("texts/splashes.txt");
/* 25 */   private static final RandomSource RANDOM = RandomSource.create();
/*    */   
/* 27 */   private final List<String> splashes = Lists.newArrayList();
/*    */   private final User user;
/*    */   
/*    */   public SplashManager(User $$0) {
/* 31 */     this.user = $$0;
/*    */   }
/*    */   
/*    */   protected List<String> prepare(ResourceManager $$0, ProfilerFiller $$1) {
/*    */     
/* 36 */     try { BufferedReader $$2 = Minecraft.getInstance().getResourceManager().openAsReader(SPLASHES_LOCATION);
/*    */ 
/*    */       
/* 39 */       try { List<String> list = (List)$$2.lines().map(String::trim).filter($$0 -> ($$0.hashCode() != 125780783)).collect(Collectors.toList());
/* 40 */         if ($$2 != null) $$2.close();  return list; } catch (Throwable throwable) { if ($$2 != null) try { $$2.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException $$3)
/* 41 */     { return Collections.emptyList(); }
/*    */   
/*    */   }
/*    */ 
/*    */   
/*    */   protected void apply(List<String> $$0, ResourceManager $$1, ProfilerFiller $$2) {
/* 47 */     this.splashes.clear();
/* 48 */     this.splashes.addAll($$0);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public SplashRenderer getSplash() {
/* 53 */     Calendar $$0 = Calendar.getInstance();
/* 54 */     $$0.setTime(new Date());
/*    */     
/* 56 */     if ($$0.get(2) + 1 == 12 && $$0.get(5) == 24)
/* 57 */       return SplashRenderer.CHRISTMAS; 
/* 58 */     if ($$0.get(2) + 1 == 1 && $$0.get(5) == 1)
/* 59 */       return SplashRenderer.NEW_YEAR; 
/* 60 */     if ($$0.get(2) + 1 == 10 && $$0.get(5) == 31) {
/* 61 */       return SplashRenderer.HALLOWEEN;
/*    */     }
/*    */ 
/*    */     
/* 65 */     if (this.splashes.isEmpty())
/* 66 */       return null; 
/* 67 */     if (this.user != null && RANDOM.nextInt(this.splashes.size()) == 42) {
/* 68 */       return new SplashRenderer(this.user.getName().toUpperCase(Locale.ROOT) + " IS YOU");
/*    */     }
/* 70 */     return new SplashRenderer(this.splashes.get(RANDOM.nextInt(this.splashes.size())));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\SplashManager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */