package com.mojang.realmsclient.gui.screens;

import com.mojang.realmsclient.gui.RealmsDataFetcher;
import com.mojang.realmsclient.gui.task.DataFetcher;

interface DataFetcherConfiguration {
  DataFetcher.Subscription initDataFetcher(RealmsDataFetcher paramRealmsDataFetcher);
  
  boolean showOldNotifications();
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\screens\RealmsNotificationsScreen$DataFetcherConfiguration.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */