/*     */ package net.minecraft;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.net.URL;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum OS
/*     */ {
/* 310 */   LINUX("linux"),
/* 311 */   SOLARIS("solaris"),
/* 312 */   WINDOWS("windows")
/*     */   {
/*     */     protected String[] getOpenUrlArguments(URL $$0) {
/* 315 */       return new String[] { "rundll32", "url.dll,FileProtocolHandler", $$0.toString() };
/*     */     }
/*     */   },
/* 318 */   OSX("mac")
/*     */   {
/*     */     protected String[] getOpenUrlArguments(URL $$0) {
/* 321 */       return new String[] { "open", $$0.toString() };
/*     */     }
/*     */   },
/* 324 */   UNKNOWN("unknown");
/*     */   
/*     */   private final String telemetryName;
/*     */   
/*     */   OS(String $$0) {
/* 329 */     this.telemetryName = $$0;
/*     */   }
/*     */   
/*     */   public void openUrl(URL $$0) {
/*     */     try {
/* 334 */       Process $$1 = AccessController.<Process>doPrivileged(() -> Runtime.getRuntime().exec(getOpenUrlArguments($$0)));
/* 335 */       $$1.getInputStream().close();
/* 336 */       $$1.getErrorStream().close();
/* 337 */       $$1.getOutputStream().close();
/* 338 */     } catch (PrivilegedActionException|java.io.IOException $$2) {
/* 339 */       Util.LOGGER.error("Couldn't open url '{}'", $$0, $$2);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void openUri(URI $$0) {
/*     */     try {
/* 345 */       openUrl($$0.toURL());
/* 346 */     } catch (MalformedURLException $$1) {
/* 347 */       Util.LOGGER.error("Couldn't open uri '{}'", $$0, $$1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void openFile(File $$0) {
/*     */     try {
/* 353 */       openUrl($$0.toURI().toURL());
/* 354 */     } catch (MalformedURLException $$1) {
/* 355 */       Util.LOGGER.error("Couldn't open file '{}'", $$0, $$1);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected String[] getOpenUrlArguments(URL $$0) {
/* 360 */     String $$1 = $$0.toString();
/* 361 */     if ("file".equals($$0.getProtocol()))
/*     */     {
/* 363 */       $$1 = $$1.replace("file:", "file://");
/*     */     }
/* 365 */     return new String[] { "xdg-open", $$1 };
/*     */   }
/*     */   
/*     */   public void openUri(String $$0) {
/*     */     try {
/* 370 */       openUrl((new URI($$0)).toURL());
/*     */     }
/* 372 */     catch (URISyntaxException|MalformedURLException|IllegalArgumentException $$1) {
/* 373 */       Util.LOGGER.error("Couldn't open uri '{}'", $$0, $$1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String telemetryName() {
/* 378 */     return this.telemetryName;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\Util$OS.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */